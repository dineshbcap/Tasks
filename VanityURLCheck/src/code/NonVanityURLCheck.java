package code;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NonVanityURLCheck{
	//Declaring web driver object
	WebDriver driver;
	
	//Declare Cell object, to interact with excel
	Cell cell = null;
	
	boolean present = true;
	//Creating object for GenericMethods class
	GenericMethods genericMethodObj = new GenericMethods();
	
	//Reading property file values
	private final String platform = genericMethodObj.fetchProperty("vanity", "platform").trim().equals("mobile") ? "m." : "www.";
	private final String environment = genericMethodObj.fetchProperty("vanity", "environment").trim().equals("") ? "URL_Empty" : genericMethodObj.fetchProperty("vanity", "environment").trim();
	private final String PASS = "Pass";
	private final String FAIL = "Fail";
	//Excel column number, the data going to be write
	private final int ACTUAL_URL_COLUMN_POSSITION = 2;
	private final int RESULT_COLUMN_POSSITION = 3;
	private final int screenShots_Failed_Testcases_COLUMN_POSSITION=4;

	@Test
	public void Non_Vanity_CategoryID() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "CategoryID";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	

	@Test
	public void Non_Vanity_ProductID() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "ProductID";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void Non_Vanity_LegacyURL() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "LegacyURL";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void Non_Vanity_StorePagesLegacyURL() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "StorePagesLegacyURL";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void Non_Vanity_BOPSLandingPage() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "BOPSLandingPage";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void Non_Vanity_FreeShippingLandingPage() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "FreeShippingLandingPage";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
		
	@Test
	public void Non_Vanity_Facet_Browse() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "Facet_Browse";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	

	@Test
	public void Non_Vanity_Facet_BOPS() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "Facet_BOPS";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void Non_Vanity_Facet_Search() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "Facet_Search";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void Non_Vanity_WhiteListExampleURL() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "WhiteListExampleURL";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void Non_Vanity_WhiteListExampleRequests() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "WhiteListExampleRequests";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void Non_Vanity_GoToCategories() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "GoToCategories";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	

	@Test
	public void Non_Vanity_SEO_URL() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "SEO_URL";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void Non_Vanity_SpecialCharacters() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "SpecialCharacters";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void Non_Vanity_Skava() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "Skava";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void Non_Vanity_CMS() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Non_Vanity.xls";
		genericMethodObj.workSheet = "CMS";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	private void urlCheck(GenericMethods genericMethodObj) throws IOException{	
		try {
			
			//------------------------------------------------------------------//
			//  Step-1:  Initialize Driver Browser                              //
			//------------------------------------------------------------------//
			driver = genericMethodObj.setupDriver();
			Assert.assertNotNull(driver, "Could not initialize a driver");
			
			//------------------------------------------------------------------//
			//  Step-2:  Launch the website in the device or desktop browser    //
			//------------------------------------------------------------------//
			//initialize '0' for every test case and it is used for getting the values from the arraylist
			int temp =0;
			
			//deleting the header values. Because, while iterating it create some impacts
			genericMethodObj.testCaseID.remove(temp);
			genericMethodObj.nonVanityURL.remove(temp);
			
			//------------------------------------------------------------------//
			//  Step-3:  Iterating the arraylist URLs => launch => verify	    //
			//------------------------------------------------------------------//
			for(@SuppressWarnings("unused") String testCaseID : genericMethodObj.testCaseID){
				present = true;
				//replace the Test data URL-> 'Environment detail ' into given environment
				//the below code will split based on ".com" string
				String[] nonVanitySplit = new String[10];
				if(genericMethodObj.nonVanityURL.get(temp).toLowerCase().contains(".com")){
					nonVanitySplit = genericMethodObj.nonVanityURL.get(temp).toLowerCase().split(".com");
					nonVanitySplit[0] = nonVanitySplit[0].replace(nonVanitySplit[0], environment);
				}
				//Some vanity URL not contains 'macys.com'. So, implemented this else block
				else{
					nonVanitySplit[0] = environment;
					nonVanitySplit[1] = genericMethodObj.nonVanityURL.get(temp).toLowerCase();
				}
				
				//Created vanityTemp variable to hold the updated vanity URL value
				String vanityTemp = nonVanitySplit[0] + nonVanitySplit[1];
				
				//navigate to the vanity URL
				driver.get("http://"+platform+ vanityTemp);
				
				//get the URL and store into the 'actualURL' variable
				String actualURL = driver.getCurrentUrl();
			
				//below piece of code used to get the status code
				int statusCode = genericMethodObj.getStatusCode(actualURL);
				
				//to write the actual URL into the excel sheet 
				genericMethodObj.writeDataToExcel(genericMethodObj.workBookName, genericMethodObj.workSheet, temp+1, ACTUAL_URL_COLUMN_POSSITION, actualURL);
				
				//if both actual and expected same. then write the status as 'Pass' else 'Fail'(with color differentiate)
				if(statusCode==200){
					genericMethodObj.writeDataToExcel(genericMethodObj.workBookName, genericMethodObj.workSheet, temp+1, RESULT_COLUMN_POSSITION, PASS);
				}else{
					genericMethodObj.writeDataToExcel(genericMethodObj.workBookName, genericMethodObj.workSheet, temp+1, RESULT_COLUMN_POSSITION, FAIL);
					String CurrentDirectory = System.getProperty("user.dir");
					String currentTime = new SimpleDateFormat("yyyy_MMM_dd_hh_mm_ss").format(new Date());
					File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					String screenShotPath=CurrentDirectory+"\\Screenshots_Failed_Testcases\\"+genericMethodObj.workBookName+"_WorkSheet_"+genericMethodObj.workSheet+"_"+genericMethodObj.testCaseID.get(temp)+"_Time_"+currentTime+".png";
					FileUtils.copyFile(screenShot, new File(screenShotPath));
					genericMethodObj.writeDataToExcel(genericMethodObj.workBookName, genericMethodObj.workSheet, temp+1, screenShots_Failed_Testcases_COLUMN_POSSITION, screenShotPath);
				}
				temp++;
			}
		} catch (Exception e) {
			Assert.fail("Some error occured during execution");
		} finally {
			driver.quit();
		}
	}
	
}
