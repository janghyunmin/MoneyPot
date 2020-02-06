package com.quantec.moneypot.activity.Main.Fragment.Tab1.Activity;

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
import com.quantec.moneypot.activity.buttondoublecheck.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdapterSumTab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TOP = 0;
    private final int ITEM = 1;

    ArrayList<ModelSumTab> modelSumTabs;
    Context context;

    public AdapterSumTab(ArrayList<ModelSumTab> modelSumTabs, Context context) {
        this.modelSumTabs = modelSumTabs;
        this.context = context;
    }

    private SumTabFilterClcick sumTabFilterClcick;
    public interface SumTabFilterClcick{
        public void onClcick(int position);
    }

    public void setSumTabFilterClcick(SumTabFilterClcick sumTabFilterClcick) {
        this.sumTabFilterClcick = sumTabFilterClcick;
    }

    private SumTabFollowClick sumTabFollowClick;
    public interface SumTabFollowClick{
        public void onClick(int position);
    }

    public void setSumTabFollowClick(SumTabFollowClick sumTabFollowClick) {
        this.sumTabFollowClick = sumTabFollowClick;
    }

    private SumTabItemClick sumTabItemClick;
    public interface SumTabItemClick{
        public void onClick(int position);
    }

    public void setSumTabItemClick(SumTabItemClick sumTabItemClick) {
        this.sumTabItemClick = sumTabItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TOP){
            return new SumTabTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sumtab_top, parent, false));
        }else{
            return new SumTabViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sumtab_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SumTabViewHolder){

            ((SumTabViewHolder)holder).title.setText(modelSumTabs.get(position).getTitle());
            ((SumTabViewHolder)holder).num.setText(String.valueOf(position));
            ((SumTabViewHolder)holder).rate.setText(String.valueOf(modelSumTabs.get(position).getRate()));

            if(modelSumTabs.get(position).getRate() < 0){
                ((SumTabViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
                ((SumTabViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
            }else{
                ((SumTabViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.c_f02654));
                ((SumTabViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_f02654));
            }

            ((SumTabViewHolder)holder).price.setText(String.valueOf(modelSumTabs.get(position).getPrice()));

            if(modelSumTabs.get(position).getFollow() == 0){
                ((SumTabViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_off_whitegray));
            }else{
                ((SumTabViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_on_blue));
            }


            RxView.clicks(((SumTabViewHolder)holder).followBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(sumTabFollowClick != null){
                    sumTabFollowClick.onClick(position);
                }
            });

            RxView.clicks(((SumTabViewHolder)holder).itemLayout).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(sumTabItemClick != null){
                    sumTabItemClick.onClick(position);
                }
            });

        }

        if(holder instanceof SumTabTopViewHolder){

            ((SumTabTopViewHolder)holder).title.setText(modelSumTabs.get(0).getFilter());

            RxView.clicks(((SumTabTopViewHolder)holder).filterBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(sumTabFilterClcick != null){
                    sumTabFilterClcick.onClcick(position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return modelSumTabs.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TOP;
        }else{
            return ITEM;
        }
    }

    public class SumTabViewHolder extends RecyclerView.ViewHolder {

        ImageView image, followBt;
        TextView num, title, rate, per, price;
        ConstraintLayout itemLayout;

        public SumTabViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            followBt = itemView.findViewById(R.id.followBt);
            num = itemView.findViewById(R.id.num);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            price = itemView.findViewById(R.id.price);

            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class SumTabTopViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout filterBt;
        TextView title;

        public SumTabTopViewHolder(@NonNull View itemView) {
            super(itemView);

            filterBt = itemView.findViewById(R.id.filterBt);
            title = itemView.findViewById(R.id.title);
        }
    }
}