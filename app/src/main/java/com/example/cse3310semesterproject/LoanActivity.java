package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoanActivity extends AppCompatActivity
{

    EditText mUserLoanEntry, mUserInterestEntry, mFinalCostTextBox;
    Button  mReturnHomeFromLoanBtn, signOutFromLoanBtn, mCalculateLoanBtn;

    Double LoanAmount, InterestRate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        // This represents the text entry box where the user inputs their loan amount
        mUserLoanEntry = findViewById(R.id.userLoanEntry);
        // This represents the text entry box where the user inputs their interest rate
        mUserInterestEntry = findViewById(R.id.userInterestEntry);
        // This represents the text entry box where the actual price of the loan is shown
        mFinalCostTextBox = findViewById(R.id.finalCostTextBox);

        mReturnHomeFromLoanBtn = findViewById(R.id.returnHomeFromLoanBtn);
        mCalculateLoanBtn = findViewById(R.id.calculateLoanBtn);
        signOutFromLoanBtn = findViewById(R.id.signOutFromLoanBtn);

        // When the user clicks the calculate cost button, we calculate the loan and show them
        // the cost
        mReturnHomeFromLoanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // This is where we save the loan amount the user entered into a value we can use
                LoanAmount = Double.valueOf(mUserLoanEntry.getText().toString());

                // This is where we save the interest amount the user entered into a value we can use
                InterestRate = Double.valueOf(mUserInterestEntry.getText().toString());

                // To set the text in the entry box
                // The documentation for setText is here: https://developer.android.com/reference/android/widget/EditText#setText(java.lang.CharSequence,%20android.widget.TextView.BufferType)
            }
        });

        // Let the user go back to the homepage
        mReturnHomeFromLoanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        // Let the user sign out
        signOutFromLoanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(LoanActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });




    }
}