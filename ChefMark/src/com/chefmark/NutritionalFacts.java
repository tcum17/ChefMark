package chefmark;

import java.util.ArrayList;

public class NutritionalFacts {

    private ArrayList<String> healthLables = new ArrayList<>();
    private ArrayList<String> dietLables = new ArrayList<>();
    private double calories;
    private double glycemicIndex;
    private double yeild;

    /**
     * nutritional facts constructor
     * @param dietLabels
     * @param healthLabels
     * @param calories
     */
    public NutritionalFacts(ArrayList<String> dietLabels, ArrayList<String> healthLabels, double calories) {
        this.dietLables = dietLabels;
        this.healthLables = healthLabels;
        this.calories = calories;
    }

    /**
     * empty constructor
     */
    public NutritionalFacts() {}

    /**
     * 
     * @return returns health labels
     */
    public ArrayList<String> getHealthLables() {
        return healthLables;
    }

    /**
     * 
     * @return returns diet labels
     */
    public ArrayList<String> getDietLables() {
        return dietLables;
    }

    /**
     * sets calories
     * @param calories
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * 
     * @return
     */
    public double getCalories() {
        return calories;
    }

    /**
     * 
     * @param glycemicIndex
     */
    public void setGlycemicIndex(double glycemicIndex) {
        this.glycemicIndex = glycemicIndex;
    }

    /**
     * 
     * @return
     */
    public double getGlycemicIndex() {
        return glycemicIndex;
    }

    /**
     * 
     * @param yeild
     */
    public void setYeild(double yeild) {
        this.yeild = yeild;
    }

    /**
     * 
     * @param dietLables
     */
    public void setDietLabels(ArrayList<String> dietLables){
        this.dietLables = dietLables;
    }

    /**
     * 
     * @param healthLabels
     */
    public void setHealthLabels(ArrayList<String> healthLabels){
        this.healthLables = healthLabels;
    }

    /**
     * 
     * @return 
     */
    public double getYeild() {
        return yeild;
    }
}
