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
    private Parent mapViewParent;
    private Parent tableViewParent;
	
    public void init(Stage stage) {
        initialiseTableView();
        initialiseMapView();

        displayTopBar();
        displayMenuBar();
        displayMapView();

        stage.sizeToScene();
    }

    private void displayTopBar() {
        try {
            FXMLLoader topBarLoader = new FXMLLoader(getClass().getResource("/fxml/top_bar.fxml"));
            Parent topBarParent = topBarLoader.load();
            TopBarController topBarController = topBarLoader.getController();
            topBarController.init();
            mainWindow.setTop(topBarParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayMenuBar() {
        try {
            FXMLLoader menuBarLoader = new FXMLLoader(getClass().getResource("/fxml/menu_bar.fxml"));
            Parent navigationBarParent = menuBarLoader.load();
            MenuBarController menuBarController = menuBarLoader.getController();
            menuBarController.init(this);
            mainWindow.setLeft(navigationBarParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initialiseTableView() {
        try {
            FXMLLoader tableViewLoader = new FXMLLoader(getClass().getResource("/fxml/table_view.fxml"));
            tableViewParent = tableViewLoader.load();
            TableViewController tableViewController = tableViewLoader.getController();
            tableViewController.init();
            mainWindow.setCenter(tableViewParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initialiseMapView() {
        try {
            FXMLLoader mapViewLoader = new FXMLLoader(getClass().getResource("/fxml/map_view.fxml"));
            mapViewParent = mapViewLoader.load();
            MapViewController mapViewController = mapViewLoader.getController();
            mapViewController.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayTableView() {
        mainWindow.setCenter(tableViewParent);
    }
	
    public void displayMapView() {
        mainWindow.setCenter(mapViewParent);
    }
}