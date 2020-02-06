package com.quantec.moneypot.activity.Main.Fragment.Tab1.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Activity.ActivityYieldChart;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Activity.AdapterSumTab;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Activity.ModelSingleTab;
import com.quantec.moneypot.activity.Main.Fragment.Tab1.Activity.ModelSumTab;
import com.quantec.moneypot.activity.PotDetail.ActivityPackDetail;
import com.quantec.moneypot.datamanager.DataManager;
import com.quantec.moneypot.datamodel.nmodel.ModelGetRateList;
import com.quantec.moneypot.dialog.DialogYieldFilter;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;
import com.quantec.moneypot.util.FomatToWon.MoneyFormatToWon;
import com.quantec.moneypot.util.view.refresh.Footer.LoadingView;
import com.quantec.moneypot.util.view.refresh.RefreshListenerAdapter;
import com.quantec.moneypot.util.view.refresh.TwinklingRefreshLayout;
import com.quantec.moneypot.util.view.refresh.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.quantec.moneypot.datamodel.dmodel.ModelUserSelectDto;
import com.quantec.moneypot.datamodel.dmodel.userselectdto.Select;

public class FgSumTab extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelSumTab> modelSumTabs;
    AdapterSumTab adapterSumTab;

    TwinklingRefreshLayout refresh;
    ActivityYieldChart activityYieldChart;

    DialogYieldFilter dialogYieldFilter;

    String number = "4";
    String selectedType = "all";

    Bundle followInfo;

    public FgSumTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_yieldchart_sumtab, container, false);

        initializeViews();

        refresh = view.findViewById(R.id.refresh);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(activityYieldChart);
        recyclerView.setLayoutManager(layoutManager);

        modelSumTabs = new ArrayList<>();
        adapterSumTab = new AdapterSumTab(modelSumTabs, activityYieldChart);
        recyclerView.setAdapter(adapterSumTab);

        followInfo = new Bundle();

        return view;
    }

    private void initializeViews() {
        activityYieldChart = (ActivityYieldChart) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActivityYieldChart) {
            activityYieldChart = (ActivityYieldChart) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        yieldFilter("all", false);

        SinaRefreshView headerView = new SinaRefreshView(activityYieldChart);
        headerView.setArrowResource(R.drawable.anim_loading_view);
        headerView.setTextColor(0xff745D5C);
        refresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(activityYieldChart);
        refresh.setBottomView(loadingView);
        refresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                        Toast.makeText(activityYieldChart.getApplicationContext(), "새로고침 완료.", Toast.LENGTH_SHORT).show();

                        yieldFilter(selectedType, false);
                    }
                },1500);
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activityYieldChart.getApplicationContext(), "바텀입니다.", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadmore();
                    }
                },1500);
            }
        });

        adapterSumTab.setSumTabFilterClcick(new AdapterSumTab.SumTabFilterClcick() {
            @Override
            public void onClcick(int position) {
                dialogYieldFilter = new DialogYieldFilter(activityYieldChart, number, m1BtListener, m3BtListener, m6BtListener, mAllBtListener, closeListener);
                dialogYieldFilter.show();
            }
        });

        adapterSumTab.setSumTabFollowClick(new AdapterSumTab.SumTabFollowClick() {
            @Override
            public void onClick(int position) {
                if(modelSumTabs.get(position).getFollow() == 0){
                    followClick(1, 0, modelSumTabs.get(position).getCode(), position);
                }else{
                    followClick(0, 0, modelSumTabs.get(position).getCode(), position);
                }
            }
        });

        adapterSumTab.setSumTabItemClick(new AdapterSumTab.SumTabItemClick() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(activityYieldChart, ActivityPackDetail.class);
                intent.putExtra("title", modelSumTabs.get(position).getTitle());
                intent.putExtra("code", modelSumTabs.get(position).getCode());
                intent.putExtra("potPosition", position);
                startActivityForResult(intent, 100);
            }
        });

    }//onViewCreated 끝

    private View.OnClickListener m1BtListener = new View.OnClickListener() {
        public void onClick(View v) {
            modelSumTabs.get(0).setFilter("1개월 수익률");
            number = "1";
            dialogYieldFilter.dismiss();

            adapterSumTab.notifyItemChanged(0);

            yieldFilter("one", true);
        }
    };
    private View.OnClickListener m3BtListener = new View.OnClickListener() {
        public void onClick(View v) {
            modelSumTabs.get(0).setFilter("3개월 수익률");
            number = "2";
            dialogYieldFilter.dismiss();

            adapterSumTab.notifyItemChanged(0);

            yieldFilter("three", true);
        }
    };
    private View.OnClickListener m6BtListener = new View.OnClickListener() {
        public void onClick(View v) {
            modelSumTabs.get(0).setFilter("6개월 수익률");
            number = "3";
            dialogYieldFilter.dismiss();

            adapterSumTab.notifyItemChanged(0);

            yieldFilter("six", true);
        }
    };
    private View.OnClickListener mAllBtListener = new View.OnClickListener() {
        public void onClick(View v) {
            modelSumTabs.get(0).setFilter("누적 수익률");
            number = "4";
            dialogYieldFilter.dismiss();

            adapterSumTab.notifyItemChanged(0);

            yieldFilter("all", true);
        }
    };

    private View.OnClickListener closeListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogYieldFilter.dismiss();
        }
    };


    void followClick(int follow, int type, String code, int position){

        List<Select> selects = new ArrayList<>();
        Select select = new Select();
        select.setIsDam(0);
        select.setIsLike(0);
        select.setIsZim(0);

        select.setCode(code);
        select.setIsFollow(follow);
        select.setType(type);
        selects.add(select);

        ModelUserSelectDto modelUserSelectDto = new ModelUserSelectDto();
        modelUserSelectDto.setSelects(selects);

        Call<Object> getReList = RetrofitClient.getInstance().getService().setUserSelect("application/json", "follow", modelUserSelectDto);
        getReList.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {

                    DataManager.get_INstance().setCheckTab1(true);
                    DataManager.get_INstance().setCheckTab2(true);
                    DataManager.get_INstance().setCheckHome(true);

                    if(follow == 0){
                        modelSumTabs.get(position).setFollow(0);
                        followInfo.putBoolean("search_follow", false);
                    }else{
                        modelSumTabs.get(position).setFollow(1);
                        followInfo.putBoolean("search_follow", true);
                    }
                    RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_CLICK_ZZIM, followInfo));
                    adapterSumTab.notifyItemChanged(position);
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });
    }


    void yieldFilter(String type, boolean click){

        Call<ModelGetRateList> setSearch = RetrofitClient.getInstance(activityYieldChart).getService().getRateList(type);
        setSearch.enqueue(new Callback<ModelGetRateList>() {
            @Override
            public void onResponse(Call<ModelGetRateList> call, Response<ModelGetRateList> response) {
                if(response.code() == 200) {
                    if(response.body().getStatus() == 200){

                        modelSumTabs.clear();

                        modelSumTabs.add(new ModelSumTab("누적 수익률", "", "", 0, MoneyFormatToWon.moneyFormatToWon(659483), 0));
                        for(int index = 0; index < response.body().getTotalElements(); index++){

                            if(response.body().getContent().get(index).getType() == 1){

                                int follow = 0;
                                if(response.body().getContent().get(index).getUserSelect() != null){
                                    follow = response.body().getContent().get(index).getUserSelect().getIsFollow();
                                }

                                modelSumTabs.add(new ModelSumTab("",
                                        response.body().getContent().get(index).getName(),
                                        response.body().getContent().get(index).getCode(),
                                        response.body().getContent().get(index).getRate(),
                                        MoneyFormatToWon.moneyFormatToWon(response.body().getContent().get(index).getPrice()),
                                        follow));
                            }
                        }

                        if(click){

                            if(type.equals("one")){
                                modelSumTabs.get(0).setFilter("1개월 수익률");
                                number = "1";
                            }else if(type.equals("three")){
                                modelSumTabs.get(0).setFilter("3개월 수익률");
                                number = "2";
                            }else if(type.equals("six")){
                                modelSumTabs.get(0).setFilter("누적 수익률");
                                number = "4";
                            }else{
                                modelSumTabs.get(0).setFilter("누적 수익률");
                                number = "4";
                            }

                            dialogYieldFilter.dismiss();
                        }
                        adapterSumTab.notifyDataSetChanged();
                        selectedType = type;
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelGetRateList> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == 500){
                modelSumTabs.get(data.getIntExtra("potPosition", 0)).setFollow(data.getIntExtra("potFollow", 0));
                adapterSumTab.notifyItemChanged(data.getIntExtra("potPosition", 0));
            }
        }
    }

}
