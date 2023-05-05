package chefmark.tests;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

import chefmark.*;

public class DeleteCustomRecipesTest{


    // Exact copy of App.java, we did not want to make the method publicly available
    private static void deleteCustomRecipe(Scanner sc, UserController uc, DBQuery dbq) throws SQLException {
        System.out.println("Type the number of the recipe you want to delete");
        String input = sc.nextLine();
        int recipeNum = 0;
        
        ArrayList<Recipe> recipes = uc.getUser().getCustomRecipeList();
        int counter = recipes.size();
        try {
            recipeNum= Integer.parseInt(input);
        } catch (NumberFormatException e) {
            //
        }
        if(recipeNum>counter || recipeNum == 0)
        {
            System.out.println("There is not a recipe number" + recipeNum);
        }
        else
        {
            dbq.delete(recipes.get(recipeNum-1), uc.getUser());
            recipes.remove(recipeNum-1);
        }
    }
    
    @Test
    public void deletionCustomeRecipeTest() throws SQLException{
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        // Create a fake input stream with username and password


        // Call the login method and verify that it returns true
        UserController uc = new UserController();
      

        //User user = new User("testuser", "Create1@1", "");
        uc.createUser("newuser2", "Create1@1", "");
        Recipe testRec = new Recipe();
        if(uc.getUser().getCustomRecipeList().size() == 0)
        {

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
            dbq.createCustomRecipe(testRec, uc.getUser());
        }
        int index = 0;
        for (int i = 0; i < uc.getUser().getCustomRecipeList().size(); i++) {
            if (uc.getUser().getCustomRecipeList().get(i).getName().equals(testRec)) {
                index = i;
                i=uc.getUser().getCustomRecipeList().size();
            }
        }
        index++;
        String input = index + "\n";
        
        InputStream in = new ByteArrayInputStream(input.getBytes());

        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);

        deleteCustomRecipe(scanner, uc, dbq);


        boolean result = false;
        ResultSet rs = dbq.read(testRec, uc.getUser());
        if (!rs.next())
            result = true;
        if (uc.getUser().getCustomRecipeList().contains(testRec))
            result =false;
        assert (result==true);
    }

}