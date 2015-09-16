package com.example.alan.medicationorderingapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Alan on 13/09/2015.
 */
public class updateDatabase extends Activity {

    Integer pharmacy_id, stock_id, quantity;
    EditText name, phone;
    String entered_name, entered_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.update_database);

        name = (EditText)findViewById(R.id.enter_name);
        phone = (EditText)findViewById(R.id.enter_phone);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(((int) (width * .8)), (int) (height * .6));

        Intent intent = getIntent();
        pharmacy_id = intent.getIntExtra("pharmacy id",0);
        stock_id = intent.getIntExtra("stock id", 0);
        quantity = intent.getIntExtra("quantity", 0);

    }

    //button which when clicked updates the database.
    public void submit(View view) {

        requestData("http://awhitten02.students.cs.qub.ac.uk/update_database.php");

        Toast toast = Toast.makeText(getApplicationContext(),"Medication reserved in pharmacy", Toast.LENGTH_LONG);
        toast.getView().setBackgroundColor(Color.WHITE);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.BLACK);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        startActivity(new Intent(updateDatabase.this, MainActivity.class));
    }

    private void requestData(String uri) {

        entered_name = name.getText().toString();
        entered_phone = phone.getText().toString();

        requestPackage p = new requestPackage();
        p.setMethod("POST");
        p.setUri(uri);

        p.setParam("pharmacy_id", pharmacy_id);
        p.setParam("stock_id", stock_id);
        p.setParam("quantity", quantity);
        p.setParam("name", entered_name);
        p.setParam("phone", entered_phone);

        Update update = new Update();
        update.execute(p);

    }

    private class Update extends AsyncTask<requestPackage, Void, String> {

        @Override
        protected String doInBackground(requestPackage... params) {
            String content = httpManager.getData(params[0]);
            return content;
        }

    }
}
