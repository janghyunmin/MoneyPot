package quantec.com.moneypot.Activity.PotDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import quantec.com.moneypot.Activity.PotDetail.AdpaterPotDetail.AdapterPotDetail;
import quantec.com.moneypot.DataModel.nModel.ModelChartData;
import quantec.com.moneypot.Database.Realm.CommonCode;
import quantec.com.moneypot.Dialog.DialogHelpInfo;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ActivityPotDetailBinding;
import quantec.com.moneypot.util.DecimalScale.DecimalScale;
import quantec.com.moneypot.util.view.ScrollDisabledRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPotDetail extends AppCompatActivity {

    ArrayList<PieEntry> yValues;
    TextView preTextView, preChartBt;
    boolean openState;
    public static final int[] JOYFUL_COLORS = {
            Color.rgb(126, 111, 123),Color.rgb(255, 114, 114)
    };

    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelPotDetail> modelPotDetails;
    AdapterPotDetail adapterPotDetail;

    List<Entry> stableEntries;
    LineDataSet lineDataSet2;
    LineData lineData2;
    float currentX, maxX;

    List<Entry> centerEntries;
    List<Entry> advenceEntries;

    private DialogHelpInfo dialogHelpInfo;
    ArrayList<DateMathDto> date;
    List<DateMathDtoResult> resultDate;
    double exp = 0.0;
    String nowDate, stCode;
    List<Entry> monThEntries;
    boolean monTh = false;

    Realm realm;
    float stableBond, stableStock,
            centerBond, centerStock,
            advenceBond, advenceStock;

    //MP외에는 propensity가 0 이므로 stCode값에서 분기를 통해 MP면 potType 1 / 그 외는 potType 0 을 주어서 해당 기간 호출시 기존 propensity * potType을 해준다.
    int potType;

    ActivityPotDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pot_detail);

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

        Intent intent1 = getIntent();
        stCode = intent1.getStringExtra("potCode");

        if(stCode.contains("MP")){
            potType = 1;
        }else{
            potType = 0;
        }

        realm = Realm.getDefaultInstance();
        RealmResults<CommonCode> results = realm.where(CommonCode.class)
                .findAllAsync();

        stableBond = results.get(0).getTypeLists().get(0).getGrade();
        stableStock = 100 - stableBond;

        centerBond = results.get(0).getTypeLists().get(1).getGrade();
        centerStock = 100 - centerBond;

        advenceBond = results.get(0).getTypeLists().get(2).getGrade();
        advenceStock = 100 - advenceBond;

        monThEntries = new ArrayList<>();
        date = new ArrayList<>();
        resultDate = new ArrayList<>();

        binding.includePotDetail.chartBt1.setTextColor(getResources().getColor(R.color.dark_gray_color));
        binding.includePotDetail.chartBt1.setBackground(getResources().getDrawable(R.drawable.chartbt_enable));
        binding.includePotDetail.chartBt2.setTextColor(getResources().getColor(R.color.dark_gray_color));
        binding.includePotDetail.chartBt2.setBackground(getResources().getDrawable(R.drawable.chartbt_enable));
        binding.includePotDetail.chartBt3.setTextColor(getResources().getColor(R.color.dark_gray_color));
        binding.includePotDetail.chartBt3.setBackground(getResources().getDrawable(R.drawable.chartbt_enable));
        binding.includePotDetail.chartBt4.setTextColor(getResources().getColor(R.color.red_text_color));
        binding.includePotDetail.chartBt4.setBackground(getResources().getDrawable(R.drawable.chartbt_able));

        binding.includePotDetail.chartLoading.setAnimation("loading_animation.json");
        binding.includePotDetail.chartLoading.setRepeatCount(ValueAnimator.INFINITE);
        binding.includePotDetail.chartLoading.playAnimation();

        binding.includePotDetail.recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        binding.includePotDetail.recyclerView.setLayoutManager(layoutManager);
        modelPotDetails = new ArrayList<>();
        adapterPotDetail = new AdapterPotDetail(modelPotDetails, this);
        binding.includePotDetail.recyclerView.setAdapter(adapterPotDetail);

        binding.includePotDetail.pieChart.setNoDataText("");
        binding.includePotDetail.pieChart.setNoDataTextColor(R.color.text_white_color);
        binding.includePotDetail.pieChart.invalidate();

        binding.includePotDetail.chartView.setNoDataText("");
        binding.includePotDetail.chartView.setNoDataTextColor(R.color.text_white_color);
        binding.includePotDetail.chartView.invalidate();

        stableEntries = new ArrayList<>();
        centerEntries = new ArrayList<>();
        advenceEntries = new ArrayList<>();

        yValues = new ArrayList<>();

        preTextView = binding.includePotDetail.categoryBt1;
        preChartBt = binding.includePotDetail.chartBt4;

        binding.includePotDetail.warningTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openState = !openState;

                changeVisibility(openState);

                binding.scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.scrollView.setSmoothScrollingEnabled(true);
                        binding.scrollView.fullScroll(View.FOCUS_DOWN);
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

        binding.includePotDetail.categoryBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.includePotDetail.chartView.setVisibility(View.INVISIBLE);
                binding.includePotDetail.chartLoading.setVisibility(View.VISIBLE);
                binding.includePotDetail.chartLoading.playAnimation();
                ChartDur(stCode,0, 701*potType, stableBond, stableStock);

                if(preTextView != null){
                    preTextView.setBackgroundResource(0);
                    preTextView.setTextColor(getResources().getColor(R.color.dark_gray_color));
                }
                preTextView = binding.includePotDetail.categoryBt1;

                binding.includePotDetail.categoryBt1.setBackgroundResource(R.drawable.potdetail_round_able);
                binding.includePotDetail.categoryBt1.setTextColor(getResources().getColor(R.color.text_black_color));

            }
        });

        binding.includePotDetail.categoryBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.includePotDetail.chartView.setVisibility(View.INVISIBLE);
                binding.includePotDetail.chartLoading.setVisibility(View.VISIBLE);
                binding.includePotDetail.chartLoading.playAnimation();
                ChartDur(stCode,0, 703*potType, centerBond, centerStock);

                if(preTextView != null){
                    preTextView.setBackgroundResource(0);
                    preTextView.setTextColor(getResources().getColor(R.color.dark_gray_color));
                }

                preTextView = binding.includePotDetail.categoryBt2;

                binding.includePotDetail.categoryBt2.setBackgroundResource(R.drawable.potdetail_round_able);
                binding.includePotDetail.categoryBt2.setTextColor(getResources().getColor(R.color.text_black_color));

            }
        });

        binding.includePotDetail.categoryBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                binding.includePotDetail.chartView.setVisibility(View.INVISIBLE);
                binding.includePotDetail.chartLoading.setVisibility(View.VISIBLE);
                binding.includePotDetail.chartLoading.playAnimation();
                ChartDur(stCode,0, 704*potType, advenceBond, advenceStock);

                if(preTextView != null){
                    preTextView.setBackgroundResource(0);
                    preTextView.setTextColor(getResources().getColor(R.color.dark_gray_color));
                }

                preTextView = binding.includePotDetail.categoryBt3;

                binding.includePotDetail.categoryBt3.setBackgroundResource(R.drawable.potdetail_round_able);
                binding.includePotDetail.categoryBt3.setTextColor(getResources().getColor(R.color.text_black_color));
            }
        });

        binding.potZimBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(){
//                    potZimBt.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_off_gray));
//                }else{
//                    potZimBt.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_on));
//                }
            }
        });


        binding.includePotDetail.investHelpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHelpInfo = new DialogHelpInfo(ActivityPotDetail.this, "투자 자산 비율", "채권과 주식의 비중을 고객의 성향에 따라서 맞춰드립니다.", exithelpInfo);
                dialogHelpInfo.show();
            }
        });

        binding.includePotDetail.minInvestHelpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHelpInfo = new DialogHelpInfo(ActivityPotDetail.this, "최소 투자 가능 금액", "투자를 위해서 최소로 필요한 투자 금액입니다.", exithelpInfo);
                dialogHelpInfo.show();
            }
        });

        binding.includePotDetail.chartBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(binding.includePotDetail.chartBt1);
                //month -> 1개월 3개월 6개월 : -1, -3, -6
                String startDate = startDate(nowDate, -1);
                monTh = false;
                calDate(startDate, monTh);

                drawLineChart(monThEntries);

                execute(0.0f ,(float)resultDate.get(resultDate.size()-1).getExp());
            }
        });

        binding.includePotDetail.chartBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(binding.includePotDetail.chartBt2);

                //month -> 1개월 3개월 6개월 : -1, -3, -6
                String startDate = startDate(nowDate, -3);
                monTh = false;
                calDate(startDate, monTh);

                drawLineChart(monThEntries);

                execute(0.0f ,(float)resultDate.get(resultDate.size()-1).getExp());

            }
        });

        binding.includePotDetail.chartBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(binding.includePotDetail.chartBt3);

                //month -> 1개월 3개월 6개월 : -1, -3, -6
                String startDate = startDate(nowDate, -6);
                monTh = false;
                calDate(startDate, monTh);

                drawLineChart(monThEntries);

                execute(0.0f ,(float)resultDate.get(resultDate.size()-1).getExp());
            }
        });

        binding.includePotDetail.chartBt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(binding.includePotDetail.chartBt4);

                drawLineChart(stableEntries);
                execute(0.0f ,(float)date.get(date.size()-1).getRate());
            }
        });

        ChartDur(stCode,0, 701*potType, stableBond, stableStock);

    }// onCreate 끝


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
            monThEntries.add(new Entry(index, DecimalScale.decimalScale2(String.valueOf(resultDate.get(index).getExp()*100), 2, 2),
                    resultDate.get(index).getDate()));
        }
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

        XAxis xAxis = binding.includePotDetail.chartView.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setEnabled(false);

        YAxis yAxis = binding.includePotDetail.chartView.getAxisLeft();
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawAxisLine(false);
        yAxis.setEnabled(false);

        YAxis yAxis1 = binding.includePotDetail.chartView.getAxisRight();
        yAxis1.setDrawLabels(false);
        yAxis1.setDrawGridLines(false);
        yAxis1.setDrawAxisLine(false);
        yAxis1.setEnabled(false);

        Legend legend = binding.includePotDetail.chartView.getLegend();
        legend.setEnabled(false);
        legend.setDrawInside(false);

        binding.includePotDetail.chartView.setDescription(null);
        binding.includePotDetail.chartView.setDrawGridBackground(false);
        binding.includePotDetail.chartView.setData(lineData2);
        binding.includePotDetail.chartView.setDoubleTapToZoomEnabled(false);
        binding.includePotDetail.chartView.setDrawGridBackground(false);
        binding.includePotDetail.chartView.animateY(600, Easing.EasingOption.EaseInCubic);
        binding.includePotDetail.chartView.setPinchZoom(false);
        binding.includePotDetail.chartView.setScaleEnabled(false);

        maxX = binding.includePotDetail.chartView.getXRange();

        CustomMarkerView marker = new CustomMarkerView(ActivityPotDetail.this,R.layout.item_chart_marker);
        marker.setChartView(binding.includePotDetail.chartView);
        binding.includePotDetail.chartView.setMarker(marker);
    }

    private void drawPieChart(List<PieEntry> chartData, float bond, float stock){

        binding.includePotDetail.pieChart.setTransparentCircleRadius(0f);
        binding.includePotDetail.pieChart.setDrawEntryLabels(false);
        binding.includePotDetail.pieChart.setUsePercentValues(false);
        binding.includePotDetail.pieChart.getDescription().setEnabled(false);
        binding.includePotDetail.pieChart.setDrawHoleEnabled(true);
        binding.includePotDetail.pieChart.setRotationEnabled(false);

        binding.includePotDetail.bondRate.setText(String.valueOf(bond)+"%");
        binding.includePotDetail.stockRate.setText(String.valueOf(stock)+"%");

        binding.includePotDetail.pieChart.animateY(600, Easing.EasingOption.EaseInOutCubic); //애니메이션
        binding.includePotDetail.pieChart.setDrawCenterText(false);

        Legend l = binding.includePotDetail.pieChart.getLegend();
        l.setDrawInside(false);
        l.setEnabled(false);

        PieDataSet dataSet = new PieDataSet(chartData,"");
        dataSet.setColors(JOYFUL_COLORS);
        dataSet.setDrawValues(false);

        PieData data = new PieData(dataSet);
        binding.includePotDetail.pieChart.setData(data);
        binding.includePotDetail.pieChart.invalidate();
    }

    void changedChartBt(TextView chartBt) {
        if(preChartBt != null){
            preChartBt.setBackground(getResources().getDrawable(R.drawable.chartbt_enable));
            preChartBt.setTextColor(getResources().getColor(R.color.dark_gray_color));
        }
        preChartBt = chartBt;

        chartBt.setBackground(getResources().getDrawable(R.drawable.chartbt_able));
        chartBt.setTextColor(getResources().getColor(R.color.red_text_color));
    }

    private View.OnClickListener exithelpInfo = new View.OnClickListener() {
        public void onClick(View v) {
            dialogHelpInfo.dismiss();
        }
    };

    public void execute(float mStartValue, float mEndValue){

        if(mEndValue < 0){
            binding.includePotDetail.rate.setTextColor(getResources().getColor(R.color.blue_color));
            binding.includePotDetail.per.setTextColor(getResources().getColor(R.color.blue_color));
        }else{
            binding.includePotDetail.rate.setTextColor(getResources().getColor(R.color.red_text_color));
            binding.includePotDetail.per.setTextColor(getResources().getColor(R.color.red_text_color));
        }

        ValueAnimator mValueAnimator = ValueAnimator.ofFloat(mStartValue, mEndValue);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float current = DecimalScale.decimalScale2(String.valueOf(Float.valueOf(valueAnimator.getAnimatedValue().toString())*100), 2, 2);
                binding.includePotDetail.rate.setText(String.valueOf(current));
            }
        });
        mValueAnimator.start();
    }

    void ChartDur(String code, int pDate, int propensity, float bondNum, float stockNum) {

        binding.includePotDetail.chartBt1.setVisibility(View.INVISIBLE);
        binding.includePotDetail.chartBt2.setVisibility(View.INVISIBLE);
        binding.includePotDetail.chartBt3.setVisibility(View.INVISIBLE);
        binding.includePotDetail.chartBt4.setVisibility(View.INVISIBLE);

        Call<ModelChartData> getTest2 = RetrofitClient.getInstance().getService().getPotChartData(code, pDate, propensity);
        getTest2.enqueue(new Callback<ModelChartData>() {
            @Override
            public void onResponse(Call<ModelChartData> call, Response<ModelChartData> response) {
                if(response.code() == 200) {

                    date.clear();
                    stableEntries.clear();
                    yValues.clear();

                    for(int a = 0 ; a < response.body().getContent().size() ; a++) {
                        date.add(new DateMathDto(response.body().getContent().get(a).getDate(), response.body().getContent().get(a).getPrice(), response.body().getContent().get(a).getExp()));

                        stableEntries.add(new Entry(a, DecimalScale.decimalScale2(String.valueOf(response.body().getContent().get(a).getExp()*100), 2, 2), response.body().getContent().get(a).getDate()));
                    }
                    //최근 날짜 받음 -> 몇개월뒤 날짜 계산을 위해서
                    nowDate = date.get(date.size()-1).getDate();
                    drawLineChart(stableEntries);

                    execute(0.0f ,(float)date.get(date.size()-1).getRate());
                    //////
                    binding.includePotDetail.chartLoading.cancelAnimation();
                    binding.includePotDetail.chartLoading.setVisibility(View.GONE);
                    /////
                    yValues.add(new PieEntry(stockNum,"주식"));
                    yValues.add(new PieEntry(bondNum,"채권"));
                    drawPieChart(yValues, bondNum, stockNum);

                    preChartBt = binding.includePotDetail.chartBt4;

                    binding.includePotDetail.chartView.setVisibility(View.VISIBLE);
                    binding.includePotDetail.chartBt1.setVisibility(View.VISIBLE);
                    binding.includePotDetail.chartBt2.setVisibility(View.VISIBLE);
                    binding.includePotDetail.chartBt3.setVisibility(View.VISIBLE);
                    binding.includePotDetail.chartBt4.setVisibility(View.VISIBLE);

                    binding.includePotDetail.chartBt1.setTextColor(getResources().getColor(R.color.dark_gray_color));
                    binding.includePotDetail.chartBt1.setBackground(getResources().getDrawable(R.drawable.chartbt_enable));
                    binding.includePotDetail.chartBt2.setTextColor(getResources().getColor(R.color.dark_gray_color));
                    binding.includePotDetail.chartBt2.setBackground(getResources().getDrawable(R.drawable.chartbt_enable));
                    binding.includePotDetail.chartBt3.setTextColor(getResources().getColor(R.color.dark_gray_color));
                    binding.includePotDetail.chartBt3.setBackground(getResources().getDrawable(R.drawable.chartbt_enable));
                    binding.includePotDetail.chartBt4.setTextColor(getResources().getColor(R.color.red_text_color));
                    binding.includePotDetail.chartBt4.setBackground(getResources().getDrawable(R.drawable.chartbt_able));
                }
            }
            @Override
            public void onFailure(Call<ModelChartData> call, Throwable t) {
                binding.includePotDetail.chartLoading.cancelAnimation();
                binding.includePotDetail.chartLoading.setVisibility(View.GONE);
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
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
            binding.includePotDetail.warningImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_gray));
        }else{
            binding.includePotDetail.warningImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_gray));
        }

        binding.includePotDetail.layout2.measure(View.MeasureSpec.UNSPECIFIED , View.MeasureSpec.UNSPECIFIED );
        int height = binding.includePotDetail.layout2.getMeasuredHeight();
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
                binding.includePotDetail.layout2.getLayoutParams().height = value;
                binding.includePotDetail.layout2.requestLayout();
                // imageView가 실제로 사라지게하는 부분
                binding.includePotDetail.layout2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        });
        // Animation start
        va.start();
    }
}
