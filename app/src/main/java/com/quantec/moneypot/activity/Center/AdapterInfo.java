package com.quantec.moneypot.activity.center;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;

import java.util.ArrayList;

public class AdapterInfo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TOP = 0;
    private final int ITEM = 1;


    ArrayList<ModelInfo> modelInfos;
    Context context;

    public AdapterInfo(ArrayList<ModelInfo> modelInfos, Context context) {
        this.modelInfos = modelInfos;
        this.context = context;
    }

    private InfoItemClick infoItemClick;
    public interface InfoItemClick{
        public void onClick(int position);
    }

    public void setInfoItemClick(InfoItemClick infoItemClick) {
        this.infoItemClick = infoItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TOP) {
            return new InfoTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_type, parent, false));
        } else {
            return new InfoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof InfoViewHolder){
            ((InfoViewHolder)holder).title.setText(modelInfos.get(position).getTitle());
            ((InfoViewHolder)holder).date.setText(modelInfos.get(position).getDate());

            ((InfoViewHolder)holder).linearItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infoItemClick != null){
                        infoItemClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof InfoTopViewHolder){
            ((InfoTopViewHolder)holder).title.setText(modelInfos.get(position).getType());
        }
    }

    @Override
    public int getItemCount() {
        return modelInfos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelInfos.get(position).isTop()){
            return TOP;
        }else{
            return ITEM;
        }
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder {

        TextView title, date;
        ConstraintLayout linearItem;

        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);

            linearItem = itemView.findViewById(R.id.linearItem);
        }
    }

    public class InfoTopViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public InfoTopViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
        }
    }
}
