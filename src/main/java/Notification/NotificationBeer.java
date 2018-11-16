package Notification;

import java.util.*; 
import javax.mail.*; 
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;

public class NotificationBeer implements Notifications {

	@Override
	public boolean sendNotification(String email) {
		boolean sendOK = false;
		final String username = "fromEmail@gmail.com"; //Colocar email da empresa aqui
		final String password = "senhaFromEmail"; //passworda do email da empresa
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
			message.setFrom(new InternetAddress("fromEmail@gmail.com")); //Colocar email da empresa aqui
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(email));
			message.setSubject("Seek and Buy");
			message.setText("Hey there!"
				+ "\n\n We a new product for you =]");
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
		if(notification.sendNotification("toEmail")){
			System.out.println("deu tudo certo!");
		}
	}
	*/
}
