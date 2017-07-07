package com.stg.bluckau.qa;


public class EmailProperties
{
	private String recipients;
	private String sender;
	private String emailAuthFileName;
	private String body;
	private String subject;
	private String fileName;
	private String filesToSend;

	public EmailProperties(String recipients, String sender, String emailAuthFileName, String body, String subject,
			String filesToSend)
	{
		this.recipients = recipients;
		this.sender = sender;
		this.emailAuthFileName = emailAuthFileName;
		this.body = body;
		this.subject = subject;
		this.setFilesToSend(filesToSend);
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

	public String getFilesToSend()
	{
		return filesToSend;
	}

	public void setFilesToSend(String filesToSend)
	{
		this.filesToSend = filesToSend;
	}
}
