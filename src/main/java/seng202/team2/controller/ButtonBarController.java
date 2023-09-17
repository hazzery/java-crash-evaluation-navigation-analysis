package seng202.team2.controller;

import seng202.team2.database.DbAttributes;
import seng202.team2.database.QueryBuilder;
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
    private Slider selectedYear;

    @FXML
    private MenuButton regionSelect;

    @FXML
    private RadioMenuItem nonInjury;

    @FXML
    private RadioMenuItem minorInjury;

    @FXML
    private RadioMenuItem seriousInjury;

    @FXML
    private RadioMenuItem fatal;

    @FXML
    private RadioMenuItem unknownSeverity;

    private static final Map<String, DbAttributes> buttonIdToVehicle = new HashMap<>() {{
        put("pedestrian", DbAttributes.PEDESTRIAN);
        put("bicycle", DbAttributes.BICYCLE);
        put("car", DbAttributes.CAR_OR_STATION_WAGON);
        put("bus", DbAttributes.BUS);
    }};

    private static final Map<String, String> buttonIdToSeverity = new HashMap<>() {{
        put("nonInjury", "NON_INJURY_CRASH");
        put("minorInjury", "MINOR_CRASH");
        put("seriousInjury", "SERIOUS_CRASH");
        put("fatal", "FATAL_CRASH");
        put("unknownSeverity", "UNKNOWN");
    }};

    private static final Logger log = LogManager.getLogger(ButtonBarController.class);

    public void setIcons() {
        Image personIMG = null;
        Image cyclistIMG = null;
        Image carIMG = null;
        Image busIMG = null;
        try {
            personIMG = new Image(getClass().getResourceAsStream("/icons/person.png"),20,20, true, false);
            cyclistIMG = new Image(getClass().getResourceAsStream("/icons/cyclist.png"), 20 ,20, true, false);
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

    public void setSeverityValues() {
        nonInjury.setText(Severity.toDisplayValue(0));
        minorInjury.setText(Severity.toDisplayValue(1));
        seriousInjury.setText(Severity.toDisplayValue(2));
        fatal.setText(Severity.toDisplayValue(3));
        unknownSeverity.setText(Severity.toDisplayValue(4));
    }


    void init() {
        setIcons();
        setSeverityValues();
        regionSelect.setDisable(true);
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
                .filter(item -> ((RadioMenuItem) item).isSelected())
                .map(MenuItem::getId)
                .map(buttonIdToSeverity::get)
                .toList();

        queryBuilder.orString(selectedSeverities, DbAttributes.SEVERITY)
                    .betweenValues((int) yearSelect.getLowValue(), (int) yearSelect.getHighValue(), DbAttributes.YEAR);

        log.info(queryBuilder.getQuery());
    }
}


