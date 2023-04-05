package com.chef_mark;

import java.util.ArrayList;

public class Pantry {
    private ArrayList<Ingredient> ingredientList;

    public ArrayList<Ingredient> getIngredientList() {
        return this.ingredientList;
    }

    public boolean removeIngredient(Ingredient ingredient) {

        Ingredient temp = search(ingredient.getIngredientName());
        return ingredientList.remove(temp);
    }

    private Ingredient search(String ingredientName) {
        for (int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getIngredientName().equals(ingredientName)) {
                return ingredientList.get(i);
            }
        }
        return null;
    }

    // TODO: createIngredient
}
