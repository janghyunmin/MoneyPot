package com.quantec.moneypot.activity.prefer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.activity.prefer.ModelEnterList;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdapterEnter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelEnterList> modelEnterLists;
    Context context;

    public AdapterEnter(ArrayList<ModelEnterList> modelEnterLists, Context context) {
        this.modelEnterLists = modelEnterLists;
        this.context = context;
    }


    private SectorItemClick sectorItemClick;
    public interface SectorItemClick {
        public void onClick(int position);
    }

    public void setSectorItemClick(SectorItemClick sectorItemClick) {
        this.sectorItemClick = sectorItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 1){
            return new EnterEndPointViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sectorendpoint, parent, false));
        }else{
            return new EnterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sector, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof EnterViewHolder){

            ((EnterViewHolder)holder).title.setText(modelEnterLists.get(position).getTitle());
            int resource = context.getResources().getIdentifier("ci_"+modelEnterLists.get(position).getImage(), "drawable", context.getPackageName());
            ((EnterViewHolder)holder).image.setImageDrawable(context.getResources().getDrawable(resource));

            if(modelEnterLists.get(position).isSelect()){

                ((EnterViewHolder)holder).imageSelected.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_selected));

            }else{
                ((EnterViewHolder)holder).imageSelected.setImageDrawable(null);
            }


            RxView.clicks(((EnterViewHolder)holder).image).throttleFirst(700, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(sectorItemClick != null){
                    sectorItemClick.onClick(position);
                }
            });

        }

        if(holder instanceof EnterEndPointViewHolder){
        }
    }

    @Override
    public int getItemCount() {
        return modelEnterLists.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(modelEnterLists.get(position).isEndPoint()){
            return 1;
        }else{
            return 0;
        }
    }

    public class EnterViewHolder extends RecyclerView.ViewHolder {

        ImageView image, imageSelected;
        TextView title;
        ConstraintLayout itemLayout;

        public EnterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageSelected = itemView.findViewById(R.id.imageSelected);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);

            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class EnterEndPointViewHolder extends RecyclerView.ViewHolder {
        public EnterEndPointViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
