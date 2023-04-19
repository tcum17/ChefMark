package chefmark;

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

    public Ingredient createIngredient(String ingredientName, float quantity, String measure, float weight){

        Ingredient newIngredient = new Ingredient();
        newIngredient.setIngredientName(ingredientName);
        newIngredient.setQuantity(quantity);
        newIngredient.setMeasure(measure);
        newIngredient.setWeight(weight);
        return newIngredient;
    }
}
