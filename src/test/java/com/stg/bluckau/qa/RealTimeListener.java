package com.stg.bluckau.qa;


import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestRunner;

import com.stg.bluckau.qa.LogLevel;

/**
 * @author Brian Luckau
 *
 */
public class RealTimeListener implements ITestListener
{

	@SuppressWarnings("unused")
	private ITestContext context;
	private TestLogger testLogger;
	private LogLevel logLevel;

	RealTimeListener(ITestContext context, WebDriver driver, TestLogger testLogger)
	{
		this.context = context;
		this.testLogger = testLogger;
		this.logLevel = (LogLevel) context.getAttribute("logLevel");
	}

	@Override
	public void onStart(ITestContext arg0) {
		testLogger.log(LogLevel.OFF, "Testing Log Level:" + logLevel);
		testLogger.log(LogLevel.INFO, "TESTNG Verbosity: " + TestRunner.getVerbose());
		testLogger.log(LogLevel.INFO, "Start Of Execution(TEST)->" + arg0.getName());
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		testLogger.log(LogLevel.INFO, "Test Started->" + arg0.getName());
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		testLogger.log(LogLevel.INFO, "Test Suceeded->" + arg0.getName());
		if (this.logLevel.getValue() >= LogLevel.INFO.getValue())
		{
			TestLogger.takeScreenShot("success" + arg0.getName() + ".jpg");
		}
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		testLogger.log(LogLevel.INFO, "Test Failed->" + arg0.getName());
		if (this.logLevel.getValue() >= LogLevel.ERROR.getValue())
		{
			TestLogger.takeScreenShot("failure" + arg0.getName() + ".jpg");
		}
	}


	@Override
	public void onTestSkipped(ITestResult arg0) {
		testLogger.log(LogLevel.INFO, "Test Skipped->" + arg0.getName());
	}

	@Override
	public void onFinish(ITestContext arg0) {
		testLogger.log(LogLevel.INFO, "END Of Execution(TEST)->" + arg0.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// Do we need?
	}

}