package seng202.team2;

import com.sun.tools.javac.Main;
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
        baseController.init(primaryStage);

        primaryStage.setTitle("JCENA");
        primaryStage.setScene(new Scene(root, 1100, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
