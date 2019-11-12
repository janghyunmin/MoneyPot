package com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.datamodel.dmodel.ModelAllRankingTop3;
import com.quantec.moneypot.datamodel.dmodel.ModelFgTab3;
import com.quantec.moneypot.datamodel.dmodel.ModelMini;
import com.quantec.moneypot.datamodel.dmodel.ModelStrongTop3;
import com.quantec.moneypot.datamodel.dmodel.ModelThemeTop3;
import com.quantec.moneypot.R;

public class AdapterFgTab3  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int RANK = 0;
    private final int STRONG = 1;
    private final int MINI = 2;
    private final int THEME = 3;

    ArrayList<ModelFgTab3> modelFgTab3s;
    Context context;

    ArrayList<ModelAllRankingTop3> modelAllRankingTop3s;
    ArrayList<ModelStrongTop3> modelStrongTop3s;
    ArrayList<ModelMini> modelMinis;
    ArrayList<ModelThemeTop3> modelThemeTop3s;

    public AdapterFgTab3(ArrayList<ModelFgTab3> modelFgTab3s, Context context, ArrayList<ModelAllRankingTop3> modelAllRankingTop3s, ArrayList<ModelStrongTop3> modelStrongTop3s, ArrayList<ModelMini> modelMinis, ArrayList<ModelThemeTop3> modelThemeTop3s) {
        this.modelFgTab3s = modelFgTab3s;
        this.context = context;
        this.modelAllRankingTop3s = modelAllRankingTop3s;
        this.modelStrongTop3s = modelStrongTop3s;
        this.modelMinis = modelMinis;
        this.modelThemeTop3s = modelThemeTop3s;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == RANK){
            return new AllRankingTop3ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allrankingtop3, parent, false));
        }else if(viewType == STRONG){
            return new StrongTop3ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_strongtop3, parent, false));
        }else if(viewType == MINI){
            return new MiniTop3ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minitop3, parent, false));
        }else{
            return new ThemeTop3ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_themetop3, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return modelFgTab3s.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return RANK;
        }else if(position == 1){
            return STRONG;
        }else if(position == 2){
            return MINI;
        }else {
            return THEME;
        }
    }

    public class AllRankingTop3ViewHolder extends RecyclerView.ViewHolder {
        public AllRankingTop3ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class StrongTop3ViewHolder extends RecyclerView.ViewHolder {
        public StrongTop3ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MiniTop3ViewHolder extends RecyclerView.ViewHolder {
        public MiniTop3ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ThemeTop3ViewHolder extends RecyclerView.ViewHolder {
        public ThemeTop3ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
