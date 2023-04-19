package chefmark;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    public static void sendRecipe(Recipe recipeIn, String recipient) {
		
        // Recipient's email ID needs to be mentioned.
        String to = "maxwilson568@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "ChefMarkIT326@outlook.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp-mail.outlook.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.auth", "true"); 

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("ChefMarkIT326@outlook.com", "IT326Chef!");

            }

        });

        // Used to debug SMTP issues
        //session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("ChefMark: " + recipeIn.getName() + "recipe!");

            // Now set the actual message
            message.setText("Recipe goes here i guess");

            // Send message
            Transport.send(message);
            System.out.println("Recipe shared successfully!");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

	}
}
