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

public class UpdateWeeklyPlanTest{

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
        uc.login(dbq, scanner);

        //Temp weeklyplan and recipe to be added to user
        Ingredient testIngredient = new Ingredient();
        testIngredient.setIngredientName("Carrots");
        testIngredient.setMeasure("OZ");
        testIngredient.setQuantity(4);
        
        uc.getUser().getPantry().addIngredient(testIngredient);
        
        
        
        boolean result = false;

        Ingredient newIngred = uc.getUser().getPantry().search("Carrots");
        newIngred.setIngredientName("Cool Carrots");
        newIngred.setMeasure("LBS");
        newIngred.setMeasure(2);
        
        if(newIngred.getIngredientName().equals("Cool Carrots") && newIngred.getMeasure().equals("LBS") && newIngred.getQuantity() == 2)
        {
            result = true;
        }
        assert(result == true);
        uc.getUser().getPantry().removeIngredient(testIngredient.getIngredientName());
    }

    @Test
    public void updateIngredientInPantryThatdoesntExist() throws SQLException{
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
        uc.login(dbq, scanner);

        //Temp weeklyplan and recipe to be added to user
        Ingredient testIngredient = new Ingredient();
        testIngredient.setIngredientName("Carrots");
        testIngredient.setMeasure("OZ");
        testIngredient.setQuantity(4);
        
        uc.getUser().getPantry().addIngredient(testIngredient);
        
        
        
        boolean result = false;

        Ingredient newIngred = uc.getUser().getPantry().search("Epic Carrots");
        
        if(newIngred == null)
        {
            result = true;
        }
        assert(result == true);
        uc.getUser().getPantry().removeIngredient(testIngredient.getIngredientName());
    }
}