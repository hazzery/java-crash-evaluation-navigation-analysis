package seng202.team2.unittests.models;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import seng202.team2.models.Severity;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Unit test for Severity enum.
 * Tests converting from a string to an enum.
 *
 * @author Findlay Royds
 */
public class SeverityTest {
    /**
     * A parameterised JUnit test to test generating a severity enum from a string found in the crash data CSV file
     *
     * @param severityString A string that may be encountered when importing severity enums from the crash data CSV file
     * @param severityEnum The expected resulting severity enum
     */
    @ParameterizedTest
    @MethodSource("severityStringToEnum")
    void fromStringTest(String severityString, Severity severityEnum) {
        assertEquals(severityEnum, Severity.fromString(severityString));
    }

    /**
     * A method source for the parametrised severity string to enum test case
     *
     * @return a Stream of Arguments that map different severities represented as Strings
     * to their corresponding Severity enum values.
     */
    private static Stream<Arguments> severityStringToEnum() {
        return Stream.of(
                // Expected case
                arguments("Fatal crash", Severity.FATAL),
                arguments("Minor crash", Severity.MINOR),
                arguments("Non-injury crash", Severity.NON_INJURY),
                arguments("Serious crash", Severity.SERIOUS),

                // Alternative cases
                arguments("Fatal Crash", Severity.FATAL),
                arguments("fatal crash", Severity.FATAL),
                arguments("FATAL_CRASH", Severity.FATAL),
                arguments("Minor Crash", Severity.MINOR),
                arguments("minor crash", Severity.MINOR),
                arguments("MINOR_CRASH", Severity.MINOR),
                arguments("Non-Injury Crash", Severity.NON_INJURY),
                arguments("non-injury crash", Severity.NON_INJURY),
                arguments("Non-Injury_Crash", Severity.NON_INJURY),
                arguments("NON_INJURY_CRASH", Severity.NON_INJURY),
                arguments("Serious Crash", Severity.SERIOUS),
                arguments("serious crash", Severity.SERIOUS),
                arguments("SERIOUS_CRASH", Severity.SERIOUS),

                // Unknown enums
                arguments("", Severity.UNKNOWN),
                arguments("not too dangerous", Severity.UNKNOWN),
                arguments(" ", Severity.UNKNOWN),
                arguments("N/A", Severity.UNKNOWN),
                arguments("NULL", Severity.UNKNOWN),
                arguments("null", Severity.UNKNOWN),
                arguments("None", Severity.UNKNOWN)
        );
    }
}
