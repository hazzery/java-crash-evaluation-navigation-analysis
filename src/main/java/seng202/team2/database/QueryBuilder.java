package seng202.team2.database;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team2.models.*;

import javax.management.Query;
import java.util.ArrayList;

/**
 * Query builder class
 *
 * @author James Lanigan
 */
public class QueryBuilder {
    private static final Logger log = LogManager.getLogger(QueryBuilder.class);
    private final DatabaseManager databaseManager;

    private StringBuilder sql = new StringBuilder("SELECT * FROM crashes WHERE ");

    public QueryBuilder() {
        this.databaseManager = DatabaseManager.getInstance();
    }

    /**
     * Creates query for tuples between val1 and val2 inclusive
     *
     * @param val1 lower bound for year
     * @param val2 upper bound for year
     */
    public void betweenVals(int val1, int val2, DbAttributes queryField) {
        sql.append("(" + queryField + " >= " + val1 + " AND " + queryField + " <= " + val2 + ") AND ");
    }

    /**
     * Creates query for queryField == val1
     *
     * @param val1 value for comparison
     * @param queryField field for comparison
     */
    public void equalVal(int val1, DbAttributes queryField) {
        sql.append("(" + queryField + " == " + val1 + ") AND ");
    }

    /**
     * Create query for string searching queryField
     */
    public void likeString(String searchString, DbAttributes queryField) {
        sql.append("(" + queryField + " LIKE " + searchString + ") AND ");
    }

    /**
     * Create query for tuples satisfying all conditions in conditionList
     *
     * @param conditionList list of conditions for checking (String)
     * @param queryField field for querying
     */
    public void andString(ArrayList<String> conditionList, DbAttributes queryField) {
        sql.append("(" + queryField + " == (");
        for (String condition : conditionList) {
            sql.append(condition + " AND ");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 5));
        sql.append(")) AND ");
    }

    /**
     * Create query for tuples satisfying one condition in conditionList
     *
     * @param conditionList list of conditions for checking (String)
     * @param queryField field for querying
     */
    public void orString(ArrayList<String> conditionList, DbAttributes queryField) {
        sql.append("(" + queryField + " == (");
        for (String condition : conditionList) {
            sql.append(condition + " OR ");
        }
        sql = new StringBuilder(sql.substring(0, sql.length() - 4));
        sql.append(")) AND ");
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
