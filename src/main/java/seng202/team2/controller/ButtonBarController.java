package seng202.team2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import seng202.team2.database.DbAttributes;
import seng202.team2.database.QueryBuilder;
import seng202.team2.models.Severity;

/**
 * Controls the filter button bar of the table
 *
 * @author Isaac Ure
 * @author Ben Moore
 */

public class ButtonBarController {

    @FXML
    public ButtonBar buttonBar;

    @FXML
    private ToggleButton person;

    @FXML
    private ToggleButton cyclist;

    @FXML
    private ToggleButton car;

    @FXML
    private ToggleButton bus;

    @FXML
    private Slider selectedYear;

    @FXML
    private MenuButton regionSelect;

    @FXML
    private RadioMenuItem sev1;

    @FXML
    private RadioMenuItem sev2;

    @FXML
    private RadioMenuItem sev3;

    @FXML
    private RadioMenuItem sev4;

    @FXML
    private RadioMenuItem sev5;

    QueryBuilder queryBuilder = new QueryBuilder();

    //
    public void filterTable() {
        // Slider
        double year = selectedYear.getValue();
        System.out.println(year);

        if (sev1.isSelected()) {
            System.out.println(1);
        }
        if (sev2.isSelected()) {
            System.out.println(2);
        }
        if (sev3.isSelected()) {
            System.out.println(3);
        }
        if (sev4.isSelected()) {
            System.out.println(4);
        }
        if (sev5.isSelected()) {
            System.out.println(5);
        }
    }

    public void setIcons() {
        Image personIMG = null;
        Image cyclistIMG = null;
        Image carIMG = null;
        Image busIMG = null;
        try {
            personIMG = new Image(getClass().getResourceAsStream("/icons/person.png"),20,20, true, false);
            cyclistIMG = new Image(getClass().getResourceAsStream("/icons/cyclist.png"), 20 ,20, true, false);
            carIMG = new Image(getClass().getResourceAsStream("/icons/car.png"), 20, 20, true, false);
            busIMG = new Image(getClass().getResourceAsStream("/icons/bus.png"), 20, 20, true, false);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        person.setGraphic(new ImageView(personIMG));
        cyclist.setGraphic(new ImageView(cyclistIMG));
        car.setGraphic(new ImageView(carIMG));
        bus.setGraphic(new ImageView(busIMG));
    }

    public void setSeverityValues() {
        sev1.setText(Severity.toDisplayValue(0));
        sev2.setText(Severity.toDisplayValue(1));
        sev3.setText(Severity.toDisplayValue(2));
        sev4.setText(Severity.toDisplayValue(3));
        sev5.setText(Severity.toDisplayValue(4));
    }


    void init() {
        setIcons();
        setSeverityValues();
        regionSelect.setDisable(true);
    }

    public void addVehicle(ActionEvent actionEvent) {
        RadioMenuItem caller = (RadioMenuItem) actionEvent.getSource();
        DbAttributes vehicle = DbAttributes.valueOf(caller.getText());
        queryBuilder.greaterThan(1, vehicle);
    }

    public void addSeverity(ActionEvent actionEvent) {
    }
}


