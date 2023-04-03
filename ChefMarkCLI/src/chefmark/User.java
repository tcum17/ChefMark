package chefmark;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private String email;
    private ArrayList<Recipe> favoriteRecipes;
    private HashMap<String, Recipe> recipeHistory;
    private Pantry pantry;
    private WeeklyPlan weeklyPlan;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public HashMap<String, Recipe> getRecipeHistory() {
        return recipeHistory;
    }

    public Pantry getPantry() {
        return pantry;
    }

    public WeeklyPlan getWeeklyPlan() {
        return weeklyPlan;
    }
}
