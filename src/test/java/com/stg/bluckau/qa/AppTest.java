package com.stg.bluckau.qa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class AppTest 
{
	private static WebDriver driver;
	    

	@BeforeClass
	public static void beforeClass()
	{
		ChromeDriverManager.getInstance().setup();
		System.out.println("before");
		driver = new ChromeDriver();
	}
	
	@AfterClass
	public static void Afterclass()
	{
		System.out.println("after");
		driver.quit();
	}

	//Not big enough to require a helper or page object just yet
    @Test
    public void testTitle()
    {
    	driver.get("http://www.skiutah.com");
    	String title = driver.getTitle();
        System.out.println("Title is " + title);
        assertEquals(title, "Ski Utah | Utah Ski Resorts, Lift Tickets, Ski Passes, Maps & More! - Ski Utah");
    }
}
