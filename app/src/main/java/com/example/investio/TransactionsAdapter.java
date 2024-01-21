package com.example.investio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {


    private Context context;

    private ArrayList transactionID,walletID,amount,transactionTime;

    public TransactionsAdapter(Context context, ArrayList transactionID, ArrayList walletID, ArrayList amount, ArrayList transactionTime) {
        this.context = context;
        this.transactionID = transactionID;
        this.walletID = walletID;
        this.amount = amount;
        this.transactionTime = transactionTime;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactionscard, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.transactionIdTextView.setText("PortfolioID: " + String.valueOf(transactionID.get(position)));
        holder.walletIdTextView.setText("Transaction Time: " + String.valueOf(walletID.get(position)));
        holder.amountTextView.setText("WalletID: " + String.valueOf(amount.get(position)));
        holder.timeTextView.setText("Amount: " + String.valueOf(transactionTime.get(position)));
    }




    @Override
    public int getItemCount() {
        return transactionID.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView transactionIdTextView;
        TextView walletIdTextView;
        TextView amountTextView;
        TextView timeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionIdTextView = itemView.findViewById(R.id.transacid);
            walletIdTextView = itemView.findViewById(R.id.transacwalletid);
            amountTextView = itemView.findViewById(R.id.transacamount);
            timeTextView = itemView.findViewById(R.id.transactime);
        }
    }
}



