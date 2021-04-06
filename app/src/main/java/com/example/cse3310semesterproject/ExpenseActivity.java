package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class ExpenseActivity extends AppCompatActivity
{

    EditText mIncomeEntryBox, mExpensesEntryBox;
    Double Income, Expense;

    Button mSaveExpensesBtn, mReturnHomeFromExpenseBtn, mSignOutFromExpenseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        //------------------------------------------------------------------------------------------
        // This represents the text entry box where the user inputs their income
        mIncomeEntryBox = findViewById(R.id.incomeEntryBox);
        // This represents the text entry box where the user inputs their expenses
        mExpensesEntryBox = findViewById(R.id.expensesEntryBox);
        // The button that saves the inputted user data
        mSaveExpensesBtn = findViewById(R.id.saveExpensesBtn);

        mReturnHomeFromExpenseBtn = findViewById(R.id.returnHomeFromExpenseBtn);
        mSignOutFromExpenseBtn = findViewById(R.id.signOutFromExpenseBtn);


        //------------------------------------------------------------------------------------------
        // WE NEED TO SAVE THESE VALUES TO THE FIREBASE ACCOUNT AND BE ABLE TO PULL THEM BACK FOR
        // USE IN THE REPORTS PAGE
        mSaveExpensesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //----------------------------------------------------------------------------------
                // This is where we save the income amount the user entered into a value we can use
                Income = Double.valueOf(mIncomeEntryBox.getText().toString());

                //----------------------------------------------------------------------------------
                // This is where we save the expense amount the user entered into a value we can use
                Expense = Double.valueOf(mExpensesEntryBox.getText().toString());

                //----------------------------------------------------------------------------------
                // Let the user know the user input has been successfully saved.
                Toast.makeText(ExpenseActivity.this, "Input and Expenses Saved Successfully!", Toast.LENGTH_SHORT).show();

                //----------------------------------------------------------------------------------
                // The below code allows for the entry boxes to be represented in US currency
                Locale usa = new Locale("en", "US");
                Currency dollars = Currency.getInstance(usa);
                NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

                //----------------------------------------------------------------------------------
                // To reset the text in the entry box we reset the values to empty
                mIncomeEntryBox.getText().clear();
                mExpensesEntryBox.getText().clear();
            }
        });

        //------------------------------------------------------------------------------------------
        // Let the user go back to the homepage
        mReturnHomeFromExpenseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //------------------------------------------------------------------------------------------
        // Let the user sign out
        mSignOutFromExpenseBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // The toast is what makes the message pop up when the user signs out
                Toast.makeText(ExpenseActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut(); //firebase command to delete token created for account locally
                startActivity(new Intent(getApplicationContext(), Login.class)); //back to login screen
            }
        });
    }
}