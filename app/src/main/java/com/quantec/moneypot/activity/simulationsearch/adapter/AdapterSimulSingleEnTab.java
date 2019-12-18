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
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.activity.simulationsearch.ActivitySimulationSearch;
import com.quantec.moneypot.activity.simulationsearch.ModelSimulSingle;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdapterSimulSingleEnTab extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int TOP = 1;
    private int ITEM = 2;

    ArrayList<ModelSimulSingle> modelSimulSingles;
    Context context;

    public AdapterSimulSingleEnTab(ArrayList<ModelSimulSingle> modelSimulSingles, Context context) {
        this.modelSimulSingles = modelSimulSingles;
        this.context = context;
    }

    private SingleEnRecomBt1 singleEnRecomBt1;
    public interface SingleEnRecomBt1{
        public void onClick(String title);
    }

    public void setSingleEnRecomBt1(SingleEnRecomBt1 singleEnRecomBt1) {
        this.singleEnRecomBt1 = singleEnRecomBt1;
    }

    private SingleEnRecomBt2 singleEnRecomBt2;
    public interface SingleEnRecomBt2{
        public void onClick(String title);
    }

    public void setSingleEnRecomBt2(SingleEnRecomBt2 singleEnRecomBt2) {
        this.singleEnRecomBt2 = singleEnRecomBt2;
    }

    private SingleEnRecomBt3 singleEnRecomBt3;
    public interface SingleEnRecomBt3 {
        public void onClick(String title);
    }

    public void setSingleEnRecomBt3(SingleEnRecomBt3 singleEnRecomBt3) {
        this.singleEnRecomBt3 = singleEnRecomBt3;
    }

    private SingleEnRecomBt4 singleEnRecomBt4;
    public interface SingleEnRecomBt4 {
        public void onClick(String title);
    }

    public void setSingleEnRecomBt4(SingleEnRecomBt4 singleEnRecomBt4) {
        this.singleEnRecomBt4 = singleEnRecomBt4;
    }

    private SingleEnRecomBt5 singleEnRecomBt5;
    public interface SingleEnRecomBt5 {
        public void onClick(String title);
    }

    public void setSingleEnRecomBt5(SingleEnRecomBt5 singleEnRecomBt5) {
        this.singleEnRecomBt5 = singleEnRecomBt5;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == EMPTY){
            return new SimulSingleEnEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulsingleentab_empty, parent, false));
        }
        else if(viewType == TOP){
            return new SimulSingleEnTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulsingleentab_top, parent, false));
        }
        else{
            return new SimulSingleEnViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simulsingleentab_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SimulSingleEnViewHolder){

            ((SimulSingleEnViewHolder)holder).title.setText(modelSimulSingles.get(position).getName());
            ((SimulSingleEnViewHolder)holder).rate.setText(String.format("%.2f", modelSimulSingles.get(position).getRate()));
            if(modelSimulSingles.get(position).getRate() < 0){
                ((SimulSingleEnViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((SimulSingleEnViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((SimulSingleEnViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((SimulSingleEnViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }
        }

        if(holder instanceof SimulSingleEnTopViewHolder){
            int total = modelSimulSingles.size()-1;
            ((SimulSingleEnTopViewHolder)holder).num.setText("단일기업 "+total+"건");
        }

        if(holder instanceof SimulSingleEnEmptyViewHolder){

            int count = ActivitySimulationSearch.recommandSearchList.size();

            if(count == 1){
                ((SimulSingleEnEmptyViewHolder)holder).recomBt1.setVisibility(View.VISIBLE);
                ((SimulSingleEnEmptyViewHolder)holder).recomBt1.setText(ActivitySimulationSearch.recommandSearchList.get(0));
            }else if(count == 2){
                ((SimulSingleEnEmptyViewHolder)holder).recomBt1.setVisibility(View.VISIBLE);
                ((SimulSingleEnEmptyViewHolder)holder).recomBt2.setVisibility(View.VISIBLE);

                ((SimulSingleEnEmptyViewHolder)holder).recomBt1.setText(ActivitySimulationSearch.recommandSearchList.get(0));
                ((SimulSingleEnEmptyViewHolder)holder).recomBt2.setText(ActivitySimulationSearch.recommandSearchList.get(1));
            }else if(count == 3){
                ((SimulSingleEnEmptyViewHolder)holder).recomBt1.setVisibility(View.VISIBLE);
                ((SimulSingleEnEmptyViewHolder)holder).recomBt2.setVisibility(View.VISIBLE);
                ((SimulSingleEnEmptyViewHolder)holder).recomBt3.setVisibility(View.VISIBLE);

                ((SimulSingleEnEmptyViewHolder)holder).recomBt1.setText(ActivitySimulationSearch.recommandSearchList.get(0));
                ((SimulSingleEnEmptyViewHolder)holder).recomBt2.setText(ActivitySimulationSearch.recommandSearchList.get(1));
                ((SimulSingleEnEmptyViewHolder)holder).recomBt3.setText(ActivitySimulationSearch.recommandSearchList.get(2));

            }else if(count == 4){
                ((SimulSingleEnEmptyViewHolder)holder).recomBt1.setVisibility(View.VISIBLE);
                ((SimulSingleEnEmptyViewHolder)holder).recomBt2.setVisibility(View.VISIBLE);
                ((SimulSingleEnEmptyViewHolder)holder).recomBt3.setVisibility(View.VISIBLE);
                ((SimulSingleEnEmptyViewHolder)holder).recomBt4.setVisibility(View.VISIBLE);

                ((SimulSingleEnEmptyViewHolder)holder).recomBt1.setText(ActivitySimulationSearch.recommandSearchList.get(0));
                ((SimulSingleEnEmptyViewHolder)holder).recomBt2.setText(ActivitySimulationSearch.recommandSearchList.get(1));
                ((SimulSingleEnEmptyViewHolder)holder).recomBt3.setText(ActivitySimulationSearch.recommandSearchList.get(2));
                ((SimulSingleEnEmptyViewHolder)holder).recomBt4.setText(ActivitySimulationSearch.recommandSearchList.get(3));

            }else if(count == 5){
                ((SimulSingleEnEmptyViewHolder)holder).recomBt1.setVisibility(View.VISIBLE);
                ((SimulSingleEnEmptyViewHolder)holder).recomBt2.setVisibility(View.VISIBLE);
                ((SimulSingleEnEmptyViewHolder)holder).recomBt3.setVisibility(View.VISIBLE);
                ((SimulSingleEnEmptyViewHolder)holder).recomBt4.setVisibility(View.VISIBLE);
                ((SimulSingleEnEmptyViewHolder)holder).recomBt5.setVisibility(View.VISIBLE);

                ((SimulSingleEnEmptyViewHolder)holder).recomBt1.setText(ActivitySimulationSearch.recommandSearchList.get(0));
                ((SimulSingleEnEmptyViewHolder)holder).recomBt2.setText(ActivitySimulationSearch.recommandSearchList.get(1));
                ((SimulSingleEnEmptyViewHolder)holder).recomBt3.setText(ActivitySimulationSearch.recommandSearchList.get(2));
                ((SimulSingleEnEmptyViewHolder)holder).recomBt4.setText(ActivitySimulationSearch.recommandSearchList.get(3));
                ((SimulSingleEnEmptyViewHolder)holder).recomBt5.setText(ActivitySimulationSearch.recommandSearchList.get(4));
            }else{
            }

            RxView.clicks(((SimulSingleEnEmptyViewHolder)holder).recomBt1).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnRecomBt1 != null){
                    singleEnRecomBt1.onClick(ActivitySimulationSearch.recommandSearchList.get(0));
                }
            });
            RxView.clicks(((SimulSingleEnEmptyViewHolder)holder).recomBt2).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnRecomBt2 != null){
                    singleEnRecomBt2.onClick(ActivitySimulationSearch.recommandSearchList.get(1));
                }
            });
            RxView.clicks(((SimulSingleEnEmptyViewHolder)holder).recomBt3).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnRecomBt3 != null){
                    singleEnRecomBt3.onClick(ActivitySimulationSearch.recommandSearchList.get(2));
                }
            });
            RxView.clicks(((SimulSingleEnEmptyViewHolder)holder).recomBt4).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnRecomBt4 != null){
                    singleEnRecomBt4.onClick(ActivitySimulationSearch.recommandSearchList.get(3));
                }
            });
            RxView.clicks(((SimulSingleEnEmptyViewHolder)holder).recomBt5).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnRecomBt5 != null){
                    singleEnRecomBt5.onClick(ActivitySimulationSearch.recommandSearchList.get(4));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelSimulSingles.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(modelSimulSingles.get(position).isEmpty()){
            return EMPTY;
        }
        else{

            if(position == 0){
                return TOP;
            }else{
                return ITEM;
            }
        }
    }

    public class SimulSingleEnViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout itemLayout;
        TextView title, rate, per;
        ImageView addBt;

        public SimulSingleEnViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.itemLayout);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            addBt = itemView.findViewById(R.id.addBt);
        }
    }

    public class SimulSingleEnTopViewHolder extends RecyclerView.ViewHolder {

        TextView num;

        public SimulSingleEnTopViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
        }
    }

    public class SimulSingleEnEmptyViewHolder extends RecyclerView.ViewHolder {

        TextView recomBt1, recomBt2, recomBt3, recomBt4, recomBt5;

        public SimulSingleEnEmptyViewHolder(@NonNull View itemView) {
            super(itemView);

            recomBt1 = itemView.findViewById(R.id.recomBt1);
            recomBt2 = itemView.findViewById(R.id.recomBt2);
            recomBt3 = itemView.findViewById(R.id.recomBt3);
            recomBt4 = itemView.findViewById(R.id.recomBt4);
            recomBt5 = itemView.findViewById(R.id.recomBt5);
        }
    }


}
