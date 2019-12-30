package com.quantec.moneypot.activity.Main.Fragment.Tab2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.fragment.ModelMySimulation;

import java.util.ArrayList;

public class AdapterMySimulation extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TOP = 0;
    private int ITEM = 1;
    private int EMPTY = 2;

    ArrayList<ModelMySimulation> modelMySimulations;
    Context context;

    public AdapterMySimulation(ArrayList<ModelMySimulation> modelMySimulations, Context context) {
        this.modelMySimulations = modelMySimulations;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TOP){
            return new MySimulationTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mysimulation_top, parent, false));
        }else if(viewType == EMPTY){
            return new MySimulationEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mysimulation_empty, parent, false));
        }else{
            return new MySimulationItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mysimulation_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        if(modelMySimulations.get(position).isEmpty()){
            return EMPTY;
        }else{
            if(position == 0){
                return TOP;
            }else{
                return ITEM;
            }
        }
    }

    @Override
    public int getItemCount() {
        return modelMySimulations.size();
    }

    public class MySimulationItemViewHolder extends RecyclerView.ViewHolder {
        public MySimulationItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MySimulationTopViewHolder extends RecyclerView.ViewHolder {
        public MySimulationTopViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MySimulationEmptyViewHolder extends RecyclerView.ViewHolder {
        public MySimulationEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
