package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ExpenseActivity extends AppCompatActivity
{

    Button mReturnHomeFromExpenseBtn, mSignOutFromExpenseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        mReturnHomeFromExpenseBtn = findViewById(R.id.returnHomeFromExpenseBtn);
        mSignOutFromExpenseBtn = findViewById(R.id.signOutFromExpenseBtn);

        // Let the user go back to the homepage
        mReturnHomeFromExpenseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        mSignOutFromExpenseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // The toast is what makes the message pop up when the user signs out
                Toast.makeText(ExpenseActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


    }
}