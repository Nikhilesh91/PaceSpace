package com.example.nikhilesh.androidproject1;

import android.app.job.JobInfo;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobOpeningsActivity extends AppCompatActivity {


    // variable declaration

    ListView listView;
    Button mButtonSubmitJobOpening;
    EditText mEditTextTitle;
    EditText mEditTextQualification;
    EditText mEditTextContact;

    // variabel declaration for firebase database

    private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_openings);
        configuration();

        // setting the bottom navigation to the page.
        //by this user can switch to different activities from HomeActivity by getting th id of the menu item
        // once we have the id, we can navigate to different activity using intent;


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intentUserInfo = new Intent(JobOpeningsActivity.this, UserInfoActivity.class);
                        startActivity(intentUserInfo);
                        break;
                    case R.id.ic_book_black:
                        Intent intentRateProfessor = new Intent(JobOpeningsActivity.this, RateProfessorActivity.class);
                        startActivity(intentRateProfessor);
                        break;
                    case R.id.ic_business_center:
                        break;
                    case R.id.ic_event:
                        Intent intentEvent = new Intent(JobOpeningsActivity.this, EventActivity.class);
                        startActivity(intentEvent);
                        break;
                    case R.id.ic_rate_course:
                        Intent intentRateCourse = new Intent(JobOpeningsActivity.this,ViewCourseActivity.class);
                        startActivity(intentRateCourse);
                        break;
                }
                return false;
            }
        });

    }

    public void configuration() {
        mButtonSubmitJobOpening = findViewById(R.id.btnSubmitJobOpening);
        listView = findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("jobopenings");
        //myRef = mFireBaseDatabase.getReference().child("users").getRef().child(userID);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<JobOpeningInfo> professorList = new ArrayList<JobOpeningInfo>();

                // Insert all objects in firebase database into the list object
                // This will be added to the list adapter class

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String title = child.getValue(JobOpeningInfo.class).getTitle().toString();
                    String qualification = child.getValue(JobOpeningInfo.class).getQualification().toString();
                    String contact = child.getValue(JobOpeningInfo.class).getContact().toString();
                    professorList.add(new JobOpeningInfo(title, qualification, contact));

                }
                JobOpeningList adapter = new JobOpeningList(JobOpeningsActivity.this, professorList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(JobOpeningsActivity.this, "Clicked on list row", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });

        mEditTextTitle = findViewById(R.id.jobOpeningEdtTitle);
        mEditTextQualification = findViewById(R.id.jobOpeningEdtQualification);
        mEditTextContact = findViewById(R.id.jobOpeningEdtContact);
        mEditTextTitle.clearFocus();
        mEditTextQualification.clearFocus();
        mEditTextContact.clearFocus();
        mButtonSubmitJobOpening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contact = mEditTextContact.getText().toString().trim();
                String qualification = mEditTextQualification.getText().toString().trim();
                String title = mEditTextTitle.getText().toString().trim();

                if(title == null || title.isEmpty()){
                    Toast.makeText(getApplication(),R.string.str_JobOpeningActivity_Class_Validation_title,Toast.LENGTH_LONG).show();
                }else if(qualification==null || qualification.isEmpty()){
                    Toast.makeText(getApplication(),R.string.str_JobOpeningActivity_Class_Validation_qualification,Toast.LENGTH_LONG).show();
                }else if(contact == null || contact.isEmpty()) {
                    Toast.makeText(getApplication(),R.string.str_JobOpeningActivity_Class_Validation_contact,Toast.LENGTH_LONG).show();

                }else{

                    // insert a new job opening in the database

                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("jobopenings");
                    String userId = myRef.push().getKey();
                    JobOpeningInfo jobInfo = new JobOpeningInfo(title, qualification, contact);
                    database.child(userId).setValue(jobInfo);
                    mEditTextTitle.setText("");
                    mEditTextQualification.setText("");
                    mEditTextContact.setText("");
                }


            }
        });
    }

}
