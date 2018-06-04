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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaterActivity extends AppCompatActivity {
    EditText description,ratings;
    Button rateProfessor;
    String profListId;
    private static FirebaseDatabase database;
    private static DatabaseReference myRef = null;
    TextView grFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rater);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        configuration();
        final EditText professorName =findViewById(R.id.professorName);
        rateProfessor= findViewById(R.id.rateProf);
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        //bottom view nav bar menu
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intentUserInfo = new Intent(RaterActivity.this,UserInfoActivity.class);
                        startActivity(intentUserInfo);
                        break;
                    case R.id.ic_book_black:
                        Intent intentRateProfessor = new Intent(RaterActivity.this,RateProfessorActivity.class);
                        startActivity(intentRateProfessor);
                        break;
                    case R.id.ic_business_center:
                        Intent intentJobOpenings = new Intent(RaterActivity.this,JobOpeningsActivity.class);
                        startActivity(intentJobOpenings);
                        break;

                    case R.id.ic_event:
                        Intent intentEvent = new Intent(RaterActivity.this,EventActivity.class);
                        startActivity(intentEvent);
                        break;
                    case R.id.ic_rate_course:
                        Intent intentRateCourse = new Intent(RaterActivity.this,ViewCourseActivity.class);
                        startActivity(intentRateCourse);
                        break;

                }
                return false;
            }
        });
//rate professor addition code
        rateProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description= findViewById(R.id.profDescription);
                ratings=findViewById(R.id.profRating);
                String desc= description.getText().toString();
                String rating= ratings.getText().toString();
                //validation for ratings and desc
                if(desc.isEmpty() || rating.isEmpty() ){
                    Toast.makeText(RaterActivity.this,R.string.enter_all_fields,Toast.LENGTH_LONG).show();
                }else if (Integer.parseInt(rating)>5 ){
                    Toast.makeText(RaterActivity.this,R.string.rating,Toast.LENGTH_LONG).show();
                }else {

                    //insertion code
                    Professor professor = new Professor();
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("ratemyprofessor");
                    profListId = getIntent().getStringExtra("uid"); // gets the userId
                    database.child("profList").child(profListId).child("description").push().setValue(desc); //puts data into database
                    database.child("profList").child(profListId).child("ratings").push().setValue(rating); //puts value into database
                    configuration();
                    Toast.makeText(RaterActivity.this,R.string.prof_rated,Toast.LENGTH_LONG).show();
                    description.setText("");
                    ratings.setText("");
                }
            }
        });
    }
//code for data retrieval
    public void configuration() {
        database = FirebaseDatabase.getInstance();
        String uid = getIntent().getStringExtra("uid");
        //gets the data for specific profList
        myRef = database.getReference("ratemyprofessor").getRef().child("profList").child(uid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            //if data is retrieved this will be called
            public void onDataChange(DataSnapshot dataSnapshot) {
                ListView listView = findViewById(android.R.id.list);
                List<String> descList = new ArrayList<>();
                List<String> ratingList = new ArrayList<>();
                List<RatedProfessor> list = new ArrayList<>();
                double totalGrade=0.0;
                Map description= (Map) dataSnapshot.child("description").getValue(); //gets the description value into mao
                Map ratings = (Map) dataSnapshot.child("ratings").getValue(); //gets the ratings value into map
                String subject= (String) dataSnapshot.child("subject").getValue(); //gets the subject value
                if(ratings!=null) {
                    for (Object ratingValue : ratings.values())
                        totalGrade +=Long.parseLong(ratingValue.toString());
                }
                grFinal = findViewById(R.id.finalGrade);
                //calculates the finalGrade
                if(ratings!=null) {
                    double finalGrade = totalGrade / ratings.size();
                    finalGrade = Math.round(finalGrade * 100.0) / 100.0;
                    grFinal.setText("Overall rating is :" + finalGrade + "");
                }
                //puts data into list if description is not null
                if(description!=null ) {
                    for (Object descValues : description.values())
                        list.add(new RatedProfessor(descValues.toString()));
                }
                //sets the adapter and calls the lisview
                RatedProfessorAdapter adapter = new RatedProfessorAdapter(RaterActivity.this, list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }
            @Override
            //if data is not retrieved this will be called
            public void onCancelled(DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
    }

}
