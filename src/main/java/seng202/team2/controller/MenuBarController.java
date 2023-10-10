package seng202.team2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class MenuBarController {
    @FXML
    private Button menuButton;

    @FXML
    private Button tableViewButton;

    @FXML
    private Button mapViewButton;

    @FXML
    private GridPane menuPane;

    private boolean expanded = false;

    private MainController mainController;


    /**
     * Initialises the menu bar controller.
     * Set the icons, tooltips and expands the menu bar
     *
     * @param mainController A reference to the main controller
     */
    void init(MainController mainController) {
        this.mainController = mainController;
        mapViewButton.setStyle("-fx-background-color: white;");
        displayIcons();
        addTooltips();
        toggleMenuBar();
    }

    /**
     * Set the tooltips for all the menu bar buttons
     * using the helper function in MainController
     */
    private void addTooltips() {
        mapViewButton.setTooltip(this.mainController.makeTooltip("Show the map view"));
        tableViewButton.setTooltip(this.mainController.makeTooltip("Show the table view"));
        menuButton.setTooltip(this.mainController.makeTooltip("Expand/Collapse the menu side bar"));
    }

    /**
     * Initialises the icon images for the menu bar
     * and places them on their respective buttons
     */
    private void displayIcons() {
        Image mapIMG = null;
        Image tableIMG = null;
        Image closeIMG = null;
        try {
            mapIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/map.png")), 20, 20, true, true);
            tableIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/table.png")), 20, 20, true, true);

            closeIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/close.png")), 12, 12, true, true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        menuButton.setContentDisplay(ContentDisplay.RIGHT);
        tableViewButton.setContentDisplay(ContentDisplay.RIGHT);
        mapViewButton.setContentDisplay(ContentDisplay.RIGHT);
        menuButton.setGraphic(new ImageView(closeIMG));
        tableViewButton.setGraphic(new ImageView(tableIMG));
        mapViewButton.setGraphic(new ImageView(mapIMG));

    }

    /**
     * Replaces the menu button icon with a close icon
     */
    private void displayClose() {
        Image menuIMG = null;
        try {
            menuIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/menu.png")), 12, 12, true, true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        menuButton.setGraphic(new ImageView(menuIMG));
    }

    /**
     * Toggles the menu bar between expanded and collapsed
     */
    @FXML
    private void toggleMenuBar() {
        if (expanded) {
            menuPane.setPrefWidth(40);

            menuButton.setText("");
            displayClose();
            tableViewButton.setText("");
            mapViewButton.setText("");
        } else {
            menuPane.setPrefWidth(120);

            menuButton.setText("Close");
            displayIcons();
            tableViewButton.setText("Table");
            mapViewButton.setText("Map");
        }
        expanded = !expanded;
    }

    /**
     * Event handler for the table view button.
     * Displays the table view and toggles the button colours to reflect their new states.
     *
     * @param ignoredActionEvent The event that triggered the handler. (Not used)
     */
    public void tableViewButtonClicked(ActionEvent ignoredActionEvent) {
        tableViewButton.setStyle("-fx-background-color: white");
        mapViewButton.setStyle("-fx-background-color: transparent");
        mainController.displayTableView();
    }

    /**
     * Event handler for the map view button.
     * Displays the map view and toggles the button colours to reflect their new states.
     *
     * @param ignoredActionEvent The event that triggered the handler. (Not used)
     */
    public void mapViewButtonClicked(ActionEvent ignoredActionEvent) {
        tableViewButton.setStyle("-fx-background-color: transparent");
        mapViewButton.setStyle("-fx-background-color: white");
        mainController.displayMapView();
    }
}
