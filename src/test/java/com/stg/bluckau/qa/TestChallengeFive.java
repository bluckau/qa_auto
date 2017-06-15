package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestChallengeFive
{
	private static WebDriver driver;
	private static SearchPage searchPage;
	private static int testNumber = 0;

	@Before
	public void before()
	{
		System.err.println("	Running Test " + testNumber++);
	}

	@After
	public void after()
	{
		System.err.println("	Finished Running Test " + testNumber);
	}

	@BeforeClass
	public static void beforeClass()
	{
		searchPage = new SearchPage();
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}

	public List<String> getResorts()
	{

		List<String> resorts = new ArrayList<String>();

		By resortsList = By.cssSelector("div.ListingFilter-column.ListingFilter-column--categories");
		WebElement resortsListTop = driver.findElement(resortsList);

		// Find subelements that are resorts
		List<WebElement> resortElements = resortsListTop.findElements(
				By.xpath("//div[@class='div.ListingFilter-column.ListingFilter-column--categories']//./div/select/*"));
		for (WebElement row : resortElements)
		{
			// System.out.println("iterate");
			System.out.println("Adding" + row.getAttribute("innerHTML"));
			resorts.add(row.getAttribute("innerHTML"));
		}
		return resorts;
	}

	public List<String> getCategories()
	{
		List<String> cats = new ArrayList<String>();

		By categoriesList = By.cssSelector("div.ListingFilter-column.ListingFilter-column--location");
		WebElement resortsListTop = driver.findElement(categoriesList);

		List<WebElement> resortElements = resortsListTop.findElements(By.xpath("//./div/select/*"));
		for (WebElement row : resortElements)
		{
			// System.out.println("iterate");
			System.out.println("Adding" + row.getAttribute("innerHTML"));
			cats.add(row.getAttribute("innerHTML"));
		}
		return cats;
	}

	public List<String> getSubCategories()
	{
		List<String> cats = new ArrayList<String>();

		By categoriesList = By.cssSelector("div.ListingFilter-column.ListingFilter-column--location");
		WebElement resortsListTop = driver.findElement(categoriesList);

		List<WebElement> resortElements = resortsListTop.findElements(By.xpath("//./div/select/*"));
		for (WebElement row : resortElements)
		{
			// System.out.println("iterate");
			System.out.println("Adding" + row.getAttribute("innerHTML"));
			cats.add(row.getAttribute("innerHTML"));
		}
		return cats;
	}
	// Search for Transportation, Taxi, Alta
	@Test
	public void testSearch1()
	{
		/*
		 * Basic search for one combination. May need to pull the results
		 * comparison out because these result sets change constantly. You would
		 * have to be fed a dynamic list of desired output values.
		 */
		System.out.println("Testing testSearch1");
		searchPage.pageLoad();
		searchPage.searchForCombination("Transportation", "Taxi", "Alta");

		List<String> results = new ArrayList<String>();
		results = searchPage.getSearchResults();

		assertEquals(results.size(), 1);
		assertEquals("Uber", results.get(0));
	}

	// Search for Transportation, Taxi, Alta
	@Test
	public void testSearch2()
	{
		/*
		 * Search all combinations. Not validating the output, but making sure
		 * they all complete without exception (for now at least)
		 *
		 */
		System.out.println("Testing testSearch2");
		searchPage.pageLoad();
		List<String> resorts = getResorts();
		List<String> categories = getCategories();
		List<String> subCategories = getSubCategories();

		// Test every combination to make sure it does not error out
		// TODO: need to take into account the mapping between category and
		// subcategory
		for (String resort : resorts)
		{
			for (String category : categories)
			{
				for (String subCategory : subCategories)
					searchPage.searchForCombination(category, subCategory, resort);

				List<String> results = new ArrayList<String>();
				results = searchPage.getSearchResults();

			}
		}
	}
}
