package com.stg.bluckau.qa;

import static com.stg.bluckau.qa.TestHelpers.getWebData;
import static org.junit.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestChallengeFour
{
	private static int testNumber;
	private static CompareResorts resortsCompare;
	private static String dataFileName;

	@DataProvider(name = "webData")
	public static Object[][] webData()
	{
		Object[][] arrayObject = getWebData(dataFileName, 2);
		return arrayObject;
	}

	@Parameters({ "fileName" })
	@BeforeTest
	public void before(String fileName)
	{
		System.out.println("Before Test");
		System.out.println("s = " + fileName);
		dataFileName = fileName;
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
		resortsCompare = new CompareResorts();
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}

	@Test(dataProvider = "webData")
	public void testMiles(String resort, String miles)
	{
		System.out.println("RUNNING THE DAMN TEST");

		resortsCompare.pageLoad();

		assertEquals(miles, resortsCompare.getMilesForResort(resort));
	}
}
