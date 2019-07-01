package com.aspireqa.ohouse.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class BasePage{
	
	public void click(WebDriver driver, WebElement element, String logger)
	{
		
	}
	
	public void locateElement()
	{
		
	}
	
	public static void configureDriverProperty(String browserName)
	{
		if (browserName.equalsIgnoreCase("iexplorer"))
		{
			System.setProperty("webdriver.ie.driver", "src/test/resources/com/aspireqa/ohouse/drivers/IEDriverServer.exe");

		}
		else if (browserName.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "src/test/resources/com/aspireqa/ohouse/drivers/chromedriver.exe");

		}
	}

}
