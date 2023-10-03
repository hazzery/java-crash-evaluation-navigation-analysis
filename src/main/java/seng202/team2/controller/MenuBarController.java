package seng202.team2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;

public class MenuBarController {
    @FXML
    private Button menuButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button tableViewButton;

    @FXML
    private Button mapViewButton;

    @FXML
    private GridPane menuPane;

    private boolean expanded = false;

    private MainController mainController;


    void init(MainController mainController) {
        this.mainController = mainController;
        helpButton.setVisible(false);
        mapViewButton.setStyle("-fx-background-color: white;");
        displayIcons();
        toggleMenuBar();
    }

    public void menuButtonClicked() {
        toggleMenuBar();
    }


    /**
     * Inits the icon images for the menu bar and places them on their
     * respective buttons
     */
    private void displayIcons() {
        Image mapIMG = null;
        Image tableIMG = null;
        Image closeIMG = null;
        try {
            mapIMG = new Image(getClass().getResourceAsStream("/icons/map.png"), 20, 20, true, true);
            tableIMG = new Image(getClass().getResourceAsStream("/icons/table.png"), 20, 20, true, true);

            closeIMG = new Image(getClass().getResourceAsStream("/icons/close.png"), 12, 12, true, true);
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

    private void displayClose() {
        Image menuIMG = null;
        try {
            menuIMG = new Image(getClass().getResourceAsStream("/icons/menu.png"), 12, 12, true, true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        menuButton.setGraphic(new ImageView(menuIMG));
    }

    private void toggleMenuBar() {
        if (expanded) {
            menuPane.setPrefWidth(40);

            menuButton.setText("");
            displayClose();
            helpButton.setText("?");
            tableViewButton.setText("");
            mapViewButton.setText("");
        } else {
            menuPane.setPrefWidth(120);

            menuButton.setText("Menu");
            displayIcons();
            helpButton.setText("Help ?");
            tableViewButton.setText("Table");
            mapViewButton.setText("Map");
        }
        expanded = !expanded;
    }

    public void tableViewButtonClicked(ActionEvent actionEvent) {
        tableViewButton.setStyle("-fx-background-color: white");
        mapViewButton.setStyle("-fx-background-color: transparent");
        mainController.displayTableView();
    }

    public void mapViewButtonClicked(ActionEvent actionEvent) {
        tableViewButton.setStyle("-fx-background-color: transparent");
        mapViewButton.setStyle("-fx-background-color: white");
        mainController.displayMapView();
    }

    public void helpButtonClicked(ActionEvent actionEvent) {
    }
}
