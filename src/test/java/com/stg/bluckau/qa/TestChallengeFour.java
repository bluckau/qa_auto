package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestChallengeFour
{

	private static int testNumber;
	private static CompareResorts resortsCompare;

	@Before
	public void before()
	{
		System.err.println("Running Test " + ++testNumber);
	}

	@After
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

	@Test
	public void testMiles()
	{
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
