package com.example.alan.medicationorderingapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;

/**
 * Created by Alan on 14/08/2015.
 */
public class StockQuery extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stock_query);
        new GetStockAvailable().execute(new Stock_Connector());

    }

    /**
     *
     * @param jsonArray
     */
    private void isStockAvailable(JSONArray jsonArray) {

        Intent intent = getIntent();
        Integer pharmacy_id = Integer.valueOf(intent.getStringExtra("pharmacy id"));
        Integer stock_id = Integer.valueOf (intent.getStringExtra("stock id"));
        String medication = intent.getStringExtra("medication");
        String pharmacy_name = intent.getStringExtra("pharmacy name");
        Integer quantity = Integer.valueOf(intent.getStringExtra("quantity"));

        Boolean flag = false;

        for (int i =0; i < jsonArray.length(); i++) {


                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int database_pharmacy_id = jsonObject.getInt("pharmacy_id");
                    int database_stock_id = jsonObject.getInt("stock_id");
                    int database_quantity = jsonObject.getInt("drug_quantity");

                    //if else statement to check if the pharmacy has the medication in stock and if it has the right quantity
                    if(database_pharmacy_id == pharmacy_id && database_stock_id == stock_id && database_quantity >= quantity){
                        //flag becomes true if the database stock value is found
                        flag = true;
                        //break the for loop when flag is true.
                        break;

                    } else {
                        flag = false;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    //if else statement which calls the appropriate class depending on whether the database has the users medication.
    if (flag){

            Intent stockIntent = new Intent(getApplicationContext(), Medication_Available.class);
            stockIntent.putExtra("pharmacy id",pharmacy_id);
            stockIntent.putExtra("stock id", stock_id);
            stockIntent.putExtra("medication", medication);
            stockIntent.putExtra("quantity", quantity);
            stockIntent.putExtra("pharmacy name", pharmacy_name);

            startActivity(stockIntent);

        } else {

            Intent notAvailIntent = new Intent(getApplicationContext(), NotAvailable.class);
            notAvailIntent.putExtra("medication", medication);
            notAvailIntent.putExtra("pharmacy name", pharmacy_name);

            startActivity(notAvailIntent);
        }
    }

    /**
     *
     */
    private class GetStockAvailable extends AsyncTask<Stock_Connector,Long,JSONArray> {

        @Override
        protected JSONArray doInBackground(Stock_Connector... params) {

            return params[0].SearchStockAvailable();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            isStockAvailable(jsonArray);
        }
    }
}
