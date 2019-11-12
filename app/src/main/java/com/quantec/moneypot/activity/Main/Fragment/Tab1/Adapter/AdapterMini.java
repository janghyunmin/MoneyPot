package com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.datamodel.dmodel.ModelMini;
import com.quantec.moneypot.R;

public class AdapterMini extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelMini> modelMinis;
    Context context;

    public AdapterMini(ArrayList<ModelMini> modelMinis, Context context) {
        this.modelMinis = modelMinis;
        this.context = context;
    }

    private MiniItemClick miniItemClick;
    public interface MiniItemClick {
        public void onClick(int position);
    }

    public void setMiniItemClick(MiniItemClick miniItemClick) {
        this.miniItemClick = miniItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MiniViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potmarket_mini, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MiniViewHolder){

            ((MiniViewHolder)holder).title.setText(modelMinis.get(position).getTitle());
            ((MiniViewHolder)holder).image.setImageDrawable(modelMinis.get(position).getImage());

            ((MiniViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(miniItemClick != null){
                        miniItemClick.onClick(position);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return modelMinis.size();
    }

    public class MiniViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        ConstraintLayout itemLayout;

        public MiniViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
