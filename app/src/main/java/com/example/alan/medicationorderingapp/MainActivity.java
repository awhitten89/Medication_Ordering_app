package com.example.alan.medicationorderingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
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
    private String pharmacy_name, pharmacy_id, file = "mydata", temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textviews that will display the scan content and the pharmacy name sfter they have been selected
        contentTxt = (TextView)findViewById(R.id.scan_content);
        selectedPharmacy = (TextView)findViewById(R.id.selected_pharmacy);

        //gets the intent from the confirm selection class
        Intent intent = getIntent();
        pharmacy_name = intent.getStringExtra("pharmacy name");
        pharmacy_id = intent.getStringExtra("pharmacy id");

        // if bith the medication and pharmacy are selected they are displayed on the UI
        if(pharmacy_name!=null){

            //pharmacy selection set in a textview
            selectedPharmacy.setText("PHARMACY SELECTED: "+pharmacy_name);
            selectedPharmacy.setBackgroundColor(Color.parseColor("#263238"));

            //file is opened and the medication taken from the QR code is displayed on UI
            try {
                FileInputStream fin = openFileInput(file);
                int c;
                temp = "";

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
     * event handler for scan button instantiates an instance of the Intent Integrator and
     * send an Intent to the barcode scanner of the device.
     * @param view view of the activity
     */
    public void scanQR(View view){

        //create an instance of the integrator class which
        // will send the scan intent to the devices barcode scanner
        IntentIntegrator integrator = new IntentIntegrator(this);
        //sets the type of code that will be scanned
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt(String.valueOf(R.string.txt_scan_qrcode));
        integrator.setResultDisplayDuration(0);
        integrator.setCameraId(0);// Use a specific camera of the device
        integrator.initiateScan();//starts the scan
    }


    /**
     * function handle scan result receives the intent from the scanned activity
     * @param requestCode scanned code
     * @param resultCode  result of scanned code
     * @param intent intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //retrieve scan result and parse it into an instance of the intent result class
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        //check we have a result
        if (scanningResult != null) {
            //save the result to a string variable
           String scanContent = scanningResult.getContents();
            //put the scan result in a new file
            try {
                FileOutputStream fout = openFileOutput(file, MODE_WORLD_READABLE);
                fout.write(scanContent.getBytes());//write the result to the file
                fout.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Toast message to inform the user that the database has been recorded.
            Toast toast = Toast.makeText(getApplicationContext(),"Medication recorded. Now select pharmacy", Toast.LENGTH_LONG);
            toast.getView().setBackgroundColor(Color.WHITE);
            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
            v.setTextColor(Color.BLACK);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }else{
            //if no result is recorded inform the user
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Event handler for the search google maps button launches the maps activity
     * @param view
     */
    public void searchMap(View view) {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));

    }

    /**
     * Event handler for the search stock button. Which calls the stock query class.
     * Sends the information about the medication and pharmacy to class which will
     * carry out the search.
     * @param view
     */
    public void searchStock(View view){

        //if statement checks that the medication has been recorded and the pharmacy selected
        if(selectedPharmacy!=null && contentTxt!=null) {
            //separates the QR result string into individual elements
            String[] separated = temp.split("_");
            String stock_id = separated[0];
            String medication = separated[1];
            String quantity = separated[3];
            //Intent send to the stock query class giving it the information it needs to carry out the
            //stock check.
            Intent dataIntent = new Intent(getApplicationContext(), StockQuery.class);
            //pharmacy name and id have been returned to the main activity after selection on the map
            dataIntent.putExtra("pharmacy name", pharmacy_name);
            dataIntent.putExtra("pharmacy id", pharmacy_id);
            dataIntent.putExtra("stock id", stock_id);
            dataIntent.putExtra("medication", medication);
            dataIntent.putExtra("quantity", quantity);
            //starts the StockQuery activity and sends the intent
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

