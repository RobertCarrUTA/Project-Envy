package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class BudgetAnalysisActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    Button mReturnHomeFromBudgetAnalysisBtn, mSignOutFromBudgetAnalysisBtn;
    EditText mHowMuchSpentTextBox, mPercentageTextBox, mBelowOrAboveBudgetTextBox;

    Double percentage;
    String PercentageString;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    private Spinner spinner;
    private static final String[] paths = {"High Priority Spending", "Medium Priority Spending", "Low Priority Spending"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_analysis);

        mHowMuchSpentTextBox = findViewById(R.id.howMuchSpentTextBox);
        mPercentageTextBox = findViewById(R.id.percentageTextBox);
        mBelowOrAboveBudgetTextBox = findViewById(R.id.belowOrAboveBudgetTextBox);

        mReturnHomeFromBudgetAnalysisBtn = findViewById(R.id.returnHomeFromBudgetAnalysisBtn);
        mSignOutFromBudgetAnalysisBtn = findViewById(R.id.signOutFromBudgetAnalysisBtn);

        /*
            Maybe have some text boxes that show how much they have spent on what, like adding up the
            totals to all their priority levels and display them in text boxes, then show them whether
            or not they are above or below budget based on the sum of their expenses for the week or
            month. Probably a garbage way of doing it but we only got 7 days left so we probably can't
            be getting too fancy.
         */

        mBelowOrAboveBudgetTextBox.setText("You are still within your budget!");

        Spinner spinner = (Spinner) findViewById(R.id.prioritySelection_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        // The priority_array was made in strings.xml
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //------------------------------------------------------------------------------------------
        // Let the user go back to the homepage
        mReturnHomeFromBudgetAnalysisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        //------------------------------------------------------------------------------------------
        // Let the user sign out
        mSignOutFromBudgetAnalysisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The toast is what makes the message pop up when the user signs out
                Toast.makeText(BudgetAnalysisActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut(); //firebase command to delete token created for account locally
                startActivity(new Intent(getApplicationContext(), Login.class)); //back to login screen
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        // The below code allows for the remaining budget value to be represented in US currency
        Locale usa = new Locale("en", "US");
        Currency dollars = Currency.getInstance(usa);
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

        switch (position)
        {
            case 0: // High
                // need to be set to the total expenses for high priority for the current week
                mHowMuchSpentTextBox.setText(dollarFormat.format(310));
                percentage = (310.0 / 680.0) * 100.0;
                PercentageString = String.format("%.2f", percentage);
                percentage = Double.valueOf(PercentageString);
                mPercentageTextBox.setText("%" + percentage);
                break;
            case 1: // Medium
                // need to be set to the total expenses for medium priority for the current week
                mHowMuchSpentTextBox.setText(dollarFormat.format(220));
                percentage = (220.0 / 680.0) * 100.0;
                PercentageString = String.format("%.2f", percentage);
                percentage = Double.valueOf(PercentageString);
                mPercentageTextBox.setText("%" + percentage);
                break;
            case 2: // Low
                // need to be set to the total expenses for low priority for the current week
                mHowMuchSpentTextBox.setText(dollarFormat.format(150));
                percentage = (150.0 / 680.0) * 100.0;
                PercentageString = String.format("%.2f", percentage);
                percentage = Double.valueOf(PercentageString);
                mPercentageTextBox.setText("%" + percentage);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}