package com.stg.bluckau.qa;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestChallengeSeven
{
	private static int testNumber = 0;
	private static MainPage mainPage;
	private static WebCrawler webCrawler;
	private final String DICT = "c:\\tmp\\dictionary.txt";

	@Before
	public void before()
	{
		System.err.println("Running Test " + ++testNumber);
		mainPage.pageLoad();
		File dictFile = new File(DICT);
		dictFile.delete();
	}

	@After
	public void after()
	{
		System.err.println("Finished Running Test " + testNumber);
	}

	@BeforeClass
	public static void beforeClass()
	{
		mainPage = new MainPage();
		String localSitePattern = "(http(s)?://)?(www)?skiutah.com/.*";
		webCrawler = new WebCrawler("https://skiutah.com", localSitePattern);
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
