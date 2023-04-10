package com.chef_mark;

import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Search {
    public static void keywordSearch(String keyword){
        String edamamAPI = "https://api.edamam.com/api/recipes/v2";
        String appid = "f9911515";
        String appkey = "eaf6c46148932e8d75de40bcb5392bd3";
        String recipeID = "b79327d05b8e5b838ad6cfd9576b30b6";
        try {
            URL url = new URL(edamamAPI+/*"/"+recipeID+*/"?type=public&q="+keyword+"&app_id="+appid+"&app_key="+appkey+"&field=label&field=yield&field=cautions&field=ingredientLines&field=calories&field=totalTime&Field=instructions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if(responseCode != 200){
                throw new RuntimeException("HTTPResponseCode: " +responseCode);
            }else{

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

                JSONArray searchHits = null;
                if(jsonresult.containsKey("hits")) searchHits = (JSONArray) jsonresult.get("hits");
                // array.add(obj);

                FileWriter file = new FileWriter("testjson.txt");
                String fileResult = "Hits: \n" + searchHits.toJSONString() + "\nNext page link: \n" + nextPage;
                fileResult = fileResult.replaceAll("\\\\",""); 

                file.write(fileResult);
                file.flush();
                file.close();

                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
