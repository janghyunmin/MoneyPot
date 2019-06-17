package quantec.com.moneypot.Activity.PotDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import quantec.com.moneypot.Activity.PotDetail.AdpaterPotDetail.AdapterPotDetail;
import quantec.com.moneypot.R;

public class ActivityPotDetail extends AppCompatActivity {

    PieChart pieChart;
    LinearLayout layout2;
    TextView warningTitle, investBt, categoryBt1, categoryBt2, categoryBt3, bondRate, stockRate;

    boolean openState;

    ScrollView scrollView;
    ImageView warningImage;

    public static final int[] JOYFUL_COLORS = {
            Color.rgb(126, 111, 123),Color.rgb(255, 114, 114)
    };

    ScrollDisabledRecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelPotDetail> modelPotDetails;
    AdapterPotDetail adapterPotDetail;

    ConstraintLayout getView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_detail);

        //                    스테이터스 바 색상 변경 -> 화이트
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

        getView = findViewById(R.id.includePotDetail);
        scrollView = findViewById(R.id.scrollView);

        recyclerView = getView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelPotDetails = new ArrayList<>();
        adapterPotDetail = new AdapterPotDetail(modelPotDetails, this);

        recyclerView.setAdapter(adapterPotDetail);


        categoryBt1 = getView.findViewById(R.id.categoryBt1);
        categoryBt2 = getView.findViewById(R.id.categoryBt2);
        categoryBt3 = getView.findViewById(R.id.categoryBt3);

        bondRate = getView.findViewById(R.id.bondRate);
        stockRate = getView.findViewById(R.id.stockRate);

        pieChart = getView.findViewById(R.id.pieChart);

        pieChart.setTransparentCircleRadius(0f);
        pieChart.setDrawEntryLabels(false);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationEnabled(false);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(50f,"채권"));
        yValues.add(new PieEntry(50f,"주식"));

        bondRate.setText("50%");
        stockRate.setText("50%");

        pieChart.animateY(600, Easing.EasingOption.EaseInOutCubic); //애니메이션
        pieChart.setDrawCenterText(false);

        Legend l = pieChart.getLegend();
        l.setDrawInside(false);
        l.setEnabled(false);

        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setColors(JOYFUL_COLORS);
        dataSet.setDrawValues(false);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();

        layout2 = getView.findViewById(R.id.layout2);
        warningTitle = getView.findViewById(R.id.warningTitle);

        warningTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openState = !openState;

                changeVisibility(openState);

                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.setSmoothScrollingEnabled(true);
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                }, 500);

            }
        });

        investBt = getView.findViewById(R.id.investBt);
        warningImage = getView.findViewById(R.id.warningImage);

        modelPotDetails.add(new ModelPotDetail(false, "", "", "", false));
        modelPotDetails.add(new ModelPotDetail(false, "대세로 떠로은 전기자전거와 킥보드 이용자 안전은","경향신문 | 2019녀 04월 10일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "자전거업계, 전지자전거로 미세먼지 악재 '정면돌파'","아주경제 | 2019년 03월 28일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "삼천리 자전거, 팬텀제로 핑크에디션 출시","트래블바이크뉴스 | 2019년 03월 18일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "서울 인구 100만명 공유 자전거 이용족들","메일신문 | 2019년 03월 02일", "",false));

        modelPotDetails.add(new ModelPotDetail(false, "자전거업계, 전지자전거로 미세먼지 악재 '정면돌파'","아주경제 | 2019년 03월 28일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "삼천리 자전거, 팬텀제로 핑크에디션 출시","트래블바이크뉴스 | 2019년 03월 18일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "서울 인구 100만명 공유 자전거 이용족들","메일신문 | 2019년 03월 02일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "자전거업계, 전지자전거로 미세먼지 악재 '정면돌파'","아주경제 | 2019년 03월 28일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "삼천리 자전거, 팬텀제로 핑크에디션 출시","트래블바이크뉴스 | 2019년 03월 18일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "서울 인구 100만명 공유 자전거 이용족들","메일신문 | 2019년 03월 02일", "",false));
        modelPotDetails.add(new ModelPotDetail(false, "", "", "",false));

        adapterPotDetail.notifyDataSetChanged();

        adapterPotDetail.setAddViewBtClick(new AdapterPotDetail.AddViewBtClick() {
            @Override
            public void onClick(int position) {

                if(modelPotDetails.get(0).isAddViewState()){
                    modelPotDetails.get(0).setAddViewState(false);
                }else{
                    modelPotDetails.get(0).setAddViewState(true);
                }

                adapterPotDetail.notifyDataSetChanged();

            }
        });

        adapterPotDetail.setPotDetailItemClick(new AdapterPotDetail.PotDetailItemClick() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(ActivityPotDetail.this, ActivityArticle.class);
                intent.putExtra("url", modelPotDetails.get(position).getUrl());
                startActivity(intent);

            }
        });

        categoryBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bondRate.setText("25%");
                stockRate.setText("75%");

                yValues.clear();
                yValues.add(new PieEntry(25f,"채권"));
                yValues.add(new PieEntry(75f,"주식"));

                pieChart.animateY(600, Easing.EasingOption.EaseInOutCubic); //애니메이션
                pieChart.setDrawCenterText(false);

                Legend l = pieChart.getLegend();
                l.setDrawInside(false);
                l.setEnabled(false);

                PieDataSet dataSet = new PieDataSet(yValues,"");
                dataSet.setColors(JOYFUL_COLORS);
                dataSet.setDrawValues(false);

                PieData data = new PieData(dataSet);
                pieChart.setData(data);
                pieChart.invalidate();
            }
        });

        categoryBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        categoryBt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }// onCreate 끝


    /**
     * 클릭된 Item의 상태 변경
     * @param isExpanded Item을 펼칠 것인지 여부
     */
    private void changeVisibility(final boolean isExpanded) {

        // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용

        if(isExpanded){
            warningImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_gray));
        }else{
            warningImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_gray));
        }

        layout2.measure(View.MeasureSpec.UNSPECIFIED , View.MeasureSpec.UNSPECIFIED );
        int height = layout2.getMeasuredHeight();

        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(1, height) : ValueAnimator.ofInt(height, 1);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(450);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // value는 height 값
                int value = (int) animation.getAnimatedValue();

                // imageView의 높이 변경
                layout2.getLayoutParams().height = value;
                layout2.requestLayout();
                // imageView가 실제로 사라지게하는 부분
                layout2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            }
        });
        // Animation start
        va.start();
    }


}
