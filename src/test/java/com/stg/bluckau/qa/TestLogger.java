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
	public TestLogger(ITestContext context)
	{
		consoleLogLevel = (LogLevel) (context.getAttribute("logLevel"));
		driver = Automation.getDriver();

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
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try
		{
			File dir = new File("test-output\\Screenshots");
			dir.mkdir();
			FileUtils.copyFile(scrFile, new File("test-output\\screenshots\\" + fileName));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
