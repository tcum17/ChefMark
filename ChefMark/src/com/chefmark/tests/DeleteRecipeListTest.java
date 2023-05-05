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

public class DeleteRecipeListTest{

    @Test
    public void testRegDeletion() throws SQLException{
        String listName = "TestList";
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the login method
        UserController uc = new UserController();
        uc.login(dbq, scanner);

        //Create temp testing list
        RecipeList testList = new RecipeList(listName);

        //Add recipe to user list
        uc.getUser().addListOfRecipies(testList);

        //Attempt deletion of recipe list
        Boolean testResult = uc.getUser().removeRecipeList(testList);
        assert(testResult == true);
    }

    @Test
    public void testDeletionNoList() throws SQLException{
        String listName = "TestList";
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the login method
        UserController uc = new UserController();
        uc.login(dbq, scanner);

        //Create new test list to search for
        RecipeList testList = new RecipeList(listName);
        Boolean testResult = uc.getUser().removeRecipeList(testList);
        assert(testResult == false);
    }

}