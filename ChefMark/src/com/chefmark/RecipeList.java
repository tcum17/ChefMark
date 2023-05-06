package chefmark;

import java.util.ArrayList;

public class RecipeList {
    private String name;
    private ArrayList<Recipe> recipeList = new ArrayList<>();

    /**
     * base constructor
     */
    public RecipeList() {
        this.recipeList = new ArrayList<Recipe>();
    }

    /**
     * constructor for recipelist with a name
     * @param name
     */
    public RecipeList(String name) {
        this.name=name;
        this.recipeList = new ArrayList<Recipe>();
    }

    /**
     * constructor for recipelist with an arraylist parameter
     * @param recipeList
     */
    public RecipeList(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @param recipeList
     */
    public void setRecipeList(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return
     */
    public ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }

    /**
     * Adds recipe to recipe list
     * @param recipe
     */
    public void addRecipeToRecipeList(Recipe recipe) {
        recipeList.add(recipe);
    }

    /**
     * Removes recipe from list
     * @param recipe
     * @return returns result of ability to remove recipe
     */
    public boolean removeRecipeFromRecipeList(Recipe recipe) {
        if (recipeList.contains(recipe)) {
            recipeList.remove(recipe);
            return true;
        }
        return false;
    }

    /**
     * prints the information in the recipe list
     */
    public void info() {
        System.out.println(name);
        System.out.println("Recipes:");
        if (recipeList.size() != 0) {
            for (int i = 0; i < recipeList.size(); i++) {
                System.out.println(recipeList.get(i).getName() + "\n");
            }
            System.out.println();
        } else {
            System.out.println("You have no recipes...\n");
        }
        
    }

    /**
     * returns a recipe in the list if it is there
     * @param recipeName
     * @return null or the recipe that is being searched for
     */
    public Recipe getRecipeByName(String recipeName) {
        for (int i = 0; i < recipeList.size(); i++) {
            if (recipeList.get(i).getName().equals(recipeName)) {
                return recipeList.get(i);
            }
        }
        return null;
    } 
}