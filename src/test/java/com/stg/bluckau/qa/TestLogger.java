/**
 *
 */
package com.stg.bluckau.qa;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;

/**
 * @author Brian Luckau Logs to stdout and optionally to the email reports.
 *         TODO: Would be best to implement slf4j.Logger interface
 */
public class TestLogger
{


	private static WebDriver driver;
	private static LogLevel consoleLogLevel;
	private static LogLevel emailLogLevel;
	/**
	 *
	 */
	public TestLogger(LogLevel logLevel, LogLevel emailLogLevel)
	{
		driver = Automation.getDriver();
		this.consoleLogLevel = logLevel;
		this.emailLogLevel = emailLogLevel;
	}

	public TestLogger(String logLevelString, String emailLogLevelString)
	{
		this.consoleLogLevel = LogLevel.valueOf(logLevelString);
		this.emailLogLevel = LogLevel.valueOf(emailLogLevelString);
	}

	public void log(LogLevel requestedLevel, String text)
	{
		/*
		 * Currently the Report.log functionality with regards to printing to
		 * stdout is insufficient. If you want to output to the stdout then you
		 * MUST output to email as well. We want something Slightly better than
		 * that.
		 */
		if (requestedLevel.getValue() >= consoleLogLevel.getValue())
		{
			System.out.printf("%s: text", requestedLevel);
		}

		if (requestedLevel.getValue() >= emailLogLevel.getValue())
		{
			Reporter.log(text);
		}
	}

	public static void takeScreenShot(String fileName)
	{
		driver = Automation.getDriver();
		if (driver == null)
			System.out.println("DRIVER IS NULL");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fullFileName = "";
		String relativeName = "";
		try
		{
			File dir = new File("test-output\\Screenshots");
			dir.mkdir();
			relativeName = "test-output\\screenshots\\" + fileName;
			String current = new java.io.File(".").getCanonicalPath();
			System.out.println("Current dir:" + current);
			fullFileName = "current" + relativeName;
			FileUtils.copyFile(scrFile, new File(relativeName));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reporter.log("<img src=\"" + fullFileName + " alt=\"Alt Text\"/>");
	}
}
