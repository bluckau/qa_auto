package com.stg.bluckau.qa;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class Automation {
	static WebDriver driver;
	static WebDriverWait wait;
	static final long WAIT_TIMEOUT = 20;
	public static WebDriver getDriver()
	{
		if (driver == null)
		{
			ChromeDriverManager.getInstance().setup();
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 30);
		}
		return driver;
	}
	
	public static WebDriverWait getWait()
	{
		return wait;
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
	
	public static String getPageTitle()
	{
		return driver.getTitle();
	}
	
	public static void quit()
	{
		driver.quit();
	}
}
