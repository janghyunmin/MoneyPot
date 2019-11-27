package com.quantec.moneypot.activity.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.account.ModelHistoryStock;

import java.util.ArrayList;

public class AdapterHistoryStock extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int ITEM = 1;

    ArrayList<ModelHistoryStock> modelHistoryStocks;
    Context context;

    public AdapterHistoryStock(ArrayList<ModelHistoryStock> modelHistoryStocks, Context context) {
        this.modelHistoryStocks = modelHistoryStocks;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == EMPTY){
            return new HistoryStockEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historystock_empty, parent, false));
        }else{
            return new HistoryStockViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historystock_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return modelHistoryStocks.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelHistoryStocks.get(position).isEmpty()){
            return EMPTY;
        }else{
            return ITEM;
        }
    }

    public class HistoryStockViewHolder extends RecyclerView.ViewHolder {
        public HistoryStockViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class HistoryStockEmptyViewHolder extends RecyclerView.ViewHolder {
        public HistoryStockEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
