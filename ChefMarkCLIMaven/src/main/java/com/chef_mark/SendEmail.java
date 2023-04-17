package chefmark;


import java.util.*;
//import javax.mail.*;
//import javax.mail.internet.*;


public class SendEmail{
    public void sendMail(String recipientEmail){

        String recipientEmail = null;
        String senderEmail = "ChefMarkIT326@gmail.com";
        String password = "IT326Chef!";
        String host = "smtp.gmail.com";
        String port = "587";

        Properties props = new Properties();

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //Session session = Session.getInstance(props, new Authenticator() {protected PasswordAuthentication(senderEmail, password);
        //};
        
        try{
            Message mesage = new MimeMessage(session);
            message.setFrom(new internetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Test email");
            message.setText("ChefMark test email");


            Transport.send(message);
        }

        catch(MessagingException e){
            System.out.println("Email sending faield");
        }
        
        
        
        
        
        }




    }



