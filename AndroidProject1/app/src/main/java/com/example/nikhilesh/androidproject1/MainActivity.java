package com.example.nikhilesh.androidproject1;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // variable declarations
    ImageView mImgView;
    EditText mEditUserName;
    EditText mEditPassword;
    Button mButtonLoginIn;
    Button mButtonRegister;

    // variable declaration required to connect to firebase database
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgView = findViewById(R.id.imgLogoImage);
        mEditUserName = findViewById(R.id.edtEmailAddress);
        mEditPassword = findViewById(R.id.edtPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        mButtonLoginIn = findViewById(R.id.btnLogIn);

        // The Login User is used to log the appropriate user in for the application
        // For authentication we are using firebase database and authentication
        // The depemdencies can be found in the app gradle file

        mButtonLoginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {LoginUser();}});

        // If it is a new user, the person has to first register
        // On clicking the register button, the person is redirected to the registration activity

        mButtonRegister = findViewById(R.id.btnRegister);
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditPassword.setText("");
                mEditUserName.setText("");
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

    private void LoginUser(){

        String userName = mEditUserName.getText().toString().trim();
        String pass = mEditPassword.getText().toString().trim();

        // check if user name or password is null.
        // if null then display the appropriate message and return.
        // else login user using firebase.

        if(userName ==null || userName.isEmpty()){
            Toast.makeText(getApplicationContext(),R.string.str_MainActivity_class_Validation_UserName,Toast.LENGTH_LONG).show();
            return;
        }else if(pass == null || pass.isEmpty()){
            Toast.makeText(getApplicationContext(),R.string.str_MainActivity_class_Validation_Password,Toast.LENGTH_LONG).show();
            return;
        }else {
            firebaseAuth.signInWithEmailAndPassword(userName, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    // if the user name passes, the isSuccessful function returns true.
                    // if true, the user will be redirected to the UserInfoActivity.

                    if (task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                        startActivity(intent);
                    }

                    // else display message showing incorrect user name or password
                    else {
                        Toast.makeText(getApplicationContext(), R.string.str_MainActivity_class_Validation_Result, Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

        mEditPassword.setText("");
        mEditUserName.setText("");
    }
}
