package seng202.team2.models;

public enum Vehicle {
    CarOrStationWagon(5),
    VanOrUte(62),
    SUV(50),
    Truck(59),
    Bicycle(2),
    Bus(4),
    SchoolBus(44),
    Moped(28),
    MotorCycle(29),
    Pedestrian(36),
    Taxi(51),
    Train(57),
    Other(33);

    private final int csvDataColumn;

    /**
     * Create new Vehicle
     * @param csvDataColumn The zero based index that the vehicle count is located in the CSV file
     */
    Vehicle(int csvDataColumn) {
        this.csvDataColumn = csvDataColumn;
    }

    /**
     * Get the zero based index that the vehicle count is located in the CSV file
     * @return The zero based index that the vehicle count is located in the CSV file
     */
    public int getCSVColumn() {
        return csvDataColumn;
    }
}
