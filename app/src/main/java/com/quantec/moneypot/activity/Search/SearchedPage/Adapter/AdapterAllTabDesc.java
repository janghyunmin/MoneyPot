package com.quantec.moneypot.activity.Search.SearchedPage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.quantec.moneypot.datamodel.dmodel.ModelDescItem;
import com.quantec.moneypot.R;
import com.quantec.moneypot.databinding.ItemAlltabDescBinding;

public class AdapterAllTabDesc extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int PORTNUMPOSITION = 0;
    private final int PORTDESCPOSITION = 1;
    private final int PORTADDVIEWPOSITION = 2;

    ArrayList<ModelDescItem> descItemModels;
    Context context;

    public AdapterAllTabDesc(ArrayList<ModelDescItem> descItemModels, Context context) {
        this.descItemModels = descItemModels;
        this.context = context;
    }

    private DescItemClick descItemClick;
    public interface DescItemClick {
        public void onClick(int position);
    }

    public void setDescItemClick(DescItemClick descItemClick) {
        this.descItemClick = descItemClick;
    }

    private DescZzimClick descZzimClick;
    public interface DescZzimClick{
        public void onClick(int position);
    }

    public void setDescZzimClick(DescZzimClick descZzimClick) {
        this.descZzimClick = descZzimClick;
    }

    private DescAddviewClick descAddviewClick;
    public interface DescAddviewClick {
        public void onClick(int position);
    }

    public void setDescAddviewClick(DescAddviewClick descAddviewClick) {
        this.descAddviewClick = descAddviewClick;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return PORTNUMPOSITION;
        }else if(position == descItemModels.size()-1){
            return PORTADDVIEWPOSITION;
        }else{
            return PORTDESCPOSITION;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == PORTNUMPOSITION){
            return new DescNumViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_number, parent, false));
        }else if(viewType == PORTADDVIEWPOSITION){
            return new DescAddViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_clickbt, parent, false));
        }else{

            return new DescViewHolder(ItemAlltabDescBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DescNumViewHolder) {

            String num = "포트 내용 "+descItemModels.get(position).getTotalNum()+"건";
            ((DescNumViewHolder) holder).number.setText(num);

        }

        if(holder instanceof DescAddViewHolder) {

            ((DescAddViewHolder)holder).allPage_tab_desc_addView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(descAddviewClick !=null) {
                        descAddviewClick.onClick(position);
                    }
                }
            });

        }

        if(holder instanceof DescViewHolder) {

            ((DescViewHolder) holder).binding.name.setText(descItemModels.get(position).getName());
            ((DescViewHolder) holder).binding.desc.setText(descItemModels.get(position).getDesc());
            ((DescViewHolder) holder).binding.rate.setText(String.valueOf(descItemModels.get(position).getRate()));

            if(!descItemModels.get(position).isZim()) {
                ((DescViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_off);
            }else{
                ((DescViewHolder) holder).binding.checkImage.setBackgroundResource(R.drawable.start_on);
            }

            if(descItemModels.get(position).getRate() < 0) {
                ((DescViewHolder) holder).binding.rate.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                ((DescViewHolder) holder).binding.per.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
            }else{
                ((DescViewHolder) holder).binding.rate.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((DescViewHolder) holder).binding.per.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }


            ((DescViewHolder) holder).binding.tabLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(descItemClick != null) {
                        descItemClick.onClick(position);
                    }
                }
            });

            ((DescViewHolder) holder).binding.checkBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(descZzimClick != null) {
                        descZzimClick.onClick(position);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return descItemModels.size();
    }

    public static class DescNumViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        public DescNumViewHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
        }
    }

    public static class DescViewHolder extends RecyclerView.ViewHolder {
        ItemAlltabDescBinding binding;

        public DescViewHolder(ItemAlltabDescBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }

    public static class DescAddViewHolder extends RecyclerView.ViewHolder {
        TextView allPage_tab_desc_addView;
        public DescAddViewHolder(View itemView) {
            super(itemView);
            allPage_tab_desc_addView = itemView.findViewById(R.id.allPage_tab_title_addView);
        }
    }
}
