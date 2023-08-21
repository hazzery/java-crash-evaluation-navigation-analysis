package seng202.team2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team2.models.Crash;
import seng202.team2.services.CSVReader;

import java.io.FileNotFoundException;

/**
 * Class to demo the table view for the application
 *
 * @author Isaac Ure
 * @author Findlay Royds
 */
public class TableViewController {
    @FXML
    private TableView<DataRow> tableView;

    /**
     * Constructs the elements of the table view
     */
    private void buildTableScene() throws FileNotFoundException {
        ObservableList<DataRow> exampleData = FXCollections.observableArrayList();

        String[] columnItems = {"Severity", "Fatalities", "NumberOfVehiclesInvolved", "Weather", "Lighting", "Year"};
        for (String columnHeader: columnItems) {
            TableColumn<DataRow, String> column = new TableColumn<>(columnHeader);
            column.setCellValueFactory(new PropertyValueFactory<>(columnHeader));
            tableView.getColumns().add(column);
        }

        // Read CSV file and populate table rows.
        CSVReader csvReader = new CSVReader("src/main/resources/crash_data.csv");
        Crash[] crashes = csvReader.readLines(10000);

        for (Crash crash: crashes) {
            exampleData.add(new DataRow(
                    crash.getSeverity().toString(),
                    crash.getFatalities(),
                    crash.getVehicles().length,
                    crash.getWeather().toString(),
                    crash.getLighting().toString(),
                    crash.getYear())
            );
        }

        tableView.setItems(exampleData);
    }

    /**
     * Inits the tableview
     */
    void init() throws FileNotFoundException {
        buildTableScene();
    }
}

