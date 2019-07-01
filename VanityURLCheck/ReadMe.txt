Overview Of The Vanity URL Project:

Setup: After import the project, we need to add the following jars(selenium-server-standalone-2.XX and POI)

Test Data:

1) Path : project_root=> src=> data

2) Each sheet contains '8' columns

	1) TestCaseID					=> Test case ID for easy mapping/ Identification	
	2) VanityURL	
	3) Production URL	
	4) Tracking info to append to redirect URL	
	5) ExpectedURL					=> Expected URL. It is actually 'Production URL' + 'Tracking info to append to redirect URL'
	6) ActualResultFromScript		=> After navigating to the vanity URL=> script will capture the URL=> update in this cell
	7) Status						=> Pass, Fail or Blank (Script will update after verify the actual and expected. 
										1) if actual = expected then 'Pass'
										2) if actual != expected then 'Fail'
										3) If expected balank then make this cell value as 'blank')
	8) Screenshoot  				=> Only captured for failure cases and creates the link in the relevent cell(easy to access screeshot)




Script:

1) project_root=> src=> code=> GenericMethods:
	1) Holding methods used for read/ write excel, property file read and setup webdriver

2) project_root=> src=> code=> ReadFromExcel:
	1) Holding utility methods used for read data from excel sheet

3) project_root=> src=> code=> VanityURLCheck:
	1) This is the main calss containing test and other stuffs
	2) All excel=> sheet will be taken care of different test

	3) Script Flow, Read test data from excel=> Navigate to the URL read from the excel=> Take the URL after navigation
		=> Verify with the expected value from the excel=> Update the Actual URL, status and Screenshoot(if case failed)
	4) Added comment in the script will give you more idea about the logic and flow used.




Properties file:

1) Mentioned environment and platform(If required can extend).
	environment = mention 'mobile' or 'desktop'
	platform    = mention the platform(qa or production env)