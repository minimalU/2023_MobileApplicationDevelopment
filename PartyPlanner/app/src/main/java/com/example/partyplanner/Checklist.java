package com.example.partyplanner;

//FILE: Checklist.java
//PROJECT : A01
//PROGRAMMER(S) : Jainish Patel, Raj Dudhat, Yujung Park, Beunard Lecaj.
//FIRST VERSION : 2023 - 02 - 11
//DESCRIPTION : An application for Party Planner.

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

        //checklistListView = findViewById(R.id.listview);
        items = new ArrayList<>();
        items.add("Finalize Guest");
        items.add("Send invitation");
        items.add("Grocery Shopping");
        items.add("Prepare Decoration");

//        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
//        checklistListView.setAdapter(adapter);
    }
}