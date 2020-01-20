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

public class AdapterSnpTab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TOP = 0;
    private final int ITEM = 1;

    ArrayList<ModelSnpTab> modelSnpTabs;
    Context context;

    public AdapterSnpTab(ArrayList<ModelSnpTab> modelSnpTabs, Context context) {
        this.modelSnpTabs = modelSnpTabs;
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
            return new SnpTabTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singletab_top, parent, false));
        }else{
            return new SnpTabViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singletab_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SnpTabViewHolder){

            ((SnpTabViewHolder)holder).title.setText(modelSnpTabs.get(position).getTitle());
            ((SnpTabViewHolder)holder).num.setText(String.valueOf(position));
            ((SnpTabViewHolder)holder).rate.setText(String.valueOf(modelSnpTabs.get(position).getRate()));

            if(modelSnpTabs.get(position).getRate() < 0){
                ((SnpTabViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
                ((SnpTabViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
            }else{
                ((SnpTabViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.c_f02654));
                ((SnpTabViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_f02654));
            }

            ((SnpTabViewHolder)holder).price.setText(String.valueOf(modelSnpTabs.get(position).getPrice()));

            if(modelSnpTabs.get(position).getFollow() == 0){
                ((SnpTabViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_off_whitegray));
            }else{
                ((SnpTabViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_on_blue));
            }

            ((SnpTabViewHolder)holder).followBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(singleTabFollowClick != null){
                        singleTabFollowClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof SnpTabTopViewHolder){

            ((SnpTabTopViewHolder)holder).title.setText(modelSnpTabs.get(0).getFilter());

            ((SnpTabTopViewHolder)holder).filterBt.setOnClickListener(new View.OnClickListener() {
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
        return modelSnpTabs.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TOP;
        }else{
            return ITEM;
        }
    }

    public class SnpTabViewHolder extends RecyclerView.ViewHolder {

        ImageView image, followBt;
        TextView num, title, rate, per, price;


        public SnpTabViewHolder(@NonNull View itemView) {
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

    public class SnpTabTopViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout filterBt;
        TextView title;

        public SnpTabTopViewHolder(@NonNull View itemView) {
            super(itemView);

            filterBt = itemView.findViewById(R.id.filterBt);
            title = itemView.findViewById(R.id.title);
        }
    }
}