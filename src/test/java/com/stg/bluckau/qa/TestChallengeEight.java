package com.stg.bluckau.qa;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TestChallengeEight
{
	private static int testNumber = 0;
	private static MainPage mainPage;
	private static WebCrawler webCrawler;
	private static WebDriver driver;

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
		driver = Automation.getDriver();
		webCrawler = new WebCrawler("https://skiutah.com", prefixes);
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}

	@Test
	public void test1()
	{

		webCrawler.checkAllImages();
	}

	@Test
	public void test2()
	{
		driver.get("http://the-internet.herokuapp.com/broken_images");
		assertFalse(WebCrawler.checkImageLoad(driver.findElement(By.cssSelector("#content > div > img:nth-child(3)"))));
	}

	@Test
	public void test3()
	{
		driver.get("http://the-internet.herokuapp.com/broken_images");
		assertTrue(WebCrawler.checkImageLoad(driver.findElement(By.cssSelector("#content > div > img:nth-child(4)"))));
	}
}

