/**
 *
 */
package com.stg.bluckau.qa;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.appender.OutputStreamAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;


/**
 * @author Brian Luckau Logs to stdout and optionally to the email reports.
 *         TODO: Would be best to implement slf4j.Logger interface
 */
/**
 * @author Brian Luckau
 *
 */
public class TestLogUtil
{
	private static final String LOGFILE_NAME = "c:\\temp\\log.txt";
	private static final String LOGGER_NAME = "TestLogger";
	private static WebDriver driver;
	private static boolean isInitialized = false;
	private Logger logger;
	private static ITestContext context;
	private static TestLogUtil currentInstance;

	public TestLogUtil(ITestContext pContext)
	{
		context = pContext;
	}

	public static TestLogUtil getInstance()
	{
		System.out.println("Current instance: " + currentInstance);
		return currentInstance;
	}

	public Logger getLogger()
	{
		if (! isInitialized)
		{
			return initialize();
		}
		else
		{
			return logger;
		}
	}

	public Logger initialize()
	{
		Logger logger = LogManager.getLogger(LOGGER_NAME);
		String emailLogLevel = setDefault("ERROR",context.getAttribute("emailLogLevel").toString());
		String consoleLogLevel = setDefault("INFO", context.getAttribute("consoleLogLevel").toString());
		String fileLogLevel = setDefault("DEBUG",context.getAttribute("fileLogLevel").toString());
		// addFileAppender(String logPath, String name, Level level)
		// addFileAppender("c:\\temp\\log.txt", LOGGER_NAME,
		// Level.toLevel(fileLogLevel));
		// addReporterLogAppender(LOGGER_NAME, Level.toLevel(fileLogLevel));
		// addOutputStreamAppender(System.out, LOGGER_NAME,
		// Level.toLevel(fileLogLevel));
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

		builder.setStatusLevel(Level.ERROR);
		builder.setConfigurationName("RollingBuilder");

		// crate a new logger for reporter
		AppenderComponentBuilder appenderBuilder = builder.newAppender("Reporter", "REPORTER").addAttribute("target",
				ConsoleAppender.Target.SYSTEM_OUT);
		appenderBuilder
		.add(builder.newLayout("PatternLayout").addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"));
		builder.add(appenderBuilder);

		// create a console appender
		appenderBuilder = builder.newAppender("Stdout", "CONSOLE").addAttribute("target",
				ConsoleAppender.Target.SYSTEM_OUT);
		appenderBuilder
		.add(builder.newLayout("PatternLayout").addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable"));
		builder.add(appenderBuilder);

		// create a rolling file appender
		LayoutComponentBuilder layoutBuilder = builder.newLayout("PatternLayout").addAttribute("pattern",
				"%d [%t] %-5level: %msg%n");
		ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
				.addComponent(builder.newComponent("CronTriggeringPolicy").addAttribute("schedule", "0 0 0 * * ?"))
				.addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "100M"));

		appenderBuilder = builder.newAppender("rolling", "RollingFile").addAttribute("fileName", "target/rolling.log")
				.addAttribute("filePattern", "target/archive/rolling-%d{MM-dd-yy}.log.gz").add(layoutBuilder)
				.addComponent(triggeringPolicy);

		builder.add(appenderBuilder);

		// create the new logger
		builder.add(builder.newLogger("TestLogger", Level.DEBUG).add(builder.newAppenderRef("rolling"))
				.addAttribute("additivity", false));

		builder.add(builder.newRootLogger(Level.DEBUG).add(builder.newAppenderRef("rolling")));
		LoggerContext ctx = Configurator.initialize(builder.build());

		System.out.println("going to return logger" + logger);
		return logger;
	}

	public static void setLogLevel(String level)
	{
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();

		Configurator.setLevel("slsls", Level.DEBUG);
		ctx.updateLoggers(); // This causes all Loggers to refetch information
		// from their LoggerConfig
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
		Reporter.log("<img src=\"" + fileName + " alt=\"Alt Text\"/>");

		// get the attribute
		String attachments = context.getAttribute("attachments").toString();
		if (attachments == null || attachments.equals(""))
		{
			attachments = relativeName;

		} else
		{
			attachments = attachments + "," + relativeName;
		}

	}

