package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoanActivity extends AppCompatActivity
{

    Button  mReturnHomeFromLoanBtn, signOutFromLoanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        mReturnHomeFromLoanBtn = findViewById(R.id.expensesBtn);

        signOutFromLoanBtn = findViewById(R.id.signOutFromLoanBtn);

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
