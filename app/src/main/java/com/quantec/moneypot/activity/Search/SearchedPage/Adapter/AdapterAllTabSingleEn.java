package com.quantec.moneypot.activity.Search.SearchedPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelSingleEn;

import java.util.ArrayList;

public class AdapterAllTabSingleEn extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int TOP = 1;
    private int ITEM = 2;

    ArrayList<ModelSingleEn> modelSingleEns;
    Context context;

    public AdapterAllTabSingleEn(ArrayList<ModelSingleEn> modelSingleEns, Context context) {
        this.modelSingleEns = modelSingleEns;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new AllTabEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_number, parent, false));
        }
        else if(viewType == TOP){
            return new AllTabSingleEnViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_number, parent, false));
        }
        else{
            return new AllTabSingleEnViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_title, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if(modelSingleEns.size()==1){
            return EMPTY;
        }else{
            if(position == 0){
                return TOP;
            }else{
                return ITEM;
            }
        }
    }

    @Override
    public int getItemCount() {
        return modelSingleEns.size();
    }

    public class AllTabSingleEnViewHolder extends RecyclerView.ViewHolder {
        public AllTabSingleEnViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class AllTabEmptyViewHolder extends RecyclerView.ViewHolder {
        public AllTabEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
