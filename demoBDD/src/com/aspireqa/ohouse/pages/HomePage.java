package com.aspireqa.ohouse.pages;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import net.thucydides.core.annotations.findby.By;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.pages.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import com.aspireqa.ohouse.utilities.LoggerUtility;
import com.thoughtworks.selenium.Selenium;

public class HomePage extends PageObject{
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	public static final String FORMAT_BASIC = "1";
	public static final String FORMAT_SIZES = "2";
	public static final String FORMAT_INTERMEDIATE = "3";
	public static final String FORMAT_PRO = "4";
	public static final String FORMAT_GREEKS = "5";
	public static final String FORMAT_CUSTOM = "6";
	
	private LoggerUtility logger = LoggerUtility.getInstance(HomePage.class);	

	@FindBy(xpath= "//div[@id='virtualTradingDisclaimerWin']")
    private WebElement virtualTradingDisclaimerAlert;
    
    @FindBy(xpath= "//div[@id='ext-gen282']")
    private WebElement virtualTradingDisclaimerCloseButton;
    
    @FindBy(xpath= "//div[@id='interstitialDisclaimerWin']")
    private WebElement interstitialDisclaimerAlert;
    
    @FindBy(xpath= "//div[@id='ext-gen282']")
    private WebElement interstitialDisclaimerCloseButton;

    @FindBy(xpath= "//*[@id='ext-gen89']/span/span")
    private WebElement optionsMenu;
    
    @FindBy(xpath= "//input[@id='loginSubmit']")
    private WebElement loginButton;
    
    @FindBy(xpath= "//input[@id='desktopSymbolBox']")
    private WebElement searchSymbolBox;
    
    @FindBy(xpath= "//*[@id='ext-gen66']")
    //By userName = By.xpath(xpathExpression);
    private WebElement goButton;
    
    @FindBy(xpath= "//*[@id='desktop-desc']")
    private WebElement stockLabel;
    
    @FindBy(xpath= "//tbody[1]/tr[2]/td[3]/div[contains(@id,'cask-')][1]")
    private WebElement firstResult;
    
    @FindBy(xpath= "//div[@id='oh_classic']")
    private WebElement goToStdOptionsBtn;
    
    @FindBy(xpath= "//div[@id='mainDiv']/table/tbody/tr[1]/td/div/img")
    private WebElement goToTigerHawkBtn;

    @FindBy(xpath= "//td[@class='sprite_header_background']/table/tbody/tr[1]/td[2]/div/img")
    private WebElement classicMsgalert;
    
    public Boolean isAlertDisplayed(String locator)
    {
    	try{
    		return getDriver().findElements(By.xpath(locator)).size() > 0;   		
    	}
    	catch(NoSuchElementException ne){
    		return false;
    	}
    }
    
       
    public void close_alerts()
    {
    	//getDriver().manage().window().maximize();
    	
//    	if(virtualTradingDisclaimerAlert.isDisplayed())
//    	{
//    		virtualTradingDisclaimerAlert.click();
//    		virtualTradingDisclaimerCloseButton.click();
//    	}

    	/*if(interstitialDisclaimerAlert.isDisplayed())
    	{
    		interstitialDisclaimerAlert.click();
    		interstitialDisclaimerCloseButton.click();
    	}*/
    	Boolean alertPresent; 

        alertPresent = isAlertDisplayed("//div[@id='interstitialDisclaimerWin']"); 
        if(alertPresent) 
        { 
        	//System.out.println(alertPresent); 
        	interstitialDisclaimerAlert.click(); 
        	interstitialDisclaimerCloseButton.click(); 
        } 
       
    }
    
    public void landsOnHomepage()
    {
    	close_alerts();    	
    }
    
    public void clickOnOptionsMenu()
    {
    	optionsMenu.click();
    	
    }
    
    public void clickOnGoToStandardOpionshouseButton()
    {
    	goToStdOptionsBtn.click();
    	
    }
    
    public void clickOnGoToTigerhawkButton()
    {
    	goToTigerHawkBtn.click();
    	
    }
    
    public void selectOptionsChainFormat(String format)
    {

    	getDriver().switchTo().frame(getDriver().findElement(By.xpath("//iframe[contains(@src,'chain.html?')]"))); 
    	
    	getDriver().findElement(By.xpath("//img[@id='ext-gen53']")).click();
    	
    	Actions builder = new Actions(getDriver());
    	Action fQuote = builder.click(getDriver().findElement(By.xpath("div[@id='ext-gen63']/div[" + computeDivIdForFormat(format) + "]"))).build();    	
    	fQuote.perform();
   }
    
    public String getMessageFromOptionsChain()
    {
    	//div[@id='ext-gen13']/div
    	
    	String message = "";
    	
    	getDriver().switchTo().frame(getDriver().findElement(By.xpath("//iframe[contains(@src,'chain.html?')]")));
    	
    	message = fetchText("//div[@id='ext-gen13']/div");
    	
    	
    	System.out.println("Message is...." + message);
    	
    	return message;
    	
    }
    
