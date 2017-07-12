package com.stg.bluckau.qa;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmailHandler
{
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private String userName;
	private String password;
	private String smtpServer;
	private String smtpPort;
	private String useAuth;
	private EmailProperties emailProperties;

	private Logger logger = LogManager.getLogger("testLogger");
	public EmailHandler(EmailProperties emailProperties)
	{
		this.emailProperties = emailProperties;
		loadEmailAuth(emailProperties.getEmailAuthFileName());
	}
	public void loadEmailAuth(String fileName)
	{
		Properties props = new Properties();
		try
		{
			System.out.println("loading from file name: " + fileName);
			props.load(new FileInputStream(fileName));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userName = props.getProperty("username");
		password = props.getProperty("password");
		smtpServer = props.getProperty("smtp_server");
		useAuth = props.getProperty("use_auth");
		smtpPort = props.getProperty("smtp_port");
	}

	// Dispatch multiple emails
	public void dispatchEmail()
	{

		String recipients;
		recipients = emailProperties.getRecipients();
		System.out.println("Recipients = " + recipients);
		for (String recipient : emailProperties.getRecipients().split(","))
		{
			sendEmail(recipient, emailProperties);
		}
	}

	public void sendEmail(String recipient, EmailProperties emailProperties)
	{
		//Get the session object
		Properties sessionProperties = System.getProperties();

		// create the properties to send to the mime message
		logger.info( "smtpServer = " + smtpServer);
		logger.info( "useAuthentication = " + useAuth);

		System.out.println("userName: " + userName);
		sessionProperties.setProperty("mail.smtp.host", smtpServer);
		sessionProperties.put("mail.smtp.port", smtpPort);
		sessionProperties.put("mail.smtp.auth", useAuth);
		sessionProperties.put("mail.smtp.", "true");
		sessionProperties.put("mail.smtp.socketFactory.fallback", "false");
		sessionProperties.put("mail.smtp.socketFactory.class", SSL_FACTORY);

		Session session = Session.getDefaultInstance(sessionProperties,
				new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			} });

		try{

			MimeMessage message = new MimeMessage(session);
			message.setFrom(emailProperties.getSender());
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(emailProperties.getSubject());

			BodyPart messageBodyPart = new MimeBodyPart();

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart.setText(emailProperties.getBody());
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();

			//Add files to send
			String filesToSend = emailProperties.getFilesToSend();
			System.out.println("Files to send: " + filesToSend);
			if (filesToSend != null && filesToSend.length() > 0)
			{
				for (String name : emailProperties.getFilesToSend().split(","))
				{
					DataSource source = new FileDataSource(name);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(name);
					multipart.addBodyPart(messageBodyPart);
				}
			}

			message.setContent(multipart);

			Transport.send(message);
			System.out.println("message sent");
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}
}