package com.example.alan.medicationorderingapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends ActionBarActivity {
    private TextView contentTxt, selectedPharmacy;
    private Button savedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentTxt = (TextView)findViewById(R.id.scan_content);
        selectedPharmacy = (TextView)findViewById(R.id.selected_pharmacy);

        Intent intent = getIntent();
        String name = intent.getStringExtra("pharmacy");

        if(name!=null){
            selectedPharmacy.setText("PHARMACY SELECTED: "+name);
            selectedPharmacy.setBackgroundResource(R.color.blue_grey);
        }

        savedBtn = (Button)findViewById(R.id.btn_selectsavedpharmacy);
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

            //display it on screen
            contentTxt.setText("MEDICATION REQUIRED: " + scanContent);
            contentTxt.setBackgroundResource(R.color.blue_grey);

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

}

