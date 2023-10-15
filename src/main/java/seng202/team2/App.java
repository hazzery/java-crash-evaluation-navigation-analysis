package seng202.team2;

import seng202.team2.models.Crashes;

/**
 * Default entry point class
 * @author seng202 teaching team
 */
public class App {
    /**
     * Entry point which runs the javaFX application
     * Also shows off some different logging levels
     * @param args program arguments from command line
     */
    public static void main(String[] args) {
        Crashes.importCrashes();
        MainWindow.main(args);
    }
}
