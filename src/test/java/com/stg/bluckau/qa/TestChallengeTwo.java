package com.stg.bluckau.qa;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class TestChallengeTwo
{
	private static int testNumber;
	private static MainPage mainPage;

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

	@Test(dataProvider = "webData", dataProviderClass = TestHelpers.class)
	public void testNav1(String menuOption, String validationString)
	{
		System.err.println("Testing menu: " + menuOption);
		System.err.println("ValidationString: " + validationString);
		mainPage.pageLoad();
		mainPage.goToMenu(menuOption, true);

		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
}
