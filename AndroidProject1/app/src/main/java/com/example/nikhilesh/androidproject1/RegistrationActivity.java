package com.example.nikhilesh.androidproject1;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class RegistrationActivity extends AppCompatActivity {


    // variable declaration
    Button mUpload;
    Button mRegister;
    EditText mFirstName;
    EditText mLastName;
    EditText mGraduationYear;
    EditText mEmailAddress;
    EditText mPassword;
    EditText mConfirmPassword;
    private String userID;
    private Uri imgURI;         // to store the uri of the image selected by user

    //FIrebase variable declaration
    // Here we will require reference of firebase storage as we want to upload an image
    // User can choose not to upload an image.

    private FirebaseStorage mFirebaseStorage;
    private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // call the method to initialize views used in layout
        configViewInstances();

        // call the method to initialize the database objects
        configDataBase();

        // select the image using Intent and File manager
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { UploadImage();}});

        // the button click event for register button
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {registerUser();
                mFirstName.setText("");
                mLastName.setText("");
                mEmailAddress.setText("");
                mGraduationYear.setText("");
                mPassword.setText("");
                mConfirmPassword.setText("");

            }
        });
    }
            private void registerUser(){
                final String firstname = mFirstName.getText().toString().trim();
                final String lastname = mLastName.getText().toString().trim();
                final String email = mEmailAddress.getText().toString().trim();
                final String graduationyear = mGraduationYear.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String confirmPassword = mConfirmPassword.getText().toString().trim();

                if(firstname==null || firstname.isEmpty()){
                    Toast.makeText(getApplicationContext(),R.string.str_RegistrationActivity_Class_Validation_FirstName,Toast.LENGTH_LONG).show();
                    return;
                }else if (firstname==null || firstname.isEmpty()){
                    Toast.makeText(getApplicationContext(),R.string.str_RegistrationActivity_Class_Validation_LastName,Toast.LENGTH_LONG).show();
                    return;
                }else if(email == null || email.isEmpty()){
                    Toast.makeText(getApplicationContext(),R.string.str_RegistrationActivity_Class_Validation_email,Toast.LENGTH_LONG).show();
                    return;
                }else if(graduationyear == null || graduationyear.isEmpty()){
                    Toast.makeText(getApplicationContext(),R.string.str_RegistrationActivity_Class_Validation_graduation_Year,Toast.LENGTH_LONG).show();
                    return;
                }else if(password == null || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),R.string.str_RegistrationActivity_Class_Validation_password,Toast.LENGTH_LONG).show();
                    return;
                }else if(confirmPassword== null || confirmPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),R.string.str_RegistrationActivity_Class_Validation_confirmPassword,Toast.LENGTH_LONG).show();
                    return;
                }else if(!password.equals(confirmPassword)){
                    Toast.makeText(getApplicationContext(),R.string.str_RegistrationActivity_Class_Validation_comparePassword,Toast.LENGTH_LONG).show();
                    return;
                }else {

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                userID = user.getUid();
                                 final String imgURL;

                                 // we first check if the user has entered all the values
                                // since image is optional, we first insert all the details of the user in firebase database
                                // this ensures that the user is created with or without image
                                // advantage is that we can be sure that user details are uploaded even if there is an
                                // error while uploading the image on firebase storage.

                                final UserInfo userInfo = new UserInfo(firstname, lastname, email, graduationyear, password,"");
                                myRef.child("users").child(userID).setValue(userInfo);
                                Toast.makeText(getApplicationContext(), R.string.str_RegistrationActivity_Class_Validation_UserRegisteredSuccess, Toast.LENGTH_LONG).show();

                                // if user has selected an image
                                if(imgURI != null){

                                    // upload the image in firebase storage and save the path into the firebase database
                                    StorageReference storageReference = mFirebaseStorage.getReference();
                                    storageReference.child("user-images").child(System.currentTimeMillis()+"").putFile(imgURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            final String temp = taskSnapshot.getDownloadUrl().toString();
                                            userInfo.seturl(temp);
                                            myRef.child("users").child(userID).setValue(userInfo);

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),R.string.str_RegistrationActivity_Class_Validation_Image_Failure,Toast.LENGTH_LONG).show();
                                        }
                                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                        }
                                    });

                                }

                            } else {
                                String temp = task.getException().toString();
                                Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();

                                // Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(getApplicationContext(), R.string.str_RegistrationActivity_Class_Validation_User_Already_Exists, Toast.LENGTH_LONG).show();

                            }


                        }
                    });
                }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 80 && resultCode == RESULT_OK && data!=null){imgURI = data.getData(); }
    }


    // function to enable user to select an image
    private void UploadImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,80);
    }

    private void configViewInstances(){
        // get the instance of all the views in the layout
        mFirstName = findViewById(R.id.edtFirstName);
        mLastName = findViewById(R.id.edtLastName);
        mEmailAddress = findViewById(R.id.edtEmailAddress);
        mPassword = findViewById(R.id.edtPassword);
        mGraduationYear = findViewById(R.id.edtGraduationYear);
        mConfirmPassword = findViewById(R.id.edtConfirmPassword);
        mRegister = findViewById(R.id.btnRegister);
        mUpload = findViewById(R.id.btnUpload);
    }

    private void configDataBase(){
        // connect to the firebase database
        mAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFireBaseDatabase.getReference();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
