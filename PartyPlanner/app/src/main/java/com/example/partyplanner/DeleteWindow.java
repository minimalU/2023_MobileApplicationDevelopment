// FILE         : DeleteWindow.java
// PROJECT      : A2PartyPlanner
// PROGRAMMER(s): Beunard Lecaj, Jainish Patel, Raj Dudhat, Yujung Park
// FIRST VERSION: 2023-03-10
// DESCRIPTION  : This file is AppCompatActivity class file.
// DeleteWindow is a follow-up activity for Partylist as the user selects list item
// and DeleteWindow allows the user to delete the selected party list item from SQLite database.
// REFERENCE    : https://www.youtube.com/watch?v=toDZ9X5uSXw&list=PLzkhjlqMgxvBxi3Wyak9NicQI7UwhFU2O&index=89
// https://www.youtube.com/watch?v=312RhjfetP8

package com.example.partyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteWindow extends AppCompatActivity {
    private Button btn_delete;
    private Button btn_cancel;
    private TextView tv_details;

    DBHelper database_helper;

    private String partyDetails;
    private int partyID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_data);

        btn_delete = (Button)findViewById(R.id.button_delete);
        btn_cancel = (Button)findViewById(R.id.button_cancel);
        tv_details = (TextView)findViewById(R.id.textViewDeleteParty);

        Intent intent = getIntent();
        partyDetails = intent.getStringExtra("party");
        partyID = intent.getIntExtra("listID", -1);

        tv_details.setText(partyDetails);

        // delete list item and go back to Partylist activity
        btn_delete.setOnClickListener((v -> {
            database_helper = new DBHelper(DeleteWindow.this);
            database_helper.deleteItem(partyID);

            Intent goBackToList = new Intent(DeleteWindow.this, Partylist.class);
            startActivity(goBackToList);
        }));

        btn_cancel.setOnClickListener((v -> {
            Intent goBackToList = new Intent(DeleteWindow.this, Partylist.class);
            startActivity(goBackToList);
        }));
    }

}
