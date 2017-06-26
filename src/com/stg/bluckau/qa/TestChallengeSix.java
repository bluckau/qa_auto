package com.stg.bluckau.qa;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestChallengeSix
{
	private static int testNumber = 0;
	private static MainPage mainPage;
	private static WebCrawler webCrawler;

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
		String localSitePattern = "(http(s)?://)?(www)?skiutah.com/.*";
		webCrawler = new WebCrawler("https://www.skiutah.com", localSitePattern, "c:\\tmp\\filename.txt");
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}

	@Test
	public void testGetLinks()
	{
		List<String> links = webCrawler.getCurrentLinks(0);
		assertTrue(links.size() > 0);
	}

	@Test
	public void testGetLinksWithLimit()
	{
		List<String> links = webCrawler.getCurrentLinks(5);
		assertEquals(links.size(), 5);
	}

	@Test
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

	// @Test
	public void testBrokenLink()
	{
		assertFalse(WebCrawler.linkWorks("http://www.asdfasdfasdfasdfasdfasdfblah.com"));
	}

	// @Test
	public void testPrint()
	{
		webCrawler.printBrokenLinks();
	}

	// Right now the following will only make sure it works without throwing any
	// exceptions.
	@Test
	public void testCrawl1()
	{
		webCrawler.setLinksLimit(2);
		webCrawler.walkSite();
	}

	@Test
	public void testCrawl2()
	{
		webCrawler.setLinksLimit(0);
		webCrawler.walkSite();
	}
}
