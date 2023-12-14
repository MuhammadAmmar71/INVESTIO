package com.example.investio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.ViewHolder>{

    private List<TimeSeriesDaily> dataList = new ArrayList<>();

    public void setData(List<TimeSeriesDaily> newData) {
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
        TimeSeriesDaily data = dataList.get(position);

        holder.openTextView.setText("Open: " + data.getOpen());
        holder.highTextView.setText("High: " + data.getHigh());
        holder.lowTextView.setText("Low: " + data.getLow());
        holder.closeTextView.setText("Close: " + data.getClose());
        holder.volumeTextView.setText("Volume: " + data.getVolume());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView openTextView, highTextView, lowTextView, closeTextView, volumeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            openTextView = itemView.findViewById(R.id.openTextView);
            highTextView = itemView.findViewById(R.id.highTextView);
            lowTextView = itemView.findViewById(R.id.lowTextView);
            closeTextView = itemView.findViewById(R.id.closeTextView);
            volumeTextView = itemView.findViewById(R.id.volumeTextView);
        }
    }
}
