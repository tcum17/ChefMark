package chefmark.tests;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static org.junit.Assert.*;

import chefmark.*;

public class UpdateIngredientInPantryTest
{

    private static void updateIngredientInPantry(UserController uc, Scanner sc, DBQuery dbq) throws SQLException
    {
        boolean updateAgain = true;
        while(updateAgain)
        {
            if(uc.getUser().getPantry().getIngredientList().size() > 0)
            {
                System.out.println("Enter the name of the ingredient you want to update:\n");
                String ingredientName = sc.nextLine();
                if(uc.getUser().getPantry().hasIngredient(ingredientName))
                {
                    Ingredient temp = uc.getUser().getPantry().search(ingredientName);

                    System.out.println("The Ingredient's current name is \"" + temp.getIngredientName() +"\"\nEnter a new name or \"Next\" to keep the old one");
                    String oldName = temp.getIngredientName();
                    String input = sc.nextLine();
                    if(input.equalsIgnoreCase("Next"))
                    {
                        System.out.println("Keeping the original name");
                    }
                    else{
                        temp.setIngredientName(input);
                    }
                    System.out.println("The Ingredient's current quantity is \"" + temp.getQuantity() +"\"\nEnter a new number(just the number) or \"Next\" to keep the old one");
                    input = "";
                    input = sc.nextLine();
                    if(input.equalsIgnoreCase("Next"))
                    {
                        updateAgain = false;
                        continue;
                    }
                    else{
                        boolean hasFloat = false;
                        float quantity = -1;
                        while(!hasFloat)
                        {
                            try
                            {
                                quantity = Float.parseFloat(input);
                                hasFloat = true;
                                temp.setQuantity(quantity);
                            }
                            catch(NumberFormatException e)
                            {
                                System.out.println("This input is invalid please try again");
                                input = sc.nextLine();
                            }
                            
                            
                        }
                    }
                    System.out.println("The Ingredient's current units are \"" + temp.getMeasure() + "\"\nEnter a new unit or \"Next\" to keep the old one");
                    input = "";
                    input = sc.nextLine();
                    if(input.equalsIgnoreCase("Next"))
                    {
                        continue;
                    }
                    else{
                        temp.setMeasure(input);
                    }

                    System.out.println("The ingredient has been updated to be:\n" + temp.getQuantity() + " " + temp.getMeasure() + " " + temp.getIngredientName());
                    String newName = temp.getIngredientName();
                    temp.setIngredientName(oldName); // weird technical thing with database, no way to store ID from query unless specifically read somewhere...
                    ResultSet rs = dbq.read(temp, uc.getUser());
                    if (rs.next()) {
                        
                        int ingredientID = rs.getInt(2);
                        temp.setIngredientID("" + ingredientID);
                    }
                    temp.setIngredientName(newName); // set back to new name immediately after getting the ID that matches the old ingredient name
                    dbq.update(temp, uc.getUser());
                    System.out.println("\nWould you like to update another ingredient?\n1 - Yes\n2 - No");
                    String updateAgainInput = sc.nextLine();
                    if(!updateAgainInput.equalsIgnoreCase("1"))
                    {
                        updateAgain = false;
                    }
                    
                }
            }
            else{
                System.out.println("You have no ingredients in your pantry");
                updateAgain = false;
            }
        }
    }




