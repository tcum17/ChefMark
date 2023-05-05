package chefmark;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Search {
    private static String edamamAPI = "https://api.edamam.com/api/recipes/v2";
    private static String appid = "f9911515";
    private static String appkey = "eaf6c46148932e8d75de40bcb5392bd3";
    private static int currentPage = 0;
    private static ArrayList<ResultPage> pageList = new ArrayList<>(); //cache results
    private static ArrayList<JSONObject> jsonPageList = new ArrayList<>();
    private static ArrayList<String> filters = new ArrayList<>();
    private static JSONObject currentRecipe = null;

    public static boolean basicSearch(boolean displayResults) throws ParseException, IOException
    {
        boolean status = false;
        jsonPageList.clear();
        pageList.clear();
        currentPage = 0;

        String urlString = (edamamAPI+"?type=public&app_id="+appid+"&app_key="+appkey);
        urlString += String.join("",filters);
        ResultPage pageResult = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if(responseCode != 200){
                throw new RuntimeException("HTTPResponseCode: " +responseCode);
            }else{

                pageResult = getPageResponse(url);

                conn.disconnect();
            }
        } catch (ProtocolException e) {
        }
        if(pageResult != null){
            if(displayResults){
                System.out.println(pageResult.getPageResult());
                System.out.flush();
            }
            pageList.add(pageResult);
            if(pageResult.getNextPage() != null) status = true;
        }else System.out.println("No results for that search.");
        filters.clear();
        return status;
    }

    public static boolean keywordSearch(String keyword) throws ParseException, IOException{
        boolean status = false;
        keyword = keyword.replaceAll("\\s+", "%20");
        keyword = keyword.replaceAll(",", "%2C");
        String keywordFilter = "&q="+keyword;
        filters.add(keywordFilter);
        status = basicSearch(true);
        return status;
    }


    public static boolean calorieSearch(int min, int max) throws ParseException, IOException{
        boolean status = false;
        String calorieFilter = "&calories="+min+"-"+max;
        filters.add(calorieFilter);
        status = basicSearch(true);
        return status;
    }

    public static JSONObject randomSearch() throws ParseException, IOException{
        JSONObject recipe = null;
        String randomFilter = "&calories=0%2B&random=true";
        filters.add(randomFilter);
        basicSearch(false);
        JSONObject pageObject = jsonPageList.get(currentPage);
        JSONArray hits = null;
        hits = (JSONArray) pageObject.get("hits");
        JSONObject jsonHit = (JSONObject) hits.get(0);
        recipe = (JSONObject) jsonHit.get("recipe");
        return recipe;
    }

    private static ResultPage getPageResponse(URL url) throws ParseException, IOException
    {
        StringBuilder responseString = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()){
            responseString.append(scanner.nextLine());
        }

        scanner.close();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(responseString));

        JSONObject jsonresult = (JSONObject) obj;
        jsonPageList.add(jsonresult);
        JSONObject links = null;
        JSONObject next = null;
        String nextPage = null;
        if(jsonresult != null && jsonresult.containsKey("_links")) links = (JSONObject) jsonresult.get("_links");
        if(links != null && links.containsKey("next") && links != null) next = (JSONObject) links.get("next");
        if(next != null && next.containsKey("href") && next != null) nextPage = (String) next.get("href");

        String resultString = "";
        JSONArray searchHits = null;
        if(jsonresult.containsKey("hits")) searchHits = (JSONArray) jsonresult.get("hits");
        int pageIndex = 1;
        for(Object hit : searchHits){
            JSONObject jsonHit = (JSONObject) hit;
            JSONObject recipe = (JSONObject) jsonHit.get("recipe");
            String recipeName = "Recipe name: " + recipe.get("label");
            String recipeSource = (String) recipe.get("source");
            String recipeUrl = (String) recipe.get("url");
            resultString += pageIndex+" " + recipeName + "\n";
            resultString += "\tFor instructions and more information, view the original recipe here at " + recipeSource + "\n\t\t" + recipeUrl + "\n";
            pageIndex++;
        }
        // resultString += "\nNext page link: \n" + nextPage + "\n";

        resultString = resultString.replaceAll("\\\\","");

        ResultPage result = new ResultPage(resultString, nextPage);
        return result;
    }

    public static void nextPage(){
        String result = "";
        if(currentPage+1 == pageList.size()){
            try {
                String nextPage = pageList.get(currentPage).getNextPage();
                if(nextPage != null){
                    URL url = new URL(nextPage);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    ResultPage pageResult = getPageResponse(url);
                    pageList.add(pageResult);
                    result = pageResult.getPageResult();
                    currentPage++;
                    conn.disconnect();
                }else System.out.println("There are no more pages.\n");
            } catch (Exception e) {
                e.printStackTrace();
            }      
        }else{
            currentPage++;
            result = pageList.get(currentPage).getPageResult();
        }
        System.out.println("\n"+result);
        System.out.flush();
    }

    public static void previousPage(){
        String result = "";
        if(currentPage == 0){
            System.out.println("No previous page!");
        }else{
            currentPage--;
            result = pageList.get(currentPage).getPageResult();
        }
        System.out.println("\n"+result);
        System.out.flush();
    }

    private static String getRecipeResponse(URL url) throws IOException, ParseException{
        StringBuilder responseString = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()){
            responseString.append(scanner.nextLine());
        }

        scanner.close();

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(responseString));

        JSONObject jsonresult = (JSONObject) obj;

        String fileResult = "";
        JSONObject recipe = (JSONObject) jsonresult.get("recipe");
        String recipeName = "Recipe name: " + recipe.get("label");
        JSONArray ingredientLines = (JSONArray) recipe.get("ingredientLines");
        String recipeSource = (String) recipe.get("source");
        String recipeUrl = (String) recipe.get("url");
        fileResult += recipeName + "\n";
        for(Object line : ingredientLines){
            String sLine = (String) line;
            fileResult += "\t" + sLine + "\n";
        }
        fileResult += "\tFor instructions and more information, view the original recipe here at " + recipeSource + "\n\t\t" + recipeUrl + "\n";

        fileResult = fileResult.replaceAll("\\\\","");

        return fileResult;
    }

    public static void viewRecipe(int index){
        String resultString = "";
        JSONObject resultRecipe = null;
        if(index < 21 && index > 0){
            index -= 1;
            JSONObject pageObject = jsonPageList.get(currentPage);
            JSONArray hits = null;
            hits = (JSONArray) pageObject.get("hits");
            if(hits != null){
                JSONObject hit = (JSONObject) hits.get(index);
                resultRecipe = (JSONObject) hit.get("recipe");
                JSONObject links = (JSONObject) hit.get("_links");
                JSONObject self = (JSONObject) links.get("self");
                String recipeUrl = (String) self.get("href");
                try {
                    URL url = new URL(recipeUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    resultString = getRecipeResponse(url);
    
                    conn.disconnect();    
                } catch (Exception e) {
                }
            }else resultString = "No hit at that index";
        }else{
            resultString = "The index provided is out of bounds, it must be 1-20";
        }
        System.out.println(resultString);
        System.out.flush();
        currentRecipe = resultRecipe;
    }

    public static void displayCurrentPage(){
        String result;
        result = pageList.get(currentPage).getPageResult();
        System.out.println(result);
        System.out.flush();
    }

    public static JSONObject getCurRecipe(){
        return currentRecipe;
    }
}
