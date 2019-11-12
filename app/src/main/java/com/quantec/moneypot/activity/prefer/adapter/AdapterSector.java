package com.quantec.moneypot.activity.prefer.adapter;

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
import com.quantec.moneypot.activity.prefer.ModelSectorList;

import java.util.ArrayList;

public class AdapterSector extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelSectorList> modelSectorLists;
    Context context;

    public AdapterSector(ArrayList<ModelSectorList> modelSectorLists, Context context) {
        this.modelSectorLists = modelSectorLists;
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
            return new SectorEndPointViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sectorendpoint, parent, false));
        }else{
            return new SectorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sector, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof SectorViewHolder){

            ((SectorViewHolder)holder).title.setText(modelSectorLists.get(position).getTitle());
            int resource = context.getResources().getIdentifier("ci_"+modelSectorLists.get(position).getImage(), "drawable", context.getPackageName());
            ((SectorViewHolder)holder).image.setImageDrawable(context.getResources().getDrawable(resource));

            if(modelSectorLists.get(position).isSelect()){

                ((SectorViewHolder)holder).imageSelected.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_selected));

            }else{
                ((SectorViewHolder)holder).imageSelected.setImageDrawable(null);
            }

            ((SectorViewHolder)holder).image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sectorItemClick != null){
                        sectorItemClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof SectorEndPointViewHolder){
        }

    }

    @Override
    public int getItemCount() {
        return modelSectorLists.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelSectorLists.get(position).isEndPoint()){
            return 1;
        }else{
            return 0;
        }
    }

    public class SectorViewHolder extends RecyclerView.ViewHolder {

        ImageView image, imageSelected;
        TextView title;
        ConstraintLayout itemLayout;

        public SectorViewHolder(@NonNull View itemView) {
            super(itemView);

            imageSelected = itemView.findViewById(R.id.imageSelected);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);

            itemLayout = itemView.findViewById(R.id.itemLayout);

        }
    }

    public class SectorEndPointViewHolder extends RecyclerView.ViewHolder {
        public SectorEndPointViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
