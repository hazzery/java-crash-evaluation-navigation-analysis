package seng202.team2.models;

public enum Weather {
    FINE,
    HAIL_OR_SLEET,
    HEAVY_RAIN,
    LIGHT_RAIN,
    MIST_OR_FOG,
    SNOW,
    UNKNOWN;

    public static Weather fromString(String name) {
        name = name.toUpperCase().replace(" ", "_");
        try {
            return Weather.valueOf(name);
        } catch (IllegalArgumentException e) {
            return Weather.UNKNOWN;
        }
    }
}
