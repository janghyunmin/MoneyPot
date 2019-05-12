package quantec.com.moneypot.Activity.DetailPort.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.DetailPort.ActivityDetailPort;
import quantec.com.moneypot.Activity.DetailPort.Model.dModel.ModelInvestItemData;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ItemActivitydetailportChartBinding;
import quantec.com.moneypot.databinding.ItemActivitydetailportInfoBinding;
import quantec.com.moneypot.databinding.ItemActivitydetailportInvestBinding;
import quantec.com.moneypot.databinding.ItemActivitydetailportRateBinding;

public class AdapterDetailPort extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //뷰아이템 겟수( 비율 , 차트 , 정보 ,투자 )
    ArrayList<Integer> DetailItem;

    private final int VIEW_RATE = 1;
    private final int VIEW_CHART = 2;
    private final int VIEW_INFO = 3;
    private final int VIEW_INVEST = 4;
    private final int VIEW_INVEST_CONST = 0;


    ArrayList<Double> Drate;
    ArrayList<String> infoItem;

    ArrayList<ModelInvestItemData> investItemData;
    ArrayList<ModelInvestItemData> investItemData5;


    Context context;
    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    float currentX;
    float maxX;

    //1 ~ 누적 버튼 클릭 포지션
    int checkedPosition;

    private DetailPageDur1 detailPageDur1;
    public interface DetailPageDur1 {
        public void onClick(int position);
    }

    public void setDetailPageDur1(DetailPageDur1 detailPageDur1) {
        this.detailPageDur1 = detailPageDur1;
    }

    private DetailPageDur3 detailPageDur3;
    public interface DetailPageDur3 {
        public void onClick(int position);
    }

    public void setDetailPageDur3(DetailPageDur3 detailPageDur3) {
        this.detailPageDur3 = detailPageDur3;
    }

    private DetailPageDur6 detailPageDur6;
    public interface DetailPageDur6 {
        public void onClick(int postion);
    }

    public void setDetailPageDur6(DetailPageDur6 detailPageDur6) {
        this.detailPageDur6 = detailPageDur6;
    }

    private DetailPageDura detailPageDura;
    public interface DetailPageDura {
        public void onClick(int position);
    }

    public void setDetailPageDura(DetailPageDura detailPageDura) {
        this.detailPageDura = detailPageDura;
    }

    private SelectedAddBT selectedAddBT;
    public interface SelectedAddBT {
        public void onClick(int position);
    }

    public void setSelectedAddBT(SelectedAddBT selectedAddBT) {
        this.selectedAddBT = selectedAddBT;
    }


    public AdapterDetailPort(ArrayList<Integer> detailItem, ArrayList<Double> drate, ArrayList<String> infoItem, ArrayList<ModelInvestItemData> investItemData,
                             ArrayList<ModelInvestItemData> investItemData5, Context context, List<Entry> entries, LineDataSet lineDataSet, LineData lineData) {
        DetailItem = detailItem;
        Drate = drate;
        this.infoItem = infoItem;
        this.investItemData = investItemData;
        this.investItemData5 = investItemData5;
        this.context = context;
        this.entries = entries;
        this.lineDataSet = lineDataSet;
        this.lineData = lineData;
    }

    @Override
    public int getItemViewType(int position) {
        if(DetailItem.get(position) == 0) {
            return VIEW_RATE;
        }else if(DetailItem.get(position) == 1){
            return VIEW_CHART;
        }else if(DetailItem.get(position) == 2){
            return VIEW_INFO;
        }
        else if(DetailItem.get(position) == 3) {
            return VIEW_INVEST_CONST;
        }
        else{
            return VIEW_INVEST;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_RATE) {
            return new DetailRate(ItemActivitydetailportRateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }else if(viewType == VIEW_CHART) {
            return new DetailChart(ItemActivitydetailportChartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }else if(viewType == VIEW_INFO){
            return new DetailInfo(ItemActivitydetailportInfoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        else if(viewType == VIEW_INVEST_CONST) {
            return new DetailInvestConst(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitydetailport_investconst, parent, false));
        }
        else{
            return new DetailInvest(ItemActivitydetailportInvestBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof DetailRate) {

            if(Drate.size() != 0)
                ((DetailRate) holder).portDetailPageRateBinding.portDetailPageRate.setText(String.format("%.2f",Drate.get(0)));

        }else if(holder instanceof DetailChart) {

            lineDataSet = new LineDataSet(entries, null);
            lineDataSet.setLineWidth(1.5f);
            lineDataSet.setColor(Color.parseColor("#FFFF0000"));
            lineDataSet.setDrawHorizontalHighlightIndicator(false);
            lineDataSet.setDrawValues(false);
            lineDataSet.setHighlightEnabled(true);
            lineDataSet.setDrawHighlightIndicators(true);
            lineDataSet.setDrawHorizontalHighlightIndicator(false);
            lineDataSet.setHighLightColor(R.color.chart_limit_line_color);
            lineDataSet.setHighlightLineWidth(1f);
            lineDataSet.setDrawCircles(false);

            lineData = new LineData(lineDataSet);
            lineData.setHighlightEnabled(true);

            XAxis xAxis = ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.getXAxis();
            xAxis.setDrawLabels(false);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);

            YAxis yAxis = ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.getAxisLeft();
            yAxis.setDrawLabels(false);
            yAxis.setDrawGridLines(false);
            yAxis.setDrawAxisLine(false);
            yAxis.setEnabled(false);

            YAxis yAxis1 = ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.getAxisRight();
            yAxis1.setDrawLabels(false);
            yAxis1.setDrawGridLines(false);
            yAxis1.setDrawAxisLine(false);
            yAxis1.setEnabled(false);

            Legend legend = ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.getLegend();
            legend.setEnabled(false);
            legend.setDrawInside(false);

            ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.setDescription(null);
            ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.setDrawGridBackground(false);
            ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.setData(lineData);
            ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.setDoubleTapToZoomEnabled(false);
            ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.setDrawGridBackground(false);
            ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.animateY(600, Easing.EasingOption.EaseInCubic);
            ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.setPinchZoom(false);
            ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.setScaleEnabled(false);
            ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.invalidate();

            maxX = ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.getXRange();


            CustomMarkerView marker = new CustomMarkerView(context,R.layout.item_chart_marker);
            marker.setChartView(((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart);
            ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChart.setMarker(marker);


            //차트 1개월 버튼 클릭
            ((DetailChart)holder).portDetailPageChartBinding.portDetailPageChartDur1Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailPageDur1 != null) {
                        detailPageDur1.onClick(position);
                        checkedPosition = 1;
                    }
                }
            });
            //차트 3개월 버튼 클릭
            ((DetailChart)holder).portDetailPageChartBinding.portDetailPageChartDur3Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailPageDur3 != null) {
                        detailPageDur3.onClick(position);
                        checkedPosition = 2;
                    }
                }
            });
            //차트 6개월 버튼 클릭
            ((DetailChart)holder).portDetailPageChartBinding.portDetailPageChartDur6Bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailPageDur6 != null) {
                        detailPageDur6.onClick(position);
                        checkedPosition = 3;
                    }
                }
            });
            //차트 누적 버튼 클릭
            ((DetailChart)holder).portDetailPageChartBinding.portDetailPageChartDuraBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailPageDura != null) {
                        detailPageDura.onClick(position);
                        checkedPosition = 4;
                    }
                }
            });


            if(checkedPosition == 1) {
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur1Bt.setBackgroundResource(R.drawable.round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur1Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 2){
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur3Bt.setBackgroundResource(R.drawable.round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur3Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 3){
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur6Bt.setBackgroundResource(R.drawable.round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur6Bt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDuraBt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDuraBt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
            }else if(checkedPosition == 4){
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur1Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur1Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur3Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur3Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur6Bt.setBackgroundResource(R.drawable.unselected_round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDur6Bt.setTextColor(context.getResources().getColor(R.color.chart_dur_bt_color));
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDuraBt.setBackgroundResource(R.drawable.round_button);
                ((DetailChart) holder).portDetailPageChartBinding.portDetailPageChartDuraBt.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
            }


        }else if(holder instanceof DetailInfo){

            if(infoItem.size() != 0) {
                ((DetailInfo) holder).portDetailPageInfoBinding.portDetailPageInfoCost.setText(infoItem.get(0));
                ((DetailInfo) holder).portDetailPageInfoBinding.portDetailPageInfoTip.setText(infoItem.get(1));
            }
        }
        else if(holder instanceof DetailInvest){

            if(ActivityDetailPort.more) {

                ((DetailInvest) holder).portDetailPageInvestBinding.investNam.setText(investItemData.get(position-4).getInvestName());
                if (investItemData.get(position-4).getInvestRate() >= 0) {
                    ((DetailInvest) holder).portDetailPageInvestBinding.investYiel.setText(String.valueOf(investItemData.get(position-4).getInvestRate()) + "%");
                    ((DetailInvest) holder).portDetailPageInvestBinding.investYiel.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                } else {
                    ((DetailInvest) holder).portDetailPageInvestBinding.investYiel.setText(String.valueOf(investItemData.get(position-4).getInvestRate()) + "%");
                    ((DetailInvest) holder).portDetailPageInvestBinding.investYiel.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                }

                if((position - 4) == (investItemData.size() - 1)){
                    ((DetailInvest) holder).portDetailPageInvestBinding.portDetailPageAddBt.setVisibility(View.VISIBLE);
                    ((DetailInvest) holder).portDetailPageInvestBinding.portDetailPageAddBt.setText("접기");
                }else{
                    ((DetailInvest) holder).portDetailPageInvestBinding.portDetailPageAddBt.setVisibility(View.GONE);
                }

            }else{

                ((DetailInvest) holder).portDetailPageInvestBinding.investNam.setText(investItemData5.get(position-4).getInvestName());
                if (investItemData5.get(position-4).getInvestRate() >= 0) {
                    ((DetailInvest) holder).portDetailPageInvestBinding.investYiel.setText(String.valueOf(investItemData5.get(position-4).getInvestRate()) + "%");
                    ((DetailInvest) holder).portDetailPageInvestBinding.investYiel.setTextColor(context.getResources().getColor(R.color.delete_pressed_text));
                } else {
                    ((DetailInvest) holder).portDetailPageInvestBinding.investYiel.setText(String.valueOf(investItemData5.get(position-4).getInvestRate()) + "%");
                    ((DetailInvest) holder).portDetailPageInvestBinding.investYiel.setTextColor(context.getResources().getColor(R.color.make_port_blue_color));
                }

                //포지션이 마지막에 위치할때
                if((position - 4) == (investItemData5.size() - 1)){
                    //5개 초과의 아이템이 있는경우
                    if(investItemData.size() > 5){
                        ((DetailInvest) holder).portDetailPageInvestBinding.portDetailPageAddBt.setVisibility(View.VISIBLE);
                        ((DetailInvest) holder).portDetailPageInvestBinding.portDetailPageAddBt.setText("더보기");
                    }
                    //5개 이하의 아이템이 있는경우 -> 마지막 아이템과 버튼 사이의 높이 때문에 GONE 이 아니라 INVISIBLE을 줌
                    else{
                        ((DetailInvest) holder).portDetailPageInvestBinding.portDetailPageAddBt.setVisibility(View.INVISIBLE);
                    }
                    //포지션이 마지막 위치가 아닐때
                }else{
                    ((DetailInvest) holder).portDetailPageInvestBinding.portDetailPageAddBt.setVisibility(View.GONE);
                }

            }

            ((DetailInvest) holder).portDetailPageInvestBinding.portDetailPageAddBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedAddBT != null) {
                        selectedAddBT.onClick(position-4);
                    }
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        if(ActivityDetailPort.more) {
            return DetailItem.size();
        }else{

            if(investItemData.size() > 5) {
                return DetailItem.size()-(investItemData.size()-5);
            }else{
                return DetailItem.size();
            }
        }
    }

    static class DetailRate extends RecyclerView.ViewHolder {
        ItemActivitydetailportRateBinding portDetailPageRateBinding;

        public DetailRate(ItemActivitydetailportRateBinding portDetailPageRateBinding) {
            super(portDetailPageRateBinding.getRoot());
            this.portDetailPageRateBinding = portDetailPageRateBinding;
        }

    }

    static class DetailChart extends RecyclerView.ViewHolder {
        ItemActivitydetailportChartBinding portDetailPageChartBinding;

        public DetailChart(ItemActivitydetailportChartBinding portDetailPageChartBinding) {
            super(portDetailPageChartBinding.getRoot());
            this.portDetailPageChartBinding = portDetailPageChartBinding;
        }
    }

    static class DetailInfo extends RecyclerView.ViewHolder {

        ItemActivitydetailportInfoBinding portDetailPageInfoBinding;

        public DetailInfo(ItemActivitydetailportInfoBinding portDetailPageInfoBinding) {
            super(portDetailPageInfoBinding.getRoot());
            this.portDetailPageInfoBinding = portDetailPageInfoBinding;
        }

    }

    static class DetailInvest extends RecyclerView.ViewHolder {

        ItemActivitydetailportInvestBinding portDetailPageInvestBinding;

        public DetailInvest(ItemActivitydetailportInvestBinding portDetailPageInvestBinding) {
            super(portDetailPageInvestBinding.getRoot());
            this.portDetailPageInvestBinding = portDetailPageInvestBinding;
        }
    }

    static class DetailInvestConst extends RecyclerView.ViewHolder {
        public DetailInvestConst(View itemView) {
            super(itemView);
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
