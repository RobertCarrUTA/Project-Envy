package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AccountActivity extends AppCompatActivity
{
    Button mReturnHomeBtn, mSignOutFromAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mReturnHomeBtn = findViewById(R.id.ReturnHomeBtn);
        mSignOutFromAccountBtn = findViewById(R.id.SignOutFromAccountBtn);

        // Let the user go back to the homepage
        mReturnHomeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        // Let the user sign out
        mSignOutFromAccountBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(AccountActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}