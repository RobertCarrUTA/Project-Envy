package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class PrioritiesActivity extends AppCompatActivity
{

    EditText mCategoryTitleEntry;
    Button mCreateCategoryBtn, mReturnHomeFromPrioritiesBtn, mSignOutFromPrioritiesBtn;

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //------------------------------------------------------------------------------------------
        // This represents the text entry box where the user inputs their category title
        mCategoryTitleEntry = findViewById(R.id.categoryTitleEntry);
        mCreateCategoryBtn = findViewById(R.id.createCategoryBtn);
        mReturnHomeFromPrioritiesBtn = findViewById(R.id.returnHomeFromPrioritiesBtn);
        mSignOutFromPrioritiesBtn = findViewById(R.id.signOutFromPrioritiesBtn);


        //------------------------------------------------------------------------------------------
        // Save the user input
        mCreateCategoryBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // All of these values need to be saved to firebase and associated to a user account

                // Save the category title
                String CategoryTitle = mCategoryTitleEntry.getText().toString();

                // Code needs to be added to save the spinner selection

                Toast.makeText(PrioritiesActivity.this, "Category " + CategoryTitle + " created successfully!", Toast.LENGTH_SHORT).show();
            }
        });


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
}
