package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/**
 * Controller for the loading screen when filtering data and starting the app.
 * Implements showing and hiding the loading label.
 *
 * @author Findlay Royds
 */
public class LoadingScreenController {
    @FXML
    BorderPane loadingLabelBorderPane;

    @FXML
    Text loadingLabelText;

    /**
     * Initialise the loading screen. Hides the loading label by default.
     */
    @FXML
    void initialize() {
        hide();
    }

    /**
     * Hide the loading label (allows click through)
     */
    public void hide() {
        loadingLabelBorderPane.setVisible(false);
    }

    /**
     * Show the loading label on top of the application and display a message.
     *
     * @param displayText The message to be displayed on the loading label
     */
    public void show(String displayText) {
        loadingLabelBorderPane.setVisible(true);
        loadingLabelText.setText(displayText);
    }
}
