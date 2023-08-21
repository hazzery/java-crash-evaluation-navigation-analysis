package seng202.team2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

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
        toggleMenuBar();
    }

    public void menuButtonClicked() {
        toggleMenuBar();
    }

    private void toggleMenuBar() {
        if (expanded) {
            menuPane.setPrefWidth(36);

            menuButton.setText("≡");
            helpButton.setText("?");
            tableViewButton.setText("▥");
            mapViewButton.setText("▢");
        } else {
            menuPane.setPrefWidth(120);

            menuButton.setText("Menu ≡");
            helpButton.setText("Help ?");
            tableViewButton.setText("Table ▥");
            mapViewButton.setText("Map ▢");
        }
        expanded = !expanded;
    }

    public void tableViewButtonClicked(ActionEvent actionEvent) {
        mainController.displayTableView();
    }

    public void mapViewButtonClicked(ActionEvent actionEvent) {
    }

    public void helpButtonClicked(ActionEvent actionEvent) {
    }
}
