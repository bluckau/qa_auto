package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import io.github.bonigarcia.wdm.ChromeDriverManager;


public class TestChallengeOne 
{
	MainPage mp = new MainPage();

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
		Automation.quit();
		Automation.driver = null;
	}

	@Test
	public void testTitle()
	{
		String validationText = "Ski Utah | Utah Ski Resorts, Lift Tickets, Ski Passes, Maps & More! - Ski Utah";
		mp.pageLoad();
		String title = Automation.getPageTitle();
		assertEquals(title, validationText);
	}
}