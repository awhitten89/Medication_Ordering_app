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

    TextView a,b,c,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stock_query);

        a = (TextView)findViewById(R.id.A);
        b = (TextView)findViewById(R.id.B);
        c = (TextView)findViewById(R.id.C);
        d = (TextView)findViewById(R.id.D);

        new GetStockAvailable().execute(new Stock_Connector());

    }

    /**
     *
     * @param jsonArray
     */
    private void isStockAvailable(JSONArray jsonArray) {

        Intent intent = getIntent();
        String pharmacy_id = intent.getStringExtra("pharmacy id");
        String stock_id = intent.getStringExtra("stock id");
        String medication = intent.getStringExtra("medication");
        String quantity = intent.getStringExtra("quantity");

        a.setText(pharmacy_id);
        b.setText(stock_id);
        c.setText(medication);
        d.setText(quantity);

        for (int i =0; i < jsonArray.length(); i++) {


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
