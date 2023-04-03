package chefmark;

import java.util.ArrayList;

public class NutritionalFacts {

    private ArrayList<String> healthLables;
    private ArrayList<String> dietLables;
    private int calories;
    private int glycemicIndex;
    private int yeild;

    public ArrayList<String> getHealthLables() {
        return healthLables;
    }

    public ArrayList<String> getDietLables() {
        return dietLables;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCalories() {
        returns calories;
    }

    public void setGlycemicIndex(int glycemicIndex) {
        this.glycemicIndex = glycemicIndex;
    }

    public int getGlycemicIndex() {
        return glycemicIndex;
    }

    public void setYeild(int yeild) {
        this.yeild = yeild;
    }

    public int getYeild() {
        return yeild;
    }
}
