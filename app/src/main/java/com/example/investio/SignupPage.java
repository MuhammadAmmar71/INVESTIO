package com.example.investio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupPage extends AppCompatActivity {
    EditText edtfname, edtemail, edtpswd, edtconfirmpswd;
    Button btnreg;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        edtfname = findViewById(R.id.edtfname);
        edtpswd = findViewById(R.id.edtpswd);
        edtconfirmpswd = findViewById(R.id.edtconfirmpswd);
        edtemail = findViewById(R.id.edtemail);

        btnreg = findViewById(R.id.btnreg);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LoginPage.class));
            finish();
        }


        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtemail.getText().toString().trim();
                String password = edtpswd.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    edtemail.setError("Email is requires.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {

                    edtpswd.setError("Password is required.");
                    return;
                }

                if (password.length() < 6) {
                    edtpswd.setError("Password must be atleast 6 characters long.");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(SignupPage.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }
                        else {
                            Toast.makeText(SignupPage.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });




    }
}