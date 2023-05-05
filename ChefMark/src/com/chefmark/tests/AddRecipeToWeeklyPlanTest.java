package chefmark.tests;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;

import chefmark.*;

public class AddRecipeToWeeklyPlanTest {

    /**
     * 
     * Test uses a currently existing user and weekly plan attached
     * to the user called plan1, and it uses a custom recipe already
     * created by the user. It uses this recipe to add it to the weekly
     * plan.
     */
    @Test
    public void testAddRecipeToWeeklyPlan() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc);
        dbq.connect();

        String input = "newuser1\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);

        UserController uc = new UserController();
        uc.login(dbq, scanner);

        WeeklyPlan wp = uc.getUser().getWeeklyPlanByName("plan1");
        Recipe recipe1 = uc.getUser().getCustomRecipeList().get(0);

        wp.addRecipeToWeeklyPlan(recipe1, "Monday");
        dbq.create(wp, recipe1, uc.getUser(), "Monday");
        dbq.disconnect();
        assert (uc.getUser().getWeeklyPlanByName("plan1").getRecipeByName("grilled chicken").getName()
                .equals("grilled chicken"));
    }

    /**
     * 
     * Test uses a currently existing user and weekly plan attached
     * to the user called plan1, and it uses a custom recipe already
     * created by the user. It uses an invalid day to add the recipe
     * too. This will result an a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void testAddToWeeklyPlanOnInvalidDay() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc);
        dbq.connect();

        String input = "newuser1\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);

        UserController uc = new UserController();
        uc.login(dbq, scanner);

        WeeklyPlan wp = uc.getUser().getWeeklyPlanByName("plan1");
        Recipe recipe1 = new Recipe();
        recipe1.setName("test invalid");

        wp.addRecipeToWeeklyPlan(recipe1, "Thorsday");
        dbq.disconnect();
        assert (uc.getUser().getWeeklyPlanByName("plan1").getRecipeByName("test invalid").getName()
                .equals("test invalid"));

    }
}
