package seng202.team2.models;

import org.apache.commons.lang3.StringUtils;

/**
 * Enum listing the possible lighting conditions at the time of a crash.
 *
 * @author Harrison Parkes
 */
public enum Lighting {
    BRIGHT_SUN,
    OVERCAST,
    TWILIGHT,
    DARK,
    UNKNOWN;

    /**
     * Convert a string to a lighting enum.
     *
     * @param name The string to convert.
     * @return The lighting enum value.
     */
    public static Lighting fromString(String name) {
        name = name.toUpperCase().replace(" ", "_");
        try {
            return Lighting.valueOf(name);
        } catch (IllegalArgumentException e) {
            return Lighting.UNKNOWN;
        }
    }

    /**
     * Gets a displayable string representation of the enum value
     * e.g. BRIGHT_SUN -> "Bright Sun".
     *
     * @return Nicely formatted string of enum value.
     */
    public String displayValue() {
        String display = name().toLowerCase().replaceAll("_", " ");
        return StringUtils.capitalize(display);
    }
}
