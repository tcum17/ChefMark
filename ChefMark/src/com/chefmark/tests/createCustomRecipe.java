package chefmark.tests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;

import chefmark.*;

public class CreateCustomRecipe{

    // @Test
    // public void testLoginNewAccountNoInformation() throws SQLException {
    //     DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
    //     DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
    //     dbq.connect();
    //     // Create a fake input stream with username and password
    //     String input = "newuser4\nCreate1@1\n";
    //     InputStream in = new ByteArrayInputStream(input.getBytes());

    //     // Create a scanner to read from the fake input stream
    //     Scanner scanner = new Scanner(in);

    //     // Call the login method and verify that it returns true
    //     UserController uc = new UserController();
    //     boolean result = uc.login(dbq, scanner);
    //     User user = uc.getUser();
    //     if (user.getCustomRecipeList().equals(null) && user.getRecipeHistory().equals(null) && user.getWeeklyPlans().equals(null) && user.getRecipeLists().equals(null))
    //      //     dbq.disconnect();
    //     assert(result==true);
    // }

    @Test
    public void createCustomRecipeNormal() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        //String input = "testuser\nCreate1@1\n";
        //InputStream in = new ByteArrayInputStream(input.getBytes());

        User user = new User("testuser", "Create1@1", "");
        String input = "water\n5\ncups\nchicken\n2\npounds\ndone\nmix the chicken and water\nheat in the microwave\ndone\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();

        RecipeController rc = new RecipeController();
        String recipeName = "chicken noodle soup";
        Recipe recipe = rc.createRecipe(recipeName, scanner, uc, dbq);

        boolean result = false;
        if(recipe!=null)
        {
            result = true;
        }

        dbq.disconnect();
        assert(result==false);
    }
}