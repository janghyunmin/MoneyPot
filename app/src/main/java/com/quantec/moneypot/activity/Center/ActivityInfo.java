package com.quantec.moneypot.activity.center;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.quantec.moneypot.R;

import java.util.ArrayList;

public class ActivityInfo extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelInfo> modelInfos;
    AdapterInfo adapterInfo;

    ImageView backBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


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

        backBt = findViewById(R.id.backBt);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelInfos = new ArrayList<>();
        adapterInfo = new AdapterInfo(modelInfos, this);

        recyclerView.setAdapter(adapterInfo);


        modelInfos.add(new ModelInfo(true, "필수항목", "", "", ""));
        modelInfos.add(new ModelInfo(false, "", "서비스 이용 약관", "19.02.01", "http://www.quantec.co.kr/SettingPage/03_terms.html"));
        modelInfos.add(new ModelInfo(false, "", "개인정보 수집이용 동의", "19.02.01", "http://www.quantec.co.kr/SettingPage/03_terms.html"));
        modelInfos.add(new ModelInfo(false, "", "개인정보 제3자 제공 동의", "19.02.01", "http://www.quantec.co.kr/SettingPage/03_terms.html"));
        modelInfos.add(new ModelInfo(true, "개인정보", "", "", ""));
        modelInfos.add(new ModelInfo(false, "", "이용자의 권리 및 유의사항", "19.02.01", "http://www.quantec.co.kr/SettingPage/03_terms.html"));
        modelInfos.add(new ModelInfo(false, "", "개인정보 처리방침", "19.02.01", "http://www.quantec.co.kr/SettingPage/03_terms.html"));


        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapterInfo.setInfoItemClick(new AdapterInfo.InfoItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(ActivityInfo.this, ActivityInfoWebView.class);
                intent.putExtra("url", modelInfos.get(position).getUrl());
                startActivity(intent);
            }
        });


    }
}
