package com.stg.bluckau.qa;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TestChallengeOne
{
	private static int testNumber;
	private static MainPage mainPage;

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
	}

	@AfterClass
	public static void afterclass()
	{
		Automation.quit();
		Automation.driver = null;
	}

	@Test(dataProvider = "webData")
	public void testTitle(String pageURL, String verificationText)
	{
		mainPage.pageLoad();
		String title = Automation.getPageTitle();
		assertEquals(title, verificationText);
	}

	// TODO: //move this to a common test class
	public static Object[][] getURLs(String file)
	{
		String[][] theArray = null;

		try
		{
			POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workBook.getSheetAt(0);
			HSSFRow row;
			// HSSFCell cell;

			int rows = 0;
			int columns = 0;

			rows = sheet.getPhysicalNumberOfRows();

			//get the columns in a robust manner
			int tmp=0;
			for(int i = 0; i < rows; i++) {
				row = sheet.getRow(i);
				if(row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > columns)
					{
						columns = tmp;
					}
				}
			}

			theArray = new String[rows - 1][columns];

			// start at row 1 not 0
			for (int r = 1; r < rows; r++)
			{
				row = sheet.getRow(r);
				if (row != null)
				{
					for (int c = 0; c < columns; c++)
					{
						// url = row.getCell(0).toString();
						// verificationText = row.getCell(1).toString();
						theArray[r - 1][c] = row.getCell((short) c).toString();
					}
				}
			}
			workBook.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return theArray;
	}

	@DataProvider(name="webData")
	public Object[][] webData() {
		Object[][] arrayObject = getURLs("url_verification.xls");
		return arrayObject;
	}
}