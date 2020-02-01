package com.quantec.moneypot.activity.PotDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelPotSimul;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelSimulCode;
import com.quantec.moneypot.activity.article.ActivityArticle;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.activity.simulation.Code;
import com.quantec.moneypot.activity.webview.ActivityWebViewArticle;
import com.quantec.moneypot.databinding.ActivitySingleDetailBinding;
import com.quantec.moneypot.datamodel.nmodel.ModelCustomDetail;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.util.DecimalScale.DecimalScale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Databinding 으로 activity 내에 include layout 이용시 먼저 해당 액티비티를 먼저 바인딩 작업을 해준뒤에 include 작업을 해준다
 * 주의할점으로 위와 같은 진행 순서를 두는 이유가 include 에 databinding 시 type 경로에 해당액티비티bing 을 넣어주는데 먼저 위 작업을 해주지 않으면 액티비티의 경로로 들어와서 에러가 뜬다
 * 제대로 동작하려면 액티비티 경로가 아닌 databinding 경로에서 얻어야 된다.
 *
 */

public class ActivitySingleDetail extends AppCompatActivity {

    ActivitySingleDetailBinding binding;
    GridLayoutManager gridLayoutManager;
    AdapterSingleDetail adapterSingleDetail;
    ArrayList<ModelSingleDetail> modelSingleDetails;

    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelArticle> modelArticles, modelArticlesAll;
    AdapterArticle adapterArticle;

    RecyclerView.LayoutManager layoutManager2;
    ArrayList<ModelLikeEnter> modelLikeEnters;
    AdapterLikeEnter adapterLikeEnter;

    LineChart chartView;
    List<Entry> entries, monTh1Entries, monTh3Entries, monTh6Entries;
    LineDataSet lineDataSet2;
    LineData lineData2;

    //종목 팔로우 여부
    boolean followState = false;
    //팔로우 클릭 여부
    boolean followClick = false;
    String code, title;
    int position, follow;

    float currentX, maxX;

    boolean monTh1 = true;
    boolean monTh3 = true;
    boolean monTh6 = true;

    LinearLayout loading;

    TextView preChartBt, topTitle, topCode;
    int nowPage, maxPage;

