package seng202.team2.unittests.models;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import seng202.team2.models.Weather;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Unit test for Severity enum.
 * Tests converting from a string to an enum.
 *
 * @author Findlay Royds
 */
public class WeatherTest {
    /**
     * A parameterised JUnit test to test generating a lighting enum from a string found in the crash data CSV file
     *
     * @param weatherString A string that may be encountered when importing lighting enums from the crash data CSV file
     * @param weatherEnum The expected resulting lighting enum
     */
    @ParameterizedTest
    @MethodSource("weatherStringToEnum")
    void fromStringTest(String weatherString, Weather weatherEnum) {
        assertEquals(weatherEnum, Weather.fromString(weatherString));
    }

    /**
     * A method source for the parametrised lighting string to enum test case
     *
     * @return a Stream of Arguments that map different lighting conditions represented as Strings
     * to their corresponding Lighting enum values.
     */
    private static Stream<Arguments> weatherStringToEnum() {
        return Stream.of(
                // Expected case
                arguments("Fine", Weather.FINE),
                arguments("Snow", Weather.SNOW),
                arguments("Hail or sleet", Weather.HAIL_OR_SLEET),
                arguments("Heavy rain", Weather.HEAVY_RAIN),
                arguments("Light rain", Weather.LIGHT_RAIN),
                arguments("Mist or fog", Weather.MIST_OR_FOG),

                // Alternative cases
                arguments("fine", Weather.FINE),
                arguments("FINE", Weather.FINE),
                arguments("snow", Weather.SNOW),
                arguments("SNOW", Weather.SNOW),
                arguments("hail or sleet", Weather.HAIL_OR_SLEET),
                arguments("HAIL_OR_SLEET", Weather.HAIL_OR_SLEET),
                arguments("heavy rain", Weather.HEAVY_RAIN),
                arguments("HEAVY_RAIN", Weather.HEAVY_RAIN),
                arguments("light rain", Weather.LIGHT_RAIN),
                arguments("LIGHT_RAIN", Weather.LIGHT_RAIN),
                arguments("mist or fog", Weather.MIST_OR_FOG),
                arguments("MIST_OR_FOG", Weather.MIST_OR_FOG),

                // Unknown enums
                arguments("", Weather.UNKNOWN),
                arguments("Hurricane", Weather.UNKNOWN),
                arguments(" ", Weather.UNKNOWN),
                arguments("N/A", Weather.UNKNOWN),
                arguments("NULL", Weather.UNKNOWN),
                arguments("null", Weather.UNKNOWN),
                arguments("None", Weather.UNKNOWN)
        );
    }
}
