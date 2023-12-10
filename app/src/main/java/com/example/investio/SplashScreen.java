package com.example.investio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView logoname;
    ImageView logo;
    View topview1,topview2,topview3;
    View bottomview1,bottomview2,bottomview3;
    private  int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView= getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_splash_screen);


        logo= findViewById(R.id.logo);
        logoname=findViewById(R.id.logoname);

        topview1=findViewById(R.id.topview1);
        topview2=findViewById(R.id.topview2);
        topview3=findViewById(R.id.topview3);

        bottomview1=findViewById(R.id.bottomview1);
        bottomview2=findViewById(R.id.bottomview2);
        bottomview3=findViewById(R.id.bottomview3);

        Animation logoanimation= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.zoomanimation);
        Animation textanimation= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.zoomanimation);



        Animation topview1anim= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.top_views_anim);
        Animation topview2anim= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.top_views_anim);
        Animation topview3anim= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.top_views_anim);


        Animation bottomview1anim= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.bottom_views_anim);
        Animation bottomview2anim= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.bottom_views_anim);
        Animation bottomview3anim= AnimationUtils.loadAnimation(SplashScreen.this,R.anim.bottom_views_anim);


        topview1.startAnimation(topview1anim);
        bottomview1.startAnimation(bottomview1anim);


        topview1anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                topview2.setVisibility(View.VISIBLE);
                bottomview2.setVisibility(View.VISIBLE);


                topview2.startAnimation(topview2anim);
                bottomview2.startAnimation(bottomview2anim);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        topview2anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                topview3.setVisibility(View.VISIBLE);
                bottomview3.setVisibility(View.VISIBLE);


                topview3.startAnimation(topview3anim);
                bottomview3.startAnimation(bottomview3anim);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        topview3anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                logo.setVisibility(View.VISIBLE);


                logo.startAnimation(logoanimation);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        logoanimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                logoname.setVisibility(View.VISIBLE);
//            }
//        }, 50); // Adjust the delay as needed
//


                logoname.setVisibility(View.VISIBLE);

                final String animatetxt =logoname.getText().toString();
                count=0;
                new CountDownTimer(animatetxt.length()*100,100){

                    @Override
                    public void onTick(long l) {

                        logoname.setText(animatetxt.substring(0,count+1));
                        count++;
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });








        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                SharedPreferences pref=getSharedPreferences("login",MODE_PRIVATE);
                Boolean check = pref.getBoolean("flag",false);

                Intent iHome;
                if(check){//for true (if user is already logged in
                    iHome = new Intent(SplashScreen.this, HomeActivity.class);
                }
                else{//for false (if either user is logged in for the first time or logged out
                    iHome = new Intent(SplashScreen.this, LoginPage.class);
                }


                startActivity(iHome);
                finish();
            }
        },3500);


    }
}