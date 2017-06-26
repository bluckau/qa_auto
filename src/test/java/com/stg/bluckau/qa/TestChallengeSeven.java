package com.stg.bluckau.qa;

import static org.testng.Assert.assertTrue;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestChallengeSeven
{
	private static int testNumber = 0;
	private static MainPage mainPage;
	private static WebCrawler webCrawler;
	private static String dataFileName;
	private static int columnsToRead;
	private final static String DICT = "c:\\tmp\\dictionary.txt";

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
		File dictFile = new File(DICT);
		dictFile.delete();
		String localSitePattern = "(http(s)?://)?(www)?skiutah.com/.*";
		webCrawler = new WebCrawler("https://skiutah.com", localSitePattern, DICT);
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}

	@Test
	public void testDictionary()
	{
		// Speed up this test
		webCrawler.setLinksLimit(2);

		webCrawler.walkSite();
		File dictFile = new File(DICT);
		assertTrue(dictFile.exists());
	}
}
