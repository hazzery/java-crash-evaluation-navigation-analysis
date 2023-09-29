package seng202.team2.controller;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controls the filter button bar of the table
 *
 * @author Isaac Ure
 * @author Ben Moore
 */

public class ButtonBarController {

    @FXML
    public ButtonBar buttonBar;

    @FXML
    public MenuButton Regions;

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

    private static final Map<String, DbAttributes> buttonIdToVehicle = new HashMap<>() {{
        put("pedestrian", DbAttributes.PEDESTRIAN);
        put("bicycle", DbAttributes.BICYCLE);
        put("car", DbAttributes.CAR_OR_STATION_WAGON);
        put("bus", DbAttributes.BUS);
    }};

    private static final Logger log = LogManager.getLogger(ButtonBarController.class);
    private MainController mainController;

    public void setIcons() {
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
        }
        pedestrian.setGraphic(new ImageView(personIMG));
        bicycle.setGraphic(new ImageView(cyclistIMG));
        car.setGraphic(new ImageView(carIMG));
        bus.setGraphic(new ImageView(busIMG));
    }

    /**
     * Sets the severity values in the severities drop-down
     */
    public void setSeverityValues() {
        for (Severity severity : Severity.severities()) {
            CheckMenuItem severityItem = new CheckMenuItem(severity.displayValue());
            severityItem.setId(severity.name());
            severities.getItems().add(severityItem);
        }
    }

    /**
     * Sets the regions in the regions drop-down
     */
    private void setRegions() {
        for (Region region : Region.regions()) {
            CheckMenuItem regionItem = new CheckMenuItem(region.displayValue());
            regionItem.setId(region.name());
            Regions.getItems().add(regionItem);
        }
    }

    /**
     * default behavior of rangeSlider is not working correctly,
     * this method sets the default values
     */
    private void setRangeSliderValues() {
        yearSelect.setLowValue(MIN_YEAR);
        yearSelect.setHighValue(MAX_YEAR);
    }

    void init() {
        setIcons();
        setSeverityValues();
        setRegions();
        setRangeSliderValues();
    }

    public void filterTable() {
        QueryBuilder queryBuilder = new QueryBuilder();

        for (ToggleButton button : List.of(pedestrian, bicycle, car, bus)) {
            if (button.isSelected()) {
                DbAttributes vehicle = buttonIdToVehicle.get(button.getId());
                queryBuilder.greaterThan(0, vehicle);
            }
        }

        List<String> selectedSeverities = severities.getItems().stream()
                .filter(item -> ((CheckMenuItem) item).isSelected())
                .map(MenuItem::getId)
                .toList();

        queryBuilder.orString(selectedSeverities, DbAttributes.SEVERITY);

        int minYear = (int) yearSelect.getLowValue();
        int maxYear = (int) yearSelect.getHighValue();
        if (minYear != yearSelect.getMin() || maxYear != yearSelect.getMax()) {
            queryBuilder.betweenValues(minYear, maxYear, DbAttributes.YEAR);
        }

        List<String> selectedRegions = Regions.getItems().stream()
                .filter(item -> ((CheckMenuItem) item).isSelected())
                .map(MenuItem::getId)
                .toList();

        queryBuilder.orString(selectedRegions, DbAttributes.REGION);

        Crashes.setQuery(queryBuilder);
        mainController.updateViews();
    }

    public void giveMainControl(MainController mainController) {
        this.mainController = mainController;
    }
}


