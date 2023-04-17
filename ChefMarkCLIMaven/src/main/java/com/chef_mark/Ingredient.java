package chefmark;

public class Ingredient {
    private String ingredientName;
    private int ingredientID;
    private float quantity;
    private String measure;
    private float weight;
    private String foodCategory;

    public Ingredient() {
    }

    public Ingredient(String ingredientName, int ingredientID, float quantity, String measure, float weight,
            String foodCategory) {
        this.ingredientName = ingredientName;
        this.ingredientID = ingredientID;
        this.quantity = quantity;
        this.measure = measure;
        this.weight = weight;
        this.foodCategory = foodCategory;

    }

    public String getIngredientName() {
        return this.ingredientName;
    }

    public int getIngredientID() {
        return this.ingredientID;
    }

    public float getQuantity() {
        return this.quantity;
    }

    public String getMeasure() {
        return this.measure;
    }

    public float getWeight() {
        return this.weight;
    }

    public String getFoodCategory() {
        return this.foodCategory;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }
}
