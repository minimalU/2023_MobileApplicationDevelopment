
package com.example.partyplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

public class PartyFood extends AppCompatActivity {

    private static final String TAG = "PartyFood";
    private Button btService;

    private boolean bService = false;
    private WebView webView;
    private pfReceiver receiver;


    AirplaneReceiver pReceiver = null;
    IntentFilter filter = null;

    BatteryReceiver bReceiver = null;
    IntentFilter bFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partyfood);

        btService = findViewById(R.id.btn_check);
        webView = (WebView) findViewById(R.id.wv_partyfood);

        final Intent intent = new Intent(this, pfService.class);

        // user checks connection as many as they want
        btService.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bService) {
                        startService(intent);
                        btService.setText("STOP");
                    }
                    else {
                        stopService(intent);
                        btService.setText("CHECK");
                    }
                    bService = !bService;
                }
        }));

        // A3 - pfService BRAODCAST receiver
        receiver = new pfReceiver(this);
        IntentFilter intentFilter = new IntentFilter("pfService");
        registerReceiver(receiver, intentFilter);



        // setup webview
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://houseandhome.com/category/food/");

        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);


        // A3 - AirPlane mode BRAODCAST receiver
        pReceiver = new AirplaneReceiver(this);
    }

    // webview use go back
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else if (webView.canGoForward()) {
            webView.goForward();
        }else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        // A3 - broadcast receiver
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(pReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // A3 - broadcast receiver
        if(pReceiver != null){
            try{
                unregisterReceiver(pReceiver);
            } catch (Exception e) {
                Log.e(TAG, "ScheduleData completion");
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // A3 - broadcast receiver
        if(pReceiver != null){
            try{
                unregisterReceiver(pReceiver);
            } catch (Exception e) {
                Log.e(TAG, "ScheduleData completion");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // A3 - broadcast receiver
        if(pReceiver != null){
            try{
                unregisterReceiver(pReceiver);
            } catch (Exception e) {
                Log.e(TAG, "ScheduleData completion");
            }
        }
    }

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

//            case R.id.menu_activity_explore:
//                intent = new Intent(this, Explore.class);
//                startActivity(intent);
//                result = true;
//                break;
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
