package com.quantec.moneypot.activity.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.account.ModelHistoryProgress;

import java.util.ArrayList;

public class AdapterHistoryProgress extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int ITEM = 1;

    ArrayList<ModelHistoryProgress> modelHistoryProgresses;
    Context context;

    public AdapterHistoryProgress(ArrayList<ModelHistoryProgress> modelHistoryProgresses, Context context) {
        this.modelHistoryProgresses = modelHistoryProgresses;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == EMPTY){
            return new HistoryProgressEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historyprogress_empty, parent, false));
        }else{
            return new HistoryProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historyprogress_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof HistoryProgressEmptyViewHolder){

        }

        if(holder instanceof HistoryProgressViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return modelHistoryProgresses.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(modelHistoryProgresses.get(position).isEmpty()){
            return EMPTY;
        }else{
            return ITEM;
        }
    }

    public class HistoryProgressViewHolder extends RecyclerView.ViewHolder {
        public HistoryProgressViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class HistoryProgressEmptyViewHolder extends RecyclerView.ViewHolder {

        public HistoryProgressEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
