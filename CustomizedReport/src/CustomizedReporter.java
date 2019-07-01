

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomizedReporter implements ITestListener{
	
	private static PrintWriter  f_out;
	private static File screenshotDir; 
	private static final String OUT_FOLDER  = "custom-test-report";
	
	/**
	 * This function will execute before test start
	 */
	public void onStart(ITestContext context) {
		try {

			//used to get the test class name
			String className = context.getCurrentXmlTest().getClasses().get(0).getName();

			//used for creating required directories
			createRequiredDirectory(OUT_FOLDER, className);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * This function will execute after test execution
	 */
	public void onFinish(ITestContext context) {
		//used for create end html tags
		endHtmlPage(f_out);

		//used for write every thing in html file
		f_out.flush();
		f_out.close();
		
	}
	
	/**
	 * This function will execute once the current test execution pass
	 */
	public void onTestSuccess(ITestResult result) {
		try {
			//used to write result details in html
			generateTestExecution(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * This function will execute once the current test execution fail
	 */
	public void onTestFailure(ITestResult result) {
		try {
			//used to write result details in html
			generateTestExecution(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * This function will execute once the current test execution skiped
	 */
	public void onTestSkipped(ITestResult result) {
		try {
			//used to write result details in html
			generateTestExecution(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	
	private void createRequiredDirectory(String outdir, String className) throws IOException
	{
		//for get the current directory
		String workingdirectory = System.getProperty("user.dir");
		
		//used to creste directory
		screenshotDir = new File(workingdirectory +"/custom-test-report");
		screenshotDir.mkdir();
		
		screenshotDir = new File(workingdirectory +"/custom-test-report/Failure_Screenshot");
		screenshotDir.mkdir();
		
		screenshotDir = new File(workingdirectory + "/custom-test-report" + "/" + className);
		screenshotDir.mkdir();
		
		
	}
	
	private static PrintWriter createRequiredFile(String testName) throws IOException
	{
		return new PrintWriter(new BufferedWriter(new FileWriter(new File(screenshotDir, testName+".html"))));
	}

	/**
	 * Used for create basic tags and styles need for generate report
	 * @param out
	 */
	private void startHtmlPage(PrintWriter out)
	{
		out.println("<html>");
		out.println("<head>");
		out.println("<meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\"/><meta content=\"cache-control\" http-equiv=\"no-cache\"/><meta content=\"pragma\" http-equiv=\"no-cache\"/>");
		out.println("<style type=\"text/css\">");
		out.println("body, table {");
		out.println("font-family: Verdana, Arial, sans-serif;");
		out.println("font-size: 12;");
		out.println("}");
		
		out.println("table {");
		out.println("border-collapse: collapse;");
		out.println("border: 1px solid #ccc;");
		out.println("}");
		
		out.println("th, td {");
		out.println("padding-left: 0.3em;");
		out.println("padding-right: 0.3em;");
		out.println("}");
		
		out.println("a {");
		out.println("text-decoration: none;");
		out.println("}");
		
		out.println(".title {");
		out.println("font-style: italic;");
		out.println("}");
		
		out.println(".selected {");
		out.println("background-color: #ffffcc;");
		out.println("}");
		
		out.println(".status_done {");
		out.println("background-color: #eeffee;");
		out.println("}");
		
	    out.println(".status_passed {");
	    out.println("background-color: #ccffcc;");
	    out.println("}");
		
	    out.println(".status_failed {");
	    out.println("background-color: #ffcccc;");
	    out.println("}");
		
	    out.println(".status_maybefailed {");
	    out.println("background-color: #ffffcc;");
	    out.println("}");
		
	    out.println(".breakpoint {");
	    out.println("background-color: #cccccc;");
	    out.println("border: 1px solid black;");
	    out.println("}");
	    out.println("</style>");
	    out.println("<title>Test results</title>");
	    out.println("</head>");
	    out.println("<body>");
	    
	    out.println("<table border=\"1\">");
	    out.println("<tbody>");
	    out.println("<tr>");
	    out.println("<td><b>Selenium-Command</b></td>");
	    out.println("<td><b>Parameter-1</b></td>");
		out.println("<td><b>Parameter-2</b></td>");
		out.println("<td><b>Status</b></td>");
		out.println("<td><b>Screenshot</b></td>");
		out.println("<td><b>Calling-Class with Linenumber</b></td>");
		out.println("</tr>");
			
	}
	
	/**
	 * Finishes HTML Stream
	 * @param out
	 */
	private void endHtmlPage(PrintWriter out)
	{
	    out.println("</table>");
	    out.println("</tbody>");
		out.println("</body></html>");
	}
	
		
	/**
	 * Used to write test results in file
	 * @param result
	 * @throws IOException
	 */
	private void generateTestExecution(ITestResult result) throws IOException{
		f_out = createRequiredFile(result.getName());
		
		startHtmlPage(f_out);
		
		Object[] array = result.getAttributeNames().toArray();
		Arrays.sort(array);
		
    	for(Object name : array){
    		
	    		String temp[] = result.getAttribute((String) name).toString().split("####");
	    		if(temp[3].toLowerCase().contains("fail")){
	    			f_out.println("<tr class=\"status_failed\" title=\"\" alt=\"\">");
	    			
	    			String pathToScreenshot = "../Failure_Screenshot/"+temp[5]+".jpg";
	    			
	    			temp[4] = "<a href=\'" + pathToScreenshot + "\'> <img src=\'" + pathToScreenshot + "\' height=\"100\" width=\"100\"> </a>";
	    			
	    			
	    		}else{
	    			f_out.println("<tr class=\"status_passed\" title=\"\" alt=\"\">");
	    		}
	    		
	    		
	    		for(String temp1 : temp){
	    				f_out.println("<td>"+ temp1 + "</td>");

	    		}
	    		
	    	f_out.println("</tr>");
    	}
    	
    	
    	
    	
	}


}