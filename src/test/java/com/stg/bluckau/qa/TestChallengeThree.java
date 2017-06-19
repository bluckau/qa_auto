package com.stg.bluckau.qa;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestChallengeThree
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

	@Test
	public void testNav1()
	{
		String validationString = "Photos - Ski Utah";
		String menuOption = "Plan Your Trip";
		String subMenu = "Photos";
		mainPage.pageLoad();
		mainPage.goToSubMenu(menuOption, subMenu);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
}
