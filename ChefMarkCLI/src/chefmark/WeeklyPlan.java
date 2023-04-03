package chefmark;

import java.util.HashMap;
import java.util.ArrayList;

public class WeeklyPlan {


    private String name;
    private HashMap<String, ArrayList<Recipe>> weeklyPlan;

    public WeeklyPlan(String name)
    {
        this.name = name;
        weeklyPlan = new HashMap<String, ArrayList<Recipe>>();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setWeeklyPlan(HashMap<String, ArrayList<Recipe>> weeklyPlan)
    {
        this.weeklyPlan = weeklyPlan;
    }

    public String getName()
    {
        return name;
    }

    public HashMap<String, ArrayList<Recipe>> getWeeklyPlan()
    {
        return this.weeklyPlan;
    }

    public boolean addRecipeToWeeklyPlan(Recipe recipe)
    {

    }

    public boolean removeRecipeFromWeeklyPlan(Recipe recipe)
    {

    }


}
