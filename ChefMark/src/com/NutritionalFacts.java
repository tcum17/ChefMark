

import java.util.ArrayList;

public class NutritionalFacts {

    private ArrayList<String> healthLables = new ArrayList<>();
    private ArrayList<String> dietLables = new ArrayList<>();
    private double calories;
    private double glycemicIndex;
    private double yeild;

    public ArrayList<String> getHealthLables() {
        return healthLables;
    }

    public ArrayList<String> getDietLables() {
        return dietLables;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getCalories() {
        return calories;
    }

    public void setGlycemicIndex(double glycemicIndex) {
        this.glycemicIndex = glycemicIndex;
    }

    public double getGlycemicIndex() {
        return glycemicIndex;
    }

    public void setYeild(double yeild) {
        this.yeild = yeild;
    }

    public void setDietLabels(ArrayList<String> dietLables){
        this.dietLables = dietLables;
    }

    public void setHealthLabels(ArrayList<String> healthLabels){
        this.healthLables = healthLabels;
    }

    public double getYeild() {
        return yeild;
    }
}
