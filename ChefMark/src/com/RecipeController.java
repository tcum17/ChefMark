

import java.util.ArrayList;
import java.util.Scanner;

public class RecipeController {
    
    public static String arrayListToText(ArrayList<String> arrayList) {
        String returnString = "";
        for (int i = 0; i < arrayList.size(); i++) {
            returnString+=arrayList.get(i) + "|";
        }
        return returnString;
    }

    public static String ingredientListToText(ArrayList<Ingredient> arrayList) {
        String returnString = "";
        for (int i = 0; i < arrayList.size(); i++) {
            Ingredient ingredient = arrayList.get(i);
            returnString+=ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredientName() + "|";
        }
        return returnString;
    }

    public Recipe createRecipe(String recipeName, Scanner sc)
    {
        Recipe newRecipe = new Recipe();
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        Rating rate = new Rating();
        newRecipe.setName(recipeName);
        boolean done = false;
        boolean done2 = false;
        boolean done3 = false;
        while(!done)
        {
            System.out.println("Add an ingredient or type done once all the ingredients have been added");
            String ingredientName = sc.nextLine();
            if(ingredientName.equals("done"))
            {
                done = true;
            }
            else
            {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientName(ingredientName);
                System.out.println("Please give the ingredient a quantity(number only)");
                float quantity = Float.parseFloat(sc.nextLine());
                ingredient.setQuantity(quantity);
                System.out.println("Please give the ingredient measure(Cups, Grams, ect...)");
                String measure = sc.nextLine();
                ingredient.setMeasure(measure);
                ingredientList.add(ingredient);      
                newRecipe.setIngredientList(ingredientList);
            }
        }
        while(!done2)
        {
            ArrayList<String> newInstructions = new ArrayList<>();
                
                String instruction = "";
                while(!instruction.equals("done"))
                {
                    System.out.println("Please type out an instruction and press enter to add another instruction. Once you are done adding instructions type done");
                    instruction = sc.nextLine();
                    if(instruction.equals("done"))
                    {
                        done2 = true;
                    }
                    else
                    {
                        newInstructions.add(instruction);

                    }
                    
                    
                }
                Instructions instructions = new Instructions();
                instructions.setInstructions(newInstructions);
                newRecipe.setInstructions(instructions);
                       
        }
        while(!done3)
        {
            System.out.println("You can add a rating 1-5 or type -1 to skip");
            String rating = sc.nextLine();
            if(rating.equals("1") ||rating.equals("2")|| rating.equals("3")|| rating.equals("4")|| rating.equals("5"))
            {
                int ratingAsAnInt = Integer.parseInt(rating);
                rate.changeRating(ratingAsAnInt);
                newRecipe.setRating(rate);
                done3=true;
            }
            else if(rating.equals("-1"))
            {
                done3=true;
                newRecipe.setRating(rate);
            }
            else
            {
                System.out.println("That is not an option");
            }
        }

        return newRecipe;
    }

    public boolean updateRecipe(Recipe recipe)
    {
        return true;
    }
    public boolean deleteRecipe(String recipeName)
    {
        return true;
    }

    public boolean shareRecipe(Recipe recipe)
    {
        return true;
    }

    public ArrayList<Recipe> viewRecipeHistory()
    {
        return null;
    }

    public Recipe generateRandomRecipe()
    {
        return null;
    }
}
