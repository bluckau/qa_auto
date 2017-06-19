package com.stg.bluckau.qa;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//Junit artifact
//Want to run them in order most times that I'm doing it manually. Then it does the quick ones first
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestChallengeEight
{
	private static int testNumber = 0;
	private static MainPage mainPage;
	private static WebCrawler webCrawler;
	private static WebDriver driver;


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

