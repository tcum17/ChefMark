
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class App {
    //Reusable output strings
    private static final String RETRY = "Your answer did not match any of our options. Please reenter";
    private static final String WELCOME = "==== Welcome to ChefMark! ====\nEnter your choice:\nlogin\nsignup\ndelete\nquit\n";
    private static final String GOODBYE = "Thanks for using ChefMark! We'll see you soon!";
    private static final String LOGIN = "login";
    private static final String SIGNUP = "signup";
    private static final String DELETE = "delete";
    private static final String QUIT = "quit";
    private static final String SELECTION = "\n==== Enter your selection ====\n1 - Search\n2 - Create\n3 - Delete\n4 - View\n5 - quit\n";
    private static final String SEARCH_PROMPT = "=== SEARCH===\n1 - By Keyword\n2 - By Ingredient\n3 - By Pantry\n4 - By Calories\n5 - Random\n6 - Back";
    private static final String SEARCH_AGAIN = "Do you want to search for another recipe?\n1 - Yes\n2 - No";
    private static final String INGREDIENT_SEARCH_PROMPT = "Please enter the ingredients you would like to use in a recipe:\nEnter \"Remove\" to remove the previous ingredient\nEnter \"Stop\" to stop";
    private static final String CREATE_PROMPT = "1 - Recipe\n2 - Ingredient\n3 - WeeklyPlan\n4 - RecipeList\n5 - Back";
    private static final String VIEW_PROMPT = "\n==== Enter your selection ====\n1 - Recipe List\n2 - Pantry\n3 - Weekly Plan\n4 - History\n5 - Custom Recipes\n6 - Back";
    private static final String VIEW_RECIPE_OPTIONS = "What do you want to do with this recipe:\n1 - Add to weekly plan"+
    "\n2 - Add to Recipe List\n3 - Add to favorite recipes\n4 - Share\n5 - Change recipe serving sizes\n6 - Back";
    private static final String ONE = "1", TWO = "2", THREE = "3", FOUR = "4", FIVE = "5", SIX = "6";
    private static final String INVALID_SELECT = "Please enter a valid choice\n";
    private static final String STOP = "Stop", REMOVE = "Remove", BACK = "back";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        DBConnection dbc = DBConnFactory.getDBConnection(DBConnFactory.DBConnType.MYSQL);
        DBQuery dbq = new MySQLQuery(dbc); // MySQL DBQuery implementation using MySQL Database
        
        UserController uc = new UserController();
        RecipeController RC = new RecipeController();

        System.out.println("Type \"Exit\" to quit the program");
        dbq.connect();

        boolean signedIn = false;
        String choice = "";
        while (!choice.equalsIgnoreCase(QUIT)) {
            while (!signedIn) {
                System.out.println(WELCOME);
                String startScreenInput = sc.nextLine();

                if (startScreenInput.equalsIgnoreCase(LOGIN)) {
                    signedIn = uc.login(dbq, sc);
                } else if (startScreenInput.equalsIgnoreCase(SIGNUP)) {
                    signedIn = uc.signUp(dbq, sc);
                    dbq.create(new Pantry(), uc.getUser()); // just initializes pantry in the database
                } else if (startScreenInput.equalsIgnoreCase(QUIT)) {
                    System.out.println(GOODBYE);
                    System.exit(0);
                } else if (startScreenInput.equalsIgnoreCase(DELETE)) {
                    signedIn = uc.deleteUser(dbq, sc);
                }
                else {
                    System.out.println(RETRY);
                }
            }
            
            /**
             * Home
             */
            
            System.out.println(SELECTION);
            
            String homeInput = sc.nextLine();
            
            
            if (homeInput.equals(ONE)) {
                String searchInput = "";
                while (!searchInput.equals(SIX)) {
                    System.out.println(SEARCH_PROMPT); // search for a recipe
                    searchInput = sc.nextLine();
                    if (searchInput.equals(ONE)) {
                        keywordSearch(sc, uc, dbq);
                    } else if (searchInput.equals(TWO)) {
                        ingredientSearch(sc, uc, dbq);
                    } else if (searchInput.equals(THREE)) {
                        pantrySearch(sc, uc, dbq);
                    } else if (searchInput.equals(FOUR)) {
                        calorieSearch(sc, uc, dbq);
                    } else if (searchInput.equals(FIVE)) {
                        randomSearch(sc, uc, dbq);
                    } else if (searchInput.equals(SIX)) {
                        searchInput = SIX;
                    } else {
                        System.out.println(RETRY);
                    }
                }

            } 
            else if (homeInput.equals(TWO)){
            
                create(sc, uc, RC, dbq);
            
            } else if (homeInput.equals(THREE)) {

                delete(sc, uc);

            } else if (homeInput.equals(FOUR)) {

                view(sc, uc, dbq);

            }  else if (homeInput.equals(FIVE)){
                // Ingredient i = new Ingredient("Salt", "", 2.5f, "cups", 2.5f, "salty");
                // uc.getUser().getPantry().addIngredient(i);
                // dbq.create(uc.getUser().getPantry(), uc.getUser());
                // dbq.update(uc.getUser().getPantry(), i, uc.getUser());
                choice = QUIT;
            } 
            else {
                System.out.println(INVALID_SELECT);
            }
        }
    }

    private static void keywordSearch(Scanner sc, UserController uc, DBQuery dbq) throws SQLException, ParseException, IOException{
        boolean searchAgain = true;
        final String KEYWORD_SEARCH_PROMPT = "Please enter the keyword(s) you would like to search by: ";
        while(searchAgain)
        {
            System.out.printf(KEYWORD_SEARCH_PROMPT);
            String searchInput = sc.nextLine();
            boolean searchSuccess = Search.keywordSearch(searchInput);
            if(searchSuccess) searchLoop(sc,uc,dbq);
            
            System.out.println(SEARCH_AGAIN);
            String researchInput = sc.nextLine();
            if(researchInput.equals(TWO))
            {
                searchAgain = false;
            }
        }
    }

    private static void ingredientSearch(Scanner sc, UserController uc, DBQuery dbq) throws SQLException, ParseException, IOException{
        boolean searchAgain = true;
        
        while(searchAgain)
        {
            ArrayList<String> ingredients = new ArrayList<String>();
            String ingredInput = "";
            System.out.println(INGREDIENT_SEARCH_PROMPT);
            while(!ingredInput.equalsIgnoreCase(STOP))
            {
                ingredInput = sc.nextLine();
                if(!ingredInput.equalsIgnoreCase(STOP))
                {
                    ingredients.add(ingredInput);
                }
                else if(ingredInput.equalsIgnoreCase(REMOVE))
                {
                    ingredients.remove(ingredients.size() -1);
                }
                
            }
            //search based on ingredients input
            String searchString = ingredients.toString(); //convert list to string
            searchString = searchString.substring(1, searchString.length()-1); //trim brackets off of the ressult
            boolean searchSuccess = Search.keywordSearch(searchString);
            if(searchSuccess) searchLoop(sc, uc, dbq);
            
            System.out.println(SEARCH_AGAIN);
            String researchInput = sc.nextLine();
            if(researchInput.equals(TWO))
            {
                searchAgain = false;
            }
        }
    }

    private static void pantrySearch(Scanner sc, UserController uc, DBQuery dbq) throws SQLException, ParseException, IOException{
        boolean searchAgain = true;
        while(searchAgain)
        {
            ArrayList<String> pickedIngredients = new ArrayList<String>();
            String pantryInput = "";
            System.out.println("Everything In Your Pantry\n");
            for(Ingredient x : uc.getUser().getPantry().getIngredientList())
            {
                System.out.println(x.getIngredientName());
            }
            System.out.println(INGREDIENT_SEARCH_PROMPT);
            while(!pantryInput.equalsIgnoreCase(STOP))
            {
                pantryInput = sc.nextLine();
                if(!pantryInput.equalsIgnoreCase(STOP))
                {
                    pickedIngredients.add(pantryInput);
                }
                else if(pantryInput.equalsIgnoreCase(REMOVE))
                {
                    pickedIngredients.remove(pickedIngredients.size() -1);
                }
            }
            //search based on all ingredients inputted
            String searchString = pickedIngredients.toString(); //convert list to string
            searchString = searchString.substring(1, searchString.length()-1); //trim brackets off of the ressult
            boolean searchSuccess = Search.keywordSearch(searchString);
            if(searchSuccess) searchLoop(sc, uc, dbq);
            
            System.out.println(SEARCH_AGAIN);
            String researchInput = sc.nextLine();
            if(researchInput.equals(TWO))
            {
                searchAgain = false;
            }
        }          
    }

    private static void calorieSearch(Scanner sc, UserController uc, DBQuery dbq) throws SQLException, ParseException, IOException{
        boolean searchAgain = true;
        while(searchAgain)
        {
            System.out.println("Please enter the lower bound of the calorie range per serving size: ");
            Integer calorieInputLow = -1;
            Integer calorieInputHigh = -1;
            System.out.print("Enter the lower bound for your calorie range per serving size: ");
            while (calorieInputLow == -1)
            {
                if (sc.hasNextInt())
                {
                    calorieInputLow = sc.nextInt();
                    if (calorieInputLow < 0 || calorieInputLow > Integer.MAX_VALUE)
                    {
                        System.out.println("Not a valid input please try again");
                        calorieInputLow = -1;
                    }

                } else
                {
                    System.out.println("Not a valid input please try again");
                    sc.nextLine();
                }
            }
            sc.nextLine();
            System.out.print("Enter the upper bound for your calorie range per serving size: ");
            while (calorieInputHigh == -1)
            {
                if (sc.hasNextInt())
                {
                    calorieInputHigh = sc.nextInt();
                    if (calorieInputHigh < 0 || calorieInputHigh > Integer.MAX_VALUE)
                    {
                        System.out.println("Not a valid input please try again");
                        calorieInputHigh = -1;
                    }
                } else
                {
                    System.out.println("Not a valid input please try again");
                    sc.nextLine();
                }
            }
            if (calorieInputLow > calorieInputHigh)
            {
                Integer temp = calorieInputLow;
                calorieInputLow = calorieInputHigh;
                calorieInputHigh = temp;
            }
            System.out.println("Your calorie range is " + calorieInputLow + " to " + calorieInputHigh);
            //search based on the calorie range
            boolean searchSuccess = Search.calorieSearch(calorieInputLow, calorieInputHigh);
            if(searchSuccess) searchLoop(sc, uc, dbq);

            System.out.println(SEARCH_AGAIN);
            String researchInput = sc.nextLine();
            if(researchInput.equals(TWO))
            {
                searchAgain = false;
            }
        }
    }

    private static void randomSearch(Scanner sc, UserController uc, DBQuery dbq) throws SQLException, ParseException, IOException{
        boolean searchAgain = true;
        while(searchAgain)
        {
            System.out.println("Do you want a random recipe from your favorite recipes or recipes from all the recipes\n1 - Favorite Recipes\n2 - All Recipes");
            String randomInput = sc.nextLine();
            if(randomInput.equals(ONE))
            {
                int size = uc.getUser().getFavoriteRecipes().size();
                if(size != 0)
                {
                    int ranRecipe = (int) Math.random() * size;
                    Recipe ranRec = uc.getUser().getFavoriteRecipes().get(ranRecipe);
                    viewRecipe(ranRec, sc, uc, dbq);
                }
                else{
                    System.out.println("You have no liked recipes to choose from!");
                }
            }
            else if(randomInput.equals(TWO))
            {
                //grab random recipe
                JSONObject JSONRecipe = Search.randomSearch(); //Changed to get single recipe

                if(JSONRecipe != null){
                    Recipe ranRec = Recipe.JSONToRecipe(JSONRecipe);
                    viewRecipe(ranRec, sc, uc, dbq);
                } 
            }
            else
            {
                System.out.println(RETRY);
            }

            System.out.println(SEARCH_AGAIN);
            String researchInput = sc.nextLine();
            if(researchInput.equals(TWO))
            {
                searchAgain = false;
            }
        }
    }
    
    private static void searchLoop(Scanner sc, UserController uc, DBQuery dbq) throws SQLException {
        boolean searching = true;
        while (searching) {
            System.out.println("Previous page: 1\tNext page: 2\tView a recipe: 3\tQuit searching: 4");
            String searchPageInput = sc.nextLine();
            switch (searchPageInput) {
                case ONE:
                    Search.previousPage();
                    break;
                case TWO:
                    Search.nextPage();
                    break;
                case THREE:
                    viewRecipeFromSearch(sc, uc, dbq);
                    break;
                case FOUR:
                    searching = false;
                    break;
                default:
                    System.out.println(INVALID_SELECT);
                    sc.nextLine();
                    break;
            }
        }
    }
    
    private static void viewRecipeFromSearch(Scanner sc, UserController uc, DBQuery dbq) throws SQLException {
        System.out.printf("Enter a recipe index to view the recipe with that index: ");
        String input = sc.nextLine();
        
        try {
            int pageIndex = Integer.parseInt(input);
            Search.viewRecipe(pageIndex);
            JSONObject recipeJSON = Search.getCurRecipe();
            boolean viewingRecipe = true;
            while (viewingRecipe) {
                System.out.println(VIEW_RECIPE_OPTIONS);
                input = sc.nextLine();
                Recipe curRecipe = null;
                curRecipe = Recipe.JSONToRecipe(recipeJSON);
                User curUser = uc.getUser();
                switch (input) {
                    case ONE:
                        if(curRecipe != null){
                            addRecipeToWeeklyPlan(sc, uc, curRecipe, dbq);
                        }else System.err.println("Error converting JSON to recipe, recipe=null");
                        break;
                    case TWO:
                        if(curRecipe != null){
                            addRecipeToRecipeList(sc, uc, curRecipe, dbq);
                        }else System.err.println("Error converting JSON to recipe, recipe=null");
                        break;
                    case THREE:
                        uc.getUser().addToFavoriteRecipes(curRecipe);
                        break;
                    case FOUR:
                        if(curRecipe != null){
                            userRecipeShare(sc, curRecipe, curUser);
                        }else System.err.println("Error converting JSON to recipe, recipe=null");
                        break;
                    case FIVE:
                        changeRecipeServingSize(curRecipe, sc, uc);
                        break;
                    case SIX:
                        viewingRecipe=false;
                        break;
                    default:
                        System.out.println(INVALID_SELECT);
                        sc.nextLine();
                        break;
                }
                if(curRecipe != null){
                    uc.getUser().addToRecipeHistory(curRecipe);
                    
                }else System.err.println("Error converting JSON to recipe, recipe=null");
            }
        } catch (NumberFormatException nfe) {
            System.out.println("The recipe index must be a number");
        }
    }

    private static void userRecipeShare(Scanner sc, Recipe recipeIn, User curUser ){
        Email emailShare = new Email();
        boolean quitSharing = false; 
                                               
        while(!quitSharing){
            System.out.println("Send to yourself or someone else?\n1 - Self\n2 - Someone else\n3 - Back");
            String shareInput = sc.nextLine();
            
            //User chooses to send to themselves
            if(shareInput.equals(ONE)){
                //Send email to user email
                emailShare.shareRecipe(recipeIn, curUser.getEmail());
            }
            
            else if(shareInput.equals(TWO)){
                String recipient;
                System.out.print("Enter the desired recipient email : ");
                recipient = sc.nextLine();
                System.out.println("\n");

                //Send email to user-designated recipient
                emailShare.shareRecipe(recipeIn, recipient);
            }  

            else if(shareInput.equals(THREE)){
                System.out.println("Cancelling share");
                quitSharing = true;
            }
            
            else{
                System.out.println(INVALID_SELECT);
            }
        } 
    }

    private static void userWeeklyPlanShare(Scanner sc, WeeklyPlan planIn, User curUser){
        Email emailShare = new Email();
        boolean quitSharing = false;
              
        while(!quitSharing){
            System.out.println("Send to yourself or someone else?\n1 - Self\n2 - Someone else\n3 - Back");
            String shareInput = sc.nextLine();
            //User chooses to send to themselves
            if(shareInput.equals(ONE)){
                //Send email to user email
                emailShare.shareWeeklyPlan(planIn, curUser.getEmail());
            }

            else if(shareInput.equals(TWO)){
                String recipient;
                System.out.print("Enter the desired recipient email : ");
                recipient = sc.nextLine();
                System.out.println("\n");
            
                //Send email to user-designated recipient
                emailShare.shareWeeklyPlan(planIn, recipient);
            }  

            else if(shareInput.equals(THREE)){
                System.out.println("Cancelling share");
                quitSharing = true;
            }
            
            else{
                System.out.println(INVALID_SELECT);
            }  

        } 
    }
    
    public static void viewRecipeList(Scanner sc, UserController uc, DBQuery dbq) throws SQLException{
        int count = uc.getUser().showRecipeLists();
        if (count != 0) {
            System.out.println("Which Recipe List would you like to view?");
            String list = sc.nextLine();
            RecipeList rlist = uc.getUser().getRecipeListByName(list);
            
            if (rlist == null) {
                System.out.println("The Recipe List you entered could not be found");
            } 
            else {
                int index = uc.getUser().getRecipeLists().indexOf(rlist);
                
                rlist.info();
                System.out.println("What would you like to do in this Recipe List?\n");
                String choice = "";

                while (!(choice.equals(FOUR))) {
                    System.out.println("1 - Change Recipe List Name\n2 - Delete Recipe\n3 - View Recipe\n4 - Back");
                    choice = sc.nextLine();   

                    if (choice.equals(ONE)) {
                        System.out.println("Enter a new name for the recipe list:");
                        String newName = sc.nextLine();
                        
                        rlist.setName(newName);
                        uc.getUser().getRecipeLists().set(index, rlist);

                    } 
                    else if (choice.equals(TWO)) {
                        System.out.println("Which recipe would you like to delete?");
                        String rName = sc.nextLine();
                        Recipe recipe = rlist.getRecipeByName(rName);

                        if (recipe == null) {
                            System.out.println("The recipe you entered could not be found in the Recipe List");
                        } else {
                            System.out.println("Deleted " + rName + " from " + rlist.getName() + ".");
                            rlist.removeRecipeFromRecipeList(recipe);
                            uc.getUser().getRecipeLists().set(index, rlist);
                        }

                    } 
                    else if (choice.equals(THREE)) {
                        Boolean enteringName = true;
                        while(enteringName){
                            System.out.println("Enter the name of the recipe you want to view:");
                            String rName = sc.nextLine();
                            Recipe recipe = rlist.getRecipeByName(rName);
                            if (recipe == null) {
                                System.out.println("The recipe you entered could not be found in the Recipe List");
                            } else {
                                enteringName = false;
                                viewRecipe(recipe, sc, uc, dbq);
                            }
                        }
                    }
                }
            }               
        }
    }

    public static void addRecipe(Scanner sc, UserController uc, Recipe recipe, DBQuery dbq) throws SQLException {
        System.out.println("\nWhere do you want to add the recipe?\n");
        String location = "";
        
        while (!location.equals(THREE)) {
            System.out.println("1 - Weekly Plan\n2 - Recipe List\n3 - Back\n");
            location = sc.nextLine();
            if (location.equals(ONE)) {
                addRecipeToWeeklyPlan(sc, uc, recipe, dbq);
            } else if (location.equals(TWO)) {
                addRecipeToRecipeList(sc, uc, recipe, dbq);
            } else if (location.equals(THREE)) {

            } else {
                System.out.println(RETRY);
            }
        }
    }
    
    public static void addRecipeToWeeklyPlan(Scanner sc, UserController uc, Recipe recipe, DBQuery dbq) throws SQLException {
        boolean enteringPlan = true;
        // ResultSet rs = dbq.populateWeeklyPlan(new WeeklyPlan(), uc.getUser());
        // while (rs.next()) {
        //     uc.getUser().addWeeklyPlan(new WeeklyPlan(rs.getString(2)));
        // }
        while(enteringPlan){
            if (uc.getUser().getWeeklyPlans().size() != 0) {
                System.out.println("\nWhich Weekly Plan would you like to add the recipe to?");

                uc.getUser().showWeeklyPlans();
                System.out.println();

                String plan = sc.nextLine();
                WeeklyPlan wp = uc.getUser().getWeeklyPlanByName(plan);

                if (wp != null) {
                    enteringPlan = false;
                    boolean back = false;
                    while(!back)
                    {
                        System.out.println("Which day of the week would you like to add the recipe to?");
                        String day = sc.nextLine();
                        day = day.substring(0, 1).toUpperCase() + day.substring(1);
                        if(day.equals("Monday") || day.equals("Tuesday") || day.equals("Wednesday") || day.equals("Thursday") || day.equals("Friday") || day.equals("Saturday") || day.equals("Sunday"))
                        {
                            wp.addRecipeToWeeklyPlan(recipe, day);
                            dbq.create(recipe, uc.getUser());
                            dbq.create(wp, recipe, uc.getUser(), day);
                            back = true;
                        }
                        else
                        {
                            System.out.println("That is not a valid day of the week");
                        }
                    }
                } else {
                    System.out.println(plan + " was not found in your Weekly Plans.\n");
                }
            } else {
                System.out.println("You have no Weekly Plans...\n");
                enteringPlan = false;
            }
        }
    }

    public static void addRecipeToRecipeList(Scanner sc, UserController uc, Recipe recipe, DBQuery dbq) throws SQLException{
        boolean enteringList = true;
        while(enteringList){
            if (uc.getUser().getRecipeLists().size() != 0) {
                System.out.println("\nWhich Recipe List would you like to add the recipe to?");
                uc.getUser().showRecipeLists();
                System.out.println();

                String list = sc.nextLine();
                RecipeList rl = uc.getUser().getRecipeListByName(list);

                if (rl != null) {
                    enteringList = false;
                    rl.addRecipeToRecipeList(recipe);
                    dbq.create(rl, recipe, uc.getUser());
                } else {
                    System.out.println(list + " was not found in your Recipe Lists.\n");
                }
            } else {
                System.out.println("You have no Recipe Lists...\n");
                enteringList = false;
            }
        }
    }
    
    public static void viewCustomRecipes(Scanner sc, UserController uc, DBQuery dbq) throws SQLException
    {
       ArrayList<Recipe> recipes = uc.getUser().getCustomRecipeList();
        if(recipes.size() == 0)
        {
            System.out.println("\nYou do not have any custom recipes\n");
        }
        else
        {
            System.out.println("Custom Recipes:\n");

            boolean back = false;
            while(!back)
            {
                int counter=0;
                if (recipes.size() == 0)
                {
                    System.out.println("\nYou do not have any custom recipes\n");
                    back = true;
                    break;
                } else {
                    System.out.println("====================\nCURRENT CUSTOM RECIPES\n====================\n");
                    for (int i=0;i<recipes.size();i++)
                    {
                        System.out.println(i+1 + " " +recipes.get(i).getName());
                        counter++;
                    }
                    System.out.println("====================\n");   
                }

                System.out.println("1-View Recipe, 2-Delete Recipe, 3-back");
                String input = sc.nextLine();
                System.out.println();
                if(input.equals(ONE))
                {
                    System.out.println("Type the number of the recipe you want to view");
                    input = sc.nextLine();
                    int recipeNum = 0;
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
                        Recipe recipe = recipes.get(recipeNum);
                        viewRecipe(recipe, sc, uc, dbq);
                    }

                }
                else if(input.equals(TWO))
                {
                    System.out.println("Type the number of the recipe you want to delete");
                    input = sc.nextLine();
                    int recipeNum = 0;
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
                else if(input.equals(THREE))
                {
                    back = true;
                }
                else
                {
                    System.out.println("That is not a valid input");
                }
            }
        } 
    }
    
    public static void viewWeeklyPlans(Scanner sc, UserController uc, DBQuery dbq) throws SQLException
    {
        uc.getUser().showWeeklyPlans();
        if (uc.getUser().getWeeklyPlans().size()!=0) {
            System.out.println("Which Weekly plan would you like to view?");
            String weeklyPlanName = sc.nextLine();
            WeeklyPlan plan = uc.getUser().getWeeklyPlanByName(weeklyPlanName);
            System.out.println(plan.info());
            String back="";
            while(!back.equals(BACK))
            {
                System.out.println("Please choose an option");
                System.out.println("1 - Update Weekly Plan Info, 2 - View Recipe, 3 - Share Weekly Plan, 4 - Convert to Shopping List, 5 - Back");
                String option = sc.nextLine();
                if(option.equals(ONE))
                {
                    boolean done = false;
                    while(!done)
                    {
                        System.out.println("Welcome to Update Weekly Plan ");
                        System.out.println();
                        System.out.println("1-Change Weekly Plan Name\n2-Delete Recipe\n3-Back");
                        
                        String input = sc.nextLine();
                        switch(input) {
                            case ONE:
                                System.out.println("Type back to get return or type in a new name for the weekly plan");
                                String line = sc.nextLine();
                                if(!line.equals(BACK))
                                {
                                    plan.setName(line);
                                    System.out.println("The plan name has been changed");
                                }
                                break;
                            case TWO:
                                System.out.println("Which recipe would you like to delete?");
                                String sRecipe = sc.nextLine();
                                System.out.println("Which day is that recipe on?");
                                String day = sc.nextLine();
                                Recipe recipe = plan.getRecipeByName(sRecipe);
                                if(plan.removeRecipeFromWeeklyPlan(recipe, day))
                                {
                                    System.out.println("The recipe has been removed from " + day);
                                }
                                else{
                                    System.out.println("Either the recipe does not exist or its not on that day of the week");
                                }
                                break;
                            case THREE:
                                done = true;
                                break;
                                
                            default:
                                System.out.println(RETRY);
                        }

                        
                    }
                }
                else if(option.equals(TWO))
                {
                    System.out.println("Which recipe would you like to view?");
                    String sRecipe = sc.nextLine();
                    Recipe recipe = plan.getRecipeByName(sRecipe);
                    if (!recipe.equals(null))
                        System.out.println(recipe.printRecipe());
                    else {
                        System.out.println("It looks like this recipe does not exist.");
                    }
                    //viewRecipe(recipe, sc, uc, dbq);
                    back = BACK;
                }
                else if(option.equals(THREE))
                {
                    userWeeklyPlanShare(sc, plan, uc.getUser());
                    back = BACK; 
                }
                else if(option.equals(FOUR)){
                    ShoppingListConverter.convertToShoppingList(plan, uc.getUser().getPantry());
                }
                else if (option.equals(FIVE)) {
                    back = BACK;
                }
                else
                {
                    System.out.println(INVALID_SELECT);
                }
            }
        }
    }

    public static void createRecipe(Scanner sc, UserController uc, RecipeController RC, DBQuery dbq) throws SQLException
    {
        System.out.println("\nWelcome to create a recipe:\n");
        System.out.println("Please enter a name for your recipe or type back to cancel: ");
        String recipeName = sc.nextLine();
        if(recipeName.equals(BACK)){
          return;
        } else {
            uc.getUser().addCustomRecipe(RC.createRecipe(recipeName, sc, uc, dbq));
        }
    }

    // public static void createIngredient(Scanner sc, UserController uc, RecipeController RC)
    // {
    //     System.out.println("\nWelcome to create a recipe:\n");
    //     System.out.println("Please enter a name for your recipe or type back to cancel: ");
    //     String recipeName = sc.nextLine();
    //     if(recipeName.equals(BACK)){
    //       return;
    //     } else {
    //         uc.getUser().addCustomRecipe(RC.createRecipe(recipeName, sc));
    //     }
    // }
    
    
    public static void createCustomRecipe(Scanner sc, UserController uc, RecipeController RC, DBQuery dbq)
    {
        String backIngredient = "";
                            
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
                catch (InputMismatchException e) {
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
            //dbq.create(temp, uc.getUser());
            uc.getUser().getPantry().addIngredient(temp);
            System.out.println(ingredientName + " has been added to the pantry");
            backIngredient = BACK;
            }
        }
    }

    public static void createWeeklyPlan(Scanner sc, UserController uc, RecipeController RC, DBQuery dbq) throws SQLException
    {
        String backWeeklyPlan = "";
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
                uc.getUser().addWeeklyPlan(newWeeklyPlan);
                System.out.println("Your weekly plan called " + weeklyPlanName + " has been created");
                dbq.create(newWeeklyPlan, uc.getUser());
                backWeeklyPlan = BACK;
            }
        }
    }

    public static void createRecipeList(Scanner sc, UserController uc, RecipeController RC, DBQuery dbq) throws SQLException
    {
        String backRecipeList = "";
        while (!backRecipeList.equals(BACK)) {
            System.out.println("Welcome to create a recipe list");
            System.out.println("Please name your recipe list or type back to return to the menu:");
            String recipeListName = sc.nextLine();
            if(recipeListName.equals(BACK)){
                backRecipeList = recipeListName;
            }
            else{
                RecipeList newRecipeList = new RecipeList();
                newRecipeList.setName(recipeListName);
                uc.getUser().addListOfRecipies(newRecipeList);
                System.out.println("Your recipe list called " + recipeListName + " has been created");
                //db write
                dbq.create(newRecipeList, uc.getUser());
                backRecipeList = BACK;
            }
        }
    }

    private static void create(Scanner sc, UserController uc, RecipeController RC, DBQuery dbq) throws SQLException{
        String createInput = "";
        while (!createInput.equals(FIVE)) {
            System.out.println(CREATE_PROMPT);
            createInput = sc.nextLine();
            if (createInput.equals(ONE)) 
            {
                createRecipe(sc, uc, RC, dbq);
            } 
            else if (createInput.equals(TWO)) 
            {
                createCustomRecipe(sc, uc, RC, dbq);
            }
            else if (createInput.equals(THREE)) 
            {
                createWeeklyPlan(sc, uc, RC, dbq);
            } 
            else if (createInput.equals(FOUR)) 
            {
                createRecipeList(sc, uc, RC, dbq);
            } 
            else if (createInput.equals(FIVE)) 
            {
                createInput = FIVE;
            } 
            else 
            {
                System.out.println(RETRY);
            }
        }
    }

    private static void viewPantry(Scanner sc, UserController uc, DBQuery dbq) throws SQLException{
        String pantryInput ="";
        while(!(pantryInput.equals(FOUR)))
        {
            System.out.println(uc.getUser().getPantry());
            System.out.println("\nWhat would you like to do in the pantry?\n1 - Add Ingredient\n2 - Delete Ingredient\n3 - Update Ingredient\n4 - Back");
            pantryInput = sc.nextLine();

            if(pantryInput.equals(ONE))
            {
                boolean addAgain = true;
                while(addAgain)
                {
                    Ingredient temp = new Ingredient();
                    
                    System.out.println("Enter the name of the ingredient you want to add:\n");
                    String ingredientInput = sc.nextLine();  
                    if(!(uc.getUser().getPantry().hasIngredient(ingredientInput)) && !(dbq.read(new Ingredient(ingredientInput, "", 0.0f, "" ), uc.getUser()).next()))
                    { 
                        temp.setIngredientName(ingredientInput);
                        ingredientInput = "";
                        System.out.println("Enter the quantity of your ingredient(number only):\n");
                        ingredientInput = sc.nextLine();
                        boolean hasFloat = false;
                        float quantity = -1;
                        while(!hasFloat)
                        {
                            try
                            {
                                quantity = Float.parseFloat(ingredientInput);
                            }
                            catch(NumberFormatException e)
                            {
                                System.out.println(RETRY);
                            }
                            hasFloat = true;
                            temp.setQuantity(quantity);
                        }
                        System.out.println("What are the units of your quantity:\n");
                        ingredientInput = "";
                        ingredientInput = sc.nextLine();
                        temp.setMeasure(ingredientInput);
                        uc.getUser().getPantry().addIngredient(temp);
                        System.out.println("The ingredient you added is:\n" + temp.getQuantity() + " " + temp.getMeasure() + " " + temp.getIngredientName());
                        dbq.create(uc.getUser().getPantry(), temp, uc.getUser());
                        // ResultSet rs = dbq.read(temp, uc.getUser());
                        // if (rs.next()) {
                                
                        //     int ingredientID = rs.getInt(2);
                        //     temp.setIngredientID("" + ingredientID);
                        // }
                        System.out.println("\nWould you like to add another ingredient?\n1 - Yes\n2 - No");
                        String updateAgainInput = sc.nextLine();
                        if(!(updateAgainInput.equalsIgnoreCase(ONE)))
                        {
                            addAgain = false;
                        }
                    }  
                    else
                    {
                        System.out.println("You already have that ingredient in your pantry");
                        addAgain = false;
                    } 
                }
            }   
            else if(pantryInput.equals(TWO))
            {
                if(uc.getUser().getPantry().getIngredientList().size() > 0)
                {
                    boolean deleteAgain = true;
                    while(deleteAgain)
                    {
                        System.out.println("What is the name of ingredient you want to delete?");
                        String ingredientName = sc.nextLine();
                        if( uc.getUser().getPantry().removeIngredient(ingredientName))
                        {
                            System.out.println(ingredientName + " has been removed from the pantry");
                            dbq.delete(new Ingredient(ingredientName, "", 0.0f, ""), uc.getUser());
                        }
                        else
                        {
                            System.out.println(ingredientName + " does not exist!");
                        }
                        
                        System.out.println("Do you want to delete something again?\n1 - Yes\n2 - No");
                        String tryAgainInput = sc.nextLine();
                        while(!tryAgainInput.equals(ONE))
                        {
                            if(tryAgainInput.equals(TWO))
                            {
                                deleteAgain =false;
                            }
                            else{
                                System.out.println(RETRY);
                            }
                        }
                    }    
                }
                else
                {
                    System.out.println("You have no ingredients in your pantry");
                }
            }    
            else if(pantryInput.equals(THREE))
            {
                boolean updateAgain = true;
                while(updateAgain)
                {
                    if(uc.getUser().getPantry().getIngredientList().size() > 0)
                    {
                        System.out.println("Enter the name of the ingredient you want to update:\n");
                        String ingredientName = sc.nextLine();
                        if(uc.getUser().getPantry().hasIngredient(ingredientName))
                        {
                            Ingredient temp = uc.getUser().getPantry().search(ingredientName);

                            System.out.println("The Ingredient's current name is \"" + temp.getIngredientName() +"\"\nEnter a new name or \"Next\" to keep the old one");
                            String oldName = temp.getIngredientName();
                            String input = sc.nextLine();
                            if(input.equalsIgnoreCase("Next"))
                            {
                                System.out.println("Keeping the original name");
                            }
                            else{
                                temp.setIngredientName(input);
                            }
                            System.out.println("The Ingredient's current quantity is \"" + temp.getQuantity() +"\"\nEnter a new number(just the number) or \"Next\" to keep the old one");
                            input = "";
                            input = sc.nextLine();
                            if(input.equalsIgnoreCase("Next"))
                            {
                                continue;
                            }
                            else{
                                boolean hasFloat = false;
                                float quantity = -1;
                                while(!hasFloat)
                                {
                                    try
                                    {
                                        quantity = Float.parseFloat(input);
                                    }
                                    catch(NumberFormatException e)
                                    {
                                        System.out.println(RETRY);
                                    }
                                    hasFloat = true;
                                    temp.setQuantity(quantity);
                                }
                            }
                            System.out.println("The Ingredient's current units are \"" + temp.getMeasure() + "\"\nEnter a new unit or \"Next\" to keep the old one");
                            input = "";
                            input = sc.nextLine();
                            if(input.equalsIgnoreCase("Next"))
                            {
                                continue;
                            }
                            else{
                                temp.setMeasure(input);
                            }

                            System.out.println("The ingredient has been updated to be:\n" + temp.getQuantity() + " " + temp.getMeasure() + " " + temp.getIngredientName());
                            String newName = temp.getIngredientName();
                            temp.setIngredientName(oldName); // weird technical thing with database, no way to store ID from query unless specifically read somewhere...
                            ResultSet rs = dbq.read(temp, uc.getUser());
                            if (rs.next()) {
                                
                                int ingredientID = rs.getInt(2);
                                temp.setIngredientID("" + ingredientID);
                            }
                            temp.setIngredientName(newName); // set back to new name immediately after getting the ID that matches the old ingredient name
                            dbq.update(temp, uc.getUser());
                            System.out.println("\nWould you like to update another ingredient?\n1 - Yes\n2 - No");
                            String updateAgainInput = sc.nextLine();
                            if(!updateAgainInput.equalsIgnoreCase(ONE))
                            {
                                updateAgain = false;
                            }
                            
                        }
                    }
                    else{
                        System.out.println("You have no ingredients in your pantry");
                        updateAgain = false;
                    }
                }
            }
            else if(pantryInput.equals(FOUR))
            {
                pantryInput = FOUR;
            }
            else{
                System.out.println(RETRY);
            }
        }
    }

    private static void view(Scanner sc, UserController uc, DBQuery dbq) throws SQLException{
        String viewInput = "";
        while (!viewInput.equals(SIX)) {
            System.out.println(VIEW_PROMPT);
            viewInput = sc.nextLine();
            if (viewInput.equals(ONE)) {  
                viewRecipeList(sc, uc, dbq);
            }
            else if (viewInput.equals(TWO)) {
                viewPantry(sc, uc, dbq);
            }
            else if (viewInput.equals(THREE)) {
                viewWeeklyPlans(sc, uc, dbq);
            } else if (viewInput.equals(FOUR)) {
                ArrayList<Recipe> recipeHis = uc.getUser().getRecipeHistory();
                int counter = 0;
                for(Recipe x : recipeHis)
                {
                    counter++;
                    System.out.println(counter + " " + x.getName()); 
                }
                System.out.println("What do you want to do?\n1 - View Recipe\n2 - back");
                String historyInput = "";
                boolean goodInput = false;
                while(!goodInput)
                {
                    historyInput = sc.nextLine();
                    if(historyInput.equalsIgnoreCase(ONE))
                    {
                        historyInput ="";
                        boolean goodInput2 = true;
                        int num = -1;
                        while(!goodInput2)
                        {
                            System.out.println("Enter the number of the recipe you want to view:\n");
                            ArrayList<Recipe> temp = uc.getUser().getRecipeHistory();
                            historyInput = sc.nextLine();
                            try{
                                num = Integer.parseInt(historyInput);
                            }
                            catch(NumberFormatException e)
                            {
                                System.out.println(RETRY);
                            }
                        
                            if(num > 0 && num <= 20)
                            {
                                viewRecipe(temp.get(num-1), sc, uc, dbq);
                                goodInput2 = true;
                            }
                            else{
                                System.out.println(RETRY);
                            }
                        } 
                    }
                    else if(historyInput.equalsIgnoreCase(TWO))
                    {
                        goodInput = true;
                    }
                    else{
                        System.out.println(RETRY);
                    }
                }   
            } else if (viewInput.equals(FIVE)) {
                viewCustomRecipes(sc, uc, dbq);
            } else if (viewInput.equals(SIX)) {
                viewInput.equals(SIX);
            }
            else 
            {
                System.out.println(RETRY);
            }
        }
    }

    private static void changeRecipeServingSize(Recipe recipe, Scanner sc, UserController uc){
        boolean getInput = true;
        while(getInput){
            System.out.println("Enter the factor you want to multiply the serving size by:");
            String input = sc.nextLine();
            try {
                double factor = Double.parseDouble(input);
                recipe.changeServingSize(factor);
                System.out.println("Note that the recipe instructions will not contain updated ingredient amounts");
                getInput = false;
            } catch (NumberFormatException e) {
                 System.out.println("Your input was not a valid factor, please try again");
            }
        }
    }
    
    private static void viewRecipe(Recipe recipe, Scanner sc, UserController uc, DBQuery dbq) throws SQLException
    {
        boolean done =false;
        while(!done)
        {
            uc.getUser().addToRecipeHistory(recipe);
            System.out.println(recipe.printRecipe());
            System.out.println("What do you want to do with this recipe:\n1 - Add to weekly plan"+
                "\n2 - Add to Recipe List\n3 - Add to favorite recipes\n4 - Share\n5 - Change recipe serving sizes\n6 - Back");
            String input = sc.nextLine();
            switch (input) {
            case ONE:
                addRecipeToWeeklyPlan(sc, uc, recipe, dbq);
                break;
            case TWO:
                addRecipeToRecipeList(sc, uc, recipe, dbq);
                break;
            case THREE:
                uc.getUser().addToFavoriteRecipes(recipe);
                break;
            case FOUR:
                userRecipeShare(sc, recipe, uc.getUser());
                break;
            case FIVE:
                changeRecipeServingSize(recipe, sc, uc);
                break;
            case SIX:
                done = true;
                break;
            default:
                System.out.println(INVALID_SELECT);
                break;
            }
        }
        
    }

    private static void delete(Scanner sc, UserController uc){
        String deleteInput = "";
        while (!deleteInput.equals(THREE)) {
            System.out.println("Type:\n1 - Weekly Plan\n2 - Recipe List\n3 - Back\n");
            deleteInput = sc.nextLine();
            if (deleteInput.equals(ONE)) {
                uc.getUser().showWeeklyPlans();
                if (uc.getUser().getWeeklyPlans().size() != 0) {
                    System.out.println("\nWhich weekly plan would you like to remove?");
                    String plan = sc.nextLine();
                    WeeklyPlan temp = uc.getUser().getWeeklyPlanByName(plan);
                    if (temp != null) {
                        boolean delres = uc.getUser().removeWeeklyPlan(temp);
                        if (delres) {
                            System.out.println("Deleted weekly plan.");
                        }
                    }
                    else {
                        System.out.println("The weekly plan you entered could not be found...");
                    }
                }
            } else if (deleteInput.equals(TWO)) {
                uc.getUser().showRecipeLists();
                if (uc.getUser().getRecipeLists().size() != 0) {
                    System.out.println("\nWhich recipe list would you like to remove?");
                    String list = sc.nextLine();
                    RecipeList temp = uc.getUser().getRecipeListByName(list);
                    if (temp != null) {
                        boolean delres = uc.getUser().removeRecipeList(temp);
                        if (delres) {
                            System.out.println("Deleted recipe list.");
                        }
                    }
                } else {
                    System.out.println("The recipe plan you entered could not be found...");
                }
                
            } else if (deleteInput.equals(THREE)) {
                deleteInput = THREE;
            } else {
                System.out.println(RETRY);
            }

        }
    }
}

    

