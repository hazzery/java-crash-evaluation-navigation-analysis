package seng202.team2.models;

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
}
