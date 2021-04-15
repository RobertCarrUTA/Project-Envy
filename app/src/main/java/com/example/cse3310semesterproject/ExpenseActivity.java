package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;


public class ExpenseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    //String email = user.getEmail();
    EditText mIncomeEntryBox, mExpensesEntryBox;
    Double Income, Expense;
    Date createDate = new Date(System.currentTimeMillis());
    Button mSaveExpensesBtn, mReturnHomeFromExpenseBtn, mSignOutFromExpenseBtn;
    private static final String[] paths = {"High", "Medium", "Low"};
    private Spinner spinner2;
    public static int priorityInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        //------------------------------------------------------------------------------------------

        Spinner spinner = (Spinner) findViewById(R.id.priority_spinner2);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
            String email = user.getEmail();
            Expenses expenses = new Expenses(uid, priorityInt, Expense, createDate, email); //need to add spinner to select priority
            Income income  = new Income(uid, createDate, Income, email);
            //database.getReference().

            FirebaseDatabase.getInstance().getReference("Users").child(email).child("Income").push().setValue(income);

            FirebaseDatabase.getInstance().getReference("Users").child(email).child("Expenses").push().setValue(income);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position)
        {
            case 0: // If High is selected on the drop down menu
                priorityInt = 1;
                break;
            case 1: // If Medium is selected on the drop down menu
                priorityInt = 2;
                break;
            case 2: // If Low is selected on the drop down menu
                priorityInt = 3;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}