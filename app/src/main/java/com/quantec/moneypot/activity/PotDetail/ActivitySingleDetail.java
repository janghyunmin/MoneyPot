package com.quantec.moneypot.activity.PotDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import com.quantec.moneypot.R;
import com.quantec.moneypot.databinding.ActivitySingleDetailBinding;


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
    ArrayList<ModelArticle> modelArticles;
    AdapterArticle adapterArticle;

    RecyclerView.LayoutManager layoutManager2;
    ArrayList<ModelLikeEnter> modelLikeEnters;
    AdapterLikeEnter adapterLikeEnter;

    List<Entry> stableEntries;
    LineDataSet lineDataSet2;
    LineData lineData2;

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

        stableEntries = new ArrayList<>();

        Chip chip = new Chip(this);
        chip.setText("#애플");
        chip.setChipBackgroundColorResource(R.color.text_white_color);
        chip.setChipStrokeWidth(1f);
        chip.setChipStrokeColorResource(R.color.gray_brown_color);
        chip.setChipStartPadding(15f);
        chip.setChipEndPadding(15f);
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivitySingleDetail.this, "검색으로 이동", Toast.LENGTH_SHORT).show();
            }
        });

        binding.includeSingleDetail.chipGroup.addView(chip);

        Chip chip1 = new Chip(this);
        chip1.setText("#구글");
        chip1.setChipBackgroundColorResource(R.color.text_white_color);
        chip1.setChipStrokeWidth(1f);
        chip1.setChipStrokeColorResource(R.color.gray_brown_color);
        chip1.setChipStartPadding(15f);
        chip1.setChipEndPadding(15f);
        chip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivitySingleDetail.this, "검색으로 이동", Toast.LENGTH_SHORT).show();
            }
        });
        binding.includeSingleDetail.chipGroup.addView(chip1);

        Chip chip2 = new Chip(this);
        chip2.setText("#페이스북");
        chip2.setChipBackgroundColorResource(R.color.text_white_color);
        chip2.setChipStrokeWidth(1f);
        chip2.setChipStrokeColorResource(R.color.gray_brown_color);
        chip2.setChipStartPadding(15f);
        chip2.setChipEndPadding(15f);
        chip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivitySingleDetail.this, "검색으로 이동", Toast.LENGTH_SHORT).show();
            }
        });
        binding.includeSingleDetail.chipGroup.addView(chip2);

        Chip chip3 = new Chip(this);
        chip3.setText("#버라이존 아마존");
        chip3.setChipBackgroundColorResource(R.color.text_white_color);
        chip3.setChipStrokeWidth(1f);
        chip3.setChipStrokeColorResource(R.color.gray_brown_color);
        chip3.setChipStartPadding(15f);
        chip3.setChipEndPadding(15f);
        chip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivitySingleDetail.this, "검색으로 이동", Toast.LENGTH_SHORT).show();
            }
        });
        binding.includeSingleDetail.chipGroup.addView(chip3);

        Chip chip4 = new Chip(this);
        chip4.setText("#유튜브 구독자 채널");
        chip4.setChipBackgroundColorResource(R.color.text_white_color);
        chip4.setChipStrokeWidth(1f);
        chip4.setChipStrokeColorResource(R.color.gray_brown_color);
        chip4.setChipStartPadding(15f);
        chip4.setChipEndPadding(15f);
        chip4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivitySingleDetail.this, "검색으로 이동", Toast.LENGTH_SHORT).show();
            }
        });
        binding.includeSingleDetail.chipGroup.addView(chip4);

        Chip chip5 = new Chip(this);
        chip5.setText("#넷플릭스 영화");
        chip5.setChipBackgroundColorResource(R.color.text_white_color);
        chip5.setChipStrokeWidth(1f);
        chip5.setChipStrokeColorResource(R.color.gray_brown_color);
        chip5.setChipStartPadding(15f);
        chip5.setChipEndPadding(15f);
        chip5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivitySingleDetail.this, "검색으로 이동", Toast.LENGTH_SHORT).show();
            }
        });
        binding.includeSingleDetail.chipGroup.addView(chip5);

        binding.includeSingleDetail.progress.setProgressBackgroundColor(getResources().getColor(R.color.life_line));
        binding.includeSingleDetail.progress.setProgressColor(getResources().getColor(R.color.green_basket_color));
        binding.includeSingleDetail.progress.setProgress(50f);

        binding.includeSingleDetail.progress2.setProgressBackgroundColor(getResources().getColor(R.color.life_line));
        binding.includeSingleDetail.progress2.setProgressColor(getResources().getColor(R.color.blue_color));
        binding.includeSingleDetail.progress2.setReverse(true);
        binding.includeSingleDetail.progress2.setProgress(50f);

        binding.includeSingleDetail.articleRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        binding.includeSingleDetail.articleRecyclerView.setLayoutManager(layoutManager);

        modelArticles = new ArrayList<>();

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

        modelArticles.add(new ModelArticle("제동 걸린 페이스북 가상화폐 '리브라' 누가? 왜? 해법은?", "", "", "", ""));
        modelArticles.add(new ModelArticle("애플이 2020년에 '아이폰 4' 디자인 다시 부활시킬 전망이다", "", "", "", ""));
        modelArticles.add(new ModelArticle("신한카드, 넷플릭스와 '전용 멤버십' 내놓는다", "", "", "", ""));

        adapterArticle.notifyDataSetChanged();

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
    }

}
