package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BudgetActivity extends AppCompatActivity
{

    Button mReturnHomeFromBudgetBtn, mSignOutFromBudgetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        mReturnHomeFromBudgetBtn = findViewById(R.id.returnHomeFromBudgetBtn);
        mSignOutFromBudgetBtn = findViewById(R.id.signOutFromBudgetBtn2);

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
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}