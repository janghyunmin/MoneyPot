package com.quantec.moneypot.activity.simulationsearch.adapter;

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
import com.quantec.moneypot.activity.simulationsearch.ModelPreSimulSum;

import java.util.ArrayList;

public class AdapterSimulAllTabSum extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int TOP = 1;
    private int ITEM = 2;
    private int BOTTOM = 3;

    ArrayList<ModelPreSimulSum> modelPreSimulSums;
    Context context;

    public AdapterSimulAllTabSum(ArrayList<ModelPreSimulSum> modelPreSimulSums, Context context) {
        this.modelPreSimulSums = modelPreSimulSums;
        this.context = context;
    }

    private SumEnDetailBt sumEnDetailBt;
    public interface SumEnDetailBt {
        public void onClick(int position);
    }

    public void setSumEnDetailBt(SumEnDetailBt sumEnDetailBt) {
        this.sumEnDetailBt = sumEnDetailBt;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new SimulAllTabSumEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulalltab_singleempty, parent, false));
        }
        else if(viewType == TOP){
            return new SimulAllTabSumTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulalltab_singletop, parent, false));
        }
        else if(viewType == BOTTOM){
            return new SimulAllTabSumBottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulalltab_sumbottom, parent, false));
        }
        else{
            return new SimulAllTabSumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulalltab_sumitem, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SimulAllTabSumViewHolder){
            ((SimulAllTabSumViewHolder)holder).title.setText(modelPreSimulSums.get(position).getTitle());
            ((SimulAllTabSumViewHolder)holder).stock.setText(modelPreSimulSums.get(position).getCode());

            ((SimulAllTabSumViewHolder)holder).rate.setText(String.format("%.2f", modelPreSimulSums.get(position).getRate()));
            if(modelPreSimulSums.get(position).getRate() < 0){
                ((SimulAllTabSumViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((SimulAllTabSumViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((SimulAllTabSumViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((SimulAllTabSumViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }
        }

        if(holder instanceof SimulAllTabSumTopViewHolder){
            ((SimulAllTabSumTopViewHolder)holder).num.setText("묶음기업 "+modelPreSimulSums.get(position).getTotal()+"건");
        }

        if(holder instanceof SimulAllTabSumBottomViewHolder){

            ((SimulAllTabSumBottomViewHolder)holder).detailBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sumEnDetailBt != null){
                        sumEnDetailBt.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(modelPreSimulSums.get(position).isEmpty()){
            return EMPTY;
        }
        else{
            if(position == 0){
                return TOP;
            }else{
                if(modelPreSimulSums.get(position).getTotal() > 5 && modelPreSimulSums.size()-1 == position){
                    return BOTTOM;
                }else{
                    return ITEM;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return modelPreSimulSums.size();
    }

    public class SimulAllTabSumViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout itemLayout;
        TextView title, stock, rate, per;
        ImageView addBt;

        public SimulAllTabSumViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.itemLayout);
            title = itemView.findViewById(R.id.title);
            stock = itemView.findViewById(R.id.stock);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            addBt = itemView.findViewById(R.id.addBt);
        }
    }

    public class SimulAllTabSumTopViewHolder extends RecyclerView.ViewHolder {

        TextView num;

        public SimulAllTabSumTopViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
        }
    }

    public class SimulAllTabSumBottomViewHolder extends RecyclerView.ViewHolder {

        TextView detailBt;

        public SimulAllTabSumBottomViewHolder(@NonNull View itemView) {
            super(itemView);

            detailBt = itemView.findViewById(R.id.detailBt);
        }
    }
    public class SimulAllTabSumEmptyViewHolder extends RecyclerView.ViewHolder {
        public SimulAllTabSumEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
