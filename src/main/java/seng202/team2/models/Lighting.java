package seng202.team2.models;


/**
 * enum listing the possible lighting conditions at the time of a crash
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
     * Convert a string to a lighting enum
     * @param name The string to convert
     * @return The lighting enum value
     */
    public static Lighting fromString(String name) {
        name = name.toUpperCase().replace(" ", "_");
        try {
            return Lighting.valueOf(name);
        } catch (IllegalArgumentException e) {
            return Lighting.UNKNOWN;
        }
    }
}
