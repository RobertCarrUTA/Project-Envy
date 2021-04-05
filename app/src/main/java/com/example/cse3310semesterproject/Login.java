package com.example.cse3310semesterproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// -------------------------------------------------------------------------------------------------
// ***** LOGIN INFO FOR THE APP *****
// E-mail: cse3310@gmail.com
// Password: password123
// -------------------------------------------------------------------------------------------------


public class Login extends AppCompatActivity implements View.OnClickListener
{
    EditText mEmail, mPassword;
    Button loginBtn, createAccountBtn;
    private FirebaseAuth mAuth; //firebase object
    private static final String TAG = Login.class.getSimpleName(); //required declaration of TAG


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //linking button and EditText's to id's in activity_login.xml
        mEmail = findViewById(R.id.EmailLoginEntry);
        mPassword = findViewById(R.id.PasswordLoginEntry);
        loginBtn = findViewById(R.id.SignInBtn);
        createAccountBtn = findViewById(R.id.RegisterBtn);
        loginBtn.setOnClickListener(this);
        createAccountBtn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        // We can replace this with a lambda
    }

            @Override
            public void onClick(View v) {//now checks which button is pressed by id
                switch (v.getId()) {
                    case R.id.RegisterBtn://jump to CreateAccountActivity
                        startActivity(new Intent(getApplicationContext(), CreateAccountActivity.class));
                        break;
                    case R.id.SignInBtn://jump to userLogin function to log user in
                        userLogin();
                        break;
                }
            }


    public void userLogin(){
        String email = mEmail.getText().toString(); //storage
        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("E-mail is required");
            return;
        }
        // If the password text entry is empty tell the user that the password is required
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Password is required");
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)//function provided by Firebase->authentication->email and password
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class)); //jump to MainActivity if login successful
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser(); //grabbing account from database
        if(currentUser != null){
            updateUI(currentUser); //go to Mainpage with pulled user
        }
    }

}