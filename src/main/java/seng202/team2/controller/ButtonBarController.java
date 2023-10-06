package seng202.team2.controller;

import javafx.scene.text.Text;
import seng202.team2.database.DbAttributes;
import seng202.team2.database.QueryBuilder;
import seng202.team2.models.Crashes;
import seng202.team2.models.Region;
import seng202.team2.models.Severity;

import org.controlsfx.control.RangeSlider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    private static final Map<String, DbAttributes> buttonIdToVehicle = new HashMap<>() {{
        put("pedestrian", DbAttributes.PEDESTRIAN);
        put("bicycle", DbAttributes.BICYCLE);
        put("car", DbAttributes.CAR_OR_STATION_WAGON);
        put("bus", DbAttributes.BUS);
    }};

    private static final Logger log = LogManager.getLogger(ButtonBarController.class);
    private MainController mainController;

    /**
     * Set the icons on the vehicle filter buttons
     */
    private void setIcons() {
        Image personIMG = null;
        Image cyclistIMG = null;
        Image carIMG = null;
        Image busIMG = null;
        try {
            personIMG = new Image(getClass().getResourceAsStream("/icons/person.png"), 20, 20, true, false);
            cyclistIMG = new Image(getClass().getResourceAsStream("/icons/cyclist.png"), 20, 20, true, false);
            carIMG = new Image(getClass().getResourceAsStream("/icons/car.png"), 20, 20, true, false);
            busIMG = new Image(getClass().getResourceAsStream("/icons/bus.png"), 20, 20, true, false);
        } catch (NullPointerException e) {
            e.printStackTrace();
            personIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/person.png")), 20, 20, true, false);
            cyclistIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/cyclist.png")), 20, 20, true, false);
            carIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/car.png")), 20, 20, true, false);
            busIMG = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/bus.png")), 20, 20, true, false);
        }
        pedestrian.setGraphic(new ImageView(personIMG));
        bicycle.setGraphic(new ImageView(cyclistIMG));
        car.setGraphic(new ImageView(carIMG));
        bus.setGraphic(new ImageView(busIMG));
    }

    /**
     * Set the severity values in the severities drop-down
     */
    private void setSeverityValues() {
        for (Severity severity : Severity.severities()) {
            CustomMenuItem severityItem = new CustomMenuItem(new CheckBox(severity.displayValue()), false);
            severityItem.setId(severity.name());
            severities.getItems().add(severityItem);
        }
    }

    /**
     * Sets the regions in the regions drop-down
     */
    private void setRegions() {
        for (Region region : Region.regions()) {
            CustomMenuItem regionItem = new CustomMenuItem(new CheckBox(region.displayValue()), false);
            regionItem.setId(region.name());
            regions.getItems().add(regionItem);
        }
    }

    /**
     * Default behaviour of rangeSlider is not working correctly,
     * this method sets the default values
     */
    private void setRangeSliderValues() {
        yearSelect.setLowValue(MIN_YEAR);
        yearSelect.setHighValue(MAX_YEAR);
    }

    /**
     * Builds a query based on which filters are selected and updates the pool.
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
     * Gives the button bar access to the main controller.
     * This allows for the button bar to update the table and map views.
     * @param mainController The main JavaFX controller.
     */
    public void giveMainControl(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Initialises the button bar.
     */
    void init() {
        setIcons();
        setSeverityValues();
        setRegions();
        setRangeSliderValues();
        initYearSelectListeners();
    }
}


