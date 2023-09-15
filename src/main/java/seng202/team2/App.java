package seng202.team2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seng202.team2.database.DbAttributes;
import seng202.team2.database.QueryBuilder;
import seng202.team2.database.CrashDao;
import seng202.team2.io.CsvReader;
import seng202.team2.models.Crash;

import java.util.List;

/**
 * Default entry point class
 * @author seng202 teaching team
 */
public class App {
    private static final Logger log = LogManager.getLogger(App.class);

    /**
     * Entry point which runs the javaFX application
     * Also shows off some different logging levels
     * @param args program arguments from command line
     */
    public static void main(String[] args) {
        CsvReader csvReader = new CsvReader("src/main/resources/crash_data.csv");
        csvReader.importAllToDatabase();

        QueryBuilder queryBuilder = new QueryBuilder();

        // Just playing around here, feel free to remove/change these filters.
        queryBuilder.betweenValues(2000, 2023, DbAttributes.YEAR);

        CrashDao crashDao = new CrashDao();

        // This gives null!
//        Crash result = crashDao.getOne(1);
//        log.info(result);

        // result is empty so nothing is logged
        List<Crash> result = crashDao.queryDatabase(queryBuilder.getQuery());
        for (Crash crash : result) {
            log.info(crash);
        }
    }
}
