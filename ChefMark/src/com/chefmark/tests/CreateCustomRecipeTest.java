package chefmark.tests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;

import chefmark.*;

public class CreateCustomRecipeTest {
    @Test
    public void createCustomRecipeNormal() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        //String input = "testuser\nCreate1@1\n";
        //InputStream in = new ByteArrayInputStream(input.getBytes());
        String input = "water\n5\ncups\nchicken\n2\npounds\ndone\nmix the chicken and water\nheat in the microwave\ndone\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();
        uc.createUser("newuser1", "Create1@1", "");

        RecipeController rc = new RecipeController();
        String recipeName = "chicken noodle soup";
        Recipe recipe = rc.createRecipe(recipeName, scanner, uc, dbq);

        boolean result = false;
        if(recipe!=null)
        {
            result = true;
        }
        dbq.delete(recipe, uc.getUser());

        dbq.disconnect();
        assert(result==true);


    }

    @Test
    public void createCustomRecipeWrongInputs() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        //String input = "testuser\nCreate1@1\n";
        //InputStream in = new ByteArrayInputStream(input.getBytes());
        String input = "\nchicken\n\nfive\n5\n\ncups\ndone\n\ncook chicken\ndone\nfive\n5\n1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();
        uc.createUser("newuser1", "Create1@1", "");

        RecipeController rc = new RecipeController();
        String recipeName = "chicken noodle soup";
        Recipe recipe = rc.createRecipe(recipeName, scanner, uc, dbq);

        boolean result = false;
        if(recipe!=null)
        {
            result = true;
        }

        dbq.disconnect();
        dbq.delete(recipe, uc.getUser());
        assert(result==true);


    }
}