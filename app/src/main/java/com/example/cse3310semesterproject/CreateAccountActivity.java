package com.example.cse3310semesterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener//implements required for setOnClickListener's(this)
{

    Button mReturnToLoginBtn, mCreateAccount;
    private FirebaseAuth mAuth;
    EditText mEmail, mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mReturnToLoginBtn = findViewById(R.id.returnToLoginBtn);

        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.newEmailEntry);//link email and password to id of textbox
        mPassword = findViewById(R.id.newPasswordEntry);
        mCreateAccount = findViewById(R.id.registerUser);//link mCreateAccount with register user button
        mReturnToLoginBtn.setOnClickListener(this);
        mCreateAccount.setOnClickListener(this);
    }

            @Override
            public void onClick(View v) {//onClick now checks which id is matched
                switch (v.getId()) {
                    case R.id.registerUser://user clicked register account
                        userCreate();
                        break;
                    case R.id.returnToLoginBtn://user wants to go back to login screen
                        startActivity(new Intent(getApplicationContext(), Login.class));//jump to Login.java
                        break;
                }
            }
            public void userCreate(){
                String email = mEmail.getText().toString().trim(); //storage
                String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("E-mail is required");
                    mEmail.requestFocus();//request focus pings the specified location
                    return;
                }
                // If the password text entry is empty tell the user that the password is required
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required");
                    mPassword.requestFocus();
                    return;
                }
                if(password.length() < 6){//can adjust if we want additional characteristics in password
                    mPassword.setError("Min. of 6 characters!");
                    mPassword.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {//given by Firebase->authentication
                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    User user = new User(email); //will use User.java constructor to fill email into string
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(CreateAccountActivity.this, "User Registration Successful!", Toast.LENGTH_LONG).show();
                                                updateUI();
                                                return;
                                            }else{
                                                Toast.makeText(CreateAccountActivity.this, "User Registration has Failed!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                    FirebaseDatabase.getInstance().getReference("Users").child("Email").setValue(user);
                                }
                            }
                        });
                // This if condition is the one that determine if the login information was correct and tells the user
                // the login was successful then takes them to the homepage (MainActivity)
                Toast.makeText(CreateAccountActivity.this, "Successfully Created Account!", Toast.LENGTH_SHORT).show();
            }


            public void updateUI(){//used in task.isSuccessful() condition, jump to login page
                startActivity(new Intent(getApplicationContext(), Login.class));
            }


    }
