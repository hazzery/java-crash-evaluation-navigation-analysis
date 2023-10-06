package seng202.team2.database;

import org.apache.commons.lang3.NotImplementedException;
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
 *     SENG202 Advanced Applications with JavaFX</a>
 */
public class CrashDao {
    private final DatabaseManager databaseManager;
    private static final Logger log = LogManager.getLogger(CrashDao.class);


    public CrashDao() {
        this.databaseManager = DatabaseManager.getInstance();
    }


    /**
     * Gets all crashes in database
     *
     * @return a list of all crashes
     */
    @Override
    public List<Crash> getAll() {
        String sql = "SELECT * FROM crashes";
        return queryDatabase(sql);
    }

    /**
     * Gets a single crash from the database by id
     *
     * @param id id of object to get
     * @return Crash that has id given
     */
    @Override
    public Crash getOne(int id) {
        Crash crash = null;
        String sql = "SELECT * FROM crashes WHERE id=?";
        try (PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    crash = crashFromResultSet(resultSet);
                }

                return crash;
            } catch (SQLException exception) {
                log.error(exception);
                return null;
            }
        } catch (SQLException exception) {
            log.error(exception);
            return null;
        }
    }

    public List<Crash> queryDatabase(String sql) {
        List<Crash> crashes = new ArrayList<>();

        try (Statement statement = databaseManager.getConnection().createStatement();
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

    private List<Crash> crashListFromResults(ResultSet resultSet) throws SQLException {
        ArrayList<Crash> crashes = new ArrayList<>();

        while (resultSet.next()) {
            crashes.add(crashFromResultSet(resultSet));
        }

        return crashes;
    }


    private void prepareStatementForCrash(PreparedStatement preparedStatement, Crash crash) throws SQLException {
        preparedStatement.setInt(DbAttributes.ID.dbColumn(), crash.crashID());
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
     * Adds a single object of type T to database
     *
     * @param toAdd object of type T to add
     * @return object insert id if inserted correctly
     */
    @Override
    public int add(Crash toAdd) {
        String sql = "INSERT INTO crashes values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql)) {
            prepareStatementForCrash(preparedStatement, toAdd);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int insertId = -1;
            if (resultSet.next()) {
                insertId = resultSet.getInt(1);
            }
            return insertId;
        } catch (SQLException sqlException) {
            log.error(sqlException);
            return -1;
        }
    }

    /**
     * Adds a batch of sales to the database
     * This is done much quicker than individually
     * @param toAdd list of sales to add to the database
     */
    public void addBatch (List <Crash> toAdd) {
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
            while (resultSet.next()){
                log.info(resultSet.getLong(1));
            }
            connection.commit();
        } catch (SQLException sqlException) {
            log.info(sqlException);
        }
    }

    /**
     * Deletes and object from database that matches id given
     *
     * @param id id of object to delete
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM crashes WHERE id=?";
        try (PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            log.error(sqlException);
        }
    }

    /**
     * Updates an object in the database
     *
     * @param toUpdate Object that needs to be updated (this object must be able to identify itself and its previous self)
     */
    @Override
    public void update(Crash toUpdate) {
        throw new NotImplementedException();
    }
}
