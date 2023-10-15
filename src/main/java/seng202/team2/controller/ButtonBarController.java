package seng202.team2.controller;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.RangeSlider;
import seng202.team2.database.DbAttributes;
import seng202.team2.database.QueryBuilder;
import seng202.team2.models.Crashes;
import seng202.team2.models.Region;
import seng202.team2.models.Severity;

import java.util.*;

/**
 * JavaFX controller for the crash filter toolbar.
 *
 * @author Isaac Ure
 * @author Ben Moore
 */
public class ButtonBarController {
    @FXML
    private MenuButton regions;
    @FXML
    private MenuButton severities;
    @FXML
    private RangeSlider yearSelect;
    @FXML
    private ToggleButton pedestrian;
    @FXML
    private ToggleButton bicycle;
    @FXML
    private ToggleButton car;
    @FXML
    private ToggleButton bus;
    @FXML
    private Integer MIN_YEAR;
    @FXML
    private Integer MAX_YEAR;
    @FXML
    private Text yearSelectLeftLabel;
    @FXML
    private Text yearSelectRightLabel;

    /**
     * Map used to convert the IDs of a button into their respective enum values
     */
    private static final Map<String, DbAttributes> buttonIdToVehicle = new HashMap<>() {{
        put("pedestrian", DbAttributes.PEDESTRIAN);
        put("bicycle", DbAttributes.BICYCLE);
        put("car", DbAttributes.CAR_OR_STATION_WAGON);
        put("bus", DbAttributes.BUS);
    }};

    private List<String> openingState;

    private static final Logger log = LogManager.getLogger(ButtonBarController.class);
    private MainController mainController;

    /**
     * Initialises the button bar by setting the button icons,
     * preparing the filter drop down menus,
     * setting up the tool tips,
     * and initialising the year slider
     */
    @FXML
    void initialize() {
        setIcons();
        setSeverities();
        setRegions();
        setTooltips();
        initYearSlider();
    }

    /**
     * Gives the button bar access to the main controller.
     * This allows for the button bar to update the table and map views.
     *
     * @param mainController The main JavaFX controller.
     */
    public void giveMainControl(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Set the icons on the vehicle filter buttons
     */
    private void setIcons() {
        Image personIMG = null;
        Image cyclistIMG = null;
        Image carIMG = null;
        Image busIMG = null;
        try {
            personIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/person.png")), 20, 20, true, false);
            cyclistIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/cyclist.png")), 20, 20, true, false);
            carIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/car.png")), 20, 20, true, false);
            busIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/bus.png")), 20, 20, true, false);
        } catch (NullPointerException exception) {
            log.error("Error loading vehicle icons");
            log.error(exception);
        }
        pedestrian.setGraphic(new ImageView(personIMG));
        bicycle.setGraphic(new ImageView(cyclistIMG));
        car.setGraphic(new ImageView(carIMG));
        bus.setGraphic(new ImageView(busIMG));
    }

    /**
     * Set the severity checkboxes in the severities drop-down from the pre-defined severities list
     */
    private void setSeverities() {
        for (Severity severity : Severity.severities()) {
            CheckBox checkBox = new CheckBox(severity.displayValue());
            CustomMenuItem severityItem = new CustomMenuItem(checkBox, false);
            severityItem.setId(severity.name());
            severities.getItems().add(severityItem);
        }

        severities.setOnShowing(this::captureDropdownState);
        severities.setOnHiding(this::applyDropdownFilter);
    }

    /**
     * Sets the regions checkboxes in the regions drop-down from the pre-defined region list
     */
    private void setRegions() {
        for (Region region : Region.regions()) {
            CheckBox checkBox = new CheckBox(region.displayValue());
            CustomMenuItem regionItem = new CustomMenuItem(checkBox, false);
            regionItem.setId(region.name());
            regions.getItems().add(regionItem);
        }

        regions.setOnShowing(this::captureDropdownState);
        regions.setOnHiding(this::applyDropdownFilter);
    }

    private void captureDropdownState(Event event) {
        MenuButton menuButton = (MenuButton) event.getSource();
        openingState = menuButton.getItems().stream()
                .filter(item -> ((CheckBox) ((CustomMenuItem) item).getContent()).isSelected())
                .map(MenuItem::getId)
                .toList();
    }

    private void applyDropdownFilter(Event event) {
        MenuButton menuButton = (MenuButton) event.getSource();
        List<String> closingState = menuButton.getItems().stream()
                .filter(item -> ((CheckBox) ((CustomMenuItem) item).getContent()).isSelected())
                .map(MenuItem::getId)
                .toList();

        if (!openingState.equals(closingState)) {
            filterTable();
        }
    }

    /**
     * Initialises the year range slider:
     * Places the thumbs of the range slider at the min and max values.
     * Creates listeners for both thumbs on the year selector to update the values of the slider labels.
     * Creates listeners for both thumbs to query when they're let go.
     */
    private void initYearSlider() {
        yearSelect.setLowValue(MIN_YEAR);
        yearSelect.setHighValue(MAX_YEAR);

        yearSelectLeftLabel.setWrappingWidth(30);
        yearSelectRightLabel.setWrappingWidth(30);

        yearSelect.lowValueProperty().addListener((observable, oldValue, newValue) ->
                yearSelectLeftLabel.setText(Integer.toString((int) yearSelect.getLowValue())));
        yearSelect.highValueProperty().addListener((observable, oldValue, newValue) ->
                yearSelectRightLabel.setText(Integer.toString((int) yearSelect.getHighValue())));

        yearSelect.lowValueChangingProperty().addListener(this::sliderThumbChange);
        yearSelect.highValueChangingProperty().addListener(this::sliderThumbChange);
    }

    /**
     * Sets tooltips for all the buttons on the filter bar
     * using the helper function in MainController
     */
    private void setTooltips() {
        pedestrian.setTooltip(MainController.makeTooltip("Toggle: include crashes involving pedestrians"));
        bicycle.setTooltip(MainController.makeTooltip("Toggle: include crashes involving bicycles"));
        car.setTooltip(MainController.makeTooltip("Toggle: include crashes involving cars"));
        bus.setTooltip(MainController.makeTooltip("Toggle: include crashes involving heavy vehicles"));
        severities.setTooltip(MainController.makeTooltip("Dropdown: Limit crashes to specific severities"));
        regions.setTooltip(MainController.makeTooltip("Dropdown: Limit crashes to specific regions"));
        yearSelect.setTooltip(MainController.makeTooltip("Slider: Limit crashes to specific range of years"));
    }

    /**
     * Event handler for change in the year slider
     *
     * @param observable Boolean property describing whether the slider thumb is changing
     * @param oldValue   Previous value of the boolean property
     * @param newValue   New value of the boolean property, true if the thumb is changing, false if it has stopped
     */
    private void sliderThumbChange(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (!newValue) {
            filterTable();
        }
    }

    /**
     * Build a query builder based on the filter selected in the top bar
     *
     * @return the query builder used to query the database
     */
    private QueryBuilder buildQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<DbAttributes> vehiclesToQuery = new ArrayList<>();

        for (ToggleButton button : List.of(pedestrian, bicycle, car, bus)) {
            if (button.isSelected()) {
                DbAttributes vehicle = buttonIdToVehicle.get(button.getId());
                vehiclesToQuery.add(vehicle);
            }
        }
        queryBuilder.orVehicle(vehiclesToQuery);

        List<String> selectedSeverities = severities.getItems().stream()
                .filter(item -> ((CheckBox) ((CustomMenuItem) item).getContent()).isSelected())
                .map(MenuItem::getId)
                .toList();

        queryBuilder.orString(selectedSeverities, DbAttributes.SEVERITY);

        int minYear = (int) yearSelect.getLowValue();
        int maxYear = (int) yearSelect.getHighValue();
        if (minYear != yearSelect.getMin() || maxYear != yearSelect.getMax()) {
            queryBuilder.betweenValues(minYear, maxYear, DbAttributes.YEAR);
        }

        List<String> selectedRegions = regions.getItems().stream()
                .filter(item -> ((CheckBox) ((CustomMenuItem) item).getContent()).isSelected())
                .map(MenuItem::getId)
                .toList();

        queryBuilder.orString(selectedRegions, DbAttributes.REGION);

        return queryBuilder;
    }

