package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class LoanActivity extends AppCompatActivity
{

    EditText mUserLoanEntry, mUserInterestEntry, mFinalCostTextBox, mUserPeriodEntry;
    Button  mReturnHomeFromLoanBtn, signOutFromLoanBtn, mCalculateLoanBtn;

    Double LoanAmount, InterestRate_Year, InterestRate, Payment_Years, PaymentPeriods, temp, MonthlyPayment, MonthlyPaymentRounded, FinalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        //------------------------------------------------------------------------------------------
        // This represents the text entry box where the user inputs their loan amount
        mUserLoanEntry = findViewById(R.id.userLoanEntry);
        // This represents the text entry box where the user inputs their interest rate
        mUserInterestEntry = findViewById(R.id.userInterestEntry);
        // This represents the text entry box where the user inputs their payment period (in years)
        mUserPeriodEntry = findViewById(R.id.userPeriodEntry);
        // This represents the text entry box where the actual price of the loan is shown
        mFinalCostTextBox = findViewById(R.id.finalCostTextBox);

        mCalculateLoanBtn = findViewById(R.id.calculateLoanBtn);
        mReturnHomeFromLoanBtn = findViewById(R.id.returnHomeFromLoanBtn);
        signOutFromLoanBtn = findViewById(R.id.signOutFromLoanBtn);

        //------------------------------------------------------------------------------------------
        // When the user clicks the calculate cost button, we calculate the loan and show them
        // the cost
        mCalculateLoanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //----------------------------------------------------------------------------------
                // This is where we save the loan amount the user entered into a value we can use
                LoanAmount = Double.valueOf(mUserLoanEntry.getText().toString());

                //----------------------------------------------------------------------------------
                // This is where we save the interest amount the user entered into a value we can use
                InterestRate_Year = Double.valueOf(mUserInterestEntry.getText().toString());

                //----------------------------------------------------------------------------------
                // We multiply by 0.01 to change it to an accurate percentage
                InterestRate = ((InterestRate_Year)/12) * 0.01;

                Payment_Years =  Double.valueOf(mUserPeriodEntry.getText().toString());

                PaymentPeriods = Payment_Years * 12; // They pay on the loan every month

                //----------------------------------------------------------------------------------
                // For this type of loan calculation, we need to add a way to raise something
                // to a power for the MonthlyPayment equation, this is how we raise something to a
                // power. I did it this way so that the Monthly Payment would look more clean.
                // It takes ( 1 + (InterestRate) and raises it to the power of PaymentPeriods.
                //
                // Documentation for Math.pow can be found here: https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#pow-double-double-
                temp = Math.pow(( 1 + (InterestRate)), PaymentPeriods);

                //----------------------------------------------------------------------------------
                // Loan type used: Amortization
                // Link to Amortization loan interest calculation: https://www.vertex42.com/ExcelArticles/amortization-calculation.html
                MonthlyPayment = LoanAmount * ( (InterestRate * temp) / ( temp - 1) );

                //----------------------------------------------------------------------------------
                // We use this to make the MonthlyPayment as a rounded double.
                String MonthlyPaymentString = String.format("%.2f", MonthlyPayment);
                MonthlyPaymentRounded = Double.valueOf(MonthlyPaymentString);

                FinalCost = MonthlyPaymentRounded * PaymentPeriods;

                //----------------------------------------------------------------------------------
                // Just putting this comment here for later use, it might come in handy.
                // If you need to format a string to include commas and have 2 decimals,
                // You can use the code below to do it.
                // String FinalCostStringDouble= String.format("%,.2f", FinalCost);
                // The comma in String.format("%,.2f", FinalCost) lets the string use commas

                //----------------------------------------------------------------------------------
                // The below code allows for the final cost to be represented in US currency
                Locale usa = new Locale("en", "US");
                Currency dollars = Currency.getInstance(usa);
                NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

                //----------------------------------------------------------------------------------
                // To set the text in the entry box
                // The documentation for setText is here: https://developer.android.com/reference/android/widget/EditText#setText(java.lang.CharSequence,%20android.widget.TextView.BufferType)
                mFinalCostTextBox.setText(dollarFormat.format(FinalCost));
            }
        });

        //------------------------------------------------------------------------------------------
        // Let the user go back to the homepage
        mReturnHomeFromLoanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //------------------------------------------------------------------------------------------
        // Let the user sign out
        signOutFromLoanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(LoanActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}