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
 */
public class Stock_Connector {

    public JSONArray SearchStockAvailable(){

        BufferedReader reader = null;
        JSONArray jsonArray = null;
        try {
            URL url = new URL("http://awhitten02.students.cs.qub.ac.uk/search_stock.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            InputStream inputStream = con.getInputStream();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine())!= null) {

                sb.append(line);
            }

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
