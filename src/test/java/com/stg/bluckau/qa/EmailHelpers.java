package com.stg.bluckau.qa;

public class EmailHelpers
{
	public EmailHelpers()
	{

	}

	public void sendTestResults(String addresses, String from)
	{

		EmailProperties emailProperties = new EmailProperties(addresses, from, "src/main/resources/mail.properties",
				"body text",
				"SubJECT");
		EmailHandler emailHandler = new EmailHandler(emailProperties);
		emailHandler.dispatchEmail();

	}

}
