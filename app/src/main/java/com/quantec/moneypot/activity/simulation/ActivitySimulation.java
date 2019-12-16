package com.quantec.moneypot.activity.simulation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelPreChartList;
import com.quantec.moneypot.datamanager.ChartManager;
import com.quantec.moneypot.util.DecimalScale.DecimalScale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

public class ActivitySimulation extends AppCompatActivity {

    ArrayList<ModelPreChartList> modelPreChartLists;

    TextView rate, per, num;
    ChipGroup chipGroup;
    ImageView backBt;

    SpannableStringBuilder sp1, sp2;

    LineChart chartView;
    List<Entry> entries;
    LineDataSet lineDataSet2;
    LineData lineData2;
    float currentX, maxX, chartExp;
    List<DateMathDtoResult> resultDate;
    List<Entry> monThEntries;
    double exp = 0.0;

    ArrayList<DateMathDto> date;

    String stCode, nowDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        entries = new ArrayList<>();
        date = new ArrayList<>();
        resultDate = new ArrayList<>();
        monThEntries = new ArrayList<>();

        chartView = findViewById(R.id.chartView);
        chartView.setNoDataText("");
        chartView.setNoDataTextColor(R.color.text_white_color);
        chartView.invalidate();

        backBt = findViewById(R.id.backBt);

        modelPreChartLists = new ArrayList<>();
        rate = findViewById(R.id.rate);
        per = findViewById(R.id.per);
        num = findViewById(R.id.num);

        chipGroup = findViewById(R.id.chipGroup);

        Intent intent1 = getIntent();
        rate.setText(String.valueOf(intent1.getDoubleExtra("rate", 0)));

        if(intent1.getDoubleExtra("rate", 0) < 0){
            rate.setTextColor(getResources().getColor(R.color.blue_color));
            per.setTextColor(getResources().getColor(R.color.blue_color));

        }else{
            rate.setTextColor(getResources().getColor(R.color.red_text_color));
            per.setTextColor(getResources().getColor(R.color.red_text_color));
        }

        modelPreChartLists = intent1.getParcelableArrayListExtra("chartData");
        for(ModelPreChartList data : modelPreChartLists){

            sp1 = new SpannableStringBuilder(data.getName());
            sp1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.c_3d3f42)), 0, data.getName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            sp2 = new SpannableStringBuilder(String.valueOf(data.getRate()));
            if(data.getRate() < 0){
                sp2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.c_4e7cff)), 0, String.valueOf(data.getRate()).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }else{
                sp2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.c_f02654)), 0, String.valueOf(data.getRate()).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            sp1.append("  ");
            sp1.append(sp2);

            Chip chip = new Chip(this);
            chip.setTextSize(15f);
            chip.setText(sp1);
            chip.setChipBackgroundColorResource(R.color.text_white_color);
            chip.setChipStrokeWidth(1f);
            chip.setChipStrokeColorResource(R.color.gray_brown_color);
//            chip.setChipStartPadding(16f);
//            chip.setChipEndPadding(16f);
//            chip.setPadding(25, 15, 25, 15);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "칩 클릭 함", Toast.LENGTH_SHORT).show();
                }
            });

            chipGroup.addView(chip);
        }
        num.setText(String.valueOf(modelPreChartLists.size()));

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        for(int xCount = 0; xCount < ChartManager.get_Instance().getTransChartLists().size() ; xCount++) {

            date.add(new DateMathDto(ChartManager.get_Instance().getTransChartLists().get(xCount).getDate(),
                    ChartManager.get_Instance().getTransChartLists().get(xCount).getPrice(),
                    ChartManager.get_Instance().getTransChartLists().get(xCount).getRate()));

//            entries.add(new Entry(xCount,
//                    DecimalScale.decimalScale2(String.valueOf(ChartManager.get_Instance().getTransChartLists().get(xCount).getRate()*100), 2, 2), ChartManager.get_Instance().getTransChartLists().get(xCount).getDate()
//
            entries.add(new Entry(xCount,
                    DecimalScale.decimalScale2(String.valueOf(ChartManager.get_Instance().getTransChartLists().get(xCount).getRate()), 2, 2), ChartManager.get_Instance().getTransChartLists().get(xCount).getDate()

            ));
        }

