package com.stg.bluckau.qa;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

//Want to run them in order most times that I'm doing it manually. Then it does the quick ones first
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestChallengeEight
{
	private static int testNumber = 0;
	private static MainPage mainPage;
	private static WebCrawler webCrawler;
	private static WebDriver driver;


	@Before
	public void before()
	{
		System.err.println("Running Test " + ++testNumber);
		mainPage.pageLoad();

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
		driver = Automation.getDriver();
		webCrawler = new WebCrawler("https://skiutah.com", localSitePattern);
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}


	@Test
	public void testBrokenImage()
	{
		driver.get("http://the-internet.herokuapp.com/broken_images");
		assertFalse(WebCrawler.checkImageLoad(driver.findElement(By.cssSelector("#content > div > img:nth-child(3)"))));
	}

	@Test
	public void testWorkingImage()
	{
		driver.get("http://the-internet.herokuapp.com/broken_images");
		assertTrue(WebCrawler.checkImageLoad(driver.findElement(By.cssSelector("#content > div > img:nth-child(4)"))));
	}

	@Test
	public void testCheckAllImages()
	{

		webCrawler.checkAllImages();
	}

	@Test
	public void testCustomWebCrawler()
	{
		WebCrawler webCrawler2 = new WebCrawler("http://the-internet.herokuapp.com/broken_images",
				"(http(s)?://)?(www)?the-internet.herokuapp.com/broken_images");
		webCrawler2.walkSite();
	}
}

