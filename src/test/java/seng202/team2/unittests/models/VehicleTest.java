package seng202.team2.unittests.models;

import org.junit.jupiter.api.Test;
import seng202.team2.models.Vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for Vehicle enum.
 * Tests that the vehicle enum columns are correct.
 *
 * @author Findlay Royds
 */
public class VehicleTest {
    /**
     * Test the CSV column for bicycle
     */
    @Test
    public void bicycleColumnTest() {
        int expectedColumn = 2;
        Vehicle testVehicle = Vehicle.BICYCLE;
        assertEquals(expectedColumn, testVehicle.getCSVColumn());
    }

    /**
     * Test the CSV column for bus
     */
    @Test
    public void busColumnTest() {
        int expectedColumn = 4;
        Vehicle testVehicle = Vehicle.BUS;
        assertEquals(expectedColumn, testVehicle.getCSVColumn());
    }

    /**
     * Test the CSV column for car or station wagon
     */
    @Test
    public void carOrStationWagonColumnTest() {
        int expectedColumn = 5;
        Vehicle testVehicle = Vehicle.CAR_OR_STATION_WAGON;
        assertEquals(expectedColumn, testVehicle.getCSVColumn());
    }

    /**
     * Test the CSV column for moped
     */
    @Test
    public void mopedColumnTest() {
        int expectedColumn = 28;
        Vehicle testVehicle = Vehicle.MOPED;
        assertEquals(expectedColumn, testVehicle.getCSVColumn());
    }

    /**
     * Test the CSV column for motorcycle
     */
    @Test
    public void motorcycleColumnTest() {
        int expectedColumn = 29;
        Vehicle testVehicle = Vehicle.MOTOR_CYCLE;
        assertEquals(expectedColumn, testVehicle.getCSVColumn());
    }

    /**
     * Test the CSV column for school bus
     */
    @Test
    public void schoolBusColumnTest() {
        int expectedColumn = 44;
        Vehicle testVehicle = Vehicle.SCHOOL_BUS;
        assertEquals(expectedColumn, testVehicle.getCSVColumn());
    }

    /**
     * Test the CSV column for suv
     */
    @Test
    public void suvColumnTest() {
        int expectedColumn = 50;
        Vehicle testVehicle = Vehicle.SUV;
        assertEquals(expectedColumn, testVehicle.getCSVColumn());
    }

    /**
     * Test the CSV column for taxi
     */
    @Test
    public void taxiColumnTest() {
        int expectedColumn = 51;
        Vehicle testVehicle = Vehicle.TAXI;
        assertEquals(expectedColumn, testVehicle.getCSVColumn());
    }

    /**
     * Test the CSV column for train
     */
    @Test
    public void trainColumnTest() {
        int expectedColumn = 57;
        Vehicle testVehicle = Vehicle.TRAIN;
        assertEquals(expectedColumn, testVehicle.getCSVColumn());
    }

    /**
     * Test the CSV column for truck
     */
    @Test
    public void truckColumnTest() {
        int expectedColumn = 59;
        Vehicle testVehicle = Vehicle.TRUCK;
        assertEquals(expectedColumn, testVehicle.getCSVColumn());
    }

    /**
     * Test the CSV column for other
     */
    @Test
    public void otherColumnTest() {
        int expectedColumn = 33;
        Vehicle testVehicle = Vehicle.OTHER;
        assertEquals(expectedColumn, testVehicle.getCSVColumn());
    }
}
