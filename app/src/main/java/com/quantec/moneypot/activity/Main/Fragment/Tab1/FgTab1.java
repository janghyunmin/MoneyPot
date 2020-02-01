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
import com.quantec.moneypot.activity.prefer.ActivityPrefer;
import com.quantec.moneypot.datamanager.DataManager;
import com.quantec.moneypot.datamodel.nmodel.ModelTopCompare;
import com.quantec.moneypot.datamodel.nmodel.ModelUserFollow;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    int size = 0;

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

        adapterFollowHome = new AdapterFollowHome(modelFollowHomes, activityMain);
        recyclerView.setAdapter(adapterFollowHome);

        Call<ModelUserFollow> getReList = RetrofitClient.getInstance().getService().getUserSelect("application/json", "follow");
        getReList.enqueue(new Callback<ModelUserFollow>() {
            @Override
            public void onResponse(Call<ModelUserFollow> call, Response<ModelUserFollow> response) {
                if (response.code() == 200) {
                    if(response.body().getStatus() == 200){

                        modelFollowHomes3.clear();
                        modelFollowHomeAll.clear();
                        modelFollowHomes.clear();

                        if(response.body().getTotalElements() == 0){
                            modelFollowHomes3.add(new ModelFollowHome(false,true, "", "", 0, 0));
                            modelFollowHomeAll.add(new ModelFollowHome(false,true, "", "", 0, 0));
                        }else{

                            modelFollowHomeAll.add(new ModelFollowHome(false, false, "", "", 0, 0));
                            for(int index = 0; index < response.body().getContent().size(); index++){
                                modelFollowHomeAll.add(new ModelFollowHome(false, false, response.body().getContent().get(index).getName(),
                                        response.body().getContent().get(index).getCode(),
                                        response.body().getContent().get(index).getRate(),
                                        response.body().getContent().get(index).getPrice()));
                            }
                            modelFollowHomeAll.add(new ModelFollowHome(true,false, "접기", "", 0, 0));

                            modelFollowHomes3.add(new ModelFollowHome( false,false, "", "", 0, 0));

                            size = response.body().getTotalElements();
                            if(size >= 3){
                                size = 3;
                            }

                            for(int index = 0; index < size; index++){
                                modelFollowHomes3.add(new ModelFollowHome(false, false, response.body().getContent().get(index).getName(),
                                        response.body().getContent().get(index).getCode(),
                                        response.body().getContent().get(index).getRate(),
                                        response.body().getContent().get(index).getPrice()));
                            }
                            if(response.body().getTotalElements() > 3){
                                modelFollowHomes3.add(new ModelFollowHome(true, false, "더보기", "", 0, 0));
                            }
                        }
                        modelFollowHomes.addAll(modelFollowHomes3);
                        adapterFollowHome.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelUserFollow> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });

//        Call<ModelUserFollow> getReList = RetrofitClient.getInstance().getService().getUserSelect("application/json", "follow");
//        getReList.enqueue(new Callback<ModelUserFollow>() {
//            @Override
//            public void onResponse(Call<ModelUserFollow> call, Response<ModelUserFollow> response) {
//                if (response.code() == 200) {
//                    if(response.body().getStatus() == 200){
//
//                        modelFollowHomes3.clear();
//                        modelFollowHomeAll.clear();
//
//                        if(response.body().getTotalElements() == 0){
//                            modelFollowHomes3.add(new ModelFollowHome(true, "", "", 0, 0));
//                            modelFollowHomeAll.add(new ModelFollowHome(true, "", "", 0, 0));
//                        }else{
//
//                            modelFollowHomeAll.add(new ModelFollowHome(false, "", "", 0, 0));
//                            for(int index = 0; index < response.body().getContent().size(); index++){
//                                modelFollowHomeAll.add(new ModelFollowHome(false, response.body().getContent().get(index).getName(),
//                                        response.body().getContent().get(index).getCode(),
//                                        response.body().getContent().get(index).getRate(),
//                                        response.body().getContent().get(index).getPrice()));
//                            }
//                            modelFollowHomeAll.add(new ModelFollowHome(false, "접기", "", 0, 0));
//
//                            modelFollowHomes3.add(new ModelFollowHome(false, "", "", 0, 0));
//                            for(int index = 0; index < 3; index++){
//                                modelFollowHomes3.add(new ModelFollowHome(false, response.body().getContent().get(index).getName(),
//                                        response.body().getContent().get(index).getCode(),
//                                        response.body().getContent().get(index).getRate(),
//                                        response.body().getContent().get(index).getPrice()));
//                            }
//                            modelFollowHomes3.add(new ModelFollowHome(false, "더보기", "", 0, 0));
//                        }
//                        modelFollowHomes.addAll(modelFollowHomes3);
//                        adapterFollowHome.notifyDataSetChanged();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelUserFollow> call, Throwable t) {
//                Log.e("실패","실패"+t.getMessage());
//            }
//        });

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

//        modelRecomLists.add(new ModelRecomList("나이키", "NIKE", "세계적인 운동화 브랜드,\nJUST DO IT!", 23.48, "콴텍님 나이키 운동화 말고,\n나이키의 주주는 어떠세요?"));
//        modelRecomLists.add(new ModelRecomList("쉑쉑버거", "SHAKESHACK", "브랜드 설명", 23.48, "콴텍님 버거먹지 말고,\n쉑쉑버거 주주는 어떠세요?"));
//        modelRecomLists.add(new ModelRecomList("인텔", "INTEL", "인텔", 23.48, "콴텍님 컴퓨터 말고,\n인텔의 주주는 어떠세요?"));


        Call<ModelTopCompare> setSearch = RetrofitClient.getInstance(activityMain).getService().getTopCompare(3);
        setSearch.enqueue(new Callback<ModelTopCompare>() {
            @Override
            public void onResponse(Call<ModelTopCompare> call, Response<ModelTopCompare> response) {
                if(response.code() == 200) {
                    for(int index = 0; index < response.body().getTotalElements(); index++ ){
                        modelRecomLists.add(new ModelRecomList(response.body().getContent().get(index).getName(),
                                response.body().getContent().get(index).getCode(),
                                response.body().getContent().get(index).getDescript(),
                                response.body().getContent().get(index).getRate(),
                                response.body().getContent().get(index).getDescriptEtc()));
                    }
                    topTitle.setText(modelRecomLists.get(0).getTopTitle());
                    adapterScroll.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ModelTopCompare> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });



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
                startActivityForResult(intent, 100);
            }
        });

        userBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 = new Intent(activityMain, ActivityNotiWebView.class);
//                startActivity(intent1);

                Intent intent1 = new Intent(activityMain, ActivityPrefer.class);
                startActivity(intent1);
            }
        });

        adapterScroll.setScrollItemClick(new AdapterScroll.ScrollItemClick() {
            @Override
            public void onClick(int position) {
                Log.e("데이터","값 : "+modelRecomLists.get(position).getCode());
                Intent intent = new Intent(activityMain, ActivitySingleDetail.class);
                intent.putExtra("title", modelRecomLists.get(position).getTitle());
                intent.putExtra("code", modelRecomLists.get(position).getCode());
                startActivity(intent);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(DataManager.get_INstance().isCheckHome()){
                DataManager.get_INstance().setCheckHome(false);
                callFollowInit();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(!hidden){
            if(DataManager.get_INstance().isCheckHome()){
                DataManager.get_INstance().setCheckHome(false);
                callFollowInit();
            }
        }
    }

    void callFollowInit(){

        Call<ModelUserFollow> getReList = RetrofitClient.getInstance().getService().getUserSelect("application/json", "follow");
        getReList.enqueue(new Callback<ModelUserFollow>() {
            @Override
            public void onResponse(Call<ModelUserFollow> call, Response<ModelUserFollow> response) {
                if (response.code() == 200) {
                    if(response.body().getStatus() == 200){

                        modelFollowHomes3.clear();
                        modelFollowHomeAll.clear();
                        modelFollowHomes.clear();

                        if(response.body().getTotalElements() == 0){
                            modelFollowHomes3.add(new ModelFollowHome(false,true, "", "", 0, 0));
                            modelFollowHomeAll.add(new ModelFollowHome(false,true, "", "", 0, 0));
                        }else{

                            modelFollowHomeAll.add(new ModelFollowHome(false, false, "", "", 0, 0));
                            for(int index = 0; index < response.body().getContent().size(); index++){
                                modelFollowHomeAll.add(new ModelFollowHome(false, false, response.body().getContent().get(index).getName(),
                                        response.body().getContent().get(index).getCode(),
                                        response.body().getContent().get(index).getRate(),
                                        response.body().getContent().get(index).getPrice()));
                            }
                            modelFollowHomeAll.add(new ModelFollowHome(true,false, "접기", "", 0, 0));

                            modelFollowHomes3.add(new ModelFollowHome( false,false, "", "", 0, 0));

                            size = response.body().getTotalElements();

                            if(size > 3){
                                size = 3;
                            }

                            for(int index = 0; index < size; index++){
                                modelFollowHomes3.add(new ModelFollowHome(false, false, response.body().getContent().get(index).getName(),
                                        response.body().getContent().get(index).getCode(),
                                        response.body().getContent().get(index).getRate(),
                                        response.body().getContent().get(index).getPrice()));
                            }
                            if(response.body().getTotalElements() > 3){
                                modelFollowHomes3.add(new ModelFollowHome(true, false, "더보기", "", 0, 0));
                            }
                        }
                        modelFollowHomes.addAll(modelFollowHomes3);
                        adapterFollowHome.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelUserFollow> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });
    }

}