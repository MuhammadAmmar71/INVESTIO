package com.example.investio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginPage extends AppCompatActivity {
    Button btnSignin;
    Button btnLoginSuccessful;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        btnSignin=findViewById(R.id.btnSignin);
        btnLoginSuccessful=findViewById(R.id.btnLoginSuccessful);

        Intent iSignup = new Intent(LoginPage.this,SignupPage.class);
        Intent iHome = new Intent(LoginPage.this,HomeActivity.class);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(iSignup);
            }
        });

        btnLoginSuccessful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(iHome);
            }
        });

    }
}