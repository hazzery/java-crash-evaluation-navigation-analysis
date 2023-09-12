package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;

/**
 * Controls the filter button bar of the table
 *
 * @author Isaac Ure
 * @author Ben Moore
 */

public class ButtonBarController {

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

    }
}

