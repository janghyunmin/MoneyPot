package com.quantec.moneypot.activity.Main.Fragment.Tab1;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Activity.ActivityYieldChart;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter.AdapterFgTab1;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Adapter.AdapterFollowHome;
import com.quantec.moneypot.activity.Main.Fragment.tab4.ActivityNotiWebView;
import com.quantec.moneypot.activity.Search.ActivitySearch;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;
import com.quantec.moneypot.rxandroid.RxView;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.w3c.dom.Text;

public class FgTab1 extends Fragment {

    ImageView searchBt, userBt;

    private ActivityMain activityMain;

    DiscreteScrollView scrollView;

    public FgTab1() {
    }

    AdapterScroll adapterScroll;

    ArrayList<ModelRecomList> modelRecomLists;

    AdapterFgTab1 adapterFgTab1;
    LoopingViewPager viewPager;
    PageIndicatorView indicatorView;

    ArrayList<ModelYieldChart> modelYieldCharts;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<ModelFollowHome> modelFollowHomes;
    AdapterFollowHome adapterFollowHome;

    ArrayList<ModelFollowHome> modelFollowHomes3;
    ArrayList<ModelFollowHome> modelFollowHomeAll;

    TextView topTitle;

    boolean addViewState = false;

    LinearLayout yieldBt, followBt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab1, container, false);

        initializeViews();

        // 스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = activityMain.getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{
            Window window = activityMain.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.c_667ffe));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(0);
        }

        yieldBt = view.findViewById(R.id.yieldBt);
        followBt = view.findViewById(R.id.followBt);

        topTitle = view.findViewById(R.id.topTitle);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activityMain);
        recyclerView.setLayoutManager(layoutManager);

        modelFollowHomes = new ArrayList<>();
        modelFollowHomes3 = new ArrayList<>();
        modelFollowHomeAll = new ArrayList<>();

        modelFollowHomeAll.add(new ModelFollowHome(false, "", "", 0, 0));
        modelFollowHomeAll.add(new ModelFollowHome(false, "넷플릭스", "", 11.02, 5000));
        modelFollowHomeAll.add(new ModelFollowHome(false, "애플", "", -1.0, 454720));
        modelFollowHomeAll.add(new ModelFollowHome(false, "테슬라", "", -11.0, 0));
        modelFollowHomeAll.add(new ModelFollowHome(false, "삼성", "", 14.50, 0));
        modelFollowHomeAll.add(new ModelFollowHome(false, "하이닉스", "", 1.0, 0));
        modelFollowHomeAll.add(new ModelFollowHome(false, "반도체", "", 1.0, 0));
        modelFollowHomeAll.add(new ModelFollowHome(false, "현대", "", 1.0, 0));
        modelFollowHomeAll.add(new ModelFollowHome(false, "대우", "", 1.0, 0));
        modelFollowHomeAll.add(new ModelFollowHome(false, "해양", "", 1.0, 0));
        modelFollowHomeAll.add(new ModelFollowHome(false, "조선", "", 1.0, 0));
        modelFollowHomeAll.add(new ModelFollowHome(false, "접기", "", 0, 0));

        modelFollowHomes3.add(new ModelFollowHome(false, "", "", 0, 0));
        modelFollowHomes3.add(new ModelFollowHome(false, "넷플릭스", "", 1.0, 0));
        modelFollowHomes3.add(new ModelFollowHome(false, "애플", "", 1.0, 0));
        modelFollowHomes3.add(new ModelFollowHome(false, "테슬라", "", 1.0, 0));
        modelFollowHomes3.add(new ModelFollowHome(false, "더보기", "", 0, 0));

        modelFollowHomes.addAll(modelFollowHomes3);

        adapterFollowHome = new AdapterFollowHome(modelFollowHomes, activityMain);

        recyclerView.setAdapter(adapterFollowHome);

        modelYieldCharts = new ArrayList<>();

        indicatorView = view.findViewById(R.id.indicator);
        indicatorView.setAlpha(0.7f);
        indicatorView.setSelectedColor(activityMain.getResources().getColor(R.color.c_4e7cff));
        indicatorView.setUnselectedColor(activityMain.getResources().getColor(R.color.c_dedede));
        indicatorView.setAnimationType(AnimationType.THIN_WORM);
        viewPager = view.findViewById(R.id.viewPager);

        searchBt = view.findViewById(R.id.searchBt);
        userBt = view.findViewById(R.id.userBt);

        scrollView = view.findViewById(R.id.picker);

        scrollView.setItemTransitionTimeMillis(500);
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
        scrollView.bringToFront();

        modelRecomLists = new ArrayList<>();

        modelRecomLists.add(new ModelRecomList("나이키", "NIKE", "세계적인 운동화 브랜드,\nJUST DO IT!", 23.48, "콴텍님 나이키 운동화 말고,\n나이키의 주주는 어떠세요?"));
        modelRecomLists.add(new ModelRecomList("쉑쉑버거", "SHAKESHACK", "브랜드 설명", 23.48, "콴텍님 버거먹지 말고,\n쉑쉑버거 주주는 어떠세요?"));
        modelRecomLists.add(new ModelRecomList("인텔", "INTEL", "인텔", 23.48, "콴텍님 컴퓨터 말고,\n인텔의 주주는 어떠세요?"));


        adapterScroll = new AdapterScroll(modelRecomLists, activityMain);

        InfiniteScrollAdapter wrapper = InfiniteScrollAdapter.wrap(adapterScroll);
        scrollView.setAdapter(wrapper);


        scrollView.addScrollStateChangeListener(new DiscreteScrollView.ScrollStateChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScrollStart(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            }

            @Override
            public void onScrollEnd(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                topTitle.setText(modelRecomLists.get(wrapper.getRealPosition(i)).getTopTitle());
            }
            @Override
            public void onScroll(float v, int i, int i1, @Nullable RecyclerView.ViewHolder viewHolder, @Nullable RecyclerView.ViewHolder t1) {
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        yieldBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activityMain, ActivityYieldChart.class);
                startActivity(intent);
            }
        });

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

        adapterScroll.setScrollItemClick(new AdapterScroll.ScrollItemClick() {
            @Override
            public void onClick(int position) {
                Log.e("데이터","값 : "+modelRecomLists.get(position).getCode());
            }
        });

        modelYieldCharts.add(new ModelYieldChart(0,
                "스타벅스", "애브비", "마이크론테크", "A1", "A2", "A3", 11.02, 11.02 , 11.02, 292030 , 292030 , 292030));

        modelYieldCharts.add(new ModelYieldChart(1,
                "마법의 공식", "돼지열병과 싸우고 있는 기...", "세계적인 IT 기업들", "B1", "B2", "B3", 11.02, 11.02 , 11.02, 292030 , 292030 , 292030));

        modelYieldCharts.add(new ModelYieldChart(2,
                "한국", "미국", "일본", "C1", "C2", "C3", 11.02, 11.02 , 11.02, 292030 , 292030 , 292030));


        adapterFgTab1 = new AdapterFgTab1(activityMain, modelYieldCharts, true);
        viewPager.setAdapter(adapterFgTab1);
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(12);

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


        adapterFgTab1.setItemClick(new AdapterFgTab1.ItemClick() {
            @Override
            public void onClick(String code) {
                Log.e("클릭 이벤트", "종목코드 : "+code);
            }
        });

        adapterFollowHome.setFollowHomeClick(new AdapterFollowHome.FollowHomeClick() {
            @Override
            public void onClick(int position) {
                modelFollowHomes.clear();

                if(addViewState){
                    addViewState = false;
                    modelFollowHomes.addAll(modelFollowHomes3);
                }else{
                    addViewState = true;
                    modelFollowHomes.addAll(modelFollowHomeAll);
                }

                adapterFollowHome.notifyDataSetChanged();
            }
        });

        followBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxEventBus.getInstance().post(new RxEvent(RxEvent.FOLLOWPAGE_MOVE, null));
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


