package chefmark;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ShoppingListConverter {
    /**
     * converts a weekly plan to a shopping list based on what is in your pantry and what you need
     * @param plan
     * @param pantry
     * @return returns an arraylist of the ingredients and the quantities you need
     */
    public static ArrayList<String> convertToShoppingList(WeeklyPlan plan, Pantry pantry) {
        ArrayList<String> shoppingList = new ArrayList<>();
        HashMap<String, ArrayList<Recipe>> wp = plan.getWeeklyPlan();
        ArrayList<Recipe> wpRecipes = new ArrayList<>();

        // add all recipes from weeklyplan into arraylist
        for (Map.Entry<String, ArrayList<Recipe>> entry : wp.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                wpRecipes.add(entry.getValue().get(i));
            }
        }

        for (int i = 0; i < wpRecipes.size(); i++) {
            // if (!shoppingList.contains(wpRecipes.get(i).getName())) {
            // shoppingList.add(wpRecipes.get(i).getName());
            // }
            for (int j = 0; j < wpRecipes.get(i).getIngredients().size(); j++) {
                if (!shoppingList.contains(wpRecipes.get(i).getIngredients().get(j))) {
                    shoppingList.add(wpRecipes.get(i).getIngredients().get(j).toString());
                }
            }
        }

        System.out.println("Shopping List");
        System.out.println("==============================");

        for (int i = 0; i < shoppingList.size(); i++) {
            System.out.println(shoppingList.get(i));
        }
        System.out.println("==============================");
        System.out.println();

        return shoppingList;
    }
}