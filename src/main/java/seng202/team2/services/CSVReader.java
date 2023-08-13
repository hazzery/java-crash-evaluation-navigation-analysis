package seng202.team2.services;

import seng202.team2.models.Crash;

import java.util.*;
import java.io.*;

public class CSVReader {
    static final int NUM_OF_ATTRIBUTES = 69;
    File csvData;
    Scanner scanner;

    /**
     *
     * @param fileName The name of the file to read
     * @throws FileNotFoundException If the given file does not exist
     */
    public CSVReader(String fileName) throws FileNotFoundException
    {
        csvData = new File(fileName);
        scanner = new Scanner(csvData);
    }

    /**
     *
     */
    public void read()
    {
        scanner.useDelimiter(",");
        for (int crash = 0; crash < 10; crash++)
        {
            List<String> crashData = new ArrayList<>(NUM_OF_ATTRIBUTES);

            for (int attribute = 0; attribute < NUM_OF_ATTRIBUTES * 2; attribute++)
            {
                crashData.set(attribute, scanner.next());  //find and returns the next complete token from this scanner
            }
            Crash crash1 = new Crash();
        }
        scanner.close();  //closes the scanner
    }

    public static void doTheThing()
    {
        CSVReader obj = null;
        try {
            obj = new CSVReader("src/main/resources/crash_data.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        obj.read();
    }
}
