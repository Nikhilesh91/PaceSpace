package com.example.nikhilesh.androidproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

// This is a splash screen activity Generally splash screen are included in any background work is
// to be done before starting with the app. Currently no such work is been done, but we have just added it
// for future if we want to put in any background work.

public class SplashActivity extends AppCompatActivity {

    // variable declaration
    ImageView mSpashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSpashImage = findViewById(R.id.imgSplashImage);
        Animation myAnimation = AnimationUtils.loadAnimation(this,R.anim.my_animation);
        mSpashImage.startAnimation(myAnimation);
        final Intent i = new Intent(this,MainActivity.class);

        // Create a thread and put it in sleep for 5 seconds.
        // Then we are directed to MainActivity class
        // The logic for transition in included in the resource file in the anim folder
        // with the name my_animation.xml


        Thread thread = new Thread(){

            public void run(){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        thread.start();
    }
}
