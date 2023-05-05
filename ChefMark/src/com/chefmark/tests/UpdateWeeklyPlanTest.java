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
    public void updateTestReg() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        WeeklyPlan testPlan = new WeeklyPlan("test plan");
        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
    }
}