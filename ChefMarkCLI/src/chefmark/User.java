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

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
