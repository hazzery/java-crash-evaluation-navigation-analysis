package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.StringUtils;
import seng202.team2.models.Crash;
import seng202.team2.models.Crashes;
import seng202.team2.models.TableAttribute;

/**
 * Class to demo the table view for the application
 *
 * @author Isaac Ure
 * @author Findlay Royds
 */
public class TableViewController {
    @FXML
    private TableView<Crash> tableView;
    @FXML
    private Pagination pagination;
    //    private final int rowsPerPage = 100;
    private boolean hasBeenBuilt = false; // no point building the table twice.

    /**
     * Gets a displayable string representation of the enum value
     * e.g. SERIOUS_INJURIES -> "SeriousInjuries"
     * @return Nicely formatted string of enum value
     */
    private String toGetter(String text) {
        String[] words =  text.split("_");
        StringBuilder output = new StringBuilder();
        for (String word : words) {
            output.append(StringUtils.capitalize(word.toLowerCase()));
        }
        return output.toString();
    }

    /**
     * Constructs the elements of the table view/pagination
     */
    private void buildTableScene() {
        tableView.setItems(Crashes.getCrashes());

        for (TableAttribute tableAttribute : TableAttribute.values()) {
            TableColumn<Crash, String> column = new TableColumn<>(tableAttribute.displayValue());
            column.setCellValueFactory(new PropertyValueFactory<>(toGetter(tableAttribute.name())));
            tableView.getColumns().add(column);
        }

        updateCrashes();
        hasBeenBuilt = true;

        // Pagination is implemented with its own page,
        // so it sets the content of its page to empty while updating the table
        pagination.setPageFactory(pageIndex -> {
            updateCrashes();
            return new Label("");
        });
    }


    /**
     * Initialises the tableview
     */
    @FXML
    void initialize() {
        if (!hasBeenBuilt) {
            buildTableScene();
        }
    }

    /**
     * Updates crashes; displays first rowsPerPage
     */
    public void updateCrashes() {
//        tableView.getItems().clear();
//        pagination.setPageCount(Crashes.getCrashes().size()/rowsPerPage + 1);
//
//        for (int i = pagination.getCurrentPageIndex() * rowsPerPage; i < pagination.getCurrentPageIndex() * rowsPerPage + rowsPerPage && i < Crashes.getCrashes().size(); i++) {
//            Crash crash = Crashes.getCrashes().get(i);
//            tableCrashData.add(new DataRow(
//                    crash.severity().displayValue(),
//                    crash.fatalities(),
//                    crash.vehiclesInvolved(),
//                    toDisplayText(crash.roadName1()),
//                    toDisplayText(crash.roadName2()),
//                    crash.region().displayValue(),
//                    crash.seriousInjuries(),
//                    crash.minorInjuries(),
//                    crash.weather().displayValue(),
//                    crash.lighting().displayValue(),
//                    crash.year())
//            );
//        }
//
//        tableView.setItems(tableCrashData);
    }
}

