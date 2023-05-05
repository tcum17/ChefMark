package chefmark.tests;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static org.junit.Assert.*;
import java.util.ArrayList;

import chefmark.*;

public class DeleteCustomRecipesTest{
    @Test
    public void deletionTest() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password
        String input = "testuser\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        // Call the login method and verify that it returns true
        UserController uc = new UserController();
        RecipeController rc = new RecipeController();

        Recipe testRec = new Recipe();
        testRec.setName("Chicken Soup");
        ArrayList<Ingredient> ingreds = new ArrayList<>();
        Ingredient chicken = new Ingredient();
        chicken.setIngredientName("Chicken");
        chicken.setMeasure("oz");
        chicken.setQuantity(4);
        ingreds.add(chicken);
        Ingredient broth = new Ingredient();
        broth.setIngredientName("Broth");
        broth.setMeasure("ml");
        broth.setQuantity(500);
        ingreds.add(broth);
        testRec.setIngredientList(ingreds);
        ArrayList<String> ins = new ArrayList<String>();
        ins.add("Put the chicken in the broth");
        ins.add("Enjoy");
        Instructions instruct = new Instructions();
        instruct.setInstructions(ins);
        testRec.setInstructions(instruct);
        uc.getUser().getCustomRecipeList().add(testRec);
        ArrayList<Recipe> temp = uc.getUser().getCustomRecipeList();

        
    }
}