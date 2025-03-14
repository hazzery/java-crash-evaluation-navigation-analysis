package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;


/**
 * Main controller class for the application window.
 * Handles displaying the top filter button bar, side navigation bar, and swapping between the map and table view.
 * Adapted from "<a href="https://eng-git.canterbury.ac.nz/men63/seng202-advanced-fx-public">advanced JavaFX lab</a>"
 *
 * @author Findlay Royds
 * @author Isaac Ure
 * @author Louis Hobson
 */

public class MainController {
    @FXML
    private BorderPane mainWindow;
    private BorderPane mainViewPane;
    private StackPane overlayPane;

    private Parent mapViewParent;
    private Parent tableViewParent;

    private TableViewController tableViewController;
    private MapViewController mapViewController;
    private LoadingScreenController loadingScreenController;

    private static final Duration tooltipDelaySec = Duration.millis(300);

    /**
     * Initialises the main window by displaying button bar, menu bar, and loading view
     * and preparing the table view and map view.
     */
    @FXML
    void initialize() {
        initialiseMainViewPane();
        initialiseTableView();
        initialiseMapView();
        initialiseLoadingScreen();

        displayButtonBar();
        displayMenuBar();

        getLoadingScreen().show("Filtering crash data...");
        displayMapView();
    }

    /**
     * Initialises Loading Screen pane from FXML, styles it with CSS and initialises its controller
     */
    private void initialiseLoadingScreen() {
        try {
            FXMLLoader loadingScreenLoader = new FXMLLoader(getClass().getResource("/fxml/loading_screen.fxml"));
            Parent loadingScreenParent = loadingScreenLoader.load();
            loadingScreenParent.setPickOnBounds(false);
            loadingScreenParent.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheets/table.css")).toExternalForm());
            loadingScreenController = loadingScreenLoader.getController();
            overlayPane.getChildren().add(loadingScreenParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main view pane holds both the map and table views.
     * It sits inside a stack pane so that we can overlay items on top of the table/map
     */
    private void initialiseMainViewPane() {
        overlayPane = new StackPane();

        mainViewPane = new BorderPane();
        mainViewPane.setId("mainViewPane");
        overlayPane.getChildren().add(mainViewPane);

        mainWindow.setCenter(overlayPane);
    }

    /**
     * Initialises the button bar from its FXML file
     */
    private void displayButtonBar() {
        try {
            FXMLLoader buttonBarLoader = new FXMLLoader(getClass().getResource("/fxml/button_bar.fxml"));
            Parent buttonBarParent = buttonBarLoader.load();
            ButtonBarController buttonBarController = buttonBarLoader.getController();
            buttonBarController.giveMainControl(this);
            mainWindow.setTop(buttonBarParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialises the menu bar from its FXML file
     */
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

    /**
     * Initialises the table view from its FXML file
     * and applies the stylesheet.
     */
    private void initialiseTableView() {
        try {
            FXMLLoader tableViewLoader = new FXMLLoader(getClass().getResource("/fxml/table_view.fxml"));
            tableViewParent = tableViewLoader.load();
            tableViewController = tableViewLoader.getController();
            mainWindow.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheets/table.css")).toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialises the map view from its FXML file.
     */
    private void initialiseMapView() {
        try {
            FXMLLoader mapViewLoader = new FXMLLoader(getClass().getResource("/fxml/map_view.fxml"));
            mapViewParent = mapViewLoader.load();
            mapViewController = mapViewLoader.getController();
            mapViewController.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Swaps out the current view for the table view
     */
    public void displayTableView() {
        mainViewPane.setCenter(tableViewParent);
    }

    /**
     * Swaps out the current view for the map view
     */
    public void displayMapView() {
        mainViewPane.setCenter(mapViewParent);
    }

    /**
     * Updates the map and table views to reflect new changes in filter options
     */
    public void updateViews() {
        mapViewController.filterHeatmapPoints();
        tableViewController.updatePageCount();
    }

    public LoadingScreenController getLoadingScreen() {
        return loadingScreenController;
    }

    /**
     * Tooltip factory to ensure they all have the same delay time.
     *
     * @param tooltipText The text for the tooltip to display
     * @return new tooltip with specified text and the specific tooltip show delay time.
     */
    public static Tooltip makeTooltip(String tooltipText) {
        Tooltip newTooltip = new Tooltip(tooltipText);
        newTooltip.setShowDelay(MainController.tooltipDelaySec);
        return newTooltip;
    }
}