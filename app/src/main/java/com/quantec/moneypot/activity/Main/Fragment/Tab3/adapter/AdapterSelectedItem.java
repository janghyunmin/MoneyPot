package com.quantec.moneypot.activity.Main.Fragment.Tab3.adapter;

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
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelSelectItem;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdapterSelectedItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int ITEM = 1;
    private int EMPTY = 2;

    ArrayList<ModelSelectItem> modelSelectItems;
    Context context;

    public AdapterSelectedItem(ArrayList<ModelSelectItem> modelSelectItems, Context context) {
        this.modelSelectItems = modelSelectItems;
        this.context = context;
    }

    private SelectedItemClick selectedItemClick;
    public interface SelectedItemClick {
        public void onClick(int position);
    }

    public void setSelectedItemClick(SelectedItemClick selectedItemClick) {
        this.selectedItemClick = selectedItemClick;
    }

    private SelectedDeleteClick selectedDeleteClick;
    public interface SelectedDeleteClick{
        public void onclick(int position);
    }

    public void setSelectedDeleteClick(SelectedDeleteClick selectedDeleteClick) {
        this.selectedDeleteClick = selectedDeleteClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM){
            return new SelectedItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab3_selectitem, parent, false));
        }else{
            return new SelectedEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab3_empty, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SelectedItemViewHolder){

            ((SelectedItemViewHolder)holder).title.setText(modelSelectItems.get(position).getTitle());
            ((SelectedItemViewHolder)holder).code.setText(modelSelectItems.get(position).getCode());
            ((SelectedItemViewHolder)holder).rate.setText(modelSelectItems.get(position).getRate()+"%");

            ((SelectedItemViewHolder)holder).num.setText(String.valueOf(position+1));

            if(modelSelectItems.get(position).getRate() < 0){
                ((SelectedItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((SelectedItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

            ((SelectedItemViewHolder)holder).selectedLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedItemClick != null){
                        selectedItemClick.onClick(position);
                    }
                }
            });

            ((SelectedItemViewHolder)holder).deleteBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedDeleteClick != null){
                        selectedDeleteClick.onclick(position);
                    }
                }
            });

            RxView.clicks(((SelectedItemViewHolder)holder).deleteBt).throttleFirst(600, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(selectedDeleteClick != null){
                    selectedDeleteClick.onclick(position);
                }
            });
        }

        if(holder instanceof SelectedEmptyViewHolder){
        }
    }

    @Override
    public int getItemViewType(int position) {

            if(modelSelectItems.get(position).isEmpty()){
                return EMPTY;
            }else{
                return ITEM;
            }
    }

    @Override
    public int getItemCount() {
        return modelSelectItems.size();
    }

    public class SelectedItemViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout selectedLayout;
        ImageView deleteBt;
        TextView num, title, code ,rate;

        public SelectedItemViewHolder(@NonNull View itemView) {
            super(itemView);

            selectedLayout = itemView.findViewById(R.id.selectedLayout);
            deleteBt = itemView.findViewById(R.id.deleteBt);

            num = itemView.findViewById(R.id.num);
            title = itemView.findViewById(R.id.title);
            code = itemView.findViewById(R.id.code);
            rate = itemView.findViewById(R.id.rate);

        }
    }

    public class SelectedEmptyViewHolder extends RecyclerView.ViewHolder {
        public SelectedEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
