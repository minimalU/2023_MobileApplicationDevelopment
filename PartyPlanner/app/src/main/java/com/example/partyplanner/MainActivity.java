// FILE         : MainActivity.java
// PROJECT      : Party Planner
// PROGRAMMER(s): Beunard Lecaj, Jainish Patel, Raj Dudhat, Yujung Park
// FIRST VERSION: 2023-02-05
// DESCRIPTION  : This file is MainActivity class file. It is main entry for PartyPlanner application.

package com.example.partyplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Widget and UI elements
    CalendarView cv_Calendar;
    TextView tv_calDateText;
    Button btn_Plan;
    EditText setBudget;
    AirplaneReceiver pReceiver = null;
    BatteryReceiver bReceiver = null;
    IntentFilter aFilter = null;
    IntentFilter bFilter = null;

    private static final String TAG = "Main";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set calendar and textview for the selected date
        cv_Calendar = (CalendarView) findViewById(R.id.cal);
        tv_calDateText = (TextView)findViewById(R.id.tv_calDateText);
        btn_Plan = (Button)findViewById(R.id.button_plan);

        // selected date from calendar
        cv_Calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                tv_calDateText.setText(year + "-" + (month+1) + "-" + dayOfMonth);
            }
        });

        // get the date value and send the value to planner activity
        btn_Plan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String partyDate = tv_calDateText.getText().toString();

                Intent intent = new Intent(MainActivity.this, Planner.class);
                intent.putExtra("partyDate", partyDate);
                MainActivity.this.startActivity(intent);
            }
        });

        // A3 - Airplane, battery receivers
        pReceiver = new AirplaneReceiver(this);
        bReceiver = new BatteryReceiver(this);


        final long TIME = 60000; //stops service after one minute
        final Intent serviceIntent = new Intent(MainActivity.this, NotificationService.class);
        startService(serviceIntent);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                stopService(serviceIntent);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, TIME);


    }

    @Override
    protected void onStart() {

        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // A3 - filters for broadcast receivers
        aFilter = new IntentFilter();
        aFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(pReceiver, aFilter);

        bFilter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        registerReceiver(bReceiver, bFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();

        // A3 - broadcast receiver
        if(pReceiver != null){
            try{
                unregisterReceiver(pReceiver);
                unregisterReceiver(bReceiver);
            } catch (Exception e) {
                Log.e(TAG, "Broadcast receiver");
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // A3 - broadcast receiver
        if(pReceiver != null){
            try{
                unregisterReceiver(pReceiver);
                unregisterReceiver(bReceiver);
            } catch (Exception e) {
                Log.e(TAG, "Broadcast receiver");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // A3 - broadcast receiver
        if(pReceiver != null){
            try{
                unregisterReceiver(pReceiver);
                unregisterReceiver(bReceiver);
            } catch (Exception e) {
                Log.e(TAG, "Broadcast receiver");
            }
        }
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
                //intent = new Intent(this, Food.class);
                intent = new Intent(this, PartyFood.class);
                startActivity(intent);
                result = true;
                break;

            case R.id.menu_activity_explore:
                intent = new Intent(this, ScheduleCheck.class);
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


}