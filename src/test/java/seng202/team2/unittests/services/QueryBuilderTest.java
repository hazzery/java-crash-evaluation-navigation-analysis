package seng202.team2.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.team2.database.DbAttributes;
import seng202.team2.database.QueryBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class QueryBuilderTest {
    QueryBuilder queryBuilderTester;
    @BeforeEach
    void setup() {
        queryBuilderTester = new QueryBuilder();
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
        ArrayList<String> testStrings = new ArrayList<String>();
        testStrings.add("Auckland");
        testStrings.add("Tasman");
        queryBuilderTester.orString(testStrings, DbAttributes.REGION);
        assertEquals("SELECT * FROM crashes WHERE (REGION = (Auckland OR Tasman));", queryBuilderTester.getQuery());
    }
}
