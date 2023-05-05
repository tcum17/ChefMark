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

public class RemoveIngredientFromPantryTest{

    @Test
    public void testRemoval() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the login method and verify that it returns true
        UserController uc = new UserController();
        uc.login(dbq, scanner);

        //Adding test ingredient
        Ingredient testIngredient = new Ingredient("Cookies", "0001", 1, "whole cookies");
        uc.getUser().getPantry().addIngredient(testIngredient);

        //Removing ingredient
        uc.getUser().getPantry().removeIngredient(testIngredient.getIngredientName());

        //Testing to see if ingredient is present
        boolean result = uc.getUser().getPantry().hasIngredient(testIngredient.getIngredientName());


        dbq.disconnect();
        assert(result == false);
    }

    @Test
    public void testRemovalNotPresent() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the login method and verify that it returns true
        UserController uc = new UserController();
        uc.login(dbq, scanner);

        //Creating test ingredient, but not adding it
        Ingredient testIngredient = new Ingredient("Cookies", "0001", 1, "whole cookies");

        //Removing ingredient
        Boolean result = uc.getUser().getPantry().removeIngredient(testIngredient.getIngredientName());


        dbq.disconnect();
        assert(result == false);
    }
}