package com.stg.bluckau.qa;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class ChallengeTwo 
{
	@BeforeClass
	public static void beforeClass()
	{
		ChromeDriverManager.getInstance().setup();
		System.out.println("	Running beforeClass");
	}

	@AfterClass
	public static void Afterclass()
	{
		System.out.println("	running afterClass");
		//TestObjects.driver.quit();
	}

	@Test
	public void testTitle()
	{
		TestObjects.goToURL("http://www.skiutah.com");
		String title = TestObjects.getPageTitle();
		System.out.println("	Title is " + title);
		assertEquals(title, "Ski Utah | Utah Ski Resorts, Lift Tickets, Ski Passes, Maps & More! - Ski Utah");
	}

	@Test
	public void testNav1()
	{
		TestObjects.goToURL("http://www.skiutah.com");
		String validationString = "Ski Utah Trip Planner - Ski Utah";
		String menuOption = "Plan Your Trip";
		assertTrue(TestObjects.verifyPage(validationString, menuOption));
	}

	@Test
	public void testNav2()
	{
		TestObjects.goToURL("http://www.skiutah.com");
		String validationString = "Utah Snow Report | Snow Totals at Utah Ski Resorts - Ski Utah";
		String menuOption = "Resorts & Snow";
		assertTrue(TestObjects.verifyPage(validationString, menuOption));
	}

	@Test
	public void testNav3()
	{
		TestObjects.goToURL("http://www.skiutah.com");
		String validationString = "Read About the Latest Happenings on the Slopes - Ski Utah";
		String menuOption = "Stories";
		assertTrue(TestObjects.verifyPage(validationString, menuOption));
	}

	@Test
	public void testNav4()
	{
		TestObjects.goToURL("http://www.skiutah.com");
		String validationString = "Ski Utah | Utah Ski Resorts, Lift Tickets, Ski Passes, Maps & More! - Ski Utah";
		String menuOption = "Deals";
		assertTrue(TestObjects.verifyPage(validationString, menuOption));
	}

	@Test
	public void testNav5()
	{
		TestObjects.goToURL("http://www.skiutah.com");
		String validationString = "2017-2018 Ski Season Passes | Utah Ski Passes | Ski Utah - Ski Utah";
		String menuOption = "Passes";
		assertTrue(TestObjects.verifyPage(validationString, menuOption));
	}

	@Test
	public void testNav6()
	{
		TestObjects.goToURL("http://www.skiutah.com");
		String validationString = "Utah Ski Areas 101 | Utah Ski Resort Info | Ski Utah - Ski Utah";
		String menuOption = "Explore";
		assertTrue(TestObjects.verifyPage(validationString, menuOption));
	}
}
