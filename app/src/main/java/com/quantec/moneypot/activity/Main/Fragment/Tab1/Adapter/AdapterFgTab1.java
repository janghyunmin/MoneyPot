package com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.FgTab1;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.ModelYieldChart;

import java.util.ArrayList;
import java.util.List;

public class AdapterFgTab1 extends LoopingPagerAdapter<ModelYieldChart> {
    private final int VIEWPAGE_1 = 0;
    private final int VIEWPAGE_2 = 1;
    private final int VIEWPAGE_3 = 2;

    ArrayList<ModelYieldChart> itemList;
    Context context;

    public AdapterFgTab1(Context context, ArrayList<ModelYieldChart> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
        this.itemList = itemList;
        this.context = context;
    }

    private ItemClick itemClick;

    public interface ItemClick{
        public void onClick(String code);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    protected int getItemViewType(int listPosition) {

        if (itemList.get(listPosition).getType() == 0) {
            return VIEWPAGE_1;
        } else if(itemList.get(listPosition).getType() == 1){
            return VIEWPAGE_2;
        }else{
            return VIEWPAGE_3;
        }
    }

    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {

        if (viewType == VIEWPAGE_1) {
            return LayoutInflater.from(context).inflate(R.layout.item_fgtab1_yieldpage0, container, false);
        } else if(viewType == VIEWPAGE_2){
            return LayoutInflater.from(context).inflate(R.layout.item_fgtab1_yieldpage1, container, false);
        }else{
            return LayoutInflater.from(context).inflate(R.layout.item_fgtab1_yieldpage2, container, false);
        }
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }

    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {

        TextView topTitle, title1, title2, title3, rate1, rate2, rate3, price1, price2, price3;
        ConstraintLayout itemLayout;

        if (viewType == VIEWPAGE_1) {

            topTitle = convertView.findViewById(R.id.topTitle);
            title1 = convertView.findViewById(R.id.title1);
            title2 = convertView.findViewById(R.id.title2);
            title3 = convertView.findViewById(R.id.title3);

            rate1 = convertView.findViewById(R.id.rate1);
            rate2 = convertView.findViewById(R.id.rate2);
            rate3 = convertView.findViewById(R.id.rate3);

            price1 = convertView.findViewById(R.id.price1);
            price2 = convertView.findViewById(R.id.price2);
            price3 = convertView.findViewById(R.id.price3);


            topTitle.setText("해외기업 차트");
            title1.setText(itemList.get(listPosition).getTitle1());
            title2.setText(itemList.get(listPosition).getTitle2());
            title3.setText(itemList.get(listPosition).getTitle3());

            rate1.setText(String.valueOf(itemList.get(listPosition).getRate1()));
            rate2.setText(String.valueOf(itemList.get(listPosition).getRate2()));
            rate3.setText(String.valueOf(itemList.get(listPosition).getRate3()));

            price1.setText(String.valueOf(itemList.get(listPosition).getPrice1()));
            price2.setText(String.valueOf(itemList.get(listPosition).getPrice2()));
            price3.setText(String.valueOf(itemList.get(listPosition).getPrice3()));

            title1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick != null){
                        itemClick.onClick(itemList.get(listPosition).getCode1());
                    }
                }
            });


            title2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick != null){
                        itemClick.onClick(itemList.get(listPosition).getCode2());
                    }
                }
            });

            title3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick != null){
                        itemClick.onClick(itemList.get(listPosition).getCode3());
                    }
                }
            });
        }

        if (viewType == VIEWPAGE_2) {
            topTitle = convertView.findViewById(R.id.topTitle);
            title1 = convertView.findViewById(R.id.title1);
            title2 = convertView.findViewById(R.id.title2);
            title3 = convertView.findViewById(R.id.title3);

            rate1 = convertView.findViewById(R.id.rate1);
            rate2 = convertView.findViewById(R.id.rate2);
            rate3 = convertView.findViewById(R.id.rate3);

            price1 = convertView.findViewById(R.id.price1);
            price2 = convertView.findViewById(R.id.price2);
            price3 = convertView.findViewById(R.id.price3);

            topTitle.setText("묶음기업 차트");
            title1.setText(itemList.get(listPosition).getTitle1());
            title2.setText(itemList.get(listPosition).getTitle2());
            title3.setText(itemList.get(listPosition).getTitle3());

            rate1.setText(String.valueOf(itemList.get(listPosition).getRate1()));
            rate2.setText(String.valueOf(itemList.get(listPosition).getRate2()));
            rate3.setText(String.valueOf(itemList.get(listPosition).getRate3()));

            price1.setText(String.valueOf(itemList.get(listPosition).getPrice1()));
            price2.setText(String.valueOf(itemList.get(listPosition).getPrice2()));
            price3.setText(String.valueOf(itemList.get(listPosition).getPrice3()));


            title1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick != null){
                        itemClick.onClick(itemList.get(listPosition).getCode1());
                    }
                }
            });


            title2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick != null){
                        itemClick.onClick(itemList.get(listPosition).getCode2());
                    }
                }
            });

            title3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick != null){
                        itemClick.onClick(itemList.get(listPosition).getCode3());
                    }
                }
            });
        }

        if(viewType == VIEWPAGE_3){
            topTitle = convertView.findViewById(R.id.topTitle);
            title1 = convertView.findViewById(R.id.title1);
            title2 = convertView.findViewById(R.id.title2);
            title3 = convertView.findViewById(R.id.title3);

            rate1 = convertView.findViewById(R.id.rate1);
            rate2 = convertView.findViewById(R.id.rate2);
            rate3 = convertView.findViewById(R.id.rate3);

            price1 = convertView.findViewById(R.id.price1);
            price2 = convertView.findViewById(R.id.price2);
            price3 = convertView.findViewById(R.id.price3);

            topTitle.setText("S&P500을 이기고 있는 기업들");
            title1.setText(itemList.get(listPosition).getTitle1());
            title2.setText(itemList.get(listPosition).getTitle2());
            title3.setText(itemList.get(listPosition).getTitle3());

            rate1.setText(String.valueOf(itemList.get(listPosition).getRate1()));
            rate2.setText(String.valueOf(itemList.get(listPosition).getRate2()));
            rate3.setText(String.valueOf(itemList.get(listPosition).getRate3()));

            price1.setText(String.valueOf(itemList.get(listPosition).getPrice1()));
            price2.setText(String.valueOf(itemList.get(listPosition).getPrice2()));
            price3.setText(String.valueOf(itemList.get(listPosition).getPrice3()));



            title1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick != null){
                        itemClick.onClick(itemList.get(listPosition).getCode1());
                    }
                }
            });


            title2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick != null){
                        itemClick.onClick(itemList.get(listPosition).getCode2());
                    }
                }
            });

            title3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClick != null){
                        itemClick.onClick(itemList.get(listPosition).getCode3());
                    }
                }
            });
        }

    }
}
