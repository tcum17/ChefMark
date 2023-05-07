package chefmark;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class RecipeController {
    
    /**
     * Converts an array list into text
     * @param arrayList
     * @return returns constructed string
     */
    public static String arrayListToText(ArrayList<String> arrayList) {
        String returnString = "";
        for (int i = 0; i < arrayList.size(); i++) {
            returnString+=arrayList.get(i) + "|";
        }
        return returnString;
    }

    /**
     * converts ingredient list into string
     * @param arrayList
     * @return returns constructed string
     */
    public static String ingredientListToText(ArrayList<Ingredient> arrayList) {
        String returnString = "";
        for (int i = 0; i < arrayList.size(); i++) {
            Ingredient ingredient = arrayList.get(i);
            returnString+=ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredientName() + "|";
        }
        return returnString;
    }

    /**
     * converts a string to an array that is split by | 
     * @param text
     * @return an arraylist that has parsed teh strings apart
     */
    public static ArrayList<String> textToArrayList(String text) {
        ArrayList<String> returnList = new ArrayList<>();
        if (text!=null)
        {
            String[] splitString = text.split("\\|");
            for (String s : splitString) {
                if (!s.isEmpty()) {
                    returnList.add(s);
                }
            }
        }
        return returnList;
    }

    /**
     * Converts String version of ingredient list to array list
     * @param text
     * @return returns newly constructed array list
     */
    public static ArrayList<Ingredient> textToIngredientList(String text) {
        ArrayList<Ingredient> returnList = new ArrayList<>();
        String[] splitString = text.split("\\|");
        for (String s : splitString) {
            if (!s.isEmpty()) {
                ArrayList<String> parts = new ArrayList<String>();
                String[] tokens = s.split(" ", 3);
                for (String token : tokens) {
                    parts.add(token);
                }
                returnList.add(new Ingredient(parts.get(2), "", Float.parseFloat(parts.get(0)), parts.get(1)));
            }
        }
        return returnList;
    }

    /**
     * Creates a recipe
     * @param recipeName
     * @param sc
     * @param uc
     * @param dbq
     * @return returns the created recipe
     * @throws SQLException
     */
    public Recipe createRecipe(String recipeName, Scanner sc, UserController uc, DBQuery dbq) throws SQLException
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
            else if(ingredientName.equals(""))
            {
                System.out.println("You did not enter a name for the ingredient");
                System.out.println();
            }
            else
            {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientName(ingredientName);
                System.out.println("Please give the ingredient a quantity(number only)");
                float quantity = -1;
                boolean goodInput = false;
                while(!goodInput)
                {
                    try{
                    quantity = Float.parseFloat(sc.nextLine());
                    goodInput = true;
                    }
                    catch(NumberFormatException e)
                    {
                        System.out.println("Invalid input please try again");
                        
                    }
                }
                ingredient.setQuantity(quantity);
                goodInput = false;
                while(!goodInput)
                {
                    System.out.println("Please give the ingredient measure(Cups, Grams, ect...)");
                    String measure = sc.nextLine();

                    if(measure.equals(""))
                    {
                        System.out.println("You did not enter anything for the measure");
                        System.out.println();
                    }
                    else
                    {
                        goodInput = true;
                        ingredient.setMeasure(measure);
                    }
                }
                
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
                    else if(instruction.equals(""))
                    {
                        System.out.println("You didnt input anything for the instruction");
                        System.out.println();
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
            System.out.println("You can add a rating (\"0\" for bad, \"1\" for good) or type -1 to skip");
            String rating = sc.nextLine();
            if(rating.equals("1") ||rating.equals("2")|| rating.equals("-1"))
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
        newRecipe.setCustom(1);
        dbq.createCustomRecipe(newRecipe, uc.getUser());
        return newRecipe;
    }
}
