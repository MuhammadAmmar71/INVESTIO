package com.example.investio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Random;


public class HomeActivity extends AppCompatActivity implements StocksOnClickInterface{


    RecyclerView recview;
    ArrayList<MainUiParentModelClass> parentModelClassArrayList;
    ArrayList<MainUiChildModelClass> stocks;
    ArrayList<MainUiChildModelClass> crypto;
    ArrayList<MainUiChildModelClass> forex;
    MainUiParentAdapter parentAdapt;

    TextView txtuserid;
//    FloatingActionButton btnfloat;

    CardView btntransachistory;

    CardView walletcard;
    TextView amountwallet;

//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;

    FirebaseAuth currentuserid;

    TextView btnlogout;

    CardView btnUserPortfolio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        walletcard=findViewById(R.id.walletcardview);

        btntransachistory=findViewById(R.id.btntransachistory);

        DatabaseClass db= new DatabaseClass(this);

        btnlogout = findViewById(R.id.btnlogout);

       amountwallet=findViewById(R.id.txtamountwallet);

        recview = findViewById(R.id.rvparent);

//        txtuserid=findViewById(R.id.txtuserid);
//
//        btnfloat=findViewById(R.id.btnfloat);

        btnUserPortfolio=findViewById(R.id.btnUserPortfolio);






        parentModelClassArrayList = new ArrayList<>();
        stocks = new ArrayList<>();
        crypto = new ArrayList<>();
        forex = new ArrayList<>();

        stocks.add(new MainUiChildModelClass(R.drawable.tech));
        stocks.add(new MainUiChildModelClass(R.drawable.health));
        stocks.add(new MainUiChildModelClass(R.drawable.education));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));
        stocks.add(new MainUiChildModelClass(R.drawable.graph));



        parentModelClassArrayList.add(new MainUiParentModelClass(stocks));



        crypto.add(new MainUiChildModelClass(R.drawable.ethereum));
        crypto.add(new MainUiChildModelClass(R.drawable.bitcoin));
        crypto.add(new MainUiChildModelClass(R.drawable.dodgecoin));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));
//        crypto.add(new MainUiChildModelClass(R.drawable.crypto));



        parentModelClassArrayList.add(new MainUiParentModelClass(crypto));

        forex.add(new MainUiChildModelClass(R.drawable.riyaal));
        forex.add(new MainUiChildModelClass(R.drawable.pound));
        forex.add(new MainUiChildModelClass(R.drawable.euro));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));
//        forex.add(new MainUiChildModelClass(R.drawable.forex));


        parentModelClassArrayList.add(new MainUiParentModelClass(forex));


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



 //fetching user id
//
        if(!db.readuser()){
            fetchuserid(db);

        }

        //   generating walletid
        if(!db.readwallet()){
            generatewalletid(db);
        }

        // Before showing the dialog, get the initial wallet amount and display it
        Double initialWalletAmount = db.readwalletamount();
        amountwallet.setText(String.valueOf(initialWalletAmount));

walletcard.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Dialog dialog =new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.addamountdialogbox);

        EditText edtamount=dialog.findViewById(R.id.edtamountwallet);

        Button   btnSave=dialog.findViewById(R.id.btnaddamount);

        btnSave.setOnClickListener(new View.OnClickListener() {

            String getamount;
            Double newamount;
            @Override
            public void onClick(View view) {
                 getamount=edtamount.getText().toString();
                newamount=Double.parseDouble(getamount);
             String timestamp=db.getCurrentTimestamp();

             Double previousamount = db.readwalletamount();
             Double  totalamount=newamount + previousamount; // HERE WE GET OUR WALLET AMOUNT




                db.updatewalletamount(totalamount);

                amountwallet.setText(totalamount.toString());
                dialog.dismiss();


            }
        });

        dialog.show();

    }
});





btntransachistory.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),TransactionsHistory.class));
    }
});

btnUserPortfolio.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),UserPortfolio.class));
    }
});


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


    public void fetchuserid(DatabaseClass db){
        FirebaseUser currentuserid=FirebaseAuth.getInstance().getCurrentUser();

        String userid;
        userid=currentuserid.getUid();




        //   generating walletid
        if(!db.readwallet()){
            generatewalletid(db);
        }

        String walletid=db.fetchwalletid();

        db.adduserid(userid,walletid);


    }

    public void generatewalletid(DatabaseClass db){
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder stringBuilder=new StringBuilder();
        Random random = new Random();

        int length=9;
        for(int i=0;i<length;i++){

            int index=random.nextInt(str.length());
            char randomchar = str.charAt(index);
            stringBuilder.append(randomchar);

        }

        String walletid =stringBuilder.toString();



        Double amount=0.0;



        db.addWalletid(walletid,amount);
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

                startActivity(intent);
                break;

            case 1:
                intent = new Intent(this, HealthUSAStocks.class);

                startActivity(intent);
                break;


            case 2:
                intent = new Intent(this, EdTechUSAStocks.class);

                startActivity(intent);
                break;


            case 3:
                intent = new Intent(this, RealEstateUSAStocks.class);

                startActivity(intent);
                break;



            case 4:
                intent = new Intent(this, BankUSAStocks.class);

                startActivity(intent);
                break;


            case 5:
                intent = new Intent(this, TopTechWorldwideStocks.class);

                startActivity(intent);
                break;





            case 6:
                intent = new Intent(this, TopDividendYieldStocks.class);

                startActivity(intent);
                break;



            case 7:
                intent = new Intent(this, TopPERatioStocks.class);

                startActivity(intent);
                break;



            case 8:
                intent = new Intent(this, TopPBRatioStocks.class);

                startActivity(intent);
                break;



            case 9:
                intent = new Intent(this, AgricultureStocks.class);

                startActivity(intent);
                break;

        }


    }







}


//                       GOOGLE SIGN IN
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




