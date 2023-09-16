package seng202.team2.models;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * enum listing the possible crash severities
 *
 * @author Harrison Parkes
 */
public enum Severity {
    NON_INJURY_CRASH,
    MINOR_CRASH,
    SERIOUS_CRASH,
    FATAL_CRASH,
    UNKNOWN;

    private static final Logger log = LogManager.getLogger(Severity.class);


    /**
     * Converts a string to a severity enum value
     * @param name The string to convert
     * @return The severity enum value
     */
    public static Severity fromString(String name) {
        name = name.toUpperCase().replace(" ", "_").replace("-", "_");
        try {
            return Severity.valueOf(name);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid severity: " + name);
            return Severity.UNKNOWN;
        }
    }

    /**
     * Converts an int value to a formatted string:
     * i.e: 1 to "Fatal crash"
     * @param value the int location of an enum in values to be displayed nicely
     * @return nicely formatted string of enum value
     */
    public static String toDisplayValue(int value) {
        String display = values()[value].name().replaceAll("_", " ");
        return display.substring(0,1).toUpperCase() + display.substring(1, display.length()).toLowerCase();
    }
}
