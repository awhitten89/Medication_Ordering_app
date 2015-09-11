package com.example.alan.medicationorderingapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alan on 14/08/2015.
 */
public class StockQuery extends Activity {

    private TextView testTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stock_query);

        testTxt = (TextView)findViewById(R.id.textView);

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String > {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            testTxt.setText(result);
            testTxt.setTextColor(Color.WHITE);
            testTxt.setBackgroundColor(Color.BLACK);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            String result = "";

            Intent intent = getIntent();
            String pharmacy = intent.getStringExtra("pharmacy");
            String id = intent.getStringExtra("id");
            String name = intent.getStringExtra("name");
            String potency = intent.getStringExtra("potency");
            String quantity = intent.getStringExtra("quantity");

            try {

                url = new URL("http://www.awhitten89.byethost7.com/database_con.php");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("pharmacy", pharmacy)
                        .appendQueryParameter("id", id)
                        .appendQueryParameter("name", name)
                        .appendQueryParameter("potency", potency)
                        .appendQueryParameter("quantity", quantity);

                String query = builder.build().getQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os)
                );

                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();

                InputStream is = new BufferedInputStream(conn.getInputStream());
                result = readStream(is);
                conn.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }return result;
        }

    }
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
