package chefmark.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import chefmark.Search;
import chefmark.EdamamSearch;
import chefmark.Recipe;

public class SearchTests {
    Search s = null;
    
    @Before
    public void startUp(){
        s = new EdamamSearch();
    }

    @Test
    public void hasNextPageBefore(){
        assertTrue(!s.hasNextPage());
    }

    @Test
    public void noNextPage(){
        assertTrue(!s.nextPage());
    }

    @Test
    public void noPreviousPage(){
        assertTrue(!s.previousPage());
    }

    @Test
    public void noViewGetCurRecipe(){
        Recipe r = s.getCurRecipe();
        assertNull(r);
    }

    @Test
    public void displayPageNoSearch(){
        assertTrue(!s.displayCurrentPage());
    }

    @Test
    public void viewRecipeBeforeSearch(){
        assertTrue(!s.viewRecipe(0));
    }

    //All searching tests call basicSearch with their added filters
    @Test
    public void keywordSearchNullTest(){
        assertTrue(!s.keywordSearch(null));
    }

    @Test
    public void keywordSearchTest(){
        String searchStr = "cheese";
        assertTrue(s.keywordSearch(searchStr));
    }

    @Test
    public void hasNextPageAfter(){
        String searchStr = "cheese";
        s.keywordSearch(searchStr);
        assertTrue(s.hasNextPage());
    }

    @Test
    public void nextPage(){
        String searchStr = "cheese";
        s.keywordSearch(searchStr);
        assertTrue(s.nextPage());
    }

    @Test
    public void previousPage(){
        String searchStr = "cheese";
        s.keywordSearch(searchStr);
        s.nextPage();
        assertTrue(s.previousPage());
    }

    @Test
    public void displayPageAfterSearch(){
        String searchStr = "cheese";
        s.keywordSearch(searchStr);
        assertTrue(s.displayCurrentPage());
    }

    @Test
    public void viewRecipeAfterSearch(){
        String searchStr = "cheese";
        s.keywordSearch(searchStr);
        assertTrue(s.viewRecipe(0));
    }

    @Test
    public void afterViewGetCurRecipe(){
        String searchStr = "cheese";
        s.keywordSearch(searchStr);
        s.viewRecipe(0);
        assertNotNull(s.getCurRecipe());
    }

    @Test
    public void ingredientSearchTestEmpty(){
        ArrayList<String> ingredients = new ArrayList<String>();
        String searchString = ingredients.toString(); //convert list to string
        searchString = searchString.substring(1, searchString.length()-1); //trim brackets off of the ressult
        assertTrue(!s.keywordSearch(searchString));
    }

    @Test
    public void ingredientSearchTest(){ //Pantry search works same way but with ingredients from pantry
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("corn");
        ingredients.add("beef");
        String searchString = ingredients.toString(); //convert list to string
        searchString = searchString.substring(1, searchString.length()-1); //trim brackets off of the ressult
        assertTrue(s.keywordSearch(searchString));
    }

    @Test
    public void calorieSearchTest(){
        int low, high;
        low = 0; high = 0;
        assertTrue(s.calorieSearch(low, high));
        low = 500; high = 2000;
        assertTrue(s.calorieSearch(low, high));
    }

    @Test
    public void randomSearchTest(){
        Recipe r = s.randomSearch();
        assertNotNull(r);
    }

    @After
    public void cleanUp(){
        s = null;
    }

}