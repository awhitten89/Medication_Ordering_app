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
public class Medication_Available extends Activity {

    TextView available, reserve;
    Button yes, cancel;
    Integer pharmacy_id, stock_id, quantity;
    String pharmacy_name, medication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.medication_available);

        available = (TextView) findViewById(R.id.med_available);
        reserve = (TextView) findViewById(R.id.reserve_med);
        yes = (Button) findViewById(R.id.btn_reserve);
        cancel = (Button) findViewById(R.id.btn_cancel);

        //sets the look of the display window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(((int) (width * .8)), (int) (height * .6));

        updateUI();
    }

    /**
     * Informs the user that the medication is available at the pharmacy
     */
    private void updateUI() {
        //gets intent from the main activity
        Intent intent = getIntent();
        pharmacy_id = intent.getIntExtra("pharmacy id", 0);
        stock_id = intent.getIntExtra("stock id", 0);
        quantity = intent.getIntExtra("quantity", 0);
        medication = intent.getStringExtra("medication");
        pharmacy_name = intent.getStringExtra("pharmacy name");

        available.setText(medication + " is available at " + pharmacy_name);
        reserve.setText("Would you like to reserve it?");
    }

    /**
     * If the cancel button is clicked the user is brought back to the main activity
     * @param view
     */
    public void cancel(View view) {
        startActivity(new Intent(Medication_Available.this, MainActivity.class));
    }

    /**
     * If the confirm button is clicked intent is sent to the update database class
     * @param view
     */
    public void medication_reserve(View view) {

        Intent pharmacyInfo = new Intent(getApplicationContext(), updateDatabase.class);
        pharmacyInfo.putExtra("pharmacy id", pharmacy_id);
        pharmacyInfo.putExtra("stock id", stock_id);
        pharmacyInfo.putExtra("quantity", quantity);

        startActivity(pharmacyInfo);
    }

}
