package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestChallengeOne
{
	private static int testNumber;
	private static MainPage mainPage;

	@Before
	public void before()
	{
		System.err.println("Running Test " + ++testNumber);
	}

	@After
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
	public void testTitle()
	{
		String validationText = "Ski Utah | Utah Ski Resorts, Lift Tickets, Ski Passes, Maps & More! - Ski Utah";
		mainPage.pageLoad();
		String title = Automation.getPageTitle();
		assertEquals(title, validationText);
	}
}