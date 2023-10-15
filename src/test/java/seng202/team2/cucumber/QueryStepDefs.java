package seng202.team2.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import seng202.team2.database.CrashDao;
import seng202.team2.database.DbAttributes;
import seng202.team2.database.QueryBuilder;
import seng202.team2.models.Crash;
import seng202.team2.models.Region;
import seng202.team2.models.Severity;
import seng202.team2.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Definitions for testing query outputs
 * @author Ben Moore
 * @author James Lanigan
 */
public class QueryStepDefs {
    QueryBuilder queryTester;
    QueryBuilder tempQueryTester;
    List<Crash> queryResult;
    CrashDao testDao = new CrashDao();

    @Before
    public void setupQuery() {queryTester = new QueryBuilder();}

    @Given("I have no filters selected")
    public void noFilters() {}

    @Given("I have the year slider set to {int}-{int}")
    public void yearRange(Integer lowBound, Integer highBound) {
        queryTester.betweenValues(lowBound, highBound, DbAttributes.YEAR);

        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Given("I have pedestrian selected")
    public void pedestrianFilter() {
        ArrayList<DbAttributes> pedestrianTest = new ArrayList<>();
        pedestrianTest.add(DbAttributes.PEDESTRIAN);
        queryTester.orVehicle(pedestrianTest);

        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Given("I have fatal severity selected")
    public void fatalFilter() {
        ArrayList<String> severityTest = new ArrayList<>();
        severityTest.add("FATAL");
        queryTester.orString(severityTest, DbAttributes.SEVERITY);

        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Given("I have cyclist selected")
    public void cyclistFilter() {
        ArrayList<DbAttributes> cyclistTest = new ArrayList<>();
        cyclistTest.add(DbAttributes.BICYCLE);
        queryTester.orVehicle(cyclistTest);

        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Given("I have Bay of plenty region selected")
    public void BayofplentyFilter() {
        ArrayList<String> regionTest = new ArrayList<>();
        regionTest.add("BAY_OF_PLENTY");
        queryTester.orString(regionTest, DbAttributes.REGION);

        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Given("I have bus selected")
    public void busFilter() {
        ArrayList<DbAttributes> busTest = new ArrayList<>();
        busTest.add(DbAttributes.BUS);
        queryTester.orVehicle(busTest);

        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Given("I have pedestrian and bus selected")
    public void pedestrianBusFilter() {
        ArrayList<DbAttributes> pedestrianBusTest = new ArrayList<>();
        pedestrianBusTest.add(DbAttributes.PEDESTRIAN);
        pedestrianBusTest.add(DbAttributes.BUS);
        queryTester.orVehicle(pedestrianBusTest);

        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Given("I have serious and fatal severities selected")
    public void seriousFatalFilter() {
        ArrayList<String> severityTest = new ArrayList<>();
        severityTest.add("FATAL");
        severityTest.add("SERIOUS");
        queryTester.orString(severityTest, DbAttributes.SEVERITY);

        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Given("I have bicycle and car selected")
    public void bicycleCarFilter() {
        ArrayList<DbAttributes> bikeCarTest = new ArrayList<>();
        bikeCarTest.add(DbAttributes.BICYCLE);
        bikeCarTest.add(DbAttributes.CAR_OR_STATION_WAGON);
        queryTester.orVehicle(bikeCarTest);

        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Given("I have non injury and minor severities selected")
    public void minorSeriousFilter() {
        ArrayList<String> severityTest = new ArrayList<>();
        severityTest.add("NON_INJURY");
        severityTest.add("MINOR");
        queryTester.orString(severityTest, DbAttributes.SEVERITY);

        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Given("I have Auckland and Northland regions selected")
    public void aucklandNorthlandFilter() {
        ArrayList<String> regionTest = new ArrayList<>();
        regionTest.add("AUCKLAND");
        regionTest.add("NORTHLAND");
        queryTester.orString(regionTest, DbAttributes.REGION);

        tempQueryTester = queryTester;
        queryResult = testDao.queryDatabase(tempQueryTester.getQuery());
    }

    @When("I press apply")
    public void applyQuery() {
        queryResult = testDao.queryDatabase(queryTester.getQuery());
        queryTester = new QueryBuilder();
    }

    @Then("All results in database are shown")
    public void allRowsShown() {
        Assertions.assertEquals(820467,queryResult.size());
    }

    @Then("All results shown involve a pedestrian")
    public void pedestrianInCrash() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!(crash.vehicles().containsKey(Vehicle.PEDESTRIAN))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Then("All results shown are of fatal severity")
    public void fatalSeverity() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!(crash.severity().equals(Severity.FATAL))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Then("All results shown involve a bicycle")
    public void cyclistInCrash() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!(crash.vehicles().containsKey(Vehicle.BICYCLE))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Then("All results shown occurred in the Bay of Plenty")
    public void bopInCrash() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!(crash.region().equals(Region.BAY_OF_PLENTY))) {
                valid = false;
                break;
            }
        }
    }
    @Then("All results shown involve a bus")
    public void busInCrash() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!(crash.vehicles().containsKey(Vehicle.BUS))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Then("All results shown occurred between {int}-{int}")
    public void inYearRange(Integer lowBound, Integer highBound) {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!((crash.year() <= highBound) && (crash.year() >= lowBound))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Then("All results shown involve a pedestrian or a bus")
    public void pedestrianOrBus() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!((crash.vehicles().containsKey(Vehicle.BUS)) ||
                            (crash.vehicles().containsKey(Vehicle.PEDESTRIAN)))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Then ("All results shown are of serious or fatal severity")
    public void fatalOrSerious() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!((crash.severity().equals(Severity.SERIOUS)) ||
                    (crash.severity().equals(Severity.FATAL)))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Then("All results shown involve a bicycle or car")
    public void bikeOrCar() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!((crash.vehicles().containsKey(Vehicle.BICYCLE)) ||
                            (crash.vehicles().containsKey(Vehicle.CAR_OR_STATION_WAGON)))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Then("All results shown are of non-injury or minor severity")
    public void notSeriousSeverity() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!((crash.severity().equals(Severity.NON_INJURY)) ||
                    (crash.severity().equals(Severity.MINOR)))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Then("All results shown occurred in either Auckland or Northland")
    public void aucklandOrNorthland() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!((crash.region().equals(Region.AUCKLAND)) || (crash.region().equals(Region.NORTHLAND)))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Then("All results shown involve a bicycle or car, with no injury or minor, in the Auckland or Northland" +
            " regions between the years 2018 and 2023")
    public void allEverythingResults() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!((crash.severity().equals(Severity.NON_INJURY)) ||
                    (crash.severity().equals(Severity.MINOR))) ||
                    !((crash.vehicles().containsKey(Vehicle.BICYCLE)) ||
                            (crash.vehicles().containsKey(Vehicle.CAR_OR_STATION_WAGON))) ||
                    !((crash.region().equals(Region.AUCKLAND)) || (crash.region().equals(Region.NORTHLAND))) ||
                    !((crash.year() <= 2023) && (crash.year() >= 2018))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }
}


