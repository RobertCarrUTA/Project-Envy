package com.example.cse3310semesterproject;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrioritiesActivity extends AppCompatActivity //implements AdapterView.OnItemSelectedListener
{
    EditText mCategoryTitleEntry;
    Button mCreateCategoryBtn, mReturnHomeFromPrioritiesBtn, mSignOutFromPrioritiesBtn;
    private Spinner spinner2;
    private static final String[] paths = {"High", "Med", "Low"};
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    String priorityString, CategoryTitle;
    int priorityInt;


    //////////////////////////////////////////////////
    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    BudgetCategory budgetCategory;

    //////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priorities);

        //------------------------------------------------------------------------------------------
        // A spinner would make the user selection of the category priority a lot easier.
        // Spinner documentation here: https://developer.android.com/guide/topics/ui/controls/spinner

        Spinner spinner = (Spinner) findViewById(R.id.priority_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        // The priority_array was made in strings.xml
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);
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

        ////////////////////////////////////////////////////////////////////////////////
        // below line is used to get the
        // instance of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("BudgetCategory");

        // initializing our object
        // class variable.
        budgetCategory = new BudgetCategory();

        ////////////////////////////////////////////////////////////////////////////////

        /*
        @Override
        protected void onCreate (Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Get reference of widgets from XML layout
            final Spinner spinner = (Spinner) findViewById(R.id.spinner);

            // Initializing a String Array
            String[] plants = new String[]
                    {
                            "Laceflower",
                            "California sycamore",
                            "Mountain mahogany",
                            "Butterfly weed",
                            "Carrot weed"
                    };

            final List<String> plantsList = new ArrayList<>(Arrays.asList(plants));

            // Initializing an ArrayAdapter
            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, plantsList) {
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position % 2 == 1) {
                        // Set the item background color
                        tv.setBackgroundColor(Color.parseColor("#FFC9A3FF"));
                    } else {
                        // Set the alternate item background color
                        tv.setBackgroundColor(Color.parseColor("#FFAF89E5"));
                    }
                    return view;
                }
            };
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            spinner.setAdapter(spinnerArrayAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItemText = (String) parent.getItemAtPosition(position);
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
        }
        */


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
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                priorityInt = 1;
                                priorityString = "High";
                                Toast.makeText(PrioritiesActivity.this, "Category High priority created successfully!", Toast.LENGTH_SHORT).show();
                                //BudgetCategory budgetCategory  = new BudgetCategory(uid, "test", "High", 1);
                                //FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Budget Category").setValue(budgetCategory);
                                break;
                            case 1:
                                priorityInt = 2;
                                priorityString = "Medium";
                                Toast.makeText(PrioritiesActivity.this, "Category Medium priority created successfully!", Toast.LENGTH_SHORT).show();
                                //BudgetCategory budgetCategory2  = new BudgetCategory(uid, "test2", "Med", 2);
                                //FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Budget Category").setValue(budgetCategory2);
                                break;
                            case 2:
                                priorityInt = 3;
                                priorityString = "Low";
                                Toast.makeText(PrioritiesActivity.this, "Category Low priority created successfully!", Toast.LENGTH_SHORT).show();
                                //BudgetCategory budgetCategory3  = new BudgetCategory(uid, "test3", "Low", 3);
                                //FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Budget Category").setValue(budgetCategory3);
                                break;

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                // below line is for checking weather the
                // Category field is empty or not.
                if (TextUtils.isEmpty(CategoryTitle))
                {
                    // If it is the category text box is empty, tell the user to add a title
                    Toast.makeText(PrioritiesActivity.this, "Please add a category title.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // else call the method to add
                    // data to our database.
                    // BudgetCategory(uid, "test", "High", 1);
                    //addDatatoFirebase(uid, CategoryTitle, priorityString, priorityInt);

                    // BudgetCategory budgetCategory = new BudgetCategory(uid, CategoryTitle);
                    //FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Budget Category").setValue(budgetCategory);


                    //String PString = String.valueOf(priorityString);
                    //String P_I_String = String.valueOf(priorityInt);
                    //Toast.makeText(PrioritiesActivity.this, "Category " + CategoryTitle + " created successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
        private void addDatatoFirebase(uid, String CategoryTitle, String priorityString, int priorityInt)
        {
                // below 3 lines of code is used to set
                // data in our object class.
                budgetCategory.setUserID(uid);
                budgetCategory.setCategoryTitle(CategoryTitle);
                budgetCategory.setPriorityString(priorityString);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(budgetCategory);

                // after adding this data we are showing toast message.
                Toast.makeText(PrioritiesActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(PrioritiesActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
        */


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

    /*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                priorityInt = 1;
                priorityString = "High";
                //BudgetCategory budgetCategory  = new BudgetCategory(uid, "test", "High", 1);
                //FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Budget Category").setValue(budgetCategory);
                break;
            case 1:
                priorityInt = 2;
                priorityString = "Medium";
                //BudgetCategory budgetCategory2  = new BudgetCategory(uid, "test2", "Med", 2);
                //FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Budget Category").setValue(budgetCategory2);
                break;
            case 2:
                priorityInt = 3;
                priorityString = "Low";
                BudgetCategory budgetCategory = new BudgetCategory(uid, CategoryTitle, priorityString, priorityInt);
                FirebaseDatabase.getInstance().getReference("Users").child(uid).push().child("Budget Category").setValue(budgetCategory);
                break;

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

     */

}
