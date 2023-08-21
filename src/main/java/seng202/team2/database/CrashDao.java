package seng202.team2.database;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team2.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts between SQLite Database and {@link Crash} objects
 *
 * @author Harrison Parkes
 * @see <a href="https://docs.google.com/document/d/1OzJJYrHxHRYVzx_MKjC2XPGS8_arDKSxYD4NhDN37_E/edit">
 *     SENG202 Advanced Applications with JavaFX</a>
 */
public class CrashDao implements DaoInterface<Crash> {
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
        List<Crash> crashes = new ArrayList<>();
        String sql = "SELECT * FROM crashes";
        try (Statement statement = databaseManager.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

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
     * Gets a single object of type T from the database by id
     *
     * @param id id of object to get
     * @return Object of type T that has id given
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
            }
        } catch (SQLException exception) {
            log.error(exception);
            return null;
        }
    }

    private Crash crashFromResultSet(ResultSet resultSet) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();

        for (Vehicle vehicle : Vehicle.values()) {
            // TODO yeah Crash::vehicles should definitely be a map
            int vehicleCount = resultSet.getInt(
                    14 + vehicle.ordinal());
            for (int i = 0; i < vehicleCount; i++) {
                vehicles.add(vehicle);
            }
        }
        return new Crash(
                resultSet.getInt("crash_id"),
                resultSet.getInt("year"),
                resultSet.getInt("fatalities"),
                resultSet.getInt("serious_injuries"),
                resultSet.getInt("minor_injuries"),
                resultSet.getDouble("latitude"),
                resultSet.getDouble("longitude"),
                resultSet.getString("road_name_1"),
                resultSet.getString("road_name_2"),
                resultSet.getString("region"),
                vehicles.toArray(new Vehicle[0]),
                Weather.fromString(resultSet.getString("weather")),
                Lighting.fromString(resultSet.getString("lighting")),
                Severity.fromString(resultSet.getString("severity"))
        );
    }

    public void prepareStatementForCrash(PreparedStatement preparedStatement, Crash crash) throws SQLException {
        preparedStatement.setInt(1, crash.crashID());
        preparedStatement.setInt(2, crash.year());
        preparedStatement.setInt(3, crash.fatalities());
        preparedStatement.setInt(4, crash.seriousInjuries());
        preparedStatement.setInt(5, crash.minorInjuries());
        preparedStatement.setDouble(6, crash.latitude());
        preparedStatement.setDouble(7, crash.longitude());
        preparedStatement.setString(8, crash.roadName1());
        preparedStatement.setString(9, crash.roadName2());
        preparedStatement.setString(10, crash.region());
        preparedStatement.setString(11, crash.weather().toString());
        preparedStatement.setString(12, crash.lighting().toString());
        preparedStatement.setString(13, crash.severity().toString());

        // TODO maybe crash::vehicles should be a map
        ResultSet resultSet =  preparedStatement.getGeneratedKeys();
        for (Vehicle vehicle : crash.vehicles()) {
            preparedStatement.setInt(14 + vehicle.ordinal(), resultSet.getInt(vehicle.getCsvColumn()) + 1);
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
        String sql = "INSERT OR IGNORE INTO crashes values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
            log.error(sqlException);
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
