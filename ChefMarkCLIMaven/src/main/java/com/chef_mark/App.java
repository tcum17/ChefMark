package com.chef_mark;

import java.util.Scanner;

public class App {
    private static final String RETRY = "Your answer did not match any of our options. Please reenter";

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Scanner sc = new Scanner(System.in);
        String currentUser;
        String password;
        DBConnection db = new DBConnection(); // mySQL
        UserController uc = new UserController();

        System.out.println("Type \"Exit\" to quit the program");
        System.out.println("Enter your username: ");
        currentUser = sc.nextLine();
        System.out.println("Enter your password: ");
        password = sc.nextLine();
        uc.login(currentUser, password, db);

        String exit = sc.nextLine();

        if (exit.equals("Exit")) {
            System.exit(0);
        }
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
                    /**
                     * LOGIN
                     * 
                     * Query database passing in email and password
                     * If doesn't exist, tell user, show original login/signup prompt
                     * If password incorrect, tell user, reprompt password field
                     * Otherwise, enter into main application
                     */
                    signedIn = true;
                } else if (startScreenInput.equals("signup")) {
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

                    } else if (searchInput.equals("2")) {

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

                        } else if (createInput.equals("3")) {

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

            } else if (homeInput.equals("9")){
                
            } else if (homeInput.equals("10")){
                
            } else if (homeInput.equals("11")){
                
            }
            
                

                
                   
        }
    }
}
