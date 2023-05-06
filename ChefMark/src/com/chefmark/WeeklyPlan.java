package chefmark;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class WeeklyPlan {

    private String name;
    private HashMap<String, ArrayList<Recipe>> weeklyPlan = new HashMap<>();

    /**
     * creates a weekly plan
     */
    public WeeklyPlan() {
        this.weeklyPlan.put("Monday", new ArrayList<>());
        this.weeklyPlan.put("Tuesday", new ArrayList<>());
        this.weeklyPlan.put("Wednesday", new ArrayList<>());
        this.weeklyPlan.put("Thursday", new ArrayList<>());
        this.weeklyPlan.put("Friday", new ArrayList<>());
        this.weeklyPlan.put("Saturday", new ArrayList<>());
        this.weeklyPlan.put("Sunday", new ArrayList<>());
    }

    /**
     * creates a weekly plan
     * @param name name
     */
    public WeeklyPlan(String name) {
        this.name = name;
        this.weeklyPlan.put("Monday", new ArrayList<>());
        this.weeklyPlan.put("Tuesday", new ArrayList<>());
        this.weeklyPlan.put("Wednesday", new ArrayList<>());
        this.weeklyPlan.put("Thursday", new ArrayList<>());
        this.weeklyPlan.put("Friday", new ArrayList<>());
        this.weeklyPlan.put("Saturday", new ArrayList<>());
        this.weeklyPlan.put("Sunday", new ArrayList<>());
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @param weeklyPlan
     */
    public void setWeeklyPlan(HashMap<String, ArrayList<Recipe>> weeklyPlan) {
        this.weeklyPlan = weeklyPlan;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return 
     */
    public HashMap<String, ArrayList<Recipe>> getWeeklyPlan() {
        return this.weeklyPlan;
    }

    /**
     * adds recipe to the weekly plan
     * @param recipe
     * @param day
     * @return boolean
     */
    public boolean addRecipeToWeeklyPlan(Recipe recipe, String day) {
        if (this.weeklyPlan.containsKey(day)) {
            this.weeklyPlan.get(day).add(recipe);
            //System.out.println("Added " + recipe.getName() + " to " + this.name + " on " + day);
            return true;
        }
        return false;

    }

    /**
     * removes a recipe from a weekly plan on a certin day
     * @param recipe
     * @param day
     * @return boolean
     */
    public boolean removeRecipeFromWeeklyPlan(Recipe recipe, String day) {
        if (this.weeklyPlan.containsKey(day)) {
            if (this.weeklyPlan.get(day).contains(recipe)) {
                this.weeklyPlan.get(day).remove(recipe);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Prints out the weekly plan
     * @return toString
     */
    public String info() {
        String str = "";
        str += "Monday: ";
        for (int i = 0; i < weeklyPlan.get("Monday").size(); i++) {
            str += "\n" + weeklyPlan.get("Monday").get(i).getName() + "\n";
        }
        str += "\nTuesday: ";
        for (int i = 0; i < weeklyPlan.get("Tuesday").size(); i++) {
            str += "\n" + weeklyPlan.get("Tuesday").get(i).getName() + "\n";
        }
        str += "\nWednesday: ";
        for (int i = 0; i < weeklyPlan.get("Wednesday").size(); i++) {
            str += "\n" + weeklyPlan.get("Wednesday").get(i).getName() + "\n";
        }
        str += "\nThursday: ";
        for (int i = 0; i < weeklyPlan.get("Thursday").size(); i++) {
            str += "\n" + weeklyPlan.get("Thursday").get(i).getName() + "\n";
        }
        str += "\nFriday: ";
        for (int i = 0; i < weeklyPlan.get("Friday").size(); i++) {
            str += "\n" + weeklyPlan.get("Friday").get(i).getName() + "\n";
        }
        str += "\nSaturday: ";
        for (int i = 0; i < weeklyPlan.get("Saturday").size(); i++) {
            str += "\n" + weeklyPlan.get("Saturday").get(i).getName() + "\n";
        }
        str += "\nSunday: ";
        for (int i = 0; i < weeklyPlan.get("Sunday").size(); i++) {
            str += "\n" + weeklyPlan.get("Sunday").get(i).getName() + "\n";
        }
        str = str.replaceAll("Ingredients", "Ingredients:");

        return str;
    }

    /**
     * returns the name for a a recipe
     * @param recipeName recipe name
     * @return Recipe
     */
    public Recipe getRecipeByName(String recipeName) {
        for (Map.Entry<String, ArrayList<Recipe>> entry : weeklyPlan.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (entry.getValue().get(i).getName().equals(recipeName)) {
                    return entry.getValue().get(i);
                }
            }
        }
        return null;
    }

}
