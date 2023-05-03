
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

    public Recipe(){
        
    }

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
//select distinct recipeName, url, source, ingredients, dietLabels, healthLabels, calories, instructions, cautions, recipe.username from recipe,weeklyplan,weeklyplanitem where recipe.username=weeklyplan.username and weeklyplan.weeklyPlanid=weeklyplanitem.weeklyplanid and recipe.username='" + user.getUsername() + "' and dayOfWeek='" + day + "' and recipe.recipeID=weeklyplanitem.recipeid and weeklyPlan.name='" + weeklyPlan.getName() + "'
    public Recipe(String recipeName, String url, String source, ArrayList<Ingredient> ingredients, NutritionalFacts nutritionalFacts, Instructions instructions) {
        this.name=recipeName;
        this.source=source;
        this.ingredients=ingredients;
        this.nutritionalFacts=nutritionalFacts;
        this.instructions=instructions;
    }

    public String getUri() {
        return uri;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getURL() {
        return url;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public RecipeDescriptors getRecipeDescriptors() {
        return recipeDescriptors;
    }

    public Rating getRating() {
        return rating;
    }

    public Instructions getInstructions() {
        return instructions;
    }

    public NutritionalFacts getNutritionalFacts() {
        return nutritionalFacts;
    }

    public void setUri(String uri)
    {
        this.uri = uri; 
    }

    public void setName(String Name)
    {
        this.name = Name; 
    }

    public void setSource(String source)
    {
        this.source = source; 
    }

    public void setUrl(String url)
    {
        this.url = url; 
    }

    public void setTotalWeight(double totalWeight)
    {
        this.totalWeight = totalWeight; 
    }

    public void setInstructions(Instructions instructions)
    {
        this.instructions = instructions;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredients)
    {
        this.ingredients = ingredients;
    }

    public void setIngredientLines(ArrayList<String> ingredientLines)
    {
        this.ingredientLines = ingredientLines;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setRecipeDescriptors(RecipeDescriptors recipeDescriptors){
        this.recipeDescriptors = recipeDescriptors;
    }

    public void setNutritionalFacts(NutritionalFacts nutritionalFacts){
        this.nutritionalFacts = nutritionalFacts;
    }

    public void changeServingSize(double multiplier){
        this.totalWeight = this.totalWeight*multiplier;
        for(Ingredient ingredient : this.ingredients){
            double curQuantity = ingredient.getQuantity();
            ingredient.setQuantity(curQuantity*multiplier);
        }
    }

    public static Recipe JSONToRecipe(JSONObject recipeJSON){
        Recipe result = new Recipe();
        JSONObject recipe = recipeJSON;

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
        
        return result;
    }

    @Override
    public String toString() {
        String str = name + ":\n";
        if (source != null) {
            str += "source: " + source + "\n";
        }
        if (url != null) {
            str += "url: " + url + "\n";
        }
        str += "Ingredients: \n";
        for (int i = 0; i < ingredients.size(); i++) {
            str += "\t" + ingredients.get(i).toString() + "\n";
        }
        return str;
    }

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
            result += "\nRating: " + returnRate + " stars\n";
        }
        Instructions instruct = this.getInstructions();
        if(instruct != null) result += "\n" + instruct.toString();
        return result;
    }
}
