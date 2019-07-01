package code;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GlobalNavigation {
	
	//Specify the environment want to be execute. (ie: MCOM / BCOM)
	String env="MCOM";
	
	//Used ternary operator. If env is 'MCOM' then 'MCOM production' will be execute else 'BCOM Production' will be execute
    String URL = env.equals("MCOM")?"http://m.qa16codemacys.fds.com":"http://m.bloomingdales.com";
    
    String globalNavButton =  env.equals("MCOM")?"mb-j-nav-button":"mb-j-nav-button-icon";
    
	GenericMethods genericMethodObj = new GenericMethods();
	
	//To hold the root level child's of the 'Shop' category 
	static ArrayList<String> rootList =new ArrayList<String>();
	
	//To hold the sub categories in the shop
	LinkedList<LinkedList<String>> menus;
	LinkedList<String> childTab ;
	
	//Declare the required variables
	WebDriver driver;	

	private static int parentPositionToClick = 0;
	
	private int maxWaitTime = Integer.parseInt(genericMethodObj.fetchProperty("vanity", "waitTime").trim().equals("") ? "30" : genericMethodObj.fetchProperty("vanity", "waitTime").trim());
	
	private String tempTabName;
	
	@Test
	public void test() throws Exception
	{
		 GN(genericMethodObj);
	}
	
	public void GN(GenericMethods genericMethodObj) throws Exception {
		driver = genericMethodObj.setupDriver();
		Assert.assertNotNull(driver, "Could not initialize a driver");

		//navigate to the vanity URL
		driver.get(URL);
		
		if(driver.findElements(By.id("marketorial-close")).size()>0)
			driver.findElement(By.id("marketorial-close")).click();
		//Clicking on hamburger icon on the home page
		driver.findElement(By.id("mb-j-nav-button")).click();
		System.out.println("Clicked on Hamburger icon");
		
		//Verifying Navigation menu been loaded or not
		Assert.assertTrue( driver.findElement(By.xpath("//ul[@id='mb-j-nav-menu']")).isDisplayed(),"Navigation menu not displayed even on tapping Hamburger icon");
		System.out.println("Navigation menu displayed successfully on clicking Hamburger icon"); 
		
		//Verifying for the existence of shop tab
		Assert.assertTrue(driver.findElement(By.xpath("//li[@id='shop']/a")).isDisplayed(), "Shop tab not present in the navigation menu");
		System.out.println("Shop tab present in the navigation menu");
		
		//Clicking on Shop tab from Navigation menu
		driver.findElement(By.xpath("//li[@id='shop']/a")).click();
		System.out.println("Successfully clicked on Shop tab from Navigation menu");
		
		startNavigation();
	}
	
	private void startNavigation() throws InterruptedException, IOException {
		//Getting the count of child elements
		int idList = driver.findElements(By.xpath("//ul[@id='mb-j-nav-menu']/li[starts-with(@class,'child')]/a")).size();
	    System.out.println("The total no. of tabs displayed are: "+idList);

	    //Taking all the child tabs into arraylist(rootList)
		for(int rootIndex=1;rootIndex<=idList;rootIndex++)
		{
			String var = driver.findElement(By.xpath("//ul[@id='mb-j-nav-menu']/li[not(contains(@class,'headerRow'))]["+rootIndex+"]/a")).getText();
			rootList.add(var);
		}
		
		for (int index = 0; index < rootList.size(); index++) {
			menus = new LinkedList<>();
			drillChilds(rootList.get(index));
		}
	}
	
	
	private void drillChilds(String tabName) throws InterruptedException, IOException{
		
		//few category name holds the "'" in between the string, so, we have to handle this type of escape sequence characters to avoid exception 
		//System.out.println("Before replacing: "+tempTabName);
		tempTabName = tabName.replace("'", "\'");
		
		//Temporary if block.. because infinitive loop creates in few categories(like 'active pants' category).. So, add those category name in the below 'if block' to skip those from the execution
		if((tempTabName.equalsIgnoreCase("Activewear"))
				||(tempTabName.equalsIgnoreCase("Buy More, Save More"))
				
				||(tempTabName.equalsIgnoreCase("Bras & Lingerie"))
				||(tempTabName.equalsIgnoreCase("Lingerie Brands"))
				||(tempTabName.equalsIgnoreCase("Shapewear"))
				||(tempTabName.equalsIgnoreCase("Pajamas & Robes"))
				||(tempTabName.equalsIgnoreCase("Full Busted Bras"))
				
				||(tempTabName.equalsIgnoreCase("Juniors Bras"))
				||(tempTabName.equalsIgnoreCase("Juniors Panties"))
				||(tempTabName.equalsIgnoreCase("Juniors' Pajamas & Robes"))
				||(tempTabName.equalsIgnoreCase("Shop Shapewear"))
				
				||(tempTabName.equalsIgnoreCase("Prom 2015"))
				||(tempTabName.equalsIgnoreCase("Juniors' Clothing"))
				||(tempTabName.equalsIgnoreCase("Shop by price"))
				
				||(tempTabName.equalsIgnoreCase("American Rag"))){
				
			System.out.println("This is a bug in i.e.,recursively pointing the parent category on tapping the Child) ::: "+menus.getLast().getFirst());
					
			//screenshot for the failures
			String CurrentDirectory = System.getProperty("user.dir");
			System.out.println(CurrentDirectory);
			String currentTime = new SimpleDateFormat("yyyy_MMM_dd_hh_mm_ss").format(new Date());
								
			File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String screenShotPath=CurrentDirectory+"\\GN_Error_Pages\\"+menus.getLast().get(0)+"_Time_"+currentTime+".png";
			FileUtils.copyFile(screenShot, new File(screenShotPath));
			
			System.out.println(menus.toString());	
			menus.getLast().removeFirst();
			if(menus.getLast().size()>0){
				String name = menus.getLast().get(0);
				System.out.println(name);
				//Used recursive function to drill down into the sub categories
				drillChilds(name);
			}
		}
		
		helperAjaxWait();
		
		//tap on the category, based on the passed parameter
		driver.findElement(By.xpath("//ul[@id='mb-j-nav-menu']/li[starts-with(@class,'child')]/a[contains(text(),\""+tempTabName+"\")]")).click();
		try{
			//we are storing the values in the linked list. Because it has insertion/ deletion faster
			//And having useful methods like getFirst(), getLast, removeFirst() and removeLast()
			//Below initialized linked list to hold the child categories => And again we will store all these linkedlist into another linkedlist => 
			//once done with the category we are deleting one by one 
			childTab = new LinkedList<String>();
			
			helperAjaxWait();
			
			//verify the global nav opened or not to ensure the last category reached browse page
			if(driver.findElement(By.id(globalNavButton)).getAttribute("class").equals("icon-selectedMenuButton")){
				//Again get the child count of newly opened category
				int size = driver.findElements(By.xpath("//ul[@id='mb-j-nav-menu']/li[starts-with(@class,'child')]")).size();
			     
				//iterating and stored into the linkedlist
			    for(int rootIndex=1;rootIndex<=size;rootIndex++){
						String var = driver.findElement(By.xpath("//ul[@id='mb-j-nav-menu']/li[starts-with(@class,'child')]["+rootIndex+"]/a")).getText();
						childTab.add(var);
				}
			     
			    menus.add(childTab);

			    String name = childTab.getFirst();
			    //Used recursive function to drill down in       to the sub categories 
			    drillChilds(name);
			}else{
				if(driver.getPageSource().contains("having some technical issues") ||driver.getPageSource().contains("This feature is not supported")
						||driver.getPageSource().contains("Sorry, this page is no longer available."))
				{
					//screenshot for the failures
					String CurrentDirectory = System.getProperty("user.dir");
					String currentTime = new SimpleDateFormat("yyyy_MMM_dd_hh_mm_ss").format(new Date());
										
					File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					String screenShotPath=CurrentDirectory+"\\GN_Error_Pages\\"+menus.getLast().get(0)+"_Time_"+currentTime+".png";
					FileUtils.copyFile(screenShot, new File(screenShotPath));
				}
				helperAjaxWait();
				Assert.assertTrue(driver.getCurrentUrl().contains(URL), "Clicking on '"+tempTabName+"' tab redirected the user to web application site which is a bug");
				if(!driver.getCurrentUrl().contains(URL))
				{
					driver.navigate().back();
					helperAjaxWait();
				}
				
				//Clicking on hamburger icon on the home page
				driver.findElement(By.id("mb-j-nav-button")).click();
				
				helperAjaxWait();
				
				//Here in if block we are verifying in global nav, the same section selected or not
				if(driver.findElement(By.className("currentRow")).getText().equalsIgnoreCase(tabName)){
					//below xpath count will give the number of list we are having above the current category
					parentPositionToClick = driver.findElements(By.xpath("//li[contains(@class,'headerRow currentRow')]/preceding-sibling::li")).size(); //li[@class='headerRow currentRow']/preceding-sibling::li
					
					//tap on the parent category(immediate preceding sibling of the selected category)
					driver.findElement(By.xpath("//li[starts-with(@class,'headerRow')]/following-sibling::li["+(parentPositionToClick-1)+"]/a")).click();
					
					helperAjaxWait();
					
					//remove the first value in the last list. Because, we done with that part 
					menus.getLast().removeFirst();
					
					//here checking weather last list size greater than '0', to ensure no more child's there to click
					if(menus.getLast().size()>0){
						String name = menus.getLast().getFirst();
						//Used recursive function to drill down into the sub categories
						drillChilds(name);//verified this block logic
					}else{
						//So, if it reached this else part, we can come to know like we are done with all iterate over the last list
						menus.removeLast();
						menus.getLast().removeFirst();
						
						//below xpath count will give the number of list we are having above the current category
						parentPositionToClick = driver.findElements(By.xpath("//li[contains(@class,'headerRow currentRow')]/preceding-sibling::li")).size(); //li[@class='headerRow currentRow']/preceding-sibling::li
						
						//tap on the parent category(immediate preceding sibling of the selected category)
						driver.findElement(By.xpath("//li[@class='headerRow']/following-sibling::li["+(parentPositionToClick-1)+"]/a")).click();
						
						helperAjaxWait();
						
						//here checking any more category need to drill down
						boolean tempBool = true;
						while(tempBool){
							if(menus.size()>0){
								if(menus.getLast().size()>0){
									tempBool = false;
									String name = menus.getLast().getFirst();
									//Used recursive function to drill down into the sub categories
									drillChilds(name);
								}
								else{
									//if reached this else block mean, we drill down all the sub category of the parent.
									//So, we can remove the parent list from the main list
									//not verified this block logic
									//below xpath count will give the number of list we are having above the current category
									parentPositionToClick = driver.findElements(By.xpath("//li[contains(@class,'headerRow currentRow')]/preceding-sibling::li")).size(); //li[@class='headerRow currentRow']/preceding-sibling::li
									
									//tap on the parent category(immediate preceding sibling of the selected category)
									driver.findElement(By.xpath("//li[@class='headerRow']/following-sibling::li["+(parentPositionToClick-1)+"]/a")).click();
									
									helperAjaxWait();
									
									//remove the last parent from the list
									menus.removeLast();
									menus.getLast().removeFirst();
									tempBool = true;
								}
							}
						}
					}
				}
				//else some other category wrongly selected after navigation
				else{
					//If this block reached bug in the navigation
					//Now, only we displayed in console the things went wrong. We can enhance this in future
					
					System.out.println("There is bug in(pointing different category while tap on global nav) ::: "+menus.getLast().getFirst());
					
					//screenshot for the failures
					String CurrentDirectory = System.getProperty("user.dir");
        			String currentTime = new SimpleDateFormat("yyyy_MMM_dd_hh_mm_ss").format(new Date());
        								
        			File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        			String screenShotPath=CurrentDirectory+"\\GN_Error_Pages\\"+menus.getLast().get(0)+"_Time_"+currentTime+".png";
        			FileUtils.copyFile(screenShot, new File(screenShotPath));
					
					
					//Resume the drill down process, navigating back. So, it will go to the category which worked fine
					driver.navigate().back();
					helperAjaxWait();
					
					//Removing the category which caused problem
					menus.getLast().removeFirst();
					
					//if the global nav is not opened, this block will open the global nav
					if(driver.findElement(By.id(globalNavButton)).getAttribute("class").equals("icon-unselectedMenuButton")){
						//Clicking on hamburger icon on the home page
						driver.findElement(By.id("mb-j-nav-button")).click();
						helperAjaxWait();
					}
						
					//After resume the process, We have to do the same logic what we implemented in the 'if' block
					if(menus.getLast().size()>0){
						String name = menus.getLast().getFirst();
						//Used recursive function to drill down into the sub categories
						drillChilds(name);
					}else{
						menus.removeLast();
						menus.getLast().removeFirst();
						if(menus.size()>0){
							if(menus.getLast().size()>0){
								String name = menus.getLast().getFirst();
								//Used recursive function to drill down into the sub categories
								drillChilds(name);
							}
						}
					}
				}
			}
	
		}catch(Exception exce){
			
			System.out.println("Script got intrupted due to :: "+ exce.getMessage());
			
			//screenshot for the failures
			String CurrentDirectory = System.getProperty("user.dir");
			String currentTime = new SimpleDateFormat("yyyy_MMM_dd_hh_mm_ss").format(new Date());
								
			File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String screenShotPath=CurrentDirectory+"\\GN_Error_Pages\\Script_Intrupt_Time_"+currentTime+".png";
			FileUtils.copyFile(screenShot, new File(screenShotPath));
		}
	}
	
	/**
	 * this method is used for wait the page till ajax operation to complete
	 * @throws InterruptedException
	 */
	private void helperAjaxWait() throws InterruptedException{
		int tempCount = 0;
		//we used thread.sleep '500' mili second only. so, multiplying wait time by 2
		//maxWaitTime = maxWaitTime *2;
		if (driver instanceof JavascriptExecutor) {
			boolean tempBoolean = true;
			long ajaxCount;
			while(tempBoolean&&(tempCount<maxWaitTime)){
				ajaxCount = (long) ((JavascriptExecutor) driver).executeScript("return jQuery.active");
				if(ajaxCount==0){
					tempBoolean = false;
				}
				Thread.sleep(1000);
				tempCount++;
			}
		}
	}
}




