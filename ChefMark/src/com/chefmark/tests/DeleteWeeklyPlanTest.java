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

public class DeleteWeeklyPlanTest {

    /**
     * 
     * This tests the deletion of a weekly plan from the user.
     */
    @Test
    public void testWeeklyPlanDeletion() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc);
        dbq.connect();

        String input = "newuser1\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);

        UserController uc = new UserController();
        uc.login(dbq, scanner);

        WeeklyPlan wp = new WeeklyPlan("plan2");

        uc.getUser().addWeeklyPlan(wp);

        boolean result = uc.getUser().removeWeeklyPlan(wp);
        if (result)
            dbq.deleteWeeklyPlan(wp, uc.getUser());

        ResultSet rs = dbq.read(wp, uc.getUser());

        assert (rs.next() == false);
    }

    /**
     * 
     * This tests the attempt to delete a weekly plan that doesn't exist
     */
    @Test
    public void testWeeklyPlanDeletionNoPlans() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc);
        dbq.connect();

        String input = "newuser1\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);

        UserController uc = new UserController();
        uc.login(dbq, scanner);
        WeeklyPlan test = new WeeklyPlan("testplan");
        boolean result = uc.getUser().removeWeeklyPlan(test);
        assert (result == false);
    }

}
