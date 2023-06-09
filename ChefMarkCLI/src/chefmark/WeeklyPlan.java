package chefmark;

import java.util.HashMap;
import java.util.ArrayList;

public class WeeklyPlan {

    private String name;
    private HashMap<String, ArrayList<Recipe>> weeklyPlan;

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

    public void info() {
        System.out.println("Monday: ");
        for (int i = 0; i < weeklyPlan.get("Monday").size(); i++) {
            System.out.println("\t\t" + weeklyPlan.get("Monday").get(i));
        }
        System.out.println(("Tuesday: "));
        for (int i = 0; i < weeklyPlan.get("Tuesday").size(); i++) {
            System.out.println("\t\t" + weeklyPlan.get("Tuesday").get(i));
        }
        System.out.println(("Wednesday: "));
        for (int i = 0; i < weeklyPlan.get("Wednesday").size(); i++) {
            System.out.println("\t\t" + weeklyPlan.get("Wednesday").get(i));
        }
        System.out.println(("Thursday: "));
        for (int i = 0; i < weeklyPlan.get("Thursday").size(); i++) {
            System.out.println("\t\t" + weeklyPlan.get("Thursday").get(i));
        }
        System.out.println(("Friday: "));
        for (int i = 0; i < weeklyPlan.get("Friday").size(); i++) {
            System.out.println("\t\t" + weeklyPlan.get("Friday").get(i));
        }
        System.out.println(("Saturday: "));
        for (int i = 0; i < weeklyPlan.get("Saturday").size(); i++) {
            System.out.println("\t\t" + weeklyPlan.get("Saturday").get(i));
        }
        System.out.println(("Sunday: "));
        for (int i = 0; i < weeklyPlan.get("Sunday").size(); i++) {
            System.out.println("\t\t" + weeklyPlan.get("Sunday").get(i));
        }
    }

}
