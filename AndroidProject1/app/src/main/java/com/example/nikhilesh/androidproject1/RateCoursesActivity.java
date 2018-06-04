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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RateCoursesActivity extends AppCompatActivity {
    EditText description,ratings;
    Button rateCourse;
    String profListId;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef = null;
    TextView grFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_rater);
        rateCourse=findViewById(R.id.rateCourse); // id for rateCourse
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        configuration();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        //bottom nav bar menu
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intentHome = new Intent(RateCoursesActivity.this,UserInfoActivity.class);
                        startActivity(intentHome);
                        break;

                    case R.id.ic_book_black:
                        Intent intentRateProfessor = new Intent(RateCoursesActivity.this,RateProfessorActivity.class);
                        startActivity(intentRateProfessor);
                        break;

                    case R.id.ic_business_center:
                        Intent intentJobOpenings = new Intent(RateCoursesActivity.this,JobOpeningsActivity.class);
                        startActivity(intentJobOpenings);
                        break;

                    case R.id.ic_event:
                        Intent intentEvent = new Intent(RateCoursesActivity.this,EventActivity.class);
                        startActivity(intentEvent);
                        break;
                    case R.id.ic_rate_course:
                        break;

                }
                return false;
            }
        });

        //method to rate courses
        rateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description= findViewById(R.id.courseDesc); // find the coursedesc id
                ratings=findViewById(R.id.courseRating); // find the courseRATING id

                String desc= description.getText().toString(); //gets the description
                String rating= ratings.getText().toString(); // gets the ratings
                //validation for the ratings and description
                if(desc.isEmpty() || rating.isEmpty()){
                    Toast.makeText(RateCoursesActivity.this,R.string.enter_all_fields,Toast.LENGTH_LONG).show();
                }else if(Integer.parseInt(rating)>5 ) {
                    Toast.makeText(RateCoursesActivity.this, R.string.rating, Toast.LENGTH_LONG).show();
                }else {
                    // this code adds into database. This will add into ratecourses table
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("ratecourses");
                    profListId = getIntent().getStringExtra("uid");
                    database.child("courseList").child(profListId).child("description").push().setValue(desc); //puts description data into courseList
                    database.child("courseList").child(profListId).child("ratings").push().setValue(rating); //puts ratings data into courseList
                    configuration();
                    Toast.makeText(RateCoursesActivity.this,R.string.course_rated,Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    //gets the data and put into listview
    public void configuration() {
        database = FirebaseDatabase.getInstance();
        String uid = getIntent().getStringExtra("uid");
        // gets the data for a specific userId
        myRef = database.getReference("ratecourses").getRef().child("courseList").child(uid);
        myRef.addValueEventListener(new ValueEventListener() {
            //if there is no error this will be called
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ListView listView = findViewById(android.R.id.list); // finds the list view id
                List<String> courseDescriptionList = new ArrayList<>();
                List<String> courseRatingList = new ArrayList<>();
                List<RatedCourse> list = new ArrayList<>();
                double totalGrade=0.0;
                //gets the data into the map
                Map description= (Map) dataSnapshot.child("description").getValue(); // gets the values desc into maop
                Map ratings = (Map) dataSnapshot.child("ratings").getValue();// gets the value of ratings in map
                String professorName= (String) dataSnapshot.child("profName").getValue(); // gets the profName
                if(ratings!=null) {
                    for (Object ratingValue : ratings.values())
                        totalGrade +=Long.parseLong(ratingValue.toString());
                }
                grFinal=findViewById(R.id.finalGrade); //finalgrade id
                //calculates the cumulative finalGrade
                if(ratings!= null) {
                    double finalGrade = totalGrade / ratings.size();
                    finalGrade = Math.round(finalGrade * 100.0) / 100.0;
                    grFinal.setText("Overall rating is :"+finalGrade+""); //final grade put into textview
                }
                if(description!=null) {
                    for (Object descValues : description.values())
                    list.add(new RatedCourse(descValues.toString()));
                }
        //adds into listview if list is not null
                if(list!=null) {
                    RatedCourseAdapter adapter = new RatedCourseAdapter(RateCoursesActivity.this, list);
                    listView.setAdapter(adapter);
                }
                //
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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