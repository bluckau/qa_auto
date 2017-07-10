package com.stg.bluckau.qa;

import static org.testng.Assert.assertEquals;

import java.io.File;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestChallengeThree
{
	private static MainPage mainPage;
	private static String dataFileName;
	private static int columnsToRead;
	private String testingLogLevel;
	private ITestContext context;
	// ----THIS IS THE DATA PROVIDER----
	@DataProvider(name = "webData")
	public static Object[][] webData()
	{
		System.out.println("**** running data provider ****");
		System.out.println("File name: " + dataFileName + ";");
		if (new File(dataFileName).exists())
		{
			System.out.println("file exists");
		} else
		{
			System.out.println("File " + dataFileName + "not exist");
			System.exit(99);
		}
		Object[][] theArray = TestHelpers.getWebData(dataFileName, columnsToRead);

		// TestHelpers.printArray(theArray, columnsToRead);
		System.out.println("dp: Got array of size " + theArray.length);
		System.out.println(theArray.length);
		System.out.println(theArray[0].length);
		return theArray;
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

	@Parameters({ "fileName", "columnsToRead" })
	@BeforeTest
	public void before(@Optional("submenus.xls") String name, @Optional("3") String columns)
	{
		System.out.println("before");
		dataFileName = name;
		columnsToRead = Integer.parseInt(columns);
		// System.out.println("Before Test");
		System.out.println("fileName = " + dataFileName);
		System.out.println("Columns " + columnsToRead);
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
		emailHelper.sendTestResults(recipients, "brian.luckau@stgconsulting.com", context, testingLogLevel);
	}

	@Test(dataProvider = "webData")
	public void testNav1(String menuOption, String subMenu, String validationString)
	{
		mainPage.pageLoad();
		System.out.print("menu " + menuOption);
		System.out.println("  subMenu " + subMenu);
		// System.out.println("validationString " + validationString);

		System.out.printf("gotoSubMenu(%s, %s);", menuOption, subMenu);
		mainPage.goToSubMenu(menuOption, subMenu);
		Automation.snooze(5);
		System.out.println("/n               " + Automation.getPageTitle() + "\n");
		assertEquals(Automation.getPageTitle(), validationString);

	}
}
