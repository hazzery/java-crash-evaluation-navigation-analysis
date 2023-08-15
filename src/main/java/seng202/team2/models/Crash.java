package seng202.team2.models;

import java.util.Arrays;


/**
 * Crash model class.
 * Stores the relevant attributes from the CSV.
 *
 * @author Harrison Parkes
 */
public record Crash(int year, int fatalities, double latitude, double longitude, Vehicle[] vehicles, Weather weather,
                    Lighting lighting, Severity severity) {
    /**
     * Construct a new crash object
     *
     * @param year       The year the crash occurred
     * @param fatalities The number of fatalities in the crash
     * @param latitude   The latitude ordinate of the crash location
     * @param longitude  The longitude ordinate of the crash location
     * @param vehicles   The vehicles involved in the crash
     * @param weather    The weather conditions at the time of the crash
     * @param lighting   The lighting conditions at the time of the crash
     * @param severity   The severity of the crash
     */
    public Crash {
    }

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
