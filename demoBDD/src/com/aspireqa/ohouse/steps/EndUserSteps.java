package com.aspireqa.ohouse.steps;

import static org.fest.assertions.Assertions.assertThat;

import java.util.HashMap;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

import com.aspireqa.ohouse.api.ViewQuoteAPI;
import com.aspireqa.ohouse.pages.HomePage;
import com.aspireqa.ohouse.pages.LoginPage;
import com.aspireqa.ohouse.pages.OrderTicketPage;

@SuppressWarnings("serial")
public class EndUserSteps extends ScenarioSteps {


    LoginPage loginPage;
    HomePage homePage;
    OrderTicketPage orderTicketPage;
    ViewQuoteAPI viewQuoteAPI;
    
    //STEPS
    
    @Step
    public void clicksOptionsMenu() {
      homePage.landsOnHomepage();
      homePage.clickOnOptionsMenu();
    }

    @Step
    public void userClicksOnStandardOptionHouse() {
      homePage.landsOnHomepage();
      homePage.clickOnGoToStandardOpionshouseButton();
    }
    
    @Step
    public void verifyClassicPage() {
    	
   homePage.closeAlertsOnClassic();
    }
    
    @Step
    public void userClicksOnNewOptionHouse() {
    	homePage.clickOnGoToTigerhawkButton();
    }
    
    @Step
    public void verifyTigerHawkPage() {
      //homePage.landsOnHomepage();
      //homePage.clickOnGoToStandardOpionshouseButton();
    }
    
    @Step
    public void logsInWith(String usr, String pwd) {
    	loginPage.open();
    	loginPage.enterUsername(usr);
    	loginPage.enterPassword(pwd);
    	loginPage.login();
    	
    }
    
   @Step
   public void enterTickerinSearchbox(String ticker) {
      homePage.enterTickerInSearchBox(ticker);
      homePage.clickOnGoButton();
   }
   
   @Step
   public void seeStockQuotes(String stockName) {
	   assertThat(homePage.getStockname()).contains(stockName);
   }
   
   @Step
   public void clickQuote() {
	 homePage.clickOnFirstQuote();
   }
   
   @Step
   public void selectBuy() {
	 homePage.clickOnBuyMenu();
   }
   
   @Step
   public void seeOrderDialog() {
	 assertThat(orderTicketPage.getOrderDialogHeader().equals("Order Ticket"));
   }
   
   @Step
   public void enterStockQty(String qty) {
	   
	   orderTicketPage.enterStockQuantity(qty);
   }
   
   @Step
   public void clickPreviewButton() {
	   
	   orderTicketPage.clickOnPreviewButton();
   }
   
   @Step
   public void seeOrderMessage() {
	   
	 assertThat(orderTicketPage.getOrderSummary().equals("You don't have enough buying power for this trade. You will not have enough equity in your account to maintain your margin requirements after this trade."));
	 getDriver().quit();
   }
   
   @Step
   public void selectFormatFromOptionsChain(String format) {
	   
   homePage.selectOptionsChainFormat(format);
   }
   
   @Step
   public void verifyHeadersInOptionsChain(String headerFields) {
	   
	 assertThat(orderTicketPage.getOrderSummary().equals("You don't have enough buying power for this trade. You will not have enough equity in your account to maintain your margin requirements after this trade."));
	 getDriver().quit();
   }
   
   @Step
   public void assertMessageForStockWithoutOptions(String messageToVerify) {
	   
	 assertThat(homePage.getMessageFromOptionsChain().equals(messageToVerify));
	 getDriver().quit();
   }
   
   @Step
   public void verifyMenuItemsForCallAsk() {
	   
		 homePage.verifyMenuItemsForCallAsk();
		 getDriver().quit();
	   }
   
   
   @Step
   public void assertQuoteValueForPUTWithApiAndUI() { 
	   viewQuoteAPI = new ViewQuoteAPI();
	 HashMap<String, String> valuesFromOChain = homePage.getQuoteDetailsFromOptionsChainGUI();
	 HashMap<String, String> valuesFromAPI = viewQuoteAPI.callViewQuoteForPUTOption(valuesFromOChain.get("series"));
	 System.out.println(valuesFromOChain);
	 System.out.println(valuesFromAPI);
	   
	 
	 for(int i = 0; i < valuesFromAPI.size(); i++ )
	 {
		 assertThat(valuesFromOChain.get("putBidPrice").equals(valuesFromAPI.get("putBidPrice")));
		 assertThat(valuesFromOChain.get("putAskPrice").equals(valuesFromAPI.get("putAskPrice")));
		 assertThat(valuesFromOChain.get("putBidSize").equals(valuesFromAPI.get("putBidSize")));
		 assertThat(valuesFromOChain.get("putAskSize").equals(valuesFromAPI.get("putAskSize")));
		 assertThat(valuesFromOChain.get("putVolume").equals(valuesFromAPI.get("putVolume")));
		 assertThat(valuesFromOChain.get("putOI").equals(valuesFromAPI.get("putOI")));	 
		 
	 }
	 
   }
   
   @Step
   public void assertQuoteValueForCALLWithApiAndUI() { 
	   viewQuoteAPI = new ViewQuoteAPI();
	 HashMap<String, String> valuesFromOChain = homePage.getQuoteDetailsFromOptionsChainGUI();
	 HashMap<String, String> valuesFromAPI = viewQuoteAPI.callViewQuoteForCALLOption(valuesFromOChain.get("series"));
	 System.out.println(valuesFromOChain);
	 System.out.println(valuesFromAPI);
	   
	 
	 for(int i = 0; i < valuesFromAPI.size(); i++ )
	 {
		 assertThat(valuesFromOChain.get("callBidPrice").equals(valuesFromAPI.get("callBidPrice")));
		 assertThat(valuesFromOChain.get("callAskPrice").equals(valuesFromAPI.get("callAskPrice")));
		 assertThat(valuesFromOChain.get("callBidSize").equals(valuesFromAPI.get("callBidSize")));
		 assertThat(valuesFromOChain.get("callAskSize").equals(valuesFromAPI.get("callAskSize")));
		 assertThat(valuesFromOChain.get("callVolume").equals(valuesFromAPI.get("callVolume")));
		 assertThat(valuesFromOChain.get("callOI").equals(valuesFromAPI.get("callOI")));	 
		 
	 }
	 
   }
   
}
