package chefmark;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class App {
    private static final String RETRY = "Your answer did not match any of our options. Please reenter";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String currentUser;
        String password;
        DBQuery dbq = new DBQuery(new MySQLDB());
        User user;
        String state = "";
        //DBConnection db = new DBConnection(new MySQLDB()); // mySQL
        UserController uc = new UserController();
        Pantry userPantry = new Pantry();
        ArrayList<WeeklyPlan> arrayOfWeeklyPlans = new ArrayList<>();
        Pattern usernameRegex = Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$"); // username
        Pattern passwordRegex = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=[^\r\n\t\f ]+$).{8,}$"); // password

        //System.out.println("Type \"Exit\" to quit the program");
        dbq.connect();

        //String exit = sc.nextLine();

        // if (exit.equals("Exit")) {
        //     System.exit(0);
        // }
        boolean signedIn = false;
        String choice = "";
        while (!choice.equals("quit")) {
            while (!signedIn) {
                System.out.println("==== Welcome to ChefMark! ====");
                System.out.println("login");
                System.out.println("signup");
                System.out.println("quit");
                String startScreenInput = sc.nextLine();

                if (startScreenInput.equals("login")) {
                    System.out.println("\nEnter your username: ");
                    String tempString = sc.nextLine();
                    Matcher usernameMatch = usernameRegex.matcher(tempString);
                    
                    while (!(usernameMatch.matches())) {
                        System.out.println("Username is incorrect format. Usernames must be between 5 and 20 characters");
                        System.out.println("\nEnter your username: ");
                        tempString = sc.nextLine();
                        usernameMatch = usernameRegex.matcher(tempString);
                    }
                    currentUser = tempString;

                    System.out.println("Enter your password: ");

                    tempString = sc.nextLine();

                    Matcher passwordMatch = passwordRegex.matcher(tempString);
                    while (!(passwordMatch.matches())) {
                        System.out.println("Password is in incorrect format. Passwords must be at least 8 characters, include 1 uppercase, 1 lowercase, 1 number, and 1 special character");
                        System.out.println("\nEnter your password: ");
                        tempString = sc.nextLine();
                        passwordMatch = passwordRegex.matcher(tempString);
                    }
                    password = tempString;
            
                    //dbq.connect();
                    ResultSet rs = dbq.read(new User(currentUser, password, ""));
                    while (rs.next()) {
                        if (rs.getString(1).equals(currentUser) && rs.getString(2).equals(password)) {
                            System.out.println("\nLogin successful! Press 'Enter' to continue");
                            signedIn=true;
                        }
                        else {
                            System.out.println("\nUsername or password is incorrect. Do you have an account?");
                        }
                    }
                    /**
                     * LOGIN
                     * 
                     * Query database passing in email and password
                     * If doesn't exist, tell user, show original login/signup prompt
                     * If password incorrect, tell user, reprompt password field
                     * Otherwise, enter into main application
                     */
                    //signedIn = true;
                } else if (startScreenInput.equals("signup")) {
                    String email = "";
                    System.out.println("Enter your desired username: ");
                    currentUser = sc.nextLine();
                    System.out.println("Enter your desired password: ");
                    password = sc.nextLine();
                    System.out.println("Enter your desired email: ");
                    email = sc.nextLine();
                    user = new User(currentUser, password, email);
                    ResultSet rs = dbq.read(user);
                    while (rs.next()) {
                        while (currentUser.equals(rs.getString(1))) {
                            System.out.println("Username already taken. Please enter a new username: ");
                            currentUser = sc.nextLine();
                        }
                        while (email.equals(rs.getString(3))) {
                            System.out.println("Email already taken. Please enter a new email: ");
                            email = sc.nextLine();
                        }

                    }
                    dbq.create(user);
                    System.out.println("You are now logged in. Please save your login info as this cannot be retrieved. Press enter to continue");
                    currentUser = user.getUsername();
                    //dbq.create(new User(currentUser, password, email));
                    /**
                     * SIGNUP
                     * 
                     * Query database passing in email first
                     * If email already found in database, tell user, send to LOGIN
                     * Else display password input field
                     */
                    signedIn = true;
                } else if (startScreenInput.equals("quit")) {
                    System.out.println("GoodBye");
                    System.exit(0);
                } else {
                    System.out.println(RETRY);
                }
            }
            /**
             * Home
             */
            String homeInput = sc.nextLine();

            System.out.println(
                    "1 - Search\n2 - Create\n3 - Delete\n4 - Update\n5 - Add\n6 - Remove\n7 - View\n8 - Rate\n9 - Convert\n10 - Share\n11 - Next\n12 - back\n");

            if (homeInput.equals("1")) {
                String searchInput = "";
                while (!searchInput.equals("6")) {
                    System.out.println(
                            "1 - By Name\n2 - By Ingredient\n3 - By Pantry\n4 - By Calories\n5 - Random\n6 - Back");
                    searchInput = sc.nextLine();
                    if (searchInput.equals("1")) {
                            boolean searchAgain = true;
                            while(searchAgain)
                            {
                                System.out.println("Please enter the name of the recipe you would like to search: ");
                                String nameInput = sc.nextLine();
                                //search method

                                System.out.println("Do you want to search for another recipe?\n1 - Yes\n2 - No");
                                String researchInput = sc.nextLine();
                                if(researchInput.equals("2"))
                                {
                                    searchAgain = false;
                                }
                            }
                    } else if (searchInput.equals("2")) {
                        boolean searchAgain = true;
                            while(searchAgain)
                            {
                                ArrayList<String> ingredients = new ArrayList<String>();
                                String ingredInput = "";
                                System.out.println("Please enter the ingredients you would like to use in a recipe:\nEnter \"Stop\" to stop");
                                while(!ingredInput.equalsIgnoreCase("stop"))
                                {
                                    ingredInput = sc.nextLine();
                                    if(!ingredInput.equalsIgnoreCase("stop"))
                                    {
                                        ingredients.add(ingredInput);
                                    }
                                    
                                }
                                System.out.println("Do you want to search for another recipe?\n1 - Yes\n2 - No");
                                String researchInput = sc.nextLine();
                                if(researchInput.equals("2"))
                                {
                                    searchAgain = false;
                                }
                            }

                    } else if (searchInput.equals("3")) {

                    } else if (searchInput.equals("4")) {
                        boolean searchAgain = true;
                            while(searchAgain)
                            {
                                System.out.println("Please enter the calorie range per serving size: ");
                                double calorieInput = sc.nextDouble();
                                //search method

                                System.out.println("Do you want to search for another recipe?\n1 - Yes\n2 - No");
                                String researchInput = sc.nextLine();
                                if(researchInput.equals("2"))
                                {
                                    searchAgain = false;
                                }
                            }
                    } else if (searchInput.equals("5")) {
                        boolean searchAgain = true;
                            while(searchAgain)
                            {
                                System.out.println("Do you want a random recipe from liked recipes or from all the recipes\n1 - Liked Recipes\n2 - All Recipes");
                                String randomInput = sc.nextLine();
                                if(randomInput.equals("1"))
                                {
                                    //the amount of liked recipes
                                    int size = 0;
                                    int ranRecipe = (int) Math.random() * size;
                                    //search from liked recipes
                                }
                                else if(randomInput.equals("2"))
                                {
                                    //apply random filter then search and grab random recipe
                                }

                                System.out.println("Do you want to search for another recipe?\n1 - Yes\n2 - No");
                                String researchInput = sc.nextLine();
                                if(researchInput.equals("2"))
                                {
                                    searchAgain = false;
                                }
                            }
                    } else if (searchInput.equals("6")) {
                        searchInput = "6";
                    } else {
                        System.out.println(RETRY);
                    }
                }

            } else if (homeInput.equals("2")) {
                {
                    String createInput = "";
                    while (!createInput.equals("5")) {
                        System.out.println("1 - Recipe\n2 - Ingredient\n3 - WeeklyPlan\n4 - RecipeList\n5 - Back");
                        createInput = sc.nextLine();
                        if (createInput.equals("1")) {

                        } else if (createInput.equals("2")) {
                            String backIngredient = "";
                            while(!backIngredient.equals("back"))
                            {
                                System.out.println("Create an Ingredient:");
                                System.out.println("Please enter a Name for the ingredient or type back to return to the menu: ");
                                String ingredientName = sc.nextLine();
                                if(ingredientName.equals("back")){
                                    backIngredient = ingredientName;
                                }
                                else{
                                System.out.println("Please enter a number quantity of your ingredient: ");
                                float quantity = sc.nextFloat();
                                while(quantity>Float.MAX_VALUE || quantity<Float.MIN_VALUE){
                                    System.out.println("Your number is either to large or to small");
                                    System.out.println("Please enter try again: ");
                                    quantity = sc.nextFloat();
                                }
                                System.out.println(
                                    "Please enter a measure for the quantity of your ingredients(Cups, grams, ect...): ");
                                    String measure = sc.nextLine();
                                System.out.println("Please enter a weight for your ingredient");
                                    float weight = sc.nextFloat();
                                while(weight>Float.MAX_VALUE || weight<Float.MIN_VALUE){
                                    System.out.println("Your number is either to large or to small");
                                    System.out.println("Please enter try again: ");
                                    weight = sc.nextFloat();
                                }
                                
                                userPantry.getIngredientList().add(userPantry.createIngredient(ingredientName, quantity, measure, weight));
                                System.out.println(ingredientName + " has been added to the pantry");
                                backIngredient = "back";
                                }
                            }
                        } else if (createInput.equals("3")) {
                            
                            String backWeeklyPlan = "";
                            while (!backWeeklyPlan.equals("back")) {
                                System.out.println("Welcome to create a weekly plan");
                                System.out.println("Please name your weekly plan or type back to return to the menu:");
                                String weeklyPlanName = sc.nextLine();
                                if(weeklyPlanName.equals("back")){
                                    backWeeklyPlan = weeklyPlanName;
                                }
                                else{
                                    WeeklyPlan newWeeklyPlan = new WeeklyPlan();
                                    newWeeklyPlan.setName(weeklyPlanName);
                                    arrayOfWeeklyPlans.add(newWeeklyPlan);
                                    System.out.println("Your weekly plan called " + weeklyPlanName + " has been created");
                                    backWeeklyPlan = "back";
                                }
                            }
                        } else if (createInput.equals("4")) {

                        } else if (createInput.equals("5")) {
                            createInput = "5";
                        } else {
                            System.out.println(RETRY);
                        }
                    }
                }
            } else if (homeInput.equals("3")) {
                String deleteInput = "";
                while (!deleteInput.equals("3")) {
                    System.out.println("Type:\n 1 - recipe\n 2 - weeklyPlan\n 3 - back\n");
                    deleteInput = sc.nextLine();
                    if (deleteInput.equals("1")) {
                        // do something
                    } else if (deleteInput.equals("2")) {
                        // do something
                    } else if (deleteInput.equals("3")) {
                        deleteInput = "3";
                    } else {
                        System.out.println(RETRY);
                    }

                }

            } else if (homeInput.equals("4")) {
                String updateInput = "";
                while (!updateInput.equals("3")) {
                    System.out.println("1 - Recipe\n2 - Weekly Plan\n3 - Back");
                    updateInput = sc.nextLine();
                    if (updateInput.equals("1")) {

                    } else if (updateInput.equals("2")) {

                    } else if (updateInput.equals("3")) {
                        updateInput = "3";
                    } else {
                        System.out.println(RETRY);
                    }
                }
            } else if (homeInput.equals("5")) {
                String addInput = "";
                while (!addInput.equals("3")) {
                    System.out.println("1 - Recipe List\n2 - Weekly Plan\n3 - Back");
                    addInput = sc.nextLine();
                    if (addInput.equals("1")) {

                    } else if (addInput.equals("2")) {

                    } else if (addInput.equals("3")) {
                        addInput = "3";
                    } else {
                        System.out.println(RETRY);
                    }
                }
            } else if (homeInput.equals("6")) {
                String removeInput = "";
                while (!removeInput.equals("3")) {
                    System.out.println("1 - Recipe List\n2 - Pantry\n3 - Back");
                    removeInput = sc.nextLine();
                    if (removeInput.equals("1")) {

                    } else if (removeInput.equals("2")) {

                    } else if (removeInput.equals("3")) {
                        removeInput = "3";
                    } else {
                        System.out.println(RETRY);
                    }
                }
            } else if (homeInput.equals("7")) {
                String viewInput = "";
                while (!viewInput.equals("6")) {
                    System.out
                            .println("1 - Recipe List\n2 - Pantry\n3 - Weekly Plan\n4 - Recipe\n5 - History\n6 - Back");
                    viewInput = sc.nextLine();
                    if (viewInput.equals("1")) {
                        
                    } else if (viewInput.equals("2")) {

                    } else if (viewInput.equals("3")) {

                    } else if (viewInput.equals("4")) {

                    } else if (viewInput.equals("5")) {

                    } else if (viewInput.equals("6")) {
                        viewInput = "6";
                    } else {
                        System.out.println(RETRY);
                    }
                }

            } else if (homeInput.equals("8")){
                String rateInput = "";
                while (!rateInput.equals("2")) {
                    System.out.println("1 - Recipe\n2 - Back");
                    rateInput = sc.nextLine();
                    if (rateInput.equals("1")) {
                        //find the one to rate
                    } else if (rateInput.equals("2")) {
                        rateInput = "6";
                    } else {
                        System.out.println(RETRY);
                    }
                }

            } else if (homeInput.equals("9")){
                //convert
                String convertInput = "";
                while (!convertInput.equals("2")) {
                    System.out.println("1 - Recipe\n2 - Back");
                    convertInput = sc.nextLine();
                    if (convertInput.equals("1")) {
                        //find the one to rate
                    } else if (convertInput.equals("2")) {
                        convertInput = "6";
                    } else {
                        System.out.println(RETRY);
                    }
                }
                
            } else if (homeInput.equals("10")){
                String shareInput = "";
                while (!shareInput.equals("3")) {
                    System.out
                            .println("1 - Recipe\n2 - Weekly Plan\n3 - Back");
                    shareInput = sc.nextLine();
                    if (shareInput.equals("1")) {

                    } else if (shareInput.equals("2")) {

                    } else if (shareInput.equals("3")) {
                        shareInput = "3";
                    } else {
                        System.out.println(RETRY);
                    }
                }
                
            } else if (homeInput.equals("11")){
                
            }
            
                

                
                   
        }
    }
}
