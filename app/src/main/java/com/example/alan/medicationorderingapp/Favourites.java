package com.example.alan.medicationorderingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by Alan on 18/08/2015.
 */
public class Favourites extends Activity {

    private Button btn_one, btn_two, btn_three;
    private String pharmacy_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.favourites);

        btn_one = (Button)findViewById(R.id.btn_favourites_one);
        btn_two = (Button)findViewById(R.id.btn_favourites_two);
        btn_three = (Button)findViewById(R.id.btn_favourites_three);

        Intent intent = getIntent();
        pharmacy_name = intent.getStringExtra("pharmacy");
    }
}
