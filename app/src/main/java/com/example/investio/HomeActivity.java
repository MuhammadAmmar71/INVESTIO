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
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements StocksOnClickInterface{


    RecyclerView recview;
    ArrayList<MainUiParentModelClass> parentModelClassArrayList;
    ArrayList<MainUiChildModelClass> stocks;
    ArrayList<MainUiChildModelClass> crypto;
    ArrayList<MainUiChildModelClass> forex;
    MainUiParentAdapter parentAdapt;

    TextView txtuserid;

//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;

    FirebaseAuth currentuserid;

    TextView btnlogout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btnlogout = findViewById(R.id.btnlogout);

        recview = findViewById(R.id.rvparent);

        txtuserid=findViewById(R.id.txtuserid);




        parentModelClassArrayList = new ArrayList<>();
        stocks = new ArrayList<>();
        crypto = new ArrayList<>();
        forex = new ArrayList<>();

        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));



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


       //fetching userid from firebase
        txtuserid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentuserid=FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(getApplicationContext(), "" + currentuserid.getUid(), Toast.LENGTH_SHORT).show();

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

    @Override
    public void onItemClick(int position) {

        // Determine which item was clicked
        MainUiChildModelClass clickedItem = stocks.get(position);

        // Start a unique activity based on the item clicked
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(this, TechUSAStocks.class);
                // You can pass any data you need to the activity using intent.putExtra()
                // For example: intent.putExtra("stock_symbol", "AAPL");
                startActivity(intent);
                break;

            case 1:
                intent = new Intent(this, HealthUSAStocks.class);
                // You can pass any data you need to the activity using intent.putExtra()
                // For example: intent.putExtra("stock_symbol", "AAPL");
                startActivity(intent);
                break;


            case 2:
                intent = new Intent(this, EdTechUSAStocks.class);
                // You can pass any data you need to the activity using intent.putExtra()
                // For example: intent.putExtra("stock_symbol", "AAPL");
                startActivity(intent);
                break;


            case 3:
                intent = new Intent(this, RealEstateUSAStocks.class);
                // You can pass any data you need to the activity using intent.putExtra()
                // For example: intent.putExtra("stock_symbol", "AAPL");
                startActivity(intent);
                break;



            case 4:
                intent = new Intent(this, BankUSAStocks.class);
                // You can pass any data you need to the activity using intent.putExtra()
                // For example: intent.putExtra("stock_symbol", "AAPL");
                startActivity(intent);
                break;


            case 5:
                intent = new Intent(this, TopTechWorldwideStocks.class);
                // You can pass any data you need to the activity using intent.putExtra()
                // For example: intent.putExtra("stock_symbol", "AAPL");
                startActivity(intent);
                break;





            case 6:
                intent = new Intent(this, TopDividendYieldStocks.class);
                // You can pass any data you need to the activity using intent.putExtra()
                // For example: intent.putExtra("stock_symbol", "AAPL");
                startActivity(intent);
                break;



            case 7:
                intent = new Intent(this, TopPERatioStocks.class);
                // You can pass any data you need to the activity using intent.putExtra()
                // For example: intent.putExtra("stock_symbol", "AAPL");
                startActivity(intent);
                break;



            case 8:
                intent = new Intent(this, TopPBRatioStocks.class);
                // You can pass any data you need to the activity using intent.putExtra()
                // For example: intent.putExtra("stock_symbol", "AAPL");
                startActivity(intent);
                break;



            case 9:
                intent = new Intent(this, AgricultureStocks.class);
                // You can pass any data you need to the activity using intent.putExtra()
                // For example: intent.putExtra("stock_symbol", "AAPL");
                startActivity(intent);
                break;

        }


    }
}