    int newsPlus, newsMinus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_detail);

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        topTitle = findViewById(R.id.topTitle);
        topCode = findViewById(R.id.topCode);

        loading = findViewById(R.id.loading);
        loading.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        entries = new ArrayList<>();
        monTh1Entries = new ArrayList<>();
        monTh3Entries = new ArrayList<>();
        monTh6Entries = new ArrayList<>();

        Intent intent1 = getIntent();
        code = intent1.getStringExtra("code");
        title = intent1.getStringExtra("title");
        position = intent1.getIntExtra("potPosition", 0);

        binding.topTitle.setText(title);
        binding.topCode.setText(code);

        chartView = findViewById(R.id.chartView);
        chartView.setNoDataText("");
        chartView.setNoDataTextColor(R.color.text_white_color);
        chartView.invalidate();

        preChartBt = binding.includeSingleDetail.aBt;
        changedChartBt(preChartBt);


        Call<ModelCustomDetail> getReList = RetrofitClient.getInstance().getService().getDetailData("application/json", 0, "AMD", "all", 701);
        getReList.enqueue(new Callback<ModelCustomDetail>() {
            @Override
            public void onResponse(Call<ModelCustomDetail> call, Response<ModelCustomDetail> response) {
                if (response.code() == 200) {

                    binding.includeSingleDetail.price.setText(String.valueOf(response.body().getContent().getCoreData().getCore().getTotPrice()));
                    binding.includeSingleDetail.rate.setText(String.format("%.2f", response.body().getContent().getCoreData().getCore().getRate())+"%");
                    if(response.body().getContent().getCoreData().getCore().getRate() < 0){
                        binding.includeSingleDetail.rate.setTextColor(getResources().getColor(R.color.c_4e7cff));
                    }else{
                        binding.includeSingleDetail.rate.setTextColor(getResources().getColor(R.color.c_f02654));
                    }

                    for(int index = 0; index < response.body().getContent().getTags().size(); index++){
                        Chip chip = new Chip(ActivitySingleDetail.this);
                        chip.setText("#"+response.body().getContent().getTags().get(index).getName());
                        chip.setTextColor(getResources().getColor(R.color.c_3d3f42));
                        chip.setTextSize(11f);
                        chip.setChipBackgroundColorResource(R.color.c_ffffff);
                        chip.setChipStrokeWidth(1.5f);
                        chip.setChipStrokeColorResource(R.color.c_eaeaea);
                        chip.setChipStartPadding(15f);
                        chip.setChipEndPadding(12f);
                        chip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "검색으로 이동", Toast.LENGTH_SHORT).show();
                            }
                        });
                        binding.includeSingleDetail.chipGroup.addView(chip);
                    }

                    for(int xCount = 0; xCount < response.body().getContent().getCoreData().getChart().size() ; xCount++) {

                        entries.add(new Entry(xCount,
                                DecimalScale.decimalScale2(String.valueOf(response.body().getContent().getCoreData().getChart().get(xCount).getExp()), 2, 2),
                                response.body().getContent().getCoreData().getChart().get(xCount).getDate()
                        ));
                    }

                    drawLineChart(entries);

                    if(response.body().getContent().getNews().getTotal() < 3){
                        nowPage = response.body().getContent().getNews().getTotal();
                    }else{
                        nowPage = 3;
                    }

                    if(response.body().getContent().getNews().getTotal() > 10){
                        maxPage = 10;
                    }else{
                        maxPage = response.body().getContent().getNews().getTotal();
                    }

                    if(response.body().getContent().getNews().getTotal() == 0){
                        modelArticles.add(new ModelArticle(response.body().getContent().getNews().getTotal(),"",
                                "",
                                "",
                                "",
                                "",
                                true, false, false));
                    }else{

                        for(int index = 0; index < maxPage; index++){
                            modelArticlesAll.add(new ModelArticle(response.body().getContent().getNews().getTotal(),
                                    ReplaceTag(response.body().getContent().getNews().getNewsData().get(index).getTitle(), "decode"),
                                    response.body().getContent().getNews().getNewsData().get(index).getFrom(),
                                    response.body().getContent().getNews().getNewsData().get(index).getDate(),
                                    response.body().getContent().getNews().getNewsData().get(index).getNewsUrl(),
                                    response.body().getContent().getNews().getNewsData().get(index).getImgUrl(),
                                    false, false, false));
                        }

                        for(int index = 0; index < nowPage; index++){
                            modelArticles.add(new ModelArticle(response.body().getContent().getNews().getTotal(),
                                    ReplaceTag(response.body().getContent().getNews().getNewsData().get(index).getTitle(), "decode"),
                                    response.body().getContent().getNews().getNewsData().get(index).getFrom(),
                                    response.body().getContent().getNews().getNewsData().get(index).getDate(),
                                    response.body().getContent().getNews().getNewsData().get(index).getNewsUrl(),
                                    response.body().getContent().getNews().getNewsData().get(index).getImgUrl(),
                                    false, false, false));
                        }


                        if(response.body().getContent().getNews().getTotal() == 3){
                            modelArticles.add(new ModelArticle(response.body().getContent().getNews().getTotal(),"",
                                    "",
                                    "",
                                    "",
                                    "",
                                    false, true, false));
                        }else if(response.body().getContent().getNews().getTotal() > 3){
                            modelArticles.add(new ModelArticle(response.body().getContent().getNews().getTotal(),"",
                                    "",
                                    "",
                                    "",
                                    "",
                                    false, true, true));
                        }
                    }

                    adapterArticle.notifyDataSetChanged();

                    newsPlus = response.body().getContent().getNewsPlus();
                    newsMinus = response.body().getContent().getNewsMinus();

                    binding.includeSingleDetail.progress.setProgressBackgroundColor(getResources().getColor(R.color.life_line));
                    binding.includeSingleDetail.progress.setProgressColor(getResources().getColor(R.color.c_4e7cff));
                    binding.includeSingleDetail.progress.setProgress(33f);
                    binding.includeSingleDetail.progressText.setText("33");

                    binding.includeSingleDetail.progress2.setProgressBackgroundColor(getResources().getColor(R.color.life_line));
                    binding.includeSingleDetail.progress2.setProgressColor(getResources().getColor(R.color.c_a8a7a7));
                    binding.includeSingleDetail.progress2.setProgress(20f);
                    binding.includeSingleDetail.progressText2.setText("20");

                    if(newsPlus > newsMinus){
                        binding.includeSingleDetail.aiImage.setBackgroundResource(R.drawable.img_positive);
                        binding.includeSingleDetail.aiText.setText("긍정적");
                        binding.includeSingleDetail.aiText.setTextColor(getResources().getColor(R.color.c_4e7cff));
                    }else if(newsPlus < newsMinus){
                        binding.includeSingleDetail.aiImage.setBackgroundResource(R.drawable.img_negative);
                        binding.includeSingleDetail.aiText.setText("부정적");
                        binding.includeSingleDetail.aiText.setTextColor(getResources().getColor(R.color.c_9a9a9a));
                    }else if((newsPlus == 0) & (newsMinus == 0)) {
                        binding.includeSingleDetail.aiLayout2.setVisibility(View.GONE);
                        binding.includeSingleDetail.aiEmpty.setVisibility(View.VISIBLE);
                    }else{
                        binding.includeSingleDetail.aiImage.setBackgroundResource(R.drawable.img_neutral);
                        binding.includeSingleDetail.aiText.setTextColor(getResources().getColor(R.color.c_cccccc));
                        binding.includeSingleDetail.aiText.setText("평가동일");
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelCustomDetail> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });

