package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    // This sets us up to use our buttons that we have on our homepage
    Button mUserAccountBtn, mFinancialReportBtn, mBudgetingBtn, mSignOutBtn;

    // If we have time, I might try to add the drawer menu back in, but for now, just to have the very basics of the app running
    // this will do

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserAccountBtn = findViewById(R.id.UserAccountBtn);
        mFinancialReportBtn = findViewById(R.id.FinancialReportBtn);
        mBudgetingBtn = findViewById(R.id.BudgetingBtn);
        mSignOutBtn = findViewById(R.id.SignOutBtn);

        mUserAccountBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // This if condition is the one that determine if the login information was correct and tells the user
                // the login was successful then takes them to the homepage (MainActivity)
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
            }
        });

        mFinancialReportBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // This if condition is the one that determine if the login information was correct and tells the user
                // the login was successful then takes them to the homepage (MainActivity)
                startActivity(new Intent(getApplicationContext(), FinanceActivity.class));
            }
        });

        mBudgetingBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // This if condition is the one that determine if the login information was correct and tells the user
                // the login was successful then takes them to the homepage (MainActivity)
                startActivity(new Intent(getApplicationContext(), BudgetActivity.class));
            }
        });

        mSignOutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // This if condition is the one that determine if the login information was correct and tells the user
                // the login was successful then takes them to the homepage (MainActivity)
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}