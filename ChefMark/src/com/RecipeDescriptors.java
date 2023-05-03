package com;


import java.util.ArrayList;

public class RecipeDescriptors {
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<String> dishType = new ArrayList<>();
    private ArrayList<String> mealType = new ArrayList<>();
    private ArrayList<String> cuisineType = new ArrayList<>();

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

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setDishType(ArrayList<String> dishType) {
        this.dishType = dishType;
    }

    public void setMealType(ArrayList<String> mealType) {
        this.mealType = mealType;
    }

    public void setCuisineType(ArrayList<String> cuisineType) {
        this.cuisineType = cuisineType;
    }
}
