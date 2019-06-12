package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
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

import quantec.com.moneypot.DataModel.dModel.ModelPotCookAll;
import quantec.com.moneypot.R;

public class AdapterPotCookAll extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<ModelPotCookAll> modelPotCookAll;
    Context context;

    float currentX, maxX;
    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    public AdapterPotCookAll(ArrayList<ModelPotCookAll> modelPotCookAll, Context context, List<Entry> entries, LineDataSet lineDataSet, LineData lineData) {
        this.modelPotCookAll = modelPotCookAll;
        this.context = context;
        this.entries = entries;
        this.lineDataSet = lineDataSet;
        this.lineData = lineData;
    }

    private ShowChartBtClick showChartBtClick;
    public interface ShowChartBtClick {
        public void onClick(int position);
    }

    public void setShowChartBtClick(ShowChartBtClick showChartBtClick) {
        this.showChartBtClick = showChartBtClick;
    }

    private StAddBtClick stAddBtClick;
    public interface StAddBtClick {
        public void onClick(int position);
    }

    public void setStAddBtClick(StAddBtClick stAddBtClick) {
        this.stAddBtClick = stAddBtClick;
    }


    private PotCookAllItemClick potCookAllItemClick;
    public interface PotCookAllItemClick {
        public void onClick(int position);
    }

    public void setPotCookAllItemClick(PotCookAllItemClick potCookAllItemClick) {
        this.potCookAllItemClick = potCookAllItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PotCookAllViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_potcookall, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof PotCookAllViewHolder){

            ((PotCookAllViewHolder)holder).potCookAllItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(potCookAllItemClick != null){
                        potCookAllItemClick.onClick(position);
                    }
                }
            });


            ((PotCookAllViewHolder)holder).stTitle.setText(modelPotCookAll.get(position).getStTitle());
            ((PotCookAllViewHolder)holder).stCode.setText(modelPotCookAll.get(position).getStSubTitle());

            ((PotCookAllViewHolder)holder).chartBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(showChartBtClick != null){
                        showChartBtClick.onClick(position);
                    }
                }
            });

            if(modelPotCookAll.get(position).isOpenView()){
                ((PotCookAllViewHolder)holder).showChartView.setVisibility(View.VISIBLE);
            }else{
                ((PotCookAllViewHolder)holder).showChartView.setVisibility(View.GONE);
            }

            ((PotCookAllViewHolder)holder).stAddBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(stAddBtClick != null){
                        stAddBtClick.onClick(position);
                    }
                }
            });

            if(modelPotCookAll.get(position).isAddSt()){
                ((PotCookAllViewHolder)holder).stAddBt.setBackgroundResource(R.drawable.oval_red_line);
                ((PotCookAllViewHolder)holder).stAddBt.setImageDrawable(context.getResources().getDrawable(R.drawable.red_check));
                if(modelPotCookAll.get(position).isOpenView()){
                    ((PotCookAllViewHolder)holder).potCookAllItemView.setBackgroundResource(R.drawable.potcookall_open_red_view);
                }else{
                    ((PotCookAllViewHolder)holder).potCookAllItemView.setBackgroundResource(R.drawable.potcookall_red_view);
                }

            }else{
                ((PotCookAllViewHolder)holder).stAddBt.setBackgroundResource(R.drawable.oval_gray_line);
                ((PotCookAllViewHolder)holder).stAddBt.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_gray_24_dp));

                if(modelPotCookAll.get(position).isOpenView()){
                    ((PotCookAllViewHolder)holder).potCookAllItemView.setBackgroundResource(R.drawable.potcookall_open_gray_view);
                }else{
                    ((PotCookAllViewHolder)holder).potCookAllItemView.setBackgroundResource(R.drawable.potcookall_gray_view);
                }
            }


            if (modelPotCookAll.get(position).isOpenView()) {
                if (entries.size() > 0) {

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
                    lineDataSet.setCircleHoleRadius(5f);
                    lineDataSet.setDrawCircleHole(true);
                    lineDataSet.setDrawCircles(false);

                    lineDataSet.setLabel("");

                    lineData = new LineData(lineDataSet);
                    lineData.setHighlightEnabled(true);

                    XAxis xAxis = ((PotCookAllViewHolder)holder).chartView.getXAxis();
                    xAxis.setDrawLabels(false);
                    xAxis.setDrawGridLines(false);
                    xAxis.setDrawAxisLine(false);

                    YAxis yAxis = ((PotCookAllViewHolder)holder).chartView.getAxisLeft();
                    yAxis.setDrawLabels(false);
                    yAxis.setDrawGridLines(false);
                    yAxis.setDrawAxisLine(false);
                    yAxis.setEnabled(false);

                    YAxis yAxis1 = ((PotCookAllViewHolder)holder).chartView.getAxisRight();
                    yAxis1.setDrawLabels(false);
                    yAxis1.setDrawGridLines(false);
                    yAxis1.setDrawAxisLine(false);
                    yAxis1.setEnabled(false);

                    Legend legend = ((PotCookAllViewHolder)holder).chartView.getLegend();
                    legend.setEnabled(false);
                    legend.setDrawInside(false);

                    ((PotCookAllViewHolder)holder).chartView.setDescription(null);
                    ((PotCookAllViewHolder)holder).chartView.setDrawGridBackground(false);
                    ((PotCookAllViewHolder)holder).chartView.setData(lineData);
                    ((PotCookAllViewHolder)holder).chartView.setDoubleTapToZoomEnabled(false);
                    ((PotCookAllViewHolder)holder).chartView.setDrawGridBackground(false);
                    ((PotCookAllViewHolder)holder).chartView.animateY(600, Easing.EasingOption.EaseInCubic);
                    ((PotCookAllViewHolder)holder).chartView.setPinchZoom(false);
                    ((PotCookAllViewHolder)holder).chartView.setScaleEnabled(false);
                    ((PotCookAllViewHolder)holder).chartView.invalidate();

                    maxX = ((PotCookAllViewHolder)holder).chartView.getXRange();

                    CustomMarkerView marker = new CustomMarkerView(context, R.layout.item_chart_marker);
                    marker.setChartView((((PotCookAllViewHolder)holder).chartView));
                    ((PotCookAllViewHolder)holder).chartView.setMarker(marker);

                }
            }


        }
    }

    @Override
    public int getItemCount() {
        return modelPotCookAll.size();
    }

    public class PotCookAllViewHolder extends RecyclerView.ViewHolder {

        TextView stTitle, stCode;
        ImageView chartBt, stAddBt;
        ConstraintLayout showChartView, potCookAllItemView;
        LinearLayout allView;
        LineChart chartView;


        public PotCookAllViewHolder(View itemView) {
            super(itemView);

            stTitle = itemView.findViewById(R.id.stTitle);
            stCode = itemView.findViewById(R.id.stCode);
            chartBt = itemView.findViewById(R.id.chartBt);
            showChartView = itemView.findViewById(R.id.showChartView);
            allView = itemView.findViewById(R.id.allView);
            stAddBt = itemView.findViewById(R.id.stAddBt);
            potCookAllItemView = itemView.findViewById(R.id.potCookAllItemView);

            chartView = itemView.findViewById(R.id.chartView);

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
