package seng202.team2.database;
import java.util.ArrayList;

/**
 * Query builder class
 *
 * @author James Lanigan
 */
public class QueryBuilder {
    private final DatabaseManager databaseManager;

    private StringBuilder sql = new StringBuilder("SELECT * FROM crashes WHERE ");

    public QueryBuilder(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
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
     * @param conditionList list of conditions for checking
     * @param queryField field for querying
     */
    public void andString(ArrayList<String> conditionList, DbAttributes queryField) {
        sql.append("(" + queryField + " == (");
        for (String condition : conditionList) {
            sql.append(condition + " OR ");
        }
    }

    /**
     *
     * @return final concatenated query
     */
    public String getQuery() {
        sql = new StringBuilder(sql.substring(0, sql.length() - 5));
        sql.append(";");
        return sql.toString();
    }


}
