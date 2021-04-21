package com.example.cse3310semesterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText mNewCurrentPasswordEntry;
    Button mReturnHomeBtn, mSignOutFromAccountBtn, mChangePasswordBtn, mAvatarButton;
    //ImageView profileImage;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mReturnHomeBtn = findViewById(R.id.ReturnHomeBtn);
        mSignOutFromAccountBtn = findViewById(R.id.SignOutFromAccountBtn);
        mChangePasswordBtn = findViewById(R.id.changePasswordBtn); //change current password
        mNewCurrentPasswordEntry = findViewById(R.id.newCurrentPasswordEntry); //user input of password
        mAvatarButton = findViewById(R.id.AvatarButton);//change user avatar
        storageRef = FirebaseStorage.getInstance().getReference();
        mAvatarButton.setOnClickListener(this);
        /*mAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });*/
        mChangePasswordBtn.setOnClickListener(this);
        mSignOutFromAccountBtn.setOnClickListener(this);
        mReturnHomeBtn.setOnClickListener(this);


    }

    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.ReturnHomeBtn:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));//jump to homepage
                break;
            case R.id.SignOutFromAccountBtn:
                Toast.makeText(AccountActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
            case R.id.changePasswordBtn:
                changePassword();
                break;
            case R.id.AvatarButton://temporary, not yet implemented
                changeProfile();
                //Toast.makeText(AccountActivity.this, "Please somebody make me work :(", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void changePassword()
    {
        String passwordValue = mNewCurrentPasswordEntry.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // If the password text entry is empty tell the user that the password is required
        if(TextUtils.isEmpty(passwordValue))
        {
            mNewCurrentPasswordEntry.setError("Password is required");
            return;
        }

        // We should make the 6 in this case an int value we define and change
        // once then it changes through the whole program
        if(passwordValue.length() < 6)
        {
            mNewCurrentPasswordEntry.setError("Password should be more than 6 characters");
            return;
        }

        //very very basic, not making user reauthenticate
        user.updatePassword(passwordValue).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(AccountActivity.this, "Password changed successfully!", Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(AccountActivity.this, "Password change failed!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //@Override
    public void changeProfile()
    {//open gallery
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGalleryIntent, 1000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) //from startActivityForResult
        {
            if(resultCode == Activity.RESULT_OK)
            {
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri);
            }
        }
    }

    public void uploadImageToFirebase(Uri imageUri)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();//user user id to store image
        StorageReference fileRef = storageRef.child(uid + ".jpeg");//attaching .jpeg to title
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AccountActivity.this, "Image has been uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AccountActivity.this, "Upload has failed", Toast.LENGTH_SHORT).show();
            }
        });


        }

}