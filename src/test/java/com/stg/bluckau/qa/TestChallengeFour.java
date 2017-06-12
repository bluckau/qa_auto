package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestChallengeFour
{

	private static int testNumber;
	private static WebDriver driver;
	private static WebDriverWait wait;
	private static MainPage mainPage;

	@Before
	public void before()
	{
		System.err.println("	Running Test " + testNumber);
		testNumber++;
	}

	@After
	public void after()
	{
		System.err.println("	Finished Running Test " + testNumber);
		testNumber++;
	}

	@BeforeClass
	public static void beforeClass()
	{
		driver = Automation.getDriver();
		wait = Automation.getWait();
		mainPage = new MainPage();
	}

	@Test
	public void testNav1()
	{
		System.out.println("Testing testNav1");
		CompareResorts resortsCompare = new CompareResorts();

		resortsCompare.pageLoad();
		final Map<String,Integer> dMap = new HashMap<String,Integer>();	
		{
			dMap.put("Beaver Mountain", 114);
			dMap.put("Alta", 32);
			dMap.put("Beaver Mountain", 114);
			dMap.put("Brian Head Ski Resort", 35);
			dMap.put("Brighton", 35);
			dMap.put("Cherry Peak", 99);
			dMap.put("Deer Valley Resort", 36);
			dMap.put("Eagle Point", 217);
			dMap.put("Nordic Valley", 51);
			dMap.put("Park City Mountain", 32);
			dMap.put("Powder Mountain", 55);
			dMap.put("Snowbasin Resort", 45);
		}

		for (Map.Entry<String, Integer> entry : dMap.entrySet())
		{
			int miles = resortsCompare.getMilesForResort(entry.getKey());
			assertEquals(entry.getValue().intValue(), miles);
		}
	}
}
