package chefmark.tests;
import org.junit.Test;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

import chefmark.*;


public class CreateRecipeListTest 
{
    public static void createRecipeList(Scanner sc, UserController uc, RecipeController RC, DBQuery dbq) throws SQLException
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
            }
            else{
                newRecipeList.setName(recipeListName);
                uc.getUser().addListOfRecipies(newRecipeList);
                System.out.println("Your recipe list called " + recipeListName + " has been created");
                //db write
                dbq.create(newRecipeList, uc.getUser());
                backRecipeList = BACK;
                newRecipeList.setName(recipeListName);
            }
        }
    }

    @Test
    public void createRecipeListNormal() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        String input = "recipe list 1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();
        uc.createUser("newuser1", "Create1@1", "");

        RecipeController rc = new RecipeController();
        
        CreateRecipeListTest.createRecipeList(scanner, uc, rc, dbq);

        RecipeList myList = uc.getUser().getRecipeListByName("recipe list 1");

        boolean result =false;
        ResultSet rs = dbq.read(myList, uc.getUser());
        if (rs.next())
            result = true;
        if (myList==null)
            result =false;

        dbq.delete(myList, uc.getUser());

        dbq.disconnect();
        assert(result==true);
    }

    @Test
    public void createRecipeListNoNameGiven() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        String input = "\nrecipe list 1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();
        uc.createUser("newuser1", "Create1@1", "");

        RecipeController rc = new RecipeController();
        
        CreateRecipeListTest.createRecipeList(scanner, uc, rc, dbq);

        RecipeList myList = uc.getUser().getRecipeListByName("recipe list 1");

        boolean result =false;
        ResultSet rs = dbq.read(myList, uc.getUser());
        if (rs.next())
            result = true;
        if (myList==null)
            result =false;

        dbq.delete(myList, uc.getUser());

        dbq.disconnect();
        assert(result==true);

    } 
}