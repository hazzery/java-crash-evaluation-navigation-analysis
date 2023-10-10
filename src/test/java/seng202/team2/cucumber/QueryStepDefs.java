package seng202.team2.cucumber;

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
 * Skeleton for what will become the Query tests
 * @author bmo80
 * @author James Lanigan
 */
public class QueryStepDefs {
    QueryBuilder queryTester;
    List<Crash> queryResult;
    CrashDao testDao = new CrashDao();

    @Given("I have no filters selected")
    public void noFilters() {
        queryTester = new QueryBuilder();
    }

    @When("I press apply")
    public void applyQuery() {
        queryResult = testDao.queryDatabase(queryTester.getQuery());
    }

    @Then("All results in database are shown")
    public void allRowsShown() {
        Assertions.assertEquals(820467,queryResult.size());
    }

    @Given("I have person and fatal severity selected")
    public void fatalPersonFilter() {
        queryTester = new QueryBuilder();
        ArrayList<String> severityTest = new ArrayList<>();
        severityTest.add("FATAL");
        queryTester.orString(severityTest, DbAttributes.SEVERITY);

        ArrayList<DbAttributes> pedestrianTest = new ArrayList<>();
        pedestrianTest.add(DbAttributes.PEDESTRIAN);
        queryTester.orVehicle(pedestrianTest);
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
        queryTester = new QueryBuilder();
        ArrayList<String> regionTest = new ArrayList<>();
        regionTest.add("BAYOFPLENTY");
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
    public void HeavyvehicleYearFilter() {
        queryTester = new QueryBuilder();
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

    @Given("I have pedestrian and bus buttons and serious and fatal severities selected")
    public void pedestrianBusSeriousFatalFilter() {
        queryTester = new QueryBuilder();
        ArrayList<String> severityTest = new ArrayList<>();
        severityTest.add("FATAL");
        severityTest.add("SERIOUS");
        queryTester.orString(severityTest, DbAttributes.SEVERITY);

        ArrayList<DbAttributes> pedestrianBusTest = new ArrayList<>();
        pedestrianBusTest.add(DbAttributes.PEDESTRIAN);
        pedestrianBusTest.add(DbAttributes.BUS);
        queryTester.orVehicle(pedestrianBusTest);
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


}
