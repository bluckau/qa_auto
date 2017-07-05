package com.stg.bluckau.qa;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestChallengeSix
{
	private static int testNumber = 0;
	private static MainPage mainPage;
	private static WebCrawler webCrawler;
	private static String dataFileName;
	private static int columnsToRead;

	@Parameters({ "fileName", "columnsToRead" })
	@BeforeTest
	public void before(@Optional("menuurls.xls") String name, @Optional("2") String columns)
	{
		System.out.println("before");
		dataFileName = name;
		columnsToRead = Integer.parseInt(columns);
		System.out.println("Before Test");
		System.out.println("fileName = " + dataFileName);
		System.out.println("Columns " + columnsToRead);
	}

	@AfterTest
	public void after()
	{
		System.err.println("Finished Running Test " + testNumber);
	}

	@BeforeClass
	public static void beforeClass()
	{
		mainPage = new MainPage();
		mainPage.pageLoad();
		String localSitePattern = "(http(s)?://)?(www)?skiutah.com/.*";
		webCrawler = new WebCrawler("https://www.skiutah.com", localSitePattern, "c:\\tmp\\filename.txt");
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}

	@DataProvider(name = "webData")
	public static Object[][] webData()
	{
		System.out.println("**** running data provider ****");
		System.out.println("File name: " + dataFileName + ";");
		if (new File(dataFileName).exists())
		{
			System.out.println("file exists");
		} else
		{
			System.out.println("File " + dataFileName + "not exist");
			System.exit(99);
		}
		Object[][] theArray = TestHelpers.getWebData(dataFileName, columnsToRead);

		// TestHelpers.printArray(theArray, columnsToRead);
		return theArray;
	}


	@Test
	public void testGetLinksWithLimit()
	{
		List<String> links = webCrawler.getCurrentLinks(5);
		assertEquals(links.size(), 5);
	}

	// @Test
	public void testGetLinksWithOutLimit()
	{
		List<String> links = webCrawler.getCurrentLinks(0);
		assertTrue(links.size() > 10);
	}

	@Test
	public void testLinkWorks()
	{
		assertTrue(WebCrawler.linkWorks("https://www.google.com"));
	}

	@Test
	public void testBrokenLink()
	{
		assertFalse(WebCrawler.linkWorks("http://www.asdfasdfasdfasdfasdfasdfblah.com"));
	}

	@Test
	public void testPrint()
	{
		webCrawler.printBrokenLinks();
	}

	@Test(dataProvider = "webData")
	public void testCrawler(@Optional("https://www.skiutah.com/trip-planner") String menuUrl,
			@Optional("https://www.skiutah.com/trip-planner") String menuUrlV)
	{
		webCrawler = new WebCrawler(menuUrl, menuUrlV);
		webCrawler.walkSite();
	}

}
