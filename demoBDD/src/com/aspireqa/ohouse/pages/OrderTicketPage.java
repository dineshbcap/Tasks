package com.aspireqa.ohouse.pages;
import org.openqa.selenium.WebDriver;

import com.aspireqa.ohouse.utilities.LoggerUtility;

import net.thucydides.core.annotations.findby.By;
import net.thucydides.core.pages.PageObject;


public class OrderTicketPage extends PageObject{
	
	public OrderTicketPage(WebDriver driver) {
		super(driver);
	}
	
	private LoggerUtility logger = LoggerUtility.getInstance(OrderTicketPage.class);
	
	 public String getOrderDialogHeader()
	    {
		 return getDriver().findElement(By.xpath("//span[@class='x-window-header-text']")).getText();	    	 
	    }
	
	 public void enterStockQuantity(String qty)
	    {
		 getDriver().switchTo().frame("ticketWindowFrame");
		 getDriver().findElement(By.xpath("//div[@id='ext-gen87']/div/table/tbody/tr/td[6]/div/div/input")).click();
		 getDriver().findElement(By.xpath("//input[@id='ext-comp-1087']")).sendKeys(qty); 
	    }
	    
	    public void clickOnPreviewButton()
	    {    	
	    	getDriver().findElement(By.xpath("//button[@id='ext-gen35']")).click();    	    	
	    }
	    
	    public String getOrderSummary() 
	    {
	    	getDriver().findElement(By.xpath("//div[@id='ext-gen291']/span")).getAttribute("style"); 
	    	
	    	//log.info("TXT :     " + getDriver().findElement(By.xpath("//div[@id='ext-gen291']/span[@class='messageError']")).getText());
	    	return (getDriver().findElement(By.xpath("//div[@id='ext-gen291']/span[@class='messageError']")).getText());   	
	    }

}
