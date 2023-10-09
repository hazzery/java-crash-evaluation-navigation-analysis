package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private BorderPane notificationLayoutPane;
    private FlowPane notificationPane;
    private StackPane overlayPane;
    private BorderPane topBarPane;

    private Parent mapViewParent;
    private Parent tableViewParent;

    private TableViewController tableViewController;
    private MapViewController mapViewController;

    private Label loadingLabel;
    private int currentView;
    private int notificationStack;
    private final Duration tooltipDelaySec = Duration.seconds(1);

    public void init(Stage stage) {
        initialiseLoadingView();

        initialiseTableView();
        initialiseMapView();

        displayTopBar();
        displayTableButtonsPane();
        displayButtonBar();
        displayMenuBar();

        initialiseNotificationPane();

        stage.sizeToScene();
    }

    private void initialiseLoadingView() {
        loadingLabel = new Label();
        currentView = 1;
        notificationStack = 0;
    }

    private void initialiseNotificationPane() {
        notificationStack = 0;
        notificationLayoutPane = new BorderPane();
        notificationPane = new FlowPane(Orientation.VERTICAL);
        notificationPane.setAlignment(Pos.BOTTOM_CENTER);
        notificationLayoutPane.setRight(notificationPane);
        overlayPane.getChildren().add(notificationLayoutPane);
    }

    private void displayTableButtonsPane() {
        tableButtonsPane = new BorderPane();
        overlayPane = new StackPane();
        tableButtonsPane.setId("tableButtonsPane");
        displayLoadingView("Loading crash data onto the map...");
        overlayPane.getChildren().add(tableButtonsPane);
        mainWindow.setCenter(overlayPane);
    }

    private void displayButtonBar() {
        try {
            FXMLLoader buttonBarLoader = new FXMLLoader(getClass().getResource("/fxml/button_bar.fxml"));
            Parent buttonBarParent = buttonBarLoader.load();
            ButtonBarController buttonBarController = buttonBarLoader.getController();
            buttonBarController.giveMainControl(this);
            buttonBarController.init();
            topBarPane.setCenter(buttonBarParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayTopBar() {
        try {
            topBarPane = new BorderPane();
            topBarPane.setId("topBarPane");
            FXMLLoader topBarLoader = new FXMLLoader(getClass().getResource("/fxml/top_bar.fxml"));
            Parent topBarParent = topBarLoader.load();
            TopBarController topBarController = topBarLoader.getController();
            topBarController.init();
            mainWindow.setTop(topBarPane);
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
            tableViewController = tableViewLoader.getController();
            mainWindow.getStylesheets().add(getClass().getResource("/stylesheets/table.css").toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void displayLoadingView(String text) {
        loadingLabel.setText(text);
        tableButtonsPane.setCenter(loadingLabel);
    }

    public void hideLoadingView() {
        if (currentView == 1) {
            displayMapView();
        } else {
            displayTableView();
        }
    }

    public void displayTableView() {
        tableButtonsPane.setCenter(tableViewParent);
        currentView = 0;
        showNotification("displayingTableView");
    }

    public void displayMapView() {
        tableButtonsPane.setCenter(mapViewParent);
        currentView = 1;
        showNotification("displayingMapView");
    }

    public void updateViews() {
        mapViewController.addAllCrashMarkers();
        tableViewController.updateCrashes();
    }

    /**
     * A helper function to condense making new tooltips for all the buttons
     * @param tooltipText The text for the tooltip to display
     * @return new tooltip with specified text and the specific tooltip show delay time.
     */
    public Tooltip makeTooltip(String tooltipText) {
        Tooltip newTooltip = new Tooltip();
        newTooltip.setShowDelay(tooltipDelaySec);
        newTooltip.setText(tooltipText);
        return newTooltip;
    }

    /**
     * Notification builder
     * @param text text for the notification to show
     */
    public void showNotification(String text) {
        Label notifLabel = new Label(text);
        notifLabel.getStylesheets().add(getClass().getResource("/stylesheets/notification.css").toExternalForm());
        notificationPane.getChildren().add(notifLabel);
    }
}