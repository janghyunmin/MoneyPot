package com.quantec.moneypot.activity.invest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.invest.ModelTrDetail;

import java.util.ArrayList;

public class AdapterTrDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelTrDetail> modelTrDetails;
    Context context;

    public AdapterTrDetail(ArrayList<ModelTrDetail> modelTrDetails, Context context) {
        this.modelTrDetails = modelTrDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trdetail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof TrDetailViewHolder){

            ((TrDetailViewHolder)holder).num.setText(modelTrDetails.get(position).getNum());
            ((TrDetailViewHolder)holder).title.setText(modelTrDetails.get(position).getTitle());
            ((TrDetailViewHolder)holder).code.setText(modelTrDetails.get(position).getCode());
            ((TrDetailViewHolder)holder).ratio.setText(modelTrDetails.get(position).getRatio()+"%");
        }
    }

    @Override
    public int getItemCount() {
        return modelTrDetails.size();
    }

    public class TrDetailViewHolder extends RecyclerView.ViewHolder {

        TextView num, title, code, ratio;
        ConstraintLayout itemLayout;

        public TrDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
            title = itemView.findViewById(R.id.title);
            code = itemView.findViewById(R.id.code);
            ratio = itemView.findViewById(R.id.ratio);

            itemLayout = itemView.findViewById(R.id.itemLayout);

        }
    }

}
