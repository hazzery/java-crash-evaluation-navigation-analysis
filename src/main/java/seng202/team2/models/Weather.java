package seng202.team2.models;


import org.apache.commons.lang3.StringUtils;

/**
 * Enum listing all possible weather conditions.
 *
 * @author Harrison Parkes
 */
public enum Weather {
    FINE,
    HAIL_OR_SLEET,
    HEAVY_RAIN,
    LIGHT_RAIN,
    MIST_OR_FOG,
    SNOW,
    UNKNOWN;

    /**
     * Converts a string to a Weather enum value.
     *
     * @param name The string to convert.
     * @return The Weather enum value.
     */
    public static Weather fromString(String name) {
        name = name.toUpperCase().replace(" ", "_");
        try {
            return Weather.valueOf(name);
        } catch (IllegalArgumentException e) {
            return Weather.UNKNOWN;
        }
    }

    /**
     * Gets a displayable string representation of the enum value
     * e.g. HEAVY_RAIN -> "Heavy Rain".
     *
     * @return Nicely formatted string of enum value.
     */
    public String displayValue() {
        String display = name().toLowerCase().replaceAll("_", " ");
        return StringUtils.capitalize(display);
    }
}
