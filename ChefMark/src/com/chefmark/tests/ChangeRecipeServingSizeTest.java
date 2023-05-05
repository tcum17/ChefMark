package chefmark.tests;

import chefmark.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChangeRecipeServingSizeTest {
    
    private static void changeRecipeServingSize(Recipe recipe, Scanner sc, UserController uc, DBQuery dbq) throws SQLException{
        boolean getInput = true;
        while(getInput){
            System.out.println("Enter the factor you want to multiply the serving size by:");
            String input = sc.nextLine();
            try {
                double factor = Double.parseDouble(input);
                recipe.changeServingSize(factor);
                recipe.setCustom(1);
                dbq.updateServingSize(recipe, uc.getUser());
                dbq.updateIsCustomRecipe(recipe, uc.getUser());
                System.out.println("Note that the recipe instructions will not contain updated ingredient amounts");
                getInput = false;
            } catch (NumberFormatException e) {
                 System.out.println("Your input was not a valid factor, please try again");
            }
        }
    }





    @Test
    public void ChangeRecipeServingSizeTest() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc);
        dbq.connect();

         String input = "newuser1\nCreate1@1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());

        Scanner scanner = new Scanner(in);

        UserController uc = new UserController();
        uc.login(dbq, scanner);

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
        dbq.createCustomRecipe(testRec, uc.getUser());
        if(uc.getUser().getCustomRecipeList().size() == 0)
        {
            uc.getUser().addCustomRecipe(testRec);
        }
        else
        {
            uc.getUser().getCustomRecipeList().add(0,testRec);
        }


        boolean result = false;

        String input2 = "";

        changeRecipeServingSize(testRec, scanner, uc, dbq);
        

        
        dbq.disconnect();
        assert(result = true);

    }
}