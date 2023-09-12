package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private RadioButton sevOne;

    @FXML
    private RadioButton sevTwo;

    @FXML
    private RadioButton sevThree;

    @FXML
    private RadioButton sevFour;

    @FXML
    private RadioButton sevFive;
    //
    public void filterTable() {
        // Slider
        double year = selectedYear.getValue();
        System.out.println(year);

        // Radio buttons
        // Could be improved?
        if (sevOne.isSelected()) {
            System.out.println(1);
        } else if (sevTwo.isSelected()) {
            System.out.println(2);
        } else if (sevThree.isSelected()) {
            System.out.println(3);
        } else if (sevFour.isSelected()) {
            System.out.println(4);
        } else if (sevFive.isSelected()) {
            System.out.println(5);
        }
    }

    void init() {
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
}

