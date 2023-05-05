package chefmark.tests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;

import chefmark.*;

public class CreateRecipeListTest 
{
    

    public static RecipeList createRecipeList(Scanner sc, UserController uc, RecipeController RC, DBQuery dbq) throws SQLException
    {
        String BACK="back";
        String backRecipeList = "";
        RecipeList newRecipeList = new RecipeList();
        while (!backRecipeList.equals(BACK)) {
            System.out.println("Welcome to create a recipe list");
            System.out.println("Please name your recipe list or type back to return to the menu:");
            String recipeListName = sc.nextLine();
            if(recipeListName.equals(BACK)){
                backRecipeList = recipeListName;
                return newRecipeList;
            }
            else{
                newRecipeList.setName(recipeListName);
                uc.getUser().addListOfRecipies(newRecipeList);
                System.out.println("Your recipe list called " + recipeListName + " has been created");
                //db write
                dbq.create(newRecipeList, uc.getUser());
                backRecipeList = BACK;
                return newRecipeList;
            }
        }
        return newRecipeList;
    }
}

class tester
{
//TODO
    @Test
    public void createRecipeListNormal() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        
        String input = "water\n5\ncups\nchicken\n2\npounds\ndone\nmix the chicken and water\nheat in the microwave\ndone\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        // Create a scanner to read from the fake input stream
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();
        uc.createUser("newuser1", "Create1@1", "");

        RecipeController rc = new RecipeController();
        String recipeName = "chicken noodle soup";
        RecipeList recipeList = CreateRecipeListTest.createRecipeList(scanner, uc, rc, dbq);

        boolean result = false;
        // if(recipe!=null)
        // {
        //     result = true;
        // }
        // dbq.delete(recipe, uc.getUser());

        dbq.disconnect();
        assert(result==true);


    }

    
}