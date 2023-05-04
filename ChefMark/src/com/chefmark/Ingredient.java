package chefmark;

import org.json.simple.JSONObject;

public class Ingredient {
    private String ingredientName;
    private String ingredientID;
    private double quantity;
    private String measure;

    public Ingredient() {
    }

    public Ingredient(String ingredientName, String ingredientID, float quantity, String measure) {
        this.ingredientName = ingredientName;
        this.ingredientID = ingredientID;
        this.quantity = quantity;
        this.measure = measure;
    }

    public String getIngredientName() {
        return this.ingredientName;
    }

    public String getIngredientID() {
        return this.ingredientID;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public String getMeasure() {
        return this.measure;
    }


    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

   
    public static Ingredient JSONToIngredient(JSONObject ingredJSON){
        Ingredient result = new Ingredient();
        if(ingredJSON.containsKey("text")){
            String ingredientName = (String) ingredJSON.get("text");
            result.setIngredientName(ingredientName);
        }
        if(ingredJSON.containsKey("foodId")){
            String ingredientID = (String) ingredJSON.get("foodId");
            result.setIngredientID(ingredientID);
        }
        if(ingredJSON.containsKey("quantity")){
            double quantity = (double) ingredJSON.get("quantity");
            result.setQuantity(quantity);
        }
        if(ingredJSON.containsKey("measure")){
            String measure = (String) ingredJSON.get("measure");
            result.setMeasure(measure);
        }
        return result;
    }

    @Override
    public String toString()
    {
        String result = "";
        if(quantity > 0) result += quantity + " ";
        if(measure != null) result += measure + " ";
        if(ingredientName != null) result += ingredientName + " ";
       
        return result;
    }
    
}