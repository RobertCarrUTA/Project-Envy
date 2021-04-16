package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FinanceActivity extends AppCompatActivity
{

    Button mWeeklyReportBtn, mMonthlyReportBtn, mBudgetAnalysisBtn, mReturnHomeFromFinanceBtn, mSignOutFromFinanceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        /*mWeeklyReportBtn = findViewById(R.id.weeklyReportBtn);
        mMonthlyReportBtn = findViewById(R.id.monthlyReportBtn);
        mBudgetAnalysisBtn = findViewById(R.id.budgetAnalysisBtn);*/

        mReturnHomeFromFinanceBtn = findViewById(R.id.returnHomeFromFinanceBtn);
        mSignOutFromFinanceBtn = findViewById(R.id.signOutFromFinanceBtn);

        //------------------------------------------------------------------------------------------
        // The below section is just for handling button presses that deal with navigation
        //
        // Let the user go to the weekly report page
        mWeeklyReportBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), WeeklyReportActivity.class));
            }
        });

        // Let the user go to the monthly report page
        mMonthlyReportBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MonthlyReportActivity.class));
            }
        });

        // Let the user go to the budget analysis page
        mBudgetAnalysisBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), BudgetAnalysisActivity.class));
            }
        });

        // Let the user go back to the homepage
        mReturnHomeFromFinanceBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        // Let the user sign out
        mSignOutFromFinanceBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(FinanceActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut(); //firebase command to delete token created for account locally
                startActivity(new Intent(getApplicationContext(), Login.class)); //back to login screen
            }
        });

        // End of navigation button section
        //------------------------------------------------------------------------------------------
    }
}