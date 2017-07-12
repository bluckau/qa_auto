package com.stg.bluckau.qa;

import static org.testng.Assert.assertEquals;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestChallengeTwo
{
	private static MainPage mainPage;
	private static String dataFileName;
	private static String columnsToRead;
	private String logLevel;
	private ITestContext context;

	@DataProvider(name = "webData")
	public static Object[][] webData()
	{
		System.out.println("**** get web data ****");
		System.out.println("File name: " + dataFileName + ";");
		Object[][] arrayObject = TestHelpers.getWebData(dataFileName, Integer.parseInt(columnsToRead));
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

	/*
	 * /**
	 *
	 * @param testingLogLevel Testing log level currently 1 through 10. passed
	 * automatically by testng from the xml parameter.
	 *
	 * @BeforeSuite
	 *
	 * @Parameters({ "testingLogLevel" }) public void setLogLevel(@Optional("3")
	 * String testingLogLevel) { this.testingLogLevel = testingLogLevel;
	 * System.out.println("Log level = " + this.testingLogLevel); }
	 *
	 */
	@Parameters({ "fileName", "columnsToRead" })
	@BeforeTest
	public void before(@Optional("src/test/resources/menus.xls") String fileName, @Optional("2") String columns)
	{
		System.out.println("Before Test");
		System.out.println("fileName = " + fileName);
		dataFileName = fileName;
		columnsToRead = columns;
		// System.err.println("Running Test " + ++testNumber);
	}

	@AfterTest
	public void after()
	{
		// System.err.println("Finished Running Test " + testNumber);
	}

	@BeforeClass
	public static void beforeClass()
	{
		mainPage = new MainPage();
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}

	@AfterSuite
	@Parameters({ "email" })
	public void sendMailAfterSuite(@Optional("brian.luckau@stgconsulting.com") String recipients)
	{
		System.out.println("After suite " + context.getSuite().getName());

		EmailHelper emailHelper = new EmailHelper(context);
		emailHelper.sendTestResults(recipients, "brian.luckau@stgconsulting.com", context);
	}

	@Test(dataProvider = "webData")
	public void testNav1(String menuOption, String validationString)
	{
		// System.err.println("Testing menu: " + menuOption);
		// System.err.println("ValidationString: " + validationString);

		mainPage.pageLoad();
		mainPage.goToMenu(menuOption, true);

		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
}