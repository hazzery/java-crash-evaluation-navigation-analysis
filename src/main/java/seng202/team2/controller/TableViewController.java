package seng202.team2.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team2.models.Crash;
import seng202.team2.models.Crashes;
import seng202.team2.models.TableAttribute;

/**
 * Table view controller implements the initialisation of the table view
 *
 * @author Isaac Ure
 * @author Findlay Royds
 * @author Harrison Parkes
 */
public class TableViewController {
    @FXML
    private TableView<Crash> tableView;
    @FXML
    private Pagination pagination;
    private final int rowsPerPage = 1000;

    /**
     * Initialises the tableview,
     * defining each of the columns,
     * telling the column where to fetch their data from,
     * and setting up pagination.
     */
    @FXML
    void initialize() {
        for (TableAttribute tableAttribute : TableAttribute.values()) {
            TableColumn<Crash, String> column = new TableColumn<>(tableAttribute.displayValue());
            column.setCellValueFactory(new PropertyValueFactory<>(tableAttribute.columnGetterName()));
            tableView.getColumns().add(column);
        }

        pagination.setPageFactory(this::setPage);
        updateCrashes();
    }

    /**
     * Sets the tableview page.
     * This callback is executed every time the user presses a page button on the pagination toolbar.
     * @param pageIndex The index of the page to show.
     * @return A JavaFX Node. This node is not used and only exists to please JavaFX.
     */
    private Node setPage(int pageIndex) {
        int indexOfFirst = pageIndex * rowsPerPage;
        int indexOfLast = Math.min(indexOfFirst + rowsPerPage, Crashes.getCrashes().size());
        tableView.setItems(FXCollections.observableArrayList(Crashes.getCrashes().subList(indexOfFirst, indexOfLast)));
        return new Label();
    }

    /**
     * Applies the new filter to the table view,
     * making sure to update the page count too
     */
    public void updateCrashes() {
        tableView.setItems(Crashes.getCrashes());
        pagination.setPageCount(Crashes.getCrashes().size() / rowsPerPage + 1);
    }
}

