package chefmark;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UserController {
    private static Pattern usernameRegex = Pattern
    .compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$"); // username
private static Pattern passwordRegex = Pattern
    .compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=[^\r\n\t\f ]+$).{8,}$"); // password

    private User user;

    public static Pattern getUsernameRegex() {
        return usernameRegex;
    }

    public static Pattern getPasswordRegex() {
        return passwordRegex;
    }

    public void initializePantry(DBQuery dbq) throws SQLException {
        ResultSet rs = dbq.read(user.getPantry(), this.user);
        while (rs.next()) {
            user.getPantry().addIngredient(new Ingredient(rs.getString(1), "", Float.parseFloat(rs.getString(2)), rs.getString(3)));
        }
    }

    public void initializeCustomRecipes(DBQuery dbq) throws SQLException {
        ResultSet rs = dbq.populateCustomRecipe(new Recipe(), this.user);
        while (rs.next()) {
            user.getCustomRecipeList().add(new Recipe(rs.getString(1), rs.getString(2), rs.getString(3), RecipeController.textToIngredientList(rs.getString(4)), new NutritionalFacts(RecipeController.textToArrayList(rs.getString(5)), RecipeController.textToArrayList(rs.getString(6)), rs.getInt(7)), new Instructions(RecipeController.textToArrayList(rs.getString(8)), RecipeController.textToArrayList(rs.getString(9)))));
        }
    }
    //String query = "SELECT recipeName, url, source, ingredients,                                                                                                                                                                                                        totalWeight, dietLabels, healthLabels, calories, glycemicIndex, yield, instructions, cautions, username FROM RECIPELIST WHERE USERNAME=?";
        
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

            // public Recipe(String recipeName, String url, String source, ArrayList<Ingredient> ingredients, NutritionalFacts nutritionalFacts, Instructions instructions) {
            //     this.name=recipeName;
            //     this.source=source;
            //     this.ingredients=ingredients;
            //     this.nutritionalFacts=nutritionalFacts;
            //     this.instructions=instructions;
            // }
            //select distinct recipeName, url, source, ingredients, dietLabels, healthLabels, calories, instructions, cautions, recipe.username from recipe,weeklyplan,weeklyplanitem where recipe.username=weeklyplan.username and weeklyplan.weeklyPlanid=weeklyplanitem.weeklyplanid and recipe.username='" + user.getUsername() + "' and dayOfWeek='" + day + "' and recipe.recipeID=weeklyplanitem.recipeid and weeklyPlan.name='" + weeklyPlan.getName() + "'

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

    public boolean createUser(String username, String password, String email) {
        //if (check if username and email already exist) -- return true if successfully created, false if otherwise
        this.user = new User(username, password, email);
        return true;
    }

    public boolean changePassword(String password) {
        //check if password is valid, false if it can't be changed
        this.user.setPassword(password);
        return true;
    }
    
    public boolean changeEmail(String email) {
        //check if email is valid, false if it can't be changed
        this.user.setEmail(email);
        return true;
    }

    public boolean deleteUser(DBQuery dbq, Scanner sc) throws SQLException {
        System.out.println("\nThis action will delete your account. Are you sure you want to continue? y/n");
        String testInput = sc.nextLine();
        if (testInput.equals("y")) {
            System.out.println("Delete process continuing.");

            System.out.println("Enter your username: ");
            String username = validateUsername(sc);

            System.out.println("Enter your password: ");

            String password = validatePassword(sc);

            // Matcher passwordMatch = passwordRegex.matcher(testInput);
            // while (!(passwordMatch.matches())) {
            //     System.out.println("Password is in incorrect format. Passwords must be at least 8 characters, include 1 uppercase, 1 lowercase, 1 number, and 1 special character");
            //     System.out.println("\nEnter your password: ");
            //     testInput = sc.nextLine();
            //     passwordMatch = passwordRegex.matcher(testInput);
            // }
            // password = testInput;

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
    

    public User getUser() {
        return this.user;
    }

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
                    email = sc.nextLine();
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

    public boolean signUp(DBQuery dbq, Scanner sc) throws SQLException {
        System.out.println("Enter your desired username: ");
        String username = validateUsername(sc);
        System.out.println("Enter your desired password: ");
        String password = validatePassword(sc);
        System.out.println("Enter your desired email: ");
        String email = sc.nextLine();
        ResultSet rs = dbq.read(new User(username, password, email));
        if (rs.next()) {
            while (username.equals(rs.getString(1))) {
                System.out.println("Username already taken. Please enter a new username: ");
                username = validateUsername(sc);
                //uc.getUser().setUser(username);
            }
            while (email.equals(rs.getString(3))) {
                System.out.println("Email already taken. Please enter a new email: ");
                email = sc.nextLine();
            }

        }
        createUser(username, password, email);
        dbq.create(this.user);
        System.out.println("You are now logged in. Please save your login info as this cannot be retrieved.");
        return true;
    }

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
}
