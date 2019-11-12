package com.quantec.moneypot.activity.PotDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.R;

public class AdapterLikeEnter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelLikeEnter> modelLikeEnters;
    Context context;

    public AdapterLikeEnter(ArrayList<ModelLikeEnter> modelLikeEnters, Context context) {
        this.modelLikeEnters = modelLikeEnters;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LikeEnterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_likeenter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof LikeEnterViewHolder){
            ((LikeEnterViewHolder)holder).title.setText(modelLikeEnters.get(position).getTitle());
            ((LikeEnterViewHolder)holder).rate.setText(String.valueOf(modelLikeEnters.get(position).getRate()));
        }

    }

    @Override
    public int getItemCount() {
        return modelLikeEnters.size();
    }

    public class LikeEnterViewHolder extends RecyclerView.ViewHolder {

        TextView title, rate;

        public LikeEnterViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
        }
    }

}
