package com.quantec.moneypot.activity.account.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.account.ModelMyAccount;

import java.util.ArrayList;

public class AdapterMyAccount extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int TOP = 1;
    private int BOTTOM = 2;
    private int ITEM = 3;

    ArrayList<ModelMyAccount> modelMyAccounts;
    Context context;

    public AdapterMyAccount(ArrayList<ModelMyAccount> modelMyAccounts, Context context) {
        this.modelMyAccounts = modelMyAccounts;
        this.context = context;
    }

    private BottomClick bottomClick;
    public interface BottomClick{
        public void onClick(int position);
    }

    public void setBottomClick(BottomClick bottomClick) {
        this.bottomClick = bottomClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == TOP){
            return new TopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myaccount_top, parent, false));
        }else if(viewType == EMPTY){
            return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myaccount_empty, parent, false));
        }else if(viewType == BOTTOM){
            return new BottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myaccount_bottom, parent, false));
        }else{
            return new MyAccountViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myaccount_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TopViewHolder){

        }

        if(holder instanceof MyAccountViewHolder){

        }

        if(holder instanceof BottomViewHolder){
            ((BottomViewHolder)holder).investHistoryBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bottomClick != null){
                        bottomClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof EmptyViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return modelMyAccounts.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(modelMyAccounts.get(position).isEmpty()){
            return EMPTY;
        }else{

            if(position == 0){
                return TOP;
            }else{
                if(modelMyAccounts.get(position).isBottom()){
                    return BOTTOM;
                }else{
                    return ITEM;
                }
            }
        }
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {
        public TopViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MyAccountViewHolder extends RecyclerView.ViewHolder {
        public MyAccountViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout investHistoryBt;

        public BottomViewHolder(@NonNull View itemView) {
            super(itemView);

            investHistoryBt = itemView.findViewById(R.id.investHistoryBt);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
