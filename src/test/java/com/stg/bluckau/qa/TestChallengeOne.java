package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.reporters.*;


@Listeners(RealTimeListener.class)

/**
 * Test Class for challenge one, get the page title
 *
 * @author Brian Luckau
 *
 */
public class TestChallengeOne
{
	private static MainPage mainPage;
	private static String dataFileName;
	private static int columnsToRead;
	// TOOD: Make this more dynamic so we dont
	// have to specify columns to read

	private String testingLogLevel;
	private ITestContext context;

	/**
	 *
	 * @return a two dimensional array of the cricital data for the test at hand
	 */
	@DataProvider(name = "webData")
	public static Object[][] webData()
	{
		System.out.println("**** get web data ****");
		Object[][] arrayObject = TestHelpers.getWebData(dataFileName, columnsToRead);
		return arrayObject;
	}

	/**
	 *
	 * @param context
	 *            the ItestContext object (which is automatically passed by
	 *            TestNG)
	 */
	@BeforeSuite
	public void processItestContext(ITestContext context)
	{
		this.context = context;
		System.out.println("Before Suite");
		System.out.println("Running XML Suite -- " + context.getSuite().getName());
	}

	/**
	 *
	 * @param testingLogLevel
	 *            Testing log level currently 1 through 10. passed automatically
	 *            by testng from the xml parameter.
	 */
	@BeforeSuite
	@Parameters({ "testingLogLevel" })
	public void setLogLevel(@Optional("3") String testingLogLevel)
	{
		this.testingLogLevel = testingLogLevel;
		System.out.println("Log level = " + this.testingLogLevel);
	}

	/**
	 *
	 * @param fileName
	 *            The file name to read of the test data
	 * @param columns
	 *            How many columns to read int he spreadsheet
	 */
	@Parameters({ "fileName", "columns" })
	@BeforeClass
	public void before(@Optional("url_verification.xls") String fileName, @Optional("2") String columns)
	{
		System.out.println("Class Test");
		System.out.println("data file name to read is " + fileName);
		dataFileName = fileName;
		columnsToRead = Integer.parseInt(columns);
	}


	@BeforeClass
	public static void beforeClass()
	{

		mainPage = new MainPage();
	}


	@AfterClass
	public static void afterClass()
	{

		Automation.quit();
		Automation.driver = null;
	}

	/**
	 *
	 * @param recipients
	 *            The recipients list for email, passed by testng
	 */
	@AfterSuite
	@Parameters({ "email" })
	public void sendMailAfterSuite(@Optional("brian.luckau@stgconsulting.com") String recipients)
	{
		// RealTimeListener rl = new RealTimeListener();
		// System.out.println("rl dot toSTring" + rl.);
		System.out.println("After suite " + context.getSuite().getName());

		EmailHelpers eh = new EmailHelpers();
		eh.sendTestResults(recipients, "brian.luckau@stgconsulting.com", context, testingLogLevel);
	}

	/**
	 *
	 * @param pageURL
	 *            The page URL to test
	 * @param verificationText
	 *            Verification Text to make sure the title was correct
	 */
	@Test(dataProvider = "webData")
	public void testTitle(String pageURL, String verificationText)
	{
		System.out.println("****testTitle***");
		mainPage.pageLoad();
		String title = Automation.getPageTitle();
		if (verificationText != null)
			assertEquals(title, verificationText);
	}

}