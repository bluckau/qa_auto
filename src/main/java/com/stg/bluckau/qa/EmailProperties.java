package com.stg.bluckau.qa;


public class EmailProperties
{
	private String recipients;
	private String sender;
	private String emailAuthFileName;
	private String body;// this will likely be replaced in a further development
	private String subject;

	public EmailProperties(String recipients, String sender, String emailAuthFileName, String body, String subject)
	{
		this.recipients = recipients;
		this.sender = sender;
		this.emailAuthFileName = emailAuthFileName;
		this.body = body;
		this.subject = subject;
	}

	public String getRecipients()
	{
		return recipients;
	}

	public void setRecipients(String recipients)
	{
		this.recipients = recipients;
	}

	public String getSender()
	{
		return sender;
	}

	public void setSender(String sender)
	{
		this.sender = sender;
	}

	public String getEmailAuthFileName()
	{
		return emailAuthFileName;
	}

	public void setEmailAuthFileName(String emailAuthFileName)
	{
		this.emailAuthFileName = emailAuthFileName;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}
}
