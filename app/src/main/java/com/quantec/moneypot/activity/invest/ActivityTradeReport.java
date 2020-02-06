package com.quantec.moneypot.activity.invest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelPreChartList;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.databinding.ActivityTradeReportBinding;
import com.quantec.moneypot.util.FomatToWon.MoneyFormatToWon;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ActivityTradeReport extends AppCompatActivity {

    ActivityTradeReportBinding binding;

    boolean openState;
    ArrayList<ModelPreChartList> modelPreChartLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_trade_report);

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

        modelPreChartLists = new ArrayList<>();
        Intent intent1 = getIntent();

        modelPreChartLists = intent1.getParcelableArrayListExtra("custominvest");

        binding.stock.setText(modelPreChartLists.get(0).getName()+" 외");
        binding.stockNum.setText(modelPreChartLists.size()+"건");
        binding.exPrice.setText(MoneyFormatToWon.moneyFormatToWon(Integer.valueOf(intent1.getStringExtra("price")))+"원");

        binding.totalPrice.setText(MoneyFormatToWon.moneyFormatToWon(Integer.valueOf((int) ((Math.ceil(Integer.valueOf(intent1.getStringExtra("price"))*0.1))+Integer.valueOf(intent1.getStringExtra("price")))))+"원");
        RxView.clicks(binding.warningLayout).throttleFirst(700, TimeUnit.MILLISECONDS).subscribe(empty -> {
            openState = !openState;

            changeVisibility(openState);

            binding.scrollView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.scrollView.setSmoothScrollingEnabled(true);
                    binding.scrollView.fullScroll(View.FOCUS_DOWN);
                }
            }, 500);
        });

        RxView.clicks(binding.detailBt).throttleFirst(700, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Intent intent = new Intent(ActivityTradeReport.this, ActivityTrDetail.class);
            intent.putParcelableArrayListExtra("custominvest", modelPreChartLists);
            intent.putExtra("price", intent1.getStringExtra("price"));
            startActivity(intent);
        });


        RxView.clicks(binding.prevBt).throttleFirst(700, TimeUnit.MILLISECONDS).subscribe(empty -> {
            finish();
        });

        RxView.clicks(binding.nextBt).throttleFirst(700, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Toast.makeText(ActivityTradeReport.this.getApplicationContext(), "투자 진행 합니다.", Toast.LENGTH_SHORT).show();
        });

    }//onCreate 끝.


    /**
     * 클릭된 Item의 상태 변경
     * @param isExpanded Item을 펼칠 것인지 여부
     */
    private void changeVisibility(final boolean isExpanded) {

        // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
//        if(isExpanded){
//            warningImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_gray));
//        }else{
//            warningImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_gray));
//        }

        binding.layout2.measure(View.MeasureSpec.UNSPECIFIED , View.MeasureSpec.UNSPECIFIED );
        int height = binding.layout2.getMeasuredHeight();
        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(1, height) : ValueAnimator.ofInt(height, 1);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // value는 height 값
                int value = (int) animation.getAnimatedValue();

                // imageView의 높이 변경
                binding.layout2.getLayoutParams().height = value;
                binding.layout2.requestLayout();
                // imageView가 실제로 사라지게하는 부분
                binding.layout2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        });
        // Animation start
        va.start();
    }

}
