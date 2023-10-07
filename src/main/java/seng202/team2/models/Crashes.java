package seng202.team2.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng202.team2.database.CrashDao;
import seng202.team2.database.QueryBuilder;
import seng202.team2.io.CsvReader;

public class Crashes {
    private static ObservableList<Crash> crashes;
    private static final CrashDao crashDao = new CrashDao();

    public static void importCrashes() {
        CsvReader csvReader = new CsvReader("crash_data.csv");
        crashes = FXCollections.observableList(csvReader.generateAllCrashes());
        crashDao.addBatch(crashes);
    }

    public static ObservableList<Crash> getCrashes() {
        return crashes;
    }

    public static void setQuery(QueryBuilder query) {
        crashes.clear();
        crashes.addAll(crashDao.queryDatabase(query.getQuery()));
    }
}
