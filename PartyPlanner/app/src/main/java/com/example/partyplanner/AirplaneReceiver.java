package com.example.partyplanner;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AirplaneReceiver extends BroadcastReceiver {

    private Context context;

    // constructor
    public AirplaneReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // airplane status braodcast
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            boolean planeMode = intent.getBooleanExtra("state", false);
            if (planeMode == false) {
                Snackbar.make(((Activity)this.context).findViewById(android.R.id.content),
                        "Airplane mode OFF", Snackbar.LENGTH_LONG).show();
            }
            else {
                Snackbar.make(((Activity)this.context).findViewById(android.R.id.content),
                        "Airplane mode ON", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}