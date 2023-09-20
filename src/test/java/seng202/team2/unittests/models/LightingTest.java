package seng202.team2.unittests.models;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import seng202.team2.models.Lighting;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Unit test for Severity enum.
 * Tests converting from a string to an enum.
 *
 * @author Findlay Royds
 */
public class LightingTest {
    /**
     * A parameterised JUnit test to test generating a lighting enum from a string found in the crash data CSV file
     *
     * @param lightingString A string that may be encountered when importing lighting enums from the crash data CSV file
     * @param lightingEnum The expected resulting lighting enum
     */
    @ParameterizedTest
    @MethodSource("lightingStringToEnum")
    void fromStringTest(String lightingString, Lighting lightingEnum) {
        assertEquals(lightingEnum, Lighting.fromString(lightingString));
    }

    /**
     * A method source for the parametrised lighting string to enum test case
     *
     * @return a Stream of Arguments that map different lighting conditions represented as Strings
     * to their corresponding Lighting enum values.
     */
    private static Stream<Arguments> lightingStringToEnum() {
        return Stream.of(
                // Expected case
                arguments("Overcast", Lighting.OVERCAST),
                arguments("Bright sun", Lighting.BRIGHT_SUN),
                arguments("Twilight", Lighting.TWILIGHT),
                arguments("Dark", Lighting.DARK),

                // Alternative cases
                arguments("OverCast", Lighting.OVERCAST),
                arguments("overcast", Lighting.OVERCAST),
                arguments("Bright Sun", Lighting.BRIGHT_SUN),
                arguments("bright sun", Lighting.BRIGHT_SUN),
                arguments("Bright_sun", Lighting.BRIGHT_SUN),
                arguments("BRIGHT_SUN", Lighting.BRIGHT_SUN),
                arguments("TwiLight", Lighting.TWILIGHT),
                arguments("twilight", Lighting.TWILIGHT),
                arguments("TWILIGHT", Lighting.TWILIGHT),
                arguments("dark", Lighting.DARK),
                arguments("DARK", Lighting.DARK),

                // Unknown enums
                arguments("", Lighting.UNKNOWN),
                arguments("rather dark", Lighting.UNKNOWN),
                arguments(" ", Lighting.UNKNOWN),
                arguments("N/A", Lighting.UNKNOWN),
                arguments("NULL", Lighting.UNKNOWN),
                arguments("null", Lighting.UNKNOWN),
                arguments("None", Lighting.UNKNOWN),
                arguments("Lighting.OVERCAST", Lighting.UNKNOWN)
        );
    }
}
