package seng202.team2.models;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public enum Region {
    AUCKLAND,
    NORTHLAND,
    WAIKATO,
    BAY_OF_PLENTY,
    HAWKES_BAY,
    WELLINGTON,
    OTAGO,
    MANAWATŪ_WHANGANUI,
    TARANAKI,
    CANTERBURY,
    GISBORNE,
    SOUTHLAND,
    WEST_COAST,
    NELSON,
    TASMAN,
    MARLBOROUGH,
    UNKNOWN;

    /**
     * Converts a string to a Region enum value
     * @param name The string to convert
     * @return The Region enum value
     */
    public static Region fromString(String name) {
        name = name.toUpperCase().replace(" ", "_").replace("-", "_");
        name = name.replace("_REGION", "").replace("'", "");
        try {
            return Region.valueOf(name);
        } catch (IllegalArgumentException e) {
            return Region.UNKNOWN;
        }
    }

    /**
     * Gets a displayable string representation of the enum value
     * e.g: BAY_OF_PLENTY -> "Bay Of Plenty"
     * @return Nicely formatted string of enum value
     */
    public String displayValue() {
        String display = name().toLowerCase().replaceAll("_", " ");
        return StringUtils.capitalize(display);
    }

    /**
     * Gets a list of all the regions
     * @return All regions minus UNKNOWN
     */
    public static List<Region> regions() {
        return List.of(
                AUCKLAND,
                NORTHLAND,
                WAIKATO,
                BAY_OF_PLENTY,
                HAWKES_BAY,
                WELLINGTON,
                OTAGO,
                MANAWATŪ_WHANGANUI,
                TARANAKI,
                CANTERBURY,
                GISBORNE,
                SOUTHLAND,
                WEST_COAST,
                NELSON,
                TASMAN,
                MARLBOROUGH
        );
    }
}
