package com.example.investio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {


    RecyclerView recview;
    ArrayList<MainUiParentModelClass> parentModelClassArrayList;
    ArrayList<MainUiChildModelClass> stocks;
    ArrayList<MainUiChildModelClass> crypto;
    ArrayList<MainUiChildModelClass> forex;
    MainUiParentAdapter parentAdapt;

//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;


    TextView btnlogout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btnlogout = findViewById(R.id.btnlogout);

        recview = findViewById(R.id.rvparent);

        parentModelClassArrayList = new ArrayList<>();
        stocks = new ArrayList<>();
        crypto = new ArrayList<>();
        forex = new ArrayList<>();

        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));
        stocks.add(new MainUiChildModelClass(R.drawable.stocks));


        parentModelClassArrayList.add(new MainUiParentModelClass(stocks));

//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//
//
//        parentModelClassArrayList.add(new MainUiParentModelClass(crypto));
//
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//
//
//        parentModelClassArrayList.add(new MainUiParentModelClass(forex));


        parentAdapt = new MainUiParentAdapter(parentModelClassArrayList, this);
        recview.setLayoutManager(new LinearLayoutManager(this));
        recview.setAdapter(parentAdapt);
        parentAdapt.notifyDataSetChanged();


        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });


//       btnlogout.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               SignOut();
//               gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//                   @Override
//                   public void onComplete(@NonNull Task<Void> task) {
//                       finish();
//                       startActivity(new Intent(getApplicationContext(),LoginPage.class));
//                   }
//               });
//
//           }
//       });
//
//
//
//    }
//
//    private void SignOut() {
//
//    }



    }

    private void logout(View view) {

        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("flag", false);
        editor.apply();


        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginPage.class));
        finish();
    }
    }




