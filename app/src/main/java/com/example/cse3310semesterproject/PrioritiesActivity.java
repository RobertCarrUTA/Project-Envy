package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PrioritiesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    EditText mCategoryTitleEntry;
    Button mCreateCategoryBtn, mReturnHomeFromPrioritiesBtn, mSignOutFromPrioritiesBtn;
    private Spinner spinner2;
    private static final String[] paths = {"High", "Med", "Low"};
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    String CategoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priorities);


        //------------------------------------------------------------------------------------------
        // A spinner would make the user selection of the category priority a lot easier.
        // Spinner documentation here: https://developer.android.com/guide/topics/ui/controls/spinner
        Spinner spinner = (Spinner) findViewById(R.id.priority_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        // The priority_array was made in strings.xml
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //------------------------------------------------------------------------------------------
        // This represents the text entry box where the user inputs their category title
        mCategoryTitleEntry = findViewById(R.id.categoryTitleEntry);
        mCreateCategoryBtn = findViewById(R.id.createCategoryBtn);
        mReturnHomeFromPrioritiesBtn = findViewById(R.id.returnHomeFromPrioritiesBtn);
        mSignOutFromPrioritiesBtn = findViewById(R.id.signOutFromPrioritiesBtn);
        String CategoryTitle;


        //------------------------------------------------------------------------------------------
        // Let the user go back to the homepage
        mReturnHomeFromPrioritiesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        //------------------------------------------------------------------------------------------
        // Let the user sign out
        mSignOutFromPrioritiesBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // The toast is what makes the message pop up when the user signs out
                Toast.makeText(PrioritiesActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut(); //firebase command to delete token created for account locally
                startActivity(new Intent(getApplicationContext(), Login.class)); //back to login screen
            }
        });
    }


    //----------------------------------------------------------------------------------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        // Save the user input and MAKE the spinner wait for the user to hit the create button
        mCreateCategoryBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // All of these values need to be saved to firebase and associated to a user account

                // Save the category title
                String CategoryTitle = mCategoryTitleEntry.getText().toString();
                setCategoryTitle(CategoryTitle);

                // Code needs to be added to save the spinner selection
                if (TextUtils.isEmpty(CategoryTitle))
                {
                    // If it is the category text box is empty, tell the user to add a title
                    Toast.makeText(PrioritiesActivity.this, "Please add a category title.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    switch (position)
                    {
                        case 0:
                            BudgetCategory budgetCategory = new BudgetCategory(uid, CategoryTitle, "High", 1);
                            FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Budget Category").setValue(budgetCategory);
                            mCategoryTitleEntry.getText().clear();
                            break;
                        case 1:
                            BudgetCategory budgetCategory2 = new BudgetCategory(uid, CategoryTitle, "Medium", 2);
                            FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Budget Category").setValue(budgetCategory2);
                            mCategoryTitleEntry.getText().clear();
                            break;
                        case 2:
                            BudgetCategory budgetCategory3 = new BudgetCategory(uid, CategoryTitle, "Low", 3);
                            FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Budget Category").setValue(budgetCategory3);
                            mCategoryTitleEntry.getText().clear();
                            break;
                    }
                }
                Toast.makeText(PrioritiesActivity.this, "Category " + CategoryTitle + " created successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void setCategoryTitle(String CategoryTitle)
    {
        this.CategoryTitle = CategoryTitle;
    }
}