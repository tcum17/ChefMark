package com.chef_mark;

import java.util.ArrayList;

public class RecipeDescriptors {
    private ArrayList<String> tags;
    private ArrayList<String> dishType;
    private ArrayList<String> mealType;
    private ArrayList<String> cuisineType;

    public RecipeDescriptors() {
    }

    public RecipeDescriptors(ArrayList<String> tags, ArrayList<String> dishType, ArrayList<String> mealType,
            ArrayList<String> cuisineType) {
        this.tags = tags;
        this.dishType = dishType;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
    }

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
