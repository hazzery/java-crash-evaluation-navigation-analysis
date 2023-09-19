package seng202.team2.unittests.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team2.database.CrashDao;
import seng202.team2.models.*;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CrashDaoTest {
    CrashDao crashDao;

    @BeforeEach
    void setup() {
        crashDao = new CrashDao();
    }

    @Test
    void getAll() {
        List<Crash> all = crashDao.getAll();
        assertTrue(all.size() > 820000);
    }

    @Test
    void getOne() {
        Crash crash = crashDao.getOne(1);
        assertEquals(1, crash.crashID());
    }

    @Test
    void queryDatabase() {
        List<Crash> crashes = crashDao.queryDatabase("SELECT * FROM crashes WHERE ID < 101");
        assertEquals(100, crashes.size());
    }

    @Test
    void addBatch() {
        Crash crash1 = new Crash(9999999, 2023,
                100, 100, 100,
                1.1, 2.2, "John Road", "James Road", "Jack Region",
                Weather.SNOW, Lighting.TWILIGHT, Severity.FATAL_CRASH,
                Map.ofEntries(
                        Map.entry(Vehicle.TRUCK,1),
                        Map.entry(Vehicle.CAR_OR_STATION_WAGON,2),
                        Map.entry(Vehicle.BICYCLE,3),
                        Map.entry(Vehicle.MOTOR_CYCLE,4),
                        Map.entry(Vehicle.MOPED,5),
                        Map.entry(Vehicle.BUS,6),
                        Map.entry(Vehicle.TAXI,7),
                        Map.entry(Vehicle.OTHER,8),
                        Map.entry(Vehicle.VAN_OR_UTE,9),
                        Map.entry(Vehicle.SUV,10),
                        Map.entry(Vehicle.SCHOOL_BUS,11),
                        Map.entry(Vehicle.PEDESTRIAN,12),
                        Map.entry(Vehicle.TRAIN,13)
                )
        );

        Crash crash2 = new Crash(9999998, 2023,
                100, 100, 100,
                1.1, 2.2, "John Road", "James Road", "Jack Region",
                Weather.SNOW, Lighting.TWILIGHT, Severity.FATAL_CRASH,
                Map.ofEntries(
                        Map.entry(Vehicle.TRUCK,1),
                        Map.entry(Vehicle.CAR_OR_STATION_WAGON,2),
                        Map.entry(Vehicle.BICYCLE,3),
                        Map.entry(Vehicle.MOTOR_CYCLE,4),
                        Map.entry(Vehicle.MOPED,5),
                        Map.entry(Vehicle.BUS,6),
                        Map.entry(Vehicle.TAXI,7),
                        Map.entry(Vehicle.OTHER,8),
                        Map.entry(Vehicle.VAN_OR_UTE,9),
                        Map.entry(Vehicle.SUV,10),
                        Map.entry(Vehicle.SCHOOL_BUS,11),
                        Map.entry(Vehicle.PEDESTRIAN,12),
                        Map.entry(Vehicle.TRAIN,13)
                )
        );

        crashDao.addBatch(List.of(crash1, crash2));

        Crash crash1FromDatabase = crashDao.getOne(9999999);
        Crash crash2FromDatabase = crashDao.getOne(9999998);

        assertEquals(crash1, crash1FromDatabase);
        assertEquals(crash2, crash2FromDatabase);
    }
}