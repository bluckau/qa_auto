package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import io.github.bonigarcia.wdm.ChromeDriverManager;


public class TestChallengeThree 
{
	MainPage mp = new MainPage();
	
	@BeforeClass
	public static void beforeClass()
	{
		ChromeDriverManager.getInstance().setup();
		System.out.println("...........Running beforeClass");
	}

	@AfterClass
	public static void Afterclass()
	{
		System.out.println("...........Running afterClass");
		//Automation.quit();
		//Automation.driver = null;
	}
		
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
