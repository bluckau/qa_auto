package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestChallengeTwo extends TestChallenge
{
	MainPage mp = new MainPage();
	
		
	@Test
	public void testNav1()
	{
		System.out.println("nav1");
		String validationString = "Ski Utah Trip Planner - Ski Utah";
		String menuOption = "Plan Your Trip";
		mp.pageLoad();
		mp.goToMenu(menuOption,true);

		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
	
	@Test
	public void testNav2()
	{
		String validationString = "Utah Snow Report | Snow Totals at Utah Ski Resorts - Ski Utah";
		String menuOption = "Resorts & Snow";
		mp.pageLoad();
		mp.goToMenu(menuOption);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
	
	@Test
	public void testNav3()
	{
		String validationString = "Read About the Latest Happenings on the Slopes - Ski Utah";
		String menuOption = "Stories";
		mp.pageLoad();
		mp.goToMenu(menuOption, false);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
	
	@Test
	public void testNav4()
	{
		String validationString = "Deals - All Services - Ski Utah";
		String menuOption = "Deals";
		mp.pageLoad();
		mp.goToMenu(menuOption);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
	
	@Test
	public void testNav5()
	{
		String validationString = "2017-2018 Ski Season Passes | Utah Ski Passes | Ski Utah - Ski Utah";
		String menuOption = "Passes";
		mp.pageLoad();
		mp.goToMenu(menuOption);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
	
	@Test
	public void testNav6()
	{
		String validationString = "Utah Ski Areas 101 | Utah Ski Resort Info | Ski Utah - Ski Utah";
		String menuOption = "Explore";
		mp.pageLoad();
		mp.goToMenu(menuOption);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}

}
