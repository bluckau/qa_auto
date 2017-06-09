package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestChallengeThree extends TestChallenge
{
	MainPage mp = new MainPage();
	
	@Test
	public void testNav1()
	{
		String validationString = "Photos - Ski Utah";
		String menuOption = "Plan Your Trip";
		String subMenu = "Photos";
		mp.pageLoad();
		mp.goToSubMenu(menuOption, subMenu);
		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
}
