package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main controller class for the application window.
 * Handles displaying the top bar and navigation bar as well as swapping between the map and
 * table view.
 * adapted from "<a href="https://eng-git.canterbury.ac.nz/men63/seng202-advanced-fx-public">advanced JavaFX lab</a>"
 *
 * @author Findlay Royds
 */

public class MainController {
    @FXML
    private BorderPane mainWindow;

    public void init(Stage stage) {

        displayTableView();
        stage.sizeToScene();
    }

    private void displayTableView() {
        System.out.println("got here");
        try {
            FXMLLoader tableViewLoader = new FXMLLoader(getClass().getResource("/fxml/table_view.fxml"));
            Parent tableViewParent = tableViewLoader.load();
            TableViewController tableViewController = tableViewLoader.getController();
            tableViewController.init();
            mainWindow.setCenter(tableViewParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}