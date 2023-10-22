package seng202.team2.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.team2.database.CrashDao;
import seng202.team2.database.IdDao;
import seng202.team2.database.QueryBuilder;
import seng202.team2.io.CsvReader;

/**
 * The Crashes class acts as a pool of all crashes the met the previous query.
 * It is a centralised location that both the table and map view pull from.
 *
 * @author Harrison Parkes
 */
public class Crashes {
    private static ObservableList<Integer> crashIds;
    private static final CrashDao crashDao = new CrashDao();
    private static final IdDao idDao = new IdDao();

    /**
     * Import all crashes from the CSV file and add them to the database.
     */
    public static void importCrashes() {
        CsvReader csvReader = new CsvReader("crash_data.csv");
        crashDao.addBatch(csvReader.generateAllCrashes());
        crashIds = FXCollections.observableList(idDao.getAll());
    }


    /**
     * Get all crashes in the current pool.
     *
     * @return A list of all crashes in the current pool.
     */
    public static ObservableList<Integer> getCrashes() {
        return crashIds;
    }

    /**
     * Update the current pool of crashes.
     * This method empties the current pool and replaces it with the results of the query.
     *
     * @param query A QueryBuilder containing the query for the database.
     */
    public static void setQuery(QueryBuilder query) {
        crashIds = FXCollections.observableList(idDao.getSubset(query.getQuery()));
    }
}
