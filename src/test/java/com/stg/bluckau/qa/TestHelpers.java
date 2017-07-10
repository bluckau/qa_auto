package com.stg.bluckau.qa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

		for (int i = 0; i < rows; i++)
		{
			// System.out.print("processing row " + i);
			for (int j = 0; j < columns; j++)
			{
				// System.out.print("processing column " + j);
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
		System.err.println("****getWebData");
		System.err.println("file name: " + fileName);
		if (new File(fileName).exists())
		{
			// System.out.println("file exists");
		} else
		{
			System.err.println("File " + fileName + "not exist");
			System.exit(99);
		}

		String[][] theArray = null;
		try
		{
			POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(fileName));
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workBook.getSheetAt(0);
			HSSFRow row;
			// HSSFCell cell;

			int rows = 0;
			String cellData = "";
			rows = sheet.getPhysicalNumberOfRows();
			// System.out.println("Found number of rows as:" + rows);
			// System.out.println("num of coumns is:");
			theArray = new String[rows - 1][columns];

			// start at row 1 not 0
			for (int r = 1; r < rows; r++)
			{
				// System.out.println("processing row " + r);
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
								// System.out.println("Adding to the array " +
								// cellData.toString());
								theArray[r - 1][c] = cellData;
							}
						}
					}
				}
			}
			// System.out.println("Close workbook");
			workBook.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		// printArray(theArray, columns);

		return theArray;
	}

	public static List<String> getResorts(String resortsFileName)
	{
		List<String> list = new ArrayList<String>();
		try
		{
			POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(resortsFileName));
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workBook.getSheetAt(0);
			HSSFRow row;
			// HSSFCell cell;

			// get the resorts

			int rows = 0;
			String cellData = "";
			rows = sheet.getPhysicalNumberOfRows();

			// System.out.println("Found number of rows as:" + rows);


			// start at row 1 not 0
			for (int r = 1; r < rows; r++)
			{
				row = sheet.getRow(r);

				HSSFCell tmpc = row.getCell(0);
				// System.out.println(tmpc);
				if (tmpc != null)
				{
					DataFormatter formatter = new DataFormatter();
					cellData = formatter.formatCellValue(tmpc);
					// System.out.println("ADDING " + cellData);
					if (!("".equals(cellData)))
					{
						// System.out.println("ADDING " + cellData);
						list.add(cellData);
					}
				}
				// }

			}
			// System.out.println("Close workbook");
			workBook.close();
			System.exit(1);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return list;
	}

	public static Object[][] getSearchData(String searchFileName, String resortsFileName)
	{

		List<String> resorts = new LinkedList<String>();
		resorts = getResorts(resortsFileName);

		Object[][] combinationsArray = getWebData(searchFileName, 2);
		Object[][] parametersArray = new String[resorts.size()][3];
		// iterate over resorts, build the array of combinations
		int count=0;
		for (String s : resorts)
		{
			count++;
			String[] rowArray = new String[3];
			rowArray[0] = s;
			rowArray[1] = combinationsArray[count - 1][0].toString();
			rowArray[2] = combinationsArray[count - 1][1].toString();
		}

		return parametersArray;
	}

}
