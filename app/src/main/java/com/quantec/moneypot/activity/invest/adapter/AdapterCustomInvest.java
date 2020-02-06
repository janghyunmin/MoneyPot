package com.quantec.moneypot.activity.invest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.activity.invest.ModelInvestList;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdapterCustomInvest extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<ModelInvestList> modelInvestLists;
    Context context;

    public AdapterCustomInvest(ArrayList<ModelInvestList> modelInvestLists, Context context) {
        this.modelInvestLists = modelInvestLists;
        this.context = context;
    }

    private CustomInvestClick customInvestClick;
    public interface CustomInvestClick{
        public void onClick(int position);
    }

    public void setCustomInvestClick(CustomInvestClick customInvestClick) {
        this.customInvestClick = customInvestClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      return new CustomInvestItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custominvest_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof CustomInvestItemViewHolder){

            ((CustomInvestItemViewHolder)holder).title.setText(modelInvestLists.get(position).getTitle());
            ((CustomInvestItemViewHolder)holder).code.setText(modelInvestLists.get(position).getCode());
            ((CustomInvestItemViewHolder)holder).rate.setText(String.valueOf(modelInvestLists.get(position).getRate()));
            ((CustomInvestItemViewHolder)holder).ratio.setText(modelInvestLists.get(position).getRatio()+"%");

            if(modelInvestLists.get(position).getRate() < 0){
                ((CustomInvestItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
                ((CustomInvestItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
            }else{
                ((CustomInvestItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.c_f02654));
                ((CustomInvestItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_f02654));
            }

        }

    }

    @Override
    public int getItemCount() {
        return modelInvestLists.size();
    }


    public class CustomInvestItemViewHolder extends RecyclerView.ViewHolder {

        TextView title, code, rate, per, ratio;


        public CustomInvestItemViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            code = itemView.findViewById(R.id.code);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            ratio = itemView.findViewById(R.id.ratio);
        }
    }

}
