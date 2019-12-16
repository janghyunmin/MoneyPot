package com.quantec.moneypot.activity.Search.SearchedPage.Adapter;

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
import com.quantec.moneypot.activity.Search.SearchedPage.ModelPreSumEn;
import com.quantec.moneypot.activity.Search.SearchedPage.ModelSumEn;

import java.util.ArrayList;

public class AdapterAllTabSumEn extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int EMPTY = 0;
    private int TOP = 1;
    private int ITEM = 2;
    private int BOTTOM = 3;

    ArrayList<ModelPreSumEn> modelPreSumEn;
    Context context;

    public AdapterAllTabSumEn(ArrayList<ModelPreSumEn> modelPreSumEn, Context context) {
        this.modelPreSumEn = modelPreSumEn;
        this.context = context;
    }

    private SumEnFollowBt sumEnFollowBt;
    public interface SumEnFollowBt{
        public void onClick(int position);
    }

    public void setSumEnFollowBt(SumEnFollowBt sumEnFollowBt) {
        this.sumEnFollowBt = sumEnFollowBt;
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
            return new AllTabEmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_number, parent, false));
        }
        else if(viewType == TOP){
            return new AllTabSumEnTopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_sumentop, parent, false));
        }
        else if(viewType == BOTTOM){
            return new AllTabSumEnBottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_sumenbottom, parent, false));
        }
        else{
            return new AllTabSumEnViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alltab_sumenitem, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AllTabSumEnViewHolder){
            ((AllTabSumEnViewHolder)holder).title.setText(modelPreSumEn.get(position).getTitle());
            ((AllTabSumEnViewHolder)holder).stock.setText(modelPreSumEn.get(position).getCode());

            ((AllTabSumEnViewHolder)holder).rate.setText(String.format("%.2f", modelPreSumEn.get(position).getRate()));
            if(modelPreSumEn.get(position).getRate() < 0){
                ((AllTabSumEnViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((AllTabSumEnViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((AllTabSumEnViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((AllTabSumEnViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

            if(modelPreSumEn.get(position).getFollow() == 0){
                ((AllTabSumEnViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_off));
            }else{
                ((AllTabSumEnViewHolder)holder).followBt.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_star_on));
            }

            ((AllTabSumEnViewHolder)holder).followBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sumEnFollowBt != null){
                        sumEnFollowBt.onClick(position);
                    }
                }
            });
        }

        if(holder instanceof AllTabSumEnTopViewHolder){
            ((AllTabSumEnTopViewHolder)holder).num.setText("묶음기업 "+modelPreSumEn.get(position).getTotal()+"건");
        }

        if(holder instanceof AllTabEmptyViewHolder){

        }

        if(holder instanceof AllTabSumEnBottomViewHolder){

            ((AllTabSumEnBottomViewHolder)holder).detailBt.setOnClickListener(new View.OnClickListener() {
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
        if(modelPreSumEn.size()==0){
            return EMPTY;
        }
        else{

            if(position == 0){
                return TOP;
            }else if(modelPreSumEn.get(position).getTotal() > 5 && modelPreSumEn.size()-1 == position){
                return BOTTOM;
            }else{
                return ITEM;
            }
        }
    }

    @Override
    public int getItemCount() {
        return modelPreSumEn.size();
    }

    public class AllTabSumEnViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout itemLayout;
        TextView title, stock, rate, per;
        ImageView followBt;

        public AllTabSumEnViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.itemLayout);
            title = itemView.findViewById(R.id.title);
            stock = itemView.findViewById(R.id.stock);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);
            followBt = itemView.findViewById(R.id.followBt);

        }
    }

    public class AllTabSumEnTopViewHolder extends RecyclerView.ViewHolder {

        TextView num;

        public AllTabSumEnTopViewHolder(@NonNull View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
        }
    }

    public class AllTabEmptyViewHolder extends RecyclerView.ViewHolder {
        public AllTabEmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class AllTabSumEnBottomViewHolder extends RecyclerView.ViewHolder {

        TextView detailBt;

        public AllTabSumEnBottomViewHolder(@NonNull View itemView) {
            super(itemView);

            detailBt = itemView.findViewById(R.id.detailBt);

        }
    }


}
