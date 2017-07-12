package com.stg.bluckau.qa;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;

/**
 *
 * @author Brian Luckau
 * @version %I%, %G%
 * @since 0.1
 */
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

	/**
	 *
	 * @return the instance of WebDriverWait
	 */
	public static WebDriverWait getWait()
	{
		return wait;
	}

	/**
	 *
	 * @param n
	 *            The number of seconds to snooze
	 *
	 */
	public static void snooze(int n)
	{
		try {
			Thread.sleep(n*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return The page title of the current web page
	 */
	public static String getPageTitle()
	{
		return driver.getTitle();
	}

	public static void quit()
	{
		driver.quit();
	}

}
