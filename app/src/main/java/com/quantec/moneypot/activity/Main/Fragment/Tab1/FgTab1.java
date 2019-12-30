package com.quantec.moneypot.activity.Main.Fragment.Tab1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.quantec.moneypot.activity.Main.Fragment.tab4.ActivityNotiWebView;
import com.quantec.moneypot.activity.Search.ActivitySearch;
import com.quantec.moneypot.rxandroid.RxView;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Activity.ActivityPotMarket;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter.AdapterAllRankingTop3;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter.AdapterMini;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter.AdapterThemeTop3;
import com.quantec.moneypot.activity.PotDetail.ActivitySingleDetail;
import com.quantec.moneypot.datamodel.dmodel.ModelAllRankingTop3;
import com.quantec.moneypot.datamodel.dmodel.ModelMini;
import com.quantec.moneypot.datamodel.dmodel.ModelThemeTop3;
import com.quantec.moneypot.R;
import com.quantec.moneypot.util.DecimalScale.DecimalScale;

public class FgTab1 extends Fragment implements View.OnClickListener {

    RecyclerView rankingRecyclerView, themeTop3RecyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<ModelAllRankingTop3> modelAllRankingTop3s;
    ArrayList<ModelMini> modelMinis;
    ArrayList<ModelThemeTop3> modelThemeTop3s;

    AdapterAllRankingTop3 adapterAllRankingTop3;
    AdapterMini adapterMini;
    AdapterThemeTop3 adapterThemeTop3;

    ImageView rankingAddBt, themeAddBt, searchBt, userBt;

    DemoInfiniteAdapter adapter;
    LoopingViewPager viewPager;
    PageIndicatorView indicatorView;

    ArrayList<ModelYieldChart> modelYieldCharts;

    private ActivityMain activityMain;

