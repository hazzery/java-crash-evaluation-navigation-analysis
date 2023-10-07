package seng202.team2.models;

import org.apache.commons.lang3.StringUtils;

public enum TableAttribute {
    YEAR,
    FATALITIES,
    SERIOUS_INJURIES,
    MINOR_INJURIES,
    ROAD_NAME_1,
    ROAD_NAME_2,
    REGION,
    SEVERITY,
    WEATHER,
    LIGHTING,
    NUMBER_OF_VEHICLES_INVOLVED;

    /**
     * Gets a displayable string representation of the enum value
     * e.g: NUMBER_OF_VEHICLES_INVOLVED -> "Number of vehicles involved"
     * @return Nicely formatted string of enum value
     */
    public String displayValue() {
        String display = name().toLowerCase().replaceAll("_", " ");
        return StringUtils.capitalize(display);
    }
}
