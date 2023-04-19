package chefmark;

import java.util.ArrayList;

public class Pantry {
    private ArrayList<Ingredient> ingredientList;

    public ArrayList<Ingredient> getIngredientList() {
        return this.ingredientList;
    }

    public boolean removeIngredient(Ingredient ingredient) {
        return ingredientList.remove(search(ingredient.getIngredientName()));
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
    public void createIngredient(Ingredient ingredient) {
        ingredientList.add(ingredient);
    }
}
