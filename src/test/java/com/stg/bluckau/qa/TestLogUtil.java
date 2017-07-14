/**
 *
 */
package com.stg.bluckau.qa;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;


/**
 * @author Brian Luckau Logs to stdout and optionally to the email reports.
 */

public class TestLogUtil
{
	private static final String LOGFILE_NAME = "c:\\temp\\log.txt";
	public static final String LOGGER_NAME = "TestLogger";
	private static WebDriver driver;
	private static Logger logger = LogManager.getLogger("LOGGER_NAME");
	public static boolean loggerIsInitialized = false;
	public static String DEFAULT_LOGLEVEL = "WARN";
	public static String consoleLogLevel = DEFAULT_LOGLEVEL;
	public static String emailLogLevel = DEFAULT_LOGLEVEL;
	public static String fileLogLevel = DEFAULT_LOGLEVEL;
	public static ArrayList<String> screenshots = new ArrayList<String>();

	public static Logger getLogger()
	{
		if (!loggerIsInitialized)
		{
			System.err.println("Warning, loggers have not been explicitly initialized for test framework");
			return LogManager.getLogger(LOGGER_NAME);
		} else
		{
			// Don't currently know if this refreshes anything after we modify
			// the log levels
			logger = LogManager.getLogger(LOGGER_NAME);
			logger.debug("getLogger getting existing instance");
			return logger;
		}
	}

	/**
	 * Initializes the logger.
	 *
	 * @return the logger instance.
	 */
	public static void initializeLoggers(ITestContext context)
	{
		// getAttributeOrDefault(ITestContext context, String attr, String
		// defaultValue)
		emailLogLevel = getAttributeOrDefault(context, "emailLogLevel", DEFAULT_LOGLEVEL);
		consoleLogLevel = getAttributeOrDefault(context, "consoleLogLevel", DEFAULT_LOGLEVEL);
		fileLogLevel = getAttributeOrDefault(context, "fileogLevel", DEFAULT_LOGLEVEL);

		// TODO: Update the log levels. The below code was
		loggerIsInitialized = true;
	}


	/**
	 * Takes a screen shot and adds it to the file name. Adds the file/path to
	 * the ItestContext
	 *
	 * @param fileName
	 */
	public static void takeScreenShot(String fileName)
	{
		driver = Automation.getDriver();
		if (driver == null)
			logger.error("DRIVER IS NULL");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String relativeName = "";
		try
		{
			File dir = new File("test-output\\Screenshots");
			dir.mkdir();
			relativeName = "test-output\\Screenshots\\" + fileName;
			String current = new java.io.File(".").getCanonicalPath();
			logger.trace("Current dir:" + current);
			FileUtils.copyFile(scrFile, new File(relativeName));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reporter.log("<img src=\"" + fileName + " alt=\"Alt Text\"/>");

		// Add the screenshots to the list of attachments
		screenshots.add(relativeName);
	}

	/**
	 * Simply sets the default on a string depending on if it was already set to
	 * to something
	 *
	 * @param var
	 * @param defaultValue
	 */
	private static String getAttributeOrDefault(ITestContext context, String attr, String defaultValue)
	{
		Object temp = context.getAttribute(attr);
		if ((temp != null) && !"".equals(temp.toString()))
		{
			return temp.toString();
		}
		return defaultValue;
	}
}
