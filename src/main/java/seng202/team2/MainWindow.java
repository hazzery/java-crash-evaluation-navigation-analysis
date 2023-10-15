package seng202.team2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Creates the main FXML window to start the application
 *
 * @author Findlay Royds
 */
public class MainWindow extends Application {
    /**
     * Initialises the main window of JCENA
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = baseLoader.load();
        primaryStage.sizeToScene();
        primaryStage.setTitle("JCENA");
        try {
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icon.png"))));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root, 1100, 800));

        // Limit minimum size of application
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(500);

        primaryStage.show();
    }

    /**
     * Opens the main window of JCENA
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
