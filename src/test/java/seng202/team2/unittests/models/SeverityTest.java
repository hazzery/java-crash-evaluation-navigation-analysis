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
                arguments("Fatal crash", Severity.FATAL_CRASH),
                arguments("Minor crash", Severity.MINOR_CRASH),
                arguments("Non-injury crash", Severity.NON_INJURY_CRASH),
                arguments("Serious crash", Severity.SERIOUS_CRASH),

                // Alternative cases
                arguments("Fatal Crash", Severity.FATAL_CRASH),
                arguments("fatal crash", Severity.FATAL_CRASH),
                arguments("FATAL_CRASH", Severity.FATAL_CRASH),
                arguments("Minor Crash", Severity.MINOR_CRASH),
                arguments("minor crash", Severity.MINOR_CRASH),
                arguments("MINOR_CRASH", Severity.MINOR_CRASH),
                arguments("Non-Injury Crash", Severity.NON_INJURY_CRASH),
                arguments("non-injury crash", Severity.NON_INJURY_CRASH),
                arguments("Non-Injury_Crash", Severity.NON_INJURY_CRASH),
                arguments("NON_INJURY_CRASH", Severity.NON_INJURY_CRASH),
                arguments("Serious Crash", Severity.SERIOUS_CRASH),
                arguments("serious crash", Severity.SERIOUS_CRASH),
                arguments("SERIOUS_CRASH", Severity.SERIOUS_CRASH),

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
