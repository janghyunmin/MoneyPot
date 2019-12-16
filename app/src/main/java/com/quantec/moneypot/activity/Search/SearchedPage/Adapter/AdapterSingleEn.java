package com.quantec.moneypot.activity.Search.SearchedPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelSingleEn;

import java.util.ArrayList;

public class AdapterSingleEn extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int TOP = 1;
    private int ITEM = 2;

    ArrayList<ModelSingleEn> modelSingleEns;
    Context context;

    public AdapterSingleEn(ArrayList<ModelSingleEn> modelSingleEns, Context context) {
        this.modelSingleEns = modelSingleEns;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new SingleEnEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_empty, parent, false));
        }
        else if(viewType == TOP){
            return new SingleEnTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singleen_top, parent, false));
        }
        else{
            return new SingleEnViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singleen_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SingleEnViewHolder){

            ((SingleEnViewHolder)holder).title.setText(modelSingleEns.get(position).getTitle());
            ((SingleEnViewHolder)holder).rate.setText(String.format("%.2f", modelSingleEns.get(position).getRate()));
            if(modelSingleEns.get(position).getRate() < 0){
                ((SingleEnViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((SingleEnViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((SingleEnViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((SingleEnViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

            if(modelSingleEns.get(position).getFollow() == 0){
                ((SingleEnViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_off));
            }else{
                ((SingleEnViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_on));
            }

            ((SingleEnViewHolder)holder).followBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(singleEnFollowBt != null){
//                        singleEnFollowBt.onClick(position);
//                    }
                }
            });

        }

        if(holder instanceof SingleEnEmptyViewHolder){

        }

        if(holder instanceof SingleEnTopViewHolder){
            int total = modelSingleEns.size()-1;
            ((SingleEnTopViewHolder)holder).num.setText("단일기업 "+total+"건");
        }

    }

    @Override
    public int getItemCount() {
        return modelSingleEns.size();
    }


    @Override
    public int getItemViewType(int position) {
        if(modelSingleEns.size()==0){
            return EMPTY;
        }
        else{

            if(position == 0){
                return TOP;
            }else{
                return ITEM;
            }
        }
    }

    public class SingleEnViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout itemLayout;
        TextView title, rate, per;
        ImageView followBt;

        public SingleEnViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.itemLayout);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            followBt = itemView.findViewById(R.id.followBt);
        }
    }

    public class SingleEnTopViewHolder extends RecyclerView.ViewHolder {

        TextView num;

        public SingleEnTopViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
        }
    }

    public class SingleEnEmptyViewHolder extends RecyclerView.ViewHolder {
        public SingleEnEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
