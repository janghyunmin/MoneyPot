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
import com.quantec.moneypot.activity.Search.SearchedPage.ModelNewsEn;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdapterNewsEn extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTY = 0;
    private final int TOP = 1;
    private final int ITEM = 2;

    ArrayList<ModelNewsEn> modelNewsEns;
    Context context;

    public AdapterNewsEn(ArrayList<ModelNewsEn> modelNewsEns, Context context) {
        this.modelNewsEns = modelNewsEns;
        this.context = context;
    }

    private NewsEnClick newsEnClick;
    public interface NewsEnClick{
        public void onClick(int position);
    }

    public void setNewsEnClick(NewsEnClick newsEnClick) {
        this.newsEnClick = newsEnClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new NewsEnEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsen_empty, parent, false));
        }
        else if(viewType == TOP){
            return new NewsEnTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsen_top, parent, false));
        }
        else{
            return new NewsEnItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newsen_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof NewsEnItemViewHolder){
            ((NewsEnItemViewHolder)holder).title.setText(modelNewsEns.get(position).getTitle());
            ((NewsEnItemViewHolder)holder).date.setText(modelNewsEns.get(position).getDate());

            RxView.clicks(((NewsEnItemViewHolder)holder).itemLayout).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(newsEnClick != null){
                    newsEnClick.onClick(position);
                }
            });
        }

        if(holder instanceof NewsEnTopViewHolder){
            ((NewsEnTopViewHolder)holder).num.setText("기사제목 "+modelNewsEns.get(position).getTotal()+"건");
        }
    }

    @Override
    public int getItemCount() {
        return modelNewsEns.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelNewsEns.get(0).isEmpty()){
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

    public class NewsEnItemViewHolder extends RecyclerView.ViewHolder {

        TextView title, date;
        ConstraintLayout itemLayout;

        public NewsEnItemViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);

            itemLayout = itemView.findViewById(R.id.itemLayout);

        }
    }

    public class NewsEnTopViewHolder extends RecyclerView.ViewHolder {

        TextView num;

        public NewsEnTopViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
        }
    }

    public class NewsEnEmptyViewHolder extends RecyclerView.ViewHolder {
        public NewsEnEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
