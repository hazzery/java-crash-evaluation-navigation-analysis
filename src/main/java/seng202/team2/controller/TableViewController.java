package seng202.team2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.StringUtils;
import seng202.team2.models.Crash;
import seng202.team2.models.Crashes;

/**
 * Class to demo the table view for the application
 *
 * @author Isaac Ure
 * @author Findlay Royds
 */
public class TableViewController {
    @FXML
    private TableView<DataRow> tableView;
    ObservableList<DataRow> tableCrashData = FXCollections.observableArrayList();
    private boolean hasBeenBuilt = false; // no point building the table twice.

    /**
     * Gets a displayable string representation of the enum value
     * e.g: FATAL_CRASH -> "Fatal crash"
     * @return Nicely formatted string of enum value
     */
    private String toDisplayText(String text) {
        String lowered = text.toLowerCase().replace("_", " ");
        return StringUtils.capitalize(lowered);
    }

    /**
     * Constructs the elements of the table view
     */
    private void buildTableScene() {

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

        updateCrashes();
        hasBeenBuilt = true;
    }

    /**
     * Inits the tableview
     */
    void init() {
        if (!hasBeenBuilt) {
            buildTableScene();
        }
    }

    public void updateCrashes() {
        tableView.getItems().clear();

        for (Crash crash: Crashes.getCrashes()) {
            tableCrashData.add(new DataRow(
                    crash.severity().displayValue(),
                    crash.fatalities(),
                    crash.vehiclesInvolved(),
                    toDisplayText(crash.roadName1()),
                    toDisplayText(crash.roadName2()),
                    crash.region(),
                    crash.seriousInjuries(),
                    crash.minorInjuries(),
                    crash.weather().displayValue(),
                    crash.lighting().displayValue(),
                    crash.year())
            );
        }

        tableView.setItems(tableCrashData);
    }
}

