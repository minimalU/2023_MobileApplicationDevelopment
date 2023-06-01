package com.example.partyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.content.DialogInterface;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Budget extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    TextView budgetText;
    private double initialBudget;

    private SeekBar seekBar5, seekBar3, seekBar2;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        budgetText = findViewById(R.id.BudgetValText);

        seekBar5 = findViewById(R.id.seekBar5);
        seekBar3 = findViewById(R.id.seekBar3);
        seekBar2 = findViewById(R.id.seekBar2);

        seekBar5.setOnSeekBarChangeListener(this);
        seekBar3.setOnSeekBarChangeListener(this);
        seekBar2.setOnSeekBarChangeListener(this);

        double revBudget;
        try {
            Intent intent = getIntent();
            revBudget = intent.getDoubleExtra("budget", 0.00);
            budgetText.setText("CAD " + revBudget);
        } catch (Exception e) {
            Log.e("Budget", "Error");
            revBudget = 0.00;
            budgetText.setText("CAD " + revBudget);
        }
        initialBudget = revBudget;

        View cLayout = findViewById(R.id.BudgetActivity);
        cLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) budgetText.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(budgetText.getWindowToken(), 0);
            }
        });

        Button updateBudgetButton = findViewById(R.id.update_budget_button);
        updateBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateBudgetDialog();
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuBuilder m = (MenuBuilder) menu;
        m.setOptionalIconsVisible(true);
        return true;
    }

    private void showUpdateBudgetDialog() {
        updateBudgetText();
        double remainingBudget = initialBudget - ((seekBar5.getProgress() * 0.01) * initialBudget + (seekBar3.getProgress() * 0.01) * initialBudget + (seekBar2.getProgress() * 0.01) * initialBudget);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Budget");
        builder.setMessage("Are you sure you want to update the budget?\nRemaining budget: CAD " + String.format("%.2f", remainingBudget));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Budget.this, "Success!!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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

            case R.id.menu_activity_explore:
                intent = new Intent(this, ScheduleCheck.class);
                startActivity(intent);
                result = true;
                break;

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


    private void updateBudgetText() {
        double totalSpent = (seekBar5.getProgress() * 0.01) * initialBudget + (seekBar3.getProgress() * 0.01) * initialBudget + (seekBar2.getProgress() * 0.01) * initialBudget;
        double remainingBudget = initialBudget - totalSpent;
        budgetText.setText("CAD " + String.format("%.2f", remainingBudget));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        updateBudgetText();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
