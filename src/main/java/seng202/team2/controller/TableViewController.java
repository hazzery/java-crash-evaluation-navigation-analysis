package seng202.team2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.util.*;
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
    @FXML
    private Pagination pagination;
    private int rowsPerPage = 100;
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
     * Constructs the elements of the table view/pagination
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
        
        pagination.setStyle("-fx-border-color:red;");

        /**
         * Pagination is implemented with its own page, so it sets the content of its page to empty while updating table
         */
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                updateCrashes();
                return new Label("");
            }
        });
    }


    /**
     * Inits the tableview
     */
    void init() {
        if (!hasBeenBuilt) {
            buildTableScene();
        }
    }

    /**
     * Updates crashes; displays first rowsPerPage
     */
    public void updateCrashes() {
        tableView.getItems().clear();
        pagination.setPageCount(Crashes.getCrashes().size()/rowsPerPage + 1);

        for (int i = pagination.getCurrentPageIndex() * rowsPerPage; i < pagination.getCurrentPageIndex() * rowsPerPage + rowsPerPage && i < Crashes.getCrashes().size(); i++) {
            Crash crash = Crashes.getCrashes().get(i);
            tableCrashData.add(new DataRow(
                    crash.severity().displayValue(),
                    crash.fatalities(),
                    crash.vehiclesInvolved(),
                    toDisplayText(crash.roadName1()),
                    toDisplayText(crash.roadName2()),
                    crash.region().displayValue(),
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

