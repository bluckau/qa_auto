package com.stg.bluckau.qa;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestChallengeFive
{
	private static WebDriver driver;
	private static SearchPage searchPage;
	private static int testNumber = 0;
	By resortsList = By.cssSelector("div.ListingFilter-column.ListingFilter-column--categories");
	private static int columnsToRead;
	private static String dataFileName;
	private static String resortsFileName;
	private static String resorts;

	@DataProvider(name = "webData")
	public static Object[][] webData()
	{
		System.out.println("**** PROVIDER ****");
		Object[][] arrayObject = TestHelpers.getSearchData(dataFileName, resortsFileName);
		return arrayObject;
	}

	@Parameters({ "fileName" })
	@BeforeTest
	public void before(@Optional("searches.xls") String fileName)
	{
		dataFileName = fileName;
		System.err.println("Running Test " + ++testNumber);
	}

	@AfterTest
	public void after()
	{
		System.err.println("Finished Running Test " + testNumber);
	}

	@BeforeClass
	public static void beforeClass()
	{
		driver = Automation.getDriver();
		searchPage = new SearchPage();
		searchPage.pageLoad();
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}




	// public List<String> getResorts()
	// {
	//
	// List<String> resorts = new ArrayList<String>();
	// WebElement resortsListTop = driver.findElement(resortsList);
	//
	// // Find subelements that are resorts
	// List<WebElement> resortElements = resortsListTop.findElements(
	// By.xpath("//div[@class='div.ListingFilter-column.ListingFilter-column--categories']//./div/select/*"));
	// for (WebElement row : resortElements)
	// {
	// // System.out.println("iterate");
	// // System.out.println("Adding" + row.getAttribute("innerHTML"));
	// resorts.add(row.getAttribute("innerHTML"));
	// }
	// return resorts;
	// }

	// public List<String> getCategories()
	// {
	// List<String> cats = new ArrayList<String>();
	//
	// By categoriesList =
	// By.cssSelector("div.ListingFilter-column.ListingFilter-column--location");
	// WebElement resortsListTop = driver.findElement(categoriesList);
	//
	// List<WebElement> resortElements =
	// resortsListTop.findElements(By.xpath("//./div/select/*"));
	// for (WebElement row : resortElements)
	// {
	// // System.out.println("Adding " + row.getAttribute("innerHTML"));
	// cats.add(row.getAttribute("innerHTML"));
	// }
	// return cats;
	// }

	// public List<String> getSubCategories()
	// {
	// List<String> cats = new ArrayList<String>();
	//
	// By categoriesList =
	// By.cssSelector("div.ListingFilter-column.ListingFilter-column--location");
	// WebElement resortsListTop = driver.findElement(categoriesList);
	//
	// List<WebElement> resortElements =
	// resortsListTop.findElements(By.xpath("//./div/select/*"));
	// for (WebElement row : resortElements)
	// {
	// // System.out.println("Adding" + row.getAttribute("innerHTML"));
	// cats.add(row.getAttribute("innerHTML"));
	// }
	// return cats;
	// }
	// Search for Transportation, Taxi, Alta
	@Test
	public void testSearch1()
	{
		/*
		 * Basic search for one combination. May need to pull the results
		 * comparison out because these result sets change constantly. You would
		 * have to be fed a dynamic list of desired output values.
		 */

		searchPage.searchForCombination("Transportation", "Taxi", "Alta");

		List<String> results = new ArrayList<String>();
		results = searchPage.getSearchResults();

		assertEquals(results.size(), 1);
		assertEquals("Uber", results.get(0));
	}

	// Search for Transportation, Taxi, Alta
	// @Test(dataProvider = "webData")
	public void testSearch2(String resort, String menu, String subMenu)
	{

		/*
		 * Basic search for one combination. May need to pull the results
		 * comparison out because these result sets change constantly. You would
		 * have to be fed a dynamic list of desired output values.
		 */

		searchPage.searchForCombination(resort, menu, subMenu);

		List<String> results = new ArrayList<String>();
		results = searchPage.getSearchResults();

		assertEquals(results.size(), 1);
		assertEquals("Uber", results.get(0));

	}
	//	}
}

