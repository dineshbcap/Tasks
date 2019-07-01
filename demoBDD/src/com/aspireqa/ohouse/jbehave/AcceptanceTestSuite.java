package com.aspireqa.ohouse.jbehave;

import java.util.logging.Logger;

import net.thucydides.jbehave.ThucydidesJUnitStories;

import com.aspireqa.ohouse.pages.BasePage;
import com.aspireqa.ohouse.utilities.Reader;

public class AcceptanceTestSuite extends ThucydidesJUnitStories {
	
	protected final Logger log = Logger.getLogger(getClass().getSimpleName()); 
	
	public AcceptanceTestSuite()
	{				
		log.info("Starting the script...!");
		Reader.initialize();
		
		BasePage.configureDriverProperty(getEnvironmentVariables().getProperty("webdriver.driver"));		
	}
	
	
}
