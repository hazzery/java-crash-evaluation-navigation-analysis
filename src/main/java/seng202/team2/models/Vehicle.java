package seng202.team2.models;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import seng202.team2.database.DbAttributes;

/**
 * Enum listing all possible vehicle types
 * and the zero-based index that the vehicle count is located in the CSV file.
 *
 * @author Harrison Parkes
 */
public enum Vehicle {
    CAR_OR_STATION_WAGON(5),
    VAN_OR_UTE(62),
    SUV(50),
    TRUCK(59),
    BICYCLE(2),
    BUS(4),
    SCHOOL_BUS(44),
    MOPED(28),
    MOTOR_CYCLE(29),
    PEDESTRIAN(36),
    TAXI(51),
    TRAIN(57),
    OTHER(33);

    private static final Logger log = LogManager.getLogger(Vehicle.class);

    private final int csvDataColumn;

    /**
     * Create a new Vehicle.
     *
     * @param csvDataColumn The zero-based index that the vehicle count is located in the CSV file.
     */
    Vehicle(int csvDataColumn) {
        this.csvDataColumn = csvDataColumn;
    }

    /**
     * Get the zero-based index that the vehicle count is located in the CSV file.
     *
     * @return The zero-based index that the vehicle count is located in the CSV file.
     */
    public int getCsvColumn() {
        return csvDataColumn;
    }

    /**
     * Gets the column of the database that the vehicle count is located in.
     *
     * @return The column of the database that the vehicle count is located in.
     */
    public int getDbColumn() {
        try {
            return DbAttributes.valueOf(this.name()).dbColumn();
        } catch (IllegalArgumentException e) {
            log.error("Incorrectly named vehicle enum: " + this.name());
        }
        return DbAttributes.OTHER.dbColumn();
    }
}
