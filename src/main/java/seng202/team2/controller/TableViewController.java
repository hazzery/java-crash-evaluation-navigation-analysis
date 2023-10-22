package seng202.team2.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import seng202.team2.models.*;

import java.util.List;

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
    private final int ROWS_PER_PAGE = 1000;

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
            column.setSortable(false);
            tableView.getColumns().add(column);
        }

        pagination.setPageFactory(this::setPage);
        updateCrashes();
    }

    /**
     * Sets the tableview page.
     * This callback is executed every time the user presses a page button on the pagination toolbar.
     *
     * @param pageIndex The index of the page to show.
     * @return A JavaFX Node. This node is not used and only exists to please JavaFX.
     */
    private Node setPage(int pageIndex) {
        int indexOfFirst = pageIndex * ROWS_PER_PAGE;
        int indexOfLast = Math.min(indexOfFirst + ROWS_PER_PAGE, Crashes.getCrashIds().size());
        List<Integer> ids =  Crashes.getCrashIds().subList(indexOfFirst, indexOfLast);
        tableView.setItems(Crashes.getFromIds(ids));
        return new Label();
    }

    /**
     * Updates the page count to reflect the new number of results for the last query
     */
    public void updateCrashes() {
        setPage(0);
        pagination.setPageCount(Crashes.getCrashIds().size() / ROWS_PER_PAGE + 1);
    }
}
