package seng202.team2.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team2.models.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for Crash class.
 * Tests the toString method.
 *
 * @author Findlay Royds
 */
public class CrashTest {
    private Crash testCrash;

    /**
     * Setup before each test, we create an instance of a crash object
     */
    @BeforeEach
    public void setupTest() {
        testCrash = new Crash(2012, 1, -36.8668232122794, 170.45418726101,
                new Vehicle[]{Vehicle.SCHOOL_BUS, Vehicle.MOPED}, Weather.SNOW, Lighting.BRIGHT_SUN,
                Severity.MINOR_CRASH);
    }

    /**
     * Test crash toString method
     */
    @Test
    public void stringRepresentationTest() {
        String expectedResult = "Crash{year=2012, fatalities=1, latitude=-36.8668232122794, longitude=170.45418726101" +
                ", vehicles=[SCHOOL_BUS, MOPED], weather=SNOW, lighting=BRIGHT_SUN, severity=MINOR_CRASH}";
        assertEquals(expectedResult, testCrash.toString());
    }

    /**
     * Test crash get year method
     */
    @Test
    public void testGetYear() {
        assertEquals(2012, testCrash.getYear());
    }

    /**
     * Test crash get fatalities method
     */
    @Test
    public void testGetFatalities() {
        assertEquals(1, testCrash.getFatalities());
    }

    /**
     * Test crash get latitude method
     */
    @Test
    public void testGetLatitude() {
        assertEquals(-36.8668232122794, testCrash.getLatitude());
    }

    /**
     * Test crash get longitude method
     */
    @Test
    public void testGetLongitude() {
        assertEquals(170.45418726101, testCrash.getLongitude());
    }

    /**
     * Test crash get vehicles method
     */
    @Test
    public void testGetVehicles() {
        assertArrayEquals(new Vehicle[]{Vehicle.SCHOOL_BUS, Vehicle.MOPED}, testCrash.getVehicles());
    }

    /**
     * Test crash get weather method
     */
    @Test
    public void testGetWeather() {
        assertEquals(Weather.SNOW, testCrash.getWeather());
    }

    /**
     * Test crash get lighting method
     */
    @Test
    public void testGetLighting() {
        assertEquals(Lighting.BRIGHT_SUN, testCrash.getLighting());
    }

    /**
     * Test crash get severity method
     */
    @Test
    public void testGetSeverity() {
        assertEquals(Severity.MINOR_CRASH, testCrash.getSeverity());
    }
}
