package seng202.team2.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.Query;
import java.util.List;

/**
 * Query builder class
 *
 * @author James Lanigan
 */
public class QueryBuilder {
    private static final Logger log = LogManager.getLogger(QueryBuilder.class);

    private StringBuilder sql = new StringBuilder("SELECT * FROM crashes WHERE ");
    private boolean noConditions = true; // remove the WHERE
    
    public QueryBuilder() {}

    /**
     * Creates query for tuples between lowerBound and upperBound inclusive
     *
     * @param lowerBound lowest year value to include in query
     * @param upperBound highest year value to include in query
     */
    public QueryBuilder betweenValues(int lowerBound, int upperBound, DbAttributes queryField) {
        sql.append("(").append(queryField)
                .append(" BETWEEN ").append(lowerBound).append(" AND ").append(upperBound)
                .append(") AND ");
        noConditions = false;
        return this;
    }

    /**
     * Creates query for queryField == val1
     *
     * @param value value for comparison
     * @param queryField field for comparison
     */
    public QueryBuilder equalVal(int value, DbAttributes queryField) {
        sql.append("(").append(queryField).append(" = ").append(value).append(") AND ");
        noConditions = false;
        return this;
    }

    /**
     * Query all tuples with queryField less than upperBound
     *
     * @param upperBound The less than comparison operand
     * @param queryField The particular crash attribute to filter by
     */
    public QueryBuilder lessThan(int upperBound, DbAttributes queryField) {
        sql.append("(").append(queryField).append(" < ").append(upperBound).append(") AND ");
        noConditions = false;
        return this;
    }

    /**
     * Query all tuples with queryField greater than val1 exclusive
     *
     * @param lowerBound The greater than comparison operand
     * @param queryField The particular crash attribute to filter by
     */
    public QueryBuilder greaterThan(int lowerBound, DbAttributes queryField) {
        sql.append("(").append(queryField).append(" > ").append(lowerBound).append(") AND ");
        noConditions = false;
        return this;
    }

    /**
     * Builds query with list of vehicles
     * Checks if number of one is greater than 1.
     *
     * @param queryVehicles List of DbAttributes to be checked
     */
    public QueryBuilder orVehicle(List<DbAttributes> queryVehicles) {
        if (queryVehicles.isEmpty()) {
            return this;
        }
        sql.append("(");
        for (DbAttributes vehicle : queryVehicles) {
            sql.append("(").append(vehicle).append(" > 0)").append(" OR ");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 4));  // Remove trailing ` OR "`
        sql.append(") AND ");
        noConditions = false;
        return this;

    }

    /**
     * Create query for string searching queryField
     * @param searchString String input by user to search for
     * @param queryField The particular crash attribute to filter by
     */
    public QueryBuilder likeString(String searchString, DbAttributes queryField) {
        sql.append("(").append(queryField).append(" LIKE ").append(searchString).append(") AND ");
        noConditions = false;
        return this;
    }

    /**
     * Create query for tuples satisfying one condition in conditionList
     *
     * @param conditionList list of conditions for checking (String)
     * @param queryField field for querying
     */
    public QueryBuilder orString(List<String> conditionList, DbAttributes queryField) {
        if (conditionList.isEmpty()) {
            return this;
        }

        sql.append("(");
        for (String condition : conditionList) {
            sql.append(queryField).append(" = ");
            sql.append("\"" + condition).append("\" OR ");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 4));  // Remove trailing ` OR "`
        sql.append(") AND ");
        noConditions = false;
        return this;
    }

    /**
     * Return generated query
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
