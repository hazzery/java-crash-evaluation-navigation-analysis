package seng202.team2.models;

import seng202.team2.database.CrashDao;
import seng202.team2.database.QueryBuilder;
import seng202.team2.io.CsvReader;

import java.util.List;

/**
 * The Crashes class acts as a pool of all crashes the met the previous query.
 * It is a centralised location that both the table and map view pull from.
 *
 * @author Harrison Parkes
 */
public class Crashes {
    private static List<Crash> crashes;
    private static final CrashDao crashDao = new CrashDao();

    /**
     * Import all crashes from the CSV file and add them to the database.
     */
    public static void importCrashes() {
        CsvReader csvReader = new CsvReader("crash_data.csv");
        crashes = csvReader.generateAllCrashes();
        crashDao.addBatch(crashes);
    }

    /**
     * Get all crashes in the current pool.
     *
     * @return A list of all crashes in the current pool.
     */
    public static List<Crash> getCrashes() {
        return crashes;
    }

    /**
     * Update the current pool of crashes.
     * This method empties the current pool and replaces it with the results of the query.
     *
     * @param query A QueryBuilder containing the query for the database.
     */
    public static void setQuery(QueryBuilder query) {
        crashes.clear();
        crashes = crashDao.queryDatabase(query.getQuery());
    }
}
