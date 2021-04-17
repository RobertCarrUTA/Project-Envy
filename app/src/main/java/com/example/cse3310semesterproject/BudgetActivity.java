package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class BudgetActivity extends AppCompatActivity
{

    Button mReturnHomeFromBudgetBtn, mPrioritiesBtn, mExpensesBtn, mLoanInformationBtn, mSignOutFromBudgetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        mReturnHomeFromBudgetBtn = findViewById(R.id.returnHomeFromBudgetBtn);
        mPrioritiesBtn = findViewById(R.id.prioritiesBtn);
        mExpensesBtn = findViewById(R.id.expensesBtn);
        mLoanInformationBtn = findViewById(R.id.loanInformationBtn);

        mSignOutFromBudgetBtn = findViewById(R.id.signOutFromBudgetBtn2);

        // Let the user go to the priorities page to do anything priority related with their
        // budget
        mPrioritiesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), PrioritiesActivity.class));
            }
        });

        // Let the user go to the expenses page to do anything expense/income related with their
        // budget
        mExpensesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), ExpenseActivity.class));
            }
        });

        // Let the user go to the loan page to do anything loan related
        // ***** Causing issues for some reason *****
        mLoanInformationBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), LoanActivity.class));
            }
        });

        // Let the user go back to the homepage
        mReturnHomeFromBudgetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        // Let the user sign out
        mSignOutFromBudgetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(BudgetActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut(); //firebase command to delete token created for account locally
                startActivity(new Intent(getApplicationContext(), Login.class)); //back to login screen
            }
        });
    }
}