    public String computeDivIdForFormat(String format)
    {
    	String divId = "";
    	
    	if(format.equalsIgnoreCase("Basic"))
    	{
    		divId = FORMAT_BASIC;
    	}
    	else if (format.equalsIgnoreCase("Sizes"))
    	{
    		divId = FORMAT_SIZES;
    	}
    	else if (format.equalsIgnoreCase("Intermediate"))
    	{
    		divId = FORMAT_INTERMEDIATE;
    	}
    	else if (format.equalsIgnoreCase("Pro"))
    	{
    		divId = FORMAT_PRO;
    	}
    	else if (format.equalsIgnoreCase("Greeks"))
    	{
    		divId = FORMAT_GREEKS;
    	}
    	else if (format.equalsIgnoreCase("Custom"))
    	{
    		divId = FORMAT_CUSTOM;
    	}
    	
		return divId;
    	
    }
    
    public void closeAlertsOnClassic()
    {
    	
    	Boolean alertPresent; 
    	
    	getDriver().switchTo().frame(getDriver().findElement(By.xpath("//iframe[contains(@id,'frame_group_22')]")));

        alertPresent = isAlertDisplayed("//iframe[contains(@id,'frame_group_22')]");
        
        if(alertPresent) 
        { 
        	
        	System.out.println("alert displayed..............");
        	//getDriver().switchTo().frame("frame_group_22");
        	        	
        	//getDriver().switchTo().frame(getDriver().findElement(By.xpath("frame_group_22")));
        	//getDriver().switchTo().frame(getDriver().findElement(By.xpath("//iframe[contains(@id,'frame_group_22')]")));
        	
        	//System.out.println("Clicked into message box and started..."); 
        	//classicMsgalert.click();
        	
        	//System.out.println("element present..............");
        	
        	Actions builder = new Actions(getDriver());
        	Action fQuote = builder.click(getDriver().findElement(By.xpath("//div[@id='modal_close_div']/img"))).build();    	
        	fQuote.perform();
        	//getDriver().findElement(By.xpath("//div[@id='modal_close_div']/img")).click();
        	
        } 
        
        getDriver().switchTo().defaultContent();
    	
    }
    
    public void enterTickerInSearchBox(String tickerToType)
    {
    	searchSymbolBox.sendKeys(tickerToType);    	
    }
    
    public void clickOnFirstQuote()
    {
    	
    	getDriver().switchTo().frame(getDriver().findElement(By.xpath("//div[@id='xt-gen323']/iframe[contains(@src,'chain.html?')]")));
    	WebElement ele = getDriver().findElement(By.xpath("//tbody[1]/tr[2]/td[3]/div[contains(@id,'cask-')][1]"));
    	
   	Actions builder = new Actions(getDriver());
	System.out.println("FRESULT......  " + ele.getText());
	Action fQuote = builder.click(ele).build();
   
   	 fQuote.perform();
    	
    	//WebDriverWait wait = new WebDriverWait(getDriver(), 8000);
    	
    	//wait.until(ExpectedConditions
              //  .visibilityOfElementLocated(By.xpath("//tbody[1]/tr[2]/td[3]/div[contains(@id,'cask-')][1]")));
    	
    	//waitForElementToLoad("//tbody[1]/tr[2]/td[3]/div[contains(@id,'cask-')][1]");
    	
   	
//    	System.out.println("FRESULT......" + getDriver().findElement(By.xpath("//tbody[1]/tr[2]/td[3]/div[contains(@id,'cask-')][1]")).getText());
//    	
//    	getDriver().findElement(By.xpath("//tbody[1]/tr[2]/td[3]/div[contains(@id,'cask-')][1]")).click();
//    	
    	getDriver().switchTo().defaultContent();
    	
    } 
   
    
    public String getStockname()
    {
    	String stockName;
    	
    	stockName = stockLabel.getText();
    	
    	return stockName;
    }

    public void clickOnGoButton()
    {
    	goButton.click();    	
    }
    
    public void clickOnBuyMenu()
    {
    	 WebElement buyMenuItem = getDriver().findElement(By.id("x-menu-el-ext-comp-1070")) ;
    	System.out.println("BUY Txt :  " + buyMenuItem.getText());
    	buyMenuItem.click();
    }
    
