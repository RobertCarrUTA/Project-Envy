package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity
{
    EditText mEmail, mPassword;
    Button loginBtn, createAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.EmailLoginEntry);
        mPassword = findViewById(R.id.PasswordLoginEntry);
        loginBtn = findViewById(R.id.SignInBtn);
        createAccountBtn = findViewById(R.id.RegisterBtn);

        // We can replace this with a lambda
        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String emailValue = mEmail.getText().toString().trim();
                String passwordValue = mPassword.getText().toString().trim();

                // If the e-mail text entry is empty tell the user that the e-mail is required
                if(TextUtils.isEmpty(emailValue))
                {
                    mEmail.setError("E-mail is required");
                    return;
                }

                // If the password text entry is empty tell the user that the password is required
                if(TextUtils.isEmpty(passwordValue))
                {
                    mPassword.setError("Password is required");
                    return;
                }

                // We should make the 6 in this case an int value we define and change
                // once then it changes through the whole program
                if(passwordValue.length() < 6)
                {
                    mPassword.setError("Password should be more than 6 characters");
                    return;
                }

                // This if condition is the one that determine if the login information was correct and tells the user
                // the login was successful then takes them to the homepage (MainActivity)
                if(emailValue.equals("CSE3310") && passwordValue.equals("123456"))
                {
                    Toast.makeText(Login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }
        });

        // This moves us to the create account page
        createAccountBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), CreateAccountActivity.class));
            }
        });
    }
}