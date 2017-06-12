package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestChallengeThree
{
	private static int testNumber;
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static MainPage mainPage;

	@Before
	public void before()
	{
		System.err.println("	Running Test " + testNumber);
		testNumber++;
	}

	@After
	public void after()
	{
		System.err.println("	Finished Running Test " + testNumber);
		testNumber++;
	}

	@BeforeClass
	public static void beforeClass()
	{
		driver = Automation.getDriver();
		wait = Automation.getWait();
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
