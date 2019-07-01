package code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class GenericMethods {

	public String className = this.getClass().getName();
	public String workBookName = "Vanity";
	public String workBookName2 = "GN_Errors";
	public String workSheet = null;
	public String testCaseId = null;
	public String webSite = null;
	public Hashtable<String, Integer> excelHeaders = new Hashtable<String, Integer>();
	public Hashtable<String, Integer> excelrRowColumnCount = new Hashtable<String, Integer>();
	public ArrayList<String> testCaseID = new ArrayList<String>();
	public ArrayList<String> vanityURL = new ArrayList<String>();
	public ArrayList<String> nonVanityURL = new ArrayList<String>();
	public ArrayList<String> expectedResultStart = new ArrayList<String>();
	public ArrayList<String> expectedResultEnd = new ArrayList<String>();
	private final String host = fetchProperty("vanity", "host").trim().equals(
			"") ? "HOST_Empty" : fetchProperty("vanity", "host").trim();
	private final String port = fetchProperty("vanity", "port").trim().equals(
			"") ? "PORT_Empty" : fetchProperty("vanity", "port").trim();
	private final int maxWaitTime = Integer.parseInt(fetchProperty("vanity",
			"waitTime").trim().equals("") ? "30" : fetchProperty("vanity",
			"waitTime").trim());

	public HSSFSheet readFromExcel() {
		// boolean forms=false;
		HSSFSheet sheet = null;
		try {
			sheet = readExcel();
			// forms=true;
		} catch (Exception e) {
		}
		return sheet;
	}

	/**
	 * @Function:readFromExcel
	 * @Description:Fetch Data from Excel
	 * @return
	 */

	public HSSFSheet readExcel() {

		ReadFromExcel readTestData = new ReadFromExcel();
		testCaseId = testCaseId != null ? testCaseId.trim() : "";
		HSSFSheet sheet = null;
		try {
			// to initiate a connection to an excel sheet
			sheet = readTestData.initiateExcelConnection(workSheet,
					workBookName);
			// find number of rows and columns
			excelrRowColumnCount = readTestData.findRowColumnCount(sheet,
					excelrRowColumnCount);
			// to find excel header fields
			excelHeaders = readTestData.readExcelHeaders(sheet, excelHeaders,
					excelrRowColumnCount);
			HSSFRow row = null;
			HSSFCell cell = null;
			for (int r = 0; r < excelrRowColumnCount.get("RowCount"); r++) {
				row = sheet.getRow(r);
				if (row != null) {
					for (int c = 0; c < excelrRowColumnCount.get("ColumnCount");) {
						cell = row.getCell(excelHeaders.get("TestCaseID"));
						if (cell != null) {
							if (workBookName.equalsIgnoreCase("Non_Vanity.xls")) {
								testCaseID.add(readTestData
										.convertHSSFCellToString(row
												.getCell(excelHeaders
														.get("TestCaseID"))));
								nonVanityURL.add(readTestData
										.convertHSSFCellToString(row
												.getCell(excelHeaders
														.get("URL"))));
								break;
							} else {
								testCaseID.add(readTestData
										.convertHSSFCellToString(row
												.getCell(excelHeaders
														.get("TestCaseID"))));
								vanityURL.add(readTestData
										.convertHSSFCellToString(row
												.getCell(excelHeaders
														.get("VanityURL"))));
								expectedResultStart
										.add(readTestData.convertHSSFCellToString(row
												.getCell(excelHeaders
														.get("Production URL"))));
								expectedResultEnd
										.add(readTestData.convertHSSFCellToString(row.getCell(excelHeaders
												.get("Tracking info to append to redirect URL"))));
								break;
							}
						} else {
							break;
						}
					}
				}
			}
		} catch (RuntimeException e) {
			Assert.fail("Error During Execution; Execution Failed More details "
					+ e);
			e.printStackTrace();
		}
		return sheet;
	}

	/**
	 * Function To Write Data To Excel
	 * 
	 * @param workBookName
	 * @param workSheetName
	 * @param sectionName
	 * @param rowPossition
	 * @param colomnPossition
	 * @param valueToOverRide
	 * @return Boolean value
	 */
	public Boolean writeDataToExcel(String workBookName, String workSheetName,
			int rowPossition, int colomnPossition, String valueToOverRide) {

		try {
			File dir1 = new File(".");
			String strBasePath = dir1.getCanonicalPath();
			String filePath = strBasePath + File.separator + "src"
					+ File.separator + "data" + File.separator + workBookName;
			FileInputStream file = new FileInputStream(new File(filePath));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheet(workSheetName);
			HSSFCell cell = null;

			// Update the value of cell
			cell = sheet.getRow(rowPossition).getCell(colomnPossition);
			if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				cell = sheet.getRow(rowPossition).createCell(colomnPossition,
						Cell.CELL_TYPE_STRING);
			}
			if (colomnPossition == 7 || colomnPossition == 4) {
				HSSFCellStyle hlinkstyle = workbook.createCellStyle();
				HSSFFont hlinkfont = workbook.createFont();
				hlinkfont.setUnderline(HSSFFont.U_SINGLE);
				hlinkfont.setColor(HSSFColor.BLUE.index);
				hlinkstyle.setFont(hlinkfont);
				HSSFHyperlink screenshot_link = new HSSFHyperlink(
						HSSFHyperlink.LINK_FILE);
				screenshot_link.setAddress(valueToOverRide);
				cell.setHyperlink(screenshot_link);
				cell.setCellStyle(hlinkstyle);
			}
			cell.setCellValue(valueToOverRide);

			if (valueToOverRide.equalsIgnoreCase("pass")) {
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(cellStyle);
			} else if (valueToOverRide.equalsIgnoreCase("fail")) {
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFillForegroundColor(HSSFColor.RED.index);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(cellStyle);
			}

			file.close();
			FileOutputStream outFile = new FileOutputStream(new File(filePath));
			workbook.write(outFile);
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public WebDriver setupDriver() {
		WebDriver driver = null;
		try {
			//driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub/"), DesiredCapabilities.firefox() );
			driver = new FirefoxDriver();
			
			/*System.setProperty("webdriver.chrome.driver","D:\\Selenium_Useful\\chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ArrayList<String> switches = new ArrayList<String>();
			switches.add("--ignore-certificate-errors");
			capabilities.setCapability("chrome.switches", switches);
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub/"), capabilities);*/			
			
			driver.manage().timeouts()
					.implicitlyWait(maxWaitTime, TimeUnit.SECONDS);
			driver.manage().timeouts()
					.pageLoadTimeout(maxWaitTime, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}

	/**
	 * fetchProperty function is used to fetch the property from a property file
	 * 
	 * @param fileName
	 *            - Name of the Property File Name
	 * @param propertyKey
	 *            - Key using which the value needs to be searched for
	 * @since Apr 01, 2010
	 */
	public String fetchProperty(String fileName, String propertyKey) {
		int check = 0;
		String propertyValue = "";
		FileInputStream in = null;
		fileName = fileName != null ? fileName.trim() : "";
		propertyKey = propertyKey != null ? propertyKey.trim() : "";

		if (fileName.equals("") && propertyKey.equals("")) {
			return propertyValue;
		} else if (fileName.equals("")) {
			return propertyValue;
		} else if (propertyKey.equals("")) {
			return propertyValue;
		} else {
			if (!fileName.contains(".properties")) {
				fileName = fileName + ".properties";
			}

			try {
				while (check == 0) {
					check = 1;
					File file = new File(fileName);
					if (file.exists()) {
						Properties properties = new Properties();
						in = new FileInputStream(file);
						properties.load(in);
						propertyValue = properties.getProperty(propertyKey);
					} else {
						check = 0;
						break;
					}
				}
				in.close();
			} catch (IOException e) {
				try {
					in.close();
				} catch (IOException e1) {

				}
			} finally {
				try {
					in.close();
				} catch (IOException e) {

				}
			}
		}
		return propertyValue;
	}

	/**
	 * function used to get the status code of the passing URL
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public int getStatusCode(String url) throws IOException {
		System.setProperty("https.proxyHost", host);
		System.setProperty("https.proxyPort", port);
		System.setProperty("http.proxyHost", host);
		System.setProperty("http.proxyPort", port);
		URL objURL = new URL(url);
		CookieHandler.setDefault(new CookieManager(null,CookiePolicy.ACCEPT_ALL));
		HttpURLConnection connection = (HttpURLConnection) objURL
				.openConnection();
		connection.setInstanceFollowRedirects(true);
		connection.setUseCaches(false);
		connection.connect();
		return connection.getResponseCode();
	}

	/**
	 * Used to remove 'http', 'https', 'www' and 'www1' from the URL
	 * 
	 * @param actualURL
	 * @return
	 */
	public String getUpdatedURL(String actualURL) {
		 /* 
		  * due to validation part creates some impacts in the "http://" or
		 "https://" and "www" or "www1."
		 Some test data had these values and some may not.
		 So, Removed all these values in the test data
		 And below 4 lines of code used for remove these stuffs from actual
		 URL too
		  */
		actualURL = actualURL.replace("https://", "");
		actualURL = actualURL.replace("http://", "");
		actualURL = actualURL.replace("www1.", "");
		actualURL = actualURL.replace("www.", "");
		return actualURL;
	}

	/**
	 * Remove headers from the arraylist objects
	 * @param genericMethodObj
	 */
	public void removeHeaders(GenericMethods genericMethodObj) {
		int headerPossition = 0;
		genericMethodObj.testCaseID.remove(headerPossition);
		genericMethodObj.vanityURL.remove(headerPossition);
		genericMethodObj.expectedResultStart.remove(headerPossition);
		genericMethodObj.expectedResultEnd.remove(headerPossition);
	}
}
