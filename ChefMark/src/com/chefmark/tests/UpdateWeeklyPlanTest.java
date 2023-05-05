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
    public void updateTestRenaming() throws SQLException{
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
        dbq.create(testPlan, uc.getUser());

        //Changing plan name
        uc.getUser().getWeeklyPlanByName(testName).setName(newTestName);

        Boolean result = false ;
        if(uc.getUser().getWeeklyPlanByName(newTestName).getName() == newTestName){
            result = true;
        }


        uc.getUser().removeWeeklyPlan(uc.getUser().getWeeklyPlanByName(newTestName));
        dbq.disconnect();

        assert(result == true);
    }

    @Test
    public void updateTestRecipeRemoving() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();

        String testName = "test";
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());


        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        //Logging in to test account
        UserController uc = new UserController();
        uc.login(dbq, scanner);

        //Test plan and recipe
        WeeklyPlan testPlan = new WeeklyPlan(testName);

        //Add new weekly plan to test user
        uc.getUser().addWeeklyPlan(testPlan);

        //Weeekly plan returns true if successfully removed
        Boolean result = uc.getUser().removeWeeklyPlan(testPlan);

        dbq.disconnect();
        assert(result == true);
    }
}