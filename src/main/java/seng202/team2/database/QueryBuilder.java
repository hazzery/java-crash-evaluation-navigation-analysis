package seng202.team2.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Query builder class
 *
 * @author James Lanigan
 */
public class QueryBuilder {
    private static final Logger log = LogManager.getLogger(QueryBuilder.class);

    private StringBuilder sql = new StringBuilder("SELECT * FROM crashes WHERE ");
    private boolean noConditions = true; // Used to remove the WHERE of empty query

    public QueryBuilder() {
    }

    /**
     * Filter out tuples with values of `queryField` not between lowerBound and upperBound (inclusive).
     *
     * @param lowerBound lowest year value to include in results.
     * @param upperBound highest year value to include in results.
     */
    public void betweenValues(int lowerBound, int upperBound, DbAttributes queryField) {
        sql.append("(").append(queryField)
                        .append(" BETWEEN ").append(lowerBound).append(" AND ").append(upperBound)
                        .append(") AND ");
        noConditions = false;
    }

    /**
     * Filter out tuple with values of `queryField` not equal to `value`.
     *
     * @param value      value for comparison.
     * @param queryField field for comparison.
     */
    public void equalVal(int value, DbAttributes queryField) {
        sql.append("(").append(queryField).append(" = ").append(value).append(") AND ");
        noConditions = false;
    }

    /**
     * Filter out tuples with values of `queryField` greater than or equal to `upperBound`.
     *
     * @param upperBound The less than comparison operand.
     * @param queryField The particular crash attribute to filter by.
     */
    public void lessThan(int upperBound, DbAttributes queryField) {
        sql.append("(").append(queryField).append(" < ").append(upperBound).append(") AND ");
        noConditions = false;
    }

    /**
     * Filter out tuples with values of `queryField` less than or equal to `lowerBound`
     *
     * @param lowerBound The greater than comparison operand.
     * @param queryField The particular crash attribute to filter by.
     */
    public void greaterThan(int lowerBound, DbAttributes queryField) {
        sql.append("(").append(queryField).append(" > ").append(lowerBound).append(") AND ");
        noConditions = false;
    }

    /**
     * Filters out tuples which do not have at least one of the vehicles specified in `queryVehicles`.
     *
     * @param queryVehicles List of vehicles to include in results.
     */
    public void orVehicle(List<DbAttributes> queryVehicles) {
        for (DbAttributes vehicle : queryVehicles) {
            sql.append("(").append(vehicle).append(" > 0)").append(" OR ");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 4));  // Remove trailing ` OR "`
        sql.append(" AND ");
        noConditions = false;

    }

    /**
     * Filters out all tuples which do not have a value of `queryField` similar to `searchString`.
     * NOTE: Current implementation functions only as direct match, not matching similar string.
     *
     * @param searchString String input by user to search for.
     * @param queryField   The particular crash attribute to filter by.
     */
    public void likeString(String searchString, DbAttributes queryField) {
        sql.append("(").append(queryField).append(" LIKE ").append(searchString).append(") AND ");
        noConditions = false;
    }

    /**
     * Filters out all tuples which do not have a value of `queryField`
     * that is equal to one of the values in `conditionList`.
     *
     * @param conditionList list of allowable values for `queryField`.
     * @param queryField    field for filtering
     */
    public void orString(List<String> conditionList, DbAttributes queryField) {
        if (conditionList.isEmpty()) {
            return;
        }

        sql.append("(");
        for (String condition : conditionList) {
            sql.append(queryField).append(" = ");
            sql.append("\"" + condition).append("\" OR ");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 4));  // Remove trailing ` OR "`
        sql.append(") AND ");
        noConditions = false;
    }

    /**
     * Return the SQL query string for the given query
     *
     * @return final concatenated query
     */
    public String getQuery() {
        int amountToRemove = 5; // Remove trailing " AND "
        if (noConditions) {
            amountToRemove = 7; // Remove trailing " WHERE "
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - amountToRemove));
        sql.append(";");
        log.info(sql.toString());
        return sql.toString();
    }

}
