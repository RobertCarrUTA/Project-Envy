package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class BudgetManagementActivity extends AppCompatActivity
{

    EditText mWeeklyBudgetEntry;
    Button mSaveWeeklyBudgetBtn, mClearWeeklyBudgetBtn, mReturnHomeFromManageBudgetBtn, mSignOutFromManageBudgetBtn;
    Double weeklyBudget;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_management);

        mWeeklyBudgetEntry = findViewById(R.id.weeklyBudgetEntry);
        mSaveWeeklyBudgetBtn = findViewById(R.id.saveWeeklyBudgetBtn);
        mClearWeeklyBudgetBtn = findViewById(R.id.clearWeeklyBudgetBtn);
        mReturnHomeFromManageBudgetBtn = findViewById(R.id.returnHomeFromManageBudgetBtn);
        mSignOutFromManageBudgetBtn = findViewById(R.id.signOutFromManageBudgetBtn);

        /*
        If the budget does not exist on firebase, we could have the text box entry appear
        empty with a hint saying "please enter a budget", we could try this by doing an if(budget does not exist) and else or an else if(budget does exist)
        then once it is cleared from the database have it clear the text box, but while one exists, then it shows in the text box
         */

        mSaveWeeklyBudgetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // This needs to be saved to Firebase
                Calendar calendar = Calendar.getInstance();
                Date currDate = calendar.getTime();
                weeklyBudget = Double.valueOf(mWeeklyBudgetEntry.getText().toString());
                Budgets budget  = new Budgets(uid, currDate, weeklyBudget);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Budgets").setValue(budget);
                Toast.makeText(BudgetManagementActivity.this, "Weekly Budget Saved!", Toast.LENGTH_SHORT).show();
            }
        });


        mClearWeeklyBudgetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // We need to deleted the current budget from firebase or overwrite the current one so they can make a new budget when they want
                //weeklyBudget = 0.00;
                Toast.makeText(BudgetManagementActivity.this, "Weekly Budget Not Cleared!", Toast.LENGTH_SHORT).show();
            }
        });

        // Let the user go back to the homepage
        mReturnHomeFromManageBudgetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        // Let the user sign out
        mSignOutFromManageBudgetBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut(); //firebase command to delete token created for account locally
                startActivity(new Intent(getApplicationContext(), Login.class)); //back to login screen
                Toast.makeText(BudgetManagementActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}