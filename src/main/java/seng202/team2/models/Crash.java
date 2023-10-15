package seng202.team2.models;

import java.util.Map;


/**
 * Crash model class.
 * Stores the relevant attributes from the CSV.
 *
 * @author Harrison Parkes
 */
public record Crash(int id, int year, int fatalities, int seriousInjuries, int minorInjuries, double latitude,
                    double longitude, String roadName1, String roadName2, Region region, Weather weather,
                    Lighting lighting, Severity severity, Map<Vehicle, Integer> vehicles) {

    public int getYear() {
        return year;
    }

    public String getRegion() {
        return region.displayValue();
    }

    public String getSeverity() {
        return severity.displayValue();
    }

    /**
     * Creates a human-readable string representation of the crash's attributes.
     *
     * @return A string representation of the crash object.
     */
    @Override
    public String toString() {
        return "Crash{" +
                        "crashID=" + id +
                        ", year=" + year +
                        ", fatalities=" + fatalities +
                        ", seriousInjuries=" + seriousInjuries +
                        ", minorInjuries=" + minorInjuries +
                        ", latitude=" + latitude +
                        ", longitude=" + longitude +
                        ", roadName1='" + roadName1 +
                        ", roadName2='" + roadName2 +
                        ", region='" + region +
                        ", weather=" + weather +
                        ", lighting=" + lighting +
                        ", severity=" + severity +
                        ", vehicles=" + vehicles +
                        '}';
    }
}
