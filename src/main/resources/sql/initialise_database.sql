DROP TABLE IF EXISTS crashes;
--SPLIT
CREATE TABLE IF NOT EXISTS crashes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    year INTEGER NOT NULL,
    fatalities INTEGER NOT NULL,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL,
    weather TEXT NOT NULL,
    lighting TEXT NOT NULL,
    severity TEXT NOT NULL,
    car_or_station_wagon INTEGER NOT NULL,
    van_or_ute INTEGER NOT NULL,
    suv INTEGER NOT NULL,
    truck INTEGER NOT NULL,
    bicycle INTEGER NOT NULL,
    bus INTEGER NOT NULL,
    school_bus INTEGER NOT NULL,
    moped INTEGER NOT NULL,
    motor_cycle INTEGER NOT NULL,
    pedestrian INTEGER NOT NULL,
    taxi INTEGER NOT NULL,
    train INTEGER NOT NULL,
    other INTEGER NOT NULL
);