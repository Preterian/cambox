package notifiers;

import models.User;

import org.apache.commons.mail.*;
import org.apache.commons.mail.EmailException;


public class Mails {

	public static void welcome(User user) {
		try {
			System.err.println("Trying to send email....");
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setSSL(true);
			email.setAuthenticator(new DefaultAuthenticator("bogdan.ustyak",
					"intensopreterian"));
			email.setSSLOnConnect(true);
			email.setFrom("bogdan.ustyak@gmail.com");
			email.setSubject("TestMail");
			email.setMsg("This is a test mail ... :-)");
			email.addTo("bogdan.ustyak@gmail.com");
			email.send();
			System.err.println("Email was sent!!!");
		} catch (EmailException e) {
			System.err.println("Some errors occured!!!");
			e.printStackTrace();
		}
	}

	public static void lostPassword(User user) {

	}

}