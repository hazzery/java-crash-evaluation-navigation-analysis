package seng202.team2.database;

import seng202.team2.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDao extends Dao<Location> {
    private final DatabaseManager databaseManager;

    /**
     * Database Access Object for Location objects.
     * Establishes a connection to the database.
     */
    public LocationDao() {
        this.databaseManager = DatabaseManager.getInstance();
    }

    /**
     * Converts a ResultSet to a location.
     *
     * @param resultSet ResultSet to convert.
     * @return Location object.
     * @throws SQLException if there is an error converting the ResultSet.
     */
    @Override
    protected Location fromResultSet(ResultSet resultSet) throws SQLException {
        return new Location(
                        resultSet.getInt(DbAttributes.ID.dbColumn()),
                        resultSet.getDouble(DbAttributes.LATITUDE.dbColumn()),
                        resultSet.getDouble(DbAttributes.LONGITUDE.dbColumn()),
                        Severity.fromString(resultSet.getString(DbAttributes.SEVERITY.dbColumn()))
        );
    }

    /**
     * Gets the columns of the crashes table are required for a location.
     *
     * @return String to use as SQL SELECT clause
     */
    @Override
    protected String getColumns() {
        return "id, latitude, longitude, severity";
    }
}
