package seng202.team2.models;

import java.util.Arrays;


/**
 * Crash model class.
 * Stores the relevant attributes from the CSV.
 *
 * @author Harrison Parkes
 */
public record Crash(int crashID, int year, int fatalities, int seriousInjuries, int minorInjuries,
                    double latitude, double longitude, String roadName1, String roadName2, String region,
                    Vehicle[] vehicles, Weather weather, Lighting lighting, Severity severity) {

    /**
     * Creates a human-readable string representation of the crash's attributes
     *
     * @return A string representation of the crash object
     */
    @Override
    public String toString() {
        return "Crash{" +
                "crashID=" + crashID +
                ", year=" + year +
                ", fatalities=" + fatalities +
                ", seriousInjuries=" + seriousInjuries +
                ", minorInjuries=" + minorInjuries +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", roadName1='" + roadName1 +
                ", roadName2='" + roadName2 +
                ", region='" + region +
                ", vehicles=" + Arrays.toString(vehicles) +
                ", weather=" + weather +
                ", lighting=" + lighting +
                ", severity=" + severity +
                '}';
    }
}
