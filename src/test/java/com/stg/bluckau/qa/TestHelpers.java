package com.stg.bluckau.qa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;

public class TestHelpers
{
	public static void printArray(Object[][] array, int columns)
	{
		int rows = array.length;
		System.out.println("rows = " + rows);
		System.out.println("columns = " + columns);

		for (int i = 0; i < rows; i++)
		{
			System.out.print("processing row " + i);

			for (int j = 0; j < columns; j++)
			{
				System.out.println("Is [i][j] null?");
				System.out.println(array[i][j]);

				System.out.print("processing column " + j);
				Object temp = array[i][j];
				if (temp != null)
				{
					System.out.println("Values at arr[" + i + "][" + j + "] " + temp.toString());
				}
				else
				{
					System.out.println("Values at arr[" + i + "][" + j + "] is NULL");
				}
			}
			System.out.println();
		}
	}
	public static Object[][] getWebData(String fileName, int columns)
	{
		System.out.println("****getWebData");
		System.out.println("file name: " + fileName);
		if (new File(fileName).exists())
		{
			System.out.println("file exists");
		} else
		{
			System.out.println("File " + fileName + "not exist");
			System.exit(99);
		}

		String[][] theArray = null;
		{
			POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(fileName));
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workBook.getSheetAt(0);
			HSSFRow row;
			// HSSFCell cell;

			int rows = 0;
			String cellData = "";
			rows = sheet.getPhysicalNumberOfRows();
			System.out.println("Found number of rows as:" + rows);
			System.out.println("num of coumns is:");
			theArray = new String[rows][columns];

			// start at row 1 not 0
			for (int r = 1; r < rows; r++)
			{
				System.out.println("processing row " + r);
				row = sheet.getRow(r);
				if (row != null)
				{
					for (int c = 0; c < columns; c++)
					{
						HSSFCell tmpc = row.getCell((short) c);
						if (tmpc != null)
						{
							DataFormatter formatter = new DataFormatter();
							cellData = formatter.formatCellValue(tmpc);

							if (!("".equals(cellData)))
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
		} catch(IOException e)
		{
			e.printStackTrace();
		}

		System.out.println("length = " + theArray.length);
		// System.out.println("length[0] = " + theArray[0].length);
		// System.out.println("about to call print array");
		printArray(theArray, columns);

		return theArray;

	}
}
