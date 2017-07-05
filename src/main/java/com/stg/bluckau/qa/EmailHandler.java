package com.stg.bluckau.qa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailHandler
{
	// TODO: do not hard code
	private static final String SMTPServer = "smtp.gmail.com";
	// private static final int port = 465;

	private String subject = "Subject";
	private String userName = "";
	private String password = "";
	private String from = "brian.luckau@stgconsulting.com";
	private String to = "brian.luckau@stgconsulting.com";
	private String fileName;

	public EmailHandler(String toString, String subject, String fileName)
	{
		this.to = toString;
		this.subject = subject;
		this.fileName = fileName;
	}

	public void getAuth(String fileName)
	{
		Properties prop = new Properties();
		try
		{
			prop.load(new FileInputStream(fileName));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userName = prop.getProperty("username");
		if (prop.getProperty("password") != null)
		{
			password = prop.getProperty("password");
		}

	}

	public void dispatchEmail(String address, String fileName)
	{
		getAuth("mail.properties");
		//Get the session object
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host",SMTPServer );
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName,password);
			} });

		try{

			MimeMessage message = new MimeMessage(session);
			message.setFrom(from);
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			message.setSubject("Test Results");

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText("messagy body test");
			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			// Send the complete message parts

			message.setContent(multipart);
			// TODO: if filename is not null or empty?

			String filename = "miles.xls";
			DataSource source = new FileDataSource(fileName);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			Transport.send(message);
			System.out.println("message sent");
		} catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}
}
