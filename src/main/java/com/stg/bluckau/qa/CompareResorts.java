package com.stg.bluckau.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class CompareResorts 
{
	private static final By dropDown = By.cssSelector(".js-resort-comparison-select");
	private static final String URL = "https://www.skiutah.com/resorts/compare-resorts";
	private static WebDriver driver;
	private static WebDriverWait wait;

	public CompareResorts()
	{
		driver = Automation.getDriver();
		wait = Automation.getWait();
	}

	public void pageLoad()
	{
		driver.get(URL);
	}

	public int getMilesForResort(String resortName)
	{
		WebElement dropDownElement = driver.findElement(dropDown);
		Select select = new Select(dropDownElement);
		select.selectByValue("miles-to-closest-major-airport");
		StringBuilder comparisonValue = new StringBuilder(
				".ResortComparison-panelBar--miles-to-closest-major-airport a[title=\'");
		comparisonValue.append(resortName);
		comparisonValue.append("\'] .ResortComparison-value");
		String value = (wait
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(comparisonValue.toString())))
				.getText()).trim();

		return Integer.parseInt(value);

	}
}
