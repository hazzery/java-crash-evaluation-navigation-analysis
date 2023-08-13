package seng202.team2.models;

import java.util.Arrays;

public class Crash {
    private final int year;
    private final int fatalities;
    private final double latitude;
    private final double longitude;
    private final Vehicle[] vehicles;
    private final Weather weather;
    private final Lighting lighting;
    private final Severity severity;

    public Crash(int year, int fatalities, double latitude, double longitude, Vehicle[] vehicles,
                 Weather weather, Lighting lighting, Severity severity) {
        this.year = year;
        this.fatalities = fatalities;
        this.latitude = latitude;
        this.longitude = longitude;
        this.vehicles = vehicles;
        this.weather = weather;
        this.lighting = lighting;
        this.severity = severity;
    }

    public int getYear() {
        return year;
    }

    public int getFatalities() {
        return fatalities;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }

    public Weather getWeather() {
        return weather;
    }

    public Lighting getLighting() {
        return lighting;
    }

    public Severity getSeverity() {
        return severity;
    }

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
