package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class BudgetAnalysisActivity extends AppCompatActivity {

    Button mReturnHomeFromBudgetAnalysisBtn, mSignOutFromBudgetAnalysisBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_analysis);

        mReturnHomeFromBudgetAnalysisBtn = findViewById(R.id.returnHomeFromBudgetAnalysisBtn);
        mSignOutFromBudgetAnalysisBtn = findViewById(R.id.signOutFromBudgetAnalysisBtn);

        //------------------------------------------------------------------------------------------
        // Let the user go back to the homepage
        mReturnHomeFromBudgetAnalysisBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        //------------------------------------------------------------------------------------------
        // Let the user sign out
        mSignOutFromBudgetAnalysisBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // The toast is what makes the message pop up when the user signs out
                Toast.makeText(BudgetAnalysisActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut(); //firebase command to delete token created for account locally
                startActivity(new Intent(getApplicationContext(), Login.class)); //back to login screen
            }
        });
    }
}