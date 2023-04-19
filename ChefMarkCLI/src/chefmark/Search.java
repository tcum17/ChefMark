package chefmark;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
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
    private static ArrayList<ResultPage> pageList = new ArrayList<>();
    private static ArrayList<JSONObject> jsonPageList = new ArrayList<>();

    public static void keywordSearch(String keyword){
        
        String recipeID = "b79327d05b8e5b838ad6cfd9576b30b6";

        jsonPageList.clear();
        pageList.clear();
        currentPage = 0;

        String urlString = (edamamAPI+/*"/"+recipeID+*/"?type=public&q="+keyword+"&app_id="+appid+"&app_key="+appkey+"&field=uri&field=label&field=source&field=url&field=yield&field=cautions&field=ingredientLines&field=calories");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        // writeSearchPage(pageResult);
        System.out.println(pageResult.getPageResult());
        System.out.flush();
        pageList.add(pageResult);
    }

    public static void ingredientSearch(String keyword){
        keywordSearch(keyword);
    }

    public static void writeSearchPage(String page){
        try {
            FileWriter file = new FileWriter("testjson.txt");

            file.write(page);
            file.flush();
            file.close();
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private static ResultPage getPageResponse(URL url) throws ParseException, IOException
    {
        StringBuilder responseString = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()){
            responseString.append(scanner.nextLine());
        }

        scanner.close();

        // System.out.println(responseString);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(responseString));

        //Get the first JSON object in the JSON array
        // System.out.println(array.get(0));
        JSONObject jsonresult = (JSONObject) obj;
        jsonPageList.add(jsonresult);
        JSONObject links = null;
        JSONObject next = null;
        String nextPage = null;
        if(jsonresult.containsKey("_links")) links = (JSONObject) jsonresult.get("_links");
        if(links.containsKey("next") && links != null) next = (JSONObject) links.get("next");
        if(next.containsKey("href") && next != null) nextPage = (String) next.get("href");
        // System.out.println(recipe);

        String fileResult = "";
        JSONArray searchHits = null;
        if(jsonresult.containsKey("hits")) searchHits = (JSONArray) jsonresult.get("hits");
        long resultNumber = (long) jsonresult.get("from");
        int pageIndex = 1;
        for(Object hit : searchHits){
            JSONObject jsonHit = (JSONObject) hit;
            JSONObject recipe = (JSONObject) jsonHit.get("recipe");
            String recipeName = "Recipe name: " + recipe.get("label");
            JSONArray ingredientLines = (JSONArray) recipe.get("ingredientLines");
            String recipeSource = (String) recipe.get("source");
            String recipeUrl = (String) recipe.get("url");
            fileResult += pageIndex+" Hit# " +resultNumber+ " " + recipeName + "\n";
            for(Object line : ingredientLines){
                String sLine = (String) line;
                fileResult += "\t" + sLine + "\n";
            }
            fileResult += "\tFor instructions and more information, view the original recipe here at " + recipeSource + "\n\t\t" + recipeUrl + "\n";
            // System.out.println(jsonHit.toJSONString());
            resultNumber++; 
            pageIndex++;
        }
        fileResult += "\nNext page link: \n" + nextPage + "\n";

        fileResult = fileResult.replaceAll("\\\\","");

        ResultPage result = new ResultPage(fileResult, nextPage);
        return result;
    }

    public static void nextPage(){
        String result = "";
        if(currentPage+1 == pageList.size()){
            try {
                URL url = new URL(pageList.get(currentPage).getNextPage());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                ResultPage pageResult = getPageResponse(url);
                pageList.add(pageResult);
                result = pageResult.getPageResult();
                currentPage++;
                conn.disconnect();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }      
        }else{
            currentPage++;
            result = pageList.get(currentPage).getPageResult();
        }
        System.out.println(result);
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
        System.out.println(result);
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
        String result = "";
        if(index < 21 && index > 0){
            index -= 1;
            JSONObject pageObject = jsonPageList.get(currentPage);
            JSONArray hits = null;
            hits = (JSONArray) pageObject.get("hits");
            if(hits != null){
                JSONObject hit = (JSONObject) hits.get(index);
                JSONObject links = (JSONObject) hit.get("_links");
                JSONObject self = (JSONObject) links.get("self");
                String recipeUrl = (String) self.get("href");
                try {
                    URL url = new URL(recipeUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    result = getRecipeResponse(url);
    
                    conn.disconnect();    
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }else result = "No hit at that index";
        }else{
            result = "The index provided is out of bounds, it must be 1-20";
        }
        System.out.println(result);
        System.out.flush();
    }
}
