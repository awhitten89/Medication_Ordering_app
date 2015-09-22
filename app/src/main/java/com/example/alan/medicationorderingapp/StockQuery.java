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

        //instantiates and executes the asynctask and passes an instance of the stock connector class
        //to it
        new GetStockAvailable().execute(new Stock_Connector());
    }

    /**
     * Method which checks if the stock is available. Receives the JSON array from the asynctask
     * and receives the intent from the main activity. Uses the information to makes sure the medication is in stock
     * in the pharmacy
     * @param jsonArray
     */
    private void isStockAvailable(JSONArray jsonArray) {
        //receives an intent from the main activity which holds the information regarding the medication required
        //and the pharmacy whose stock will be searched.
        Intent intent = getIntent();
        Integer pharmacy_id = Integer.valueOf(intent.getStringExtra("pharmacy id"));
        Integer stock_id = Integer.valueOf (intent.getStringExtra("stock id"));
        String medication = intent.getStringExtra("medication");
        String pharmacy_name = intent.getStringExtra("pharmacy name");
        Integer quantity = Integer.valueOf(intent.getStringExtra("quantity"));

        Boolean flag = false;
        //loop to iterate through the json array
        for (int i =0; i < jsonArray.length(); i++) {

                try {
                    //receives the individual json object from the array
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //uses the json keys to get the values which will be searched.
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
                        //if the medication not found in the pharmacy then flag remains false
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
     * AsyncTask runs the stock_connector class which querys the database and returns the results
     * as a json array to the isStockAvailable class
     */
    private class GetStockAvailable extends AsyncTask<Stock_Connector,Long,JSONArray> {

        /**
         * runs the database connection class in the background
         */
        @Override
        protected JSONArray doInBackground(Stock_Connector... params) {

            return params[0].SearchStockAvailable();
        }

        /**
         * Gets the resulting JSON array result of the database query and sends it to the isStockAvailable
         * method
         * @param jsonArray
         */
        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            isStockAvailable(jsonArray);
        }
    }
}
