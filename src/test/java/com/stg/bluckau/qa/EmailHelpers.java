package com.stg.bluckau.qa;

import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.TestRunner;

public class EmailHelpers
{
	public EmailHelpers()
	{

	}

	public void sendTestResults(String addresses, String from, ITestContext context, String testingLogLevel)
	{
		System.out.println("running endTestResults");
		//The suite from the context
		ISuite s = context.getSuite();
		Reporter.log("Lets test some logging y'all!", true);

		// String bodyText = "test ran for " + s.getName();

		System.out.println("testing log level is " + TestRunner.getVerbose());

		// This is useless unless you add your own
		// System.out.println("All attributes: " + s.getAttributeNames());

		/*
		String filesToSend = "";
		int loglevel = Integer.parseInt(testingLogLevel);// todo: use an enum
		if (loglevel > 0)
		{
			bodyText = "Test ran for " + context.getSuite().getName();
		}
		if (loglevel > 1)
		{
			filesToSend = "test-output/target/surefire-reports/emailable-report.html";
		}

		EmailProperties emailProperties = new EmailProperties(addresses, from, "src/test/resources/mail.properties",
				bodyText, "Test results from testng", filesToSend);
		EmailHandler emailHandler = new EmailHandler(emailProperties);
		emailHandler.dispatchEmail();
		 */
	}

}
