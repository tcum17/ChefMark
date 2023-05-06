package chefmark;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email = "";
    private ArrayList<Recipe> recipeHistory = new ArrayList<>();
    private Pantry pantry = new Pantry();
    private ArrayList<WeeklyPlan> weeklyPlans = new ArrayList<>();
    private ArrayList<RecipeList> recipeLists = new ArrayList<>();
    private ArrayList<Recipe> customRecipes = new ArrayList<>();

    private static final int MAX_RECIPE_HISTORY_SIZE = 20;

    /**
     * User constructor
     * @param username Username to be used
     * @param password Password to be used
     * @param email Email to be used
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Returns the user's username
     * @return User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's password
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's email
     * @return User's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user's recipe history
     * @return User's recipe history
     */
    public ArrayList<Recipe> getRecipeHistory() {
        return recipeHistory;
    }

    /**
     * Returns the user's pantry
     * @return User's pantry
     */
    public Pantry getPantry() {
        return pantry;
    }

    /**
     * Returns the user's weekly plans
     * @return User's weekly plans
     */
    public ArrayList<WeeklyPlan> getWeeklyPlans() {
        return weeklyPlans;
    }

    /**
     * Returns the user's recipe lists
     * @return User's recipe lists
     */
    public ArrayList<RecipeList> getRecipeLists() {
        return recipeLists;
    }

    /**
     * Sets the user's password
     * @param password Password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the user's email
     * @param email Email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the user's username
     * @param username Username to be set
     */
    public void setUser(String username) {
        this.username = username;
    }

    /**
     * Sets the user's weekly plans
     * @param weeklyPlans The weekly plans to be set
     */
    public void setWeeklyPlans(ArrayList<WeeklyPlan> weeklyPlans)
    {
        this.weeklyPlans = weeklyPlans;
    }

    /**
     * Gets the recipe list by its name
     * @param listName The recipe list name to be located
     * @return The returned recipe list
     */
    public RecipeList getRecipeListByName(String listName) {
        for (int i = 0; i < recipeLists.size(); i++) {
            if (recipeLists.get(i).getName().equals(listName)) {
                return recipeLists.get(i);
            }
        }
        return null;
    }

    /**
     * Returns the custom recipe list
     * @return Custom recipe list
     */
    public ArrayList<Recipe> getCustomRecipeList() {
        return this.customRecipes;
    }

    /**
     * Returns the WeeklyPlan object if found
     * @param planName Name of weekly plan to be found
     * @return The plan- null if not found
     */
    public WeeklyPlan getWeeklyPlanByName(String planName) {
        for (int i = 0; i < weeklyPlans.size(); i++) {
            if (weeklyPlans.get(i).getName().equals(planName)) {
                return weeklyPlans.get(i);
            }
        }
        return null;
    }

    /**
     * Removes a recipe list
     * @param list The recipe list to be removed
     * @return True if removed, false if otherwise
     */
    public boolean removeRecipeList(RecipeList list) {
        if (recipeLists.contains(list)) {
            recipeLists.remove(list);
            return true;
        }
        return false;
    }

    /**
     * Adds a custom recipe
     * @param recipe The recipe to be added
     */
    public void addCustomRecipe(Recipe recipe)
    {
        customRecipes.add(recipe);
    }

    /**
     * Removes from a weekly plan
     * @param plan Plan to be removed
     * @return True if removed, false otherwise
     */
    public boolean removeWeeklyPlan(WeeklyPlan plan) {
        if (weeklyPlans.contains(plan)) {
            weeklyPlans.remove(plan);
            return true;
        }
        return false;
    }

    /**
     * Shows the recipe lists
     * @return Integer index
     */
    public int showRecipeLists() {
        System.out.println("Recipe Lists");
        System.out.println("============================");
        System.out.println();
        if (recipeLists.size() == 0) {
            System.out.println("You have no recipe lists...");
            return 0;
        }
        else {
            int counter=1;
            for (int i = 0; i < recipeLists.size(); i++) {
                System.out.println(recipeLists.get(i).getName());
                counter++;
            }
            return counter;
        }
    }

    /**
     * Shows the weekly plans
     * @return The integer index
     */
    public int showWeeklyPlans() {
        System.out.println("Weekly Plans");
        System.out.println("============================");
        System.out.println();
        if (weeklyPlans.size() == 0) {
            System.out.println("You have no weekly plans...");
            return 0;
        }
        else {
            int counter=1;
            for (int i = 0; i < weeklyPlans.size(); i++) {
                System.out.println(weeklyPlans.get(i).getName());
                counter++;
            }
            return counter;
        }
        
    }

    /**
     * Shows the custom recipes
     * @return Integer index
     */
    public int showCustomRecipes()
    {
        System.out.println("Custom Recipes");
        System.out.println("============================");
        System.out.println();
        if (customRecipes.size() == 0) {
            System.out.println("You have no custom recipes...");
            return 0;
        }
        else {
            int counter=1;
            for (int i = 1; i < customRecipes.size()-1; i++) {
                System.out.println(i+"- " + customRecipes.get(i).getName());
                counter++;
            }
            return counter;
        }
    }

    /**
     * Adds a weekly plan to the user
     * @param weeklyPlan The weekly plan
     */
    public void addWeeklyPlan(WeeklyPlan weeklyPlan)
    {
        weeklyPlans.add(weeklyPlan);
    }

    /**
     * Adds a recipe to the list of recipes
     * @param recipeList The list of recipes
     */
    public void addListOfRecipies(RecipeList recipeList)
    {
        recipeLists.add(recipeList);
    }

    /**
     * Adds to the recipe history
     * @param recipe The recipe to be added
     */
    public void addToRecipeHistory(Recipe recipe){
        boolean alreadyExists = false;
        for (int i = 0; i < recipeHistory.size(); i++){
            Recipe curRecipe = recipeHistory.get(i);
            if (curRecipe.getName().equals(recipe.getName())) {
                
                alreadyExists=true;
                recipeHistory.remove(i);
                addToRecipeHistory(recipe);
                break;
            }
        }
        if (alreadyExists==false) {
            recipeHistory.add(0, recipe);
            if(recipeHistory.size() > MAX_RECIPE_HISTORY_SIZE){
                recipeHistory.remove(recipeHistory.size()-1);
            }
        }
    }
}
