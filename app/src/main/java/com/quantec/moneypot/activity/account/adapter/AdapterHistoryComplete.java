package com.quantec.moneypot.activity.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.account.ModelHistoryComplete;

import java.util.ArrayList;

public class AdapterHistoryComplete extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int ITEM = 1;

    ArrayList<ModelHistoryComplete> modelHistoryCompletes;
    Context context;

    public AdapterHistoryComplete(ArrayList<ModelHistoryComplete> modelHistoryCompletes, Context context) {
        this.modelHistoryCompletes = modelHistoryCompletes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new HistoryCompleteEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historycomplete_empty, parent, false));
        }else{
            return new HistoryCompleteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historycomplete_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HistoryCompleteViewHolder){

        }

        if(holder instanceof HistoryCompleteEmptyViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return modelHistoryCompletes.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(modelHistoryCompletes.get(position).isEmpty()){
            return EMPTY;
        }else{
            return ITEM;
        }
    }

    public class HistoryCompleteViewHolder extends RecyclerView.ViewHolder {
        public HistoryCompleteViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class HistoryCompleteEmptyViewHolder extends RecyclerView.ViewHolder {
        public HistoryCompleteEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
