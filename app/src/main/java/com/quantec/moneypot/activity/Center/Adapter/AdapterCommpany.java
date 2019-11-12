package com.quantec.moneypot.activity.Center.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.datamodel.dmodel.ModelCommpanyList;
import com.quantec.moneypot.R;

public class AdapterCommpany extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelCommpanyList> modelCommpanyLists;
    Context context;

    public AdapterCommpany(ArrayList<ModelCommpanyList> modelCommpanyLists, Context context) {
        this.modelCommpanyLists = modelCommpanyLists;
        this.context = context;
    }

    private CommpanyTitleClick commpanyTitleClick;
    public interface CommpanyTitleClick {
        public void onClick(int position);
    }

    public void setCommpanyTitleClick(CommpanyTitleClick commpanyTitleClick) {
        this.commpanyTitleClick = commpanyTitleClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommpanyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgcommpany, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CommpanyViewHolder){

            ((CommpanyViewHolder)holder).commpanyTitle.setText(modelCommpanyLists.get(position).getTitle());

            ((CommpanyViewHolder)holder).layoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(commpanyTitleClick != null){
                        commpanyTitleClick.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return modelCommpanyLists.size();
    }

    public class CommpanyViewHolder extends RecyclerView.ViewHolder {

        TextView commpanyTitle;
        ConstraintLayout layoutView;

        public CommpanyViewHolder(View itemView) {
            super(itemView);

            commpanyTitle = itemView.findViewById(R.id.commpanyTitle);
            layoutView = itemView.findViewById(R.id.layoutView);
        }
    }
}
