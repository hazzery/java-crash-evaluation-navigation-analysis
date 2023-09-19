package seng202.team2.unittests.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import seng202.team2.database.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Tests for database creation, from JavaFX lab
 *
 * @author Morgan English
 */
class DatabaseTest {

    @Test
    void testDatabaseManagerInstance() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Assertions.assertNotNull(databaseManager);
        Assertions.assertEquals(databaseManager, DatabaseManager.getInstance());
    }

    @Test
    void testDatabaseConnection() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        Connection connection = databaseManager.getConnection();
        Assertions.assertNotNull(connection);
        Assertions.assertNotNull(connection.getMetaData());
        connection.close();
    }
}
