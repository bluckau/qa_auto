package com.stg.bluckau.qa;

import org.testng.ITestContext;

public class EmailHelpers
{
	public EmailHelpers()
	{

	}

	public void sendTestResults(String addresses, String from, ITestContext context, String testingLogLevel)
	{
		System.out.println("testing log level is " + testingLogLevel);
		String bodyText = "awkwardly sending an empty body";
		String filesToSend = "";
		int loglevel = Integer.parseInt(testingLogLevel);// todo: use an enum
		if (loglevel > 0)
		{
			bodyText = "Test ran for " + context.getSuite().getName();
		}
		if (loglevel > 1)
		{
			filesToSend = "test-output/emailable-report.html";
		}

		EmailProperties emailProperties = new EmailProperties(addresses, from, "src/test/resources/mail.properties",
				bodyText, "Test results from testng", filesToSend);
		EmailHandler emailHandler = new EmailHandler(emailProperties);
		emailHandler.dispatchEmail();

	}

}
