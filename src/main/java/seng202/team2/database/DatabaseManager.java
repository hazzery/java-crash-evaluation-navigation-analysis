package seng202.team2.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 * Singleton class responsible for interaction with SQLite database
 *
 * @author Morgan English
 * @author Harrison Parkes
 * @see <a href="https://docs.google.com/document/d/1OzJJYrHxHRYVzx_MKjC2XPGS8_arDKSxYD4NhDN37_E/edit">
 * SENG202 Advanced Applications with JavaFX</a>
 */
public class DatabaseManager {
    private static DatabaseManager instance = null;
    private static final Logger log = LogManager.getLogger(DatabaseManager.class);
    private final String url;

    /**
     * The constructor is private as this is a singleton class.
     * Use {@link #getInstance()} to get the current instance.
     * This creates a new database if one does not already exist in the specified location.
     *
     * @param urlIn url of the database to load (this needs to be full url e.g. "jdbc:sqlite:...") or null.
     */
    private DatabaseManager(String urlIn) {
        if (urlIn == null || urlIn.isEmpty()) {
            this.url = getDatabasePath();
        } else {
            this.url = urlIn;
        }

        if (!checkDatabaseExists(url)) {
            createDatabaseFile(url);
            resetDB();
        }
    }

    /**
     * Singleton method to get current instance if one exists, otherwise create a new one.
     *
     * @return The single instance of DatabaseManager.
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager(null);
        }

        return instance;
    }

    /**
     * Get a connection to the database.
     *
     * @return A database connection.
     * @throws SQLException if a connection cannot be made
     */
    public Connection getConnection() throws SQLException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(this.url);
        } catch (SQLException exception) {
            log.error(exception);
            throw exception;
        }

        return connection;
    }

    /**
     * Initialises the database if it does not exist using the sql script included in resources.
     */
    public void resetDB() {
        try {
            InputStream initialise = getClass().getResourceAsStream("/sql/initialise_database.sql");
            executeSQLScript(initialise);
        } catch (NullPointerException e) {
            log.error("Error loading database initialisation file", e);
        }
    }

    /**
     * Gets the path to the database relative to the jar file.
     *
     * @return JDBC encoded url location of the database.
     */
    private String getDatabasePath() {
        String path = DatabaseManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = URLDecoder.decode(path, StandardCharsets.UTF_8);
        log.info(path);
        File jarDir = new File(path);
        return "jdbc:sqlite:" + jarDir.getParentFile() + "/database.db";
    }

    /**
     * Check that a database exists in the specified location.
     *
     * @param url Location to check for a database.
     * @return True if a database exists at the location, otherwise false.
     */
    private boolean checkDatabaseExists(String url) {
        File f = new File(url.substring(12));
        return f.exists();
    }

    /**
     * Creates a database file at the location specified by the url.
     *
     * @param url Location to create the database at.
     */
    private void createDatabaseFile(String url) {
        try {
            DatabaseMetaData meta = getConnection().getMetaData();
            String metaDriverLog = String.format("A new database has been created. The driver name is %s", meta.getDriverName());
            log.info(metaDriverLog);
        } catch (SQLException exception) {
            log.error(String.format("Error creating new database file url:%s", url));
            log.error(exception);
        }
    }

    /**
     * Reads and executes all statements within the sql file provided
     * NOTE: Each statement must be separated by '--SPLIT' this is not a desired limitation but allows for a much
     * wider range of statement types.
     *
     * @param sqlFile input stream of file containing SQL statements for execution (separated by --SPLIT)
     */
    private void executeSQLScript(InputStream sqlFile) {
        String string;
        StringBuilder stringBuffer = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sqlFile))) {
            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string);
            }

            String[] individualStatements = stringBuffer.toString().split("--SPLIT");

            try (Statement statement = getConnection().createStatement()) {
                for (String singleStatement : individualStatements) {
                    statement.executeUpdate(singleStatement);
                }
            }
        } catch (FileNotFoundException exception) {
            log.error("Error could not find specified database initialisation file", exception);
        } catch (IOException exception) {
            log.error("Error working with database initialisation file", exception);
        } catch (SQLException exception) {
            log.error("Error executing sql statements in database initialisation file", exception);
        }
    }
}
