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

    public pfService() {
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int nResult = super.onStartCommand(intent, flags, startId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String message = null;
                    // check network connectivity and capabilities
                    ConnectivityManager cManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo nInfo = cManager.getActiveNetworkInfo();
                    if (nInfo != null && nInfo.isConnected()) {
                        Log.i("PartyApp","network connected");
                        if (nInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            message = "NETWORK-WIFI is working fine";
                        } else if (nInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            message = "NETWORK-CELLULAR is working fine";
                        } else {
                            message = "network issue";
                            Log.i("PartyApp", "NETWORK ISSUE");
                        }
                    }
                    Thread.sleep(2000);
                    Intent myIntent = new Intent("pfService");
                    myIntent.putExtra("msg", message);
                    sendBroadcast(myIntent);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return nResult;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
    }

}