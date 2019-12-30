package com.quantec.moneypot.activity.Main.Fragment.Tab2.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import com.quantec.moneypot.datamodel.dmodel.ModelFgAllPage;
import com.quantec.moneypot.R;
import com.quantec.moneypot.databinding.ItemAllpageFilterBinding;
import com.quantec.moneypot.databinding.ItemFgtab4Binding;


public class AdapterFollow extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final int VIEW_FILTER = 2;

    //아이템 클릭시 실행 함수
    private ItemClick itemClick;

    public interface ItemClick {
        public void onClick(int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }


    private SelectZzimClick selectZzimClick;
    public interface SelectZzimClick {
        public void onClick(int position);
    }

    //포트 찜 버튼 클릭
    public void setSelectZzimClick(SelectZzimClick selectZzimClick) {
        this.selectZzimClick = selectZzimClick;
    }


    private FilterBtClick filterBtClick;
    public interface FilterBtClick {
        public void onClick(int position);
    }

    public void setFilterBtClick(FilterBtClick filterBtClick) {
        this.filterBtClick = filterBtClick;
    }

    int checkedPosition;

    private ArrayList<ModelFgAllPage> myData;
    Context context;

    public AdapterFollow(ArrayList<ModelFgAllPage> myData, Context context) {
        this.myData = myData;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(myData.get(position).isEmpty()){
            return VIEW_PROG;
        }else{
            if(position == 0){
                return VIEW_FILTER;
            }else{
                return VIEW_ITEM;
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new MyViewHolder(ItemFgtab4Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        else if(viewType == VIEW_FILTER){
            return new FilterViewHolder(ItemAllpageFilterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        else {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fgtab4empty, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {

            ((MyViewHolder)holder).binding.potName.setText(myData.get(position).getTitle());
            ((MyViewHolder)holder).binding.potRate.setText(String.format("%.2f", myData.get(position).getRate()));

            if(myData.get(position).getRate() < 0) {
                ((MyViewHolder)holder).binding.potRate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((MyViewHolder)holder).binding.potPer.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((MyViewHolder)holder).binding.potRate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((MyViewHolder)holder).binding.potPer.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }
            if(myData.get(position).getFollow() == 0){
                ((MyViewHolder)holder).binding.potZimBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_off));
            }else{
                ((MyViewHolder)holder).binding.potZimBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_on));
            }

            //포트 클릭 이벤트
            ((MyViewHolder)holder).binding.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClick != null) {
                        itemClick.onClick(position);
                        checkedPosition = 4;
                    }
                }
            });

            ((MyViewHolder)holder).binding.potZimBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectZzimClick != null){
                        selectZzimClick.onClick(position);
                    }
                }
            });
        }

        else if(holder instanceof FilterViewHolder){
            ((FilterViewHolder)holder).binding.title.setText(myData.get(position).getFilterTitle());

            ((FilterViewHolder)holder).binding.filterBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(filterBtClick != null){
                        filterBtClick.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ItemFgtab4Binding binding;

        MyViewHolder(ItemFgtab4Binding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class FilterViewHolder extends RecyclerView.ViewHolder {

        ItemAllpageFilterBinding binding;
        public FilterViewHolder(@NonNull ItemAllpageFilterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View v) {
            super(v);
        }
    }

}
