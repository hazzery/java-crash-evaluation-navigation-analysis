package seng202.team2.unittests.models;

import org.junit.jupiter.api.Test;
import seng202.team2.models.Severity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Severity enum.
 * Tests converting from a string to an enum.
 *
 * @author Findlay Royds
 */
public class SeverityTest {
    /**
     * Test getting the enum value FATAL_CRASH from string "Fatal crash"
     */
    @Test
    public void fatalCrashFromStringTest() {
        String initialString = "Fatal crash";
        assertEquals(Severity.FATAL_CRASH, Severity.fromString(initialString));
    }

    /**
     * Test getting the enum value MINOR_CRASH from string "Minor Crash"
     */
    @Test
    public void minorCrashFromStringTest() {
        String initialString = "Minor Crash";
        assertEquals(Severity.MINOR_CRASH, Severity.fromString(initialString));
    }

    /**
     * Test getting the enum value NON_INJURY_CRASH from string "Non-injury crash"
     */
    @Test
    public void nonInjuryCrashFromStringTest() {
        String initialString = "Non-injury crash";
        assertEquals(Severity.NON_INJURY_CRASH, Severity.fromString(initialString));
    }

    /**
     * Test getting the enum value SERIOUS_CRASH from string "Serious crash"
     */
    @Test
    public void seriousCrashFromStringTest() {
        String initialString = "Serious crash";
        assertEquals(Severity.SERIOUS_CRASH, Severity.fromString(initialString));
    }

    /**
     * Test getting the enum value UNKNOWN from an empty string
     */
    @Test
    public void unknownFromEmptyStringTest() {
        String initialString = "";
        assertEquals(Severity.UNKNOWN, Severity.fromString(initialString));
    }

    /**
     * Test getting the enum value UNKNOWN from an invalid crash severity
     */
    @Test
    public void unknownFromInvalidStringTest() {
        String initialString = "Pretty bad crash";
        assertEquals(Severity.UNKNOWN, Severity.fromString(initialString));
    }
}