    public FgTab1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab1, container, false);

        initializeViews();

        modelYieldCharts = new ArrayList<>();

        searchBt = view.findViewById(R.id.searchBt);
        userBt = view.findViewById(R.id.userBt);

        rankingAddBt = view.findViewById(R.id.rankingAddBt);
        rankingAddBt.setOnClickListener(this);

        themeAddBt = view.findViewById(R.id.themeAddBt);
        themeAddBt.setOnClickListener(this);

        rankingRecyclerView = view.findViewById(R.id.rankingRecyclerView);
        themeTop3RecyclerView = view.findViewById(R.id.themeTop3RecyclerView);

        rankingRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityMain, RecyclerView.HORIZONTAL, false);
        rankingRecyclerView.setLayoutManager(layoutManager);
        modelAllRankingTop3s = new ArrayList<>();
        adapterAllRankingTop3 = new AdapterAllRankingTop3(modelAllRankingTop3s, activityMain);
        rankingRecyclerView.setAdapter(adapterAllRankingTop3);

        rankingRecyclerView.addItemDecoration(new DecorationAllRankingTop3(activityMain, 12));

        layoutManager = new LinearLayoutManager(activityMain, RecyclerView.HORIZONTAL, false);
        modelMinis = new ArrayList<>();
        adapterMini = new AdapterMini(modelMinis, activityMain);

        themeTop3RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityMain);
        themeTop3RecyclerView.setLayoutManager(layoutManager);
        modelThemeTop3s = new ArrayList<>();
        adapterThemeTop3 = new AdapterThemeTop3(modelThemeTop3s, activityMain);
        themeTop3RecyclerView.setAdapter(adapterThemeTop3);

        themeTop3RecyclerView.addItemDecoration(new DecorationItemVertical(activityMain, 12));


        indicatorView = view.findViewById(R.id.indicator);
        indicatorView.setAlpha(0.7f);
        indicatorView.setSelectedColor(activityMain.getResources().getColor(R.color.green_basket_color));
        indicatorView.setUnselectedColor(activityMain.getResources().getColor(R.color.text_gray_color));
        indicatorView.setAnimationType(AnimationType.THIN_WORM);
        viewPager = view.findViewById(R.id.viewPager);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelAllRankingTop3s.add(new ModelAllRankingTop3(activityMain.getResources().getDrawable(R.drawable.img_gold_medal), "해외 여행 플랜", 30.13));
        modelAllRankingTop3s.add(new ModelAllRankingTop3(activityMain.getResources().getDrawable(R.drawable.img_silver_medal), "연금 준비 플랜", 26.91));
        modelAllRankingTop3s.add(new ModelAllRankingTop3(activityMain.getResources().getDrawable(R.drawable.img_brown_medal), "지속 성장 플랜", 24.13));

        adapterAllRankingTop3.notifyDataSetChanged();

        modelMinis.add(new ModelMini("반려동물 천만 시대", activityMain.getResources().getDrawable(R.drawable.img_mini_1)));
        modelMinis.add(new ModelMini("코리안 파워", activityMain.getResources().getDrawable(R.drawable.img_mini_2)));
        modelMinis.add(new ModelMini("둘이라서 더욱 좋아요", activityMain.getResources().getDrawable(R.drawable.img_mini_3)));

        adapterMini.notifyDataSetChanged();


        modelThemeTop3s.add(new ModelThemeTop3(1, "핵심은 4차 산업 기술", 24.13));
        modelThemeTop3s.add(new ModelThemeTop3(2, "한국을 IT 강국으로 이끈 기업들", 22.18));
        modelThemeTop3s.add(new ModelThemeTop3(3, "한국 경제의 중심! 삼성의 계열사", 19.72));

        adapterThemeTop3.notifyDataSetChanged();



        adapterAllRankingTop3.setAllRankingTop3ItemClick(new AdapterAllRankingTop3.AllRankingTop3ItemClick() {
            @Override
            public void onClick(int position) {

                Intent intent1 = new Intent(activityMain, ActivitySingleDetail.class);
                startActivity(intent1);
                Toast.makeText(activityMain.getApplicationContext(), "상세페이지 이동 : " + modelAllRankingTop3s.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Log.e("클릭","클릭");
            }
        });

        adapterMini.setMiniItemClick(new AdapterMini.MiniItemClick() {
            @Override
            public void onClick(int position) {

            }
        });

        adapterThemeTop3.setThemeTop3itemClick(new AdapterThemeTop3.ThemeTop3itemClick() {
            @Override
            public void onClick(int position) {

            }
        });

        //내가 만든 포트 불러옴
        setMyPotDate();

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityMain, ActivitySearch.class);
                startActivity(intent);
            }
        });

        userBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(activityMain, ActivityNotiWebView.class);
                startActivity(intent1);
            }
        });

    }//onViewCreate 끝


    private void initializeViews() {
        activityMain = (ActivityMain) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActivityMain) {
            activityMain = (ActivityMain) context;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rankingAddBt:
                Intent intent1 = new Intent(activityMain, ActivityPotMarket.class);
                intent1.putExtra("title", "전체 랭킹");
                startActivity(intent1);
                break;

            case R.id.themeAddBt:
                Intent intent4 = new Intent(activityMain, ActivityPotMarket.class);
                intent4.putExtra("title", "테마 시리즈");
                startActivity(intent4);
                break;

        }
    }


    void setMyPotDate() {

        modelYieldCharts.add(new ModelYieldChart("애플", "트위터", "넷플릭스",
                "AAPL", "TRTW", "NFLX",
                "url", "url", "url",
                 DecimalScale.decimalScale(String.valueOf(23.48), 2, 2),
                 DecimalScale.decimalScale(String.valueOf(23.48), 2, 2),
                 DecimalScale.decimalScale(String.valueOf(11.01), 2, 2),
                292030, 123000, 23000,
                "", "", "",
                "", "", "",
                "url", "url", "url",
                0));


        modelYieldCharts.add(new ModelYieldChart("애플", "트위터", "넷플릭스",
                "AAPL", "TRTW", "NFLX",
                "url", "url", "url",
                DecimalScale.decimalScale(String.valueOf(23.48), 2, 2),
                DecimalScale.decimalScale(String.valueOf(23.48), 2, 2),
                DecimalScale.decimalScale(String.valueOf(11.01), 2, 2),
                292030, 123000, 23000,
                "마법의 공식", "돼지 열병과 싸우고 있는 기업들", "세계적인 IT 기업들",
                "", "", "",
                "url", "url", "url",
                1));


        adapter = new DemoInfiniteAdapter(activityMain, modelYieldCharts, true);
        viewPager.setAdapter(adapter);
        //Custom bind indicator
        indicatorView.setCount(viewPager.getIndicatorCount());
        viewPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
            @Override
            public void onIndicatorProgress(int selectingPosition, float progress) {
                indicatorView.setProgress(selectingPosition, progress);
            }

            @Override
            public void onIndicatorPageChange(int newIndicatorPosition) {
            }
        });