     @Test
    public void updateIngredientInPantryChangeNothingTest() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String testName = "test";
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());


        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        
        //Logging in to test user
        UserController uc = new UserController();
        

        //Temp weeklyplan and recipe to be added to user
        Ingredient testIngredient = new Ingredient();
        testIngredient.setIngredientName("Carrots");
        testIngredient.setMeasure("OZ");
        testIngredient.setQuantity(4);
        
        uc.getUser().getPantry().addIngredient(testIngredient);
        
        
        
        boolean result = false;
        input = "Carrots\nNext\nNext\n";
        in = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(in);

        updateIngredientInPantry(uc, scanner, dbq);

        Ingredient newIngred = uc.getUser().getPantry().search("Carrots");
        
        ResultSet rs = dbq.read(uc.getUser().getPantry(), uc.getUser());
        String name = rs.getString(1);
        int quantity = rs.getInt(2);
        String measure = rs.getString(3);
        if(newIngred.getIngredientName().equals("Carrots") && newIngred.getMeasure().equals("OZ") && newIngred.getQuantity() == 4)
        {
            if(name.equals("Carrots") && quantity == 4 && measure.equals("OZ"))
            {
                result = true;
            }
        }
        
        
        assert(result == true);
        uc.getUser().getPantry().removeIngredient(testIngredient.getIngredientName());
    }

    @Test
    public void updateIngredientInPantryChangeEverything() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String testName = "test";
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());


        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        
        //Logging in to test user
        UserController uc = new UserController();
        

        //Temp weeklyplan and recipe to be added to user
        Ingredient testIngredient = new Ingredient();
        testIngredient.setIngredientName("Carrots");
        testIngredient.setMeasure("OZ");
        testIngredient.setQuantity(4);
        
        uc.getUser().getPantry().addIngredient(testIngredient);
        
        
        
        boolean result = false;
        input = "Carrots\nCool Carrots\n2\nLBS\n2\n";
        in = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(in);

        Ingredient newIngred = uc.getUser().getPantry().search("Cool Carrots");

        updateIngredientInPantry(uc, scanner, dbq);
        
        
        ResultSet rs = dbq.read(uc.getUser().getPantry(), uc.getUser());
        String name = rs.getString(1);
        int quantity = rs.getInt(2);
        String measure = rs.getString(3);
        if(newIngred.getIngredientName().equals("Cool Carrots") && newIngred.getMeasure().equals("LBS") && newIngred.getQuantity() == 2)
        {
            if(name.equals("Cool Carrots") && quantity == 2 && measure.equals("LBS"))
            {
                result = true;
            }
        }
        assert(result == true);
        uc.getUser().getPantry().removeIngredient(testIngredient.getIngredientName());
    }

    @Test
    public void updateIngredientInPantryThatDoesntExist() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String testName = "test";
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());


        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        
        //Logging in to test user
        UserController uc = new UserController();
        

        //Temp weeklyplan and recipe to be added to user
        Ingredient testIngredient = new Ingredient();
        testIngredient.setIngredientName("Carrots");
        testIngredient.setMeasure("OZ");
        testIngredient.setQuantity(4);
        
        uc.getUser().getPantry().addIngredient(testIngredient);
        
        
        
        boolean result = false;
        input = "Car\nCarrots\nCool Carrots\n2\nLBS\n2\n";
        in = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(in);

        Ingredient newIngred = uc.getUser().getPantry().search("Cool Carrots");

        updateIngredientInPantry(uc, scanner, dbq);
        
        
       ResultSet rs = dbq.read(uc.getUser().getPantry(), uc.getUser());
        String name = rs.getString(1);
        int quantity = rs.getInt(2);
        String measure = rs.getString(3);
        if(newIngred.getIngredientName().equals("Cool Carrots") && newIngred.getMeasure().equals("LBS") && newIngred.getQuantity() == 2)
        {
            if(name.equals("Cool Carrots") && quantity == 2 && measure.equals("LBS"))
            {
                result = true;
            }
        }
        assert(result == true);
        uc.getUser().getPantry().removeIngredient(testIngredient.getIngredientName());
    }


    @Test
    public void updateIngredientInPantryWrongQuantityInput() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String testName = "test";
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());


        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        
        //Logging in to test user
        UserController uc = new UserController();
        

        //Temp weeklyplan and recipe to be added to user
        Ingredient testIngredient = new Ingredient();
        testIngredient.setIngredientName("Carrots");
        testIngredient.setMeasure("OZ");
        testIngredient.setQuantity(4);
        
        uc.getUser().getPantry().addIngredient(testIngredient);
        
        
        
        boolean result = false;
        input = "Carrots\nCool Carrots\nas\n2\nLBS\n2\n";
        in = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(in);

        Ingredient newIngred = uc.getUser().getPantry().search("Cool Carrots");

        updateIngredientInPantry(uc, scanner, dbq);
        
        
      ResultSet rs = dbq.read(uc.getUser().getPantry(), uc.getUser());
        String name = rs.getString(1);
        int quantity = rs.getInt(2);
        String measure = rs.getString(3);
        if(newIngred.getIngredientName().equals("Cool Carrots") && newIngred.getMeasure().equals("LBS") && newIngred.getQuantity() == 2)
        {
            if(name.equals("Cool Carrots") && quantity == 2 && measure.equals("LBS"))
            {
                result = true;
            }
        }
        assert(result == true);
        uc.getUser().getPantry().removeIngredient(testIngredient.getIngredientName());
    }



}