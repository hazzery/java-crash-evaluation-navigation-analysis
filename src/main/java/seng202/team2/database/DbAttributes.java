package seng202.team2.database;

/**
 * Enum listing all columns of the crashes table in the database, in order.
 *
 * @author Harrison Parkes
 */
public enum DbAttributes {
    ID,
    YEAR,
    FATALITIES,
    SERIOUS_INJURIES,
    MINOR_INJURIES,
    LATITUDE,
    LONGITUDE,
    ROAD_NAME_1,
    ROAD_NAME_2,
    REGION,
    WEATHER,
    LIGHTING,
    SEVERITY,
    CAR_OR_STATION_WAGON,
    VAN_OR_UTE,
    SUV,
    TRUCK,
    BICYCLE,
    BUS,
    SCHOOL_BUS,
    MOPED,
    MOTOR_CYCLE,
    PEDESTRIAN,
    TAXI,
    TRAIN,
    OTHER;

    /**
     * Gets the column within the database this attribute resides.
     *
     * @return The attribute's column in the database.
     */
    public int dbColumn() {
        return ordinal() + 1;
    }

    /**
     * Gets the name of the column within the database this attribute resides.
     * @return The attribute's column name in the database.
     */
    public String dbColumnName() {
        return name().toLowerCase();
    }
}
