package com.quantec.moneypot.activity.invest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelPreChartList;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.activity.invest.adapter.AdapterTrDetail;
import com.quantec.moneypot.util.FomatToWon.MoneyFormatToWon;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ActivityTrDetail extends AppCompatActivity {

    TextView okBt, price;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelTrDetail> modelTrDetails;
    AdapterTrDetail adapterTrDetail;
    NestedScrollView scrollView;

    ArrayList<ModelPreChartList> modelPreChartLists;

    int count = 0;
    String num;
    double ratio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tr_detail);


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

        okBt = findViewById(R.id.okBt);
        price = findViewById(R.id.price);
        scrollView = findViewById(R.id.scrollView);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.setSmoothScrollingEnabled(true);
                scrollView.fullScroll(View.FOCUS_UP);
            }
        }, 20);


        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }
            @Override
            public boolean canScrollHorizontally() { //가로 스크롤막기
                return false;
            }
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelTrDetails = new ArrayList<>();
        adapterTrDetail = new AdapterTrDetail(modelTrDetails, this);

        recyclerView.setAdapter(adapterTrDetail);

        RxView.clicks(okBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
           finish();
        });

        modelPreChartLists = new ArrayList<>();
        Intent intent1 = getIntent();

        modelPreChartLists = intent1.getParcelableArrayListExtra("custominvest");
        price.setText(MoneyFormatToWon.moneyFormatToWon(Integer.valueOf(intent1.getStringExtra("price"))));

        ratio = (100 / modelPreChartLists.size());

        for(int idx = 0; idx < modelPreChartLists.size(); idx++){
            count++;
            if(count == 10){
                num = String.valueOf(count);

            }else{
                num = "0"+count;
            }
            modelTrDetails.add(new ModelTrDetail(num,
                    modelPreChartLists.get(idx).getName(),
                    modelPreChartLists.get(idx).getCode(),
                    String.format("%.2f", ratio)));
        }

    }
}
