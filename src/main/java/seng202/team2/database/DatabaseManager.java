package seng202.team2.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team2.exceptions.InstanceAlreadyExistsException;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 * Singleton class responsible for interaction with SQLite database
 * @author Morgan English
 * @author Harrison Parkes
 * @see <a href="https://docs.google.com/document/d/1OzJJYrHxHRYVzx_MKjC2XPGS8_arDKSxYD4NhDN37_E/edit">
 *     SENG202 Advanced Applications with JavaFX</a>
 */
public class DatabaseManager implements AutoCloseable{
    private static DatabaseManager instance = null;
    private static final Logger log = LogManager.getLogger(DatabaseManager.class);
    private final String url;
    Connection connection;

    /**
     * Private constructor for singleton purposes
     * Creates database if it does not already exist in specified location
     */
    private DatabaseManager(String urlIn) {
        if (urlIn==null || urlIn.isEmpty()){
            this.url = getDatabasePath();
        } else {
            this.url = urlIn;
        }

        if(!checkDatabaseExists(url)){
            createDatabaseFile(url);
            resetDB();
        }

        try {
            getConnection();
        } catch (SQLException exception) {
            log.warn("Unable to get connection to database while constructing instance", exception);
        }
    }

    /**
     * Singleton method to get current Instance if exists otherwise create it
     * @return the single instance DatabaseSingleton
     */
    public static DatabaseManager getInstance() {
        if(instance == null)
            // todo find a way to actually get db within jar
            // The following line can be used to reach a db file within the jar, however this will not be modifiable
            instance = new DatabaseManager(null);

        return instance;
    }

    /**
     * WARNING Allows for setting specific database url (currently only needed for test databases, but may be useful
     * in future) USE WITH CAUTION. This does not override the current singleton instance so must be the first call.
     * @param url string url of database to load (this needs to be full url e.g. "jdbc:sqlite:./src/...")
     * @throws InstanceAlreadyExistsException if there is already a singleton instance
     * @return current singleton instance
     */
    public static DatabaseManager initialiseInstanceWithUrl(String url) throws InstanceAlreadyExistsException {
        if(instance == null)
            instance = new DatabaseManager(url);
        else
            throw new InstanceAlreadyExistsException("Database Manager instance already exists, cannot create with url: " + url);

        return instance;
    }

    /**
     *  WARNING Sets the current singleton instance to null
     */
    public static void REMOVE_INSTANCE() {
        instance = null;
    }

    /**
     * Connect to the database
     * @return database connection
     */
    public Connection getConnection() throws SQLException {
        Connection con;
        try {
            con = DriverManager.getConnection(this.url);
        } catch (SQLException exception) {
            log.error(exception);
            throw exception;
        }

        return con;
    }

    /**
     * Initialises the database if it does not exist using the sql script included in resources
     */
    public void resetDB() {
        try {
            InputStream in = getClass().getResourceAsStream("/sql/initialise_database.sql");
            executeSQLScript(in);
        } catch (NullPointerException e) {
            log.error("Error loading database initialisation file", e);
        }
    }

    /**
     * Gets path to the database relative to the jar file
     * @return jdbc encoded url location of database
     */
    private String getDatabasePath() {
        String path = DatabaseManager.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = URLDecoder.decode(path, StandardCharsets.UTF_8);
        log.info(path);
        File jarDir = new File(path);
        return "jdbc:sqlite:"+jarDir.getParentFile()+"/database.db";
    }

    /**
     * Check that a database exists in the expected location
     * @param url expected location to check for database
     * @return True if database exists else false
     */
    private boolean checkDatabaseExists(String url){
        File f = new File(url.substring(12));
        return f.exists();
    }

    /**
     * Creates a database file at the location specified by the url
     * @param url url to creat database at
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
     * Note that each statement must be separated by '--SPLIT' this is not a desired limitation but allows for a much
     * wider range of statement types.
     * @param sqlFile input stream of file containing sql statements for execution (separated by --SPLIT)
     */
    private void executeSQLScript(InputStream sqlFile) {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sqlFile))) {
            while((string = bufferedReader.readLine()) != null) {
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

    /**
     * Closes the database connection
     * @throws SQLException if connection cannot be closed
     */
    @Override
    public void close() throws SQLException {
        this.connection.close();
    }
}