//        Filter filter = new Filter();
//        Call<ModelPotList> getReList = RetrofitClient.getInstance().getService().getPotList("application/json", filter, "U", 0,0,10);
//        getReList.enqueue(new Callback<ModelPotList>() {
//            @Override
//            public void onResponse(Call<ModelPotList> call, Response<ModelPotList> response) {
//                modelMakeMyPot.clear();
//                if (response.code() == 200) {
//                    if (response.body().getTotalElements() == 0) {
//
//                        modelMakeMyPot.add(new ModelMakeMyPot("", "", "", 0, true));
//                    } else {
//
//                        for (int index = 0; index < response.body().getContent().size(); index++) {
//
//                            if(index == 3){
//                                break;
//                            }
//
//                            modelMakeMyPot.add(new ModelMakeMyPot(response.body().getContent().get(index).getName(),
//                                    response.body().getContent().get(index).getCode(),
//                                    DateTransForm.dateTransForm(response.body().getContent().get(index).getDate()),
//                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(index).getRate()*100), 2, 2), false));
//                        }
//                    }
//
//                    adapter = new DemoInfiniteAdapter(activityMain, modelMakeMyPot, true);
//                    viewPager.setAdapter(adapter);
//                    //Custom bind indicator
//                    indicatorView.setCount(viewPager.getIndicatorCount());
//                    viewPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
//                        @Override
//                        public void onIndicatorProgress(int selectingPosition, float progress) {
//                            indicatorView.setProgress(selectingPosition, progress);
//                        }
//                        @Override
//                        public void onIndicatorPageChange(int newIndicatorPosition) {
//                        }
//                    });
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelPotList> call, Throwable t) {
//                Log.e("받은값 에러","값 : "+t.getMessage());
//            }
//        });
    }


    public class DemoInfiniteAdapter extends LoopingPagerAdapter<ModelYieldChart> {

        private final int VIEWPAGE_1 = 0;
        private final int VIEWPAGE_2 = 1;

        ArrayList<ModelYieldChart> itemList;

        public DemoInfiniteAdapter(Context context, ArrayList<ModelYieldChart> itemList, boolean isInfinite) {
            super(context, itemList, isInfinite);
            this.itemList = itemList;
        }

        @Override
        protected int getItemViewType(int listPosition) {

            if (itemList.get(listPosition).getPage() == 0) {
                return VIEWPAGE_1;
            } else {
                return VIEWPAGE_2;
            }
        }

        @Override
        protected View inflateView(int viewType, ViewGroup container, int listPosition) {

            if (viewType == VIEWPAGE_1) {
                return LayoutInflater.from(activityMain).inflate(R.layout.item_fgtab1_yieldpage0, container, false);
            } else {
                return LayoutInflater.from(activityMain).inflate(R.layout.item_fgtab1_yieldpage1, container, false);
            }

        }

        @Override
        protected void bindView(View convertView, int listPosition, int viewType) {

            TextView firstChart, secondChart, thirdChart, firstSum, secondSum, thirdSum;
            ConstraintLayout itemLayout;

            if (viewType == VIEWPAGE_1) {
                firstChart = convertView.findViewById(R.id.firstChart);
                secondChart = convertView.findViewById(R.id.secondChart);
                thirdChart = convertView.findViewById(R.id.thirdChart);

                firstChart.setText(modelYieldCharts.get(listPosition).getFirstChartTitle());
                secondChart.setText(modelYieldCharts.get(listPosition).getScondChartTitle());
                thirdChart.setText(modelYieldCharts.get(listPosition).getThirdChartTitle());


            }

            if (viewType == VIEWPAGE_2) {

                firstSum = convertView.findViewById(R.id.firstSum);
                secondSum = convertView.findViewById(R.id.secondSum);
                thirdSum = convertView.findViewById(R.id.thirdSum);

                firstSum.setText(modelYieldCharts.get(listPosition).getFirstSumTitle());
                secondSum.setText(modelYieldCharts.get(listPosition).getScondSumtTitle());
                thirdSum.setText(modelYieldCharts.get(listPosition).getThirdSumTitle());
//
//                itemLayout = convertView.findViewById(R.id.itemLayout);
//
//                title.setText(modelYieldCharts.get(listPosition).getPotTitle());
//                rate.setText(String.valueOf(modelYieldCharts.get(listPosition).getPotRate()));
//
//                if(modelYieldCharts.get(listPosition).getPotRate() < 0){
//                    rate.setTextColor(getResources().getColor(R.color.blue_color));
//                    per.setTextColor(getResources().getColor(R.color.blue_color));
//                }
//                else{
//                    rate.setTextColor(getResources().getColor(R.color.red_text_color));
//                    per.setTextColor(getResources().getColor(R.color.red_text_color));
//                }
//
//                itemLayout.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent1 = new Intent(activityMain, ActivityPotDetail.class);
//                        intent1.putExtra("potCode", modelYieldCharts.get(listPosition).getPotCode());
//                        startActivity(intent1);
//                    }
//                });
            }
        }
    }


}
