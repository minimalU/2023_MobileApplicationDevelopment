// FILE         : Planner.java
// PROJECT      : Party Planner
// PROGRAMMER(s): Beunard Lecaj, Jainish Patel, Raj Dudhat, Yujung Park
// FIRST VERSION: 2023-03-15
// DESCRIPTION  : This file is AppCompatActivity class file.
// Partylist allows the user to complete party information and store it into SQLite database
// REFERENCE    : 7_sql_database
// https://www.youtube.com/watch?v=toDZ9X5uSXw&list=PLzkhjlqMgxvBxi3Wyak9NicQI7UwhFU2O&index=89
// https://www.youtube.com/watch?v=312RhjfetP8


package com.example.partyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class Planner extends AppCompatActivity {

    // TextView tv_partyDate;
    Button btn_to_budget, btn_to_add, btn_to_next;
    EditText et_date, et_time, et_theme, et_guest, et_budget;
    TextView tv_budget;
    String partyDate;

    private static final String TAG = "Planner";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        btn_to_add = (Button)findViewById(R.id.buttonSave);
        btn_to_next = (Button)findViewById(R.id.buttonNext);

        et_date = (EditText)findViewById(R.id.editTextDate);
        et_time = (EditText)findViewById(R.id.editTextTime);
        et_theme = (EditText)findViewById(R.id.editTextTheme);
        et_guest = (EditText)findViewById(R.id.editTextGuestNo);
        et_budget = (EditText)findViewById(R.id.editTextBudget);

        try{
            Intent intent = getIntent();
            partyDate = intent.getStringExtra("partyDate");
            et_date.setText(partyDate);
        }
        catch (Exception e) {
            Log.d(TAG, "party date is not completed from MainActivity");
        }


        // add button onclick listener
        btn_to_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleData scheduleData;
                try {
                    scheduleData = new ScheduleData(-1, et_date.getText().toString(), et_time.getText().toString(), et_theme.getText().toString(), Float.parseFloat(et_budget.getText().toString()), Integer.parseInt(et_guest.getText().toString()));
                    // Toast.makeText(Planner.this, scheduleData.toString(), Toast.LENGTH_SHORT).show();
                    DBHelper dataBaseHelper = new DBHelper(Planner.this);
                    boolean returnValue = dataBaseHelper.addItem(scheduleData);
                    // Toast.makeText(Planner.this, "returnValue: " + returnValue, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(Planner.this, "Error: Party schedule cannot be added", Toast.LENGTH_SHORT).show();
                    // scheduleData = new ScheduleData(-1, "error", "error" , "error", 0, 0);
                    Log.e(TAG, "Adding ScheduleData");
                }
            }
        });

        btn_to_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int Budget = Integer.parseInt(et_budget.getText().toString());
                Intent intent = new Intent(Planner.this, Budget.class);
                intent.putExtra("BudgetVal", Budget);
                startActivity(intent);
            }
        });


//        View pLayout = findViewById(R.id.PlannerActivity);
//        pLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                InputMethodManager imm = (InputMethodManager)tv_budget.getContext()
//                        .getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(tv_budget.getWindowToken(), 0);
//            }
//        });
    }



    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuBuilder m = (MenuBuilder)menu;
        m.setOptionalIconsVisible(true);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        Intent intent = null;
        switch(item.getItemId()) {
            case R.id.menu_activity_main:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                result = true;
                break;
            case R.id.menu_activity_planner:
                intent = new Intent(this, Planner.class);
                startActivity(intent);
                result = true;
                break;

            case R.id.menu_activity_budget:
                intent = new Intent(this, Budget.class);
                startActivity(intent);
                result = true;
                break;
            case R.id.menu_activity_checklist:
                intent = new Intent(this, Checklist.class);
                startActivity(intent);
                result = true;
                break;
            case R.id.menu_activity_food:
                intent = new Intent(this, Food.class);
                startActivity(intent);
                result = true;
                break;
            case R.id.menu_activity_partylist:
                intent = new Intent(this, Partylist.class);
                startActivity(intent);
                result = true;
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }


    public class BackgroundTask extends AsyncTask<String, Void, Long> {

        Context context;
        DBHelper dataBaseHelper;
        long result;

        BackgroundTask(Context ctx){
            context = ctx;
            dataBaseHelper = new DBHelper(ctx);
        }

        @Override
        protected Long doInBackground(String... strings) {

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}