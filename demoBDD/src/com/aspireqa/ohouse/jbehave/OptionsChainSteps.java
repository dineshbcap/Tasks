package com.aspireqa.ohouse.jbehave;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.aspireqa.ohouse.steps.EndUserSteps;

public class OptionsChainSteps {

    @Steps
    EndUserSteps endUser;

    @When("the user clicks on Go to Standard OptionHouse")
    public void userClicksOnStandardOptionHouse() {
        endUser.userClicksOnStandardOptionHouse();
    }
    
    @Then("the user should verify the Classic trading page")
    public void userEntersTickerInSearchBox(String symbol) {
       endUser.verifyClassicPage();
    }
   
    
    @When("the user clicks on New Option House")
    public void userShouldSeeStockQuotes(String stockName) {
       endUser.userClicksOnNewOptionHouse();
    }
    
    @Then("the user should verify the TigerHawk page")
    public void userSelectsFirstQuote() {
       endUser.verifyTigerHawkPage();
    }

}
