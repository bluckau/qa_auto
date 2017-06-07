package com.stg.bluckau.qa;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class ChallengeOne 
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
}