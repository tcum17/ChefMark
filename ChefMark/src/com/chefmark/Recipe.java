package chefmark;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Recipe {
    String uri;
    String name;
    String source;
    String url;
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    double totalWeight;
    RecipeDescriptors recipeDescriptors;
    Rating rating = new Rating();
    Instructions instructions = new Instructions();
    NutritionalFacts nutritionalFacts;
    ArrayList<String> ingredientLines = new ArrayList<>();
    int isCustom = 0;

    /**
     * 
     */
    public Recipe(){
        
    }

    /**
     * Recipe constructor
     * @param uri
     * @param name
     * @param source
     * @param url
     * @param ingredients
     * @param ingredientLines
     * @param totalWeight
     * @param recipeDescriptors
     * @param rating
     * @param instructions
     * @param nutritionalFacts
     */
    public Recipe(String uri, String name, String source, String url, ArrayList<Ingredient> ingredients, ArrayList<String> ingredientLines,
            int totalWeight, RecipeDescriptors recipeDescriptors, Rating rating, Instructions instructions,
            NutritionalFacts nutritionalFacts) {
        this.uri = uri;
        this.name = name;
        this.source = source;
        this.url = url;
        this.ingredients = ingredients;
        this.ingredientLines = ingredientLines;
        this.totalWeight = totalWeight;
        this.recipeDescriptors = recipeDescriptors;
        this.rating = rating;
        this.instructions = instructions;
        this.nutritionalFacts = nutritionalFacts;
    }

    /**
     * constructor for recipes
     * @param recipeName
     * @param url
     * @param source
     * @param ingredients
     * @param nutritionalFacts
     * @param instructions
     */
    public Recipe(String recipeName, String url, String source, ArrayList<Ingredient> ingredients, NutritionalFacts nutritionalFacts, Instructions instructions) {
        this.name=recipeName;
        this.url=url;
        this.source=source;
        this.ingredients=ingredients;
        this.nutritionalFacts=nutritionalFacts;
        this.instructions=instructions;
    }

    /**
     * Returns the URI
     * @return Recipe URI
     */
    public String getUri() {
        return uri;
    }

    /**
     * Returns the recipe's name
     * @return Recipe name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the recipe's source
     * @param source Recipe source
     */
    public String getSource() {
        return source;
    }

    /**
     * 
     * @return
     */
    public String getURL() {
        return url;
    }

    /**
     * 
     * @return
     */
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * 
     * @return
     */
    public double getTotalWeight() {
        return totalWeight;
    }

    /**
     * 
     * @return
     */
    public RecipeDescriptors getRecipeDescriptors() {
        return recipeDescriptors;
    }

    /**
     * 
     * @return
     */
    public Rating getRating() {
        return rating;
    }

    /**
     * 
     * @return
     */
    public Instructions getInstructions() {
        return instructions;
    }

    /**
     * 
     * @return
     */
    public NutritionalFacts getNutritionalFacts() {
        return nutritionalFacts;
    }

    /**
     * 
     * @return
     */
    public int getIsCustom() {
        return isCustom;
    }

    /**
     * 
     * @param uri
     */
    public void setUri(String uri)
    {
        this.uri = uri; 
    }

    /**
     * 
     * @param Name
     */
    public void setName(String Name)
    {
        this.name = Name; 
    }

    /**
     * 
     * @param source
     */
    public void setSource(String source)
    {
        this.source = source; 
    }

    /**
     * 
     * @param url
     */
    public void setUrl(String url)
    {
        this.url = url; 
    }

    /**
     * 
     * @param totalWeight
     */
    public void setTotalWeight(double totalWeight)
    {
        this.totalWeight = totalWeight; 
    }

    /**
     * 
     * @param instructions
     */
    public void setInstructions(Instructions instructions)
    {
        this.instructions = instructions;
    }

    /**
     * 
     * @param ingredients
     */
    public void setIngredientList(ArrayList<Ingredient> ingredients)
    {
        this.ingredients = ingredients;
    }

    /**
     * Sets the recipe's ingredients
     * @param ingredientLines Recipe's ingredients
     */
    public void setIngredientLines(ArrayList<String> ingredientLines)
    {
        this.ingredientLines = ingredientLines;
    }

    /**
     * Sets the recipe's rating
     * @param rating Sets a recipe's rating
     */
    public void setRating(Rating rating) {
        this.rating = rating;
    }

    /**
     * 
     * @param recipeDescriptors
     */
    public void setRecipeDescriptors(RecipeDescriptors recipeDescriptors){
        this.recipeDescriptors = recipeDescriptors;
    }

    /**
     * Sets the recipe's nutritional facts
     * @param nutritionalFacts The user's nutritional facts
     */
    public void setNutritionalFacts(NutritionalFacts nutritionalFacts){
        this.nutritionalFacts = nutritionalFacts;
    }

    /**
     * Sets the value for if the recipe is custom or not
     * @param b Int value (0 if negative, 1 if positive)
     */
    public void setCustom(int b){
        this.isCustom = b;
    }

    /**
     * Changes the serving size
     * @param multiplier Multiplier to change serving size by
     */
    public void changeServingSize(double multiplier){
        this.totalWeight = this.totalWeight*multiplier;
        for(Ingredient ingredient : this.ingredients){
            double curQuantity = ingredient.getQuantity();
            ingredient.setQuantity(curQuantity*multiplier);
        }
    }

    /**
     * Converts a JSON object to recipe
     * @param recipeJSON Recipe JSON object
     * @return returns newly constructed recipe
     */
    public static Recipe JSONToRecipe(JSONObject recipeJSON){
        Recipe result = new Recipe();
        JSONObject recipe = recipeJSON;
        if(recipeJSON == null) return null;
        String uri = (String) recipe.get("uri");
        result.setUri(uri);
        String recipeName = (String) recipe.get("label");
        result.setName(recipeName);
        String source = (String) recipe.get("source");
        result.setSource(source);
        String recipeUrl = (String) recipe.get("url");
        result.setUrl(recipeUrl);
        if(recipe.containsKey("ingredients")){
            JSONArray ingredientsJSON = (JSONArray) recipe.get("ingredients");
            for(Object ingredJSON : ingredientsJSON){
                JSONObject curIngredJSON = (JSONObject) ingredJSON;
                Ingredient curIngredient = Ingredient.JSONToIngredient(curIngredJSON);
                if(curIngredient != null) result.getIngredients().add(curIngredient);
            }
        }
        if(recipe.containsKey("totalWeight")){
            double totalWeight = (double) recipe.get("totalWeight");
            result.setTotalWeight(totalWeight);
        }
        NutritionalFacts facts = new NutritionalFacts();
        if(recipe.containsKey("healthLabels")){
            JSONArray dietLabelsJSON = (JSONArray) recipe.get("dietLabels");
            ArrayList<String> dietLables = new ArrayList<>();
            for(Object dietLabelJSON : dietLabelsJSON){
                String curLabel = (String) dietLabelJSON;
                dietLables.add(curLabel);
            }
            facts.setDietLabels(dietLables);
        }
        if(recipe.containsKey("healthLabels")){
            JSONArray healthLabelsJSON = (JSONArray) recipe.get("healthLabels");
            ArrayList<String> healthLabels = new ArrayList<>();
            for(Object healthLabelJSON : healthLabelsJSON){
                String curLabel = (String) healthLabelJSON;
                healthLabels.add(curLabel);
            }
            facts.setHealthLabels(healthLabels);
        }
        if(recipe.containsKey("calories")){
            facts.setCalories((double) recipe.get("calories"));
        } 
        if(recipe.containsKey("glycemicIndex")){
            facts.setGlycemicIndex((double) recipe.get("glycemicIndex"));
        }
        if(recipe.containsKey("yield")){
            facts.setYeild((double) recipe.get("yield"));
        }
        if(facts != null) result.setNutritionalFacts(facts);
        Instructions instruct = new Instructions();
        if(recipe.containsKey("instructions")){
            JSONArray instructionsJSON = (JSONArray) recipe.get("instructions");
            ArrayList<String> instructions = new ArrayList<>();
            for(Object instructionJSON : instructionsJSON){
                String curInstruction = (String) instructionJSON;
                instructions.add(curInstruction);
            }
            instruct.setInstructions(instructions);
        }
        if(recipe.containsKey("cautions")){
            JSONArray cautionsJSON = (JSONArray) recipe.get("cautions");
            ArrayList<String> cautions= new ArrayList<>();
            for(Object cautionJSON : cautionsJSON){
                String curCaution = (String) cautionJSON;
                cautions.add(curCaution);
            }
            instruct.setCautions(cautions);
        }
        if(instruct != null) result.setInstructions(instruct);

        RecipeDescriptors descriptors = new RecipeDescriptors();
        if(recipe.containsKey("tags")){
            JSONArray tagsJSON = (JSONArray) recipe.get("tags");
            ArrayList<String> tags= new ArrayList<>();
            for(Object tagJSON : tagsJSON){
                String curTag = (String) tagJSON;
                tags.add(curTag);
            }
            descriptors.setTags(tags);
        }
        if(recipe.containsKey("dishType")){
            JSONArray dishTypesJSON = (JSONArray) recipe.get("dishType");
            ArrayList<String> dishTypes= new ArrayList<>();
            for(Object dishTypeJSON : dishTypesJSON){
                String curType = (String) dishTypeJSON;
                dishTypes.add(curType);
            }
            descriptors.setDishType(dishTypes);
        }
        if(recipe.containsKey("mealType")){
            JSONArray mealTypesJSON = (JSONArray) recipe.get("mealType");
            ArrayList<String> mealTypes= new ArrayList<>();
            for(Object mealTypeJSON : mealTypesJSON){
                String curType = (String) mealTypeJSON;
                mealTypes.add(curType);
            }
            descriptors.setMealType(mealTypes);
        }
        if(descriptors != null) result.setRecipeDescriptors(descriptors);
        if(recipe.containsKey("ingredientLines")){
            JSONArray ingredientLines = (JSONArray) recipe.get("ingredientLines");
            ArrayList<String> newIngredientLines=  new ArrayList<>();
            for(Object line : ingredientLines){
                String sline = (String) line;
                newIngredientLines.add(sline);
            }
            result.setIngredientLines(newIngredientLines);
        }
        result.setCustom(0);
        return result;
    }

    /**
     * Prints the recipe
     * @return returns constructed string containing recipe info
     */
    public String printRecipe()
    {
        String result = "";
        result += this.getName() + "\n";
        result += "\nIngredients:\n";
        for(int i=0;i<this.getIngredients().size();i++)
        {
            String test = this.getIngredients().get(i).toString();
            result = result + test + "\n";
            //result = result + "Ingredient - " + this.getIngredients().get(i).toString() + "\n"; 
        }
        result = result + "\n";
        int returnRate = this.getRating().getRating();
        if(returnRate!=-1)
        {
            String rating = "";
            if (returnRate==0)
                rating="Negative";
            else
                rating="Positive";
            result += "\nRating: " + rating + "\n";
        }
        Instructions instruct = this.getInstructions();
        if (this.url!=null && !instruct.getInstructions().contains(this.url))
            instruct.getInstructions().add(url);
        if(instruct != null) result += "\n" + instruct.toString();
        return result;
    }
}
