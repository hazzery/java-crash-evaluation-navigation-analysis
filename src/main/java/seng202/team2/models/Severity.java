package seng202.team2.models;


import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * enum listing the possible crash severities
 *
 * @author Harrison Parkes
 */
public enum Severity {
    NON_INJURY,
    MINOR,
    SERIOUS,
    FATAL,
    UNKNOWN;

    private static final Logger log = LogManager.getLogger(Severity.class);


    /**
     * Converts a string to a severity enum value
     * @param name The string to convert
     * @return The severity enum value
     */
    public static Severity fromString(String name) {
        name = name.toUpperCase().replace(" ", "_").replace("-", "_");
        name = name.replace("_CRASH", "");
        try {
            return Severity.valueOf(name);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid severity: " + name);
            return Severity.UNKNOWN;
        }
    }

    /**
     * Gets a displayable string representation of the enum value
     * e.g: FATAL_CRASH -> "Fatal crash"
     * @return Nicely formatted string of enum value
     */
    public String displayValue() {
        String display = name().toLowerCase().replaceAll("_", " ");
        return StringUtils.capitalize(display);
    }
}
