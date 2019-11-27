package com.quantec.moneypot.activity.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.account.adapter.AdapterMyAccount;

import java.util.ArrayList;

public class ActivityMyAccount extends AppCompatActivity {

    ImageView backBt;
    LinearLayout historyBt;
    TextView accountBt;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelMyAccount> modelMyAccounts;
    AdapterMyAccount adapterMyAccount;

    ScrollView scrollView;

    ConstraintLayout AccountedLayout, AccountingLayout, noAccountLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        // 스테이터스 바 색상 변경 -> 화이트
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

        AccountedLayout = findViewById(R.id.AccountedLayout);
        AccountingLayout = findViewById(R.id.AccountingLayout);
        noAccountLayout = findViewById(R.id.noAccountLayout);

        AccountedLayout.setVisibility(View.VISIBLE);


        scrollView = findViewById(R.id.scrollView);
        scrollView.smoothScrollTo(0,0);

        backBt = findViewById(R.id.backBt);
        historyBt = findViewById(R.id.historyBt);
        accountBt = findViewById(R.id.accountBt);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelMyAccounts = new ArrayList<>();
        modelMyAccounts.add(new ModelMyAccount("", "", "" ,0, 0, false, false));

        adapterMyAccount = new AdapterMyAccount(modelMyAccounts, this);
        recyclerView.setAdapter(adapterMyAccount);


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


        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        historyBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMyAccount.this, ActivityIncomeOut.class);
                startActivity(intent);
            }
        });

        modelMyAccounts.add(new ModelMyAccount("", "", "" ,0, 0, false, false));
        modelMyAccounts.add(new ModelMyAccount("", "", "" ,0, 0, false, false));
        modelMyAccounts.add(new ModelMyAccount("", "", "" ,0, 0, false, false));

        modelMyAccounts.add(new ModelMyAccount("", "", "" ,0, 0, false, true));

        adapterMyAccount.setBottomClick(new AdapterMyAccount.BottomClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(ActivityMyAccount.this, ActivityInvestHistory.class);
                startActivity(intent);
            }
        });
    }
}
