package com.example.alan.medicationorderingapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.app.ToolbarActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private TextView contentTxt, selectedPharmacy;
    private String name, file = "mydata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentTxt = (TextView)findViewById(R.id.scan_content);
        selectedPharmacy = (TextView)findViewById(R.id.selected_pharmacy);

        Intent intent = getIntent();
        name = intent.getStringExtra("pharmacy");

        if(name!=null){

            selectedPharmacy.setText("PHARMACY SELECTED: "+name);
            selectedPharmacy.setBackgroundColor(Color.parseColor("#263238"));

            try {
                FileInputStream fin = openFileInput(file);
                int c;
                String temp = "";

                while ((c = fin.read())!=-1){
                    temp = temp + Character.toString((char)c);
                }
                contentTxt.setText("MEDICATION REQUIRED: " + temp);
                contentTxt.setBackgroundColor(Color.parseColor("#263238"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * event handler for scan button
     * @param view view of the activity
     */
    public void scanQR(View view){

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt(String.valueOf(R.string.txt_scan_qrcode));
        integrator.setResultDisplayDuration(0);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }


    /**
     * function handle scan result
     * @param requestCode scanned code
     * @param resultCode  result of scanned code
     * @param intent intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
           String scanContent = scanningResult.getContents();

            //put the scan result in a new intent
            try {
                FileOutputStream fout = openFileOutput(file, MODE_WORLD_READABLE);
                fout.write(scanContent.getBytes());
                fout.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast toast = Toast.makeText(getApplicationContext(),"Medication recorded. Now select pharmacy", Toast.LENGTH_LONG);
            toast.getView().setBackgroundColor(Color.WHITE);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.BLACK);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Event handler for the search google maps button
     * @param view
     */
    public void searchMap(View view) {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));

    }

    /**
     *
     * @param view
     */
    public void searchFavourites(View view){
        startActivity(new Intent(MainActivity.this, Favourites.class));
    }

    /**
     * Event handler for the search stock button. Which calla the stack query class.
     * Sends the information about the medication and pharmacy to class which will
     * carry out the search.
     * @param view
     */
    public void searchStock(View view){

        if(selectedPharmacy!=null && contentTxt!=null) {
            String pharmacy = selectedPharmacy.getText().toString();

            String whole = contentTxt.getText().toString();
            String[] separated = whole.split("_");
            String id = separated[0];
            String medication = separated[1];
            String potency = separated[2];
            String quantity = separated[3];

            Intent dataIntent = new Intent(getApplicationContext(), StockQuery.class);
            dataIntent.putExtra("pharmacy", pharmacy);
            dataIntent.putExtra("id", id);
            dataIntent.putExtra("medication", medication);
            dataIntent.putExtra("potency", potency);
            dataIntent.putExtra("quantity", quantity);

            startActivity(dataIntent);

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Unable to search please select medication and pharmacy", Toast.LENGTH_LONG);
            toast.getView().setBackgroundColor(Color.WHITE);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.BLACK);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
}

