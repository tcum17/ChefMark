package chefmark;

import java.util.ArrayList;

public class Pantry {
    private ArrayList<Ingredient> ingredientList = new ArrayList<>();

    /**
     * 
     * @return
     */
    public ArrayList<Ingredient> getIngredientList() {
        return this.ingredientList;
    }

    /**
     * 
     * @param ingredientName
     * @return
     */
    public boolean removeIngredient(String ingredientName) {
        for(int i = 0; i< ingredientList.size(); i++)
        {
            if(ingredientList.get(i).getIngredientName().equalsIgnoreCase(ingredientName))
            {
                ingredientList.remove(i);
                return true;
            }
            
        }
        return false;
        
        
    }

    /**
     * 
     * @return
     */
    public int ingredientListLength()
    {
        return ingredientList.size();
    }

    /**
     * searchs for an ingredient based on its name
     * @param ingredientName
     * @return returns the ingredient object
     */
    public Ingredient search(String ingredientName) {
        for (int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getIngredientName().equals(ingredientName)) {
                return ingredientList.get(i);
            }
        }
        return null;
    }

    /**
     * creates an ingredient
     * @param ingredientName
     * @param quantity
     * @param measure
     * @return returns the ingredient it creates
     */
    public Ingredient createIngredient(String ingredientName, float quantity, String measure){

        Ingredient newIngredient = new Ingredient();
        newIngredient.setIngredientName(ingredientName);
        newIngredient.setQuantity(quantity);
        newIngredient.setMeasure(measure);
        return newIngredient;
    }

    /**
     * Checks if ingredient is in pantry
     * @param name
     * @return returns result for whether or not ingredient is in pantry
     */
    public boolean hasIngredient(String name)
    {
        for (int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getIngredientName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds ingredient to the pantry
     * @param ingredient
     */
    public void addIngredient(Ingredient ingredient)
    {
        ingredientList.add(ingredient);
    }

    /**
     * overrides toString and formats the pantry in a string 
     */
    @Override
    public String toString()
    {
        
        String temp ="\tWelcome to your Pantry!\n---------------------------------------\nYou Have: \n";
        for(Ingredient x : ingredientList)
        {
          temp += x.toString() + "\n";
        }
        return temp;
    }
}
