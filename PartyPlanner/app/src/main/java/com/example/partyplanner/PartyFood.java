
package com.example.partyplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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

    private Button btService;
    private TextView txService;
    private boolean bService = false;
    private WebView webView;
    private pfReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partyfood);

        btService = findViewById(R.id.btn_check);
        txService = findViewById(R.id.tv_check_result);
        webView = (WebView) findViewById(R.id.wv_partyfood);

        btService.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exeService();
            }
        }));

        receiver = new pfReceiver();
        IntentFilter intentFilter = new IntentFilter("pfService");
        registerReceiver(receiver, intentFilter);

        // setup webview
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://houseandhome.com/category/food/");

        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
    }

    protected void exeService() {
        Intent intent = new Intent(this, pfService.class);
        if (!bService) {
            startService(intent);
            btService.setText("STOP");
            txService.setText("checking connectivity");
        }
        else {
            stopService(intent);
            btService.setText("CHECK");
            txService.setText("connectivity check is done");
        }
        bService = !bService;
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
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
