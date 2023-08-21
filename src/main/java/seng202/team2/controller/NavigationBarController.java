package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class NavigationBarController {
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

    private boolean expanded = true;

    void init() {
        toggleMenuBar();
    }

    public void toggleMenuBar() {
        if (expanded) {
            menuPane.setPrefWidth(30);

            menuButton.setText("≡");
            helpButton.setText("?");
            tableViewButton.setText("▥");
            mapViewButton.setText("\uD83D\uDCCD");
        } else {
            menuPane.setPrefWidth(100);

            menuButton.setText("Menu ≡");
            helpButton.setText("Help ?");
            tableViewButton.setText("Table ▥");
            mapViewButton.setText("Map \uD83D\uDCCD");
        }
        expanded = !expanded;
    }
}
