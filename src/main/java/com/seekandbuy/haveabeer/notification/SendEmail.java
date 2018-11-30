package com.seekandbuy.haveabeer.notification;

import java.util.*; 
import javax.mail.*; 
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;

public class SendEmail {

	//email default - seekandbuyorganization@gmail.com
	public boolean sendNotification(String emailFrom, String pass, String emailTo, String subject, String msg) {
		boolean sendOK = false;
		final String username = emailFrom;
		final String password = pass;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
			}
		  });
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailFrom));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(emailTo));
			message.setSubject(subject); //seekandbuy
			message.setText(msg); //"Hey there! \n\n We a new product for you =]"
			Transport.send(message);
			sendOK =  true;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		return sendOK;
	}
	
	/*
	public static void main(String[] args) {
		NotificationBeer notification = new NotificationBeer();
		if(notification.sendNotification("seekandbuyorganization@gmail.com", "12345678organization", "jhonattan.yoru@gmail.com", "teste", "deu certo!")){
			System.out.println("deu tudo certo!");
		}
	}
	*/
}