package com.example.cse3310semesterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;//required to call FirebaseAuth commands
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
{
    // This sets us up to use our buttons that we have on our homepage
    Button mUserAccountBtn, mFinancialReportBtn, mBudgetingBtn, mSignOutBtn;
    EditText mRemainingBudgetTextBox;
    Double remainingBudget;
    ImageView profileImage;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    StorageReference pathRef;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reff = database.getReference().child("Users").child(uid).child("Expenses");
    DatabaseReference totReff = database.getReference().child("Users").child(uid).child("Budgets");

    static double lowTot = 0, medTot = 0, highTot = 0, budget = 0;
    static double budgetTot = 0;

    // This is to store the budget somewhere so that we can use it outside of the listener
    public static final String PREFS_NAME = "MyPrefsFile";
    String budgetString;

    // If we have time, I might try to add the drawer menu back in, but for now, just to have the very basics of the app running
    // this will do

    // Documentation to add push notifications within the app:
    // https://developer.android.com/training/notify-user/build-notification#java


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserAccountBtn = findViewById(R.id.UserAccountBtn);
        mFinancialReportBtn = findViewById(R.id.FinancialReportBtn);
        mBudgetingBtn = findViewById(R.id.BudgetingBtn);
        mSignOutBtn = findViewById(R.id.SignOutBtn);
        profileImage = findViewById(R.id.profileImage);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -(calendar.get(Calendar.DAY_OF_WEEK)-2));
        Date monDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        Date nextMonDate = calendar.getTime();

        // Part of storing the budget somewhere so we can always have access to it
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);



        totReff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())  // If budget does exist for the user
                {
                    Budgets budgetObj = dataSnapshot.getValue(Budgets.class);
                    budget = budgetObj.budget;

                    // This is storing the value off to some place we can use it again whenever we want
                    budgetString = String.valueOf(budget);
                    System.out.println("BudgetString inside budget loop:");
                    System.out.println(budgetString);
                    // Storing the budget string
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("budget", budgetString);
                    editor.commit();
                }
                else // If budget does not exist for the user
                {
                    budget = 0.00;

                    // This is storing the value off to some place we can use it again whenever we want
                    budgetString = String.valueOf(budget);
                    System.out.println("BudgetString inside budget loop:");
                    System.out.println(budgetString);
                    // Storing the budget string
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("budget", budgetString);
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


        reff.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                highTot = 0;
                medTot = 0;
                lowTot = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Expenses expenses = snapshot.getValue(Expenses.class);
                    if(expenses.creationDate.compareTo(nextMonDate) <= 0 && expenses.creationDate.compareTo(monDate) >= 0)
                    {
                        highTot = highTot + expenses.spending;
                    }
                }
                // This little block of code is us getting the value of the user budget
                // from where we stored it and converting it back to a double
                budgetString = settings.getString("budget", budgetString);
                //budgetString = String.format("%.2f", budgetString);
                System.out.println("BudgetString inside oncreate:");
                System.out.println(budgetString);
                budget = Double.valueOf(budgetString);

                mRemainingBudgetTextBox = findViewById(R.id.remainingBudgetTextBox);

                if(budget == 0.00)
                {
                    mRemainingBudgetTextBox.setText("No existing budget");
                    mRemainingBudgetTextBox.setTypeface(null, Typeface.BOLD);
                    mRemainingBudgetTextBox.setTextColor(Color.parseColor("#6a6a6a"));
                }
                else
                {
                    // The below code allows for the remaining budget value to be represented in US currency
                    Locale usa = new Locale("en", "US");
                    Currency dollars = Currency.getInstance(usa);
                    NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

                    budgetTot = budget - highTot;
                    System.out.println(highTot);
                    System.out.println(budget);

                    // If the weekly budget is higher than the total weekly expenses
                    if(budget > highTot)
                    {
                        mRemainingBudgetTextBox.setText(dollarFormat.format(budgetTot));
                        mRemainingBudgetTextBox.setTypeface(null, Typeface.BOLD);
                        // Set the text to a custom dark green color, it is a lot easier to read than the default Color.GREEN
                        mRemainingBudgetTextBox.setTextColor(Color.parseColor("#049660"));
                    }
                    // If the weekly budget isn't higher than the total weekly expenses
                    else if(budget < highTot)
                    {
                        mRemainingBudgetTextBox.setText(dollarFormat.format(budgetTot));
                        mRemainingBudgetTextBox.setTypeface(null, Typeface.BOLD);
                        mRemainingBudgetTextBox.setTextColor(Color.RED);
                    }
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getUid();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                // Failed to read value
                Log.v("TestRead", "Failed to read value.", databaseError.toException());
            }
        });

        profileImage = findViewById(R.id.profileImage);
        pathRef = storageRef.child(uid + ".jpeg");
        if(pathRef != null)
        {
            pathRef.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes)
                {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);//convert bytes into bitmap
                    profileImage.setImageBitmap(bitmap);
                }
            });
        }


        mUserAccountBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
            }
        });

        mFinancialReportBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), FinanceActivity.class));
            }
        });

        mBudgetingBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), BudgetActivity.class));
            }
        });

        mSignOutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // The toast is what makes the message pop up when the user signs out
                Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut(); //firebase command to delete token created for account locally
                startActivity(new Intent(getApplicationContext(), Login.class)); //back to login screen
            }
        });
    }
}