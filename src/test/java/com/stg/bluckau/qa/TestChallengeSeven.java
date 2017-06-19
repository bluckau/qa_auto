package com.stg.bluckau.qa;

import static org.testng.Assert.assertTrue;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestChallengeSeven
{
	private static int testNumber = 0;
	private static MainPage mainPage;
	private static WebCrawler webCrawler;
	private final static String DICT = "c:\\tmp\\dictionary.txt";

	@BeforeTest
	public void before()
	{
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
