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
 * Class which sends an http request to the server and returns the results of the query
 * as a json array
 * Created by Alan on 10/09/2015.
 */
public class API_Connector {

    /**
     * Method to send http request to server and return results in a json array
     * @return jsonArray
     */
    public JSONArray PlotGoogleMarkers() {

        BufferedReader reader = null;
        JSONArray jsonArray = null;
        try {
            //url to send the http request to
            URL url = new URL("http://awhitten02.students.cs.qub.ac.uk/pharmacy.php");
            //opens the connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //opens the input stream so the app can receive information from the server
            InputStream inputStream = con.getInputStream();
            //new instance of the string builder class
            StringBuilder sb = new StringBuilder();
            //wraps the input stream and buffers the input.
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            //while loop reads each row of the returned json
            while ((line = reader.readLine())!= null) {
                //assign each line to the string builder
                sb.append(line);
            }
            //apply the string builder to a json array
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
