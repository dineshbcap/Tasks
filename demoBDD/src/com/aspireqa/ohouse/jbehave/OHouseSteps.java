package com.aspireqa.ohouse.jbehave;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.aspireqa.ohouse.steps.EndUserSteps;
import com.aspireqa.ohouse.utilities.Reader;

public class OHouseSteps {

    @Steps
    EndUserSteps endUser;
    
    @Given("the user logged in with username and password")
    public void userLogsInWithCredentials() {
        endUser.logsInWith(Reader.configMap.get("login.username"), Reader.configMap.get("login.password"));
    }

    @When("the user clicks on 'Options' Menu")
    public void userClicksOptionsMenu() {
        endUser.clicksOptionsMenu();
    }
    
    @When("the user enters the ticker symbol '$symbol'")
    public void userEntersTickerInSearchBox(String symbol) {
       endUser.enterTickerinSearchbox(symbol);
    }
   
    
    @Then("the user should see stock quotes for '$stockName'")
    public void userShouldSeeStockQuotes(String stockName) {
       endUser.seeStockQuotes(stockName);
    }
    
    @When("the user selects first quote for call option")
    public void userSelectsFirstQuote() {
       endUser.clickQuote();
    }
    
    @When("the user clicks on Buy order")
    public void userSelectsBuyOrder() {
       endUser.selectBuy();
    }
    
    @Then("the user should see Order Ticket dialog")
    public void userShouldSeeOrderDialog() {
       endUser.seeOrderDialog();
    }
    
    @When("the user enters quantity '$quantity'")
    public void userEnterStockQty(String qty) {
       endUser.enterStockQty(qty);
    }
    
    @When("the user clicks on Preview")
    public void userClicksOnPreview() {
       endUser.clickPreviewButton();
    }
    
    @Then("the user should see order message '$message'")
    public void userShouldSeeOrderMessage() {
       endUser.seeOrderMessage();
    }
    
    @Then("the Bid price, Ask price, Bid size, Ask size, Vol and OI values for PUT Option in OptionsChain listing should match with ViewQuote api response")
    public void compareQuoteDataForPUTWithApiAndUI() {
       endUser.assertQuoteValueForPUTWithApiAndUI();
    }
    
    @Then("the Bid price, Ask price, Bid size, Ask size, Vol and OI values for CALL Option in OptionsChain listing should match with ViewQuote api response")
    public void compareQuoteDataForCALLWithApiAndUI() {
       endUser.assertQuoteValueForCALLWithApiAndUI();
    }
    
    @When("the user clicks on Go to Standard OptionHouse")
    public void userClicksOnStandardOptionHouse() {
        endUser.userClicksOnStandardOptionHouse();
    }
    
    @Then("the user should verify the Classic trading page")
    public void u1() {
       endUser.verifyClassicPage();
    }
   
    
    @When("the user clicks on New Option House")
    public void u2() {
       endUser.userClicksOnNewOptionHouse();
    }
    
    @Then("the user should verify the TigerHawk page")
    public void u3() {
       endUser.verifyTigerHawkPage();
    }

    @When("the user selects format $type")
    public void u4(String type) {
       endUser.verifyTigerHawkPage();
    }
    
    @Then("the user should see the following $fields")
    public void u5(String fields) {
       endUser.verifyTigerHawkPage();
    }
    
    @Then("the user should see a message '$message'")
    public void seeErrorMessage(String message) {
       endUser.assertMessageForStockWithoutOptions(message);
    }
    
    @Then("the user should see the menu items Buy Call, Buy Advanced, Buy With OCO,Exchange Lookup,Add To watchList, Set Alert and Close Menu.")
    public void seeMenuItemsForCallAsk() {
       endUser.verifyMenuItemsForCallAsk();
    }
}

