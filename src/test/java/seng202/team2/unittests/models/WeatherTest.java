package seng202.team2.unittests.models;

import org.junit.jupiter.api.Test;
import seng202.team2.models.Weather;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Weather enum.
 * Tests converting from a string to an enum.
 *
 * @author Findlay Royds
 */
public class WeatherTest {
    /**
     * Test getting the enum value FINE from string "Fine"
     */
    @Test
    public void fineFromStringTest() {
        String initialString = "Fine";
        assertEquals(Weather.FINE, Weather.fromString(initialString));
    }

    /**
     * Test getting the enum value SNOW from string "Snow"
     */
    @Test
    public void snowFromStringTest() {
        String initialString = "Snow";
        assertEquals(Weather.SNOW, Weather.fromString(initialString));
    }

    /**
     * Test getting the enum value HAIL_OR_SLEET from string "Hail or Sleet"
     */
    @Test
    public void hailOrSleetFromStringTest() {
        String initialString = "Hail or Sleet";
        assertEquals(Weather.HAIL_OR_SLEET, Weather.fromString(initialString));
    }

    /**
     * Test getting the enum value HEAVY_RAIN from string "Heavy rain"
     */
    @Test
    public void heavyRainFromStringTest() {
        String initialString = "Heavy rain";
        assertEquals(Weather.HEAVY_RAIN, Weather.fromString(initialString));
    }

    /**
     * Test getting the enum value LIGHT_RAIN from string "Light rain"
     */
    @Test
    public void lightRainFromStringTest() {
        String initialString = "Light rain";
        assertEquals(Weather.LIGHT_RAIN, Weather.fromString(initialString));
    }

    /**
     * Test getting the enum value MIST_OR_FOG from string "Mist or Fog"
     */
    @Test
    public void mistOrFogFromStringTest() {
        String initialString = "Mist or Fog";
        assertEquals(Weather.MIST_OR_FOG, Weather.fromString(initialString));
    }

    /**
     * Test getting the enum value UNKNOWN from an empty string
     */
    @Test
    public void unknownFromEmptyStringTest() {
        String initialString = "";
        assertEquals(Weather.UNKNOWN, Weather.fromString(initialString));
    }

    /**
     * Test getting the enum value UNKNOWN from an invalid crash severity
     */
    @Test
    public void unknownFromInvalidStringTest() {
        String initialString = "Pretty bad crash";
        assertEquals(Weather.UNKNOWN, Weather.fromString(initialString));
    }
}
