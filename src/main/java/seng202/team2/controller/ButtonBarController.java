package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import seng202.team2.models.Severity;

import java.io.IOException;

/**
 * Controls the filter button bar of the table
 *
 * @author Isaac Ure
 * @author Ben Moore
 */

public class ButtonBarController {

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
    private RadioMenuItem sev1;

    @FXML
    private RadioMenuItem sev2;

    @FXML
    private RadioMenuItem sev3;

    @FXML
    private RadioMenuItem sev4;

    @FXML
    private RadioMenuItem sev5;
    //
    public void filterTable() {
        // Slider
        double year = selectedYear.getValue();
        System.out.println(year);

        // Radio buttons
        // Could be improved?
        if (sev1.isSelected()) {
            System.out.println(1);
        } else if (sev2.isSelected()) {
            System.out.println(2);
        } else if (sev3.isSelected()) {
            System.out.println(3);
        } else if (sev4.isSelected()) {
            System.out.println(4);
        } else if (sev5.isSelected()) {
            System.out.println(5);
        }
    }

    public void setIcons() {
        Image personIMG = null;
        Image cyclistIMG = null;
        Image carIMG = null;
        Image busIMG = null;
        try {
            personIMG = new Image(getClass().getResourceAsStream("/icons/person.png"));
            cyclistIMG = new Image(getClass().getResourceAsStream("/icons/cyclist.png"));
            carIMG = new Image(getClass().getResourceAsStream("/icons/car.png"));
            busIMG = new Image(getClass().getResourceAsStream("/icons/bus.png"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        person.setGraphic(new ImageView(personIMG));
        cyclist.setGraphic(new ImageView(cyclistIMG));
        car.setGraphic(new ImageView(carIMG));
        bus.setGraphic(new ImageView(busIMG));
    }

    public void setSeverityValues() {
        sev1.setText(Severity.toDisplayValue(Severity.values()[0]));
        sev2.setText(Severity.toDisplayValue(Severity.values()[1]));
        sev3.setText(Severity.toDisplayValue(Severity.values()[2]));
        sev4.setText(Severity.toDisplayValue(Severity.values()[3]));
        sev5.setText(Severity.toDisplayValue(Severity.values()[4]));

    }


    void init() {
        setIcons();
        setSeverityValues();
    }
}

