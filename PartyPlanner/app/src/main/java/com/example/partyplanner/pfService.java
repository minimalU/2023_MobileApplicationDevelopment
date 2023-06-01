package com.example.partyplanner;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class pfService extends Service {

    private static final String TAG = "pfService";

    public pfService() {
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onCreate() {
    }

    // user startService and stop as many as they want
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{
            String message = null;
            // check network connectivity and capabilities
            ConnectivityManager cManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cManager.getActiveNetworkInfo();
            if (nInfo != null && nInfo.isConnected()) {
                Log.i("PartyApp","network connected");
                if (nInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    message = "WIFI NETWORK is working fine";
                } else if (nInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    message = "CELLULAR NETWORK is working fine";
                } else {
                    message = "network issue";
                    Log.i("PartyApp", "NETWORK ISSUE");
                }
            }
            Intent myIntent = new Intent("pfService");
            myIntent.putExtra("msg", message);
            sendBroadcast(myIntent);

            // User does not stopService() quickly enough
            Thread.sleep(60);
        } catch (Exception e) {
            Log.e(TAG, "Broadcast receiver");
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // no binding
        return null;
    }

    @Override
    public void onDestroy() {
    }

}