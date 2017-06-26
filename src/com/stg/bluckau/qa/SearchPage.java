package com.stg.bluckau.qa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SearchPage 
{
	private static final String URL = "https://www.skiutah.com/members/listing";
	private static final By listingResults = By.xpath("//div[@class='ListingResults-item']//*/h3/a");
	private static final By okButton = By.name("filter-form-submit");
	private static final By categoryDropDown = By.name("filter-category-select");
	private static final By resortDropDown = By.name("filter-resort-select");
	private static final By subCategoryDropDown = By.name("filter-sub-category-select");
	private static final By resultsList = By.cssSelector(".ListingResults-items .u-cf");

	private static WebDriver driver;
	private static WebDriverWait wait;

	public SearchPage()
	{
		driver = Automation.getDriver();
		// wait = Automation.getWait();
	}

	public void pageLoad()
	{
		driver.get(URL);
	}

	public void searchForCombination(String category, String subCategory, String resortName)
	{
		// Take the spaces and convert them to a "+"
		// trim first to avoid placing a plus at the beginning or end
		String categorySanitized = category.trim().replaceAll(" ", "+");
		String subCategorySantitized = subCategory.trim().replaceAll(" ", "+");

		WebElement dropDownElement = driver.findElement(categoryDropDown);
		Select select = new Select(dropDownElement);
		select.selectByValue(categorySanitized);

		dropDownElement = driver.findElement(subCategoryDropDown);
		select = new Select(dropDownElement);
		select.selectByValue(subCategorySantitized);

		/*
		 * Map the resort names to values on the pulldown. TODO this information
		 * should be stored somewhere (text file, etc.) so we are not hard
		 * coding.
		 */
		final Map<String, String> dMap = new HashMap<String, String>();
		{
			dMap.put("Alta", "alta");
			dMap.put("Beaver Mountain", "beaver-mountain");
			dMap.put("Brian Head Ski Resort", "brian-head");
			dMap.put("Brighton", "brighton");
			dMap.put("Cherry Peak", "cherry");
			dMap.put("Deer Valley Resort", "deer-valley");
			dMap.put("Eagle Point", "eagle-point");
			dMap.put("Nordic Valley", "nordic-valley");
			dMap.put("Park City Mountain", "park-city-mountain");
			dMap.put("Powder Mountain", "powder-mountain");
			dMap.put("Snowbasin Resort", "snowbasin");
			dMap.put("Snowbird", "snowbird");
			dMap.put("Solitude", "solitude");
			dMap.put("Sundance", "sundance");
		}
		dropDownElement = driver.findElement(resortDropDown);
		select = new Select(dropDownElement);
		select.selectByValue(dMap.get(resortName));

		driver.findElement(okButton).click();
	}


	public List<String> getSearchResults()
	{
		List<String> results = new ArrayList<String>();
		
		WebElement searchElement = driver.findElement(resultsList);
		// Find subelements that are search results
		List<WebElement> listingItemsElements = searchElement
				.findElements(listingResults);

		for (WebElement row : listingItemsElements)
		{
			results.add(row.getAttribute("innerHTML"));

		}

		return results;
	}
}
