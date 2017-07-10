package com.stg.bluckau.qa;

import java.io.File;
import java.io.IOException;

import org.testng.ISuite;
import org.testng.ITestContext;

public class EmailHelper
{
	private TestLogger logger;

	private static final String EMAIL_REPORT_JENKINS = "test-output/target/surefire-reports/emailable-report.html";
	private static final String EMAIL_REPORT_NONJENKINS = "test-output/emailable-report.html";

	public EmailHelper(ITestContext context)
	{
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
			logLevelString = "DEBUG";
		}
		//logger = new TestLogger(LogLevel.valueOf(logLevelString));
		logger = new TestLogger(LogLevel.valueOf("DEBUG"), LogLevel.valueOf("DEBUG"));
	}

	public void sendTestResults(String addresses, String from, ITestContext context)
	{
		String current = "";
		try
		{
			current = new java.io.File(".").getCanonicalPath();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Current dir:" + current);
		String currentDir = System.getProperty("user.dir");
		System.out.println("Current dir using System:" + currentDir);
		logger.log(LogLevel.TRACE, "running sendTestResults");
		//The suite from the context
		ISuite suite = context.getSuite();
		String bodyText = "";
		String emailFilePath = "";
		String filesToSend = "";
		bodyText = "email report for " + suite.getName();
		//For some reason inside of Jenkins the file has a different name
		if (new File(EMAIL_REPORT_JENKINS).exists())
		{
			emailFilePath = EMAIL_REPORT_JENKINS;
		} else
		{
			emailFilePath = EMAIL_REPORT_NONJENKINS;
		}

		filesToSend += emailFilePath;
		// TODO: add the screen shots or get them to a universally accessible
		// path
		EmailProperties emailProperties = new EmailProperties(addresses, from, "src/test/resources/mail.properties",
				bodyText, "Test results from testng", filesToSend);
		EmailHandler emailHandler = new EmailHandler(emailProperties);
		emailHandler.dispatchEmail();
	}
}
