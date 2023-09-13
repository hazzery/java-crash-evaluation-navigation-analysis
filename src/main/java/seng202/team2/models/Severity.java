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

    /**
     * Converts an enum value to a formatted string:
     * i.e: FATAL_CRASH to "Fatal crash"
     * @param severity the enum value to be displayed nicely
     * @return nicely formatted string of enum value
     */
    public static String toDisplayValue(Severity severity) {
        String display = severity.name().replaceAll("_", " ");
        return display.substring(0,1).toUpperCase() + display.substring(1, display.length()).toLowerCase();
    }
}
