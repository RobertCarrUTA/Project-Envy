package com.example.cse3310semesterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    private static double lowTot = 0, medTot = 0, highTot = 0, budget = 0;

    // If we have time, I might try to add the drawer menu back in, but for now, just to have the very basics of the app running
    // this will do

    // Documentation to add push notifications within the app:
    // https://developer.android.com/training/notify-user/build-notification#java

    // Using this to store the budget value and pull from it later when we need it
    public static final String PREFS_NAME = "MyPrefsFile";
    String budgetString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        mUserAccountBtn = findViewById(R.id.UserAccountBtn);
        mFinancialReportBtn = findViewById(R.id.FinancialReportBtn);
        mBudgetingBtn = findViewById(R.id.BudgetingBtn);
        mRemainingBudgetTextBox = findViewById(R.id.remainingBudgetTextBox);
        mSignOutBtn = findViewById(R.id.SignOutBtn);
        profileImage = findViewById(R.id.profileImage);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -(calendar.get(Calendar.DAY_OF_WEEK)-2));
        Date monDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        Date nextMonDate = calendar.getTime();


        totReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Budgets budgetObj = dataSnapshot.getValue(Budgets.class);
                budget = budgetObj.budget;
                budgetString = String.valueOf(budget);
                System.out.println("BudgetString inside budget loop:");
                System.out.println(budgetString);
                // Storing the budget string
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("budget", budgetString);
                editor.commit();
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
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Expenses expenses = snapshot.getValue(Expenses.class);
                    if(expenses.creationDate.compareTo(nextMonDate) <= 0 && expenses.creationDate.compareTo(monDate) >= 0)
                    {
                        if(expenses.priority == 1)
                        {
                            highTot = highTot + expenses.spending;
                        }
                        else if(expenses.priority == 2)
                        {
                            medTot = medTot + expenses.spending;
                        }
                        else if(expenses.priority == 3)
                        {
                            lowTot = lowTot + expenses.spending;
                        }
                    }
                }

                //////////////////////////////// The code below needs to access the budget value, then this should work ////////////////////////////////

                // The below code allows for the remaining budget value to be represented in US currency
                Locale usa = new Locale("en", "US");
                Currency dollars = Currency.getInstance(usa);
                NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
                // remaining budget need to be (weekly budget - sum of all expenses in the week)
                // for now it is hardcoded
                budgetString = settings.getString("budget", budgetString);
                System.out.println("BudgetString inside nonbudget:");
                System.out.println(budgetString);
                budget = Double.valueOf(budgetString);

                remainingBudget = budget - (highTot + medTot + lowTot);
                System.out.println(budget);
                System.out.println(highTot);
                System.out.println(medTot);
                System.out.println(lowTot);
                mRemainingBudgetTextBox.setText(dollarFormat.format(remainingBudget));
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                // Failed to read value
                Log.v("TestRead", "Failed to read value.", databaseError.toException());
            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        profileImage = findViewById(R.id.profileImage);
        pathRef = storageRef.child(uid + ".jpeg");
        pathRef.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);//convert bytes into bitmap
                profileImage.setImageBitmap(bitmap);
            }
        });

        //this should be downloading the image from Storage into profileImage, not working right now
        //may need to use a bitmap implementation, will look into further later
            /*if(pathRef != null)
            {
                Glide.with(this).load(pathRef).into(profileImage);
            }*/


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