package chefmark.tests;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;

import chefmark.*;

public class AddRecipeToRecipeList {

    /**
     * 
     * Test uses a currently existing user and recipe list attached
     * to the user called list1, and it uses a custom recipe already
     * created by the user. It uses this recipe to add it to the recipe list
     */
    @Test
    public void testAddRecipeToRecipeList() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc);
        dbq.connect();

        String input = "newuser1\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);

        UserController uc = new UserController();
        uc.login(dbq, scanner);

        RecipeList rl  = uc.getUser().getRecipeListByName("list1");
        Recipe recipe1 = uc.getUser().getCustomRecipeList().get(0);

        rl.addRecipeToRecipeList(recipe1);
        dbq.create(rl, recipe1, uc.getUser());
        dbq.disconnect();
        assert(uc.getUser().getRecipeListByName("list1").getRecipeList().get(0).getName().equals(recipe1.getName()));
    }

}
