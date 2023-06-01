package com.example.partyplanner;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class pfReceiver extends BroadcastReceiver {

    private Context context;

    // constructor
    public pfReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals("pfService")) {
            String receivedMsg = intent.getStringExtra("msg");
            String printMsg = receivedMsg;

            // display result using Snack bar
            Snackbar.make(((Activity)this.context).findViewById(android.R.id.content),
                    receivedMsg, Snackbar.LENGTH_LONG).show();

        }
    }
}