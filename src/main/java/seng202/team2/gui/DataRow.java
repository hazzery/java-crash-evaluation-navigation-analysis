package seng202.team2.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * creates a data row
 */
public class DataRow {
    private final SimpleStringProperty vehicle;
    private final SimpleStringProperty severity;
    private final SimpleIntegerProperty people;

    DataRow(String vehicle, String severity, Integer people) {
        this.vehicle = new SimpleStringProperty(vehicle);
        this.severity = new SimpleStringProperty(severity);
        this.people = new SimpleIntegerProperty(people);
    }

    public String getVehicle() {
        return vehicle.get();
    }
    public String getSeverity() {
        return severity.get();
    }
    public Integer getPeople() {
        return people.get();
    }

}
