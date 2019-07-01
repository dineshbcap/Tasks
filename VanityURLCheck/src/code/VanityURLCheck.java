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

public class VanityURLCheck{
	//Declaring webdriver object
	WebDriver driver;
	
	//Declare Cell object, to interact with excel
	Cell cell = null;
	
	boolean present = true;
	
	//Creating object for GenericMethods class
	GenericMethods genericMethodObj = new GenericMethods();
	
	//Reading property file values
	private final String platform = genericMethodObj.fetchProperty("vanity", "platform").trim().equals("mobile") ? "m." : "www.";
	private final String environment = genericMethodObj.fetchProperty("vanity", "environment").trim().equals("") ? "URL_Empty" : genericMethodObj.fetchProperty("vanity", "environment").trim();
	
	//In test data vanity URL => the below mentioned environment will be replaced with => properties file 'environment' value
	private final String envToReplaceInVanity = "macys";
	
	private final String PASS = "Pass";
	private final String FAIL = "Fail";
	//Excel column number, the data going to be write
	private final int ACTUAL_URL_COLUMN_POSSITION = 5;
	private final int RESULT_COLUMN_POSSITION = 6;
	private final int screenShots_Failed_Testcases_COLUMN_POSSITION=7;

	@Test
	public void vanity_2015_sheet1() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Vanity_2015.xls";
		genericMethodObj.workSheet = "2015";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	

	@Test
	public void vanity_2015_sheet2() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Vanity_2015.xls";
		genericMethodObj.workSheet = "2014";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	

	@Test
	public void vanity_2014_sheet1() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Vanity_2014.xls";
		genericMethodObj.workSheet = "2014";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void vanity_2013_sheet1() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Vanity_2013.xls";
		genericMethodObj.workSheet = "2013";
		genericMethodObj.readFromExcel();
		urlCheck(genericMethodObj);
	}	
	
	@Test
	public void vanity_2012_sheet1() throws Exception {
		// read the test data value's from excel sheet
		genericMethodObj.workBookName = "Vanity_2012.xls";
		genericMethodObj.workSheet = "2012";
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
			genericMethodObj.removeHeaders(genericMethodObj);
			
			//------------------------------------------------------------------//
			//  Step-3:  Iterating the arraylist URLs => launch => verify	    //
			//------------------------------------------------------------------//
			for(@SuppressWarnings("unused") String testCaseID : genericMethodObj.testCaseID){
				present = true;
				//replace the vanity URL-> 'macys' into given environment
				//the below code will split based on ".com" string
				String[] vanitySplit = new String[10];
				if(genericMethodObj.vanityURL.get(temp).toLowerCase().contains(".com")){
					vanitySplit = genericMethodObj.vanityURL.get(temp).toLowerCase().split(".com");
					vanitySplit[0] = vanitySplit[0].replace(envToReplaceInVanity, environment);
				}
				//Some vanity URL not contains 'macys.com'. So, implemented this else block
				else{
					vanitySplit[0] = environment;
					vanitySplit[1] = genericMethodObj.vanityURL.get(temp).toLowerCase();
				}
				
				//Created vanityTemp variable to hold the updated vanity URL value
				String vanityTemp = vanitySplit[0] + vanitySplit[1];
				
				//navigate to the vanity URL
				driver.get("http://"+platform+ vanityTemp);
				
				//get the URL and store into the 'actualURL' variable
				String actualURL = driver.getCurrentUrl();
			
				//below piece of code used to get the status code
				int statusCode = genericMethodObj.getStatusCode(actualURL);
				
				//remove 'http', 'https', 'www' and 'www1' from the URL 
				actualURL = genericMethodObj.getUpdatedURL(actualURL);
				
				//to write the actual URL into the excel sheet 
				genericMethodObj.writeDataToExcel(genericMethodObj.workBookName, genericMethodObj.workSheet, temp+1, ACTUAL_URL_COLUMN_POSSITION, actualURL);
				
				//below condition for ensuring no blank expected URL. incase if any balnk expected then we make the status column as blank
				if(!((genericMethodObj.expectedResultStart.get(temp)=="") || (genericMethodObj.expectedResultStart.get(temp)== null))){
					
					//replace the expected URL-> -> 'macys' into given environment
					//the below code will split based on ".com" string
					String[] expectedURLSplit = new String[10];
					if(genericMethodObj.expectedResultStart.get(temp).toLowerCase().contains(".com")){
						//expectedURLSplit[1] = null;
						expectedURLSplit = genericMethodObj.expectedResultStart.get(temp).toLowerCase().split(".com");
						//some times actual URL starts part only contains upto '.com' part. So, when we try to access array's first element 
						//it will throws null pointer. So, handling here
						try{
							if(expectedURLSplit[1]==null){
								//see above comment
							}
						}
						catch(Exception e){
							present = false;
						}
						expectedURLSplit[0] = expectedURLSplit[0].replace("macys", environment);
					}
					//Some expected URL not contains 'macys.com'. So, implemented this else block
					else{
						expectedURLSplit[0] = environment;
						expectedURLSplit[1] = genericMethodObj.vanityURL.get(temp).toLowerCase();
					}
					
					//Storing later than ".com" in the expected URL into 'expectedURL' variable
					String expectedURLStartPart;
					if(present){
						expectedURLStartPart = expectedURLSplit[0] + expectedURLSplit[1];
					}else{
						expectedURLStartPart = expectedURLSplit[0];
					}
					
					//if both actual and expected same. then write the status as 'Pass' else 'Fail'(with color differentiate)
					if((statusCode==200) && (actualURL.equals(expectedURLStartPart + genericMethodObj.expectedResultEnd.get(temp)))){
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
