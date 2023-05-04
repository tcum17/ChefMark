
import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email = "";
    private ArrayList<Recipe> favoriteRecipes = new ArrayList<>();
    private ArrayList<Recipe> recipeHistory = new ArrayList<>();
    private Pantry pantry = new Pantry();
    private ArrayList<WeeklyPlan> weeklyPlans = new ArrayList<>();
    private ArrayList<RecipeList> recipeLists = new ArrayList<>();
    private ArrayList<Recipe> customRecipes = new ArrayList<>();

    private static final int MAX_RECIPE_HISTORY_SIZE = 20;

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

    public ArrayList<Recipe> getRecipeHistory() {
        return recipeHistory;
    }

    public Pantry getPantry() {
        return pantry;
    }

    public ArrayList<WeeklyPlan> getWeeklyPlans() {
        return weeklyPlans;
    }

    public ArrayList<RecipeList> getRecipeLists() {
        return recipeLists;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public void setFavorites(ArrayList<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }

    public void setWeeklyPlans(ArrayList<WeeklyPlan> weeklyPlans)
    {
        this.weeklyPlans = weeklyPlans;
    }

    public RecipeList getRecipeListByName(String listName) {
        for (int i = 0; i < recipeLists.size(); i++) {
            if (recipeLists.get(i).getName().equals(listName)) {
                return recipeLists.get(i);
            }
        }
        return null;
    }

    public ArrayList<Recipe> getCustomRecipeList() {
        return this.customRecipes;
    }

    public WeeklyPlan getWeeklyPlanByName(String planName) {
        for (int i = 0; i < weeklyPlans.size(); i++) {
            if (weeklyPlans.get(i).getName().equals(planName)) {
                return weeklyPlans.get(i);
            }
        }
        return null;
    }

    public boolean removeRecipeList(RecipeList list) {
        if (recipeLists.contains(list)) {
            recipeLists.remove(list);
            return true;
        }
        return false;
    }

    public void addCustomRecipe(Recipe recipe)
    {
        customRecipes.add(recipe);
    }

    public boolean removeWeeklyPlan(WeeklyPlan plan) {
        if (weeklyPlans.contains(plan)) {
            weeklyPlans.remove(plan);
            return true;
        }
        return false;
    }

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


    public void addWeeklyPlan(WeeklyPlan weeklyPlan)
    {
        weeklyPlans.add(weeklyPlan);
    }

    public void addListOfRecipies(RecipeList recipeList)
    {
        recipeLists.add(recipeList);
    }

    public void addToRecipeHistory(Recipe recipe){
        recipeHistory.add(0, recipe);
        if(recipeHistory.size() > MAX_RECIPE_HISTORY_SIZE){
            recipeHistory.remove(recipeHistory.size()-1);
        }
    }

    public void addToFavoriteRecipes(Recipe recipe){

        for(int i = 0; i < favoriteRecipes.size(); i++)
        {
            if(recipe.name.equalsIgnoreCase(favoriteRecipes.get(i).getName()))
            {
                return;
            }
        }
        favoriteRecipes.add(recipe);
    }
}
