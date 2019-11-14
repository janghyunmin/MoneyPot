package com.quantec.moneypot.activity.Main.Fragment.Tab3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;

import java.util.ArrayList;

public class AdapterEnterWith extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TOP = 0;
    private int ITEM = 1;

    ArrayList<ModelEnterWith> modelEnterWiths;
    Context context;

    public AdapterEnterWith(ArrayList<ModelEnterWith> modelEnterWiths, Context context) {
        this.modelEnterWiths = modelEnterWiths;
        this.context = context;
    }

    private EnterWithAddClick enterWithAddClick;
    public interface EnterWithAddClick{
        public void onClick(int position);
    }

    public void setEnterWithAddClick(EnterWithAddClick enterWithAddClick) {
        this.enterWithAddClick = enterWithAddClick;
    }

    private EnterWithAllClick enterWithAllClick;
    public interface EnterWithAllClick {
        public void onClick(int position);
    }

    public void setEnterWithAllClick(EnterWithAllClick enterWithAllClick) {
        this.enterWithAllClick = enterWithAllClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == TOP){
            return new EnterWithTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab3_enterwithtop, parent, false));
        }else{
            return new EnterWithViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab3_enterwith, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof EnterWithViewHolder){

            ((EnterWithViewHolder)holder).title.setText(modelEnterWiths.get(position).getTitle());
            ((EnterWithViewHolder)holder).code.setText(modelEnterWiths.get(position).getCode());
            ((EnterWithViewHolder)holder).rate.setText(modelEnterWiths.get(position).getRate()+"%");

            ((EnterWithViewHolder)holder).addBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(enterWithAddClick != null){
                        enterWithAddClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof EnterWithTopViewHolder){



//            ((EnterWithTopViewHolder)holder).countNum.setText(modelEnterWiths.size()-1);
//            ((EnterWithTopViewHolder)holder).allBt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(enterWithAllClick != null){
//                        enterWithAllClick.onClick(position);
//                    }
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return modelEnterWiths.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == TOP){
            return TOP;
        }else{
            return ITEM;
        }
    }

    public class EnterWithViewHolder extends RecyclerView.ViewHolder {

        ImageView addBt;
        TextView title, code, rate;

        public EnterWithViewHolder(@NonNull View itemView) {
            super(itemView);

            addBt = itemView.findViewById(R.id.addBt);

            title = itemView.findViewById(R.id.title);
            code = itemView.findViewById(R.id.code);
            rate = itemView.findViewById(R.id.rate);
        }
    }

    public class EnterWithTopViewHolder extends RecyclerView.ViewHolder {

        TextView countNum, allBt;

        public EnterWithTopViewHolder(@NonNull View itemView) {
            super(itemView);

            countNum = itemView.findViewById(R.id.countNum);
            allBt = itemView.findViewById(R.id.allBt);

        }
    }
}
