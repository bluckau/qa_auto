package com.stg.bluckau.qa;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
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
	private static String dataFileName;
	private static int columnsToRead;

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
		driver = Automation.getDriver();
		webCrawler = new WebCrawler("https://skiutah.com", localSitePattern);
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}


	// @Test
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

	@Test(dataProvider = "webData")
	public void testCrawler(@Optional("https://www.skiutah.com/trip-planner") String menuUrl,
			@Optional("https://www.skiutah.com/trip-planner") String menuUrlV)
	{
		webCrawler = new WebCrawler(menuUrl, menuUrlV);
		webCrawler.walkSite();
	}
}

