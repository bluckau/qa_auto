package com.stg.bluckau.qa;

import org.testng.ISuite;
import org.testng.ITestContext;

public class EmailHelper
{
	private TestLogger logger;

	public EmailHelper(ITestContext context)
	{
		logger = new TestLogger(context);
	}

	public void sendTestResults(String addresses, String from, ITestContext context, String testingLogLevel)
	{
		logger.log(LogLevel.TRACE, "running sendTestResults");
		//The suite from the context
		ISuite suite = context.getSuite();
		String bodyText = "";

		String filesToSend = "";
		int loglevel = Integer.parseInt(testingLogLevel);// todo: use an enum
		if (loglevel > 0)
		{
			bodyText = "Test ran for " + suite.getName();
		}
		if (loglevel > 1)
		{
			filesToSend = "test-output/target/surefire-reports/emailable-report.html";
		}

		EmailProperties emailProperties = new EmailProperties(addresses, from, "src/test/resources/mail.properties",
				bodyText, "Test results from testng", filesToSend);
		EmailHandler emailHandler = new EmailHandler(emailProperties);
		emailHandler.dispatchEmail();

	}

}
