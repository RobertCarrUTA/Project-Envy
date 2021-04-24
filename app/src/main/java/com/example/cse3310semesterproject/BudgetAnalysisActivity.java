package com.example.cse3310semesterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class BudgetAnalysisActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    Button mReturnHomeFromBudgetAnalysisBtn, mSignOutFromBudgetAnalysisBtn;
    EditText mHowMuchSpentTextBox, mPercentageTextBox, mBelowOrAboveBudgetTextBox;

    Double percentage;
    String PercentageString;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reff = database.getReference().child("Users").child(uid).child("Expenses");
    DatabaseReference totReff = database.getReference().child("Users").child(uid).child("Budgets");

    private Spinner spinner;
    private static final String[] paths = {"Select Priority Level", "High Priority Spending", "Medium Priority Spending", "Low Priority Spending"};
    static double lowTot = 0, medTot = 0, highTot = 0, budget = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_analysis);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -(calendar.get(Calendar.DAY_OF_WEEK)-2));
        Date monDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        Date nextMonDate = calendar.getTime();
        System.out.println(monDate);

        mHowMuchSpentTextBox = findViewById(R.id.howMuchSpentTextBox);
        mPercentageTextBox = findViewById(R.id.percentageTextBox);
        mBelowOrAboveBudgetTextBox = findViewById(R.id.belowOrAboveBudgetTextBox);

        mReturnHomeFromBudgetAnalysisBtn = findViewById(R.id.returnHomeFromBudgetAnalysisBtn);
        mSignOutFromBudgetAnalysisBtn = findViewById(R.id.signOutFromBudgetAnalysisBtn);


        //------------------------------------------------------------------------------------------
        totReff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())  // If budget exist for the user
                {
                    Budgets budgetObj = dataSnapshot.getValue(Budgets.class);
                    budget = budgetObj.budget;
                }
                else // If budget does not exist for the user
                {
                    budget = 0.00;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())  // If these expenses exist for the user
                {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Expenses expenses = snapshot.getValue(Expenses.class);
                        if (expenses.creationDate.compareTo(nextMonDate) <= 0 && expenses.creationDate.compareTo(monDate) >= 0)
                        {
                            if (expenses.priority == 1)
                            {
                                highTot = highTot + expenses.spending;
                            }
                            else if (expenses.priority == 2)
                            {
                                medTot = medTot + expenses.spending;
                            }
                            else if (expenses.priority == 3)
                            {
                                lowTot = lowTot + expenses.spending;
                            }
                        }
                    }
                }
                else // If these expenses do not exist for the user
                {
                    highTot = 0.00;
                    medTot = 0.00;
                    lowTot = 0.00;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                // Failed to read value
                Log.v("TestRead", "Failed to read value.", databaseError.toException());
            }
        });


        //------------------------------------------------------------------------------------------
        Spinner spinner = (Spinner) findViewById(R.id.prioritySelection_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        // The priority_array was made in strings.xml
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if ((budget == 0.00) && (highTot == 0.00) && (medTot == 0.00) && (lowTot == 0.00)) // If the info needed doesn't exist for the user
        {
            mHowMuchSpentTextBox.setText("No existing expenses");
            mPercentageTextBox.setText("No existing expenses");
            mBelowOrAboveBudgetTextBox.setText("No existing budget");
        }
        else // If the info needed does exist for the user
        {
            if (budget > (highTot + medTot + lowTot))
            {
                mBelowOrAboveBudgetTextBox.setText("You are still within your budget!");
            }
            else if (budget < (highTot + medTot + lowTot))
            {
                mBelowOrAboveBudgetTextBox.setText("You are not within your budget!");
            }

            // The below code allows for the remaining budget value to be represented in US currency
            Locale usa = new Locale("en", "US");
            Currency dollars = Currency.getInstance(usa);
            NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

            switch (position)
            {
                case 0:
                    break;
                case 1: // High
                    // need to be set to the total expenses for high priority for the current week
                    mHowMuchSpentTextBox.setText(dollarFormat.format(highTot));
                    percentage = (highTot / budget) * 100.0;
                    PercentageString = String.format("%.2f", percentage);
                    percentage = Double.valueOf(PercentageString);
                    mPercentageTextBox.setText("%" + percentage);
                    break;
                case 2: // Medium
                    // need to be set to the total expenses for medium priority for the current week
                    mHowMuchSpentTextBox.setText(dollarFormat.format(medTot));
                    percentage = (medTot / budget) * 100.0;
                    PercentageString = String.format("%.2f", percentage);
                    percentage = Double.valueOf(PercentageString);
                    mPercentageTextBox.setText("%" + percentage);
                    break;
                case 3: // Low
                    // need to be set to the total expenses for low priority for the current week
                    mHowMuchSpentTextBox.setText(dollarFormat.format(lowTot));
                    percentage = (lowTot / budget) * 100.0;
                    PercentageString = String.format("%.2f", percentage);
                    percentage = Double.valueOf(PercentageString);
                    mPercentageTextBox.setText("%" + percentage);
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}