package seng202.team2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import seng202.team2.io.CsvReader;
import seng202.team2.gui.MainWindow;

import java.io.FileNotFoundException;

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

        MainWindow.main(args);
    }
}
