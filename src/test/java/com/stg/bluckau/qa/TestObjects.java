package com.stg.bluckau.qa;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestObjects 
{
	static WebDriver driver;

	public static WebDriver getDriver()
	{
		if (driver == null)
		{
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		return driver;
	}

	public static String getPageTitle()
	{
		driver = getDriver();
		return driver.getTitle();
	}

	public static void goToURL(String url)
	{
		driver = getDriver();
		driver.get(url);
	}

	//Verifies page title after clicking a menu on the page.
	public static boolean verifyPage(String validationText, String menu)
	{
		driver = getDriver();
		WebDriverWait wait = new WebDriverWait(driver, 15);
		Actions builder = new Actions(driver);
		//By menuLocator = By.xpath("//a[@title=\'" + menu + "\']");
		WebElement menuElement = driver.findElement(By.xpath("//a[@title=\'" + menu + "\']"));
		//WebElement element = driver.findElement(menuLocator);

		//hover
		builder.moveToElement(menuElement).perform();

		//click when clickable
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SuperfishMegaMenu-level--1")));
		//The above does not work sufficiently. Snoozing for now...
		snooze(1);
		menuElement.click();

		//Wait for page title
		wait.until(ExpectedConditions.titleContains(validationText));
		System.out.println("Title is: " + driver.getTitle());

		//Now run the formal check for the page title 
		return validationText.equals(getPageTitle());
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
}
