package com.chef_mark;

import java.util.ArrayList;

public class Recipe {
    String uri;
    String name;
    String source;
    String url;
    ArrayList<Ingredient> ingredients;
    int totalWeight;
    RecipeDescriptors recipeDescriptors;
    Rating rating;
    Instructions instructions;
    NutritionalFacts nutritionalFacts;

    public Recipe(String uri, String name, String source, String url, ArrayList<Ingredient> ingredients,
            int totalWeight, RecipeDescriptors recipeDescriptors, Rating rating, Instructions instructions,
            NutritionalFacts nutritionalFacts) {
        this.uri = uri;
        this.name = name;
        this.source = source;
        this.url = url;
        this.ingredients = ingredients;
        this.totalWeight = totalWeight;
        this.recipeDescriptors = recipeDescriptors;
        this.rating = rating;
        this.instructions = instructions;
        this.nutritionalFacts = nutritionalFacts;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getURL() {
        return url;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public RecipeDescriptors getRecipeDescritpors() {
        return recipeDescriptors;
    }

    public Rating getRating() {
        return rating;
    }

    public Instructions getInstructions() {
        return instructions;
    }

    public NutritionalFacts getNutritionalFacts() {
        return nutritionalFacts;
    }
}
