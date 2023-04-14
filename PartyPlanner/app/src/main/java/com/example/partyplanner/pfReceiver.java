package com.example.partyplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class pfReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");

        String action = intent.getAction();
        if (action.equals("pfService")) {
            String receivedMsg = intent.getStringExtra("msg");
            String printMsg = receivedMsg;
            Toast.makeText(context, printMsg, Toast.LENGTH_SHORT).show();
        }
    }
}