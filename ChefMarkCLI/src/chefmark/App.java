package chefmark;



import java.util.Scanner;
import java.sql.ResultSet;
import java.util.ArrayList;

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
                    currentUser = sc.nextLine();
                    System.out.println("Enter your password: ");
                    password = sc.nextLine();
            
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
                    System.out.println("You are now logged in. Please save your login info as this cannot be retrieved.");
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

                    } else if (searchInput.equals("5")) {

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
                            String back = "";
                            while(!back.equals("back"))
                            {
                                System.out.println("Create an Ingredient:");
                                System.out.println("Please enter a Name for the ingredient: ");
                                String ingredientName = sc.nextLine();
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
                                //ingredi.createIngredient(ingredientName, quantity, measure, weight);
                            
                                
                                
                            }
                        } else if (createInput.equals("3")) {
                            //@author 
                            String back = "";
                            while (!back.equals("back")) {
                                System.out.println("");
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
