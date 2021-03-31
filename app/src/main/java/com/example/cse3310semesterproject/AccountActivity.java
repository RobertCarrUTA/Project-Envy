package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountActivity extends AppCompatActivity
{
    EditText mNewCurrentPasswordEntry;
    Button mReturnHomeBtn, mSignOutFromAccountBtn, mChangePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mReturnHomeBtn = findViewById(R.id.ReturnHomeBtn);
        mSignOutFromAccountBtn = findViewById(R.id.SignOutFromAccountBtn);
        mChangePasswordBtn = findViewById(R.id.changePasswordBtn);
        mNewCurrentPasswordEntry = findViewById(R.id.newCurrentPasswordEntry);

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

        // This is for the password change functionality, needs database check I guess
        mChangePasswordBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String passwordValue = mNewCurrentPasswordEntry.getText().toString().trim();

                // If the password text entry is empty tell the user that the password is required
                if(TextUtils.isEmpty(passwordValue))
                {
                    mNewCurrentPasswordEntry.setError("Password is required");
                    return;
                }

                // We should make the 6 in this case an int value we define and change
                // once then it changes through the whole program
                if(passwordValue.length() < 6)
                {
                    mNewCurrentPasswordEntry.setError("Password should be more than 6 characters");
                    return;
                }

                // This if condition is the one that determine if the login information was correct and tells the user
                // the login was successful then takes them to the homepage (MainActivity)
                if(passwordValue.length() > 6)
                {
                    Toast.makeText(AccountActivity.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}