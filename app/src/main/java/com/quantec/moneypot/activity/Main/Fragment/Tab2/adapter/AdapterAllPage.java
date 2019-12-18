package com.quantec.moneypot.activity.Main.Fragment.Tab2.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AdapterAllPage extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


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

    private DeleteClick deleteClick;
    public interface DeleteClick {
        public void onClick(int position);
    }

    //아이템 삭제
    public void setDeleteClick(DeleteClick deleteClick) {
        this.deleteClick = deleteClick;
    }

    private SelectDur1ItemClick selectDurItemClick1;
    public interface SelectDur1ItemClick {
        public void onclick(int position);
    }

    //차트 1개월 버튼
    public void setSelectDurItemClick1(SelectDur1ItemClick selectDurItemClick1) {
        this.selectDurItemClick1 = selectDurItemClick1;
    }

    private SelectDur3ItemClick selectDurItemClick3;
    public interface SelectDur3ItemClick {
        public void onclick(int position);
    }

    //차트 3개월 버튼
    public void setSelectDurItemClick3(SelectDur3ItemClick selectDurItemClick3) {
        this.selectDurItemClick3 = selectDurItemClick3;
    }

    private SelectDur6ItemClick selectDurItemClick6;
    public interface SelectDur6ItemClick {
        public void onclick(int position);
    }

    //차트 6개월 버튼
    public void setSelectDurItemClick6(SelectDur6ItemClick selectDurItemClick6) {
        this.selectDurItemClick6 = selectDurItemClick6;
    }

    private SelectDuraItemClick selectDurItemClicka;
    public interface SelectDuraItemClick {
        public void onclick(int position);
    }

    //차트 누적 버튼
    public void setSelectDurItemClicka(SelectDuraItemClick selectDurItemClicka) {
        this.selectDurItemClicka = selectDurItemClicka;
    }

    private SelectAddPortDetailClick selectAddPortDetailClick;
    public interface SelectAddPortDetailClick {
        public void onclick(int position);
    }

    //포트 더보기 클릭
    public void setSelectAddPortDetailClick(SelectAddPortDetailClick selectAddPortDetailClick) {
        this.selectAddPortDetailClick = selectAddPortDetailClick;
    }

    private SelectInvestClick selectInvestClick;
    public interface SelectInvestClick {
        public void onClick(int position);
    }

    //포트 바로투자 클릭
    public void setSelectInvestClick(SelectInvestClick selectInvestClick) {
        this.selectInvestClick = selectInvestClick;
    }

    private SelectZzimClick selectZzimClick;
    public interface SelectZzimClick {
        public void onClick(int position);
    }

    //포트 찜 버튼 클릭
    public void setSelectZzimClick(SelectZzimClick selectZzimClick) {
        this.selectZzimClick = selectZzimClick;
    }


    private PotFollowBtClick potFollowBtClick;
    public interface PotFollowBtClick {
        public void onClick(int position);
    }

    public void setPotFollowBtClick(PotFollowBtClick potFollowBtClick) {
        this.potFollowBtClick = potFollowBtClick;
    }

    private FilterBtClick filterBtClick;
    public interface FilterBtClick {
        public void onClick(int position);
    }

    public void setFilterBtClick(FilterBtClick filterBtClick) {
        this.filterBtClick = filterBtClick;
    }

    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    XAxis xAxis;

    float currentX, maxX;
    int checkedPosition;

    private ArrayList<ModelFgAllPage> myData;
    Context context;

    public AdapterAllPage(ArrayList<ModelFgAllPage> myData, Context context) {
        this.myData = myData;
        this.context = context;
    }

    //    public AdapterAllPage(Context context, ArrayList<ModelFgAllPage> myData, List<Entry> entries, LineDataSet lineDataSet, LineData lineData){
//        this.context = context;
//        this.myData = myData;
//        this.entries = entries;
//        this.lineDataSet = lineDataSet;
//        this.lineData = lineData;
//    }

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

