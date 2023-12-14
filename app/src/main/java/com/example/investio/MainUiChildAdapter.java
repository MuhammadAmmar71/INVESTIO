package com.example.investio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainUiChildAdapter extends  RecyclerView.Adapter<MainUiChildAdapter.ViewHolder> {

    StocksOnClickInterface stocksOnClickInterface;
    List<MainUiChildModelClass> childModelClassList;
    Context context;

    public MainUiChildAdapter(StocksOnClickInterface stocksOnClickInterface, List<MainUiChildModelClass> childModelClassList, Context context) {
        this.stocksOnClickInterface = stocksOnClickInterface;
        this.childModelClassList = childModelClassList;
        this.context = context;
    }


    @NonNull
    @Override
    public MainUiChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mainuichildrecview,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainUiChildAdapter.ViewHolder holder, int position) {



        holder.childimage.setImageResource(childModelClassList.get(position).image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stocksOnClickInterface != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        stocksOnClickInterface.onItemClick(position);
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return childModelClassList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView childimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childimage=itemView.findViewById(R.id.img);




        }
    }
}

