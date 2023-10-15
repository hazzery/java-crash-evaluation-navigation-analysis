package seng202.team2.unittests.models;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import seng202.team2.models.Region;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Unit test for region enum.
 * Tests converting from a string to an enum.
 *
 * @author Findlay Royds
 * @author Harrison Parkes
 */
public class RegionTest {
    /**
     * A parameterised JUnit test to test generating a region enum from a string found in the crash data CSV file
     *
     * @param regionString A string that may be encountered when importing region enums from the crash data CSV file
     * @param regionEnum The expected resulting region enum
     */
    @ParameterizedTest
    @MethodSource("regionStringToEnum")
    void fromStringTest(String regionString, Region regionEnum) {
        assertEquals(regionEnum, Region.fromString(regionString));
    }

    /**
     * A method source for the parametrised region string to enum test case
     *
     * @return a Stream of Arguments that map different lighting conditions represented as Strings
     * to their corresponding Region enum values.
     */
    private static Stream<Arguments> regionStringToEnum() {
        return Stream.of(
                // Expected case
                arguments("Auckland", Region.AUCKLAND),
                arguments("Northland", Region.NORTHLAND),
                arguments("Waikato", Region.WAIKATO),
                arguments("Bay of plenty", Region.BAY_OF_PLENTY),

                // All caps cases
                arguments("HAWKES BAY", Region.HAWKES_BAY),
                arguments("WELLINGTON", Region.WELLINGTON),
                arguments("OTAGO", Region.OTAGO),
                arguments("MANAWATŪ_WHANGANUI", Region.MANAWATŪ_WHANGANUI),

                // All lower cases
                arguments("taranaki", Region.TARANAKI),
                arguments("canterbury", Region.CANTERBURY),
                arguments("gisborne", Region.GISBORNE),
                arguments("southland", Region.SOUTHLAND),

                // Unknown enums
                arguments("", Region.UNKNOWN),
                arguments("Auck land", Region.UNKNOWN),
                arguments(" ", Region.UNKNOWN),
                arguments("N/A", Region.UNKNOWN),
                arguments("NULL", Region.UNKNOWN),
                arguments("null", Region.UNKNOWN),
                arguments("None", Region.UNKNOWN),
                arguments("Region.AUCKLAND", Region.UNKNOWN)
        );
    }
}
