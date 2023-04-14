package com.example.partyplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class AirplaneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            boolean planeMode = intent.getBooleanExtra("state", false);
            if (planeMode == false) {
                Toast.makeText(context, "Airplane mode is off", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Airplane mode is on", Toast.LENGTH_SHORT).show();
            }
        }
       //  throw new UnsupportedOperationException("Not yet implemented");
    }
}