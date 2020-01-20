package com.quantec.moneypot.activity.Main.Fragment.Tab3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelFgTab3Follow;

import java.util.ArrayList;

public class AdapterFgTab3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TOPITEM = 0;
    private int ITEM = 1;
    private int EMPTY = 2;

    ArrayList<ModelFgTab3Follow> modelFgTab3Follows;
    Context context;

    public AdapterFgTab3(ArrayList<ModelFgTab3Follow> modelFgTab3Follows, Context context) {
        this.modelFgTab3Follows = modelFgTab3Follows;
        this.context = context;
    }

    private FollowItemClick followItemClick;
    public interface FollowItemClick {
        public void onClick(int position);
    }

    public void setFollowItemClick(FollowItemClick followItemClick) {
        this.followItemClick = followItemClick;
    }

    private FollowAddClick followAddClick;
//    public interface FollowAddClick {
//        public void onClick(int position, String title, String code, double rate, int ent);
//    }
    public interface FollowAddClick {
        public void onClick(int position);
    }

    public void setFollowAddClick(FollowAddClick followAddClick) {
        this.followAddClick = followAddClick;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == TOPITEM){
            return new FgTab3ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab3_topitem, parent, false));
        }else if(viewType == ITEM){
            return new FgTab3ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab3_item, parent, false));
        }else{
            return new FgTab3EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab3_empty, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof FgTab3ViewHolder){
        }

        if(holder instanceof FgTab3ItemViewHolder){

            if(modelFgTab3Follows.get(position).getGubun() == 0){
                ((FgTab3ItemViewHolder)holder).addBt.setImageDrawable(context.getResources().getDrawable(R.drawable.button_add));
            }else{
                ((FgTab3ItemViewHolder)holder).addBt.setImageDrawable(context.getResources().getDrawable(R.drawable.button_arrow_down));
            }

            ((FgTab3ItemViewHolder)holder).title.setText(modelFgTab3Follows.get(position).getTitle());
            ((FgTab3ItemViewHolder)holder).code.setText(modelFgTab3Follows.get(position).getCode());
            ((FgTab3ItemViewHolder)holder).rate.setText(String.valueOf(modelFgTab3Follows.get(position).getRate()));

            if(modelFgTab3Follows.get(position).getRate() < 0){
                ((FgTab3ItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((FgTab3ItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((FgTab3ItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((FgTab3ItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

            ((FgTab3ItemViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(followItemClick != null){
                        followItemClick.onClick(position);
                    }
                }
            });

            ((FgTab3ItemViewHolder)holder).addBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(followAddClick != null){
                        followAddClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof FgTab3EmptyViewHolder){
        }
    }

    @Override
    public int getItemCount() {
        return modelFgTab3Follows.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(modelFgTab3Follows.get(position).isEmpty()){
            return EMPTY;
        }else{
            if(position == 0){
                return TOPITEM;
            }else{
                return ITEM;
            }
        }

//        if(position == 0){
//            return TOPITEM;
//        }else{
//
//            if(modelFgTab3Follows.get(position).isEmpty()){
//                return EMPTY;
//            }else{
//                return ITEM;
//            }
//        }
    }

    public class FgTab3ViewHolder extends RecyclerView.ViewHolder {
        public FgTab3ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class FgTab3ItemViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout itemLayout;
        ImageView addBt;
        TextView title, code, rate, per;

        public FgTab3ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.itemLayout);
            addBt = itemView.findViewById(R.id.addBt);

            title = itemView.findViewById(R.id.title);
            code = itemView.findViewById(R.id.code);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);

        }
    }

    public class FgTab3EmptyViewHolder extends RecyclerView.ViewHolder {
        public FgTab3EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
