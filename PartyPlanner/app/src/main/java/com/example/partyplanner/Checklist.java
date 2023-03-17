// FILE         : Checklist.java
// PROJECT      : A2PartyPlanner
// PROGRAMMER(s): Beunard Lecaj, Jainish Patel, Raj Dudhat, Yujung Park
// FIRST VERSION: 2023-02-11
// DESCRIPTION  : This is AppCompatActivity class file for Checklist activity.
// Checklist activity allow user to create the checklist using list and adaptor, checkbox


package com.example.partyplanner;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Checklist extends AppCompatActivity {

    // create list and adaptor
    ListView checklistListView;
    ArrayList<String> items;
    TextView tv_Tv1;
    ArrayAdapter<String> adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);


        ProgressBar progressBar = (ProgressBar)findViewById(R.id.taskCompBar);
        CheckBox checkBox = (CheckBox)findViewById(R.id.cakecheck);
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.foodcheck);
        CheckBox checkBox3 = (CheckBox) findViewById(R.id.invcheck);
        CheckBox checkBox4 = (CheckBox) findViewById(R.id.tnccheck);
        CheckBox checkBox5 = (CheckBox) findViewById(R.id.venuecheck);

        tv_Tv1 = (TextView)findViewById(R.id.textView);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains("checkbox1") && preferences.getBoolean("checkbox1", false) == true) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);

        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox.isChecked()) {
                    editor.putBoolean("checkbox1", true);
                    progressBar.setProgress(progressBar.getProgress()+20);
                    editor.apply();
                } else {
                    editor.putBoolean("checkbox1", false);
                    progressBar.setProgress(progressBar.getProgress()-20);
                    editor.apply();
                }
            }
        });


        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        View cLayout = findViewById(R.id.checklistActivity);
        cLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)tv_Tv1.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tv_Tv1.getWindowToken(), 0);
            }
        });
        if (preferences.contains("checkbox2") && preferences.getBoolean("checkbox2", false) == true) {
            checkBox2.setChecked(true);
        } else {
            checkBox2.setChecked(false);

        }
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox2.isChecked()) {
                    editor.putBoolean("checkBox2", true);
                    progressBar.setProgress(progressBar.getProgress()+20);
                    editor.apply();
                } else {
                    editor.putBoolean("checkBox2", false);
                    progressBar.setProgress(progressBar.getProgress()-20);
                    editor.apply();
                }
            }
        });


        if (preferences.contains("checkbox3") && preferences.getBoolean("checkbox3", false) == true) {
            checkBox3.setChecked(true);
        } else {
            checkBox3.setChecked(false);

        }
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox3.isChecked()) {
                    editor.putBoolean("checkBox3", true);
                    progressBar.setProgress(progressBar.getProgress()+20);
                    editor.apply();
                } else {
                    editor.putBoolean("checkBox3", false);
                    progressBar.setProgress(progressBar.getProgress()-20);
                    editor.apply();
                }
            }
        });

        if (preferences.contains("checkbox4") && preferences.getBoolean("checkbox4", false) == true) {
            checkBox4.setChecked(true);
        } else {
            checkBox4.setChecked(false);

        }
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox4.isChecked()) {
                    editor.putBoolean("checkbox4", true);
                    progressBar.setProgress(progressBar.getProgress()+20);
                    editor.apply();
                } else {
                    editor.putBoolean("checkbox4", false);
                    progressBar.setProgress(progressBar.getProgress()-20);
                    editor.apply();
                }
            }
        });

        if (preferences.contains("checkbox5") && preferences.getBoolean("checkbox5", false) == true) {
            checkBox5.setChecked(true);
        } else {
            checkBox5.setChecked(false);

        }
        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox5.isChecked()) {
                    editor.putBoolean("checkbox5", true);
                    progressBar.setProgress(progressBar.getProgress()+20);
                    editor.apply();
                } else {
                    editor.putBoolean("checkbox5", false);
                    progressBar.setProgress(progressBar.getProgress()-20);
                    editor.apply();
                }
            }
        });
        //checklistListView = findViewById(R.id.listview);
        items = new ArrayList<>();
        items.add("Finalize Guest");
        items.add("Send invitation");
        items.add("Grocery Shopping");
        items.add("Prepare Decoration");

//        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
//        checklistListView.setAdapter(adapter);
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
}