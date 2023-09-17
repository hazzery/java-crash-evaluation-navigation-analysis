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
        return this;
    }

    /**
     * Create query for string searching queryField
     * @param searchString String input by user to search for
     * @param queryField The particular crash attribute to filter by
     */
    public QueryBuilder likeString(String searchString, DbAttributes queryField) {
        sql.append("(").append(queryField).append(" LIKE ").append(searchString).append(") AND ");
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

        sql.append("(").append(queryField).append(" = (");
        for (String condition : conditionList) {
            sql.append(condition).append(" OR ");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 4));  // Remove trailing " OR "
        sql.append(")) AND ");
        return this;
    }

    /**
     * Return generated query
     *
     * @return final concatenated query
     */
    public String getQuery() {
        sql = new StringBuilder(sql.substring(0, sql.length() - 5));
        sql.append(";");
        log.info(sql.toString());
        return sql.toString();
    }

}
