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

    /**
     * Gets the suffix of the attributes getter within the crash class.
     * e.g. SERIOUS_INJURIES -> "SeriousInjuries"
     * @return string name of getter function for reflection
     */
    public String columnGetterName() {
        StringBuilder output = new StringBuilder();

        for (String word : name().split("_")) {
            output.append(StringUtils.capitalize(word.toLowerCase()));
        }

        return output.toString();
    }
}
