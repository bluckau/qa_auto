package com.stg.bluckau.qa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;


/**
 * @author Brian Luckau
 *
 */
public class TestHelpers
{
	static Logger logger = LogManager.getLogger(TestLogUtil.LOGGER_NAME);

	/*
	 * printArray prints the contents of an array. It is mainly for debugging
	 * the test framework
	 *
	 * @param array the 2d array to print
	 *
	 * @param columns How many columns we are using in this array
	 */
	public static void debugPrintArray(Object[][] array, int columns)
	{
		logger.trace("starting printArray");
		int rows = array.length;
		for (int i = 0; i < rows; i++)
		{
			logger.trace("processing row " + i);
			for (int j = 0; j < columns; j++)
			{
				logger.trace("processing column " + j);
				Object temp = array[i][j];
				if (temp != null)
				{
					logger.debug("Values at arr[" + i + "][" + j + "] " + temp.toString());
				}
				else
				{
					logger.debug("Values at arr[" + i + "][" + j + "] is NULL");
				}
			}
		}
	}


	/**
	 * @param fileName
	 *            The xls file name to process
	 * @param columns
	 *            How many columns are we using in this spreadsheet
	 * @return a 2d array representing the data.
	 */
	public static Object[][] getWebData(String fileName, int columns)
	{
		logger.trace("*getWebData*");
		logger.trace("file name: " + fileName);
		if (new File(fileName).exists())
		{
			logger.debug("file exists");
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
			logger.debug("Found number of rows as:" + rows);
			logger.debug("num of coumns is:");
			theArray = new String[rows - 1][columns];

			// start at row 1 not 0
			for (int r = 1; r < rows; r++)
			{
				logger.trace("processing row " + r);
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
								logger.trace("Adding to the array " + cellData.toString());
								theArray[r - 1][c] = cellData;
							}
						}
					}
				}
			}
			logger.debug("Close workbook");
			workBook.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		if (logger.isDebugEnabled())
		{
			debugPrintArray(theArray, columns);
		}
		return theArray;
	}


	/**
	 * Gets the list of resorts from a spreadsheet
	 *
	 * @param resortsFileName
	 * @return a List of strings; the list of resorts
	 */
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

			logger.debug("Found number of rows as:" + rows);

			// start at row 1 not 0
			for (int r = 1; r < rows; r++)
			{
				row = sheet.getRow(r);

				HSSFCell tmpc = row.getCell(0);
				logger.trace(tmpc);
				if (tmpc != null)
				{
					DataFormatter formatter = new DataFormatter();
					cellData = formatter.formatCellValue(tmpc);
					logger.trace("ADDING " + cellData);
					if (!("".equals(cellData)))
					{
						logger.trace("ADDING " + cellData);
						list.add(cellData);
					}
				}
			}
			workBook.close();
			System.exit(1);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * like getWebData but specific to the search test
	 *
	 * @param fileName
	 *            The xls file name to process
	 * @param columns
	 *            How many columns are we using in this spreadsheet
	 * @return a 2d array representing the data.
	 */
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
