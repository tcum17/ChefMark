package chefmark.tests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

import chefmark.*;

class CreateWeeklyPlanTest
{
    public static void createWeeklyPlan(Scanner sc, UserController uc, RecipeController RC, DBQuery dbq) throws SQLException
    {   String BACK = "back";
        String backWeeklyPlan = "";
        WeeklyPlan plan = new WeeklyPlan();
        while (!backWeeklyPlan.equals(BACK)) {
            System.out.println("Welcome to create a weekly plan");
            System.out.println("Please name your weekly plan or type back to return to the menu:");
            String weeklyPlanName = sc.nextLine();
            if(weeklyPlanName.equals(BACK)){
                backWeeklyPlan = weeklyPlanName;
            }
            else{
                WeeklyPlan newWeeklyPlan = new WeeklyPlan();
                newWeeklyPlan.setName(weeklyPlanName);
                boolean continueOn = true;
                for (int i = 0; i < uc.getUser().getWeeklyPlans().size(); i++) {
                    if (uc.getUser().getWeeklyPlans().get(i).getName().equals(newWeeklyPlan.getName())) {
                        continueOn=false;
                        System.out.println("You already have a weekly plan of the same name");
                    }
                }
                if (continueOn==true) {
                    uc.getUser().addWeeklyPlan(newWeeklyPlan);
                    System.out.println("Your weekly plan called " + weeklyPlanName + " has been created");
                    dbq.create(newWeeklyPlan, uc.getUser());
                }
                backWeeklyPlan = BACK;
            }
        }
    }

    @Test
    public void createWeeklyPlanNormal() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        String input = "Plan 1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();
        uc.createUser("newuser1", "Create1@1", "");

        RecipeController rc = new RecipeController();
        
        CreateRecipeListTest.createRecipeList(scanner, uc, rc, dbq);

        WeeklyPlan myPlan = uc.getUser().getWeeklyPlanByName("Plan 1");

        boolean result =false;
        ResultSet rs = dbq.read(myPlan, uc.getUser());
        
        
        if (rs.next())
            result = true;
        if (myPlan==null)
            result =false;

        dbq.deleteWeeklyPlan(myPlan, uc.getUser());

        dbq.disconnect();
        assert(result==true);

    }

    @Test
    public void createWeeklyPlanNoNameGiven() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        String input = "\nMy Plan";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();
        uc.createUser("newuser1", "Create1@1", "");

        RecipeController rc = new RecipeController();
        
        CreateRecipeListTest.createRecipeList(scanner, uc, rc, dbq);

        WeeklyPlan myPlan = uc.getUser().getWeeklyPlanByName("Plan 1");

        boolean result =false;
        ResultSet rs = dbq.read(myPlan, uc.getUser());
        
        
        if (rs.next())
            result = true;
        if (myPlan==null)
            result =false;

        dbq.deleteWeeklyPlan(myPlan, uc.getUser());

        dbq.disconnect();
        assert(result==true);
    } 

    @Test
    public void createWeeklyPlanDupName() throws SQLException {
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        dbq.connect();
        String input = "dupPlan\nnotDupPlan\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        
        Scanner scanner = new Scanner(in);
        UserController uc = new UserController();
        uc.createUser("newuser1", "Create1@1", "");

        WeeklyPlan plan1 = new WeeklyPlan("dupPlan");
        uc.getUser().addWeeklyPlan(plan1);

        RecipeController rc = new RecipeController();
        
        CreateRecipeListTest.createRecipeList(scanner, uc, rc, dbq);

        WeeklyPlan myPlan = uc.getUser().getWeeklyPlanByName("notDupPlan");

        boolean result =false;
        ResultSet rs = dbq.read(myPlan, uc.getUser());
        
        
        if (rs.next())
            result = true;
        if (myPlan==null)
            result =false;

        dbq.deleteWeeklyPlan(myPlan, uc.getUser());
        dbq.deleteWeeklyPlan(plan1, uc.getUser());

        dbq.disconnect();
        assert(result==true);
    } 
}