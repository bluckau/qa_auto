package com.stg.bluckau.qa;


import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestRunner;

import com.stg.bluckau.qa.LogLevel;

/**
 * @author Brian Luckau
 *
 *         TODO: would like to use log levels for these System.out.println
 */
public class RealTimeListener implements ITestListener
{
	private ITestContext context;

	@Override
	public void onStart(ITestContext context) {
		this.context = context;
		System.out.println("Testing Log Level:" + context.getAttribute("logLevel"));
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
		System.out.println("Test Suceeded->" + context.getName());

		LogLevel l;
		System.out.println("attributes:" + context.getAttributeNames().toString());
		// System.out.println("Test Failed->" + context.getName())
		System.out.println("attribute says:" + context.getAttribute("logLevel"));
		String logLevelString;

		Object logAttribute = context.getAttribute("logLevel");
		if (logAttribute != null)
		{
			logLevelString = logAttribute.toString();
		} else
		{
			// TODO: remove this and make sure things still work
			logLevelString = "DEBUG";
		}

		l = LogLevel.valueOf(logLevelString);

		// System.out.println("Log Level L is of value " + l.getValue());

		if (l.getValue() >= LogLevel.INFO.getValue())
		{
			TestLogger.takeScreenShot("success" + context.getName() + ".jpg");
		}
	}

	@Override
	public void onTestFailure(ITestResult context)
	{
		LogLevel l;
		//System.out.println("attributes:" + context.getAttributeNames().toString());
		System.out.println("Test Failed->" + context.getName());
		//System.out.println("attribute says:" + context.getAttribute("logLevel"));
		String logLevelString;

		Object logAttribute = context.getAttribute("logLevel");
		if (logAttribute != null)
		{

			logLevelString = logAttribute.toString();
		} else
		{
			//TODO: remove this and make sure things still work
			logLevelString = "DEBUG";
		}

		l = LogLevel.valueOf(logLevelString);

		//System.out.println("Log Level L is of value " + l.getValue());
		if (l.getValue() >= LogLevel.ERROR.getValue())
		{
			TestLogger.takeScreenShot("failure" + context.getName() + ".jpg");
		}
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