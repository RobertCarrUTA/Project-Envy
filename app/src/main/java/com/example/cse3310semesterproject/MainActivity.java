package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;//required to call FirebaseAuth commands


public class MainActivity extends AppCompatActivity
{
    // This sets us up to use our buttons that we have on our homepage
    Button mUserAccountBtn, mFinancialReportBtn, mBudgetingBtn, mSignOutBtn;
    ImageView profileImage;

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

        profileImage = findViewById(R.id.profileImage);


        mUserAccountBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
            }
        });

        mFinancialReportBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), FinanceActivity.class));
            }
        });

        mBudgetingBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), BudgetActivity.class));
            }
        });

        mSignOutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // The toast is what makes the message pop up when the user signs out
                Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut(); //firebase command to delete token created for account locally
                startActivity(new Intent(getApplicationContext(), Login.class)); //back to login screen
            }
        });
    }
}