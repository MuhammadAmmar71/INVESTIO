package com.example.investio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.investio.HomeActivity;
import com.example.investio.SignupPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    FirebaseAuth fAuth;
    EditText edtlemail, edtlpswd;
    Button btnlogin;
    TextView btnnotreg;
    LinearLayout btngoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        edtlemail = findViewById(R.id.edtlemail);
        edtlpswd = findViewById(R.id.edtlpswd);
        btnlogin = findViewById(R.id.btnlogin);
        btnnotreg = findViewById(R.id.btnnotreg);

        btngoogle = findViewById(R.id.btngoogle);

        fAuth = FirebaseAuth.getInstance();


        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putBoolean("flag", true);
                editor.apply();


                String email = edtlemail.getText().toString().trim();
                String password = edtlpswd.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    edtlemail.setError("Email is requires.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {

                    edtlpswd.setError("Password is required.");
                    return;
                }

                if (password.length() < 6) {
                    edtlpswd.setError("Password must be atleast 6 characters long.");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginPage.this, "Logged in Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Toast.makeText(LoginPage.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnnotreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignupPage.class));
            }
        });


        btngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, GoogleSignin.class));

            }
        });

    }
}