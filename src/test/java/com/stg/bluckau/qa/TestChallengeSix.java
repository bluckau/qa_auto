package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestChallengeSix
{
	private static int testNumber = 0;
	private static MainPage mainPage;
	private static WebCrawler webCrawler;
	@Before
	public void before()
	{
		System.err.println("	Running Test " + testNumber++);
		mainPage.pageLoad();
	}

	@After
	public void after()
	{
		System.err.println("	Finished Running Test " + testNumber);
	}

	@BeforeClass
	public static void beforeClass()
	{
		mainPage = new MainPage();

		List<String> prefixes = new ArrayList<String>();
		prefixes.add("http://skiutah.com");
		prefixes.add("https://skiutah.com");
		prefixes.add("http://www.skiutah.com");
		prefixes.add("https://www.skiutah.com");

		webCrawler = new WebCrawler("https://skiutah.com", prefixes);
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

	// Right now the following will only make sure it works without throwing any
	// exceptions.
	@Test
	public void testCrawl1()
	{
		webCrawler.recursivelyWalk("http://www.skiutah.com", 5, 5);
	}

	// @Test
	public void testCrawl2()
	{
		webCrawler.recursivelyWalk("http://www.skiutah.com", 5, 0);
	}

	// @Test
	public void testCrawl3()
	{
		webCrawler.recursivelyWalk("http://www.skiutah.com");
	}
}
