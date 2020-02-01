package com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.ModelFollowHome;

import java.util.ArrayList;

public class AdapterFollowHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int EMPTY = 0;
    private final int ITEM = 1;
    private final int TOP = 2;
    private final int BOTTOM = 3;

    ArrayList<ModelFollowHome> modelFollowHomes;
    Context context;

    public AdapterFollowHome(ArrayList<ModelFollowHome> modelFollowHomes, Context context) {
        this.modelFollowHomes = modelFollowHomes;
        this.context = context;
    }

    private FollowHomeClick followHomeClick;
    public interface FollowHomeClick{
        public void onClick(int position);
    }

    public void setFollowHomeClick(FollowHomeClick followHomeClick) {
        this.followHomeClick = followHomeClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new FollowHomeEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followhome_empty, parent, false));
        }else if(viewType == TOP){
            return new FollowHomeTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followhome_top, parent, false));
        }else if(viewType == BOTTOM){
            return new FollowHomeBottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followhome_bottom, parent, false));
        }
        else{
            return new FollowHomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_followhome_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof FollowHomeViewHolder){
            ((FollowHomeViewHolder)holder).title1.setText(modelFollowHomes.get(position).getTitle());
            ((FollowHomeViewHolder)holder).rate1.setText(String.valueOf(modelFollowHomes.get(position).getRate()));
            ((FollowHomeViewHolder)holder).price1.setText(String.valueOf(modelFollowHomes.get(position).getPrice()));

            if(modelFollowHomes.get(position).getRate() < 0){
                ((FollowHomeViewHolder)holder).rate1.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
                ((FollowHomeViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
                ((FollowHomeViewHolder)holder).image1.setBackgroundResource(R.drawable.ic_follow_cloudy);
            }else{
                ((FollowHomeViewHolder)holder).rate1.setTextColor(context.getResources().getColor(R.color.c_f02654));
                ((FollowHomeViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_f02654));
                ((FollowHomeViewHolder)holder).image1.setBackgroundResource(R.drawable.ic_follow_sunny);
            }
        }

        if(holder instanceof FollowHomeEmptyViewHolder){

        }

        if(holder instanceof FollowHomeTopViewHolder){

        }

        if(holder instanceof FollowHomeBottomViewHolder){

            ((FollowHomeBottomViewHolder)holder).addViewBt.setText(modelFollowHomes.get(position).getTitle());

            ((FollowHomeBottomViewHolder)holder).addViewBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(followHomeClick != null){
                        followHomeClick.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelFollowHomes.size();
    }

    public class FollowHomeViewHolder extends RecyclerView.ViewHolder {

        TextView title1, rate1, price1, per, won;
        ImageView image1;

        public FollowHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            title1 = itemView.findViewById(R.id.title1);
            rate1 = itemView.findViewById(R.id.rate1);
            price1 = itemView.findViewById(R.id.price1);

            per = itemView.findViewById(R.id.per);
            won = itemView.findViewById(R.id.won);

            image1 = itemView.findViewById(R.id.image1);
        }
    }

    public class FollowHomeEmptyViewHolder extends RecyclerView.ViewHolder {
        public FollowHomeEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class FollowHomeTopViewHolder extends RecyclerView.ViewHolder {
        public FollowHomeTopViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class FollowHomeBottomViewHolder extends RecyclerView.ViewHolder {

        TextView addViewBt;

        public FollowHomeBottomViewHolder(@NonNull View itemView) {
            super(itemView);

            addViewBt = itemView.findViewById(R.id.addViewBt);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(modelFollowHomes.get(position).isEmpty()){
            return EMPTY;
        }else {

            if(position == 0){
                return TOP;
            }else{
                if(modelFollowHomes.get(position).isBottom()){
                    return BOTTOM;
                }else{
                    return ITEM;
                }
            }
        }
    }

//    @Override
//    public int getItemViewType(int position) {
//        if(modelFollowHomes.get(position).isEmpty()){
//            return EMPTY;
//        }else {
//
//            if(position == 0){
//                return TOP;
//            }
//            else if(position == modelFollowHomes.size()-1){
//                return BOTTOM;
//            }
//            else{
//                return ITEM;
//            }
//        }
//    }
}
