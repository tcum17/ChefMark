package chefmark;

import org.json.simple.JSONObject;

public class Ingredient {
    private String ingredientName;
    private String ingredientID;
    private double quantity;
    private String measure;

    /**
     * Constructor
     * Constructs an empty ingredient object
     */
    public Ingredient() {
    }

    /**
     * 
     * @param ingredientName name of the ingredient
     * @param ingredientID ingredient id
     * @param quantity ingredient quantity
     * @param measure ingredient measure(cups,grams,..)
     */
    public Ingredient(String ingredientName, String ingredientID, float quantity, String measure) {
        this.ingredientName = ingredientName;
        this.ingredientID = ingredientID;
        this.quantity = quantity;
        this.measure = measure;
    }

    /**
     * Gets the ingredient name
     * @return the ingredient name
     */
    public String getIngredientName() {
        return this.ingredientName;
    }
    
    /**
     * Gets the Ingredient ID
     * @return The Ingredient Id
     */
    public String getIngredientID() {
        return this.ingredientID;
    }
    
    /**
     * Get Quantity
     * @return Quantity
     */
    public double getQuantity() {
        return this.quantity;
    }

    /**
     * Get Measure
     * @return Measure
     */
    public String getMeasure() {
        return this.measure;
    }

    /**
     * Set Ingredient Name
     * @param ingredientName the new name
     */
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    /**
     * 
     * @param ingredientID sets ingredient id
     */
    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
    }
    
    /**
     * 
     * @param quantity sets ingredient quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
    /**
     * 
     * @param measure sets ingredient measure
     */
    public void setMeasure(String measure) {
        this.measure = measure;
    }

   /**
    * 
    * @param ingredJSON 
    * @return
    */
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

    /**
     * To string method that formats the ingredient in a string
     */
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
