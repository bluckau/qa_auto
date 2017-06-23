package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestChallengeOne
{
	private static int testNumber;
	private static MainPage mainPage;
	private static String dataFileName;

	@DataProvider(name = "webData")
	public static Object[][] webData()
	{
		System.out.println("**** get web data ****");
		Object[][] arrayObject = TestHelpers.getWebData(dataFileName, 2);
		return arrayObject;
	}


	@Parameters({ "fileName" })
	@BeforeTest
	public void before(String fileName)
	{
		System.out.println("Before Test");
		System.out.println("s = " + fileName);
		dataFileName = fileName;
		System.err.println("Running Test " + ++testNumber);
	}


	@AfterTest
	public void after()
	{
		System.err.println("Finished Running Test " + testNumber);
	}


	@BeforeClass
	public static void beforeClass()
	{
		System.out.println("Before class");
		mainPage = new MainPage();
	}


	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}


	@Test(dataProvider = "webData")
	public void testTitle(String pageURL, String verificationText)
	{
		System.out.println("****testTitle***");
		mainPage.pageLoad();
		String title = Automation.getPageTitle();
		assertEquals(title, verificationText);
	}
}