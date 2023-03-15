package com.example.partyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class Planner extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    // TextView tv_partyDate;
    Button btn_to_budget;
    TextView tv_budget;
    private TextView tv_guestNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        btn_to_budget = (Button) findViewById(R.id.button);
        tv_budget = (TextView) findViewById(R.id.editTextBudget);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        // Numberpicker
        tv_guestNumber=(TextView) findViewById(R.id.tv_guestNumber);

        numberPicker.setMinValue(0);
        numberPicker.setMinValue(5);
        numberPicker.setValue(3);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                tv_guestNumber.setText(newVal);
            }
        });

        btn_to_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // int Budget = Integer.parseInt(tv_budget.getText().toString());
                Intent intent = new Intent(Planner.this, Budget.class);
                //intent.putExtra("BudgetVal", Budget);
                startActivity(intent);
            }

        });




        View pLayout = findViewById(R.id.PlannerActivity);
        pLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)tv_budget.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tv_budget.getWindowToken(), 0);
            }
        });
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        tv_guestNumber.setText(newVal);
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
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }
}