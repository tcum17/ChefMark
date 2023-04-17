package com.chef_mark;

import java.net.URL;
import java.net.HttpURLConnection;
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
    private static ArrayList<String> pageList;

    public static void keywordSearch(String keyword){
        
        String recipeID = "b79327d05b8e5b838ad6cfd9576b30b6";

        String urlString = (edamamAPI+/*"/"+recipeID+*/"?type=public&q="+keyword+"&app_id="+appid+"&app_key="+appkey+"&field=label&field=source&field=url&field=yield&field=cautions&field=ingredientLines&field=calories");
        String pageResult = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if(responseCode != 200){
                throw new RuntimeException("HTTPResponseCode: " +responseCode);
            }else{

                pageResult = getReturnString(url);

                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // writeSearchPage(pageResult);
        System.out.println(pageResult);
        pageList.add(pageResult);
        currentPage = 1;
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

    private static String getReturnString(URL url) throws ParseException, IOException
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
        int i = 1;
        for(Object hit : searchHits){
            JSONObject jsonHit = (JSONObject) hit;
            JSONObject recipe = (JSONObject) jsonHit.get("recipe");
            String recipeName = "Recipe name: " + recipe.get("label");
            JSONArray ingredientLines = (JSONArray) recipe.get("ingredientLines");
            String recipeSource = (String) recipe.get("source");
            String recipeUrl = (String) recipe.get("url");
            fileResult += "Hit# " +i+ " " + recipeName + "\n";
            for(Object line : ingredientLines){
                String sLine = (String) line;
                fileResult += "\t" + sLine + "\n";
            }
            fileResult += "\tFor instructions and more information, view the original recipe here at " + recipeSource + "\n\t\t" + recipeUrl + "\n";
            // System.out.println(jsonHit.toJSONString());
            i++;
        }
        fileResult += "\nNext page link: \n" + nextPage;

        fileResult = fileResult.replaceAll("\\\\",""); 
        return fileResult;
    }

    private static void nextPage(){
        
    }

    private static void previousPage(){
        
    }
}
