package com.example.alan.medicationorderingapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class which connects to the server and sends the parameters nedded to update the database
 * Created by Alan on 15/09/2015.
 */
public class httpManager {

    /**
     * Method which gets the parameters to be sent to the database and connects to the server.
     * @param p
     * @return
     */
    public static String getData(requestPackage p) {

        BufferedReader reader = null;
        String uri = p.getUri();

        try {
            //opens a connection with the server
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(p.getMethod());//sets the method to POST
            //sends the parameters to the database via output stream
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(p.getEncodedParams());//sends the params
            wr.flush();//flushes the stream

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = reader.readLine())!= null) {

                sb.append(line);
            }
            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
