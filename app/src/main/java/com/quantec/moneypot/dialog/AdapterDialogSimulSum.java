package com.quantec.moneypot.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.simulationsearch.ModelSimulSum;

import java.util.ArrayList;

public class AdapterDialogSimulSum extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TOP = 0;
    private int ITEM = 1;

    ArrayList<ModelSimulList> modelSimulLists;
    Context context;

    public AdapterDialogSimulSum(ArrayList<ModelSimulList> modelSimulLists, Context context) {
        this.modelSimulLists = modelSimulLists;
        this.context = context;
    }

    private DialogSimulItemClick dialogSimulItemClick;
    public interface DialogSimulItemClick {
        public void onClick(int position);
    }

    public void setDialogSimulItemClick(DialogSimulItemClick dialogSimulItemClick) {
        this.dialogSimulItemClick = dialogSimulItemClick;
    }

    private DialogSimulItemAllClick dialogSimulItemAllClick;
    public interface DialogSimulItemAllClick {
        public void onClick(int position);
    }

    public void setDialogSimulItemAllClick(DialogSimulItemAllClick dialogSimulItemAllClick) {
        this.dialogSimulItemAllClick = dialogSimulItemAllClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == TOP){
            return new DialogSimulTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialogsimultop, parent, false));
        }else{
            return new DialogSimulViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialogsimul, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DialogSimulViewHolder){
            ((DialogSimulViewHolder)holder).title.setText(modelSimulLists.get(position).getTitle());
            ((DialogSimulViewHolder)holder).code.setText(modelSimulLists.get(position).getCode());
            ((DialogSimulViewHolder)holder).rate.setText(String.valueOf(modelSimulLists.get(position).getRate()));

            if(modelSimulLists.get(position).getRate() < 0){
                ((DialogSimulViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((DialogSimulViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((DialogSimulViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((DialogSimulViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

            ((DialogSimulViewHolder)holder).addBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dialogSimulItemClick != null){
                        dialogSimulItemClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof DialogSimulTopViewHolder){
            ((DialogSimulTopViewHolder)holder).countNum.setText(String.valueOf(modelSimulLists.size()-1));
            ((DialogSimulTopViewHolder)holder).allBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dialogSimulItemAllClick != null){
                        dialogSimulItemAllClick.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelSimulLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TOP;
        }else{
            return ITEM;
        }
    }

    public class DialogSimulViewHolder extends RecyclerView.ViewHolder {

        TextView title, code, rate, per;
        ImageView addBt;

        public DialogSimulViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            code = itemView.findViewById(R.id.code);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);

            addBt = itemView.findViewById(R.id.addBt);
        }
    }

    public class DialogSimulTopViewHolder extends RecyclerView.ViewHolder {

        TextView countNum, allBt;

        public DialogSimulTopViewHolder(@NonNull View itemView) {
            super(itemView);

            countNum = itemView.findViewById(R.id.countNum);
            allBt = itemView.findViewById(R.id.allBt);
        }
    }
}