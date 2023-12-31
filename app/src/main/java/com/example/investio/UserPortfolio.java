package com.example.investio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class UserPortfolio extends AppCompatActivity {
    RecyclerView recyclerView;
    UserPortfolioAdapter adapter;

    ArrayList<String> walletID,portfolioID,percentInStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_portfolio);

        DatabaseClass db= new DatabaseClass(getApplicationContext());


        walletID=new ArrayList<>();
        portfolioID=new ArrayList<>();
        percentInStock=new ArrayList<>();

        recyclerView = findViewById(R.id.recviewuserportfolio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata(db);

        adapter = new UserPortfolioAdapter(this,walletID,portfolioID,percentInStock);
        recyclerView.setAdapter(adapter);
    }

    public void displaydata(DatabaseClass db){

        Cursor cursor = db.readUserPortfolio();
        if(cursor.getCount()==0){
            return;
        }
        else{
            while(cursor.moveToNext()){
                walletID.add(cursor.getString(0));
                portfolioID.add(cursor.getString(1));
                percentInStock.add(cursor.getString(2));
            }
        }

    }
}