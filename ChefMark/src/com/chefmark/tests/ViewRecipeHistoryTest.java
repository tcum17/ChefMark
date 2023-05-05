package chefmark.tests;

import java.util.ArrayList;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;


import chefmark.*;

public class ViewRecipeHistoryTest{
    
    @Test
    public void testViewRecipeHistoryOneRecipe() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        
        
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();

        // Call the login method and verify that it returns true
        uc.login(dbq,scanner);
        
        ArrayList<Recipe> testList = uc.getUser().getRecipeHistory();
        Recipe testRec = new Recipe();
        testRec.setName("Chicken Noodle");
        testList.add(testRec);
       
        boolean result = testList.isEmpty();

        dbq.disconnect();
        assert(result==false);
    }
    
}
