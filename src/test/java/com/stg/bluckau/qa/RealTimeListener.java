package com.stg.bluckau.qa;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestRunner;


/**
 * @author Brian Luckau
 *
 */
public class RealTimeListener implements ITestListener
{
	private ITestContext context;
	private Logger logger;
	@Override
	public void onStart(ITestContext context) {
		this.context = context;
		TestLogUtil.initializeLoggers(context);
		logger = LogManager.getLogger(TestLogUtil.LOGGER_NAME);
		logger.info("Testing Log Level:" + context.getAttribute("consoleLogLevel"));
		logger.info("TESTNG Verbosity: " + TestRunner.getVerbose());
		logger.info("Start Of Execution(TEST)->" + context.getName());
	}

	@Override
	public void onTestStart(ITestResult result)
	{
		logger.info("Test Started->" + context.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result)
	{
		logger.info("Test Suceeded->" + context.getName());
		// TODO: If email Log level > WARN
		TestLogUtil.takeScreenShot("success" + result.getTestName() + ".jpg");
	}

	@Override
	public void onTestFailure(ITestResult result)
	{
		logger.error("Test Failed->" + context.getName());
		// TODO: If email Log level > FATAL
		TestLogUtil.takeScreenShot("failure" + context.getName());
	}


	@Override
	public void onTestSkipped(ITestResult context)
	{
		logger.info("Test Skipped->" + context.getName());
	}

	@Override
	public void onFinish(ITestContext context)
	{
		logger.info("END Of Execution(TEST)->" + context.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult context)
	{
		// Do we need?
	}

}