//        Call<ModelCustomDetail> getReList = RetrofitClient.getInstance().getService().getDetailData("application/json", 0, "AMD", "one", 701);
//        getReList.enqueue(new Callback<ModelCustomDetail>() {
//            @Override
//            public void onResponse(Call<ModelCustomDetail> call, Response<ModelCustomDetail> response) {
//                if (response.code() == 200) {
//
//                    binding.includeSingleDetail.price.setText(String.valueOf(response.body().getContent().getCoreData().getCore().getTotPrice()));
//                    binding.includeSingleDetail.rate.setText(String.format("%.2f", response.body().getContent().getCoreData().getCore().getRate())+"%");
//                    if(response.body().getContent().getCoreData().getCore().getRate() < 0){
//                        binding.includeSingleDetail.rate.setTextColor(getResources().getColor(R.color.c_4e7cff));
//                    }else{
//                        binding.includeSingleDetail.rate.setTextColor(getResources().getColor(R.color.c_f02654));
//                    }
//
//                    for(int index = 0; index < response.body().getContent().getTags().size(); index++){
//                        Chip chip = new Chip(ActivitySingleDetail.this);
//                        chip.setText("#"+response.body().getContent().getTags().get(index).getName());
//                        chip.setTextColor(getResources().getColor(R.color.c_3d3f42));
//                        chip.setTextSize(11f);
//                        chip.setChipBackgroundColorResource(R.color.c_ffffff);
//                        chip.setChipStrokeWidth(1.5f);
//                        chip.setChipStrokeColorResource(R.color.c_eaeaea);
//                        chip.setChipStartPadding(15f);
//                        chip.setChipEndPadding(12f);
//                        chip.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(getApplicationContext(), "검색으로 이동", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        binding.includeSingleDetail.chipGroup.addView(chip);
//                    }
//
//                    for(int xCount = 0; xCount < response.body().getContent().getCoreData().getChart().size() ; xCount++) {
//
//                        entries.add(new Entry(xCount,
//                                DecimalScale.decimalScale2(String.valueOf(response.body().getContent().getCoreData().getChart().get(xCount).getExp()), 2, 2),
//                                response.body().getContent().getCoreData().getChart().get(xCount).getDate()
//                        ));
//                    }
//
//                    drawLineChart(entries);
//
//                    if(response.body().getContent().getNewsData().getTotal() == 0){
//                        modelArticles.add(new ModelArticle(response.body().getContent().getNewsData().getTotal(),"",
//                                "",
//                                "",
//                                "",
//                                "",
//                                true, false));
//                    }else{
//
//                        if(response.body().getContent().getNewsData().getTotal() < 3){
//                            for(int index = 0; index < response.body().getContent().getNewsData().getTotal(); index++){
//                                modelArticles.add(new ModelArticle(response.body().getContent().getNewsData().getTotal(),
//                                        ReplaceTag(response.body().getContent().getNewsData().getNewsList().get(index).getTitle(), "decode"),
//                                        response.body().getContent().getNewsData().getNewsList().get(index).getFrom(),
//                                        response.body().getContent().getNewsData().getNewsList().get(index).getDate(),
//                                        response.body().getContent().getNewsData().getNewsList().get(index).getNewsUrl(),
//                                        response.body().getContent().getNewsData().getNewsList().get(index).getImgUrl(),
//                                        false, false));
//                            }
//                        }else if(response.body().getContent().getNewsData().getTotal() == 3){
//                            for(int index = 0; index < response.body().getContent().getNewsData().getTotal(); index++){
//                                modelArticles.add(new ModelArticle(response.body().getContent().getNewsData().getTotal(),
//                                        ReplaceTag(response.body().getContent().getNewsData().getNewsList().get(index).getTitle(), "decode"),
//                                        response.body().getContent().getNewsData().getNewsList().get(index).getFrom(),
//                                        response.body().getContent().getNewsData().getNewsList().get(index).getDate(),
//                                        response.body().getContent().getNewsData().getNewsList().get(index).getNewsUrl(),
//                                        response.body().getContent().getNewsData().getNewsList().get(index).getImgUrl(),
//                                        false, false));
//                            }
//
//                            modelArticles.add(new ModelArticle(response.body().getContent().getNewsData().getTotal(),"",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    false, true));
//                        }
//
////                        for(int index = 0; index < response.body().getContent().getNewsData().getTotal(); index++){
////                            modelArticles.add(new ModelArticle(response.body().getContent().getNewsData().getTotal(),
////                                    ReplaceTag(response.body().getContent().getNewsData().getNewsList().get(index).getTitle(), "decode"),
////                                    response.body().getContent().getNewsData().getNewsList().get(index).getFrom(),
////                                    response.body().getContent().getNewsData().getNewsList().get(index).getDate(),
////                                    response.body().getContent().getNewsData().getNewsList().get(index).getNewsUrl(),
////                                    response.body().getContent().getNewsData().getNewsList().get(index).getImgUrl(),
////                                    false, false));
////                        }
//                        // 1개~2개 / 3개 / 3개 ~ 10개 / 10개 초과
//
//                    }
//
//                    adapterArticle.notifyDataSetChanged();
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelCustomDetail> call, Throwable t) {
//                Log.e("실패","실패"+t.getMessage());
//            }
//        });

//        binding.includeSingleDetail.progress.setProgressBackgroundColor(getResources().getColor(R.color.life_line));
//        binding.includeSingleDetail.progress.setProgressColor(getResources().getColor(R.color.c_4e7cff));
//        binding.includeSingleDetail.progress.setProgress(50f);
//
//        binding.includeSingleDetail.progress2.setProgressBackgroundColor(getResources().getColor(R.color.life_line));
//        binding.includeSingleDetail.progress2.setProgressColor(getResources().getColor(R.color.c_a8a7a7));
//        binding.includeSingleDetail.progress2.setProgress(50f);

        binding.includeSingleDetail.articleRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        binding.includeSingleDetail.articleRecyclerView.setLayoutManager(layoutManager);

        modelArticles = new ArrayList<>();
        modelArticlesAll = new ArrayList<>();

        adapterArticle = new AdapterArticle(modelArticles, this);
        binding.includeSingleDetail.articleRecyclerView.setAdapter(adapterArticle);

        binding.includeSingleDetail.articleRecyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

//        modelArticles.add(new ModelArticle("제동 걸린 페이스북 가상화폐 '리브라' 누가? 왜? 해법은?", "", "", "", ""));
//        modelArticles.add(new ModelArticle("애플이 2020년에 '아이폰 4' 디자인 다시 부활시킬 전망이다", "", "", "", ""));
//        modelArticles.add(new ModelArticle("신한카드, 넷플릭스와 '전용 멤버십' 내놓는다", "", "", "", ""));
//
//        adapterArticle.notifyDataSetChanged();

        binding.includeSingleDetail.likeEnterRecyclerView.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        binding.includeSingleDetail.likeEnterRecyclerView.setLayoutManager(layoutManager2);

        modelLikeEnters = new ArrayList<>();
        adapterLikeEnter = new AdapterLikeEnter(modelLikeEnters, this);

        binding.includeSingleDetail.likeEnterRecyclerView.addItemDecoration(new DecorationItemHorizontal(this, 10));

        binding.includeSingleDetail.likeEnterRecyclerView.setAdapter(adapterLikeEnter);

        modelLikeEnters.add(new ModelLikeEnter("","애플","",23.44));
        modelLikeEnters.add(new ModelLikeEnter("","아마존","",15.66));
        modelLikeEnters.add(new ModelLikeEnter("","넷플릭스","",16.44));
        modelLikeEnters.add(new ModelLikeEnter("","페이스북","",5.33));
        modelLikeEnters.add(new ModelLikeEnter("","구글","",8.12));
        modelLikeEnters.add(new ModelLikeEnter("","페이코","",1.4));
        modelLikeEnters.add(new ModelLikeEnter("","삼성","",5.83));

        adapterLikeEnter.notifyDataSetChanged();

        RxView.clicks(binding.followBt).throttleFirst(600, TimeUnit.MILLISECONDS).subscribe(empty -> {

            followClick = true;

            if(followState){
                follow = 1;
            }else{
                follow = 0;
            }

        });

        binding.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(followClick){
                    Intent intent = new Intent();
                    intent.putExtra("searched_code", code);
                    intent.putExtra("potPosition", position);
                    intent.putExtra("potFollow", follow);
                    setResult(500, intent);
                    finish();
                }
                else{
                    finish();
                }
            }
        });


        RxView.clicks(binding.includeSingleDetail.m1Bt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            if(monTh1) {
                loading.setVisibility(View.VISIBLE);
                changedChartBt(binding.includeSingleDetail.m1Bt);
                monTh1 = false;
                chartData("one");
            }else{
                changedChartBt(binding.includeSingleDetail.m1Bt);
                drawLineChart(monTh1Entries);
            }
        });
        RxView.clicks(binding.includeSingleDetail.m3Bt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            if(monTh3){
                loading.setVisibility(View.VISIBLE);
                changedChartBt(binding.includeSingleDetail.m3Bt);
                monTh3 = false;
                chartData("thr");
            }else{
                changedChartBt(binding.includeSingleDetail.m3Bt);
                drawLineChart(monTh3Entries);
            }
        });
        RxView.clicks(binding.includeSingleDetail.m6Bt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            if(monTh6){
                loading.setVisibility(View.VISIBLE);
                changedChartBt(binding.includeSingleDetail.m6Bt);
                monTh6 = false;
                chartData("six");
            }else{
                changedChartBt(binding.includeSingleDetail.m6Bt);
                drawLineChart(monTh6Entries);
            }
        });
        RxView.clicks(binding.includeSingleDetail.aBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            changedChartBt(binding.includeSingleDetail.aBt);
            drawLineChart(entries);
        });

        adapterArticle.setArticleAddBtClick(new AdapterArticle.ArticleAddBtClick() {
            @Override
            public void onClick(int position) {

                if(nowPage >= 10){

                    Intent intent = new Intent(ActivitySingleDetail.this, ActivityArticle.class);
                    intent.putExtra("code", code);
                    startActivity(intent);

                }else{

                    if((maxPage - nowPage) > 3){

                        if(nowPage == 3){

                            modelArticles.remove(modelArticles.size()-1);
                            for(int index = 3; index < 6; index++){
                                modelArticles.add(new ModelArticle(modelArticlesAll.get(index).getTotal(),
                                        modelArticlesAll.get(index).getTitle(),
                                        modelArticlesAll.get(index).getNewspaper(),
                                        modelArticlesAll.get(index).getDate(),
                                        modelArticlesAll.get(index).getUrl(),
                                        modelArticlesAll.get(index).getImg(),
                                        false, false, false));
                            }
                            nowPage = 6;
                            modelArticles.add(new ModelArticle(0,"",
                                    "",
                                    "",
                                    "",
                                    "",
                                    false, true, true));

                        }else{

                            modelArticles.remove(modelArticles.size()-1);
                            for(int index = 6; index < 10; index++){
                                modelArticles.add(new ModelArticle(modelArticlesAll.get(index).getTotal(),
                                        modelArticlesAll.get(index).getTitle(),
                                        modelArticlesAll.get(index).getNewspaper(),
                                        modelArticlesAll.get(index).getDate(),
                                        modelArticlesAll.get(index).getUrl(),
                                        modelArticlesAll.get(index).getImg(),
                                        false, false, false));
                            }
                            nowPage = 10;
                            modelArticles.add(new ModelArticle(0,"",
                                    "",
                                    "",
                                    "",
                                    "",
                                    false, true, true));
                        }

                    }else{
                        modelArticles.remove(modelArticles.size() - 1);
                        for (int index = nowPage; index < maxPage; index++) {
                            modelArticles.add(new ModelArticle(modelArticlesAll.get(index).getTotal(),
                                    modelArticlesAll.get(index).getTitle(),
                                    modelArticlesAll.get(index).getNewspaper(),
                                    modelArticlesAll.get(index).getDate(),
                                    modelArticlesAll.get(index).getUrl(),
                                    modelArticlesAll.get(index).getImg(),
                                    false, false, false));
                        }
                        modelArticles.add(new ModelArticle(0, "",
                                "",
                                "",
                                "",
                                "",
                                false, true, false));
                    }
                }
                adapterArticle.notifyDataSetChanged();
            }
        });

        adapterArticle.setArticleEnterClick(new AdapterArticle.ArticleEnterClick() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(ActivitySingleDetail.this, ActivityWebViewArticle.class);
                intent.putExtra("url", modelArticles.get(position).getUrl());
                startActivity(intent);

            }
        });

    }// onCreate 끝


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

        CustomMarkerView marker = new CustomMarkerView(ActivitySingleDetail.this, R.layout.item_chart_marker);
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


        Code code1 = new Code();
        code1.setCode("AAPL");
        code1.setType(0);
        code1.setPtCode("");
        code1.setWeight(0);

        codes.add(code1);

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
                        }else if(date.equals("thr")){
                            drawLineChart(monTh3Entries);
                        }else{
                            drawLineChart(monTh6Entries);
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

    public String ReplaceTag(String Expression, String type){
        String result = "";
        if (Expression==null || Expression.equals("")) return "";

        if (type == "encode") {
            result = ReplaceString(Expression, "&", "&amp;");
            result = ReplaceString(result, "\"", "&quot;");

            result = ReplaceString(result, "'", "&apos;");
            result = ReplaceString(result, "<", "&lt;");
            result = ReplaceString(result, ">", "&gt;");
            result = ReplaceString(result, "\r", "<br>");
            result = ReplaceString(result, "\n", "<p>");
        }
        else if (type == "decode") {
            result = ReplaceString(Expression, "&amp;", "&");
            result = ReplaceString(result, "&quot;", "\"");

            result = ReplaceString(result, "&apos;", "'");
            result = ReplaceString(result, "&lt;", "<");
            result = ReplaceString(result, "&gt;", ">");
            result = ReplaceString(result, "<br>", "\r");
            result = ReplaceString(result, "<p>", "\n");
        }

        return result;
    }

    public String ReplaceString(String Expression, String Pattern, String Rep)
    {
        if (Expression==null || Expression.equals("")) return "";

        int s = 0;
        int e = 0;
        StringBuffer result = new StringBuffer();

        while ((e = Expression.indexOf(Pattern, s)) >= 0) {
            result.append(Expression.substring(s, e));
            result.append(Rep);
            s = e + Pattern.length();
        }
        result.append(Expression.substring(s));
        return result.toString();
    }


}