    public void iterateFrames()
    {
    	List <WebElement> framesList = getDriver().findElements(By.xpath("//iframe"));
    	
    	//System.out.println("FRAME SIZE:    " + framesList.size());
    	
    	for(WebElement frame:framesList){
            //DO something with frame
    		System.out.println( frame.getAttribute("src"));
        }    	
    }
    
//    public String getDefaultOptionSeries()
//    {    	
//    	return (quoteLookupFromUI.g);    	
//    	
//    	//code to give u option format to be passed for webservice
//
//    }
    public HashMap<String, String> getQuoteDetailsFromOptionsChainGUI()
    {
    	
    	getDriver().switchTo().frame(getDriver().findElement(By.xpath("//iframe[contains(@src,'chain.html?')]")));
    	
    	HashMap<String, String> quoteLookupFromUI = new HashMap<String, String>();
    	
    	quoteLookupFromUI.put("series", fetchText("//tbody[1]/tr/td[contains(@class, 'series')]/div[contains(@class, 'at-money')]"));
    	
    	quoteLookupFromUI.put("callOI", fetchText("//tbody[1]/tr/td[contains(@class, 'coi')]/div[contains(@class, 'at-money')]"));
        quoteLookupFromUI.put("callVolume", fetchText("//tbody[1]/tr/td[contains(@class, 'cvol')]/div[contains(@class, 'at-money')]"));
    	quoteLookupFromUI.put("callAskPrice", fetchText("//tbody[1]/tr/td[contains(@class, 'cask')]/div[contains(@class, 'at-money clickable')]"));
        quoteLookupFromUI.put("callBidPrice", fetchText("//tbody[1]/tr/td[contains(@class, 'cbid')]/div[contains(@class, 'at-money clickable')]"));
        quoteLookupFromUI.put("callAskSize", fetchText("//tbody[1]/tr/td[contains(@class, 'callasksize')]/div[contains(@class, 'at-money')]"));
        quoteLookupFromUI.put("callBidSize", fetchText("//tbody[1]/tr/td[contains(@class, 'callbidsize')]/div[contains(@class, 'at-money')]"));
        

    	quoteLookupFromUI.put("putOI", fetchText("//tbody[1]/tr/td[contains(@class, 'poi')]/div[contains(@class, 'at-money')]"));
        quoteLookupFromUI.put("putVolume", fetchText("//tbody[1]/tr/td[contains(@class, 'pvol')]/div[contains(@class, 'at-money')]"));
    	quoteLookupFromUI.put("putAskPrice", fetchText("//tbody[1]/tr/td[contains(@class, 'pask')]/div[contains(@class, 'at-money clickable')]"));
        quoteLookupFromUI.put("putBidPrice", fetchText("//tbody[1]/tr/td[contains(@class, 'pbid')]/div[contains(@class, 'at-money clickable')]"));
    	quoteLookupFromUI.put("putAskSize", fetchText("//tbody[1]/tr/td[contains(@class, 'putasksize')]/div[contains(@class, 'at-money')]"));
        quoteLookupFromUI.put("putBidSize", fetchText("//tbody[1]/tr/td[contains(@class, 'putbidsize')]/div[contains(@class, 'at-money')]"));
    	
    	
    	//tbody[1]/tr/td[contains(@class, 'series')]/div[contains(@class, 'at-money')]       loc for first selected

    	return quoteLookupFromUI;
    }
    
    public String fetchText(String locator)
    {    	
    	
    	return (getDriver().findElement(By.xpath(locator)).getText());
    }
    
    public void verifyMenuItemsForCallAsk()
    {
    	WebElement buyCall = getDriver().findElement(By.id("x-menu-el-ext-comp-1070")) ;
    	WebElement buyAdvanced = getDriver().findElement(By.id("x-menu-el-ext-comp-1078")) ;
    	WebElement buyOCO = getDriver().findElement(By.id("x-menu-el-ext-comp-1072")) ;
    	WebElement exchangeLookup = getDriver().findElement(By.id("x-menu-el-ext-comp-1102")) ;
    	WebElement addToWatchList = getDriver().findElement(By.id("x-menu-el-ext-comp-1103")) ;
    	WebElement setAlert = getDriver().findElement(By.id("x-menu-el-ext-comp-1105")) ;
    	WebElement closeMenu = getDriver().findElement(By.id("x-menu-el-ext-comp-1104")) ;
     	System.out.println("BUY Txt :  " + buyCall.getText());
     	System.out.println("BUY Txt :  " + buyAdvanced.getText());
     	System.out.println("BUY Txt :  " + buyOCO.getText());
     	System.out.println("BUY Txt :  " + exchangeLookup.getText());
     	System.out.println("BUY Txt :  " + addToWatchList.getText());
     	System.out.println("BUY Txt :  " + setAlert.getText());
     	System.out.println("BUY Txt :  " + closeMenu.getText());
    }
    public void waitForElementToLoad(String elementId) {
    	
    	Selenium sel = new WebDriverBackedSelenium(getDriver(), "");
		try {
			int i = 0;
			while (!sel.isElementPresent(elementId)) {
				i++;
				System.out.println("loop    : "+i);
				Thread.sleep(3000);
				if (i == 9) {
					break;
				}
			}
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public void waitForPageToLoad(WebDriver driver) {
		try {
			// waitForPageToLoad1(driver);
			int counter = 0;
			Thread.sleep(1000);
			while (true) {
				String ajaxIsComplete = ((JavascriptExecutor) driver)
						.executeScript(" return Ajax.activeRequestCount")
						.toString();
				if (Integer.parseInt(ajaxIsComplete) == 0)
					break;
				if (counter == 100)
					break;
				Thread.sleep(100);
			}
		} catch (Exception e) {

		}

	}
}
