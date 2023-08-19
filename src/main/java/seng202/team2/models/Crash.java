package seng202.team2.models;

import java.util.Arrays;


/**
 * Crash model class.
 * Stores the relevant attributes from the CSV.
 *
 * @author Harrison Parkes
 */
public record Crash(int year, int fatalities, int seriousInjuries, int minorInjuries, double latitude, double longitude,
                    Vehicle[] vehicles, Weather weather, Lighting lighting, Severity severity) {

    /**
     * Creates a human-readable string representation of the crash's attributes
     *
     * @return A string representation of the crash object
     */
    @Override
    public String toString() {
        return "Crash{" +
                "year=" + year +
                ", fatalities=" + fatalities +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", vehicles=" + Arrays.toString(vehicles) +
                ", weather=" + weather +
                ", lighting=" + lighting +
                ", severity=" + severity +
                '}';
    }
}
