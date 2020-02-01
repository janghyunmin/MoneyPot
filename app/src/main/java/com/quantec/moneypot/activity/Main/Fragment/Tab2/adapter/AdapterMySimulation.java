package com.quantec.moneypot.activity.Main.Fragment.Tab2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

    private MySimulModifyClick mySimulModifyClick;
    public interface MySimulModifyClick{
        public void onClick(int position);
    }

    public void setMySimulModifyClick(MySimulModifyClick mySimulModifyClick) {
        this.mySimulModifyClick = mySimulModifyClick;
    }

    private MySimulClick mySimulClick;
    public interface MySimulClick{
        public void onClick(int position);
    }

    public void setMySimulClick(MySimulClick mySimulClick) {
        this.mySimulClick = mySimulClick;
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

        if(holder instanceof MySimulationItemViewHolder){

            ((MySimulationItemViewHolder)holder).title.setText(modelMySimulations.get(position).getTitle());
            ((MySimulationItemViewHolder)holder).rate.setText(String.valueOf(modelMySimulations.get(position).getRate()));
            if(modelMySimulations.get(position).getRate() < 0){
                ((MySimulationItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
                ((MySimulationItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_4e7cff));
            }else{
                ((MySimulationItemViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.c_f02654));
                ((MySimulationItemViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.c_f02654));
            }

            ((MySimulationItemViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mySimulClick != null){
                        mySimulClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof MySimulationTopViewHolder){
            ((MySimulationTopViewHolder)holder).num.setText(modelMySimulations.get(0).getTotal()+"/10개");
            ((MySimulationTopViewHolder)holder).modifyBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mySimulModifyClick != null){
                        mySimulModifyClick.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof MySimulationEmptyViewHolder){


        }

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

        TextView title, rate, per;
        ConstraintLayout itemLayout;

        public MySimulationItemViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            itemLayout = itemView.findViewById(R.id.itemLayout);

        }
    }

    public class MySimulationTopViewHolder extends RecyclerView.ViewHolder {

        TextView num, modifyBt;

        public MySimulationTopViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
            modifyBt = itemView.findViewById(R.id.modifyBt);
        }
    }

    public class MySimulationEmptyViewHolder extends RecyclerView.ViewHolder {
        public MySimulationEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
