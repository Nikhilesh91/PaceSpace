package com.example.nikhilesh.androidproject1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
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

public class ViewCourseActivity extends AppCompatActivity {

    private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef = null;
    Button addCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);
        addCourse = findViewById(R.id.addCourse); //button for addCourse
        configuration();
        //navigation bar .
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        final EditText courseName =findViewById(R.id.courseName);
        final EditText profName =findViewById(R.id.profName);
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        //this is the bottom nav bar menu
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intentHome = new Intent(ViewCourseActivity.this,UserInfoActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.ic_book_black:
                        Intent intentRateProfessor = new Intent(ViewCourseActivity.this,RateProfessorActivity.class);
                        startActivity(intentRateProfessor);
                        break;
                    case R.id.ic_business_center:
                        Intent intentJobOpenings = new Intent( ViewCourseActivity.this, JobOpeningsActivity.class);
                        startActivity(intentJobOpenings);
                        break;
                    case R.id.ic_event:
                        Intent intentEvent = new Intent( ViewCourseActivity.this, EventActivity.class);
                        startActivity(intentEvent);
                        break;
                    case R.id.ic_rate_course:
                        break;
                }
                return false;
            }
        });
    //this is the function to add the course
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cName= courseName.getText().toString(); //gets the courseName
                String pName= profName.getText().toString(); //gets the profName
                if(cName.isEmpty() || pName.isEmpty()){
                    Toast.makeText(ViewCourseActivity.this,R.string.enter_all_fields,Toast.LENGTH_LONG).show();
                }else{
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("ratecourses").getRef().child("courseList");
                    String userId = myRef.push().getKey();
                    database.child(userId).child("courseName").setValue(cName); //add the courseName into database
                    database.child(userId).child("profName").setValue(pName); //add the profName into database
                    Toast.makeText(ViewCourseActivity.this,R.string.course_Added,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //gets the data and puts into the listview
    public void configuration() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ratecourses").getRef().child("courseList"); // gets the data of the courseList. Gets all of the list
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            //if this is successfully this will be called
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<ViewCourse> courseList = new ArrayList<ViewCourse>();
                ListView listView = findViewById(android.R.id.list); //gets the listview id
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String userId= child.getKey();
                    String courseName = child.child("courseName").getValue(String.class); //gets the courseName from database
                    courseList.add(new ViewCourse(courseName,userId)); //adds into courseList
                }
                //puts into listview
                ViewCourseAdapter adapter = new ViewCourseAdapter(ViewCourseActivity.this, courseList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ViewCourseActivity.this,RateCoursesActivity.class);
                        intent.putExtra("uid",courseList.get(position).getUserId());
                        ViewCourseActivity.this.startActivity(intent);
                    }
                });
            }
            //if there is an error this will be called
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
    }
}
