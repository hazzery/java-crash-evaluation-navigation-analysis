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
     * Gets all sales in database
     *
     * @return a list of all sales
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
            int vehicleCount = resultSet.getInt(7 + vehicle.ordinal());
            for (int i = 0; i < vehicleCount; i++) {
                vehicles.add(vehicle);
            }
        }
        return new Crash(
                resultSet.getInt("year"),
                resultSet.getInt("fatalities"),
                resultSet.getDouble("latitude"),
                resultSet.getDouble("longitude"),
                vehicles.toArray(new Vehicle[0]),
                Weather.fromString(resultSet.getString("weather")),
                Lighting.fromString(resultSet.getString("lighting")),
                Severity.fromString(resultSet.getString("severity"))
        );
    }

    /**
     * Adds a single object of type T to database
     *
     * @param toAdd object of type T to add
     * @return object insert id if inserted correctly
     */
    @Override
    public int add(Crash toAdd) {
        String sql = "INSERT INTO crashes values (?,?,?,?,?,?,?);";
        try (PreparedStatement preparedStatement = databaseManager.getConnection().prepareStatement(sql)) {
            for (Vehicle vehicle : toAdd.vehicles()) {
                // TODO maybe crash::vehicles should be a map
                ResultSet resultSet =  preparedStatement.getGeneratedKeys();
                preparedStatement.setInt(7 + vehicle.ordinal(), resultSet.getInt(vehicle.getCsvColumn()) + 1);
            }

            preparedStatement.setInt(0, toAdd.year());
            preparedStatement.setInt(1, toAdd.fatalities());
            preparedStatement.setDouble(2, toAdd.latitude());
            preparedStatement.setDouble(3, toAdd.longitude());
            preparedStatement.setString(4, toAdd.weather().toString());
            preparedStatement.setString(5, toAdd.lighting().toString());
            preparedStatement.setString(6, toAdd.severity().toString());

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
