package seng202.team2.unittests.database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.team2.database.CrashDao;
import seng202.team2.database.DatabaseManager;
import seng202.team2.exceptions.InstanceAlreadyExistsException;
import seng202.team2.io.CsvReader;
import seng202.team2.models.*;

import java.util.List;


class CrashDaoTest {
    CrashDao crashDao;

    @BeforeEach
    void setup() throws InstanceAlreadyExistsException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.resetDB();

        CsvReader csvReader = new CsvReader("test_data.csv");
        List<Crash> crashes  = csvReader.generateAllCrashes();
        crashDao = new CrashDao();
        crashDao.addBatch(crashes);
    }

    @Test
    void queryDatabase() {
        List<Crash> crashes = crashDao.queryDatabase("SELECT * FROM crashes WHERE ID < 11");
        assertEquals(10, crashes.size());
    }
}