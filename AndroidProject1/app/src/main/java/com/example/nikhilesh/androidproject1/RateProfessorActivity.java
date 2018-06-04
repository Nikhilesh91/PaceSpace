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

public class RateProfessorActivity extends AppCompatActivity {
    private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static FirebaseDatabase database; // database
    private static DatabaseReference myRef = null; // this is the database Reference
    Button addProf;

    @Override
    //oncreate method is called
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_professor); //this activity will be inflated
        addProf = findViewById(R.id.addProfessor); // gets the add professor id
        configuration();
      //bottom nav bar code is written here
        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        final EditText professorName =findViewById(R.id.professorName);
        final EditText subjectName= findViewById(R.id.subjectName);
        //this is the bottom nav bar menu
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        Intent intentRateProfessor = new Intent(RateProfessorActivity.this,UserInfoActivity.class);
                        startActivity(intentRateProfessor);
                        break;

                    case R.id.ic_book_black:
                        break;
                    case R.id.ic_business_center:
                        Intent intentJobOpenings = new Intent(RateProfessorActivity.this,JobOpeningsActivity.class);
                        startActivity(intentJobOpenings);
                        break;

                    case R.id.ic_event:
                        Intent intentEvent = new Intent(RateProfessorActivity.this,EventActivity.class);
                        startActivity(intentEvent);
                        break;

                    case R.id.ic_rate_course:
                        Intent intentRateCourse = new Intent(RateProfessorActivity.this,ViewCourseActivity.class);
                        startActivity(intentRateCourse);
                        break;
                }
                return false;
            }
        });

        //Code to add the professor into the database
        addProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject =subjectName.getText().toString(); //gets the subject name
                String pName= professorName.getText().toString(); //gets the professor name
                //validation for subject and professor name
                if(subject.isEmpty() || pName.isEmpty()){
                    Toast.makeText(RateProfessorActivity.this,R.string.enter_all_fields,Toast.LENGTH_LONG).show();
                }else{
                    //insertion code. Inserts into ratemyprofessor node
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("ratemyprofessor").getRef().child("profList");
                    String userId = myRef.push().getKey();
                    Professor professor = new Professor(pName);
                    database.child(userId).setValue(professor); //this add value of professor and subject into database
                    database.child(userId).child("subject").setValue(subject); //adds the subject into database
                    Toast.makeText(RateProfessorActivity.this,R.string.prof_Added,Toast.LENGTH_LONG).show();
                    subjectName.setText("");
                    professorName.setText("");
                }
            }
        });

    }
//gets the data. This is for data retrieval and listview calls
    private void configuration(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ratemyprofessor").getRef().child("profList"); //this will retrieve data from profList
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            //if data is retrieved successfully,this will be called
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Professor> professorList = new ArrayList<Professor>();
                ListView listView = findViewById(android.R.id.list); //FIND THE LISTview id
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String userId= child.getKey(); // gets the userID
                    String profName = child.child("profName").getValue(String.class); //gets the prof name
                    //String profName = child.getValue(Professor.class).getProfName().toString();
                    professorList.add(new Professor(profName,userId)); //adds the data into the list
                }

                //listview call
                RatemyProfessorAdapter adapter = new RatemyProfessorAdapter(RateProfessorActivity.this, professorList);
                listView.setAdapter(adapter);
                //onclick of listview new activity will open
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(RateProfessorActivity.this,RaterActivity.class);
                        intent.putExtra("uid",professorList.get(position).getProfListUserId());
                        RateProfessorActivity.this.startActivity(intent);
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
