package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PrioritiesActivity extends AppCompatActivity
{

    Button mReturnHomeFromPrioritiesBtn, mSignOutFromPrioritiesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priorities);

        mReturnHomeFromPrioritiesBtn = findViewById(R.id.returnHomeFromPrioritiesBtn);
        mSignOutFromPrioritiesBtn = findViewById(R.id.signOutFromPrioritiesBtn);

        // Let the user go back to the homepage
        mReturnHomeFromPrioritiesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        mSignOutFromPrioritiesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // The toast is what makes the message pop up when the user signs out
                Toast.makeText(PrioritiesActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}
//This is a comment