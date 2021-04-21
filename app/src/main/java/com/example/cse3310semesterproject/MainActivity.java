package com.example.cse3310semesterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;//required to call FirebaseAuth commands
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.Currency;
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
        mRemainingBudgetTextBox = findViewById(R.id.remainingBudgetTextBox);
        mSignOutBtn = findViewById(R.id.SignOutBtn);
        profileImage = findViewById(R.id.profileImage);

        // The below code allows for the remaining budget value to be represented in US currency
        Locale usa = new Locale("en", "US");
        Currency dollars = Currency.getInstance(usa);
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        // remaining budget need to be (weekly budget - sum of all expenses in the week)
        // for now it is hardcoded

        remainingBudget = 130.00;
        mRemainingBudgetTextBox.setText(dollarFormat.format(remainingBudget));


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