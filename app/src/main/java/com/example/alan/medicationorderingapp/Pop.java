package com.example.alan.medicationorderingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Alan on 06/08/2015.
 */
public class Pop extends Activity {

    private TextView pharmacyQ, pharmacyID;
    private Button yesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //sets the pop up to cover 80% width and 60%
        getWindow().setLayout(((int) (width * .8)), (int) (height * .6));

        pharmacyQ = (TextView) findViewById(R.id.select_pharmacy);
        pharmacyID = (TextView) findViewById(R.id.pop_pharmacyID);
        yesBtn = (Button) findViewById(R.id.btn_select_pharmacy);

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String id = intent.getStringExtra("pharmacy id");

        pharmacyQ.setText("Select " + name);
        pharmacyID.setText("Pharmacy ID: " + id);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent popIntent = new Intent(getApplicationContext(), MainActivity.class);
                popIntent.putExtra("pharmacy name", name);
                popIntent.putExtra("pharmacy id", id);
                startActivity(popIntent);
            }
        });
    }
}