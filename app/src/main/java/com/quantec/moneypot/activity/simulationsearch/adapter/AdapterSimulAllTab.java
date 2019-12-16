package com.quantec.moneypot.activity.simulationsearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.simulationsearch.ModelSimulSingle;

import java.util.ArrayList;

public class AdapterSimulAllTab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int TOP = 1;
    private int ITEM = 2;
    private int BOTTOM = 3;

    ArrayList<ModelSimulSingle> modelSimulSingles;
    Context context;

    public AdapterSimulAllTab(ArrayList<ModelSimulSingle> modelSimulSingles, Context context) {
        this.modelSimulSingles = modelSimulSingles;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new SimulAllTabEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulalltab_singleempty, parent, false));
        }
        else if(viewType == TOP){
            return new SimulAllTabTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulalltab_singletop, parent, false));
        }
        else if(viewType == BOTTOM){
            return new SimulAllTabBottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulalltab_singlebottom, parent, false));
        }
        else{
            return new SimulAllTabViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulalltab_singleitem, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if(modelSimulSingles.size()==0){
            return EMPTY;
        }
        else{
            if(position == 0){
                return TOP;
            }else{

                if(modelSimulSingles.get(position).getTotal() > 5 && modelSimulSingles.size()-1 == position){
                    return BOTTOM;
                }else{
                    return ITEM;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return modelSimulSingles.size();
    }

    public class SimulAllTabViewHolder extends RecyclerView.ViewHolder {
        public SimulAllTabViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class SimulAllTabTopViewHolder extends RecyclerView.ViewHolder {
        public SimulAllTabTopViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class SimulAllTabBottomViewHolder extends RecyclerView.ViewHolder {
        public SimulAllTabBottomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class SimulAllTabEmptyViewHolder extends RecyclerView.ViewHolder {
        public SimulAllTabEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
