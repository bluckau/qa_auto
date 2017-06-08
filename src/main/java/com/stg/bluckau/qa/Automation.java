package com.stg.bluckau.qa;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Automation {
	static WebDriver driver;

	public static WebDriver getDriver()
	{
		if (driver == null)
		{
			//Not sure if I want Chrome to be the default, or anything to be the default
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		return driver;
	}
	
	public static void snooze(int n)
	{
		try {
			Thread.sleep(n*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setDriver(WebDriver pDriver)
	{
		driver = pDriver; 
	}
	
	public static String getCurrentTitle()
	{
		return driver.getTitle();
	}
	
	public static void quit()
	{
		driver.quit();
	}
	public static String getPageTitleWithWait(String validationText)
	{
		String title;
		//Wait for page title
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.titleContains(validationText));
		}
		catch(org.openqa.selenium.TimeoutException e)
		{
			//TODO: Change this to stderr
			System.out.println("Error: The title we are looking for did not appear");
			System.out.println("Title expected: " + validationText);
			System.out.println("Title seen: " + driver.getTitle());
			throw e;
		}
		//snooze(1);
		//TODO: change this to stderr
		title = driver.getTitle();
		System.out.println("Title is: " + title);
		return title;
	}
}
