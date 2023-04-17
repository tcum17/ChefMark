package chefmark;

import java.util.ArrayList;

public class RecipeList {
    private ArrayList<Recipe> recipes;
    private String name;

    public RecipeList() {
    }

    public RecipeList(String name) {
        this.name = name;
    }

    public ArrayList<Recipe> getRecipes() {
        return this.recipes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRecipeToRecipeList(Recipe recipe) {
        this.recipes.add(recipe);
    }

    public boolean removeRecipeFromRecipeList(Recipe recipe) {
        return this.recipes.remove(recipe);
    }

}
