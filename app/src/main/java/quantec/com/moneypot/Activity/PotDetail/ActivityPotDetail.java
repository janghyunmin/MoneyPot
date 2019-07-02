package quantec.com.moneypot.Activity.PotDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.PotDetail.AdpaterPotDetail.AdapterPotDetail;
import quantec.com.moneypot.DataModel.nModel.ModelChartData;
import quantec.com.moneypot.Dialog.DialogHelpInfo;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.util.DecimalScale.DecimalScale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPotDetail extends AppCompatActivity {

    PieChart pieChart;
    ArrayList<PieEntry> yValues;

    LinearLayout layout2;
    TextView warningTitle, investBt, categoryBt1,
            categoryBt2, categoryBt3, bondRate,
            stockRate, preTextView, rate,
            chartBt1, chartBt2, chartBt3, chartBt4, preChartBt;


    boolean openState;

    ScrollView scrollView;
    ImageView warningImage, potZimBt, investHelpBt, minInvestHelpBt;

    public static final int[] JOYFUL_COLORS = {
            Color.rgb(126, 111, 123),Color.rgb(255, 114, 114)
    };

    ScrollDisabledRecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelPotDetail> modelPotDetails;
    AdapterPotDetail adapterPotDetail;

    ConstraintLayout getView;

    LineChart chartView;
    List<Entry> entries2;
    LineDataSet lineDataSet2;
    LineData lineData2;
    float currentX, maxX;

    private DialogHelpInfo dialogHelpInfo;
    LottieAnimationView chartLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_detail);

        //                    스테이터스 바 색상 변경 -> 화이트
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

        chartBt1 = findViewById(R.id.chartBt1);
        chartBt2 = findViewById(R.id.chartBt2);
        chartBt3 = findViewById(R.id.chartBt3);
        chartBt4 = findViewById(R.id.chartBt4);

        getView = findViewById(R.id.includePotDetail);

        chartLoading = getView.findViewById(R.id.chartLoading);

        chartLoading.setAnimation("loading_animation.json");
        chartLoading.setRepeatCount(ValueAnimator.INFINITE);
        chartLoading.playAnimation();
        chartLoading.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

        });

        scrollView = findViewById(R.id.scrollView);
        rate = getView.findViewById(R.id.rate);
        potZimBt = findViewById(R.id.potZimBt);
        recyclerView = getView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        modelPotDetails = new ArrayList<>();
        adapterPotDetail = new AdapterPotDetail(modelPotDetails, this);
        recyclerView.setAdapter(adapterPotDetail);

        categoryBt1 = getView.findViewById(R.id.categoryBt1);
        categoryBt2 = getView.findViewById(R.id.categoryBt2);
        categoryBt3 = getView.findViewById(R.id.categoryBt3);
        bondRate = getView.findViewById(R.id.bondRate);
        stockRate = getView.findViewById(R.id.stockRate);
        pieChart = getView.findViewById(R.id.pieChart);
        pieChart.setNoDataText("");
        pieChart.setNoDataTextColor(R.color.text_white_color);
        pieChart.invalidate();

        chartView = getView.findViewById(R.id.chartView);
        chartView.setNoDataText("");
        chartView.setNoDataTextColor(R.color.text_white_color);
        chartView.invalidate();

        entries2 = new ArrayList<>();
        yValues = new ArrayList<>();
        layout2 = getView.findViewById(R.id.layout2);
        warningTitle = getView.findViewById(R.id.warningTitle);
        investBt = getView.findViewById(R.id.investBt);
        warningImage = getView.findViewById(R.id.warningImage);
        investHelpBt = getView.findViewById(R.id.investHelpBt);
        minInvestHelpBt = getView.findViewById(R.id.minInvestHelpBt);

