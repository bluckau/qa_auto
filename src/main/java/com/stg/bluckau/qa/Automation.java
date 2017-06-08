package com.stg.bluckau.qa;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class Automation {
	static WebDriver driver;
	static WebDriverWait wait;
	
	public static WebDriver getDriver()
	{
		if (driver == null)
		{
			ChromeDriverManager.getInstance().setup();
			//Not sure if I want Chrome to be the default, or anything to be the default
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		return driver;
	}
	
	public static WebDriverWait getWait()
	{
		if (wait == null)
		{
			setWait (15);
		}
		return wait;
	}
	
	public static void setWait(long pWait)
	{
		wait = new WebDriverWait(driver, pWait);
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
	
	public static String getPageTitle()
	{
		return driver.getTitle();
	}
	
	public static void quit()
	{
		driver.quit();
	}
	
	/*
	 * public static String getPageTitleWithWait(String validationText) { String
	 * title; //Wait for page title try { WebDriverWait wait = new
	 * WebDriverWait(driver, 15);
	 * wait.until(ExpectedConditions.titleContains(validationText)); }
	 * catch(org.openqa.selenium.TimeoutException e) { //TODO: Change this to
	 * stderr
	 * System.out.println("Error: The title we are looking for did not appear");
	 * System.out.println("Title expected: " + validationText);
	 * System.out.println("Title seen:     " + driver.getTitle()); throw e; }
	 * //snooze(1); //TODO: change this to stderr title = driver.getTitle();
	 * return title;
	 * 
	 * }
	 */
}
