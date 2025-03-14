package seng202.team2.controller;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import java.util.concurrent.CompletableFuture;

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
     * preparing the filter dropdown menus,
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

    /**
     * Checks which dropdown items are selected, puts them in a list and assigns to openingState.
     *
     * @param event Event caused by user interacting with a dropdown menu
     */
    private void captureDropdownState(Event event) {
        MenuButton menuButton = (MenuButton) event.getSource();
        openingState = menuButton.getItems().stream()
                .filter(item -> ((CheckBox) ((CustomMenuItem) item).getContent()).isSelected())
                .map(MenuItem::getId)
                .toList();
    }

    /**
     * Checks which dropdown items are selected, puts them in a list and assigns to closingState.
     * Compares with openingState and only filters the table if there is a change in items selected.
     *
     * @param event Event caused by user interacting with a dropdown menu
     */
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
        bus.setTooltip(MainController.makeTooltip("Toggle: include crashes involving buses"));
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
     * Build a query builder based on the filters selected by the user
     *
     * @return the query builder used to query the database
     */
    private QueryBuilder buildQuery() {
        QueryBuilder queryBuilder = new QueryBuilder();
        List<DbAttributes> vehiclesToQuery = new ArrayList<>();

        for (ToggleButton button : List.of(pedestrian, bicycle, car, bus)) {
            button.getStyleClass().remove("used");
            if (button.isSelected()) {
                DbAttributes vehicle = buttonIdToVehicle.get(button.getId());
                vehiclesToQuery.add(vehicle);
                if (!button.getStyleClass().contains("used")) {
                    button.getStyleClass().add("used");
                }
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
        
        
        regions.getStyleClass().remove("used");
        if (!selectedRegions.isEmpty()) {
            if (!regions.getStyleClass().contains("used")) {
                regions.getStyleClass().add("used");
            }
        }
        
        severities.getStyleClass().remove("used");
        if (!selectedSeverities.isEmpty()) {
            if (!severities.getStyleClass().contains("used")) {
                severities.getStyleClass().add("used");
            }
        }
        
        return queryBuilder;
    }

    /**
     * Applies the selected filters to the table and map views.
     * This query is then run and the view is updated to show the new data.
     */
    public void filterTable() {
        // Disable apply button when a filter is being applied already
        QueryBuilder queryBuilder = buildQuery();

        // Query the database in a separate thread and then update the table and map once complete
        CompletableFuture.runAsync(() -> {
            Crashes.setQuery(queryBuilder);
            setButtonsDisabled(true);
            mainController.getLoadingScreen().show("Filtering crash data...");

            Platform.runLater(() -> {
                mainController.updateViews();
                mainController.getLoadingScreen().hide();
                setButtonsDisabled(false);
            });
        });
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
}


