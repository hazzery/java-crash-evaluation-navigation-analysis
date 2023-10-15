package seng202.team2.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team2.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts between SQLite Database and {@link Crash} objects
 *
 * @author Harrison Parkes
 * @see <a href="https://docs.google.com/document/d/1OzJJYrHxHRYVzx_MKjC2XPGS8_arDKSxYD4NhDN37_E/edit">
 * SENG202 Advanced Applications with JavaFX</a>
 */
public class CrashDao {
    private final DatabaseManager databaseManager;
    private static final Logger log = LogManager.getLogger(CrashDao.class);


    /**
     * Database Access Object for Crash objects.
     * Establishes a connection to the database.
     */
    public CrashDao() {
        this.databaseManager = DatabaseManager.getInstance();
    }

    /**
     * Get a selection of crashes from the database.
     *
     * @param sql An SQL query to select the crashes to return.
     * @return A list of crashes matching the query.
     */
    public List<Crash> queryDatabase(String sql) {
        List<Crash> crashes = new ArrayList<>();

        try (Connection connection = databaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            log.info("Queried " + sql);

            while (resultSet.next()) {
                crashes.add(crashFromResultSet(resultSet));
            }
            return crashes;
        } catch (SQLException sqlException) {
            log.error(sqlException);
            return new ArrayList<>();
        }
    }

    /**
     * Convert a JDBC ResultSet to a Crash object.
     *
     * @param resultSet The result of the SQL query
     * @return A Crash object with the data from the ResultSet
     * @throws SQLException If the ResultSet is invalid
     */
    private Crash crashFromResultSet(ResultSet resultSet) throws SQLException {
        Map<Vehicle, Integer> vehicles = new HashMap<>();

        for (Vehicle vehicle : Vehicle.values()) {
            int vehicleCount = resultSet.getInt(vehicle.getDbColumn());
            for (int i = 0; i < vehicleCount; i++) {
                vehicles.put(vehicle, vehicleCount);
            }
        }
        return new Crash(
                        resultSet.getInt(DbAttributes.ID.dbColumn()),
                        resultSet.getInt(DbAttributes.YEAR.dbColumn()),
                        resultSet.getInt(DbAttributes.FATALITIES.dbColumn()),
                        resultSet.getInt(DbAttributes.SERIOUS_INJURIES.dbColumn()),
                        resultSet.getInt(DbAttributes.MINOR_INJURIES.dbColumn()),
                        resultSet.getDouble(DbAttributes.LATITUDE.dbColumn()),
                        resultSet.getDouble(DbAttributes.LONGITUDE.dbColumn()),
                        resultSet.getString(DbAttributes.ROAD_NAME_1.dbColumn()),
                        resultSet.getString(DbAttributes.ROAD_NAME_2.dbColumn()),
                        Region.fromString(resultSet.getString(DbAttributes.REGION.dbColumn())),
                        Weather.fromString(resultSet.getString(DbAttributes.WEATHER.dbColumn())),
                        Lighting.fromString(resultSet.getString(DbAttributes.LIGHTING.dbColumn())),
                        Severity.fromString(resultSet.getString(DbAttributes.SEVERITY.dbColumn())),
                        vehicles
        );
    }

    /**
     * Prepares a JDBC SQL insertion for a given crash
     *
     * @param preparedStatement The statement to add the crash data into
     * @param crash             The crash to add to the statement
     * @throws SQLException If the statement is not of the correct format for a crash
     */
    private void prepareStatementForCrash(PreparedStatement preparedStatement, Crash crash) throws SQLException {
        preparedStatement.setInt(DbAttributes.ID.dbColumn(), crash.id());
        preparedStatement.setInt(DbAttributes.YEAR.dbColumn(), crash.year());
        preparedStatement.setInt(DbAttributes.FATALITIES.dbColumn(), crash.fatalities());
        preparedStatement.setInt(DbAttributes.SERIOUS_INJURIES.dbColumn(), crash.seriousInjuries());
        preparedStatement.setInt(DbAttributes.MINOR_INJURIES.dbColumn(), crash.minorInjuries());
        preparedStatement.setDouble(DbAttributes.LATITUDE.dbColumn(), crash.latitude());
        preparedStatement.setDouble(DbAttributes.LONGITUDE.dbColumn(), crash.longitude());
        preparedStatement.setString(DbAttributes.ROAD_NAME_1.dbColumn(), crash.roadName1());
        preparedStatement.setString(DbAttributes.ROAD_NAME_2.dbColumn(), crash.roadName2());
        preparedStatement.setString(DbAttributes.REGION.dbColumn(), crash.region().toString());
        preparedStatement.setString(DbAttributes.WEATHER.dbColumn(), crash.weather().toString());
        preparedStatement.setString(DbAttributes.LIGHTING.dbColumn(), crash.lighting().toString());
        preparedStatement.setString(DbAttributes.SEVERITY.dbColumn(), crash.severity().toString());

        for (Vehicle vehicle : crash.vehicles().keySet()) {
            preparedStatement.setInt(vehicle.getDbColumn(), crash.vehicles().get(vehicle));
        }
    }

    /**
     * Adds a batch of sales to the database
     * This is done much quicker than individually
     *
     * @param toAdd list of sales to add to the database
     */
    public void addBatch(List<Crash> toAdd) {
        String sql = "INSERT INTO crashes values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            for (Crash crash : toAdd) {
                prepareStatementForCrash(preparedStatement, crash);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                log.info(resultSet.getLong(1));
            }
            connection.commit();
        } catch (SQLException sqlException) {
            log.info(sqlException);
        }
    }
}
