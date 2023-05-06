package chefmark;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UserController {
    private static Pattern usernameRegex = Pattern
    .compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$"); // username
private static Pattern passwordRegex = Pattern
    .compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=[^\r\n\t\f ]+$).{8,}$"); // password
private static Pattern emailRegex = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$"); // email

    private User user;

    /**
     * 
     * @return
     */
    public static Pattern getUsernameRegex() {
        return usernameRegex;
    }

    /**
     * 
     * @return
     */
    public static Pattern getPasswordRegex() {
        return passwordRegex;
    }

    /**
     * creates the pantry in the database
     * @param dbq
     * @throws SQLException
     */
    public void initializePantry(DBQuery dbq) throws SQLException {
        ResultSet rs = dbq.read(user.getPantry(), this.user);
        while (rs.next()) {
            user.getPantry().addIngredient(new Ingredient(rs.getString(1), "", Float.parseFloat(rs.getString(2)), rs.getString(3)));
        }
    }

    /**
     * creates the users custom recipe list in data base
     * @param dbq
     * @throws SQLException
     */
    public void initializeCustomRecipes(DBQuery dbq) throws SQLException {
        ResultSet rs = dbq.populateCustomRecipe(new Recipe(), this.user);
        while (rs.next()) {
            user.getCustomRecipeList().add(new Recipe(rs.getString(1), rs.getString(2), rs.getString(3), RecipeController.textToIngredientList(rs.getString(4)), new NutritionalFacts(RecipeController.textToArrayList(rs.getString(5)), RecipeController.textToArrayList(rs.getString(6)), rs.getInt(7)), new Instructions(RecipeController.textToArrayList(rs.getString(8)), RecipeController.textToArrayList(rs.getString(9)))));
        }
    }
   
    /**
     * creates  the users recipe lists in database
     * @param dbq
     * @throws SQLException
     */
    public void initializeRecipeLists(DBQuery dbq) throws SQLException {
        ResultSet rs = dbq.populateRecipeList(new RecipeList(), this.user);
        while (rs.next()) {
            user.getRecipeLists().add(new RecipeList(rs.getString(1)));
        }

        for (int i = 0; i < user.getRecipeLists().size(); i++) {
            rs = dbq.getRecipeLists(user.getRecipeLists().get(i), user);
            RecipeList recipeList = user.getRecipeLists().get(i);
            while (rs.next()) {
                recipeList.addRecipeToRecipeList(new Recipe(rs.getString(1), rs.getString(2), rs.getString(3), RecipeController.textToIngredientList(rs.getString(4)), new NutritionalFacts(RecipeController.textToArrayList(rs.getString(5)), RecipeController.textToArrayList(rs.getString(6)), rs.getInt(7)), new Instructions(RecipeController.textToArrayList(rs.getString(8)), RecipeController.textToArrayList(rs.getString(9)))));
            }
        }
    }

    /**
     * creates  the users weekly plan in database
     * @param dbq
     * @throws SQLException
     */
    public void initializeWeeklyPlans(DBQuery dbq) throws SQLException {
        ResultSet rs = dbq.populateWeeklyPlan(new WeeklyPlan(), this.user);
        while (rs.next())
        {
            String weeklyPlanName = rs.getString(2);
            String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
            while (weeklyPlanName!=null) {
                this.user.addWeeklyPlan(new WeeklyPlan(weeklyPlanName));
                if (rs.next()) {
                    weeklyPlanName=rs.getString(2);
                } else {
                    weeklyPlanName=null;
                }
            }

            for (int i = 0; i < this.user.getWeeklyPlans().size(); i++) {
                rs = dbq.read(this.user.getWeeklyPlans().get(i), this.user, daysOfWeek[6]);
                WeeklyPlan wp = this.user.getWeeklyPlans().get(i);
                while (rs.next()) {
                    //WeeklyPlan wp = this.user.getWeeklyPlans().get(i);
                    wp.addRecipeToWeeklyPlan(new Recipe(rs.getString(1), rs.getString(2), rs.getString(3), RecipeController.textToIngredientList(rs.getString(4)), new NutritionalFacts(RecipeController.textToArrayList(rs.getString(5)), RecipeController.textToArrayList(rs.getString(6)), rs.getInt(7)), new Instructions(RecipeController.textToArrayList(rs.getString(8)), RecipeController.textToArrayList(rs.getString(9)))), daysOfWeek[6]);
                }
                rs = dbq.read(this.user.getWeeklyPlans().get(i), this.user, daysOfWeek[0]);
                while (rs.next()) {
                    //WeeklyPlan wp = this.user.getWeeklyPlans().get(i);
                    wp.addRecipeToWeeklyPlan(new Recipe(rs.getString(1), rs.getString(2), rs.getString(3), RecipeController.textToIngredientList(rs.getString(4)), new NutritionalFacts(RecipeController.textToArrayList(rs.getString(5)), RecipeController.textToArrayList(rs.getString(6)), rs.getInt(7)), new Instructions(RecipeController.textToArrayList(rs.getString(8)), RecipeController.textToArrayList(rs.getString(9)))), daysOfWeek[0]);
                }
                rs = dbq.read(this.user.getWeeklyPlans().get(i), this.user, daysOfWeek[1]);
                while (rs.next()) {
                    //WeeklyPlan wp = this.user.getWeeklyPlans().get(i);
                    wp.addRecipeToWeeklyPlan(new Recipe(rs.getString(1), rs.getString(2), rs.getString(3), RecipeController.textToIngredientList(rs.getString(4)), new NutritionalFacts(RecipeController.textToArrayList(rs.getString(5)), RecipeController.textToArrayList(rs.getString(6)), rs.getInt(7)), new Instructions(RecipeController.textToArrayList(rs.getString(8)), RecipeController.textToArrayList(rs.getString(9)))), daysOfWeek[1]);
                }
                rs = dbq.read(this.user.getWeeklyPlans().get(i), this.user, daysOfWeek[2]);
                while (rs.next()) {
                    //WeeklyPlan wp = this.user.getWeeklyPlans().get(i);
                    wp.addRecipeToWeeklyPlan(new Recipe(rs.getString(1), rs.getString(2), rs.getString(3), RecipeController.textToIngredientList(rs.getString(4)), new NutritionalFacts(RecipeController.textToArrayList(rs.getString(5)), RecipeController.textToArrayList(rs.getString(6)), rs.getInt(7)), new Instructions(RecipeController.textToArrayList(rs.getString(8)), RecipeController.textToArrayList(rs.getString(9)))), daysOfWeek[2]);
                }
                rs = dbq.read(this.user.getWeeklyPlans().get(i), this.user, daysOfWeek[3]);
                while (rs.next()) {
                    //WeeklyPlan wp = this.user.getWeeklyPlans().get(i);
                    wp.addRecipeToWeeklyPlan(new Recipe(rs.getString(1), rs.getString(2), rs.getString(3), RecipeController.textToIngredientList(rs.getString(4)), new NutritionalFacts(RecipeController.textToArrayList(rs.getString(5)), RecipeController.textToArrayList(rs.getString(6)), rs.getInt(7)), new Instructions(RecipeController.textToArrayList(rs.getString(8)), RecipeController.textToArrayList(rs.getString(9)))), daysOfWeek[3]);
                }
                rs = dbq.read(this.user.getWeeklyPlans().get(i), this.user, daysOfWeek[4]);
                while (rs.next()) {
                    //WeeklyPlan wp = this.user.getWeeklyPlans().get(i);
                    wp.addRecipeToWeeklyPlan(new Recipe(rs.getString(1), rs.getString(2), rs.getString(3), RecipeController.textToIngredientList(rs.getString(4)), new NutritionalFacts(RecipeController.textToArrayList(rs.getString(5)), RecipeController.textToArrayList(rs.getString(6)), rs.getInt(7)), new Instructions(RecipeController.textToArrayList(rs.getString(8)), RecipeController.textToArrayList(rs.getString(9)))), daysOfWeek[4]);
                }
                rs = dbq.read(this.user.getWeeklyPlans().get(i), this.user, daysOfWeek[5]);
                while (rs.next()) {
                    //WeeklyPlan wp = this.user.getWeeklyPlans().get(i);
                    wp.addRecipeToWeeklyPlan(new Recipe(rs.getString(1), rs.getString(2), rs.getString(3), RecipeController.textToIngredientList(rs.getString(4)), new NutritionalFacts(RecipeController.textToArrayList(rs.getString(5)), RecipeController.textToArrayList(rs.getString(6)), rs.getInt(7)), new Instructions(RecipeController.textToArrayList(rs.getString(8)), RecipeController.textToArrayList(rs.getString(9)))), daysOfWeek[5]);
                }
            }
        }
    }
    
    /**
     * Creates a new user
     * @param username
     * @param password
     * @param email
     * @return boolean
     */
    public boolean createUser(String username, String password, String email) {
        //if (check if username and email already exist) -- return true if successfully created, false if otherwise
        this.user = new User(username, password, email);
        return true;
    }

    /**
     * changes the users password
     * @param password
     * @return boolean
     */
    public boolean changePassword(String password) {
        //check if password is valid, false if it can't be changed
        this.user.setPassword(password);
        return true;
    }
    
    /**
     * changes the users email
     * @param email
     * @return boolean
     */
    public boolean changeEmail(String email) {
        //check if email is valid, false if it can't be changed
        this.user.setEmail(email);
        return true;
    }

    /**
     * deletes the user from the database
     * @param dbq
     * @param sc
     * @return
     * @throws SQLException
     */
    public boolean deleteUser(DBQuery dbq, Scanner sc) throws SQLException {
        System.out.println("\nThis action will delete your account. Are you sure you want to continue? y/n");
        String testInput = sc.nextLine();
        if (testInput.equals("y")) {
            System.out.println("Delete process continuing.");

            System.out.println("Enter your username: ");
            String username = validateUsername(sc);

            System.out.println("Enter your password: ");

            String password = validatePassword(sc);

            ResultSet rs = dbq.read(new User(username, password, ""));
            if (rs.next()) {
                if (username.equals(rs.getString(1)) && password.equals(rs.getString(2))) {
                    System.out.println("Are you absolutely sure you want to delete your account? This action cannot be undone. y/n");
                    testInput = sc.nextLine();
                    if (testInput.equals("y")) {
                        System.out.println("Proceeding with user deletion.");
                        dbq.delete(new User(username, password, ""));
                        System.out.println("User " + username + " has been deleted. Sorry to see you go!");
                    } else {
                        System.out.println("User " + username + " will not be deleted.");
                    }
                } else {
                    System.out.println("User information does not match with database. Try again.");
                }
            } else {
                System.out.println("Database query unsuccessful. Try again later.");
            }
        } else {
            System.out.println("Deletion process halted.");
        }
        return false;
    }
    
    /**
     * 
     * @return
     */
    public User getUser() {
        return this.user;
    }

    /**
     * updates the user
     * @param dbq
     * @param sc
     */
    public boolean updateUser(DBQuery dbq, Scanner sc) throws SQLException {
        System.out.println("Enter your username: ");

        String username = validateUsername(sc);

        System.out.println("Enter your password: ");

        String password = validatePassword(sc);
        String email = "";
        ResultSet rs = dbq.read(new User(username, password, ""));
        if (rs.next()) {
            if (rs.getString(1).equals(username) && rs.getString(2).equals(password)) {
                createUser(username, password, rs.getString(3));
                email = rs.getString(3);
                if (email==null)
                    email = "";
                System.out.println("Do you want to update your password? (type no if not changing)");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("no")) {
                    
                }
                else {
                    System.out.println("Enter your new password");
                    password = validatePassword(sc);
                }
                System.out.println("Do you want to update your email? (type no if not changing)");
                input = sc.nextLine();
                if (input.equalsIgnoreCase("no")) {
                    
                }
                else {
                    System.out.println("Enter your new email");
                    email = validateEmail(sc);
                }
                user.setEmail(email);
                user.setPassword(password);
                dbq.update(user);
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * allow the user to login
     * @param dbq
     * @param sc
     * @return boolean
     * @throws SQLException
     */
    public boolean login(DBQuery dbq, Scanner sc) throws SQLException {
        System.out.println("Enter your username: ");

        String username = validateUsername(sc);

        System.out.println("Enter your password: ");

        String password = validatePassword(sc);
        ResultSet rs = dbq.read(new User(username, password, ""));
        if (rs.next()) {
            if (rs.getString(1).equals(username) && rs.getString(2).equals(password)) {
                System.out.println("\nLogin successful!\n");
                createUser(username, password, rs.getString(3)); // change name
                initializePantry(dbq);
                initializeWeeklyPlans(dbq);
                initializeCustomRecipes(dbq);
                initializeRecipeLists(dbq);
                return true;
            }
            else {
                System.out.println("\nUsername or password is incorrect. Do you have an account?"); // either username or password doesn't match
            }
        } else {
            System.out.println("\nUsername or password is incorrect. Do you have an account?"); // username and password don't exist (don't tell user)
        }
        return false;
    }

    /**
     * allows you to signup for email 
     * @param dbq
     * @param sc
     * @return boolean
     * @throws SQLException
     */
    public boolean signUp(DBQuery dbq, Scanner sc) throws SQLException {
        System.out.println("Enter your desired username: ");
        String username = validateUsername(sc);
        System.out.println("Enter your desired password: ");
        String password = validatePassword(sc);
        System.out.println("Enter your desired email: ");
        String email = sc.nextLine();
        ResultSet rs = dbq.read(new User(username, password, email));

        boolean next;
        if (rs.next())
            next=true;
        else {
            next=false;
        }
        if (next) {
            while (next==true) {
                System.out.println("Username already taken. Please enter a new username: ");
                username = validateUsername(sc);
                //uc.getUser().setUser(username);
                ResultSet rs2=dbq.read(new User(username, password, email));
                if (rs2.next())
                    next=true;
                else {
                    next=false;
                }
            }
            Matcher emailMatch = emailRegex.matcher(email);
            while (!(emailMatch.matches())) {
                System.out.println("Invalid email format. Please enter a new email: ");
                email = validateEmail(sc);
            }

        }
        createUser(username, password, email);
        dbq.create(this.user);
        System.out.println("You are now logged in. Please save your login info as this cannot be retrieved.");
        return true;
    }

    /**
     * validate the username and returns the username
     * @param sc 
     * @return String
     */
    public String validateUsername(Scanner sc) {
        String username = sc.nextLine();
        Matcher usernameMatch = usernameRegex.matcher(username);
        while (!(usernameMatch.matches())) {
            System.out.println("Username is in incorrect form. Usernames must be at least 6 characters");
            System.out.println("\nEnter your username: ");
            username = sc.nextLine();
            usernameMatch = usernameRegex.matcher(username);
        }
        return username;
    }

    /**
     * validates the password and returns the password
     * @param sc
     * @return String
     */
    public String validatePassword(Scanner sc) {
        String password = sc.nextLine();
        Matcher passwordMatch = passwordRegex.matcher(password);
        while (!(passwordMatch.matches())) {
            System.out.println(
                    "Password is in incorrect format. Passwords must be at least 8 characters, include 1 uppercase, 1 lowercase, 1 number, and 1 special character");
            System.out.println("\nEnter your password: ");
            password = sc.nextLine();
            passwordMatch = passwordRegex.matcher(password);
        }
        return password;
    }

    /**
     * validate the email and returns the email
     * @param sc
     * @return string
     */
    public String validateEmail(Scanner sc) {
        String email = sc.nextLine();
        Matcher emailMatch = emailRegex.matcher(email);
        while (!(emailMatch.matches())) {
            System.out.println(
                    "Email is in invalid form. Make sure you are properly formatting your @ and including the .<domain> (ex: chefmark@gmail.com)");
            System.out.println("\nEnter your email: ");
            email = sc.nextLine();
            emailMatch = emailRegex.matcher(email);
        }
        return email;
    }
}
