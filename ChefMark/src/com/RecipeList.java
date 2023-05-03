import java.util.ArrayList;

public class RecipeList {
    private String name;
    private ArrayList<Recipe> recipeList = new ArrayList<>();

    public RecipeList() {
        this.recipeList = new ArrayList<Recipe>();
    }

    public RecipeList(String name) {
        this.name=name;
        this.recipeList = new ArrayList<Recipe>();
    }

    public RecipeList(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecipeList(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }

    public void addRecipeToRecipeList(Recipe recipe) {
        recipeList.add(recipe);
    }

    public boolean removeRecipeFromRecipeList(Recipe recipe) {
        if (recipeList.contains(recipe)) {
            recipeList.remove(recipe);
            return true;
        }
        return false;
    }

    public void info() {
        System.out.println(name);
        System.out.println("Recipes:");
        if (recipeList.size() != 0) {
            for (int i = 0; i < recipeList.size(); i++) {
                System.out.println(recipeList.get(i).getName() + "\n");
            }
            System.out.println();
        } else {
            System.out.println("You have no recipes...\n");
        }
        
    }

    public Recipe getRecipeByName(String recipeName) {
        for (int i = 0; i < recipeList.size(); i++) {
            if (recipeList.get(i).getName().equals(recipeName)) {
                return recipeList.get(i);
            }
        }
        return null;
    } 
}