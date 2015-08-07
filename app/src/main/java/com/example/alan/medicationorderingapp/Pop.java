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

    private TextView pharmacyQ;
    private Button yesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(((int)(width*.8)),(int)(height*.6));

        pharmacyQ = (TextView)findViewById(R.id.select_pharmacy);
        yesBtn = (Button)findViewById(R.id.btn_select_pharmacy);

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("name");

        pharmacyQ.setText("Select "+name);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent popIntent = new Intent(getApplicationContext(),MainActivity.class);

                popIntent.putExtra("pharmacy",name);

                startActivity(popIntent);
            }
        });
    }
}
