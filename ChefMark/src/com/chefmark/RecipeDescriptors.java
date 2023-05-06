package chefmark;

import java.util.ArrayList;

public class RecipeDescriptors {
    private ArrayList<String> tags = new ArrayList<>();
    private ArrayList<String> dishType = new ArrayList<>();
    private ArrayList<String> mealType = new ArrayList<>();
    private ArrayList<String> cuisineType = new ArrayList<>();

    /**
     * Empty constructor
     */
    public RecipeDescriptors() {
    }

    /**
     * Constructor for RecipeDescriptors
     * @param tags
     * @param dishType
     * @param mealType
     * @param cuisineType
     */
    public RecipeDescriptors(ArrayList<String> tags, ArrayList<String> dishType, ArrayList<String> mealType,
            ArrayList<String> cuisineType) {
        this.tags = tags;
        this.dishType = dishType;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getTags() {
        return this.tags;
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getDishType() {
        return this.dishType;
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getMealType() {
        return this.mealType;
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getCuisineType() {
        return this.cuisineType;
    }

    /**
     * 
     * @param tags
     */
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    /**
     * 
     * @param dishType
     */
    public void setDishType(ArrayList<String> dishType) {
        this.dishType = dishType;
    }

    /**
     * 
     * @param mealType
     */
    public void setMealType(ArrayList<String> mealType) {
        this.mealType = mealType;
    }

    /**
     * 
     * @param cuisineType
     */
    public void setCuisineType(ArrayList<String> cuisineType) {
        this.cuisineType = cuisineType;
    }
}
