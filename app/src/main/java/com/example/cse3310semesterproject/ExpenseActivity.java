package com.example.cse3310semesterproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;


public class ExpenseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    EditText mIncomeEntryBox, mExpensesEntryBox;
    Double Income, Expense;
    Calendar calendar = Calendar.getInstance();
    Date createDate = new Date();
    Button mSaveExpensesBtn, mReturnHomeFromExpenseBtn, mSignOutFromExpenseBtn;
    String[] paths = {"High", "Medium", "Low"};
    private Spinner spinner;
    public static int priorityInt;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reff = database.getReference().child("Users").child(uid).child("Budget Category");
    final ArrayList<BudgetCategory> categoryList = new ArrayList<BudgetCategory>();
    final List<String> categoryTitles = new ArrayList<String>();
    //String[] categoryString = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
    String[] categoryString = {"", "", "", "", "", "", "", "", "", ""};
    String strThatDay = "04/20/2021";
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Date d = formatter.parse(strThatDay);


    public ExpenseActivity() throws ParseException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        System.out.println(d);

        createDate = calendar.getTime();

        reff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                int i = 0;
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                //String str[] = new String[categoryTitles.size()];
                for(DataSnapshot child : children)
                {
                    BudgetCategory singleCategory = child.getValue(BudgetCategory.class);
                    categoryList.add(singleCategory);
                    String store = singleCategory.categoryTitle.toString();
                    //categoryTitles.add(singleCategory.categoryTitle);
                    categoryTitles.add(store);
                    categoryString[i] = singleCategory.categoryTitle.trim();
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.v("TestRead", "Failed to read value.", error.toException());
            }
        });

        //------------------------------------------------------------------------------------------




        //ArrayAdapter<BudgetCategory> categoryArrayAdapter = new ArrayAdapter<BudgetCategory>(this, android.R.layout.simple_list_item_1, categoryList);
        //categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        Spinner spinner = (Spinner) findViewById(R.id.priority_spinner2);
        if(spinner == null){Log.w("", "Spinner is null");}
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryString);
        //str = categoryTitles.toArray(new String[0]);
        String[] str = getStringArray(categoryTitles);
        /*ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, str);*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + selectedItem, Toast.LENGTH_LONG).show();
                this.onItemSelected(parent, view, position, id);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ExpenseActivity.this, "Make a Selection " , Toast.LENGTH_LONG).show();
            }
        });*/
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

    public void onClick(View v)
    {
        switch(v.getId())
        {
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
    public void inputInfo()
    {
        if(TextUtils.isEmpty(mIncomeEntryBox.getText()) && TextUtils.isEmpty(mExpensesEntryBox.getText()))
        {
            mIncomeEntryBox.setError("Please enter an income or expense");
            Toast.makeText(ExpenseActivity.this, "Please enter an Income or an Expense.", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            if(!(TextUtils.isEmpty(mIncomeEntryBox.getText())))
            {
                Income = Double.valueOf(mIncomeEntryBox.getText().toString());
                //----------------------------------------------------------------------------------
                // This is where we save the expense amount the user entered into a value we can use

                Income income  = new Income(uid, createDate, Income);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Income").push().setValue(income);
                Toast.makeText(ExpenseActivity.this, "Income Saved Successfully!", Toast.LENGTH_SHORT).show();
            }
            if(!(TextUtils.isEmpty(mExpensesEntryBox.getText())))
            {
                Expense = Double.valueOf(mExpensesEntryBox.getText().toString());

                Expenses expenses = new Expenses(uid, priorityInt, Expense, d); //need to add spinner to select priority
                FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Expenses").push().setValue(expenses);
                Toast.makeText(ExpenseActivity.this, "Expense Saved Successfully!", Toast.LENGTH_SHORT).show();
            }
        }
        //----------------------------------------------------------------------------------
        // To reset the text in the entry box we reset the values to empty
        mIncomeEntryBox.getText().clear();
        mExpensesEntryBox.getText().clear();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
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
            case 4: // If High is selected on the drop down menu
                priorityInt = 4;
                break;
            case 5: // If Medium is selected on the drop down menu
                priorityInt = 2;
                break;
            case 6: // If Low is selected on the drop down menu
                priorityInt = 3;
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        Toast.makeText(ExpenseActivity.this, "Please Select A Category", Toast.LENGTH_SHORT).show();
    }

    public static String[] getStringArray(List<String> arr)
    {
        String str[] = new String[arr.size()];
        //String str1[] = arr.toArray();
        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++)
        {

            // Assign each value to String array
            str[j] = arr.get(j);

        }
        //Toast.makeText(ExpenseActivity.this, "" + str[0], Toast.LENGTH_SHORT).show();
        return str;
    }
}