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
import com.quantec.moneypot.activity.simulationsearch.ModelPreSimulSingle;
import com.quantec.moneypot.activity.simulationsearch.ModelSimulSingle;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdapterSimulAllTabSingle extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int TOP = 1;
    private int ITEM = 2;
    private int BOTTOM = 3;

    ArrayList<ModelPreSimulSingle> modelSimulSingles;
    Context context;

    public AdapterSimulAllTabSingle(ArrayList<ModelPreSimulSingle> modelSimulSingles, Context context) {
        this.modelSimulSingles = modelSimulSingles;
        this.context = context;
    }

    private SingleEnDetailBt singleEnDetailBt;
    public interface SingleEnDetailBt {
        public void onClick(int position);
    }

    public void setSingleEnDetailBt(SingleEnDetailBt singleEnDetailBt) {
        this.singleEnDetailBt = singleEnDetailBt;
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

    private SingleEnAddBt singleEnAddBt;
    public interface SingleEnAddBt {
        public void onClick(int position);
    }

    public SingleEnAddBt getSingleEnAddBt() {
        return singleEnAddBt;
    }

    public void setSingleEnAddBt(SingleEnAddBt singleEnAddBt) {
        this.singleEnAddBt = singleEnAddBt;
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
        if(holder instanceof SimulAllTabViewHolder){

            ((SimulAllTabViewHolder)holder).title.setText(modelSimulSingles.get(position).getTitle());
            ((SimulAllTabViewHolder)holder).rate.setText(String.format("%.2f", modelSimulSingles.get(position).getRate()));
            if(modelSimulSingles.get(position).getRate() < 0){
                ((SimulAllTabViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((SimulAllTabViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((SimulAllTabViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((SimulAllTabViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }


            RxView.clicks(((SimulAllTabViewHolder)holder).addBt).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnAddBt != null){
                    singleEnAddBt.onClick(position);
                }
            });

        }

        if(holder instanceof SimulAllTabTopViewHolder){
            ((SimulAllTabTopViewHolder)holder).num.setText("단일기업 "+modelSimulSingles.get(position).getTotal()+"건");
        }

        if(holder instanceof SimulAllTabEmptyViewHolder){

            int count = ActivitySimulationSearch.recommandSearchList.size();

            if(count == 1){
                ((SimulAllTabEmptyViewHolder)holder).recomBt1.setVisibility(View.VISIBLE);
                ((SimulAllTabEmptyViewHolder)holder).recomBt1.setText(ActivitySimulationSearch.recommandSearchList.get(0));
            }else if(count == 2){
                ((SimulAllTabEmptyViewHolder)holder).recomBt1.setVisibility(View.VISIBLE);
                ((SimulAllTabEmptyViewHolder)holder).recomBt2.setVisibility(View.VISIBLE);

                ((SimulAllTabEmptyViewHolder)holder).recomBt1.setText(ActivitySimulationSearch.recommandSearchList.get(0));
                ((SimulAllTabEmptyViewHolder)holder).recomBt2.setText(ActivitySimulationSearch.recommandSearchList.get(1));
            }else if(count == 3){
                ((SimulAllTabEmptyViewHolder)holder).recomBt1.setVisibility(View.VISIBLE);
                ((SimulAllTabEmptyViewHolder)holder).recomBt2.setVisibility(View.VISIBLE);
                ((SimulAllTabEmptyViewHolder)holder).recomBt3.setVisibility(View.VISIBLE);

                ((SimulAllTabEmptyViewHolder)holder).recomBt1.setText(ActivitySimulationSearch.recommandSearchList.get(0));
                ((SimulAllTabEmptyViewHolder)holder).recomBt2.setText(ActivitySimulationSearch.recommandSearchList.get(1));
                ((SimulAllTabEmptyViewHolder)holder).recomBt3.setText(ActivitySimulationSearch.recommandSearchList.get(2));

            }else if(count == 4){
                ((SimulAllTabEmptyViewHolder)holder).recomBt1.setVisibility(View.VISIBLE);
                ((SimulAllTabEmptyViewHolder)holder).recomBt2.setVisibility(View.VISIBLE);
                ((SimulAllTabEmptyViewHolder)holder).recomBt3.setVisibility(View.VISIBLE);
                ((SimulAllTabEmptyViewHolder)holder).recomBt4.setVisibility(View.VISIBLE);

                ((SimulAllTabEmptyViewHolder)holder).recomBt1.setText(ActivitySimulationSearch.recommandSearchList.get(0));
                ((SimulAllTabEmptyViewHolder)holder).recomBt2.setText(ActivitySimulationSearch.recommandSearchList.get(1));
                ((SimulAllTabEmptyViewHolder)holder).recomBt3.setText(ActivitySimulationSearch.recommandSearchList.get(2));
                ((SimulAllTabEmptyViewHolder)holder).recomBt4.setText(ActivitySimulationSearch.recommandSearchList.get(3));

            }else if(count == 5){
                ((SimulAllTabEmptyViewHolder)holder).recomBt1.setVisibility(View.VISIBLE);
                ((SimulAllTabEmptyViewHolder)holder).recomBt2.setVisibility(View.VISIBLE);
                ((SimulAllTabEmptyViewHolder)holder).recomBt3.setVisibility(View.VISIBLE);
                ((SimulAllTabEmptyViewHolder)holder).recomBt4.setVisibility(View.VISIBLE);
                ((SimulAllTabEmptyViewHolder)holder).recomBt5.setVisibility(View.VISIBLE);

                ((SimulAllTabEmptyViewHolder)holder).recomBt1.setText(ActivitySimulationSearch.recommandSearchList.get(0));
                ((SimulAllTabEmptyViewHolder)holder).recomBt2.setText(ActivitySimulationSearch.recommandSearchList.get(1));
                ((SimulAllTabEmptyViewHolder)holder).recomBt3.setText(ActivitySimulationSearch.recommandSearchList.get(2));
                ((SimulAllTabEmptyViewHolder)holder).recomBt4.setText(ActivitySimulationSearch.recommandSearchList.get(3));
                ((SimulAllTabEmptyViewHolder)holder).recomBt5.setText(ActivitySimulationSearch.recommandSearchList.get(4));
            }else{

            }


            RxView.clicks(((SimulAllTabEmptyViewHolder)holder).recomBt1).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnRecomBt1 != null){
                    singleEnRecomBt1.onClick(ActivitySimulationSearch.recommandSearchList.get(0));
                }
            });
            RxView.clicks(((SimulAllTabEmptyViewHolder)holder).recomBt2).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnRecomBt2 != null){
                    singleEnRecomBt2.onClick(ActivitySimulationSearch.recommandSearchList.get(1));
                }
            });
            RxView.clicks(((SimulAllTabEmptyViewHolder)holder).recomBt3).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnRecomBt3 != null){
                    singleEnRecomBt3.onClick(ActivitySimulationSearch.recommandSearchList.get(2));
                }
            });
            RxView.clicks(((SimulAllTabEmptyViewHolder)holder).recomBt4).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnRecomBt4 != null){
                    singleEnRecomBt4.onClick(ActivitySimulationSearch.recommandSearchList.get(3));
                }
            });
            RxView.clicks(((SimulAllTabEmptyViewHolder)holder).recomBt5).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(singleEnRecomBt5 != null){
                    singleEnRecomBt5.onClick(ActivitySimulationSearch.recommandSearchList.get(4));
                }
            });
        }

        if(holder instanceof SimulAllTabBottomViewHolder){

            ((SimulAllTabBottomViewHolder)holder).detailBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(singleEnDetailBt != null){
                        singleEnDetailBt.onClick(position);
                    }
                }
            });
        }
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

        ConstraintLayout itemLayout;
        TextView title, rate, per;
        ImageView addBt;

        public SimulAllTabViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.itemLayout);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            addBt = itemView.findViewById(R.id.addBt);
        }
    }

    public class SimulAllTabTopViewHolder extends RecyclerView.ViewHolder {

        TextView num;

        public SimulAllTabTopViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.num);
        }
    }

    public class SimulAllTabBottomViewHolder extends RecyclerView.ViewHolder {

        TextView detailBt;

        public SimulAllTabBottomViewHolder(@NonNull View itemView) {
            super(itemView);
            detailBt = itemView.findViewById(R.id.detailBt);
        }
    }

    public class SimulAllTabEmptyViewHolder extends RecyclerView.ViewHolder {

        TextView recomBt1, recomBt2, recomBt3, recomBt4, recomBt5;

        public SimulAllTabEmptyViewHolder(@NonNull View itemView) {
            super(itemView);

            recomBt1 = itemView.findViewById(R.id.recomBt1);
            recomBt2 = itemView.findViewById(R.id.recomBt2);
            recomBt3 = itemView.findViewById(R.id.recomBt3);
            recomBt4 = itemView.findViewById(R.id.recomBt4);
            recomBt5 = itemView.findViewById(R.id.recomBt5);
        }
    }

}
