package chefmark.tests;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;

import chefmark.*;

public class RateRecipeTest {
    @Test
    public void testRateRecipe() throws SQLException{
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

        //Creating temporary rating and recipe objects
        Recipe testRecipe = new Recipe();
        Rating testRating = new Rating();
        int ratingVal = 5;

        //Changing test rating value
        testRating.changeRating(ratingVal);

        //Booleans for ensuring rating changes effectively
        Boolean firstResult = false;
        Boolean secondResult = false;

        //Checking to ensure recipe rating is not already at 5
        if(testRecipe.getRating() != testRating){
            firstResult = true;
        }

        //Changing recipe rating
        testRecipe.setRating(testRating);
        
        if(testRecipe.getRating() == testRating){
            secondResult = true;
        }
        
        dbq.disconnect();
        assert(firstResult == true && secondResult == true);
    }
    
}
