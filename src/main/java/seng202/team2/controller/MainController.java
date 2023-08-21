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

    private Stage stage;

    public void init(Stage stage) throws IOException {
        this.stage = stage;

        displayTableView(stage);
        stage.sizeToScene();
    }

    private void displayTableView(Stage stage) throws IOException {
        System.out.println("got here");
        try {
            // Load our sales_table.fxml file
            FXMLLoader tableViewLoader = new FXMLLoader(getClass().getResource("/fxml/table_view.fxml"));
            // Get the root FXML element after loading
            Parent tableViewParent = tableViewLoader.load();
            // Get access to the controller the FXML is using
            TableViewController tableViewController = tableViewLoader.getController();
            // Initialise the controller
            tableViewController.init(stage);
            // Set the root of our new component to the center of the borderpane
            mainWindow.setCenter(tableViewParent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}