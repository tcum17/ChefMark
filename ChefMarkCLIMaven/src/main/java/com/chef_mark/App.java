package com.chef_mark;

import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String edamamAPI = "https://api.edamam.com/api/recipes/v2";
        String appid = "f9911515";
        String appkey = "eaf6c46148932e8d75de40bcb5392bd3";
        String recipeID = "b79327d05b8e5b838ad6cfd9576b30b6";
        try {
            // URL url = new URL(edamamAPI+/*"/"+recipeID+*/"?type=public&q=chicken&app_id="+appid+"&app_key="+appkey+"&field=label&field=yield&field=cautions&field=ingredientLines&field=calories&field=totalTime&Field=instructions");
            URL url = new URL("https://api.edamam.com/api/recipes/v2?q=chicken&Field=instructions&app_key=eaf6c46148932e8d75de40bcb5392bd3&field=label&field=yield&field=cautions&field=ingredientLines&field=calories&field=totalTime&_cont=CHcVQBtNNQphDmgVQntAEX4BYldtBAUCRGRIAmUUZFx2AAIVX3dEA2EUMgQgAQsCF2NHUDdCZ1YhVldTQmJEUGpAZgd6UBFqX3cWQT1OcV9xBB8VADQWVhFCPwoxXVZEITQeVDcBaR4-SQ%3D%3D&type=public&app_id=f9911515");
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
                JSONArray array = new JSONArray();
                array.add(obj);

                //Get the first JSON object in the JSON array
                // System.out.println(array.get(0));
                JSONObject jsonresult = (JSONObject) array.get(0);
                JSONObject recipe = (JSONObject) jsonresult.get("recipe");
                // System.out.println(recipe);

                FileWriter file = new FileWriter("testjson.txt");

                file.write(jsonresult.toJSONString());
                file.flush();
                file.close();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

