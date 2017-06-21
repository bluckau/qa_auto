package com.stg.bluckau.qa;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

public class TestHelpers
{
	public static void printArray(Object[][] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.println("i = " + i);

			for (int j = 0; j < array[i].length; j++)
			{
				System.out.println("j = " + j);
				System.out.println("Values at arr[" + i + "][" + j + "] is " + array[i][j].toString());
			}
		}
	}

	public static Object[][] getWebData(String fileName)
	{
		System.out.println("****getWebData");
		System.out.println("file name: " + fileName);
		String[][] theArray = null;
		System.out.println("about to try");
		try
		{
			POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(fileName));
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workBook.getSheetAt(0);
			HSSFRow row;
			// HSSFCell cell;

			int rows = 0;
			int columns = 0;

			rows = sheet.getPhysicalNumberOfRows();
			System.out.println("Found number of rows as:" + rows);
			// get the columns in a robust manner
			int tmp = 0;
			for (int i = 0; i < rows; i++)
			{
				row = sheet.getRow(i);
				if (row != null)
				{
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > columns)
					{
						System.out.println("setting number of columns to: " + tmp);
						columns = tmp;
					}
				}
			}

			theArray = new String[columns][rows];

			// start at row 1 not 0
			for (int r = 1; r < rows; r++)
			{
				System.out.println("processing row " + r);
				row = sheet.getRow(r);
				if (row != null)
				{
					String cellData = "";
					for (int c = 0; c < columns; c++)
					{
						HSSFCell tmpc = row.getCell((short) c);
						if (tmpc != null)
						{
							if (!"".equals(cellData))
							{
								System.out.println("Adding to the array " + cellData.toString());
								theArray[r - 1][c] = cellData;
							}
						}
					}
				}
				System.out.println("Done processing row " + r);
			}
			System.out.println("Close workbook");
			workBook.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("finished with try");

		System.out.println("length = " + theArray.length);
		System.out.println("length[0] = " + theArray[0].length);

		// printArray(theArray);

		return theArray;
	}
}
