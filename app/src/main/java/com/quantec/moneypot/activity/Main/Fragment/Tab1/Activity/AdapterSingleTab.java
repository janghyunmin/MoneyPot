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

import java.util.ArrayList;

public class AdapterSingleTab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TOP = 0;
    private final int ITEM = 1;

    ArrayList<ModelSingleTab> modelSingleTabs;
    Context context;

    public AdapterSingleTab(ArrayList<ModelSingleTab> modelSingleTabs, Context context) {
        this.modelSingleTabs = modelSingleTabs;
        this.context = context;
    }

    private SingleTabFilterClcick singleTabFilterClcick;
    public interface SingleTabFilterClcick{
        public void onClcick(int position);
    }

    public void setSingleTabFilterClcick(SingleTabFilterClcick singleTabFilterClcick) {
        this.singleTabFilterClcick = singleTabFilterClcick;
    }

    private SingleTabFollowClick singleTabFollowClick;
    public interface SingleTabFollowClick{
        public void onClick(int position);
    }

    public void setSingleTabFollowClick(SingleTabFollowClick singleTabFollowClick) {
        this.singleTabFollowClick = singleTabFollowClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TOP){
            return new SingleTabTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singletab_top, parent, false));
        }else{
            return new SingleTabViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singletab_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof SingleTabViewHolder){

                ((SingleTabViewHolder)holder).title.setText(modelSingleTabs.get(position).getTitle());
                ((SingleTabViewHolder)holder).num.setText(String.valueOf(position));
                ((SingleTabViewHolder)holder).rate.setText(String.valueOf(modelSingleTabs.get(position).getRate()));

                if(modelSingleTabs.get(position).getRate() < 0){
                    ((SingleTabViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
                    ((SingleTabViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
                }else{
                    ((SingleTabViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.c_f02654));
                    ((SingleTabViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_f02654));
                }

                ((SingleTabViewHolder)holder).price.setText(String.valueOf(modelSingleTabs.get(position).getPrice()));

                if(modelSingleTabs.get(position).getFollow() == 0){
                    ((SingleTabViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_off_whitegray));
                }else{
                    ((SingleTabViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_on_blue));
                }

                ((SingleTabViewHolder)holder).followBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(singleTabFollowClick != null){
                            singleTabFollowClick.onClick(position);
                        }
                    }
                });

            }

            if(holder instanceof SingleTabTopViewHolder){

                ((SingleTabTopViewHolder)holder).title.setText(modelSingleTabs.get(0).getFilter());

                ((SingleTabTopViewHolder)holder).filterBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(singleTabFilterClcick != null){
                            singleTabFilterClcick.onClcick(position);
                        }
                    }
                });

            }
    }

    @Override
    public int getItemCount() {
        return modelSingleTabs.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TOP;
        }else{
            return ITEM;
        }
    }

    public class SingleTabViewHolder extends RecyclerView.ViewHolder {

        ImageView image, followBt;
        TextView num, title, rate, per, price;


        public SingleTabViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            followBt = itemView.findViewById(R.id.followBt);
            num = itemView.findViewById(R.id.num);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            price = itemView.findViewById(R.id.price);
        }
    }

    public class SingleTabTopViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout filterBt;
        TextView title;

        public SingleTabTopViewHolder(@NonNull View itemView) {
            super(itemView);

            filterBt = itemView.findViewById(R.id.filterBt);
            title = itemView.findViewById(R.id.title);
        }
    }
}
