package com.example.nikhilesh.androidproject1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class HomepageActivity extends AppCompatActivity {

    // Variable declarations


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        // setting the bottom navigation to the page.
        //by this user can switch to different activities from HomeActivity by getting th id of the menu item
        // once we have the id, we can navigate to different activity using intent;
       BottomNavigationView bottomNavigationView =  findViewById(R.id.bottomNavView_Bar);
       bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.ic_home:
                       Intent intentUserInfo = new Intent(HomepageActivity.this,UserInfoActivity.class);
                       startActivity(intentUserInfo);
                       break;
                       case R.id.ic_book_black:
                       Intent intentRateProfessor = new Intent(HomepageActivity.this,RateProfessorActivity.class);
                       startActivity(intentRateProfessor);
                        break;
                   case R.id.ic_business_center:
                       Intent intentJobOpenings = new Intent(HomepageActivity.this,JobOpeningsActivity.class);
                       startActivity(intentJobOpenings);
                       break;

                   case R.id.ic_event:
                       Intent intentEvent = new Intent(HomepageActivity.this,EventActivity.class);
                       startActivity(intentEvent);
                       break;

                   case R.id.ic_rate_course:
                       Intent intentRateCourse = new Intent(HomepageActivity.this,ViewCourseActivity.class);
                       startActivity(intentRateCourse);
                       break;
               }
               return false;
           }
       });
    }

    // this is a private method to inintialize all the views used in this activity


}
