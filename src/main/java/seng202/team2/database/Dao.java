package seng202.team2.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for Database Access Objects (DAOs) that provides common functionality for database access
 *
 * @author Harrison Parkes
 */
public abstract class Dao<T> {
    /**
     * Converts a ResultSet to an object of type T
     *
     * @param resultSet ResultSet to convert
     * @return Object of type T
     * @throws SQLException if there is an error converting the ResultSet
     */
    protected abstract T fromResultSet(ResultSet resultSet) throws SQLException;

    /**
     * Gets the columns of the crashes table that the DAO is for
     *
     * @return String to use as SQL SELECT clause
     */
    protected abstract String getColumns();

    /**
     * Queries the database with the given SQL query and returns the results as a list of objects of type T
     *
     * @param sql SQL query to run.
     * @return List of objects of type T that match the query.
     */
    private List<T> queryDatabase(String sql) {
        List<T> objects = new ArrayList<>();

        Logger log = LogManager.getLogger(this.getClass());
        DatabaseManager databaseManager = DatabaseManager.getInstance();

        try (Connection connection = databaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            log.info("Queried " + sql);

            while (resultSet.next()) {
                objects.add(fromResultSet(resultSet));
            }

            return objects;
        } catch (SQLException sqlException) {
            log.error(sqlException);
            return new ArrayList<>();
        }
    }

    /**
     * Gets all objects of type T from the database that match the filter.
     *
     * @param filter SQL WHERE clause for database query.
     * @return List of objects of type T that match the filter.
     */
    public List<T> getSubset(String filter) {
        return queryDatabase("SELECT " + getColumns() + " FROM crashes WHERE " + filter);
    }

    /**
     * Gets a single object of type T from the database by id
     *
     * @param id id of object to get
     * @return Object of type T that has id given
     */
    public T getOne(int id) {
        List<T> objects = getSubset("id = " + id);

        if (objects.isEmpty()) {
            return null;
        } else {
            return objects.get(0);
        }
    }

    /**
     * Gets all of T from the database
     *
     * @return List of all objects type T from the database
     */
    public List<T> getAll() {
        return queryDatabase("SELECT " + getColumns() + " FROM crashes;");
    }
}
