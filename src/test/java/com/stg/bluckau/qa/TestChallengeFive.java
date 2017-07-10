package com.stg.bluckau.qa;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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
	// private static WebDriver driver;
	private static SearchPage searchPage;
	private static int testNumber = 0;
	By resortsList = By.cssSelector("div.ListingFilter-column.ListingFilter-column--categories");
	private static String dataFileName;
	private static String resortsFileName;

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
		// system.err.println("Finished Running Test " + testNumber);
	}

	@BeforeClass
	public static void beforeClass()
	{
		searchPage = new SearchPage();
		searchPage.pageLoad();
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}

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
}