//        public class DemoInfiniteAdapter extends LoopingPagerAdapter<ModelYieldChart> {
//
//        private final int VIEWPAGE_1 = 0;
//        private final int VIEWPAGE_2 = 1;
//        private final int VIEWPAGE_3 = 2;
//
//        ArrayList<ModelYieldChart> itemList;
//
//        public DemoInfiniteAdapter(Context context, ArrayList<ModelYieldChart> itemList, boolean isInfinite) {
//            super(context, itemList, isInfinite);
//            this.itemList = itemList;
//        }
//
//        private ItemClick itemClick;
//        interface ItemClick{
//            public void onClick(int position);
//        }
//
//        @Override
//        protected int getItemViewType(int listPosition) {
//
//            if (itemList.get(listPosition).getType() == 0) {
//                return VIEWPAGE_1;
//            } else if(itemList.get(listPosition).getType() == 1){
//                return VIEWPAGE_2;
//            }else{
//                return VIEWPAGE_3;
//            }
//        }
//
//        @Override
//        protected View inflateView(int viewType, ViewGroup container, int listPosition) {
//
//            if (viewType == VIEWPAGE_1) {
//                return LayoutInflater.from(activityMain).inflate(R.layout.item_fgtab1_yieldpage0, container, false);
//            } else if(viewType == VIEWPAGE_2){
//                return LayoutInflater.from(activityMain).inflate(R.layout.item_fgtab1_yieldpage1, container, false);
//            }else{
//                return LayoutInflater.from(activityMain).inflate(R.layout.item_fgtab1_yieldpage2, container, false);
//            }
//        }
//
//            @Override
//            public float getPageWidth(int position) {
//                return 0.93f;
//            }
//
//            @Override
//        protected void bindView(View convertView, int listPosition, int viewType) {
//
//            TextView topTitle, title1, title2, title3, rate1, rate2, rate3, price1, price2, price3;
//            ConstraintLayout itemLayout;
//
//            if (viewType == VIEWPAGE_1) {
//
//                topTitle = convertView.findViewById(R.id.topTitle);
//                title1 = convertView.findViewById(R.id.title1);
//                title2 = convertView.findViewById(R.id.title2);
//                title3 = convertView.findViewById(R.id.title3);
//
//                rate1 = convertView.findViewById(R.id.rate1);
//                rate2 = convertView.findViewById(R.id.rate2);
//                rate3 = convertView.findViewById(R.id.rate3);
//
//                price1 = convertView.findViewById(R.id.price1);
//                price2 = convertView.findViewById(R.id.price2);
//                price3 = convertView.findViewById(R.id.price3);
//
//
//                topTitle.setText("해외기업 차트");
//                title1.setText(itemList.get(listPosition).getTitle1());
//                title2.setText(itemList.get(listPosition).getTitle2());
//                title3.setText(itemList.get(listPosition).getTitle3());
//
//                rate1.setText(String.valueOf(itemList.get(listPosition).getRate1()));
//                rate2.setText(String.valueOf(itemList.get(listPosition).getRate2()));
//                rate3.setText(String.valueOf(itemList.get(listPosition).getRate3()));
//
//                price1.setText(String.valueOf(itemList.get(listPosition).getPrice1()));
//                price2.setText(String.valueOf(itemList.get(listPosition).getPrice2()));
//                price3.setText(String.valueOf(itemList.get(listPosition).getPrice3()));
//            }
//
//            if (viewType == VIEWPAGE_2) {
//                topTitle = convertView.findViewById(R.id.topTitle);
//                title1 = convertView.findViewById(R.id.title1);
//                title2 = convertView.findViewById(R.id.title2);
//                title3 = convertView.findViewById(R.id.title3);
//
//                rate1 = convertView.findViewById(R.id.rate1);
//                rate2 = convertView.findViewById(R.id.rate2);
//                rate3 = convertView.findViewById(R.id.rate3);
//
//                price1 = convertView.findViewById(R.id.price1);
//                price2 = convertView.findViewById(R.id.price2);
//                price3 = convertView.findViewById(R.id.price3);
//
//                topTitle.setText("묶음기업 차트");
//                title1.setText(itemList.get(listPosition).getTitle1());
//                title2.setText(itemList.get(listPosition).getTitle2());
//                title3.setText(itemList.get(listPosition).getTitle3());
//
//                rate1.setText(String.valueOf(itemList.get(listPosition).getRate1()));
//                rate2.setText(String.valueOf(itemList.get(listPosition).getRate2()));
//                rate3.setText(String.valueOf(itemList.get(listPosition).getRate3()));
//
//                price1.setText(String.valueOf(itemList.get(listPosition).getPrice1()));
//                price2.setText(String.valueOf(itemList.get(listPosition).getPrice2()));
//                price3.setText(String.valueOf(itemList.get(listPosition).getPrice3()));
//            }
//
//            if(viewType == VIEWPAGE_3){
//                topTitle = convertView.findViewById(R.id.topTitle);
//                title1 = convertView.findViewById(R.id.title1);
//                title2 = convertView.findViewById(R.id.title2);
//                title3 = convertView.findViewById(R.id.title3);
//
//                rate1 = convertView.findViewById(R.id.rate1);
//                rate2 = convertView.findViewById(R.id.rate2);
//                rate3 = convertView.findViewById(R.id.rate3);
//
//                price1 = convertView.findViewById(R.id.price1);
//                price2 = convertView.findViewById(R.id.price2);
//                price3 = convertView.findViewById(R.id.price3);
//
//                topTitle.setText("S&P500을 이기고 있는 기업들");
//                title1.setText(itemList.get(listPosition).getTitle1());
//                title2.setText(itemList.get(listPosition).getTitle2());
//                title3.setText(itemList.get(listPosition).getTitle3());
//
//                rate1.setText(String.valueOf(itemList.get(listPosition).getRate1()));
//                rate2.setText(String.valueOf(itemList.get(listPosition).getRate2()));
//                rate3.setText(String.valueOf(itemList.get(listPosition).getRate3()));
//
//                price1.setText(String.valueOf(itemList.get(listPosition).getPrice1()));
//                price2.setText(String.valueOf(itemList.get(listPosition).getPrice2()));
//                price3.setText(String.valueOf(itemList.get(listPosition).getPrice3()));
//            }
//        }
//    }

}


