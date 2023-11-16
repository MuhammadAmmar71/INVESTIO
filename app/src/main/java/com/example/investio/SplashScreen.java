package com.example.investio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


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
        },2500);


    }
}