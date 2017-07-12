package com.stg.bluckau.qa;



import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestRunner;


/**
 * @author Brian Luckau
 *
 *         TODO: would like to use log levels for these System.out.println
 */
public class RealTimeListener implements ITestListener
{
	private ITestContext context;
	private TestLogUtil testLogUtil;
	private Logger logger;
	@Override
	public void onStart(ITestContext context) {
		this.context = context;
		testLogUtil = new TestLogUtil(context);
		logger = testLogUtil.getLogger();
		System.out.println("testlogutil instance: " + testLogUtil);
		System.out.println("Testing Log Level:" + context.getAttribute("consoleLogLevel"));
		System.out.println("TESTNG Verbosity: " + TestRunner.getVerbose());
		System.out.println("Start Of Execution(TEST)->" + context.getName());
	}

	@Override
	public void onTestStart(ITestResult result)
	{
		System.out.println("Test Started->" + context.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result)
	{
		logger.info("Test Suceeded->" + context.getName());

		// if (logger.getLevel().isLessSpecificThan(Level.WARN))
		// {
		// TODO sort or rotate these files
		TestLogUtil.takeScreenShot("success" + context.getName() + ".jpg");
		// }
	}

	@Override
	public void onTestFailure(ITestResult context)
	{
		logger.error("Test Failed->" + context.getName());

		// if (logger.getLevel().isLessSpecificThan(Level.FATAL))
		// {
		// TODO sort or rotate these files
		TestLogUtil.takeScreenShot("failure" + context.getName() + ".jpg");

		// }
	}


	@Override
	public void onTestSkipped(ITestResult context)
	{
		System.out.println("Test Skipped->" + context.getName());
	}

	@Override
	public void onFinish(ITestContext context)
	{
		System.out.println("END Of Execution(TEST)->" + context.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult context)
	{
		// Do we need?
	}

}