//        chartExp = DecimalScale.decimalScale2(String.valueOf(date.get(date.size()-1).getRate()*100), 2, 2);
        chartExp = DecimalScale.decimalScale2(String.valueOf(date.get(date.size()-1).getRate()), 2, 2);

        //최근 날짜 받음 -> 몇개월뒤 날짜 계산을 위해서
        nowDate = date.get(date.size()-1).getDate();
        drawLineChart(entries);

    }//onCreate 끝


    private String startDate(String endDate, int month){
        exp = 0.0;
        resultDate.clear();
        monThEntries.clear();

        String laterDay = null;
        try {
            Calendar cal = Calendar.getInstance();
            Date originDate = new Date();
            Date laterDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //날짜 형식에 따라서 달리 할 수 있다.
            originDate = format.parse(endDate);
            cal.setTime(laterDate);
            cal.add(Calendar.MONTH, month);        //laterCnt 만큼 후의 날짜를 구한다. laterCnt 자리에 -7 을 입력하면 일주일전에 날짜를 구할 수 있다.
            laterDate = cal.getTime();
            laterDay = format.format(laterDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return laterDay;
    }

    private void calDate(String startDate, boolean monTh){
        for(int index = 0 ; index < date.size(); index++){
            if(monTh){
                exp += (date.get(index).getPrice() / date.get(index-1).getPrice()) -1;
                resultDate.add(new DateMathDtoResult(date.get(index).getDate(), exp));
            }
            if(date.get(index).getDate().equals(startDate) && !monTh){
                monTh =true;
                resultDate.add(new DateMathDtoResult(date.get(index).getDate(), exp));
            }
        }
        for(int index = 0 ; index < resultDate.size() ; index++){
//            monThEntries.add(new Entry(index, DecimalScale.decimalScale2(String.valueOf(resultDate.get(index).getExp()*100), 2, 2),
//                    resultDate.get(index).getDate()));
            monThEntries.add(new Entry(index, DecimalScale.decimalScale2(String.valueOf(resultDate.get(index).getExp()), 2, 2),
                    resultDate.get(index).getDate()));
        }

//        chartExp = DecimalScale.decimalScale2(String.valueOf(resultDate.get(resultDate.size()-1).getExp()*100), 2, 2);
        chartExp = DecimalScale.decimalScale2(String.valueOf(resultDate.get(resultDate.size()-1).getExp()), 2, 2);
    }


    private void drawLineChart(List<Entry> chartData){

        lineDataSet2 = new LineDataSet(chartData, null);
        lineDataSet2.setLineWidth(1.5f);
        lineDataSet2.setColor(Color.parseColor("#FFFF0000"));
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawHorizontalHighlightIndicator(false);
        lineDataSet2.setDrawValues(false);
        lineDataSet2.setHighlightEnabled(true);
        lineDataSet2.setDrawHighlightIndicators(true);
        lineDataSet2.setDrawHorizontalHighlightIndicator(false);
        lineDataSet2.setHighLightColor(R.color.chart_limit_line_color);
        lineDataSet2.setHighlightLineWidth(1f);

        lineData2 = new LineData(lineDataSet2);

        XAxis xAxis = chartView.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setEnabled(false);

        YAxis yAxis = chartView.getAxisLeft();
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setEnabled(false);

        YAxis yAxis1 = chartView.getAxisRight();
        yAxis1.setDrawLabels(false);
        yAxis1.setDrawGridLines(false);
        yAxis1.setDrawAxisLine(false);
        yAxis1.setEnabled(false);

        Legend legend = chartView.getLegend();
        legend.setEnabled(false);
        legend.setDrawInside(false);

        chartView.setDescription(null);
        chartView.setDrawGridBackground(false);
        chartView.setData(lineData2);
        chartView.setDoubleTapToZoomEnabled(false);
        chartView.setDrawGridBackground(false);
        chartView.animateY(600, Easing.EasingOption.EaseInCubic);
        chartView.setPinchZoom(false);
        chartView.setScaleEnabled(false);
        chartView.invalidate();

        maxX = chartView.getXRange();

        CustomMarkerView marker = new CustomMarkerView(ActivitySimulation.this, R.layout.item_chart_marker);
        marker.setChartView(chartView);
        chartView.setMarker(marker);
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
            paint.setColor(getResources().getColor(R.color.chart_point_color));
            paint.setStrokeWidth(5f);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(posX, posY,18, paint);
            super.draw(canvas, posX, posY);
        }
    }
}
