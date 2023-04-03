package chefmark;

import java.util.ArrayList;

public class RecipeDescriptors {
    private ArrayList<String> tags;
    private ArrayList<String> dishType;
    private ArrayList<String> mealType;
    private ArrayList<String> cuisineType;

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public ArrayList<String> getDishType() {
        return this.dishType;
    }

    public ArrayList<String> getMealType() {
        return this.mealType;
    }

    public ArrayList<String> getCuisineType() {
        return this.cuisineType;
    }
}