	/**
	 * Simply sets the default on a string depending on if it was already set to
	 * to something
	 *
	 * @param var
	 * @param defaultValue
	 */
	private String setDefault(String var, String defaultValue)
	{
		if (var == null || "".equals(var.trim()))
		{
			var = defaultValue;
		}
		return var;
	}
	/*
	 * public static void addFileAppender(String logPath, String name, Level
	 * level) { Logger log = LogManager.getLogger(); LoggerContext ctx =
	 * (LoggerContext) LogManager.getContext(true); Configuration config =
	 * ctx.getConfiguration();
	 *
	 * Map<String, LoggerConfig> loggrs = config.getLoggers();
	 * System.out.println("map is " + loggrs); LoggerConfig lc =
	 * config.getLoggers().get(name);
	 *
	 * Layout<?> layout =
	 * PatternLayout.createLayout(PatternLayout.SIMPLE_CONVERSION_PATTERN, null,
	 * config, null, null, false, false, null, null); Appender fileAppender =
	 * FileAppender.createAppender(logPath, "false", "false", name, "true",
	 * "false", "true", null, layout, null, "false", null, config);
	 * fileAppender.start(); System.out.println("is lc  " + lc);
	 *
	 * lc.addAppender(fileAppender, Level.DEBUG, null); ctx.updateLoggers(); }
	 */

	/*
	 * public static void addOutputStreamAppender(OutputStream appendTo, String
	 * name, Level level) { ConfigurationBuilder<BuiltConfiguration> builder =
	 * ConfigurationBuilderFactory.newConfigurationBuilder();
	 * builder.setStatusLevel(level);
	 * builder.setConfigurationName("BuilderTest");
	 * builder.add(builder.newFilter("ThresholdFilter", Filter.Result.ACCEPT,
	 * Filter.Result.NEUTRAL) .addAttribute("level", level));
	 * AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout",
	 * "CONSOLE").addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
	 * appenderBuilder
	 * .add(builder.newLayout("PatternLayout").addAttribute("pattern",
	 * "%d [%t] %-5level: %msg%n%throwable"));
	 * appenderBuilder.add(builder.newFilter("MarkerFilter", Filter.Result.DENY,
	 * Filter.Result.NEUTRAL) .addAttribute("marker", "FLOW"));
	 * builder.add(appenderBuilder);
	 * builder.add(builder.newLogger("org.apache.logging.log4j",
	 * Level.DEBUG).add(builder.newAppenderRef("Stdout"))
	 * .addAttribute("additivity", false));
	 * builder.add(builder.newRootLogger(Level.ERROR).add(builder.newAppenderRef
	 * ("Stdout"))); LoggerContext ctx =
	 * Configurator.initialize(builder.build());
	 *
	 *
	 * LoggerContext ctx = (LoggerContext) LogManager.getContext();
	 * Configuration config = ctx.getConfiguration(); LoggerConfig lc =
	 * config.getLoggers().get(name); Appender streamAppender =
	 * OutputStreamAppender.createAppender(null, null, appendTo, name, true,
	 * false); streamAppender.start(); lc.addAppender(streamAppender,
	 * Level.INFO, null); //
	 * config.addLoggerAppender((org.apache.logging.log4j.core.Logger) //
	 * LogManager.getRootLogger(), streamAppender); ctx.updateLoggers(config);
	 *
	 * }
	 */

	/*
	 * public static void addReporterLogAppender(String name, Level level) {
	 * LoggerContext ctx = (LoggerContext) LogManager.getContext();
	 * Configuration config = ctx.getConfiguration(); LoggerConfig lc =
	 * config.getLoggers().get(name); ReporterAppender reportAppender =
	 * ReporterAppender.createAppender(name, null, null, name);
	 * reportAppender.start(); lc.addAppender(reportAppender, Level.INFO, null);
	 * ctx.updateLoggers(config); }
	 */
}
