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
import com.quantec.moneypot.activity.Search.SearchedPage.ModelSumEn;

import java.util.ArrayList;

public class AdapterSumEn extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int TOP = 1;
    private int ITEM = 2;

    ArrayList<ModelSumEn> modelSumEn;
    Context context;

    public AdapterSumEn(ArrayList<ModelSumEn> modelSumEn, Context context) {
        this.modelSumEn = modelSumEn;
        this.context = context;
    }

    private SumEnFollowClick sumEnFollowClick;
    public interface SumEnFollowClick{
        public void onClick(int position);
    }

    public void setSumEnFollowClick(SumEnFollowClick sumEnFollowClick) {
        this.sumEnFollowClick = sumEnFollowClick;
    }

    private SumEnItemClick sumEnItemClick;
    public interface SumEnItemClick{
        public void onClick(int position);
    }

    public void setSumEnItemClick(SumEnItemClick sumEnItemClick) {
        this.sumEnItemClick = sumEnItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new SumEnEmptyViewHoller(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_empty, parent, false));
        }
        else if(viewType == TOP){
            return new SumEnTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singleen_top, parent, false));
        }
        else{
            return new SumEnViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singleen_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SumEnViewHolder){

            ((SumEnViewHolder)holder).title.setText(modelSumEn.get(position).getTitle());
            ((SumEnViewHolder)holder).rate.setText(String.format("%.2f", modelSumEn.get(position).getRate()));
            if(modelSumEn.get(position).getRate() < 0){
                ((SumEnViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((SumEnViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((SumEnViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((SumEnViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

            if(modelSumEn.get(position).getFollow() == 0){
                ((SumEnViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_off));
            }else{
                ((SumEnViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_on));
            }

            ((SumEnViewHolder)holder).followBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sumEnFollowClick != null){
                        sumEnFollowClick.onClick(position);
                    }
                }
            });

            ((SumEnViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sumEnItemClick != null){
                        sumEnItemClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof SumEnEmptyViewHoller){

        }

        if(holder instanceof SumEnTopViewHolder){
            int total = modelSumEn.size()-1;
            ((SumEnTopViewHolder)holder).num.setText("단일기업 "+total+"건");
        }
    }

    @Override
    public int getItemCount() {
        return modelSumEn.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelSumEn.size()==0){
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

    public class SumEnViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout itemLayout;
        TextView title, rate, per;
        ImageView followBt;

        public SumEnViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.itemLayout);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            followBt = itemView.findViewById(R.id.followBt);
        }
    }

    public class SumEnTopViewHolder extends RecyclerView.ViewHolder {

        TextView num;

        public SumEnTopViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
        }
    }

    public class SumEnEmptyViewHoller extends RecyclerView.ViewHolder {
        public SumEnEmptyViewHoller(@NonNull View itemView) {
            super(itemView);
        }
    }

}
