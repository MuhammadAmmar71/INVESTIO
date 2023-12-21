package com.example.investio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.ViewHolder> {

    private List<CompanyData> dataList = new ArrayList<>();

    public void setData(List<CompanyData> newData) {
        dataList.clear();
        dataList.addAll(newData);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apicardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompanyData data = dataList.get(position);

        // Set company name and symbol from the database
        holder.companyNameTextView.setText("Company Name: " + data.getStockName());
        holder.companySymbolTextView.setText("Symbol: " + data.getStockSymbol());
        holder.closeTextView.setText("Close: " + data.getClose());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView companyNameTextView;
        TextView companySymbolTextView;
        TextView closeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            companySymbolTextView = itemView.findViewById(R.id.companySymbolTextView);
            closeTextView = itemView.findViewById(R.id.closeTextView);
        }
    }
}
