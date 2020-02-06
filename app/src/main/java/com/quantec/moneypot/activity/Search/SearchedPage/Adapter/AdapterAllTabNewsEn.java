package com.quantec.moneypot.activity.Search.SearchedPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelPreNewsEn;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdapterAllTabNewsEn extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTY = 0;
    private final int TOP = 1;
    private final int ITEM = 2;
    private final int BOTTOM = 3;

    ArrayList<ModelPreNewsEn> modelPreNewsEns;
    Context context;

    public AdapterAllTabNewsEn(ArrayList<ModelPreNewsEn> modelPreNewsEns, Context context) {
        this.modelPreNewsEns = modelPreNewsEns;
        this.context = context;
    }

    private AllTabNewsEnClick allTabNewsEnClick;
    public interface AllTabNewsEnClick{
        public void onClick(int position);
    }

    public void setAllTabNewsEnClick(AllTabNewsEnClick allTabNewsEnClick) {
        this.allTabNewsEnClick = allTabNewsEnClick;
    }

    private AllTabNewsEnBottomClick allTabNewsEnBottomClick;
    public interface AllTabNewsEnBottomClick{
        public void onClick(int position);
    }

    public void setAllTabNewsEnBottomClick(AllTabNewsEnBottomClick allTabNewsEnBottomClick) {
        this.allTabNewsEnBottomClick = allTabNewsEnBottomClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new AllTabNewsEnEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_number, parent, false));
        }
        else if(viewType == TOP){
            return new AllTabNewsEnTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_newsentop, parent, false));
        }
        else if(viewType == BOTTOM){
            return new AllTabNewsEnBottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_singleenbottom, parent, false));
        }
        else{
            return new AllTabNewsEnItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_newsenitem, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AllTabNewsEnItemViewHolder){
            ((AllTabNewsEnItemViewHolder)holder).title.setText(modelPreNewsEns.get(position).getTitle());
            ((AllTabNewsEnItemViewHolder)holder).date.setText(modelPreNewsEns.get(position).getDate());

            RxView.clicks(((AllTabNewsEnItemViewHolder)holder).itemLayout).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(allTabNewsEnClick != null){
                    allTabNewsEnClick.onClick(position);
                }
            });
        }


        if(holder instanceof AllTabNewsEnTopViewHolder){
            ((AllTabNewsEnTopViewHolder)holder).num.setText("기사제목 "+modelPreNewsEns.get(position).getTotal()+"건");
        }

        if(holder instanceof AllTabNewsEnBottomViewHolder){

            ((AllTabNewsEnBottomViewHolder)holder).detailBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(allTabNewsEnBottomClick != null){
                        allTabNewsEnBottomClick.onClick(position);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return modelPreNewsEns.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelPreNewsEns.get(0).isEmpty()){
            return EMPTY;
        }
        else{
            if(position == 0){
                return TOP;
            }else{

                if(modelPreNewsEns.get(position).getTotal() > 5 && modelPreNewsEns.size()-1 == position){
                    return BOTTOM;
                }else{
                    return ITEM;
                }
            }
        }
    }


    public class AllTabNewsEnItemViewHolder extends RecyclerView.ViewHolder {

        TextView title, date;
        ConstraintLayout itemLayout;

        public AllTabNewsEnItemViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);

            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class AllTabNewsEnEmptyViewHolder extends RecyclerView.ViewHolder {
        public AllTabNewsEnEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class AllTabNewsEnTopViewHolder extends RecyclerView.ViewHolder {

        TextView num;

        public AllTabNewsEnTopViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
        }
    }

    public class AllTabNewsEnBottomViewHolder extends RecyclerView.ViewHolder {

        TextView detailBt;

        public AllTabNewsEnBottomViewHolder(@NonNull View itemView) {
            super(itemView);

            detailBt = itemView.findViewById(R.id.detailBt);
        }
    }
}
