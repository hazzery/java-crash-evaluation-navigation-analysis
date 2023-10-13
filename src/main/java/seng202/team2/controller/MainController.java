package seng202.team2.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

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
    private FlowPane notificationPane;
    private StackPane overlayPane;

    private Parent mapViewParent;
    private Parent tableViewParent;

    private TableViewController tableViewController;
    private MapViewController mapViewController;
    private LoadingScreenController loadingScreenController;

    private Label loadingLabel;
    private Label overflowLabel;
    private int currentView;
    private int notificationCount;

    private static final Duration tooltipDelaySec = Duration.millis(300);

    /**
     * Initialises the main window by displaying button bar, menu bar, and loading view
     * and preparing the table view and map view and notification pane.
     */
    @FXML
    void initialize() {
        initialiseMainViewPane();
        initialiseTableView();
        initialiseMapView();
        initialiseNotificationPane();
        initialiseLoadingScreen();

        displayButtonBar();
        displayMenuBar();

        getLoadingScreen().show("Filtering crash data...");
        displayMapView();
    }

    /**
     * Initialises The notification pane by
     * nesting a flow pane inside a border pane
     * and overlaying it over the map and table view
     */
    private void initialiseNotificationPane() {
        notificationCount = 0;
        overflowLabel = new Label();
        overflowLabel.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheets/notification.css")).toExternalForm());
        overflowLabel.setMinWidth(300);
        overflowLabel.setMinHeight(30);
        overflowLabel.setText("Too many notifications!");

        BorderPane notificationLayoutPane = new BorderPane();
        notificationPane = new FlowPane(Orientation.VERTICAL);
        notificationPane.setMaxWidth(400);
        notificationPane.setAlignment(Pos.BOTTOM_LEFT);
        notificationPane.setPickOnBounds(false);
        notificationLayoutPane.setPickOnBounds(false);
        notificationLayoutPane.setRight(notificationPane);

        overlayPane.getChildren().add(notificationLayoutPane);
    }

    private void initialiseLoadingScreen() {
        try {
            FXMLLoader loadingScreenLoader = new FXMLLoader(getClass().getResource("/fxml/loading_screen.fxml"));
            Parent loadingScreenParent = loadingScreenLoader.load();
            loadingScreenParent.setPickOnBounds(false);
            loadingScreenParent.getStylesheets().add(getClass().getResource("/stylesheets/table.css").toExternalForm());
            loadingScreenController = loadingScreenLoader.getController();
            loadingScreenController.init();
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

    public void displayTableView() {
        mainViewPane.setCenter(tableViewParent);
        currentView = 0;
    }

    /**
     * Swaps out the current view for the map view
     */
    public void displayMapView() {
        mainViewPane.setCenter(mapViewParent);
        currentView = 1;
    }

    /**
     * Updates the map and table views to reflect new changes in filter options
     */
    public void updateViews() {
        mapViewController.addAllCrashMarkers();

        if (currentView == 0)
            tableButtonsPane.setCenter(mapViewParent);
        tableViewController.updateCrashes();
        if (currentView == 0)
            tableButtonsPane.setCenter(tableViewParent);
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

    /**
     * Notification builder
     * Creates a notification as a label and
     * uses timer on a separate thread to delete notification
     * after 3 seconds
     *
     * @param text Text for the notification to show
     */
    public void showNotification(String text) {
        if (notificationCount > 5) {
            if (!notificationPane.getChildren().contains(overflowLabel)) {
                notificationPane.getChildren().add(overflowLabel);
            }
            return;
        }
        notificationCount++;
        Label notifLabel = new Label(text);
        notifLabel.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesheets/notification.css")).toExternalForm());
        notifLabel.setMinWidth(300);
        notifLabel.setMaxWidth(300);
        notifLabel.setWrapText(true);
        notifLabel.setMinHeight(30);
        notifLabel.setAlignment(Pos.BOTTOM_RIGHT);
        notificationPane.getChildren().add(notifLabel);
        FadeTransition ft = new FadeTransition(Duration.seconds(1), notifLabel);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    ft.play();
                    ft.setOnFinished(e -> notificationPane.getChildren().remove(notifLabel));
                    notificationCount--;
                    if (notificationCount <= 5) {
                        notificationPane.getChildren().remove(overflowLabel);
                    }
                    cancel();
                });
            }
        }, 2000);
    }
}