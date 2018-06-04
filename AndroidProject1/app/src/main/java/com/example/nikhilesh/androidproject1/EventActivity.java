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

public class EventActivity extends AppCompatActivity {

    // variable declaration

    ListView listView;
    Button mButtonEvent;
    EditText mEditTextTitle;
    EditText mEditTextDescription;

    // variable declaration for firebase database

    private static FirebaseDatabase database;
    private static DatabaseReference myRef = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        configuration();

        // setting the bottom navigation to the page.
        //by this user can switch to different activities from HomeActivity by getting th id of the menu item
        // once we have the id, we can navigate to different activity using intent;


        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intentUserInfo = new Intent(EventActivity.this,UserInfoActivity.class);
                        startActivity(intentUserInfo);
                        break;
                    case R.id.ic_book_black:
                        Intent intentRateProfessor = new Intent(EventActivity.this,RateProfessorActivity.class);
                        startActivity(intentRateProfessor);
                        break;
                    case R.id.ic_business_center:
                        Intent intentJobOpenings = new Intent(EventActivity.this,JobOpeningsActivity.class);
                        startActivity(intentJobOpenings);
                        break;
                    case R.id.ic_event:
                        break;
                    case R.id.ic_rate_course:
                        Intent intentRateCourse = new Intent(EventActivity.this,ViewCourseActivity.class);
                        startActivity(intentRateCourse);
                        break;

                }
                return false;
            }
        });
    }

    private void configuration(){

        mButtonEvent = findViewById(R.id.btnSubmitEvent);
        listView = findViewById(R.id.listViewEvent);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("events");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<EventInfo> EventList = new ArrayList<EventInfo>();

                // We will assign the records obtained from firebase to the list

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String title = child.getValue(EventInfo.class).getEventtitle().toString();
                    String description = child.getValue(EventInfo.class).getEventdescription().toString();

                    EventList.add(new EventInfo(title, description));

                }
                EventList adapter = new EventList(EventActivity.this, EventList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(EventActivity.this, "Clicked on list row", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });


        mEditTextTitle = findViewById(R.id.eventEdtTitle);
        mEditTextDescription = findViewById(R.id.eventEdtDescription);
        mButtonEvent = findViewById(R.id.btnSubmitEvent);
        mButtonEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mEditTextTitle.getText().toString().trim();
                String description = mEditTextDescription.getText().toString().trim();

                if(title == null || title.isEmpty()){
                    Toast.makeText(getApplication(),R.string.str_EventActivity_Class_Validation_title,Toast.LENGTH_LONG).show();
                }else if(description == null || description.isEmpty()){
                    Toast.makeText(getApplication(),R.string.str_EventActivity_Class_Validation_description,Toast.LENGTH_LONG).show();
                }else {

                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("events");
                    String userId = myRef.push().getKey();
                    EventInfo eventInfo = new EventInfo(title, description);
                    database.child(userId).setValue(eventInfo);
                    mEditTextTitle.setText("");
                    mEditTextDescription.setText("");

                }

            }
        });


    }

}
