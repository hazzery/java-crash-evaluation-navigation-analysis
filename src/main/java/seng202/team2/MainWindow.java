package seng202.team2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng202.team2.controller.MainController;

import java.io.IOException;

/**
 * Creates the main FXML window to start the application
 *
 * @author Findlay Royds
 */
public class MainWindow extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = baseLoader.load();
        MainController baseController = baseLoader.getController();
        primaryStage.sizeToScene();
        primaryStage.setTitle("JCENA");
        primaryStage.setScene(new Scene(root, 1100, 800));

        // Limit minimum size of application
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(500);

        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
