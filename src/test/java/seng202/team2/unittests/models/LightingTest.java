package seng202.team2.unittests.models;

import org.junit.jupiter.api.Test;
import seng202.team2.models.Lighting;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Lighting enum.
 * Tests converting from a string to an enum.
 *
 * @author Findlay Royds
 */
public class LightingTest {
    /**
     * Test getting the enum value OVERCAST from string "Overcast"
     */
    @Test
    public void overcastFromStringTest() {
        String initialString = "Overcast";
        assertEquals(Lighting.OVERCAST, Lighting.fromString(initialString));
    }

    /**
     * Test getting the enum value BRIGHT_SUN from string "Bright sun"
     */
    @Test
    public void brightSunFromStringTest() {
        String initialString = "Bright sun";
        assertEquals(Lighting.BRIGHT_SUN, Lighting.fromString(initialString));
    }

    /**
     * Test getting the enum value TWILIGHT from string "Twilight"
     */
    @Test
    public void twilightFromStringTest() {
        String initialString = "Twilight";
        assertEquals(Lighting.TWILIGHT, Lighting.fromString(initialString));
    }

    /**
     * Test getting the enum value DARK from string "Dark"
     */
    @Test
    public void darkFromStringTest() {
        String initialString = "Dark";
        assertEquals(Lighting.DARK, Lighting.fromString(initialString));
    }

    /**
     * Test getting the enum value UNKNOWN from an empty string
     */
    @Test
    public void unknownFromEmptyStringTest() {
        String initialString = "";
        assertEquals(Lighting.UNKNOWN, Lighting.fromString(initialString));
    }

    /**
     * Test getting the enum value UNKNOWN from an invalid lighting condition
     */
    @Test
    public void unknownFromInvalidStringTest() {
        String initialString = "Rather dark eh";
        assertEquals(Lighting.UNKNOWN, Lighting.fromString(initialString));
    }
}
