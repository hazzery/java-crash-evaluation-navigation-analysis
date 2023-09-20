package seng202.team2.unittests.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team2.io.CsvReader;
import seng202.team2.models.Crash;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    CsvReader csvReader;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReader("test_data.csv");
    }

    @Test
    void generateAllCrashes() {
        List<Crash> crashes = csvReader.generateAllCrashes();
        assertEquals(10, crashes.size());
    }
}