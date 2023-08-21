package seng202.team2.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * creates a data row
 *
 * @author Isaac Ure
 * @author Findlay Royds
 */
public class DataRow {
    private final SimpleStringProperty severity;
    private final SimpleIntegerProperty fatalities;
    private final SimpleIntegerProperty numberOfVehiclesInvolved;
    private final SimpleStringProperty weather;
    private final SimpleStringProperty lighting;
    private final SimpleIntegerProperty year;

    DataRow(String severity, int fatalities, int numVehicles, String weather, String lighting, int year) {
        this.severity = new SimpleStringProperty(severity);
        this.fatalities = new SimpleIntegerProperty(fatalities);
        this.numberOfVehiclesInvolved = new SimpleIntegerProperty(numVehicles);
        this.weather = new SimpleStringProperty(weather);
        this.lighting = new SimpleStringProperty(lighting);
        this.year = new SimpleIntegerProperty(year);

    }

    public String getSeverity() {
        return severity.get();
    }
    public Integer getNumberOfVehiclesInvolved() {
        return numberOfVehiclesInvolved.get();
    }
    public Integer getFatalities() {return fatalities.get();}
    public String getWeather() {return weather.get();}
    public String getLighting() {return lighting.get();}
    public Integer getYear() {return year.get();}

}
