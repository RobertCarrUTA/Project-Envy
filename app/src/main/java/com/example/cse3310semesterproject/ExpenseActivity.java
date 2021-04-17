package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;


public class ExpenseActivity extends AppCompatActivity implements View.OnClickListener
{

    EditText mIncomeEntryBox, mExpensesEntryBox;
    Double Income, Expense;
    Date createDate = new Date(System.currentTimeMillis());
    Button mSaveExpensesBtn, mReturnHomeFromExpenseBtn, mSignOutFromExpenseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        mIncomeEntryBox.setOnClickListener(this);
        mExpensesEntryBox.setOnClickListener(this);
        mSaveExpensesBtn.setOnClickListener(this);
        mReturnHomeFromExpenseBtn.setOnClickListener(this);
        mSignOutFromExpenseBtn.setOnClickListener(this);


    };

    public void onClick(View v){
        switch(v.getId()){
            case R.id.returnHomeFromExpenseBtn:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));//jump to homepage
                break;
            case R.id.signOutFromExpenseBtn:
                Toast.makeText(ExpenseActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
            case R.id.saveExpensesBtn:
                inputInfo();
                break;
        }
    }
    public void inputInfo(){
        Income = Double.valueOf(mIncomeEntryBox.getText().toString());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //----------------------------------------------------------------------------------
        // This is where we save the expense amount the user entered into a value we can use
        Expense = Double.valueOf(mExpensesEntryBox.getText().toString());

        if(TextUtils.isEmpty(mIncomeEntryBox.getText()))
        {
            mIncomeEntryBox.setError("Please enter an income");
            return;
        }
        else if(TextUtils.isEmpty(mExpensesEntryBox.getText()))
        {
            mExpensesEntryBox.setError("Please enter an expense");
            return;
        }else{

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            Expenses expenses = new Expenses(uid, 1, Expense, createDate); //need to add spinner to select priority
            Income income  = new Income(uid, createDate, Income);
            //database.getReference().
            FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Income").setValue(income);
            FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Expenses").setValue(expenses);
            //----------------------------------------------------------------------------------
            // Let the user know the user input has been successfully saved.
            Toast.makeText(ExpenseActivity.this, "Input and Expenses Saved Successfully!", Toast.LENGTH_SHORT).show();
        }


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

}