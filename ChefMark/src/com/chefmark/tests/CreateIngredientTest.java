package chefmark.tests;
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

import chefmark.*;

public class CreateIngredientTest{

    private static void createCustomIngredient(Scanner sc, UserController uc, RecipeController RC, DBQuery dbq) throws SQLException
    {
        String backIngredient = "";
        String BACK = "back";
                            
        while(!backIngredient.equals(BACK))
        {
            System.out.println("Create an Ingredient:\n");
            System.out.println("Please enter a Name for the ingredient or type back to return to the menu: ");
            String ingredientName = sc.nextLine();
            if(ingredientName.equals(BACK)){
                backIngredient = ingredientName;
            }
            else{
            System.out.println("Please enter a number quantity of your ingredient: ");
            float quantity = 0;
            boolean again = true;
            while (again) {
                try {
                    quantity = Float.parseFloat(sc.nextLine());
                    again=false;
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a numerical value");
                }
            }
            while(quantity>Float.MAX_VALUE || quantity<Float.MIN_VALUE){
                System.out.println("Your number is either to large or to small");
                System.out.println("Please enter try again: ");
                quantity = Float.parseFloat(sc.nextLine());
            }
            System.out.println(
                "Please enter a measure for the quantity of your ingredients(Cups, grams, ect...): ");
                String measure = sc.nextLine();
            Ingredient temp = new Ingredient();
            temp.setIngredientName(ingredientName);
            temp.setQuantity(quantity);
            temp.setMeasure(measure);
            dbq.create(uc.getUser().getPantry(), temp, uc.getUser());
            uc.getUser().getPantry().addIngredient(temp);
            System.out.println(ingredientName + " has been added to the pantry");
            backIngredient = BACK;
            }
        }
    }

    @Test
    public void createNormalIngredient() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        String input = "corn\n5\nhusks\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();
        uc.createUser("newuser1", "Create1@1", "");

        RecipeController rc = new RecipeController();
        
        CreateIngredientTest.createCustomIngredient(scanner, uc, rc, dbq);

        Ingredient ingredient = uc.getUser().getPantry().search("corn");

        boolean result =false;
        
        ResultSet rs = dbq.read(ingredient, uc.getUser());
        if (rs.next())
            result = true;
        

        
        dbq.delete(ingredient, uc.getUser());

        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void createIngredientWrongInputs() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        String input = "\npeanuts\n\ntwo\n2\n\ncups\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();
        uc.createUser("newuser1", "Create1@1", "");

        RecipeController rc = new RecipeController();
        
        CreateIngredientTest.createCustomIngredient(scanner, uc, rc, dbq);

        Ingredient ingredient = uc.getUser().getPantry().search("peanuts");

        boolean result =false;
        
        ResultSet rs = dbq.read(ingredient, uc.getUser());
        if (rs.next())
            result = true;
    

        
        dbq.delete(ingredient, uc.getUser());

        dbq.disconnect();
        assert(result==true);

    } 
}