//        InitViewData(0, 50f, 50f);
        preTextView = categoryBt1;
        preChartBt = chartBt2;

        warningTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openState = !openState;

                changeVisibility(openState);

                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.setSmoothScrollingEnabled(true);
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                }, 500);

            }
        });

        modelPotDetails.add(new ModelPotDetail(false, "", "", "", false));
        modelPotDetails.add(new ModelPotDetail(false, "대세로 떠로은 전기자전거와 킥보드 이용자 안전은","경향신문 | 2019녀 04월 10일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "자전거업계, 전지자전거로 미세먼지 악재 '정면돌파'","아주경제 | 2019년 03월 28일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "삼천리 자전거, 팬텀제로 핑크에디션 출시","트래블바이크뉴스 | 2019년 03월 18일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "서울 인구 100만명 공유 자전거 이용족들","메일신문 | 2019년 03월 02일", "",false));

        modelPotDetails.add(new ModelPotDetail(false, "자전거업계, 전지자전거로 미세먼지 악재 '정면돌파'","아주경제 | 2019년 03월 28일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "삼천리 자전거, 팬텀제로 핑크에디션 출시","트래블바이크뉴스 | 2019년 03월 18일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "서울 인구 100만명 공유 자전거 이용족들","메일신문 | 2019년 03월 02일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "자전거업계, 전지자전거로 미세먼지 악재 '정면돌파'","아주경제 | 2019년 03월 28일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "삼천리 자전거, 팬텀제로 핑크에디션 출시","트래블바이크뉴스 | 2019년 03월 18일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "서울 인구 100만명 공유 자전거 이용족들","메일신문 | 2019년 03월 02일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "", "", "",false));

        adapterPotDetail.notifyDataSetChanged();

        adapterPotDetail.setAddViewBtClick(new AdapterPotDetail.AddViewBtClick() {
            @Override
            public void onClick(int position) {

                if(modelPotDetails.get(0).isAddViewState()){
                    modelPotDetails.get(0).setAddViewState(false);
                }else{
                    modelPotDetails.get(0).setAddViewState(true);
                }

                adapterPotDetail.notifyDataSetChanged();

            }
        });

        adapterPotDetail.setPotDetailItemClick(new AdapterPotDetail.PotDetailItemClick() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(ActivityPotDetail.this, ActivityArticle.class);
                intent.putExtra("url", modelPotDetails.get(position).getUrl());
                startActivity(intent);

            }
        });

        categoryBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(preTextView != null){
                    preTextView.setBackgroundResource(0);
                    preTextView.setTextColor(getResources().getColor(R.color.dark_gray_color));
                }
                preTextView = categoryBt1;

                categoryBt1.setBackgroundResource(R.drawable.potdetail_round_able);
                categoryBt1.setTextColor(getResources().getColor(R.color.text_black_color));

                InitViewData(0, 50f, 50f);

            }
        });

        categoryBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(preTextView != null){
                    preTextView.setBackgroundResource(0);
                    preTextView.setTextColor(getResources().getColor(R.color.dark_gray_color));
                }

                preTextView = categoryBt2;

                categoryBt2.setBackgroundResource(R.drawable.potdetail_round_able);
                categoryBt2.setTextColor(getResources().getColor(R.color.text_black_color));

                InitViewData(1, 25f, 75f);

            }
        });

        categoryBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(preTextView != null){
                    preTextView.setBackgroundResource(0);
                    preTextView.setTextColor(getResources().getColor(R.color.dark_gray_color));
                }

                preTextView = categoryBt3;

                categoryBt3.setBackgroundResource(R.drawable.potdetail_round_able);
                categoryBt3.setTextColor(getResources().getColor(R.color.text_black_color));


                InitViewData(2, 10f, 90f);

            }
        });

        potZimBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        investHelpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHelpInfo = new DialogHelpInfo(ActivityPotDetail.this, "투자 자산 비율", "채권과 주식의 비중을 고객의 성향에 따라서 맞춰드립니다.", exithelpInfo);
                dialogHelpInfo.show();
            }
        });

        minInvestHelpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHelpInfo = new DialogHelpInfo(ActivityPotDetail.this, "최소 투자 가능 금액", "투자를 위해서 최소로 필요한 투자 금액입니다.", exithelpInfo);
                dialogHelpInfo.show();
            }
        });

        chartBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(chartBt1);
            }
        });

        chartBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(chartBt2);
            }
        });

        chartBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(chartBt3);
            }
        });

        chartBt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(chartBt4);
            }
        });

        ChartDur("MP0001",0);

    }// onCreate 끝

    void changedChartBt(TextView chartBt) {

        if(preChartBt != null){
            preChartBt.setBackground(getResources().getDrawable(R.drawable.chartbt_enable));
            preChartBt.setTextColor(getResources().getColor(R.color.dark_gray_color));
        }
        preChartBt = chartBt;

        chartBt.setBackground(getResources().getDrawable(R.drawable.chartbt_able));
        chartBt.setTextColor(getResources().getColor(R.color.red_text_color));

    }


    //FIDO 인증 취소 앱 종료
    private View.OnClickListener exithelpInfo = new View.OnClickListener() {
        public void onClick(View v) {
            dialogHelpInfo.dismiss();
        }
    };


    void ChartDur(String code, int pDate) {

        Call<ModelChartData> getTest2 = RetrofitClient.getInstance().getService().getPotChartData(code, pDate);
        getTest2.enqueue(new Callback<ModelChartData>() {
            @Override
            public void onResponse(Call<ModelChartData> call, Response<ModelChartData> response) {
                if(response.code() == 200) {

                    entries2.clear();

                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                        entries2.add(new Entry(a, DecimalScale.decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                    }

                    lineDataSet2 = new LineDataSet(entries2, null);
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

                    chartView.setNoDataText("");
                    chartView.setNoDataTextColor(R.color.text_white_color);
                    chartView.invalidate();

                    maxX = chartView.getXRange();

                    CustomMarkerView marker = new CustomMarkerView(ActivityPotDetail.this,R.layout.item_chart_marker);
                    marker.setChartView(chartView);
                    chartView.setMarker(marker);


                    chartLoading.cancelAnimation();
                    chartLoading.setVisibility(View.GONE);

                    ///////////
                    pieChart.setTransparentCircleRadius(0f);
                    pieChart.setDrawEntryLabels(false);
                    pieChart.setUsePercentValues(false);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setDrawHoleEnabled(true);
                    pieChart.setRotationEnabled(false);

                    bondRate.setText("50%");
                    stockRate.setText("50%");

                    yValues.add(new PieEntry(50f,"채권"));
                    yValues.add(new PieEntry(50f,"주식"));

                    pieChart.animateY(600, Easing.EasingOption.EaseInOutCubic); //애니메이션
                    pieChart.setDrawCenterText(false);

                    Legend l = pieChart.getLegend();
                    l.setDrawInside(false);
                    l.setEnabled(false);

                    PieDataSet dataSet = new PieDataSet(yValues,"");
                    dataSet.setColors(JOYFUL_COLORS);
                    dataSet.setDrawValues(false);

                    PieData data = new PieData(dataSet);
                    pieChart.setData(data);
                    pieChart.invalidate();


                }
            }
            @Override
            public void onFailure(Call<ModelChartData> call, Throwable t) {
                chartLoading.cancelAnimation();
                chartLoading.setVisibility(View.GONE);
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }


    void InitViewData(int category, float bond, float stock){

        ///////////
        entries2.clear();
        yValues.clear();

        if(category == 0){

            for(int a = 0 ; a < 100 ; a++){
                entries2.add(new Entry(a, a, a));
            }

            lineDataSet2 = new LineDataSet(entries2, null);
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

            chartView.setNoDataText("");
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

            CustomMarkerView marker = new CustomMarkerView(ActivityPotDetail.this,R.layout.item_chart_marker);
            marker.setChartView(chartView);
            chartView.setMarker(marker);

            ///////////
            pieChart.setTransparentCircleRadius(0f);
            pieChart.setDrawEntryLabels(false);
            pieChart.setUsePercentValues(false);
            pieChart.getDescription().setEnabled(false);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setRotationEnabled(false);

            bondRate.setText(bond+"%");
            stockRate.setText(stock+"%");

            yValues.add(new PieEntry(bond,"채권"));
            yValues.add(new PieEntry(stock,"주식"));

            pieChart.animateY(600, Easing.EasingOption.EaseInOutCubic); //애니메이션
            pieChart.setDrawCenterText(false);

            Legend l = pieChart.getLegend();
            l.setDrawInside(false);
            l.setEnabled(false);

            PieDataSet dataSet = new PieDataSet(yValues,"");
            dataSet.setColors(JOYFUL_COLORS);
            dataSet.setDrawValues(false);

            PieData data = new PieData(dataSet);
            pieChart.setData(data);
            pieChart.invalidate();


        }else if(category == 1){

            for(int a = 100 ; a > 0 ; a--){
                entries2.add(new Entry(99-a, a, a));
            }

            lineDataSet2 = new LineDataSet(entries2, null);
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

            CustomMarkerView marker = new CustomMarkerView(ActivityPotDetail.this,R.layout.item_chart_marker);
            marker.setChartView(chartView);
            chartView.setMarker(marker);

            ///////////

            pieChart.setTransparentCircleRadius(0f);
            pieChart.setDrawEntryLabels(false);
            pieChart.setUsePercentValues(false);
            pieChart.getDescription().setEnabled(false);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setRotationEnabled(false);

            bondRate.setText(bond+"%");
            stockRate.setText(stock+"%");

            yValues.add(new PieEntry(bond,"채권"));
            yValues.add(new PieEntry(stock,"주식"));

            pieChart.animateY(600, Easing.EasingOption.EaseInOutCubic); //애니메이션
            pieChart.setDrawCenterText(false);

            Legend l = pieChart.getLegend();
            l.setDrawInside(false);
            l.setEnabled(false);

            PieDataSet dataSet = new PieDataSet(yValues,"");
            dataSet.setColors(JOYFUL_COLORS);
            dataSet.setDrawValues(false);

            PieData data = new PieData(dataSet);
            pieChart.setData(data);
            pieChart.invalidate();


        }else{

            for(int a = 0 ; a < 100 ; a++){
                entries2.add(new Entry(a, 6, a));
            }

            lineDataSet2 = new LineDataSet(entries2, null);
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

            CustomMarkerView marker = new CustomMarkerView(ActivityPotDetail.this,R.layout.item_chart_marker);
            marker.setChartView(chartView);
            chartView.setMarker(marker);

            ///////////

            pieChart.setTransparentCircleRadius(0f);
            pieChart.setDrawEntryLabels(false);
            pieChart.setUsePercentValues(false);
            pieChart.getDescription().setEnabled(false);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setRotationEnabled(false);

            bondRate.setText(bond+"%");
            stockRate.setText(stock+"%");

            yValues.add(new PieEntry(bond,"채권"));
            yValues.add(new PieEntry(stock,"주식"));

            pieChart.animateY(600, Easing.EasingOption.EaseInOutCubic); //애니메이션
            pieChart.setDrawCenterText(false);

            Legend l = pieChart.getLegend();
            l.setDrawInside(false);
            l.setEnabled(false);

            PieDataSet dataSet = new PieDataSet(yValues,"");
            dataSet.setColors(JOYFUL_COLORS);
            dataSet.setDrawValues(false);

            PieData data = new PieData(dataSet);
            pieChart.setData(data);
            pieChart.invalidate();

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
            paint.setColor(getResources().getColor(R.color.chart_point_color));
            paint.setStrokeWidth(5f);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(posX, posY,18, paint);
            super.draw(canvas, posX, posY);
        }
    }


    /**
     * 클릭된 Item의 상태 변경
     * @param isExpanded Item을 펼칠 것인지 여부
     */
    private void changeVisibility(final boolean isExpanded) {

        // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용

        if(isExpanded){
            warningImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_gray));
        }else{
            warningImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_gray));
        }

        layout2.measure(View.MeasureSpec.UNSPECIFIED , View.MeasureSpec.UNSPECIFIED );
        int height = layout2.getMeasuredHeight();

        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(1, height) : ValueAnimator.ofInt(height, 1);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(450);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // value는 height 값
                int value = (int) animation.getAnimatedValue();

                // imageView의 높이 변경
                layout2.getLayoutParams().height = value;
                layout2.requestLayout();
                // imageView가 실제로 사라지게하는 부분
                layout2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            }
        });
        // Animation start
        va.start();
    }


}