//            ((MyViewHolder)holder).binding.potFollow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(potFollowBtClick != null){
//                        potFollowBtClick.onClick(position);
//                    }
//                }
//            });
//
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
//
//            //포트 차트 1개월 버튼
//            ((MyViewHolder)holder).binding.m1Bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (selectDurItemClick1 != null) {
//                        selectDurItemClick1.onclick(position);
//                        checkedPosition = 1;
//                    }
//                }
//            });
//
//            //포트 차트 3개월 버튼
//            ((MyViewHolder)holder).binding.m3Bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (selectDurItemClick3 != null) {
//                        selectDurItemClick3.onclick(position);
//                        checkedPosition = 2;
//                    }
//                }
//            });
//
//            //포트 차트 6개월 버튼
//            ((MyViewHolder)holder).binding.m6Bt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (selectDurItemClick6 != null) {
//                        selectDurItemClick6.onclick(position);
//                        checkedPosition = 3;
//                    }
//                }
//            });
//
//            //포트 차트 누적 버튼
//            ((MyViewHolder)holder).binding.mAllBt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (selectDurItemClicka != null) {
//                        selectDurItemClicka.onclick(position);
//                        checkedPosition = 4;
//                    }
//                }
//            });
//
//            if(checkedPosition == 1) {
//                ((MyViewHolder)holder).binding.m1Bt.setBackgroundResource(R.drawable.round_button);
//                ((MyViewHolder)holder).binding.m1Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
//                ((MyViewHolder)holder).binding.m3Bt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.m3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//                ((MyViewHolder)holder).binding.m6Bt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.m6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//                ((MyViewHolder)holder).binding.mAllBt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.mAllBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//            }else if(checkedPosition == 2) {
//                ((MyViewHolder)holder).binding.m1Bt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.m1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//                ((MyViewHolder)holder).binding.m3Bt.setBackgroundResource(R.drawable.round_button);
//                ((MyViewHolder)holder).binding.m3Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
//                ((MyViewHolder)holder).binding.m6Bt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.m6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//                ((MyViewHolder)holder).binding.mAllBt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.mAllBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//            }else if(checkedPosition == 3) {
//                ((MyViewHolder)holder).binding.m1Bt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.m1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//                ((MyViewHolder)holder).binding.m3Bt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.m3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//                ((MyViewHolder)holder).binding.m6Bt.setBackgroundResource(R.drawable.round_button);
//                ((MyViewHolder)holder).binding.m6Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
//                ((MyViewHolder)holder).binding.mAllBt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.mAllBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//            }else if(checkedPosition == 4) {
//                ((MyViewHolder)holder).binding.m1Bt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.m1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//                ((MyViewHolder)holder).binding.m3Bt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.m3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//                ((MyViewHolder)holder).binding.m6Bt.setBackgroundResource(R.drawable.unselected_round_button);
//                ((MyViewHolder)holder).binding.m6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
//                ((MyViewHolder)holder).binding.mAllBt.setBackgroundResource(R.drawable.round_button);
//                ((MyViewHolder)holder).binding.mAllBt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
//            }
//
//            if(myData.get(position).isZim()){
//                ((MyViewHolder)holder).binding.potZimBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_on_red_48_px));
//            }else{
//                ((MyViewHolder)holder).binding.potZimBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_off_whitegray_48_px));
//            }
//
//            //포트 찜하기 버튼
//            ((MyViewHolder)holder).binding.potZimBt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(selectZzimClick != null) {
//                        selectZzimClick.onClick(position);
//                    }
//                }
//            });
//
//            if (myData.get(position).isOnenChart()) {
//                if (entries.size() > 0) {
//
//                    lineDataSet = new LineDataSet(entries, null);
//                    lineDataSet.setLineWidth(1.5f);
//                    lineDataSet.setColor(Color.parseColor("#FFFF0000"));
//                    lineDataSet.setDrawHorizontalHighlightIndicator(false);
//                    lineDataSet.setDrawValues(false);
//                    lineDataSet.setHighlightEnabled(true);
//                    lineDataSet.setDrawHighlightIndicators(true);
//                    lineDataSet.setDrawHorizontalHighlightIndicator(false);
//                    lineDataSet.setHighLightColor(R.color.chart_limit_line_color);
//                    lineDataSet.setHighlightLineWidth(1f);
//                    lineDataSet.setDrawCircles(false);
//
//                    lineData = new LineData(lineDataSet);
//                    lineData.setHighlightEnabled(true);
//
//                    xAxis = ((MyViewHolder)holder).binding.chartView.getXAxis();
//                    xAxis.setDrawLabels(false);
//                    xAxis.setDrawGridLines(false);
//                    xAxis.setDrawAxisLine(false);
//
//                    YAxis yAxis = ((MyViewHolder)holder).binding.chartView.getAxisLeft();
//                    yAxis.setDrawLabels(false);
//                    yAxis.setDrawGridLines(false);
//                    yAxis.setDrawAxisLine(false);
//                    yAxis.setEnabled(false);
//
//                    YAxis yAxis1 = ((MyViewHolder)holder).binding.chartView.getAxisRight();
//                    yAxis1.setDrawLabels(false);
//                    yAxis1.setDrawGridLines(false);
//                    yAxis1.setDrawAxisLine(false);
//                    yAxis1.setEnabled(false);
//
//                    Legend legend = ((MyViewHolder)holder).binding.chartView.getLegend();
//                    legend.setEnabled(false);
//                    legend.setDrawInside(false);
//
//                    ((MyViewHolder)holder).binding.chartView.setDescription(null);
//                    ((MyViewHolder)holder).binding.chartView.setDrawGridBackground(false);
//                    ((MyViewHolder)holder).binding.chartView.setData(lineData);
//                    ((MyViewHolder)holder).binding.chartView.setDoubleTapToZoomEnabled(false);
//                    ((MyViewHolder)holder).binding.chartView.setDrawGridBackground(false);
//                    ((MyViewHolder)holder).binding.chartView.animateY(600, Easing.EasingOption.EaseInCubic);
//                    ((MyViewHolder)holder).binding.chartView.setPinchZoom(false);
//                    ((MyViewHolder)holder).binding.chartView.setScaleEnabled(false);
//                    ((MyViewHolder)holder).binding.chartView.invalidate();
//
//                    maxX = ((MyViewHolder) holder).binding.chartView.getXRange();
//
//                    CustomMarkerView marker = new CustomMarkerView(context, R.layout.item_chart_marker);
//                    marker.setChartView(((MyViewHolder)holder).binding.chartView);
//                    ((MyViewHolder)holder).binding.chartView.setMarker(marker);
//
//                }
//
//                ((MyViewHolder)holder).binding.chartView.setVisibility(View.VISIBLE);
//            } else {
//                ((MyViewHolder)holder).binding.chartView.setVisibility(View.GONE);
//            }
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

    public class CustomMarkerView extends MarkerView {
        private TextView tvContent;
        private TextView tvContent2;
        public CustomMarkerView(Context context, int layoutResource){
            super(context, layoutResource);
            tvContent = findViewById(R.id.tvContent);
            tvContent2 = findViewById(R.id.tvContent2);
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            currentX = e.getX();
            if (e instanceof CandleEntry) {
                CandleEntry ce = (CandleEntry) e;
                String num = String.format("%.2f",e.getY());
                tvContent.setText(("" + ce.getData()).replace("-","."));
                tvContent2.setText(num+"%");
            } else {

                String num = String.format("%.2f",e.getY());
                tvContent.setText(("" + e.getData()).replace("-","."));
                tvContent2.setText(num+"%");
            }
            super.refreshContent(e, highlight);
        }

        @Override
        public MPPointF getOffset() {
            if(maxX/3 < currentX) {
                return new MPPointF(-(getWidth())-40, -getHeight()+80);
            }else{
                return new MPPointF((getWidth()/5), -getHeight()+80);
            }
        }

        @Override
        public void draw(Canvas canvas, float posX, float posY) {

            Paint paint = new Paint();
            paint.setColor(context.getResources().getColor(R.color.chart_point_color));
            paint.setStrokeWidth(5f);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(posX, posY,18, paint);
            super.draw(canvas, posX, posY);
        }
    }

}
