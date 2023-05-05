package chefmark.tests;

import java.util.ArrayList;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import java.util.Scanner;


import chefmark.*;

public class AddRecipeListTest{

    @Test
    public void AddRecipeListReg() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        String testName = "test list";
        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the login method and verify that it returns true
        UserController uc = new UserController();
        uc.login(dbq, scanner);

        RecipeList testList = new RecipeList(testName);

        uc.getUser().addListOfRecipies(testList);

        ArrayList<RecipeList> testList2 =  uc.getUser().getRecipeLists();

        Boolean result = testList2.contains(testList);

        uc.getUser().removeRecipeList(testList);

        dbq.disconnect();
        assert(result == true);
    }
}