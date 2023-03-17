package com.example.partyplanner;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Explore extends AppCompatActivity {

    ListView listView;

    String[] pages = { "page1", "page2", "page3",
            "page4"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        listView = findViewById(R.id.listview);

        ArrayAdapter adapter = new ArrayAdapter<String> (
                this,
                R.layout.activity_explore,
                pages
        );

        listView.setAdapter(adapter);
    }
}