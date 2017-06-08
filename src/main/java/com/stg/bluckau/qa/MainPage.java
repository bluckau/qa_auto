package com.stg.bluckau.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


public class MainPage 
{
	private static final String URL = "https://skiutah.com";
	
	private static WebDriver driver;

	public void pageLoad()
	{
		driver = Automation.getDriver();
		driver.get(URL);
	}
	
	public void goToMenu(String menu)
	{
		driver = Automation.getDriver();
		
		Actions builder = new Actions(driver);
		WebElement menuElement = driver.findElement(By.xpath("//a[@title=\'" + menu + "\']"));
		
		//hover
		builder.moveToElement(menuElement).perform();

		//click when clickable
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SuperfishMegaMenu-level--1")));
		//The above does not work sufficiently. Snoozing for now...
		Automation.snooze(1);
		menuElement.click();
	}
}
