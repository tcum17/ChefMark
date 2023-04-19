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
        Search.keywordSearch("Chicken");
        Search.nextPage();
        Search.previousPage();
        Search.previousPage();
    }
}

