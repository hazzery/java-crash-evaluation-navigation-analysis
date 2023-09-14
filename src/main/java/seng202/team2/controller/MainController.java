package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/**
 * Main controller class for the application window.
 * Handles displaying the top bar and navigation bar as well as swapping between the map and
 * table view.
 * adapted from "<a href="https://eng-git.canterbury.ac.nz/men63/seng202-advanced-fx-public">advanced JavaFX lab</a>"
 *
 * @author Findlay Royds
 * @author Isaac Ure
 * @author Louis Hobson
 */

public class MainController {
    @FXML
    private BorderPane mainWindow;
    private BorderPane tableButtonsPane;

    private Parent mapViewParent;
    private Parent tableViewParent;

    public void init(Stage stage) {
        initialiseTableView();
        initialiseMapView();

        displayTopBar();
        displayTableButtonsPane();
        displayButtonBar();
        displayMenuBar();
        displayMapView();

        stage.sizeToScene();
    }

    private void displayTableButtonsPane() {
        tableButtonsPane = new BorderPane();
        tableButtonsPane.setId("tableButtonsPane");
        //Region region = new Region();
        //region.setMinWidth(30);
        //tableButtonsPane.setLeft(region);
        mainWindow.setCenter(tableButtonsPane);
    }

    private void displayButtonBar() {
        try {
            FXMLLoader buttonBarLoader = new FXMLLoader(getClass().getResource("/fxml/button_bar.fxml"));
            Parent buttonBarParent = buttonBarLoader.load();
            ButtonBarController buttonBarController = buttonBarLoader.getController();
            buttonBarController.init();
            tableButtonsPane.setTop(buttonBarParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            navigationBarParent.toFront();
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
            mainWindow.getStylesheets().add(getClass().getResource("/stylesheets/table.css").toExternalForm());
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
        tableButtonsPane.setCenter(tableViewParent);
    }

    public void displayMapView() {
        tableButtonsPane.setCenter(mapViewParent);
    }
}