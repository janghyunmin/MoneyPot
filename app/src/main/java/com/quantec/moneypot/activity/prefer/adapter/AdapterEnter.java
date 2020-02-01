package com.quantec.moneypot.activity.prefer.adapter;

import android.content.Context;
import android.util.Log;
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
import com.quantec.moneypot.activity.prefer.ModelEnterList;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdapterEnter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int MORE = 0;
    private final int CATE = 2;
    private final int CATE2 = 3;
    private final int GAP = 4;

    ArrayList<ModelEnterList> modelEnterLists;
    Context context;

    public AdapterEnter(ArrayList<ModelEnterList> modelEnterLists, Context context) {
        this.modelEnterLists = modelEnterLists;
        this.context = context;
    }


    private SectorItemClick sectorItemClick;
    public interface SectorItemClick {
        public void onClick(int position);
    }

    public void setSectorItemClick(SectorItemClick sectorItemClick) {
        this.sectorItemClick = sectorItemClick;
    }

    private EnterMoreClick enterMoreClick;
    public interface EnterMoreClick{
        public void onClick(int position);
    }

    public void setEnterMoreClick(EnterMoreClick enterMoreClick) {
        this.enterMoreClick = enterMoreClick;
    }

    private EnterCate2Click enterCate2Click;
    public interface EnterCate2Click{
        public void onClick(int position);
    }

    public void setEnterCate2Click(EnterCate2Click enterCate2Click) {
        this.enterCate2Click = enterCate2Click;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 1){
            return new EnterEndPointViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sectorendpoint, parent, false));
        }
        else if(viewType == MORE){
            return new EnterMoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enter_more, parent, false));
        }
        else if(viewType == GAP){
            return new EnterGapViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enter_gap, parent, false));
        }
        else if(viewType == CATE){
            return new EnterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enter, parent, false));
        }else{
            return new EnterCate2ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enter_cate2, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof EnterViewHolder){

            ((EnterViewHolder)holder).title.setText(modelEnterLists.get(position).getTitle());
            Log.e("아이콘","값 : "+modelEnterLists.get(position).getImage());
            int resource = context.getResources().getIdentifier("ci_"+modelEnterLists.get(position).getImage(), "drawable", context.getPackageName());
//            ((EnterViewHolder)holder).image.setImageDrawable(context.getResources().getDrawable(resource));

            if(modelEnterLists.get(position).isSelect()){

                ((EnterViewHolder)holder).imageSelected.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_selected));

            }else{
                ((EnterViewHolder)holder).imageSelected.setImageDrawable(null);
            }

            RxView.clicks(((EnterViewHolder)holder).image).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(sectorItemClick != null){
                    sectorItemClick.onClick(position);
                }
            });
        }

        if(holder instanceof EnterEndPointViewHolder){
        }

        if(holder instanceof EnterCate2ViewHolder){
            if(modelEnterLists.get(0).isVisible()){
                ((EnterCate2ViewHolder)holder).moreLayout.setVisibility(View.VISIBLE);
            }

            if(modelEnterLists.get(position).isCate2Click()){
                ((EnterCate2ViewHolder)holder).clickBt.setText("선택완료");
                ((EnterCate2ViewHolder)holder).clickBt.setTextColor(context.getResources().getColor(R.color.c_cccccc));
                ((EnterCate2ViewHolder)holder).clickBt.setBackground(context.getResources().getDrawable(R.drawable.custom_enter_click));
            }else{
                ((EnterCate2ViewHolder)holder).clickBt.setText("+ 선택");
                ((EnterCate2ViewHolder)holder).clickBt.setTextColor(context.getResources().getColor(R.color.c_ffffff));
                ((EnterCate2ViewHolder)holder).clickBt.setBackground(context.getResources().getDrawable(R.drawable.custom_enter_nonclick));
            }

            RxView.clicks(((EnterCate2ViewHolder)holder).clickBt).throttleFirst(1200, TimeUnit.MILLISECONDS).subscribe(empty -> {
                if(enterCate2Click != null){
                    enterCate2Click.onClick(position);
                }
            });

            ((EnterCate2ViewHolder)holder).title.setText(modelEnterLists.get(position).getTitle());
            ((EnterCate2ViewHolder)holder).code.setText(modelEnterLists.get(position).getCode());
        }

        if(holder instanceof EnterMoreViewHolder){
            ((EnterMoreViewHolder)holder).moreBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(enterMoreClick != null){
                        enterMoreClick.onClick(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelEnterLists.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(modelEnterLists.get(position).isEndPoint()){
            return 1;
        }
        else{
            if(modelEnterLists.get(position).getCategory() == MORE){
                return MORE;
            }else if(modelEnterLists.get(position).getCategory() == GAP){
                return GAP;
            }
            else if(modelEnterLists.get(position).getCategory() == CATE){
                return CATE;
            }else{
                return CATE2;
            }
        }
    }

    public class EnterViewHolder extends RecyclerView.ViewHolder {

        ImageView image, imageSelected;
        TextView title;
        ConstraintLayout itemLayout;

        public EnterViewHolder(@NonNull View itemView) {
            super(itemView);

            imageSelected = itemView.findViewById(R.id.imageSelected);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);

            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }

    public class EnterEndPointViewHolder extends RecyclerView.ViewHolder {
        public EnterEndPointViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class EnterCate2ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout moreLayout;
        TextView clickBt, title, code;
        ImageView image;

        public EnterCate2ViewHolder(@NonNull View itemView) {
            super(itemView);

            moreLayout = itemView.findViewById(R.id.moreLayout);
            clickBt = itemView.findViewById(R.id.clickBt);

            title = itemView.findViewById(R.id.title);
            code = itemView.findViewById(R.id.code);

            image = itemView.findViewById(R.id.image);
        }
    }

    public class EnterMoreViewHolder extends RecyclerView.ViewHolder {

        TextView moreBt;

        public EnterMoreViewHolder(@NonNull View itemView) {
            super(itemView);

            moreBt = itemView.findViewById(R.id.moreBt);
        }
    }

    public class EnterGapViewHolder extends RecyclerView.ViewHolder {
        public EnterGapViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
