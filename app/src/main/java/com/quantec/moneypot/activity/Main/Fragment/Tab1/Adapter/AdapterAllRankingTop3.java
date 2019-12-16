package com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.quantec.moneypot.activity.PotDetail.ActivitySingleDetail;
import com.quantec.moneypot.datamodel.dmodel.ModelAllRankingTop3;
import com.quantec.moneypot.R;
import com.quantec.moneypot.rxandroid.RxView;

public class AdapterAllRankingTop3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ModelAllRankingTop3> modelAllRankingTop3s;
    Context context;

    public AdapterAllRankingTop3(ArrayList<ModelAllRankingTop3> modelAllRankingTop3s, Context context) {
        this.modelAllRankingTop3s = modelAllRankingTop3s;
        this.context = context;
    }

    private AllRankingTop3ItemClick allRankingTop3ItemClick;
    public interface AllRankingTop3ItemClick {
        public void onClick(int position);
    }

    public void setAllRankingTop3ItemClick(AllRankingTop3ItemClick allRankingTop3ItemClick) {
        this.allRankingTop3ItemClick = allRankingTop3ItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllRankingTop3ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potmarket_allrankingtop3, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof AllRankingTop3ViewHolder){

            ((AllRankingTop3ViewHolder)holder).image.setImageDrawable(modelAllRankingTop3s.get(position).getImage());
            ((AllRankingTop3ViewHolder)holder).title.setText(modelAllRankingTop3s.get(position).getTitle());
            ((AllRankingTop3ViewHolder)holder).rate.setText(String.valueOf(modelAllRankingTop3s.get(position).getRate()));
            if(modelAllRankingTop3s.get(position).getRate() < 0){
                ((AllRankingTop3ViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.blue_color));
                ((AllRankingTop3ViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.blue_color));
            }else{
                ((AllRankingTop3ViewHolder)holder).rate.setTextColor(context.getResources().getColor(R.color.red_text_color));
                ((AllRankingTop3ViewHolder)holder).per.setTextColor(context.getResources().getColor(R.color.red_text_color));
            }

//            ((AllRankingTop3ViewHolder)holder).itemLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(allRankingTop3ItemClick != null){
//                        allRankingTop3ItemClick.onClick(position);
//                    }
//                }
//            });

            RxView.clicks(((AllRankingTop3ViewHolder)holder).itemLayout).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(empty -> {
                Intent intent1 = new Intent(context, ActivitySingleDetail.class);
                context.startActivity(intent1);
                Toast.makeText(context.getApplicationContext(), "상세페이지 이동 : " + modelAllRankingTop3s.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Log.e("클릭","클릭");
            });

        }
    }

    @Override
    public int getItemCount() {
        return modelAllRankingTop3s.size();
    }




    public class AllRankingTop3ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, rate, per;
        ConstraintLayout itemLayout;

        public AllRankingTop3ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            rate = itemView.findViewById(R.id.rate);
            per = itemView.findViewById(R.id.per);

            itemLayout = itemView.findViewById(R.id.itemLayout);

        }
    }
}
