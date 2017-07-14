package com.stg.bluckau.qa;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ITestContext;

public class EmailHelper
{
	private Logger logger = LogManager.getLogger("testLogger");

	private static final String EMAIL_REPORT_JENKINS = "test-output\\target\\surefire-reports\\emailable-report.html";
	private static final String EMAIL_REPORT_NONJENKINS = "test-output\\emailable-report.html";

	public EmailHelper(ITestContext context)
	{
		logger.debug("attributes:" + context.getAttributeNames().toString());
		logger.error("Test Failed->" + context.getName());
		logger.debug("attribute says:" + context.getAttribute("logLevel"));
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
		logger.debug("Current dir:" + current);
		logger.trace("running sendTestResults");
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


		filesToSend = emailFilePath;

		if (TestLogUtil.screenshots.size() > 0)
			filesToSend = filesToSend + "," + StringUtils.join(TestLogUtil.screenshots, (","));


		EmailProperties emailProperties = new EmailProperties(addresses, from, "src/test/resources/mail.properties",
				bodyText, "Test results from testng", filesToSend);
		EmailHandler emailHandler = new EmailHandler(emailProperties);
		emailHandler.dispatchEmail();
	}
}
