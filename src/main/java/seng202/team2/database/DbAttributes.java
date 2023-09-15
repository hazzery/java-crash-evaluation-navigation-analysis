package seng202.team2.database;

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

    public int dbColumn() {
        return ordinal() + 1;
    }
}
