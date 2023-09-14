package seng202.team2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team2.models.Crash;
import seng202.team2.services.CsvReader;

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
    private boolean hasBeenBuilt = false; // no point building the table twice.

    private String toDisplayText(String text) {
        String lowered = text.toLowerCase().replace("_", " ");
        return lowered.substring(0, 1).toUpperCase() + lowered.substring(1);
    }

    /**
     * Constructs the elements of the table view
     */
    private void buildTableScene() throws FileNotFoundException {
        ObservableList<DataRow> tableCrashData = FXCollections.observableArrayList();

        String[] columnKeys = {"Severity", "Fatalities", "NumberOfVehiclesInvolved", "RoadName1", "RoadName2",
                "Region", "SeriousInjuries", "MinorInjuries", "Weather", "Lighting", "Year"};
        String[] columnHeaders = {"Severity", "Fatalities", "Number of Vehicles Involved", "Road 1", "Road 2",
                "Region", "Serious Injuries", "Minor Injuries", "Weather", "Lighting", "Year"};
        for (int i = 0; i < columnKeys.length; i++) {
            TableColumn<DataRow, String> column = new TableColumn<>(columnHeaders[i]);
            column.setCellValueFactory(new PropertyValueFactory<>(columnKeys[i]));
            column.setReorderable(false);
            tableView.getColumns().add(column);
        }

        // Read CSV file and populate table rows.
        CsvReader csvReader = new CsvReader("src/main/resources/crash_data.csv");
        Crash[] crashes = csvReader.readLines(10000);

        for (Crash crash: crashes) {
            tableCrashData.add(new DataRow(
                    toDisplayText(crash.severity().toString()),
                    crash.fatalities(),
                    crash.vehicles().length,
                    toDisplayText(crash.roadName1()),
                    toDisplayText(crash.roadName2()),
                    crash.region(),
                    crash.seriousInjuries(),
                    crash.minorInjuries(),
                    toDisplayText(crash.weather().toString()),
                    toDisplayText(crash.lighting().toString()),
                    crash.year())
            );
        }

        tableView.setItems(tableCrashData);
        hasBeenBuilt = true;
    }

    /**
     * Inits the tableview
     */
    void init() throws FileNotFoundException {
        if (!hasBeenBuilt) {
            buildTableScene();
        }
    }
}

