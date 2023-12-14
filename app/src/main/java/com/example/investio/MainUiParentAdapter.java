package com.example.investio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class  MainUiParentAdapter extends RecyclerView.Adapter<MainUiParentAdapter.ViewHolder> {

    List<MainUiParentModelClass> parentModelClassList;
    Context context;

    public MainUiParentAdapter(List<MainUiParentModelClass> parentModelClassList, Context context) {
        this.parentModelClassList = parentModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainUiParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mainuiparentrecview,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainUiParentAdapter.ViewHolder holder, int position) {
        List<MainUiChildModelClass> childModelClassList = parentModelClassList.get(position).childModelClassList;
        MainUiChildAdapter childAdapter= new MainUiChildAdapter((StocksOnClickInterface)context,parentModelClassList.get(position).childModelClassList,context);
        //Since you are already implementing StocksOnClickInterface in HomeActivity, you can pass HomeActivity as the interface implementation (StocksOnClickInterface)context
        holder.childrv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.childrv.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        return parentModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView childrv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            childrv=itemView.findViewById(R.id.rvchild);
        }
    }
}
