package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity
{

    Button mReturnToLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mReturnToLoginBtn = findViewById(R.id.returnToLoginBtn);

        mReturnToLoginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // This if condition is the one that determine if the login information was correct and tells the user
                // the login was successful then takes them to the homepage (MainActivity)
                Toast.makeText(CreateAccountActivity.this, "Successfully Created Account!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}