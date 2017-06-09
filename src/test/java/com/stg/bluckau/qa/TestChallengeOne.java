package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestChallengeOne extends TestChallenge
{
	MainPage mp = new MainPage();

	@Test
	public void testTitle()
	{
		String validationText = "Ski Utah | Utah Ski Resorts, Lift Tickets, Ski Passes, Maps & More! - Ski Utah";
		mp.pageLoad();
		String title = Automation.getPageTitle();
		assertEquals(title, validationText);
	}
}