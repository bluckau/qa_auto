package com.stg.bluckau.qa;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestChallengeTwo
{
	private static int testNumber;
	private static MainPage mainPage;
	private static String dataFileName;
	private static String columnsToRead;

	@DataProvider(name = "webData")
	public static Object[][] webData()
	{
		System.out.println("**** get web data ****");
		System.out.println("File name: " + dataFileName + ";");
		Object[][] arrayObject = TestHelpers.getWebData(dataFileName, Integer.parseInt(columnsToRead));

		System.out.println(arrayObject.length);
		System.out.println(arrayObject[0].length);
		return arrayObject;
	}


	@Parameters({ "fileName", "columnsToRead" })
	@BeforeTest
	public void before(@Optional("menus.xls") String fileName, @Optional("2") String columns)
	{
		System.out.println("Before Test");
		System.out.println("fileName = " + fileName);
		dataFileName = fileName;
		columnsToRead = columns;
		System.err.println("Running Test " + ++testNumber);
	}

	@AfterTest
	public void after()
	{
		// System.err.println("Finished Running Test " + testNumber);
	}

	@BeforeClass
	public static void beforeClass()
	{
		mainPage = new MainPage();
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}

	// @Test(dataProvider = "webData")
	// TODO: stop having to take extra strings
	public void testNav1(String menuOption, String validationString)
	{
		System.err.println("Testing menu: " + menuOption);
		System.err.println("ValidationString: " + validationString);

		mainPage.pageLoad();
		mainPage.goToMenu(menuOption, true);

		String title = Automation.getPageTitle();
		assertEquals(title, validationString);
	}
}