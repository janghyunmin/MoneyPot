package quantec.com.moneypot.Activity.Myinfo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

import quantec.com.moneypot.Activity.Main.Fragment.FgTab1.Model.nModel.ModelMiddleChartData;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ActivityMyinfoBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMyinfo extends AppCompatActivity {

    ActivityMyinfoBinding activityMyinfoBinding;

    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    float currentX;
    float maxX;

    Button CurrentButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMyinfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_myinfo);
        activityMyinfoBinding.setMyInfoActivity(this);

//        스테이터스 바 색상 변경 -> 화이트
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

        activityMyinfoBinding.myInfoPageCloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        activityMyinfoBinding.myInfoPageChartViewDur1Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartDur("1");
                ChangedChartButton(activityMyinfoBinding.myInfoPageChartViewDur1Bt);
            }
        });

        activityMyinfoBinding.myInfoPageChartViewDur3Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartDur("3");
                ChangedChartButton(activityMyinfoBinding.myInfoPageChartViewDur3Bt);
            }
        });

        activityMyinfoBinding.myInfoPageChartViewDur6Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartDur("6");
                ChangedChartButton(activityMyinfoBinding.myInfoPageChartViewDur6Bt);
            }
        });

        activityMyinfoBinding.myInfoPageChartViewDuraBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChartDur("a");
                ChangedChartButton(activityMyinfoBinding.myInfoPageChartViewDuraBt);
            }
        });

        entries = new ArrayList<>();
        //초기 누적값으로 차트 보여줌
        ChartDur("a");
        ChangedChartButton(activityMyinfoBinding.myInfoPageChartViewDuraBt);
    }

    // 차트 위치 ChartPosition : 0 -> 미들 차트 / 1 -> 바텀 차트
    void ChangedChartButton(Button currentBT){

            if(CurrentButtonView != null) {
                CurrentButtonView.setBackgroundResource(R.drawable.unselected_round_button);
                CurrentButtonView.setTextColor(getResources().getColor(R.color.chart_dur_bt_color));
            }
        CurrentButtonView = currentBT;
        CurrentButtonView.setBackgroundResource(R.drawable.round_button);
        CurrentButtonView.setTextColor(getResources().getColor(R.color.delete_pressed_text));
    }

    //각 차트의 1개월 / 3개월 / 6개월 / 누적 버튼
    void ChartDur(String dur) {

        //코드값과 기간을 기준으로 차트 데이터를 불러옴 ( 누적 데이터 )
        Call<ModelMiddleChartData> getchartItem = RetrofitClient.getInstance().getService().getChart(15,dur);
        getchartItem.enqueue(new Callback<ModelMiddleChartData>() {
            @Override
            public void onResponse(Call<ModelMiddleChartData> call, Response<ModelMiddleChartData> response) {
                if(response.code() == 200) {
                    entries.clear();

                    for(int a = 0 ; a < response.body().getElements().size() ; a++) {
                        entries.add(new Entry(a, response.body().getElements().get(a).getRate(), response.body().getElements().get(a).getDate()));
                    }

                    lineDataSet = new LineDataSet(entries, null);
                    lineDataSet.setLineWidth(1.5f);
                    lineDataSet.setColor(Color.parseColor("#FFFF0000"));
                    lineDataSet.setDrawCircleHole(false);
                    lineDataSet.setDrawCircles(false);
                    lineDataSet.setDrawHorizontalHighlightIndicator(false);
                    lineDataSet.setDrawValues(false);
                    lineDataSet.setLabel("");
                    lineDataSet.setHighlightEnabled(true);
                    lineDataSet.setDrawHighlightIndicators(true);
                    lineDataSet.setDrawHorizontalHighlightIndicator(false);
                    lineDataSet.setHighLightColor(R.color.chart_limit_line_color);
                    lineDataSet.setHighlightLineWidth(1f);
                    lineDataSet.setCircleHoleRadius(5f);

                    lineData = new LineData(lineDataSet);

                    XAxis xAxis = activityMyinfoBinding.myInfoPageChartView.getXAxis();
                    xAxis.setDrawLabels(false);
                    xAxis.setDrawGridLines(false);
                    xAxis.setDrawAxisLine(false);
                    xAxis.setEnabled(false);

                    YAxis yAxis = activityMyinfoBinding.myInfoPageChartView.getAxisLeft();
                    yAxis.setDrawLabels(false);
                    yAxis.setDrawGridLines(false);
                    yAxis.setDrawAxisLine(false);
                    yAxis.setEnabled(false);

                    YAxis yAxis1 = activityMyinfoBinding.myInfoPageChartView.getAxisRight();
                    yAxis1.setDrawLabels(false);
                    yAxis1.setDrawGridLines(false);
                    yAxis1.setDrawAxisLine(false);
                    yAxis1.setEnabled(false);

                    Legend legend = activityMyinfoBinding.myInfoPageChartView.getLegend();
                    legend.setEnabled(false);
                    legend.setDrawInside(false);

                    activityMyinfoBinding.myInfoPageChartView.setDescription(null);
                    activityMyinfoBinding.myInfoPageChartView.setDrawGridBackground(false);
                    activityMyinfoBinding.myInfoPageChartView.setData(lineData);
                    activityMyinfoBinding.myInfoPageChartView.setDoubleTapToZoomEnabled(false);
                    activityMyinfoBinding.myInfoPageChartView.setDrawGridBackground(false);
                    activityMyinfoBinding.myInfoPageChartView.animateY(600, Easing.EasingOption.EaseInCubic);
                    activityMyinfoBinding.myInfoPageChartView.setPinchZoom(false);
                    activityMyinfoBinding.myInfoPageChartView.setScaleEnabled(false);
                    activityMyinfoBinding.myInfoPageChartView.invalidate();

                    maxX = activityMyinfoBinding.myInfoPageChartView.getXRange();

                    CustomMarkerView marker = new CustomMarkerView(ActivityMyinfo.this,R.layout.item_chart_marker);
                    marker.setChartView(activityMyinfoBinding.myInfoPageChartView);
                    activityMyinfoBinding.myInfoPageChartView.setMarker(marker);
                }
            }
            @Override
            public void onFailure(Call<ModelMiddleChartData> call, Throwable t) {
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
}
