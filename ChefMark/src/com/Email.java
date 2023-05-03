//package chefmark;
import java.util.Properties;
import java.util.ArrayList;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email extends CommunicationsProvider{
    static Properties properties;
    static String from = "ChefMarkIT326@outlook.com";
    static String pass = "IT326Chef!";

    // Setup mail server
    public Email(){
        properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
	    properties.put("mail.smtp.port", "587");
	    properties.put("mail.smtp.starttls.enable","true");
	    properties.put("mail.smtp.auth", "true"); 
    }
    

    public void shareRecipe(Recipe recipeIn, String recipient) {
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        try {
            // Create a message
            MimeMessage message = new MimeMessage(session);

            // Set from field
            message.setFrom(new InternetAddress(from));

            // Set recipient
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set subject line
            message.setSubject("ChefMark: " + recipeIn.getName() + "recipe!");

            //Prepare message contents
            String messageContent = "Recipe : " + recipeIn.getName() + "\n";

            if(recipeIn.getRating().getRating() != 0){
                messageContent += "\tRating : " + recipeIn.getRating() + "/5\n" ;
                
            }

            messageContent += "Ingredients : \n";
            for(int  i = 0; i < recipeIn.getIngredients().size() ; i++){
                messageContent += "\t" + recipeIn.ingredients.get(i).getQuantity() + recipeIn.ingredients.get(i).getMeasure() + "(s) of " + recipeIn.ingredients.get(i).getIngredientName() + " \n";;
            }
            
            ///Add instructions here
            messageContent += recipeIn.getURL();

            messageContent += "Nutritional Facts : \n";
            messageContent += "\tYield : " + recipeIn.getNutritionalFacts().getYeild();
            messageContent += "\tCalories : " + recipeIn.getNutritionalFacts().getCalories();
            messageContent += "\tDietary info : " ;

            ArrayList<String> dietaryLabels = recipeIn.getNutritionalFacts().getDietLables(); 

            for(int i = 0; i < dietaryLabels.size(); i++){
                messageContent += dietaryLabels.get(i);
            }
            
            // Set message contents
            message.setText(messageContent);

            // Send message
            Transport.send(message);
            System.out.println("Recipe shared successfully!");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
    /*
    * Shares user's weekly plan to either theirs, or anothers email. 
    */
    public void shareWeeklyPlan(WeeklyPlan planIn, String recipient) {
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: header field
            message.setSubject("Your ChefMark Weekly Plan is ready!");

            // Now set the actual message
            message.setText(planIn.info());

            // Send message
            Transport.send(message);
            System.out.println("Weekly plan shared successfully!");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
}

