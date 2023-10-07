package seng202.team2.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

/**
 * Skeleton for what will become the Query tests
 * @author bmo80
 */
public class QueryStepDefs {
    @Given("I have the car button selected")
    public void allInformationShown() {

    }

    @When("User Asks For Just 2018 Crashes")
    public void userAsksForJust2018Crashes() {

    }

    @Then("??? Results Are Shown")
    public void resultsAreShown() {
        Assertions.assertEquals(2,2);
    }
}
