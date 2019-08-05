package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import quantec.com.moneypot.Activity.PotDetail.DateMathDto;
import quantec.com.moneypot.Activity.PotDetail.DateMathDtoResult;
import quantec.com.moneypot.DataManager.ChartManager;
import quantec.com.moneypot.DataModel.dModel.UpPotDto;
import quantec.com.moneypot.DataModel.nModel.ModelPrevPotSave;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.util.DecimalScale.DecimalScale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPreviewChart extends AppCompatActivity {

    Button prevBt, saveBT;

    LineChart chartView;
    List<Entry> entries;
    LineDataSet lineDataSet2;
    LineData lineData2;
    float currentX, maxX, chartExp;
    List<DateMathDtoResult> resultDate;
    List<Entry> monThEntries;
    double exp = 0.0;

    ArrayList<DateMathDto> date;
    TextView chartBt1, chartBt2, chartBt3, chartBt4, preChartBt, category, potName1, potName2, potName3, potName4, priceBt, calPrice;
    String stCode, nowDate;
    EditText priceEditText;

    boolean monTh = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_chart);

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

        saveBT = findViewById(R.id.saveBT);

        priceEditText = findViewById(R.id.priceEditText);
        priceBt = findViewById(R.id.priceBt);
        calPrice = findViewById(R.id.calPrice);


        category = findViewById(R.id.category);

        potName1 = findViewById(R.id.potName1);
        potName2 = findViewById(R.id.potName2);
        potName3 = findViewById(R.id.potName3);
        potName4 = findViewById(R.id.potName4);

        chartBt1 = findViewById(R.id.chartBt1);
        chartBt2 = findViewById(R.id.chartBt2);
        chartBt3 = findViewById(R.id.chartBt3);
        chartBt4 = findViewById(R.id.chartBt4);

        chartBt1.setTextColor(getResources().getColor(R.color.dark_gray_color));
        chartBt1.setBackground(getResources().getDrawable(R.drawable.chartbt_enable));
        chartBt2.setTextColor(getResources().getColor(R.color.dark_gray_color));
        chartBt2.setBackground(getResources().getDrawable(R.drawable.chartbt_enable));
        chartBt3.setTextColor(getResources().getColor(R.color.dark_gray_color));
        chartBt3.setBackground(getResources().getDrawable(R.drawable.chartbt_enable));
        chartBt4.setTextColor(getResources().getColor(R.color.red_text_color));
        chartBt4.setBackground(getResources().getDrawable(R.drawable.chartbt_able));

        preChartBt = chartBt4;

        chartView = findViewById(R.id.chartView);
        chartView.setNoDataText("");
        chartView.setNoDataTextColor(R.color.text_white_color);
        chartView.invalidate();

        entries = new ArrayList<>();
        date = new ArrayList<>();
        resultDate = new ArrayList<>();
        monThEntries = new ArrayList<>();

        prevBt = findViewById(R.id.prevBt);
        prevBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityPreviewChart.this, ActivityPotCook.class);
                setResult(100, intent);
                finish();
            }
        });

        Intent intent1 = getIntent();
        stCode = intent1.getBundleExtra("chartData").get("transptcode").toString();

        if(intent1.getIntExtra("finishcategory", 0) == 701){
            category.setText("안정형 상품");
        }
        else if(intent1.getIntExtra("finishcategory", 0) == 703){
            category.setText("중립형 상품");
        }
        else{
            category.setText("모험가형 상품");
        }

        for(int index = 0 ; index < intent1.getBundleExtra("chartData").getStringArrayList("transtitle").size() ; index++){

            if(index == 0){
                potName1.setText(intent1.getBundleExtra("chartData").getStringArrayList("transtitle").get(index));
            }else if(index == 1){
                potName2.setText(intent1.getBundleExtra("chartData").getStringArrayList("transtitle").get(index));
            }else if(index == 2){
                potName3.setVisibility(View.VISIBLE);
                potName3.setText(intent1.getBundleExtra("chartData").getStringArrayList("transtitle").get(index));
            }else{
                potName4.setVisibility(View.VISIBLE);
                potName4.setText(intent1.getBundleExtra("chartData").getStringArrayList("transtitle").get(index));
            }
        }
        for(int xCount = 0; xCount < ChartManager.get_Instance().getTransChartLists().size() ; xCount++) {

            date.add(new DateMathDto(ChartManager.get_Instance().getTransChartLists().get(xCount).getDate(),
                    ChartManager.get_Instance().getTransChartLists().get(xCount).getPrice(),
                    ChartManager.get_Instance().getTransChartLists().get(xCount).getRate()));

            entries.add(new Entry(xCount,
                    DecimalScale.decimalScale2(String.valueOf(ChartManager.get_Instance().getTransChartLists().get(xCount).getRate()*100), 2, 2), ChartManager.get_Instance().getTransChartLists().get(xCount).getDate()
            ));
        }

        chartExp = DecimalScale.decimalScale2(String.valueOf(date.get(date.size()-1).getRate()*100), 2, 2);

        //최근 날짜 받음 -> 몇개월뒤 날짜 계산을 위해서
        nowDate = date.get(date.size()-1).getDate();
        drawLineChart(entries);


        chartBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(chartBt1);

                //month -> 1개월 3개월 6개월 : -1, -3, -6
                String startDate = startDate(nowDate, -1);
                monTh = false;
                calDate(startDate, monTh);

                drawLineChart(monThEntries);

//                execute(0.0f ,(float)resultDate.get(resultDate.size()-1).getExp());

            }
        });

        chartBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(chartBt2);

                //month -> 1개월 3개월 6개월 : -1, -3, -6
                String startDate = startDate(nowDate, -3);
                monTh = false;
                calDate(startDate, monTh);

                drawLineChart(monThEntries);
            }
        });

        chartBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(chartBt3);

                //month -> 1개월 3개월 6개월 : -1, -3, -6
                String startDate = startDate(nowDate, -6);
                monTh = false;
                calDate(startDate, monTh);

                drawLineChart(monThEntries);
            }
        });

        chartBt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedChartBt(chartBt4);

                drawLineChart(entries);
            }
        });


        priceBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calPrice.setText(String.valueOf((1 + (chartExp/100)) * (Float.valueOf(priceEditText.getText().toString()))));
            }
        });

        saveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpPotDto upPotDto = new UpPotDto(stCode, "제목을 담아봅니다.", "설명을 담아봅니다.");
                Call<ModelPrevPotSave> getchartItem = RetrofitClient.getInstance().getService().getUpPot("application/json", upPotDto);
                getchartItem.enqueue(new Callback<ModelPrevPotSave>() {
                    @Override
                    public void onResponse(Call<ModelPrevPotSave> call, Response<ModelPrevPotSave> response) {

                        if(response.code() == 200) {

                            Intent intent11 = new Intent(ActivityPreviewChart.this, ActivityPotCook.class);
                            setResult(100, intent11);
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelPrevPotSave> call, Throwable t) {
                        Toast.makeText(ActivityPreviewChart.this, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                        Log.e("레트로핏 실패","값 : "+t.getMessage());
                    }
                });

            }
        });

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

        chartExp = DecimalScale.decimalScale2(String.valueOf(resultDate.get(resultDate.size()-1).getExp()*100), 2, 2);
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

        CustomMarkerView marker = new CustomMarkerView(ActivityPreviewChart.this,R.layout.item_chart_marker);
        marker.setChartView(chartView);
        chartView.setMarker(marker);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityPreviewChart.this, ActivityPotCook.class);
        setResult(100, intent);
        finish();
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
