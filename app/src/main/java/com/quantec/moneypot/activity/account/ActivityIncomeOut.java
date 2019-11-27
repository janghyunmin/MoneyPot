package com.quantec.moneypot.activity.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.account.adapter.AdapterIncomeOut;

import java.util.ArrayList;

public class ActivityIncomeOut extends AppCompatActivity {

    ImageView backBt;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelIncomeOut> modelIncomeOuts;
    AdapterIncomeOut adapterIncomeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_out);

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

        backBt = findViewById(R.id.backBt);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelIncomeOuts = new ArrayList<>();
        adapterIncomeOut = new AdapterIncomeOut(modelIncomeOuts, this);

        recyclerView.setAdapter(adapterIncomeOut);

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        modelIncomeOuts.add(new ModelIncomeOut("", "", "" , "" , true));
//        modelIncomeOuts.add(new ModelIncomeOut("", "", "" , "" , false));
//        modelIncomeOuts.add(new ModelIncomeOut("", "", "" , "" , false));
//        modelIncomeOuts.add(new ModelIncomeOut("", "", "" , "" , false));
//        modelIncomeOuts.add(new ModelIncomeOut("", "", "" , "" , false));
//        modelIncomeOuts.add(new ModelIncomeOut("", "", "" , "" , false));
//        modelIncomeOuts.add(new ModelIncomeOut("", "", "" , "" , false));

    }
}
