package chefmark.tests;

import chefmark.*;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConvertToShoppingListTest {
    /**
     * 
     * Test uses existing Weekly Plan attached to user and
     * converts the plan into a shopping list.
     * There is only one recipe in this weekly list called
     * grilled chicken.
     */
    @Test
    public void testConvertWeeklyPlanToShoppingList() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc);
        dbq.connect();

        String input = "newuser1\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);

        UserController uc = new UserController();
        uc.login(dbq, scanner);

        WeeklyPlan wp = uc.getUser().getWeeklyPlanByName("plan1");
        ArrayList<String> test = new ArrayList<>();
        test.add("4.0 cups chicken");

        ArrayList<String> result = ShoppingListConverter.convertToShoppingList(wp, uc.getUser().getPantry());
        dbq.disconnect();
        assert(result.get(0).substring(0, 14).equals(test.get(0).substring(0, 14)));

    }
}
