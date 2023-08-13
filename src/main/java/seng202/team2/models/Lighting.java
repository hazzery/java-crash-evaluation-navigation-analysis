package seng202.team2.models;

public enum Lighting {
    BRIGHT_SUN,
    OVERCAST,
    TWILIGHT,
    DARK,
    UNKNOWN;

    public static Lighting fromString(String name) {
        name = name.toUpperCase().replace(" ", "_");
        try {
            return Lighting.valueOf(name);
        } catch (IllegalArgumentException e) {
            return Lighting.UNKNOWN;
        }
    }
}
