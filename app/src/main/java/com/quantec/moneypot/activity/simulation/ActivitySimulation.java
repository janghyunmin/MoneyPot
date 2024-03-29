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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelPotSimul;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelPreChartList;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelSimulCode;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.activity.invest.ActivityCustomInvest;
import com.quantec.moneypot.datamanager.ChartManager;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.util.DecimalScale.DecimalScale;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySimulation extends AppCompatActivity {

    ArrayList<ModelPreChartList> modelPreChartLists;

    TextView rate, per, num, mon1, mon3, mon6, monAll, preChartBt, saveBt, investBt;
    ChipGroup chipGroup;
    ImageView backBt;

    SpannableStringBuilder sp1, sp2;

    LineChart chartView;
    List<Entry> entries;
    LineDataSet lineDataSet2;
    LineData lineData2;
    float currentX, maxX, chartExp;
    List<DateMathDtoResult> resultDate;
    List<Entry> monTh1Entries, monTh3Entries, monTh6Entries;
    double exp = 0.0;

    ArrayList<DateMathDto> date;

    String stCode, nowDate, exAllRate, ex1Rate, ex3Rate, ex6Rate;

    boolean monTh1 = true;
    boolean monTh3 = true;
    boolean monTh6 = true;

    LinearLayout loading;

    int nowPrice, price;

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

        saveBt = findViewById(R.id.saveBt);

        loading = findViewById(R.id.loading);
        loading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        mon1 = findViewById(R.id.mon1);
        mon3 = findViewById(R.id.mon3);
        mon6 = findViewById(R.id.mon6);
        monAll = findViewById(R.id.monAll);

        preChartBt = monAll;

        entries = new ArrayList<>();
        date = new ArrayList<>();
        resultDate = new ArrayList<>();
        monTh1Entries = new ArrayList<>();
        monTh3Entries = new ArrayList<>();
        monTh6Entries = new ArrayList<>();

        chartView = findViewById(R.id.chartView);
        chartView.setNoDataText("");
        chartView.setNoDataTextColor(R.color.text_white_color);
        chartView.invalidate();

        backBt = findViewById(R.id.backBt);
        investBt = findViewById(R.id.investBt);

        modelPreChartLists = new ArrayList<>();
        rate = findViewById(R.id.rate);
        per = findViewById(R.id.per);
        num = findViewById(R.id.num);

        chipGroup = findViewById(R.id.chipGroup);

        Intent intent1 = getIntent();
        exAllRate = String.valueOf(intent1.getDoubleExtra("rate", 0));
        rate.setText(exAllRate);
        nowPrice = intent1.getIntExtra("nowPrice", 0);
        price = intent1.getIntExtra("price", 0);

        if(intent1.getDoubleExtra("rate", 0) < 0){
            rate.setTextColor(getResources().getColor(R.color.blue_color));
            per.setTextColor(getResources().getColor(R.color.blue_color));

        }else{
            rate.setTextColor(getResources().getColor(R.color.red_text_color));
            per.setTextColor(getResources().getColor(R.color.red_text_color));
        }

        ArrayList<Code> codes = new ArrayList<>();

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

            Code code = new Code();
            code.setCode(data.getCode());
            code.setPtCode("");
            code.setType(0);
            code.setWeight(0);

            codes.add(code);

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
            entries.add(new Entry(xCount,
                    DecimalScale.decimalScale2(String.valueOf(ChartManager.get_Instance().getTransChartLists().get(xCount).getRate()), 2, 2), ChartManager.get_Instance().getTransChartLists().get(xCount).getDate()

            ));
        }

        chartExp = DecimalScale.decimalScale2(String.valueOf(date.get(date.size()-1).getRate()), 2, 2);

        //최근 날짜 받음 -> 몇개월뒤 날짜 계산을 위해서
        nowDate = date.get(date.size()-1).getDate();
        drawLineChart(entries);


        RxView.clicks(mon1).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            if(monTh1) {
                loading.setVisibility(View.VISIBLE);
                changedChartBt(mon1);
                monTh1 = false;
                chartData("one");
            }else{
                changedChartBt(mon1);
                drawLineChart(monTh1Entries);
                rate.setText(ex1Rate);
            }
        });
        RxView.clicks(mon3).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            if(monTh3){
                loading.setVisibility(View.VISIBLE);
                changedChartBt(mon3);
                monTh3 = false;
                chartData("thr");
            }else{
                changedChartBt(mon3);
                drawLineChart(monTh3Entries);
                rate.setText(ex3Rate);
            }
        });
        RxView.clicks(mon6).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            if(monTh6){
                loading.setVisibility(View.VISIBLE);
                changedChartBt(mon6);
                monTh6 = false;
                chartData("six");
            }else{
                changedChartBt(mon6);
                drawLineChart(monTh6Entries);
                rate.setText(ex6Rate);
            }
        });
        RxView.clicks(monAll).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            changedChartBt(monAll);
            drawLineChart(entries);
            rate.setText(exAllRate);
        });

        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModelSimulation modelSimulation = new ModelSimulation();
                modelSimulation.setCodes(codes);
                modelSimulation.setDescript("");
                modelSimulation.setName("");
                modelSimulation.setPeriod("");
                modelSimulation.setRate(intent1.getDoubleExtra("rate", 0));
                modelSimulation.setType(11);
                modelSimulation.setCode("");
                modelSimulation.setNowPrice(nowPrice);

                Call<Object> getReList = RetrofitClient.getInstance().getService().setAssetsCustom("application/json", modelSimulation);
                getReList.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.code() == 200) {

                            setResult(200);
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e("실패","실패"+t.getMessage());
                    }
                });
            }
        });


        RxView.clicks(investBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {

            Intent intent = new Intent(this, ActivityCustomInvest.class);
            intent.putParcelableArrayListExtra("custominvest", modelPreChartLists);
            intent.putExtra("price", price);
            startActivity(intent);
        });


    }//onCreate 끝

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

    void changedChartBt(TextView chartBt) {
        if(preChartBt != null){
            preChartBt.setBackground(getResources().getDrawable(R.drawable.custom_no_line));
            preChartBt.setTextColor(getResources().getColor(R.color.c_9a9a9a));
        }
        preChartBt = chartBt;

        chartBt.setBackground(getResources().getDrawable(R.drawable.custom_bt_line_blue_15dp));
        chartBt.setTextColor(getResources().getColor(R.color.c_4e7cff));
    }

    void chartData(String date){
        ModelSimulCode modelSimulCode = new ModelSimulCode();
        modelSimulCode.setCode("");
        modelSimulCode.setDescript("");
        modelSimulCode.setName("");
        modelSimulCode.setPeriod(date);
        modelSimulCode.setPropensity(701);
        modelSimulCode.setRate(0);
        modelSimulCode.setType(0);

        ArrayList<Code> codes = new ArrayList<>();


        for(int index = 0 ; index < modelPreChartLists.size(); index++){

            Code code1 = new Code();
            code1.setCode(modelPreChartLists.get(index).getCode());
            code1.setType(0);
            code1.setPtCode("");
            code1.setWeight(0);

            codes.add(code1);
        }

        modelSimulCode.setCodes(codes);

        Call<ModelPotSimul> getReList = RetrofitClient.getInstance().getService().getPotSimul("application/json", modelSimulCode);
        getReList.enqueue(new Callback<ModelPotSimul>() {
            @Override
            public void onResponse(Call<ModelPotSimul> call, Response<ModelPotSimul> response) {
                if (response.code() == 200) {
                    if(response.body().getStatus() == 200){

                        for(int index = 0 ; index < response.body().getContent().getChart().size() ; index++) {

                            if(date.equals("one")){

                                monTh1Entries.add(new Entry(index,
                                        DecimalScale.decimalScale2(String.valueOf(response.body().getContent().getChart().get(index).getExp()), 2, 2),
                                        response.body().getContent().getChart().get(index).getDate()));
                            }else if(date.equals("thr")){
                                monTh3Entries.add(new Entry(index,
                                        DecimalScale.decimalScale2(String.valueOf(response.body().getContent().getChart().get(index).getExp()), 2, 2),
                                        response.body().getContent().getChart().get(index).getDate()));
                            }else{
                                monTh6Entries.add(new Entry(index,
                                        DecimalScale.decimalScale2(String.valueOf(response.body().getContent().getChart().get(index).getExp()), 2, 2),
                                        response.body().getContent().getChart().get(index).getDate()));
                            }
                        }

                        if(date.equals("one")){
                            drawLineChart(monTh1Entries);
                            ex1Rate = String.valueOf(monTh1Entries.get(monTh1Entries.size()-1).getY());
                            rate.setText(ex1Rate);
                        }else if(date.equals("thr")){
                            drawLineChart(monTh3Entries);
                            ex3Rate = String.valueOf(monTh3Entries.get(monTh3Entries.size()-1).getY());
                            rate.setText(ex3Rate);
                        }else{
                            drawLineChart(monTh6Entries);
                            ex6Rate = String.valueOf(monTh6Entries.get(monTh6Entries.size()-1).getY());
                            rate.setText(ex6Rate);
                        }
                        loading.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelPotSimul> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
                loading.setVisibility(View.GONE);
            }
        });
    }

}
