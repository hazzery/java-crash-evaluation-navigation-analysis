package seng202.team2.unittests.database;

import seng202.team2.database.DbAttributes;
import seng202.team2.database.QueryBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;



public class QueryBuilderTest {
    QueryBuilder queryBuilderTester;
    @BeforeEach
    void setup() {
        queryBuilderTester = new QueryBuilder();
    }

    @Test
    void noConditionsTest() {
        assertEquals("SELECT * FROM crashes;", queryBuilderTester.getQuery());
    }

    @Test
    void equalTest() {
        queryBuilderTester.equalVal(2, DbAttributes.ID);
        assertEquals("SELECT * FROM crashes WHERE (ID = 2);", queryBuilderTester.getQuery());
    }

    @Test
    void betweenTest() {
        queryBuilderTester.betweenValues(2002,2004, DbAttributes.YEAR);
        assertEquals("SELECT * FROM crashes WHERE (YEAR BETWEEN 2002 AND 2004);", queryBuilderTester.getQuery());
    }

    @Test
    void lessThanTest() {
        queryBuilderTester.lessThan(2002, DbAttributes.YEAR);
        assertEquals("SELECT * FROM crashes WHERE (YEAR < 2002);", queryBuilderTester.getQuery());
    }

    @Test
    void greaterThanTest() {
        queryBuilderTester.greaterThan(2002, DbAttributes.YEAR);
        assertEquals("SELECT * FROM crashes WHERE (YEAR > 2002);", queryBuilderTester.getQuery());
    }

    @Test
    void orStringTest() {
        ArrayList<String> testStrings = new ArrayList<>();
        testStrings.add("Auckland");
        testStrings.add("Tasman");
        queryBuilderTester.orString(testStrings, DbAttributes.REGION);
        assertEquals("SELECT * FROM crashes WHERE (REGION = \"Auckland\" OR REGION = \"Tasman\");", queryBuilderTester.getQuery());
    }

    @Test
    void orVehicleTest() {
        ArrayList<DbAttributes> testVehicles = new ArrayList<>();
        testVehicles.add(DbAttributes.BICYCLE);
        testVehicles.add(DbAttributes.BUS);
        queryBuilderTester.orVehicle(testVehicles);
        assertEquals("SELECT * FROM crashes WHERE ((BICYCLE > 0) OR (BUS > 0));", queryBuilderTester.getQuery());
    }

    @Test
    void emptyOrStringTest() {
        ArrayList<String> testStrings = new ArrayList<>();
        queryBuilderTester.orString(testStrings, DbAttributes.REGION);
        assertEquals("SELECT * FROM crashes;", queryBuilderTester.getQuery());
    }

    @Test
    void emptyVehicleTest() {
        ArrayList<DbAttributes> testVehicles = new ArrayList<>();
        queryBuilderTester.orVehicle(testVehicles);
        assertEquals("SELECT * FROM crashes;", queryBuilderTester.getQuery());
    }
    
    @Test
    void combinationTest() {
        ArrayList<String> testStrings = new ArrayList<>();
        testStrings.add("Auckland");
        testStrings.add("Tasman");
        testStrings.add("Nelson");
        queryBuilderTester.orString(testStrings, DbAttributes.REGION);
        queryBuilderTester.greaterThan(2002, DbAttributes.YEAR);
        assertEquals("SELECT * FROM crashes WHERE (REGION = \"Auckland\" OR REGION = \"Tasman\" OR REGION = \"Nelson\") AND (YEAR > 2002);", queryBuilderTester.getQuery());
    }


}
