package com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.ModelStockList;

import java.util.ArrayList;

public class AdapterStockList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TOP = 0;
    private final int ITEM = 1;

    ArrayList<ModelStockList> modelStockLists;
    Context context;

    public AdapterStockList(ArrayList<ModelStockList> modelStockLists, Context context) {
        this.modelStockLists = modelStockLists;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TOP){
            return new StockListTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stocklist_top, parent, false));
        }else{
            return new StockListItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stocklist_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof StockListItemViewHolder){
            ((StockListItemViewHolder)holder).title.setText(modelStockLists.get(position).getTitle());
            ((StockListItemViewHolder)holder).rate.setText(String.valueOf(modelStockLists.get(position).getRate())+"%");
        }

    }

    @Override
    public int getItemCount() {
        return modelStockLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){

            return TOP;

        }else{
            return ITEM;
        }
    }

    public class StockListTopViewHolder extends RecyclerView.ViewHolder {
        public StockListTopViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class StockListItemViewHolder extends RecyclerView.ViewHolder {

        TextView title, rate;

        public StockListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            title  = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);

        }
    }

}
