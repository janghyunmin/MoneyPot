package quantec.com.moneypot.Activity.Myinfo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
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

    String myname;

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

//        String[] names = new String[]{"재경", "희성", "라성", "인영"};
//        Observable<String> src = Observable.interval(3000L, TimeUnit.MILLISECONDS)
//                .map(idx -> names[idx.intValue()])
//                .take(names.length)
//                .switchMap(name -> Observable.interval(3000L, TimeUnit.MILLISECONDS)
//                        .map(unUsed -> "*" + name)
//                        .take(1)
//                );
//        src.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(getObserver());

            Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "100점";
            }
        };

        Observable<String> source = Observable.fromCallable(callable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
        source.subscribe(System.out::println);

        Log.e("1번", "1번");

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

//        String[] names = new String[]{"재경", "희성", "라성", "인영"};
////        Observable<String> src = Observable.fromArray(names)
////                .map(name -> "hello "+name);
//
////        Observable<String> src = Observable.interval(100L, TimeUnit.MILLISECONDS)
////                .map(idx -> names[idx.intValue()])
////                .take(names.length)
////                .concatMap(name -> Observable.interval(2000L, TimeUnit.MILLISECONDS)
////                .map(unUsed -> "*" + name)
////                .take(1)
////                );
//
//        Observable<String> src = Observable.interval(3000L, TimeUnit.MILLISECONDS)
//                .map(idx -> names[idx.intValue()])
//                .take(names.length)
//                .switchMap(name -> Observable.interval(2000L, TimeUnit.MILLISECONDS)
//                        .map(unUsed -> "*" + name)
//                        .take(1)
//                );
//
//                src.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(getObserver());

//        String[] names = new String[]{"이상", "이하", "중간", "하락"};
//
//        Function<String, Integer> fee = name -> {
//            int charge = 0;
//            switch (name) {
//                case "이상":
//                    charge = 1000;
//                    break;
//                case "중간":
//                    charge = 500;
//                    break;
//                case "하락":
//                    charge = 300;
//                    break;
//                case "이하":
//                    charge = 100;
//                    break;
//            }
//            return charge;
//        };
//
//
////        Observable<Integer> src = Observable.fromArray(names)
////                .filter(name -> name.startsWith("이"))
////                .map(fee);
//        Maybe<Integer> src = Observable.fromArray(names)
//                .filter(name -> name.startsWith("이"))
//                .map(fee)
//                .reduce((in1, in2) -> in1+in2);
//
////        src.subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(getObserver2());
//
//            src.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(getObserver3());
//
//        List<Po> salses = new ArrayList<>();
//        salses.add(new Po("A", 1000));
//        salses.add(new Po("B", 2000));
//        salses.add(new Po("C", 3000));
//        salses.add(new Po("B", 3000));
//        salses.add(new Po("D", 3000));
//
//        Maybe<Integer> tv = Observable.fromIterable(salses)
//                .filter(sale -> "B".equals(sale.getName()))
//                .map(sale -> sale.getPrice())
//                .reduce((sale1, sale2) -> sale1+sale2);
//
//        tv.subscribe(total -> System.out.println("총 값 : "+ total));

//        BlockingQueue<String> order = new ArrayBlockingQueue<>(100);
//        order.add("1번");
//        order.add("2번");
//        order.add("3번");
//
//        Observable<String> source = Observable.fromIterable(order);
//        source.subscribe(ordered -> System.out.println(ordered.toString()));

//        Callable<String> callable = new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                Thread.sleep(1000);
//                return "100점";
//            }
//        };
//        Observable<String> source = Observable.fromCallable(callable);
//        source.subscribe(System.out::println);

    }



    public class Po{
        String name;
        int price;

        public Po(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    private MaybeObserver<Integer> getObserver3(){
        return new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onSuccess(Integer integer) {
                Log.e("가격","가격 : "+integer);
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        };
    }

    private Observer<Integer> getObserver2(){
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e("가격","가격 : "+integer);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private Observer<String> getObserver(){
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                    Log.e("이름","참가자 : "+s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
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
