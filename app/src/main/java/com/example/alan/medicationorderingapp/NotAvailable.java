package com.example.alan.medicationorderingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by Alan on 13/09/2015.
 */
public class NotAvailable extends Activity {

    TextView not_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.not_available);

        not_available = (TextView) findViewById(R.id.not_available);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(((int) (width * .8)), (int) (height * .6));

        Intent intent = getIntent();
        String medication = intent.getStringExtra("medication");
        String pharmacy = intent.getStringExtra("pharmacy name");

        not_available.setText("Unfortunately " + medication + " is not available in " + pharmacy + " at present.");
    }

    /**
     *Method which implements the ok button and calls the main activity.
     *
     * @param view
     */
    public void returnToMain(View view) {
        startActivity(new Intent(NotAvailable.this, MainActivity.class));
    }
}
