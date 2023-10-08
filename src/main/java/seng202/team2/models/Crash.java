package seng202.team2.models;

import java.util.Map;


/**
 * Crash model class.
 * Stores the relevant attributes from the CSV.
 *
 * @author Harrison Parkes
 */
public record Crash(int crashID, int year, int fatalities, int seriousInjuries, int minorInjuries,
                    double latitude, double longitude, String roadName1, String roadName2, Region region,
                    Weather weather, Lighting lighting, Severity severity, Map<Vehicle, Integer> vehicles) {

    /**
     * Creates a human-readable string representation of the crash's attributes.
     *
     * @return A string representation of the crash object.
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
                        ", vehicles=" + vehicles +
                        ", weather=" + weather +
                        ", lighting=" + lighting +
                        ", severity=" + severity +
                        '}';
    }

    /**
     * Calculates the total number of vehicles involved in the crash.
     *
     * @return The total number of vehicles involved in the crash.
     */
    public int vehiclesInvolved() {
        int total = 0;
        for (int value : vehicles.values()) {
            total += value;
        }
        return total;
    }
}