    /**
     * Builds a query based on which filters are selected:
     * Checks which toggle buttons are selected and adds them to the orVehicle method of QueryBuilder
     * Then checks which severities are selected and adds them to the orString method of QueryBuilder
     * Then checks the year range defined by the slider and runs the betweenValues method if the slider has been changed
     * Finally checks the selected regions and queries them using another QueryBuilder orString.
     * This query is then run and the view is updated to show the new data.
     */
    public void filterTable() {
        // Disable apply button when a filter is being applied already
        QueryBuilder queryBuilder = buildQuery();

        // Query the database in a separate thread and then update the table and map once complete
        runAfter(() -> Crashes.setQuery(queryBuilder), () -> mainController.updateViews());
    }

    /**
     * Disables all buttons on the filter bar
     *
     * @param disabled Whether the buttons should be disabled or not
     */
    private void setButtonsDisabled(boolean disabled) {
        pedestrian.setDisable(disabled);
        bicycle.setDisable(disabled);
        car.setDisable(disabled);
        bus.setDisable(disabled);
        severities.setDisable(disabled);
        regions.setDisable(disabled);
        yearSelect.setDisable(disabled);
    }

    /**
     * Runs a task on a different thread and then continues with the after runnable once execution is complete.
     * Allows a task to be run without interrupting the main application thread, keeping the application responsive.
     *
     * @param before The runnable to run on a new thread
     * @param after  The runnable to run on the main thread after before has finished executing
     */
    private void runAfter(Runnable before, Runnable after) {
        // Run before task in separate thread to allow JavaFX application to update
        Task<Void> beforeTask = new Task<>() {
            @Override
            protected Void call() {
                before.run();
                return null;
            }
        };
        // After the before task has completed, run the after task in the main JavaFX thread
        beforeTask.setOnSucceeded(event -> {
            after.run();
            mainController.getLoadingScreen().hide();
            setButtonsDisabled(false);
        });

        new Thread(beforeTask).start();
        setButtonsDisabled(true);
        mainController.getLoadingScreen().show("Filtering crash data...");
    }
}


