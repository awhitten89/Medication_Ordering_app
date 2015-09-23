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

        //edit text views which the user will fill in their details
        name = (EditText)findViewById(R.id.enter_name);
        phone = (EditText)findViewById(R.id.enter_phone);

        //implements the look of the pop-up window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(((int) (width * .8)), (int) (height * .6));

        //gets the intent from the medication available class
        Intent intent = getIntent();
        pharmacy_id = intent.getIntExtra("pharmacy id",0);
        stock_id = intent.getIntExtra("stock id", 0);
        quantity = intent.getIntExtra("quantity", 0);

    }

    /**
     * button which when clicked updates the database.
     */
    public void submit(View view) {

        requestData("http://awhitten02.students.cs.qub.ac.uk/update_database.php");//uri passed to the request data method

        Toast toast = Toast.makeText(getApplicationContext(),"Medication reserved in pharmacy, collect when ready", Toast.LENGTH_LONG);
        toast.getView().setBackgroundColor(Color.WHITE);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.BLACK);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        //after the update the user is brought back to the main activity
        startActivity(new Intent(updateDatabase.this, MainActivity.class));
    }

    /**
     * Method which sets the keys and values of the hash map and executes the aysnctask
     * and passes in the values
     * @param uri
     */
    private void requestData(String uri) {

        entered_name = name.getText().toString();//gets the users name
        entered_phone = phone.getText().toString();//gets the users phone

        //instantiates the request package class
        requestPackage p = new requestPackage();
        p.setMethod("POST");//sets the type of method that will be used by the PHP script
        p.setUri(uri);//sets the uri address
        //sets the keys and values of the hash map
        p.setParam("pharmacy_id", pharmacy_id);
        p.setParam("stock_id", stock_id);
        p.setParam("quantity", quantity);
        p.setParam("name", entered_name);
        p.setParam("phone", entered_phone);
        //instantiates the asyntask and executes it.
        Update update = new Update();
        update.execute(p);

    }

    /**
     * AsyncTask which carries out the database query on a background thread
     */
    private class Update extends AsyncTask<requestPackage, Void, String> {

        @Override
        protected String doInBackground(requestPackage... params) {
            String content = httpManager.getData(params[0]);
            return content;
        }

    }
}
