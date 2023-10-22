package seng202.team2.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IdDao extends Dao<Integer> {

    /**
     * Converts a ResultSet to a crash ID
     *
     * @param resultSet ResultSet to convert
     * @return Crash ID
     * @throws SQLException if there is an error converting the ResultSet
     */
    @Override
    protected Integer fromResultSet(ResultSet resultSet) throws SQLException {
        return resultSet.getInt(DbAttributes.ID.dbColumn());
    }

    /**
     * Gets the columns of the crashes table that the DAO is for
     *
     * @return String to use as SQL SELECT clause
     */
    @Override
    protected String getColumns() {
        return "id";
    }
}
