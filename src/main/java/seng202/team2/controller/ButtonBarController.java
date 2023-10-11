package seng202.team2.controller;

import javafx.event.ActionEvent;
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
    public ButtonBar buttonBar;

    @FXML
    public MenuButton regions;

    @FXML
    private RangeSlider yearSelect;

    @FXML
    public MenuButton severities;

    @FXML
    public Button confirmSelection;

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

    private Boolean consumeAction;

    /**
     * Map used to convert the IDs of a button into their respective enum values
     */
    private static final Map<String, DbAttributes> buttonIdToVehicle = new HashMap<>() {{
        put("pedestrian", DbAttributes.PEDESTRIAN);
        put("bicycle", DbAttributes.BICYCLE);
        put("car", DbAttributes.CAR_OR_STATION_WAGON);
        put("bus", DbAttributes.BUS);
    }};

    private static final Logger log = LogManager.getLogger(ButtonBarController.class);
    private MainController mainController;

    /**
     * Set the icons on the vehicle filter buttons by using included images
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
     * Set the severity values in the severities drop-down from the pre-defined severities list
     */
    private void setSeverityValues() {
        for (Severity severity : Severity.severities()) {
            CustomMenuItem severityItem = new CustomMenuItem(new CheckBox(severity.displayValue()), false);
            severityItem.setId(severity.name());
            severityItem.setOnAction(this::notificationSeverity);
            severities.getItems().add(severityItem);
        }
        consumeAction = false;
    }

    /**
     * Sets the regions in the regions drop-down from the pre-defined region list
     */
    private void setRegions() {
        for (Region region : Region.regions()) {
            CustomMenuItem regionItem = new CustomMenuItem(new CheckBox(region.displayValue()), false);
            regionItem.setId(region.name());
            regionItem.setOnAction(this::notificationRegion);
            regions.getItems().add(regionItem);
        }
    }

    /**
     * Default behaviour of rangeSlider does not correctly set default values,
     * this method overrides the default values with the correct ones
     */
    private void setRangeSliderValues() {
        yearSelect.setLowValue(MIN_YEAR);
        yearSelect.setHighValue(MAX_YEAR);
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

        Crashes.setQuery(queryBuilder);

        mainController.updateViews();
    }

    /**
     * Creates listeners for both handles on the year selector.
     * These update the values of the slider labels to show the current values of the year filter.
     */
    private void initYearSelectListeners() {
        yearSelectLeftLabel.setWrappingWidth(30);
        yearSelectRightLabel.setWrappingWidth(30);

        yearSelect.lowValueProperty().addListener((observable, oldValue, newValue) ->
                        yearSelectLeftLabel.setText(Integer.toString((int) yearSelect.getLowValue())));
        yearSelect.highValueProperty().addListener((observable, oldValue, newValue) ->
                        yearSelectRightLabel.setText(Integer.toString((int) yearSelect.getHighValue())));
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
        confirmSelection.setTooltip(MainController.makeTooltip("Apply all the selected filters (May take time to load)"));
    }

    /**
     * A function to generate notifications for all the toggle
     * buttons in a compact manner.
     *
     * @param event An event representing some type of action
     */
    @FXML
    public void notificationToggle(ActionEvent event) {
        ToggleButton eventOrigin = (ToggleButton) event.getSource();
        if (!pedestrian.isSelected() & !bicycle.isSelected() & !car.isSelected() & !bus.isSelected()) {
            mainController.showNotification("Filtering crashes by all vehicle types");
            return;
        }
        switch (eventOrigin.getId()) {
            case "pedestrian" -> mainController.showNotification(pedestrian.isSelected() ?
                    "Crashes involving pedestrians have been added to the filter" : "Crashes involving pedestrians have been removed from the filter");
            case "bicycle" -> mainController.showNotification(bicycle.isSelected() ?
                    "Crashes involving bikes have been added to the filter" : "Crashes involving bikes have been removed from the filter");
            case "car" -> mainController.showNotification(car.isSelected() ?
                    "Crashes involving cars have been added to the filter" : "Crashes involving cars have been removed from the filter");
            case "bus" -> mainController.showNotification(bus.isSelected() ?
                    "Crashes involving heavy vehicles have been added to the filter" : "Crashes involving heavy vehicles have been removed from the filter");
        }

    }

    /**
     * Generates notifications on severity selections
     * gets called twice per action so every second one is ignored
     * uses functions in {@link Severity} to get nicely formatted strings
     *
     * @param event An event representing some type of action
     */
    public void notificationSeverity(ActionEvent event) {
        if (consumeAction) {
            consumeAction = false;
            return;
        }
        boolean anySelected = false;
        for (Object option : severities.getItems()) {
            if (((CheckBox) (((CustomMenuItem) option).getContent())).isSelected()) {
                anySelected = true;
            }
        }
        if (!anySelected) {
            mainController.showNotification("Filtering crashes by all severities");
            consumeAction = true;
            return;
        }
        CustomMenuItem customActionOrigin = (CustomMenuItem) event.getSource();
        CheckBox actionOrigin = ((CheckBox) customActionOrigin.getContent());
        Severity checkedSeverity = Severity.fromString(customActionOrigin.getId());
        String actionString = checkedSeverity.displayValue().toLowerCase();
        if (actionOrigin.isSelected()) {
            mainController.showNotification("Adding all " + actionString + " crashes to the filter.");
        } else {
            mainController.showNotification("Removing all " + actionString + " crashes from the filter.");
        }
        consumeAction = true;
    }


    /**
     * Generates notifications on region selections
     * gets called twice per action so every second one is ignored
     * uses functions in {@link Region} to get nicely formatted strings
     *
     * @param event An event representing some type of action
     */
    public void notificationRegion(ActionEvent event) {
        if (consumeAction) {
            consumeAction = false;
            return;
        }
        boolean anySelected = false;
        for (Object option : regions.getItems()) {
            if (((CheckBox) (((CustomMenuItem) option).getContent())).isSelected()) {
                anySelected = true;
            }
        }
        if (!anySelected) {
            mainController.showNotification("Filtering crashes by all regions");
            consumeAction = true;
            return;
        }
        CustomMenuItem customActionOrigin = (CustomMenuItem) event.getSource();
        CheckBox actionOrigin = ((CheckBox) customActionOrigin.getContent());
        Region checkedRegion = Region.fromString(customActionOrigin.getId());
        String actionString = checkedRegion.displayValue();
        if (actionOrigin.isSelected()) {
            mainController.showNotification("Adding all crashes in " + actionString + " to the filter");
        } else {
            mainController.showNotification("Removing all crashes in " + actionString + " from the filter.");
        }
        consumeAction = true;
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
     * Initialises the button bar by setting the button icons,
     * preparing the filter drop down menus,
     * setting up the tool tips,
     * and initialising the year slider
     */
    @FXML
    void initialize() {
        setIcons();
        setSeverityValues();
        setRegions();
        setRangeSliderValues();
        setTooltips();
        initYearSelectListeners();
    }
}


