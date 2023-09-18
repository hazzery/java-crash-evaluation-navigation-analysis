package seng202.team2.models;

import seng202.team2.database.CrashDao;
import seng202.team2.database.QueryBuilder;
import seng202.team2.io.CsvReader;

import java.util.List;

public class Crashes {
    private static List<Crash> crashes;
    private static final CrashDao crashDao = new CrashDao();

    public static void importCrashes() {
        CsvReader csvReader = new CsvReader("crash_data.csv");
        crashes = csvReader.generateAllCrashes();
        crashDao.addBatch(crashes);
    }

    public static List<Crash> getCrashes() {
        return crashes;
    }

    public static void setQuery(QueryBuilder query) {
        crashes.clear();
        crashes = crashDao.queryDatabase(query.getQuery());
    }
}
