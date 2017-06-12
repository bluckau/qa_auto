package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestChallengeTwo
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
		System.out.println("nav1");
		String validationString = "Ski Utah Trip Planner - Ski Utah";
		String menuOption = "Plan Your Trip";
		mainPage.pageLoad();
		mainPage.goToMenu(menuOption, true);

		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
	
	@Test
	public void testNav2()
	{
		String validationString = "Utah Snow Report | Snow Totals at Utah Ski Resorts - Ski Utah";
		String menuOption = "Resorts & Snow";
		mainPage.pageLoad();
		mainPage.goToMenu(menuOption);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
	
	@Test
	public void testNav3()
	{
		String validationString = "Read About the Latest Happenings on the Slopes - Ski Utah";
		String menuOption = "Stories";
		mainPage.pageLoad();
		mainPage.goToMenu(menuOption, false);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
	
	@Test
	public void testNav4()
	{
		String validationString = "Deals - All Services - Ski Utah";
		String menuOption = "Deals";
		mainPage.pageLoad();
		mainPage.goToMenu(menuOption);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
	
	@Test
	public void testNav5()
	{
		String validationString = "2017-2018 Ski Season Passes | Utah Ski Passes | Ski Utah - Ski Utah";
		String menuOption = "Passes";
		mainPage.pageLoad();
		mainPage.goToMenu(menuOption);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
	
	@Test
	public void testNav6()
	{
		String validationString = "Utah Ski Areas 101 | Utah Ski Resort Info | Ski Utah - Ski Utah";
		String menuOption = "Explore";
		mainPage.pageLoad();
		mainPage.goToMenu(menuOption);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}

}
