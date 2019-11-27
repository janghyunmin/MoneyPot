package com.quantec.moneypot.activity.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.account.ModelIncomeOut;

import java.util.ArrayList;

public class AdapterIncomeOut extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int ITEM = 1;

    ArrayList<ModelIncomeOut> modelIncomeOuts;
    Context context;

    public AdapterIncomeOut(ArrayList<ModelIncomeOut> modelIncomeOuts, Context context) {
        this.modelIncomeOuts = modelIncomeOuts;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == EMPTY){
            return new IncomeOutEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incomeout_empty, parent, false));
        }else{
            return new IncomeOutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incomeout_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof IncomeOutEmptyViewHolder){

        }

        if(holder instanceof IncomeOutViewHolder){

        }

    }


    @Override
    public int getItemViewType(int position) {
        if(modelIncomeOuts.get(position).isEmpty()){
            return EMPTY;
        }else{
            return ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return modelIncomeOuts.size();
    }

    public class IncomeOutViewHolder extends RecyclerView.ViewHolder {
        public IncomeOutViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class IncomeOutEmptyViewHolder extends RecyclerView.ViewHolder {
        public IncomeOutEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
