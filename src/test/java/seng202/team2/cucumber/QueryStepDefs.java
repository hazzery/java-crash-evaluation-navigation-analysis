package seng202.team2.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import seng202.team2.database.CrashDao;
import seng202.team2.database.DbAttributes;
import seng202.team2.database.QueryBuilder;
import seng202.team2.models.Crash;
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

    @Given("User has Person and fatal severity selected")
    public void fatalPersonFilter() {
        queryTester = new QueryBuilder();
        ArrayList<String> severityTest = new ArrayList<String>();
        severityTest.add("FATAL");
        queryTester.orString(severityTest, DbAttributes.SEVERITY);

        ArrayList<DbAttributes> pedestrianTest = new ArrayList<DbAttributes>();
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

    @When("User Asks For Just 2018 Crashes")
    public void userAsksForJust2018Crashes() {

    }

    @Then("??? Results Are Shown")
    public void resultsAreShown() {
        Assertions.assertEquals(2,2);
    }
}
