package com.stg.bluckau.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage 
{
	private static final String URL = "https://skiutah.com";
	private static WebDriver driver;
	private static WebDriverWait wait;

	public MainPage()
	{
		driver = Automation.getDriver();
		wait = Automation.getWait();
	}

	public void pageLoad()
	{
		driver = Automation.getDriver();
	}

	public void goToMenu(String menu)
	{
		goToMenu(menu, true);
	}

	public void goToMenu(String menu, boolean subMenuExists)
	{
		WebElement menuElement = driver.findElement(By.cssSelector("a[title=\'" + menu + "\']"));

		if (subMenuExists == true)
		{
			// Submenu exists, look for the hover
			Actions builder = new Actions(driver);
			builder.moveToElement(menuElement).perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SuperfishMegaMenu .sfHover")));
		}
		menuElement.click();
	}

	public void goToSubMenu(String menu, String subMenu)
	{
		Actions builder = new Actions(driver);
		WebElement menuElement = driver.findElement(By.cssSelector("a[title=\'" + menu + "\']"));
		driver = Automation.getDriver();

		//hover
		builder.moveToElement(menuElement).perform();

		//look for the element now instead of before it exists
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Photos"))).click();
	}
}
