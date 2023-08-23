package seng202.team2.services;

import seng202.team2.models.*;

import java.util.*;
import java.io.*;


/**
 * CSVReader class provides the ability to read in the CSV file and generate {@link Crash} objects for each row.
 *
 * @author Harrison Parkes
 */
public class CsvReader {
    FileReader csvData;

    /**
     * Creates a new CSVReader object for the given file.
     * @param fileName The name of the file to read
     * @throws FileNotFoundException If the given file does not exist
     */
    public CsvReader(String fileName) throws FileNotFoundException {
        csvData = new FileReader(fileName);
    }

    /**
     * Creates an array of vehicles from a list containing a crash's whole row from the CSV.
     * @param crashData A list containing the crash's whole row from the CSV.
     * @return An array of {@link Vehicle} objects for the given crash data
     */
    private Vehicle[] vehiclesFromCSVData(String[] crashData) {
        List<Vehicle> vehicles = new ArrayList<>();

        for (Vehicle vehicle : Vehicle.values()) {
            int vehicleCount;
            try {
                vehicleCount = Integer.parseInt(crashData[vehicle.getCsvColumn()]);
            } catch (NumberFormatException e) {
                continue;
            }

            for (int i = 0; i < vehicleCount; i++) {
                vehicles.add(vehicle);
            }
        }
        System.out.println();
        return vehicles.toArray(new Vehicle[0]);
    }

    /**
     * Creates a Crash object from a list containing crash's whole row from the CSV.
     * @param crashData A list containing the crash's whole row from the CSV.
     * @return A new {@link Crash} object for the given crash data
     */
    private Crash crashFromCSVData(String[] crashData) {
        int year = Integer.parseInt(crashData[CsvAttributes.CRASH_YEAR.ordinal()]);
        int speedLimit = Integer.parseInt(crashData[CsvAttributes.SPEED_LIMIT.ordinal()]);

        int fatalities = Integer.parseInt(crashData[CsvAttributes.FATAL_COUNT.ordinal()]);
        int seriousInjuries = Integer.parseInt(crashData[CsvAttributes.SERIOUS_INJURY_COUNT.ordinal()]);
        int minorInjuries = Integer.parseInt(crashData[CsvAttributes.MINOR_INJURY_COUNT.ordinal()]);

        double latitude = Double.parseDouble(crashData[CsvAttributes.LAT.ordinal()]);
        double longitude = Double.parseDouble(crashData[CsvAttributes.LNG.ordinal()]);
        String roadName1 = crashData[CsvAttributes.CRASH_LOCATION_1.ordinal()];
        String roadName2 = crashData[CsvAttributes.CRASH_LOCATION_2.ordinal()];
        String region = crashData[CsvAttributes.REGION.ordinal()];

        Vehicle[] vehicles = vehiclesFromCSVData(crashData);
        Weather weather = Weather.fromString(crashData[CsvAttributes.WEATHER_A.ordinal()]);
        Lighting lighting = Lighting.fromString(crashData[CsvAttributes.LIGHT.ordinal()]);
        Severity severity = Severity.fromString(crashData[CsvAttributes.CRASH_SEVERITY.ordinal()]);

        return new Crash(year, speedLimit, fatalities, seriousInjuries, minorInjuries,
                latitude, longitude, roadName1, roadName2, region,
                vehicles, weather, lighting, severity);
    }


    /**
     * Reads the CSV file and creates a new {@link Crash} object for each row.
     *
     * @param numCrashes The number of crashes to read from the CSV file
     */
    public Crash[] readLines(int numCrashes) {
        Crash[] crashes = new Crash[numCrashes];

        try (BufferedReader reader = new BufferedReader(this.csvData)) {
            reader.readLine(); // skip the first line of headers

            for (int crash = 0; crash < numCrashes; crash++) {
                String[] values = reader.readLine().split(",");

                crashes[crash] = crashFromCSVData(values);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return crashes;
    }

    /**
     * Prints the first 10 crashes in the CSV file.
     */
    public static void printCrashes() {
        CsvReader csvReader;
        try {
            csvReader = new CsvReader("src/main/resources/crash_data.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Crash[] crashes = csvReader.readLines(10);

        for (Crash crash : crashes) {
            System.out.println(crash);
        }
    }
}
