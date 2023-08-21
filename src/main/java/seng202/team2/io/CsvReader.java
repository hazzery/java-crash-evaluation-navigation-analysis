package seng202.team2.io;

import com.opencsv.exceptions.CsvValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import seng202.team2.database.CrashDao;
import seng202.team2.models.*;
import com.opencsv.CSVReader;

import java.util.*;
import java.io.*;


/**
 * CSVReader class provides the ability to read in the CSV file and generate {@link Crash} objects for each row.
 *
 * @author Harrison Parkes
 */
public class CsvReader {
    private static final Logger log = LogManager.getLogger(CsvReader.class);

    private final CrashDao crashDao;
    private final String fileName;

    /**
     * Creates a new CSVReader object for the given file.
     * @param fileName The name of the file to read
     */
    public CsvReader(String fileName) {
        this.fileName = fileName;
        this.crashDao = new CrashDao();
    }

    /**
     * Some integer fields in the CSV, e.g. fatal_count, have empty cells
     * This function simply returns 0 in this case.
     * @param string The string to parse
     * @return The integer value of the string, or 0 if the string is empty
     */
    private static int nullSafeParseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Creates an array of vehicles from a list containing a crash's whole row from the CSV.
     * @param crashData A list containing the crash's whole row from the CSV.
     * @return An array of {@link Vehicle} objects for the given crash data
     */
    private Map<Vehicle, Integer> vehiclesFromCsvData(String[] crashData) {
        Map<Vehicle, Integer> vehicles = new HashMap<>();

        for (Vehicle vehicle : Vehicle.values()) {
            int vehicleCount = nullSafeParseInt(crashData[vehicle.getCsvColumn()]);

            vehicles.put(vehicle, vehicleCount);
        }
        return vehicles;
    }

    /**
     * Creates a Crash object from a list containing crash's whole row from the CSV.
     * @param crashData A list containing the crash's whole row from the CSV.
     * @return A new {@link Crash} object for the given crash data
     */
    private Crash crashFromCsvData(String[] crashData) {
        try {
            int crashId = nullSafeParseInt(crashData[CsvAttributes.OBJECT_ID.ordinal()]);
            int year = nullSafeParseInt(crashData[CsvAttributes.CRASH_YEAR.ordinal()]);
            int fatalities = nullSafeParseInt(crashData[CsvAttributes.FATAL_COUNT.ordinal()]);
            int seriousInjuries = nullSafeParseInt(crashData[CsvAttributes.SERIOUS_INJURY_COUNT.ordinal()]);
            int minorInjuries = nullSafeParseInt(crashData[CsvAttributes.MINOR_INJURY_COUNT.ordinal()]);

            double latitude = Double.parseDouble(crashData[CsvAttributes.LAT.ordinal()]);
            double longitude = Double.parseDouble(crashData[CsvAttributes.LNG.ordinal()]);
            String roadName1 = crashData[CsvAttributes.CRASH_LOCATION_1.ordinal()];
            String roadName2 = crashData[CsvAttributes.CRASH_LOCATION_2.ordinal()];
            String region = crashData[CsvAttributes.REGION.ordinal()];

            Map<Vehicle, Integer> vehicles = vehiclesFromCsvData(crashData);
            Weather weather = Weather.fromString(crashData[CsvAttributes.WEATHER_A.ordinal()]);
            Lighting lighting = Lighting.fromString(crashData[CsvAttributes.LIGHT.ordinal()]);
            Severity severity = Severity.fromString(crashData[CsvAttributes.CRASH_SEVERITY.ordinal()]);

            return new Crash(crashId, year, fatalities, seriousInjuries, minorInjuries,
                    latitude, longitude, roadName1, roadName2, region,
                    vehicles, weather, lighting, severity);
        } catch (NumberFormatException exception) {
            log.error("Error parsing crash data: " + Arrays.toString(crashData));
            exception.printStackTrace();
            log.error(exception);
        }

        return null;
    }

    public List<Crash> generateAllCrashes() {
        List<Crash> crashes = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(this.fileName))) {
            reader.skip(1); // skip the first line of headers
            String[] line;
            while ((line = reader.readNext()) != null) {
                Crash crash = crashFromCsvData(line);

                if (crash != null) {
                    crashes.add(crash);
                }
            }
        } catch (IOException | CsvValidationException exception) {
            log.error(exception);
        }
        return crashes;
    }

    /**
     * Imports all crashes from the CSV file into the database.
     */
    public void importAllToDatabase() {
        List<Crash> crashes = generateAllCrashes();
//        List<Crash> crashes = Arrays.stream(readLines(10000)).toList();
        crashDao.addBatch(crashes);
    }


    /**
     * Reads the CSV file and creates a new {@link Crash} object for each row.
     *
     * @param numCrashes The number of crashes to read from the CSV file
     */
    public Crash[] readLines(int numCrashes) {
        Crash[] crashes = new Crash[numCrashes];

        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            reader.skip(1); // skip the first line of headers

            for (int crash = 0; crash < numCrashes; crash++) {
                String[] values = reader.readNext();

                crashes[crash] = crashFromCsvData(values);
            }
        } catch (IOException | CsvValidationException exception) {
            log.error(exception);
        }
        return crashes;
    }

    /**
     * Prints the first `numLines` crashes in the CSV file.
     */
    public static void printCrashes(int numLines) {
        CsvReader csvReader = new CsvReader("src/main/resources/crash_data.csv");
        Crash[] crashes = csvReader.readLines(numLines);

        for (Crash crash : crashes) {
            System.out.println(crash);
        }
    }
}
