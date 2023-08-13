package seng202.team2.models;

public enum Severity {
    NON_INJURY_CRASH,
    MINOR_CRASH,
    SERIOUS_CRASH,
    FATAL_CRASH,
    UNKNOWN;

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
