package seng202.team2.unittests.models;

import org.junit.jupiter.api.Test;
import seng202.team2.models.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Crash class.
 * Tests the toString method.
 *
 * @author Findlay Royds
 */
public class CrashTest {
    /**
     * Test crash toString method
     */
    @Test
    public void stringRepresentationTest() {
        Crash testCrash = new Crash(2012, 1, -36.8668232122794, 170.45418726101,
                new Vehicle[]{Vehicle.SCHOOL_BUS, Vehicle.MOPED}, Weather.SNOW, Lighting.BRIGHT_SUN,
                Severity.MINOR_CRASH);
        String expectedResult = "Crash{year=2012, fatalities=1, latitude=-36.8668232122794, longitude=170.45418726101" +
                ", vehicles=[SCHOOL_BUS, MOPED], weather=SNOW, lighting=BRIGHT_SUN, severity=MINOR_CRASH}";
        assertEquals(expectedResult, testCrash.toString());
    }
}
