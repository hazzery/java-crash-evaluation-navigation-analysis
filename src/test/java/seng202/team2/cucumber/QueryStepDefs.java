package seng202.team2.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import seng202.team2.database.CrashDao;
import seng202.team2.database.QueryBuilder;
import seng202.team2.models.Crash;

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

    @When("User Asks For Just 2018 Crashes")
    public void userAsksForJust2018Crashes() {

    }

    @Then("??? Results Are Shown")
    public void resultsAreShown() {
        Assertions.assertEquals(2,2);
    }
}
