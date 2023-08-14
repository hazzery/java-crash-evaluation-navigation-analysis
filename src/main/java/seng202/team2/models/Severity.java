package seng202.team2.models;


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
            System.out.println("Invalid severity: " + name);
            return Severity.UNKNOWN;
        }
    }
}
