package com.example.investio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class TransactionsHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    TransactionsAdapter adapter;

    ArrayList<String> transacID,walletID,amount,transacTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_history);
        DatabaseClass db= new DatabaseClass(getApplicationContext());

        transacID=new ArrayList<>();
        walletID=new ArrayList<>();
        amount=new ArrayList<>();
        transacTime=new ArrayList<>();

        recyclerView = findViewById(R.id.recviewtranssactions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata(db);

        adapter = new TransactionsAdapter(this,transacID,walletID,amount,transacTime);
        recyclerView.setAdapter(adapter);
    }

    public void displaydata(DatabaseClass db){

        Cursor cursor = db.readTransactions();
        if(cursor.getCount()==0){
           return;
        }
        else{
            while(cursor.moveToNext()){
                transacID.add(cursor.getString(0));
                walletID.add(cursor.getString(3));
                amount.add(cursor.getString(1));
                transacTime.add(cursor.getString(2));
            }
        }
    }

}




