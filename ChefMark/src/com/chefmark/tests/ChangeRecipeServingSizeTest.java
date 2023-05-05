package chefmark.tests;

import chefmark.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.ResultSet;

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
    public void ChangeRecipeServingSizeTestRegular() throws SQLException {
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
        dbq.create(testRec, uc.getUser());
        boolean result = false;
        String input2 = "4\n";
        in = new ByteArrayInputStream(input2.getBytes());
        scanner = new Scanner(in);
        changeRecipeServingSize(testRec, scanner, uc, dbq);
        ResultSet rs = dbq.read(testRec, uc.getUser());
        String name = rs.getString(2);
        String dbIngred = rs.getString(5);
        float quant1 = Float.parseFloat(dbIngred.substring(0, dbIngred.indexOf(" ")));
        dbIngred = dbIngred.substring(dbIngred.indexOf("|"));
        float quant2 = Float.parseFloat(dbIngred.substring(0, dbIngred.indexOf(" ")));
        if(testRec.getName().equals("Chicken Soup") && testRec.getIngredients().get(0).getQuantity() == 16 && testRec.getIngredients().get(1).getQuantity() == 2000)
        {
            if(name.equals("Chicken Soup") && quant1 == 16 &&  quant2 == 2000)
            {
                result = true;
            }
        }


        dbq.delete(testRec, uc.getUser());
        dbq.disconnect();
        assert(result = true);

    }
}