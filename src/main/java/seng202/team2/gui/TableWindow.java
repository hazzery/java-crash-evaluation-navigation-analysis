package seng202.team2.gui;

import com.sun.javafx.scene.shape.ArcHelper;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to demo the table view for the application
 */
public class TableWindow extends Application {

    /**
     * builds a demo table without fxml
     * @param primaryStage the current stage
     * @throws IOException if there is an issue loading fxml file
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        StackPane root = new StackPane();
        primaryStage.setTitle("JCENA table view");

        buildTableScene(root);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Constructs the elements of the table view
     * @param root the root node of the table scene being built
     */
    private void buildTableScene(StackPane root) {
        TableView table = new TableView<>();
        ObservableList<DataRow> exampleData = FXCollections.observableArrayList(new DataRow("Car", "High", 6));
        exampleData.add(new DataRow("Car", "High", 7));
        TableColumn column_1 = new TableColumn("Vehicle");
        column_1.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
        TableColumn column_2 = new TableColumn("Severity");
        column_2.setCellValueFactory(new PropertyValueFactory<>("severity"));
        TableColumn column_3 = new TableColumn("People");
        column_3.setCellValueFactory(new PropertyValueFactory<>("people"));

        table.setItems(exampleData);
        table.getColumns().addAll(column_1, column_2, column_3);
        root.getChildren().add(table);
    }


    /**
     * Arbitrary functionality to load FXML application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

