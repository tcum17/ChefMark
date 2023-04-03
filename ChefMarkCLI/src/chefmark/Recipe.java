package chefmark;

import java.util.ArrayList;

public class Recipe {
    string uri;
    string name;
    string source;
    string url;
    Ingredient[] ingredients;
    int totalWeight;
    RecipeDescriptors recipeDescriptors;
    Rating rating;
    Instructions instructions;
    NutritionalFacts nutritionalFacts;

    public recipe(string uri, string name, string source, string url, Ingredient[] ingredients, int totalWeight, RecipeDescriptors recipeDescriptors, Rating rating, Instructions instructions, NutritionalFacts nutritionalFacts){
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

    
}
