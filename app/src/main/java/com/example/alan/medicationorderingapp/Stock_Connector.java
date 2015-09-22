package com.example.alan.medicationorderingapp;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alan on 11/09/2015.
 * Class which send a http request to a PHP script on the server
 */
public class Stock_Connector {

    public JSONArray SearchStockAvailable(){

        BufferedReader reader = null;
        JSONArray jsonArray = null;
        try {
            //opens the URL connection and established an http connection
            URL url = new URL("http://awhitten02.students.cs.qub.ac.uk/search_stock.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //input stream to receive data from the database
            InputStream inputStream = con.getInputStream();
            //new string builder to get the result
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            //while loop to record the results and append then to the string builder
            while ((line = reader.readLine())!= null) {
                sb.append(line);
            }
            //puts the results in a JSON array
            jsonArray = new JSONArray(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}
