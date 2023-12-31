package com.example.investio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserPortfolioAdapter extends RecyclerView.Adapter<UserPortfolioAdapter.ViewHolder> {
    private Context context;

    private ArrayList walletID,portfolioID,percentInStock;

    public UserPortfolioAdapter(Context context, ArrayList walletID, ArrayList portfolioID, ArrayList percentInStock) {
        this.context = context;
        this.walletID = walletID;
        this.portfolioID = portfolioID;
        this.percentInStock = percentInStock;
    }


    @NonNull
    @Override
    public UserPortfolioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userportfoliocard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPortfolioAdapter.ViewHolder holder, int position) {


        holder.walletIDtxtview.setText("WalletID: " + String.valueOf(walletID.get(position)));
        holder.portfolioIDtxtview.setText("PortfolioID: " + String.valueOf(portfolioID.get(position)));
        holder.percentInStocktxtview.setText("PercentInStock: " + String.valueOf(percentInStock.get(position)));

    }

    @Override
    public int getItemCount() {
       return portfolioID.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

TextView walletIDtxtview,portfolioIDtxtview,percentInStocktxtview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            walletIDtxtview = itemView.findViewById(R.id.walletID);
            portfolioIDtxtview = itemView.findViewById(R.id.portfolioID);
            percentInStocktxtview = itemView.findViewById(R.id.percentInPortfolio);
        }
    }
}