//public class FgTab1 extends Fragment implements View.OnClickListener {
//
//    RecyclerView rankingRecyclerView, themeTop3RecyclerView;
//    RecyclerView.LayoutManager layoutManager;
//
//    ArrayList<ModelAllRankingTop3> modelAllRankingTop3s;
//    ArrayList<ModelMini> modelMinis;
//    ArrayList<ModelThemeTop3> modelThemeTop3s;
//
//    AdapterAllRankingTop3 adapterAllRankingTop3;
//    AdapterMini adapterMini;
//    AdapterThemeTop3 adapterThemeTop3;
//
//    ImageView rankingAddBt, themeAddBt, searchBt, userBt;
//
//    DemoInfiniteAdapter adapter;
//    LoopingViewPager viewPager;
//    PageIndicatorView indicatorView;
//
//    ArrayList<ModelYieldChart> modelYieldCharts;
//
//    private ActivityMain activityMain;
//
//    public FgTab1() {
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fg_tab1, container, false);
//
//        initializeViews();
//
//        modelYieldCharts = new ArrayList<>();
//
//        searchBt = view.findViewById(R.id.searchBt);
//        userBt = view.findViewById(R.id.userBt);
//
//        rankingAddBt = view.findViewById(R.id.rankingAddBt);
//        rankingAddBt.setOnClickListener(this);
//
//        themeAddBt = view.findViewById(R.id.themeAddBt);
//        themeAddBt.setOnClickListener(this);
//
//        rankingRecyclerView = view.findViewById(R.id.rankingRecyclerView);
//        themeTop3RecyclerView = view.findViewById(R.id.themeTop3RecyclerView);
//
//        rankingRecyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(activityMain, RecyclerView.HORIZONTAL, false);
//        rankingRecyclerView.setLayoutManager(layoutManager);
//        modelAllRankingTop3s = new ArrayList<>();
//        adapterAllRankingTop3 = new AdapterAllRankingTop3(modelAllRankingTop3s, activityMain);
//        rankingRecyclerView.setAdapter(adapterAllRankingTop3);
//
//        rankingRecyclerView.addItemDecoration(new DecorationAllRankingTop3(activityMain, 12));
//
//        layoutManager = new LinearLayoutManager(activityMain, RecyclerView.HORIZONTAL, false);
//        modelMinis = new ArrayList<>();
//        adapterMini = new AdapterMini(modelMinis, activityMain);
//
//        themeTop3RecyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(activityMain);
//        themeTop3RecyclerView.setLayoutManager(layoutManager);
//        modelThemeTop3s = new ArrayList<>();
//        adapterThemeTop3 = new AdapterThemeTop3(modelThemeTop3s, activityMain);
//        themeTop3RecyclerView.setAdapter(adapterThemeTop3);
//
//        themeTop3RecyclerView.addItemDecoration(new DecorationItemVertical(activityMain, 12));
//
//
//        indicatorView = view.findViewById(R.id.indicator);
//        indicatorView.setAlpha(0.7f);
//        indicatorView.setSelectedColor(activityMain.getResources().getColor(R.color.green_basket_color));
//        indicatorView.setUnselectedColor(activityMain.getResources().getColor(R.color.text_gray_color));
//        indicatorView.setAnimationType(AnimationType.THIN_WORM);
//        viewPager = view.findViewById(R.id.viewPager);
//
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        modelAllRankingTop3s.add(new ModelAllRankingTop3(activityMain.getResources().getDrawable(R.drawable.img_gold_medal), "해외 여행 플랜", 30.13));
//        modelAllRankingTop3s.add(new ModelAllRankingTop3(activityMain.getResources().getDrawable(R.drawable.img_silver_medal), "연금 준비 플랜", 26.91));
//        modelAllRankingTop3s.add(new ModelAllRankingTop3(activityMain.getResources().getDrawable(R.drawable.img_brown_medal), "지속 성장 플랜", 24.13));
//
//        adapterAllRankingTop3.notifyDataSetChanged();
//
//        modelMinis.add(new ModelMini("반려동물 천만 시대", activityMain.getResources().getDrawable(R.drawable.img_mini_1)));
//        modelMinis.add(new ModelMini("코리안 파워", activityMain.getResources().getDrawable(R.drawable.img_mini_2)));
//        modelMinis.add(new ModelMini("둘이라서 더욱 좋아요", activityMain.getResources().getDrawable(R.drawable.img_mini_3)));
//
//        adapterMini.notifyDataSetChanged();
//
//
//        modelThemeTop3s.add(new ModelThemeTop3(1, "핵심은 4차 산업 기술", 24.13));
//        modelThemeTop3s.add(new ModelThemeTop3(2, "한국을 IT 강국으로 이끈 기업들", 22.18));
//        modelThemeTop3s.add(new ModelThemeTop3(3, "한국 경제의 중심! 삼성의 계열사", 19.72));
//
//        adapterThemeTop3.notifyDataSetChanged();
//
//
//
//        adapterAllRankingTop3.setAllRankingTop3ItemClick(new AdapterAllRankingTop3.AllRankingTop3ItemClick() {
//            @Override
//            public void onClick(int position) {
//
//                Intent intent1 = new Intent(activityMain, ActivitySingleDetail.class);
//                startActivity(intent1);
//                Toast.makeText(activityMain.getApplicationContext(), "상세페이지 이동 : " + modelAllRankingTop3s.get(position).getTitle(), Toast.LENGTH_SHORT).show();
//                Log.e("클릭","클릭");
//            }
//        });
//
//        adapterMini.setMiniItemClick(new AdapterMini.MiniItemClick() {
//            @Override
//            public void onClick(int position) {
//
//            }
//        });
//
//        adapterThemeTop3.setThemeTop3itemClick(new AdapterThemeTop3.ThemeTop3itemClick() {
//            @Override
//            public void onClick(int position) {
//
//            }
//        });
//
//        //내가 만든 포트 불러옴
//        setMyPotDate();
//
//        searchBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activityMain, ActivitySearch.class);
//                startActivity(intent);
//            }
//        });
//
//        userBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(activityMain, ActivityNotiWebView.class);
//                startActivity(intent1);
//            }
//        });
//
//    }//onViewCreate 끝
//
//
//    private void initializeViews() {
//        activityMain = (ActivityMain) getActivity();
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof ActivityMain) {
//            activityMain = (ActivityMain) context;
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//
//            case R.id.rankingAddBt:
//                Intent intent1 = new Intent(activityMain, ActivityPotMarket.class);
//                intent1.putExtra("title", "전체 랭킹");
//                startActivity(intent1);
//                break;
//
//            case R.id.themeAddBt:
//                Intent intent4 = new Intent(activityMain, ActivityPotMarket.class);
//                intent4.putExtra("title", "테마 시리즈");
//                startActivity(intent4);
//                break;
//
//        }
//    }
//
//
//    void setMyPotDate() {
//
//        modelYieldCharts.add(new ModelYieldChart("애플", "트위터", "넷플릭스",
//                "AAPL", "TRTW", "NFLX",
//                "url", "url", "url",
//                 DecimalScale.decimalScale(String.valueOf(23.48), 2, 2),
//                 DecimalScale.decimalScale(String.valueOf(23.48), 2, 2),
//                 DecimalScale.decimalScale(String.valueOf(11.01), 2, 2),
//                292030, 123000, 23000,
//                "", "", "",
//                "", "", "",
//                "url", "url", "url",
//                0));
//
//
//        modelYieldCharts.add(new ModelYieldChart("애플", "트위터", "넷플릭스",
//                "AAPL", "TRTW", "NFLX",
//                "url", "url", "url",
//                DecimalScale.decimalScale(String.valueOf(23.48), 2, 2),
//                DecimalScale.decimalScale(String.valueOf(23.48), 2, 2),
//                DecimalScale.decimalScale(String.valueOf(11.01), 2, 2),
//                292030, 123000, 23000,
//                "마법의 공식", "돼지 열병과 싸우고 있는 기업들", "세계적인 IT 기업들",
//                "", "", "",
//                "url", "url", "url",
//                1));
//
//
//        adapter = new DemoInfiniteAdapter(activityMain, modelYieldCharts, true);
//        viewPager.setAdapter(adapter);
//        //Custom bind indicator
//        indicatorView.setCount(viewPager.getIndicatorCount());
//        viewPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
//            @Override
//            public void onIndicatorProgress(int selectingPosition, float progress) {
//                indicatorView.setProgress(selectingPosition, progress);
//            }
//
//            @Override
//            public void onIndicatorPageChange(int newIndicatorPosition) {
//            }
//        });
//
//
////        Filter filter = new Filter();
////        Call<ModelPotList> getReList = RetrofitClient.getInstance().getService().getPotList("application/json", filter, "U", 0,0,10);
////        getReList.enqueue(new Callback<ModelPotList>() {
////            @Override
////            public void onResponse(Call<ModelPotList> call, Response<ModelPotList> response) {
////                modelMakeMyPot.clear();
////                if (response.code() == 200) {
////                    if (response.body().getTotalElements() == 0) {
////
////                        modelMakeMyPot.add(new ModelMakeMyPot("", "", "", 0, true));
////                    } else {
////
////                        for (int index = 0; index < response.body().getContent().size(); index++) {
////
////                            if(index == 3){
////                                break;
////                            }
////
////                            modelMakeMyPot.add(new ModelMakeMyPot(response.body().getContent().get(index).getName(),
////                                    response.body().getContent().get(index).getCode(),
////                                    DateTransForm.dateTransForm(response.body().getContent().get(index).getDate()),
////                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(index).getRate()*100), 2, 2), false));
////                        }
////                    }
////
////                    adapter = new DemoInfiniteAdapter(activityMain, modelMakeMyPot, true);
////                    viewPager.setAdapter(adapter);
////                    //Custom bind indicator
////                    indicatorView.setCount(viewPager.getIndicatorCount());
////                    viewPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
////                        @Override
////                        public void onIndicatorProgress(int selectingPosition, float progress) {
////                            indicatorView.setProgress(selectingPosition, progress);
////                        }
////                        @Override
////                        public void onIndicatorPageChange(int newIndicatorPosition) {
////                        }
////                    });
////                }
////            }
////            @Override
////            public void onFailure(Call<ModelPotList> call, Throwable t) {
////                Log.e("받은값 에러","값 : "+t.getMessage());
////            }
////        });
//    }
//
//
//    public class DemoInfiniteAdapter extends LoopingPagerAdapter<ModelYieldChart> {
//
//        private final int VIEWPAGE_1 = 0;
//        private final int VIEWPAGE_2 = 1;
//
//        ArrayList<ModelYieldChart> itemList;
//
//        public DemoInfiniteAdapter(Context context, ArrayList<ModelYieldChart> itemList, boolean isInfinite) {
//            super(context, itemList, isInfinite);
//            this.itemList = itemList;
//        }
//
//        @Override
//        protected int getItemViewType(int listPosition) {
//
//            if (itemList.get(listPosition).getPage() == 0) {
//                return VIEWPAGE_1;
//            } else {
//                return VIEWPAGE_2;
//            }
//        }
//
//        @Override
//        protected View inflateView(int viewType, ViewGroup container, int listPosition) {
//
//            if (viewType == VIEWPAGE_1) {
//                return LayoutInflater.from(activityMain).inflate(R.layout.item_fgtab1_yieldpage0, container, false);
//            } else {
//                return LayoutInflater.from(activityMain).inflate(R.layout.item_fgtab1_yieldpage1, container, false);
//            }
//
//        }
//
//        @Override
//        protected void bindView(View convertView, int listPosition, int viewType) {
//
//            TextView firstChart, secondChart, thirdChart, firstSum, secondSum, thirdSum;
//            ConstraintLayout itemLayout;
//
//            if (viewType == VIEWPAGE_1) {
//                firstChart = convertView.findViewById(R.id.firstChart);
//                secondChart = convertView.findViewById(R.id.secondChart);
//                thirdChart = convertView.findViewById(R.id.thirdChart);
//
//                firstChart.setText(modelYieldCharts.get(listPosition).getFirstChartTitle());
//                secondChart.setText(modelYieldCharts.get(listPosition).getScondChartTitle());
//                thirdChart.setText(modelYieldCharts.get(listPosition).getThirdChartTitle());
//
//
//            }
//
//            if (viewType == VIEWPAGE_2) {
//
//                firstSum = convertView.findViewById(R.id.firstSum);
//                secondSum = convertView.findViewById(R.id.secondSum);
//                thirdSum = convertView.findViewById(R.id.thirdSum);
//
//                firstSum.setText(modelYieldCharts.get(listPosition).getFirstSumTitle());
//                secondSum.setText(modelYieldCharts.get(listPosition).getScondSumtTitle());
//                thirdSum.setText(modelYieldCharts.get(listPosition).getThirdSumTitle());
////
////                itemLayout = convertView.findViewById(R.id.itemLayout);
////
////                title.setText(modelYieldCharts.get(listPosition).getPotTitle());
////                rate.setText(String.valueOf(modelYieldCharts.get(listPosition).getPotRate()));
////
////                if(modelYieldCharts.get(listPosition).getPotRate() < 0){
////                    rate.setTextColor(getResources().getColor(R.color.blue_color));
////                    per.setTextColor(getResources().getColor(R.color.blue_color));
////                }
////                else{
////                    rate.setTextColor(getResources().getColor(R.color.red_text_color));
////                    per.setTextColor(getResources().getColor(R.color.red_text_color));
////                }
////
////                itemLayout.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        Intent intent1 = new Intent(activityMain, ActivityPotDetail.class);
////                        intent1.putExtra("potCode", modelYieldCharts.get(listPosition).getPotCode());
////                        startActivity(intent1);
////                    }
////                });
//            }
//        }
//    }
//
//
//}
