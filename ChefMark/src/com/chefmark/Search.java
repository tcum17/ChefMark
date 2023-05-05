package chefmark;

import java.util.ArrayList;

public abstract class Search {
    private static int currentPage = 0;
    private static ArrayList<ResultPage> pageList = new ArrayList<>(); //cache results

    public abstract boolean keywordSearch(String keyword);

    public abstract boolean calorieSearch(int min, int max);

    public abstract Recipe randomSearch();

    public abstract void nextPage();

    public abstract void previousPage();

    public abstract void viewRecipe(int index);

    public abstract void displayCurrentPage();

    public abstract Recipe getCurRecipe();
}
