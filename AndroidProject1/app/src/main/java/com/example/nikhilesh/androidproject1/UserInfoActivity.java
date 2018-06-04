package com.example.nikhilesh.androidproject1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserInfoActivity extends AppCompatActivity {

    // variable declarations
    TextView mFirstName;
    TextView mLastName;
    TextView mEmail;
    TextView mGraduationYear;
    ImageView userImageView;
    Button mButtonSignOut;


    // variable declaration for firebase databse and firebase storage
    private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        break;

                    case R.id.ic_book_black:
                        Intent intentRateProfessor = new Intent(UserInfoActivity.this,RateProfessorActivity.class);
                        startActivity(intentRateProfessor);
                        break;
                    case R.id.ic_business_center:
                        Intent intentJobOpenings = new Intent(UserInfoActivity.this,JobOpeningsActivity.class);
                        startActivity(intentJobOpenings);
                        break;

                    case R.id.ic_event:
                        Intent intentEvent = new Intent(UserInfoActivity.this,EventActivity.class);
                        startActivity(intentEvent);
                        break;
                    case R.id.ic_rate_course:
                        Intent intentRateCourse = new Intent(UserInfoActivity.this,ViewCourseActivity.class);
                        startActivity(intentRateCourse);
                        break;
                }
                return false;
            }
        });

        configuration();
        }

    private void configuration(){
        //mFirstName = findViewById(R.id.txtFirstName);
        mLastName = findViewById(R.id.txtLastName);
        mEmail = findViewById(R.id.txtEmail);
        mGraduationYear = findViewById(R.id.txtGraduationYear);
        userImageView = findViewById(R.id.imgUserImage);
        mButtonSignOut = findViewById(R.id.signOut);

        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.getInstance().signOut();
                Intent intent = new Intent(UserInfoActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        // connect to the firebase database
        mAuth = FirebaseAuth.getInstance();
        mFireBaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        // get the reference of the node for the logged in user
        myRef = mFireBaseDatabase.getReference().child("users").getRef().child(userID);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                        // userInfo will have the user details.
                        // we will assign those details to the fields declared above
                        // we will use Picasso to retrieve image from the firebase wstorage URL
                        // the dependency is added in the app gradle.

                        UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                        String userURL = userInfo.geturl();
                        if(userURL != null || !userURL.isEmpty())
                        Picasso.get().load(userURL).into(userImageView);
                        //mFirstName.setText("WELCOME");
                        mLastName.setText(userInfo.getFirstname().toString().concat(" ").concat(userInfo.getLastname().toString()));
                        mEmail.setText(userInfo.getEmail().toString());
                        mGraduationYear.setText(userInfo.getGraduationyear().toString());

                }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
