package com.quantec.moneypot.activity.invest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelPreChartList;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.activity.invest.adapter.AdapterCustomInvest;
import com.quantec.moneypot.util.FomatToWon.MoneyFormatToWon;
import com.quantec.moneypot.util.editcomma.NumberTextWatcher;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ActivityCustomInvest extends AppCompatActivity {

    ArrayList<ModelPreChartList> modelPreChartLists;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelInvestList> modelInvestLists;
    AdapterCustomInvest adapterCustomInvest;

    TextView num, nextBt;
    double ratio;
    EditText editText;

    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_invest);

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

        editText = findViewById(R.id.editText);
        nextBt = findViewById(R.id.nextBt);

        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        Intent intent = getIntent();
        editText.setHint(MoneyFormatToWon.moneyFormatToWon(intent.getIntExtra("price", 0))+"원");
        price = intent.getIntExtra("price", 0);

        num = findViewById(R.id.num);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelInvestLists = new ArrayList<>();
        adapterCustomInvest = new AdapterCustomInvest(modelInvestLists, this);
        recyclerView.setAdapter(adapterCustomInvest);

        modelPreChartLists = new ArrayList<>();
        Intent intent1 = getIntent();
        modelPreChartLists = intent1.getParcelableArrayListExtra("custominvest");

        ratio = (100 / modelPreChartLists.size());

        for(ModelPreChartList data : modelPreChartLists){
            modelInvestLists.add(new ModelInvestList(data.getName(), data.getCode(), data.getRate(), String.format("%.2f", ratio)));
        }

        num.setText(String.valueOf(modelPreChartLists.size()));

        RxView.clicks(nextBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {

            if(!editText.getText().toString().isEmpty() && !editText.getText().toString().equals("")){

                if(price <= Integer.valueOf(editText.getText().toString().replaceAll(",", ""))){

                    Intent intent2 = new Intent(ActivityCustomInvest.this, ActivityTradeReport.class);
                    intent2.putExtra("price", editText.getText().toString().replaceAll(",", ""));
                    intent2.putParcelableArrayListExtra("custominvest", modelPreChartLists);
                    startActivity(intent2);
                }else{
                    Toast.makeText(ActivityCustomInvest.this.getApplicationContext(), "최소 투자 금액이상을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(ActivityCustomInvest.this.getApplicationContext(), "투자 금액을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            }

        });

        editText.addTextChangedListener(new NumberTextWatcher(editText));

    }
}
