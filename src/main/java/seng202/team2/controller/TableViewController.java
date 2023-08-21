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

    private String toDisplayText(String text) {
        String lowered = text.toLowerCase().replace("_", " ");
        return lowered.substring(0, 1).toUpperCase() + lowered.substring(1);
    }

    /**
     * Constructs the elements of the table view
     */
    private void buildTableScene() throws FileNotFoundException {
        ObservableList<DataRow> tableCrashData = FXCollections.observableArrayList();

        String[] columnKeys = {"Severity", "Fatalities", "NumberOfVehiclesInvolved", "Weather", "Lighting", "Year"};
        String[] columnHeaders = {"Severity", "Fatalities", "Number of Vehicles Involved", "Weather", "Lighting", "Year"};
        for (int i = 0; i < columnKeys.length; i++) {
            TableColumn<DataRow, String> column = new TableColumn<>(columnHeaders[i]);
            column.setCellValueFactory(new PropertyValueFactory<>(columnKeys[i]));
            tableView.getColumns().add(column);
        }

        // Read CSV file and populate table rows.
        CSVReader csvReader = new CSVReader("src/main/resources/crash_data.csv");
        Crash[] crashes = csvReader.readLines(10000);

        for (Crash crash: crashes) {
            tableCrashData.add(new DataRow(
                    toDisplayText(crash.getSeverity().toString()),
                    crash.getFatalities(),
                    crash.getVehicles().length,
                    toDisplayText(crash.getWeather().toString()),
                    toDisplayText(crash.getLighting().toString()),
                    crash.getYear())
            );
        }

        tableView.setItems(tableCrashData);
    }

    /**
     * Inits the tableview
     */
    void init() throws FileNotFoundException {
        buildTableScene();
    }
}

