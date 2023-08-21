package seng202.team2.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class to demo the table view for the application
 */
public class TableViewController {
    @FXML
    private TableView<DataRow> salesTableView;

    private Stage stage;

    /**
     * builds a demo table without fxml
     * @param primaryStage the current stage
     * @throws IOException if there is an issue loading fxml file
     */
    /*@Override
    public void start(Stage primaryStage) throws IOException {

        StackPane root = new StackPane();
        primaryStage.setTitle("JCENA table view");

        //buildTableScene(root);
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.show();

    }*/

    /**
     * Constructs the elements of the table view
     * //@param root the root node of the table scene being built
     */
    private void buildTableScene() {//StackPane root) {
        //TableView table = new TableView<>();
        ObservableList<DataRow> exampleData = FXCollections.observableArrayList(new DataRow("Car", "High", 6));

        exampleData.add(new DataRow("Car", "High", 7));
        TableColumn<DataRow, String> column_1 = new TableColumn<>("Vehicle");
        column_1.setCellValueFactory(new PropertyValueFactory<>("vehicle"));
        TableColumn<DataRow, String> column_2 = new TableColumn<>("Severity");
        column_2.setCellValueFactory(new PropertyValueFactory<>("severity"));
        TableColumn<DataRow, String> column_3 = new TableColumn<>("People");
        column_3.setCellValueFactory(new PropertyValueFactory<>("people"));

        salesTableView.getColumns().add(column_1);
        salesTableView.getColumns().add(column_2);
        salesTableView.getColumns().add(column_3);
        salesTableView.setItems(exampleData);
        //root.getChildren().add(table);
    }

    /**
     * Inits the tableview
     * @param stage
     */
    void init(Stage stage) {
        this.stage = stage;

        buildTableScene();
    }


    /**
     * Arbitrary functionality to load FXML application
     * @param args command line arguments
     */
    /*public static void main(String[] args) {
        launch(args);
    }*/
}

