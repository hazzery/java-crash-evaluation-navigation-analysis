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
    List<Crash> queryResult;
    CrashDao testDao = new CrashDao();

    @Before
    public void setupQuery() {queryTester = new QueryBuilder();}

    @Given("I have no filters selected")
    public void noFilters() {}

    @Given("I have pedestrian selected")
    public void addPedestrian() {
        ArrayList<DbAttributes> pedestrianTest = new ArrayList<>();
        pedestrianTest.add(DbAttributes.PEDESTRIAN);
        queryTester.orVehicle(pedestrianTest);
    }
    @When("I press apply")
    public void applyQuery() {
        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Then("All results in database are shown")
    public void allRowsShown() {
        Assertions.assertEquals(820467,queryResult.size());
    }

    @Given("I have fatal severity selected")
    public void fatalSeverityFilter() {
        ArrayList<String> severityTest = new ArrayList<>();
        severityTest.add("FATAL");
        queryTester.orString(severityTest, DbAttributes.SEVERITY);
    }
    @Then("All results shown involve a pedestrian and a fatality")
    public void allFatalPedestrianResults() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!(crash.severity().equals(Severity.FATAL)) ||
                    !(crash.vehicles().containsKey(Vehicle.PEDESTRIAN))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Given("I have cyclist and Bay of plenty region selected")
    public void BayofplentyCyclistFilter() {
        ArrayList<String> regionTest = new ArrayList<>();
        regionTest.add("BAY_OF_PLENTY");
        queryTester.orString(regionTest, DbAttributes.REGION);

        ArrayList<DbAttributes> cyclistTest = new ArrayList<>();
        cyclistTest.add(DbAttributes.BICYCLE);
        queryTester.orVehicle(cyclistTest);
    }

    @Then("All results shown involve a cyclist in the Bay of plenty")
    public void allBayofplentyCyclistResults() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!(crash.region().equals(Region.BAY_OF_PLENTY)) ||
                    !(crash.vehicles().containsKey(Vehicle.BICYCLE))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Given("I have bus selected with the year slider set between 2006 and 2016")
    public void BusYearFilter() {
        queryTester.betweenValues(2006, 2016, DbAttributes.YEAR);
        ArrayList<DbAttributes> busTest = new ArrayList<>();
        busTest.add(DbAttributes.BUS);
        queryTester.orVehicle(busTest);
    }

    @Then("All results shown involve a bus between 2006 and 2016")
    public void allBusYearResults() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!((crash.year() <= 2016) && (crash.year() >= 2006)) ||
                    !(crash.vehicles().containsKey(Vehicle.BUS))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Given("I have bus selected")
    public void bus() {
        ArrayList<DbAttributes> busTest = new ArrayList<>();
        busTest.add(DbAttributes.BUS);
        queryTester.orVehicle(busTest);
    }
    @Given("I have serious severity selected")
    public void pedestrianBusSeriousFatalFilter() {
        ArrayList<String> severityTest = new ArrayList<>();
        severityTest.add("SERIOUS");
        queryTester.orString(severityTest, DbAttributes.SEVERITY);

    }

    @Then("All results shown involve a pedestrian or a bus with a serious or fatal severity")
    public void allPedestrianBusSeriousFatalResults() {
        boolean valid = true;
        for (Crash crash: queryResult) {
            if (!((crash.severity().equals(Severity.SERIOUS)) ||
                    (crash.severity().equals(Severity.FATAL))) ||
                    !((crash.vehicles().containsKey(Vehicle.BUS)) ||
                    (crash.vehicles().containsKey(Vehicle.PEDESTRIAN)))) {
                valid = false;
                break;
            }
        }
        Assertions.assertTrue(valid);
    }

    @Given("I have bicycle and car buttons and non injury and minor severities and Auckland and Northland regions" +
            " selected with the year range set to 2018-2023")
    public void everythingFilter() {
        ArrayList<String> severityTest = new ArrayList<>();
        severityTest.add("NON_INJURY");
        severityTest.add("MINOR");
        queryTester.orString(severityTest, DbAttributes.SEVERITY);

        ArrayList<DbAttributes> bikeCarTest = new ArrayList<>();
        bikeCarTest.add(DbAttributes.BICYCLE);
        bikeCarTest.add(DbAttributes.CAR_OR_STATION_WAGON);
        queryTester.orVehicle(bikeCarTest);

        queryTester.betweenValues(2018, 2023, DbAttributes.YEAR);

        ArrayList<String> regionTest = new ArrayList<>();
        regionTest.add("AUCKLAND");
        regionTest.add("NORTHLAND");
        queryTester.orString(regionTest, DbAttributes.REGION);
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
