package com;


import java.util.ArrayList;

public class Pantry {
    private ArrayList<Ingredient> ingredientList = new ArrayList<>();

    public ArrayList<Ingredient> getIngredientList() {
        return this.ingredientList;
    }

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

    public Ingredient search(String ingredientName) {
        for (int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getIngredientName().equals(ingredientName)) {
                return ingredientList.get(i);
            }
        }
        return null;
    }

    public Ingredient createIngredient(String ingredientName, float quantity, String measure){

        Ingredient newIngredient = new Ingredient();
        newIngredient.setIngredientName(ingredientName);
        newIngredient.setQuantity(quantity);
        newIngredient.setMeasure(measure);
        return newIngredient;
    }

     
    public boolean hasIngredient(String name)
    {
        for (int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getIngredientName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addIngredient(Ingredient ingredient)
    {
        ingredientList.add(ingredient);
    }

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
