package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;
import static com.stg.bluckau.qa.*;
import static com.stg.bluckau.qa.TestHelpers.getURLs;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestChallengeOne
{
	private static int testNumber;
	private static MainPage mainPage;
	private static String dataFileName;

	@BeforeTest
	public void before()
	{
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
		mainPage = new MainPage();
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}

	@Parameters({ "fileName" })
	@Test(dataProvider = "webData", dataProviderClass = TestHelpers.class)
	public void testTitle(String pageURL, String verificationText)
	{
		mainPage.pageLoad();
		String title = Automation.getPageTitle();
		assertEquals(title, verificationText);
	}
}