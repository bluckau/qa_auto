package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestChallengeOne
{
	private static MainPage mainPage;
	private static String dataFileName;
	private static int columnsToRead;

	@DataProvider(name = "webData")
	public static Object[][] webData()
	{
		System.out.println("**** get web data ****");
		Object[][] arrayObject = TestHelpers.getWebData(dataFileName, columnsToRead);
		return arrayObject;
	}

	@Parameters({ "fileName", "columns" })
	@BeforeTest
	public void before(@Optional("url_verification.xls") String fileName, @Optional("2") String columns)
	{
		System.out.println("Before Test");
		System.out.println("s = " + fileName);
		dataFileName = fileName;
		columnsToRead = Integer.parseInt(columns);
		// System.err.println("Running Test " + ++testNumber);
	}


	@Parameters({ "email" })
	@AfterTest
	public void afterTest(@Optional("brian.luckau@stgconsulting.com") String recipient)
	{
		// System.err.println("Finished Running Test " + testNumber);
	}


	@BeforeClass
	public static void beforeClass()
	{

		mainPage = new MainPage();
	}


	@AfterClass
	@Parameters({ "email" })
	public void afterClass(@Optional("brian.luckau@stgconsulting.com") String recipients)
	{

		EmailHelpers eh = new EmailHelpers();
		eh.sendTestResults(recipients, "brian.luckau@stgconsulting.com");

		Automation.quit();
		Automation.driver = null;
	}


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