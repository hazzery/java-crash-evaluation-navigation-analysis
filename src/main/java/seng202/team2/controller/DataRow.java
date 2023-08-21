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
    private final SimpleStringProperty roadName1;
    private final SimpleStringProperty roadName2;
    private final SimpleStringProperty region;
    private final SimpleIntegerProperty seriousInjuries;
    private final SimpleIntegerProperty minorInjuries;
    private final SimpleStringProperty weather;
    private final SimpleStringProperty lighting;
    private final SimpleIntegerProperty year;



    DataRow(String severity, int fatalities, int numVehicles, String roadName1, String roadName2, String region,
            int seriousInjuries, int minorInjuries, String weather, String lighting, int year) {
        this.severity = new SimpleStringProperty(severity);
        this.fatalities = new SimpleIntegerProperty(fatalities);
        this.numberOfVehiclesInvolved = new SimpleIntegerProperty(numVehicles);
        this.roadName1 = new SimpleStringProperty(roadName1);
        this.roadName2 = new SimpleStringProperty(roadName2);
        this.region = new SimpleStringProperty(region);
        this.seriousInjuries = new SimpleIntegerProperty(seriousInjuries);
        this.minorInjuries = new SimpleIntegerProperty(minorInjuries);
        this.weather = new SimpleStringProperty(weather);
        this.lighting = new SimpleStringProperty(lighting);
        this.year = new SimpleIntegerProperty(year);

    }

    public String getSeverity() {return severity.get();}
    public Integer getFatalities() {return fatalities.get();}
    public Integer getNumberOfVehiclesInvolved() {
        return numberOfVehiclesInvolved.get();
    }
    public String getRoadName1() {return roadName1.get();}
    public String getRoadName2() {return roadName2.get();}
    public String getRegion() {return region.get();}
    public Integer getSeriousInjuries() {return seriousInjuries.get();}
    public Integer getMinorInjuries() {return minorInjuries.get();}
    public String getWeather() {return weather.get();}
    public String getLighting() {return lighting.get();}
    public Integer getYear() {return year.get();}

}
