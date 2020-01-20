package com.quantec.moneypot.activity.Main.Fragment.Tab1;

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

public class AdapterScroll extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<ModelRecomList> modelRecomLists;
    Context context;

    public AdapterScroll(ArrayList<ModelRecomList> modelRecomLists, Context context) {
        this.modelRecomLists = modelRecomLists;
        this.context = context;
    }

    private ScrollItemClick scrollItemClick;
    public interface ScrollItemClick{
        public void onClick(int position);
    }

    public void setScrollItemClick(ScrollItemClick scrollItemClick) {
        this.scrollItemClick = scrollItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScrollViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scroll_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ScrollViewHolder){
            ((ScrollViewHolder)holder).title.setText(modelRecomLists.get(position).getTitle());
            ((ScrollViewHolder)holder).desc.setText(modelRecomLists.get(position).getDesc());
            ((ScrollViewHolder)holder).rate.setText(String.valueOf(modelRecomLists.get(position).getRate()));

            int resource =context.getResources().getIdentifier("img_reco_"+modelRecomLists.get(position).getCode().toLowerCase(), "drawable", context.getPackageName());
            ((ScrollViewHolder)holder).image.setImageDrawable(context.getResources().getDrawable(resource));
//            if(position == 0){
//                ((ScrollViewHolder)holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.img_reco_nike));
//            }else if(position == 1){
//                ((ScrollViewHolder)holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.img_reco_shakeshack));
//            }else{
//                ((ScrollViewHolder)holder).image.setImageDrawable(context.getResources().getDrawable(R.drawable.img_reco_intel));
//            }

            ((ScrollViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(scrollItemClick != null){
                        scrollItemClick.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelRecomLists.size();
    }

    public class ScrollViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc, rate;
        ConstraintLayout itemLayout;
        ImageView image;

        public ScrollViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            rate = itemView.findViewById(R.id.rate);

            itemLayout = itemView.findViewById(R.id.itemLayout);
            image = itemView.findViewById(R.id.image);
        }
    }
}
