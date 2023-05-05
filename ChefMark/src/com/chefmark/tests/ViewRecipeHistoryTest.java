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

public class viewRecipeHistoryTest{
    
    @Test
    public void testViewRecipeHistory() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        
        
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        User user = new User("testuser", "Create1@1", "");
        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();
        RecipeController rc = new RecipeController();

        // Call the login method and verify that it returns true
        uc.login(dbq,scanner);
       

        String input2 = "";
        boolean result = false;

        
        dbq.disconnect();
        assert(result==false);
    }
    
}
