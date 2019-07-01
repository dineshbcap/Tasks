

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class CustomizedReporterUsingIReporter implements IReporter, ITestListener{
	
	private static PrintWriter  f_out;
	private static final String OUT_FOLDER  = "custom-test-report";
	
	public void generateReport(List<XmlSuite> arg0, List<ISuite> suites, String outdir){
		try {
			f_out = createWriter(OUT_FOLDER);
		} catch (IOException e) {
			e.printStackTrace();
		}

		startHtmlPage(f_out);
		generateTestExecutionStatus(suites, f_out);
		
		endHtmlPage(f_out);

		f_out.flush();
		f_out.close();
		
		
	}
	
	private static PrintWriter createWriter(String outdir) throws IOException
	{
		new File(outdir).mkdirs();

		return new PrintWriter(new BufferedWriter(new FileWriter(new File(OUT_FOLDER, "customized-test-run-report.html"))));
		
	}
	
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
		out.println("<td><b>Time [ms]</b></td>");
		out.println("<td><b>Calling-Class with Linenumber</b></td>");
		out.println("</tr>");
		out.println("<img src=\'Failed\\aa.jpg\' height=\"42\" width=\"42\">");
			
			
	}
	
	/** Finishes HTML Stream */
	private void endHtmlPage(PrintWriter out)
	{
	    out.println("</table>");
	    out.println("</tbody>");
		out.println("</body></html>");
	}
	
		
	/** Creates a table showing the highlights of each test method with links to the method details */
	private void generateTestExecutionStatus(List<ISuite> suites, PrintWriter out){
	    for (ISuite suite : suites) {
	      Map<String, ISuiteResult> r = suite.getResults();
	      for (ISuiteResult r2 : r.values()) {
	        ITestContext testContext = r2.getTestContext();
	        String testName = testContext.getName();
	        //resultSummary(testContext.getFailedConfigurations(), testName, "failed", " (configuration methods)");
	        resultSummary(testContext.getFailedTests(), testName, "failed", "", out);
	        //resultSummary(testContext.getSkippedConfigurations(), testName, "skipped", " (configuration methods)");
	        resultSummary(testContext.getSkippedTests(), testName, "skipped", "", out);
	        resultSummary(testContext.getPassedTests(), testName, "passed", "", out);
	        
	      }
	    }
	  }

			
			
			
	 private void resultSummary(IResultMap tests, String testname, String style, String details, PrintWriter out) {
	    if (tests.getAllResults().size() > 0) {
	    	System.out.println(tests.getAllResults().size());
	      for (ITestNGMethod method : getMethodSet(tests)) {
	        //ITestClass testClass = method.getTestClass();

	        long end = Long.MIN_VALUE;
	        long start = Long.MAX_VALUE;
	        for (ITestResult testResult : tests.getResults(method)) {
	        	out.println("<tr class=\"status_passed\" title=\"\" alt=\"\">");
	        	
	        	for(String name : testResult.getAttributeNames()){
	        		String temp[] = testResult.getAttribute(name).toString().split("####");
	        		
	        		
	        		for(String temp1 : temp){
	        			out.println("<td>"+ temp1 + "</td>");
	        		}
	        		
	        	}
	        	
	        	out.println("</tr>");
	        	/*<tr class="status_passed" title="" alt="">
				<td>isElementPresent</td>
				<td>//td[@class='yui-dt-loading']</td>
				<td>&nbsp;</td>
				<td>OK</td>
				<td>true</td>
				<td title="time delta reporting is alpha and subject to change" alt="time delta reporting is alpha and subject to change">7</td>
				<td>com.macys.stella.testutils.framework.DefaultAjaxSelenium#286</td>
			</tr>*/
	        	
	          if (testResult.getEndMillis() > end) {
	            end = testResult.getEndMillis();
	          }
	          if (testResult.getStartMillis() < start) {
	            start = testResult.getStartMillis();
	          }
	        }
	    }
	  }
	
		// Code to store and process testng groups …
	
		/*String browser = overview.getCurrentXmlTest().getParameter("browser");
		String browser_version = overview.getCurrentXmlTest().getParameter("browser_version");
		String platform = overview.getCurrentXmlTest().getParameter("platform");*/
	
	}
	


	/**
	 * @param tests
	 * @return
	 */
	private Collection<ITestNGMethod> getMethodSet(IResultMap tests) {
	  List<ITestNGMethod> r = new ArrayList<ITestNGMethod>(tests.getAllMethods());
	  Arrays.sort(r.toArray(new ITestNGMethod[r.size()]));
	  return r;
	}

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	
	public void onFinish(ITestContext context) {
		System.out.println("Reached");
		
		// TODO Auto-generated method stub
		
	}

}