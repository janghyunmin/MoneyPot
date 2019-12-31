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
import com.quantec.moneypot.activity.Search.SearchedPage.ModelPreSingleEn;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelSingleEn;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdapterAllTabSingleEn extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int TOP = 1;
    private int ITEM = 2;
    private int BOTTOM = 3;

    ArrayList<ModelPreSingleEn> modelPreSingleEns;
    Context context;

    public AdapterAllTabSingleEn(ArrayList<ModelPreSingleEn> modelPreSingleEns, Context context) {
        this.modelPreSingleEns = modelPreSingleEns;
        this.context = context;
    }

    private SingleEnFollowBt singleEnFollowBt;
    public interface SingleEnFollowBt{
        public void onClick(int position);
    }

    public void setSingleEnFollowBt(SingleEnFollowBt singleEnFollowBt) {
        this.singleEnFollowBt = singleEnFollowBt;
    }

    private SingleEnDetailBt singleEnDetailBt;
    public interface SingleEnDetailBt {
        public void onClick(int position);
    }

    public void setSingleEnDetailBt(SingleEnDetailBt singleEnDetailBt) {
        this.singleEnDetailBt = singleEnDetailBt;
    }

    private SingleItemClick singleItemClick;
    public interface SingleItemClick{
        public void onClick(int position);
    }

    public void setSingleItemClick(SingleItemClick singleItemClick) {
        this.singleItemClick = singleItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new AllTabEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_number, parent, false));
        }
        else if(viewType == TOP){
            return new AllTabSingleEnTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_singletop, parent, false));
        }
        else if(viewType == BOTTOM){
            return new AllTabSingleEnBottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_singleenbottom, parent, false));
        }
        else{
            return new AllTabSingleEnViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_singleenitem, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AllTabSingleEnViewHolder){

            ((AllTabSingleEnViewHolder)holder).title.setText(modelPreSingleEns.get(position).getTitle());
            ((AllTabSingleEnViewHolder)holder).rate.setText(String.format("%.2f", modelPreSingleEns.get(position).getRate()));
            if(modelPreSingleEns.get(position).getRate() < 0){
                ((AllTabSingleEnViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((AllTabSingleEnViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((AllTabSingleEnViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((AllTabSingleEnViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

            if(modelPreSingleEns.get(position).getFollow() == 0){
                ((AllTabSingleEnViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_off));
            }else{
                ((AllTabSingleEnViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_on));
            }

            RxView.clicks(((AllTabSingleEnViewHolder)holder).followBt).throttleFirst(600, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnFollowBt != null){
                    singleEnFollowBt.onClick(position);
                }
            });

            ((AllTabSingleEnViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(singleItemClick != null){
                        singleItemClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof AllTabEmptyViewHolder){

        }

        if(holder instanceof AllTabSingleEnTopViewHolder){
            ((AllTabSingleEnTopViewHolder)holder).num.setText("단일기업 "+modelPreSingleEns.get(position).getTotal()+"건");
        }

        if(holder instanceof AllTabSingleEnBottomViewHolder){

            ((AllTabSingleEnBottomViewHolder)holder).detailBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(singleEnDetailBt != null){
                        singleEnDetailBt.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(modelPreSingleEns.size()==0){
            return EMPTY;
        }
        else{
            if(position == 0){
                return TOP;
            }else{

                if(modelPreSingleEns.get(position).getTotal() > 5 && modelPreSingleEns.size()-1 == position){
                    return BOTTOM;
                }else{
                    return ITEM;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return modelPreSingleEns.size();
    }

    public class AllTabSingleEnViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout itemLayout;
        TextView title, rate, per;
        ImageView followBt;

        public AllTabSingleEnViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.itemLayout);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            followBt = itemView.findViewById(R.id.followBt);

        }
    }

    public class AllTabEmptyViewHolder extends RecyclerView.ViewHolder {
        public AllTabEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public class AllTabSingleEnTopViewHolder extends RecyclerView.ViewHolder {

        TextView num;

        public AllTabSingleEnTopViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);

        }
    }
    public class AllTabSingleEnBottomViewHolder extends RecyclerView.ViewHolder {

        TextView detailBt;

        public AllTabSingleEnBottomViewHolder(@NonNull View itemView) {
            super(itemView);

            detailBt = itemView.findViewById(R.id.detailBt);
        }
    }
}
