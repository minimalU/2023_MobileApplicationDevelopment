// FILE         : PartyList.java
// PROJECT      : Party Planner
// PROGRAMMER(s): Beunard Lecaj, Jainish Patel, Raj Dudhat, Yujung Park
// FIRST VERSION: 2023-03-15
// DESCRIPTION  : This file is AppCompatActivity class file.
// Partylist reads the data base table and displays as a list
// REFERENCE    : 7_sql_database
// https://www.youtube.com/watch?v=312RhjfetP8
// https://www.youtube.com/watch?v=6GFpOlSiR7Q&list=PLzkhjlqMgxvBxi3Wyak9NicQI7UwhFU2O&index=73
// https://www.youtube.com/watch?v=toDZ9X5uSXw&list=PLzkhjlqMgxvBxi3Wyak9NicQI7UwhFU2O&index=89

package com.example.partyplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import java.util.ArrayList;
import java.util.List;

public class Partylist extends AppCompatActivity {

    Button button_list;
    ListView party_list;
    PartyData scheduleSelected;
    DBHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partylist);

        button_list = findViewById(R.id.buttonList);
        party_list = findViewById(R.id.listViewPartylist);

        dbHelper = new DBHelper(Partylist.this);


        button_list.setOnClickListener((v -> {
            List<PartyData> allList = dbHelper.getSchedule();
            ArrayAdapter partylistArrayAdaptor = new ArrayAdapter<PartyData>(Partylist.this, android.R.layout.simple_list_item_1, allList);
            party_list.setAdapter(partylistArrayAdaptor);

            //displayParty();

        }));


        // setOnItemClick
        party_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                scheduleSelected = (PartyData)parent.getItemAtPosition(position);

                String partyItem = parent.getItemAtPosition(position).toString();
                int listID = dbHelper.getID(scheduleSelected);

                Toast.makeText(Partylist.this, String.valueOf(position), Toast.LENGTH_SHORT).show();

                Intent deleteScreen = new Intent(Partylist.this, DeleteWindow.class);
                deleteScreen.putExtra("party", partyItem);
                deleteScreen.putExtra("listID", listID);
                startActivity(deleteScreen);
            }

        });
    }

    public void displayParty() {
        List<PartyData> allList = new ArrayList<>();

        String str_uri = "content://com.example.partyplanner/party";
        Uri uri = new Uri.Builder().build().parse(str_uri);

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
    }



    // menu option
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
