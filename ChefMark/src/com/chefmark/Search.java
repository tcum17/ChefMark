package chefmark;

import java.util.ArrayList;

public abstract class Search {
    private static int currentPage = 0;
    private static ArrayList<ResultPage> pageList = new ArrayList<>(); //cache results

    public abstract boolean keywordSearch(String keyword);

    public abstract boolean calorieSearch(int min, int max);

    public abstract Recipe randomSearch();

    public abstract boolean nextPage();

    public abstract boolean previousPage();

    public abstract boolean viewRecipe(int index);

    public abstract boolean displayCurrentPage();

    public abstract Recipe getCurRecipe();

    public abstract boolean hasNextPage();
}
