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
    public void updateTestRecipeAdding() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String testName = "test";
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());


        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        

        UserController uc = new UserController();
        uc.login(dbq, scanner);
        WeeklyPlan testPlan = new WeeklyPlan(testName);
        Recipe testRecipe = new Recipe();

        uc.getUser().addWeeklyPlan(testPlan);

        Boolean result = uc.getUser().getWeeklyPlanByName(testName).addRecipeToWeeklyPlan(testRecipe, "Monday");

        uc.getUser().removeWeeklyPlan(testPlan);
        assert(result == true);

    }

    @Test
    public void updateTestRecipeRemoval() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String testName = "test";
        String newTestName = "newName";
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        //Logging in
        UserController uc = new UserController();
        uc.login(dbq, scanner);

        //Creating a weekly plan for testing
        WeeklyPlan testPlan = new WeeklyPlan(testName);


        //Adding plan to be tested
        uc.getUser().addWeeklyPlan(testPlan);

        //Changing plan name
        uc.getUser().getWeeklyPlanByName(testName).setName(newTestName);

        Boolean result = false ;
        if(uc.getUser().getWeeklyPlanByName(newTestName).getName() == newTestName){
            result = true;
        }

         uc.getUser().removeWeeklyPlan(uc.getUser().getWeeklyPlanByName(newTestName));
        
        assert(result == true);
    }

    @Test
    public void updateTestRecipeRenaming() throws SQLException{
            
    }
}