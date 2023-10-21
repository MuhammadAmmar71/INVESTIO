package com.example.investio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupPage extends AppCompatActivity {

    Button btnSigninSuccessful;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        Intent iLogin = new Intent(SignupPage.this, LoginPage.class);
        btnSigninSuccessful=findViewById(R.id.btnSigninSuccessful);
        btnSigninSuccessful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iLogin);
            }
        });

    }
}