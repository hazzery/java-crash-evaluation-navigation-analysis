package seng202.team2.unittests.database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seng202.team2.database.CrashDao;
import seng202.team2.io.CsvReader;
import seng202.team2.models.*;

import java.util.List;


class CrashDaoTest {
    CrashDao crashDao;

    @BeforeEach
    void setup() {
        CsvReader csvReader = new CsvReader("test_data.csv");
        List<Crash> crashes  = csvReader.generateAllCrashes();
        crashDao = new CrashDao();
        crashDao.addBatch(crashes);
    }

    @Test
    void getAll() {
        List<Crash> all = crashDao.getAll();
        assertEquals(1, all.size());
    }

    @Test
    void getOne() {
        Crash crash = crashDao.getOne(1);
        assertEquals(1, crash.crashID());
    }

    @Test
    void queryDatabase() {
        List<Crash> crashes = crashDao.queryDatabase("SELECT * FROM crashes WHERE ID < 11");
        assertEquals(10, crashes.size());
    }

    @Test
    void addBatch() {

//        assertEquals(crash1, crash1FromDatabase);
    }
}