import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class WeeklyPlan {

    private String name;
    private HashMap<String, ArrayList<Recipe>> weeklyPlan = new HashMap<>();

    public WeeklyPlan() {
        this.weeklyPlan.put("Monday", new ArrayList<>());
        this.weeklyPlan.put("Tuesday", new ArrayList<>());
        this.weeklyPlan.put("Wednesday", new ArrayList<>());
        this.weeklyPlan.put("Thursday", new ArrayList<>());
        this.weeklyPlan.put("Friday", new ArrayList<>());
        this.weeklyPlan.put("Saturday", new ArrayList<>());
        this.weeklyPlan.put("Sunday", new ArrayList<>());
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setWeeklyPlan(HashMap<String, ArrayList<Recipe>> weeklyPlan) {
        this.weeklyPlan = weeklyPlan;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, ArrayList<Recipe>> getWeeklyPlan() {
        return this.weeklyPlan;
    }

    public boolean addRecipeToWeeklyPlan(Recipe recipe, String day) {
        if (this.weeklyPlan.containsKey(day)) {
            this.weeklyPlan.get(day).add(recipe);
            System.out.println("Added " + recipe.getName() + " to " + this.name);
            return true;
        }
        return false;

    }

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

    public String info() {
        String str = "";
        str += "Monday: \n";
        for (int i = 0; i < weeklyPlan.get("Monday").size(); i++) {
            str += "\t\t" + weeklyPlan.get("Monday").get(i) + "\n";
        }
        str += "\nTuesday: \n";
        for (int i = 0; i < weeklyPlan.get("Tuesday").size(); i++) {
            str += "\t\t" + weeklyPlan.get("Tuesday").get(i) + "\n";
        }
        str += "\nWednesday: \n";
        for (int i = 0; i < weeklyPlan.get("Wednesday").size(); i++) {
            str += "\t\t" + weeklyPlan.get("Wednesday").get(i) + "\n";
        }
        str += "\nThursday: \n";
        for (int i = 0; i < weeklyPlan.get("Thursday").size(); i++) {
            str += "\t\t" + weeklyPlan.get("Thursday").get(i) + "\n";
        }
        str += "\nFriday: \n";
        for (int i = 0; i < weeklyPlan.get("Friday").size(); i++) {
            str += "\t\t" + weeklyPlan.get("Friday").get(i) + "\n";
        }
        str += "\nSaturday: \n";
        for (int i = 0; i < weeklyPlan.get("Saturday").size(); i++) {
            str += "\t\t" + weeklyPlan.get("Saturday").get(i) + "\n";
        }
        str += "\nSunday: \n";
        for (int i = 0; i < weeklyPlan.get("Sunday").size(); i++) {
            str += "\t\t" + weeklyPlan.get("Sunday").get(i) + "\n";
        }

        return str;
    }

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
