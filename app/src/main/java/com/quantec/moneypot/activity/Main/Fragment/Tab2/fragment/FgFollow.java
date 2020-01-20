package com.quantec.moneypot.activity.Main.Fragment.Tab2.fragment;

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
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.adapter.AdapterFollow;
import com.quantec.moneypot.activity.PotDetail.ActivitySingleDetail;
import com.quantec.moneypot.datamanager.DataManager;
import com.quantec.moneypot.datamodel.dmodel.ModelFgAllPage;
import com.quantec.moneypot.datamodel.dmodel.ModelUserSelectDto;
import com.quantec.moneypot.datamodel.dmodel.userselectdto.Select;
import com.quantec.moneypot.datamodel.nmodel.ModelUserFollow;
import com.quantec.moneypot.dialog.DialogBasketFilter;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.util.view.refresh.Footer.LoadingView;
import com.quantec.moneypot.util.view.refresh.RefreshListenerAdapter;
import com.quantec.moneypot.util.view.refresh.TwinklingRefreshLayout;
import com.quantec.moneypot.util.view.refresh.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgFollow extends Fragment {

    private ActivityMain activityMain;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterFollow myAdapter;
    ArrayList<ModelFgAllPage> myData;

    ArrayList<ModelFgAllPage> singleList;
    ArrayList<ModelFgAllPage> sumList;
    ArrayList<ModelFgAllPage> allList;

    private DialogBasketFilter dialogBasketFilter;
    String filterNumber = "00";

    TwinklingRefreshLayout refresh;

    boolean state = false;

    public FgFollow() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_fgtab2_follow, container, false);

        initializeViews();

        refresh = view.findViewById(R.id.refresh);

        singleList = new ArrayList<>();
        sumList = new ArrayList<>();
        allList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        myData = new ArrayList<>();

        myAdapter = new AdapterFollow(allList, getContext());
        recyclerView.setAdapter(myAdapter);

        return view;
    }

    private void initializeViews(){
        activityMain = (ActivityMain) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityMain) {
            activityMain = (ActivityMain) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SinaRefreshView headerView = new SinaRefreshView(activityMain);
        headerView.setArrowResource(R.drawable.anim_loading_view);
        headerView.setTextColor(0xff745D5C);
        refresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(activityMain);
        refresh.setBottomView(loadingView);
        refresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(getFollowList()){
                            refreshLayout.finishRefreshing();
                            Toast.makeText(activityMain.getApplicationContext(), "새로고침 완료.", Toast.LENGTH_SHORT).show();
                        }else{
                            refreshLayout.finishRefreshing();
                            Toast.makeText(activityMain.getApplicationContext(), "새로고침 실패.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },1500);
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activityMain.getApplicationContext(), "바텀입니다.", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadmore();
                    }
                },1500);
            }
        });

        //팟 찜 클릭
        myAdapter.setSelectZzimClick(new AdapterFollow.SelectZzimClick() {
            @Override
            public void onClick(int position) {

                DataManager.get_INstance().setCheckTab1(true);

                if(myData.get(position).getFollow() == 0){
                    followClick(position, myData.get(position).getCode(), 1, myData.get(position).getType());
                }else{
                    followClick(position, myData.get(position).getCode(), 0, myData.get(position).getType());
                }
            }
        });

        //팟 아이템 클릭
        myAdapter.setItemClick(new AdapterFollow.ItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(activityMain, ActivitySingleDetail.class);
                intent1.putExtra("potCode", myData.get(position).getCode());
                startActivity(intent1);
            }
        });

        myAdapter.setFilterBtClick(new AdapterFollow.FilterBtClick() {
            @Override
            public void onClick(int position) {
                dialogBasketFilter = new DialogBasketFilter(activityMain, filterNumber, selectedAppBt, selectedAccountBt, selectedProductBt);
                dialogBasketFilter.show();
            }
        });

        getFollowList();

    }//onViewCreate 끝


    private View.OnClickListener selectedAppBt = new View.OnClickListener() {
        public void onClick(View v) {

            myData.get(0).setFilterTitle("전체");
            filterNumber = "01";
            dialogBasketFilter.dismiss();

            allList.clear();
            allList.addAll(myData);
            myAdapter.notifyDataSetChanged();
        }
    };
    private View.OnClickListener selectedAccountBt = new View.OnClickListener() {
        public void onClick(View v) {

            myData.get(0).setFilterTitle("단일 기업 순");
            filterNumber = "02";
            dialogBasketFilter.dismiss();

            allList.clear();
            allList.addAll(singleList);
            myAdapter.notifyDataSetChanged();
        }
    };
    private View.OnClickListener selectedProductBt = new View.OnClickListener() {
        public void onClick(View v) {

            myData.get(0).setFilterTitle("묶음 기업 순");
            filterNumber = "03";
            dialogBasketFilter.dismiss();

            allList.clear();
            allList.addAll(sumList);
            myAdapter.notifyDataSetChanged();
        }
    };

    void filteredList(int type){

        if(type == 2){

        }else if(type == 1){

        }else{

        }
    }

    boolean getFollowList(){

        state = false;

        Call<ModelUserFollow> getReList = RetrofitClient.getInstance().getService().getUserSelect("application/json", "follow");
        getReList.enqueue(new Callback<ModelUserFollow>() {
            @Override
            public void onResponse(Call<ModelUserFollow> call, Response<ModelUserFollow> response) {
                if (response.code() == 200) {
                    if(response.body().getStatus() == 200){

                        myData.clear();
                        singleList.clear();
                        sumList.clear();
                        allList.clear();
                        if(response.body().getTotalElements() == 0){

                            myData.add(new ModelFgAllPage(true, 0, "", "", 0, 0, 2,"전체"));
                            singleList.add(new ModelFgAllPage(true, 0, "", "", 0, 0, 2,"전체"));
                            sumList.add(new ModelFgAllPage(true, 0, "", "", 0, 0, 2,"전체"));
                        }else{
                            myData.add(new ModelFgAllPage(false, 0, "", "", 0, 0, 2,"전체"));
                            for(int index = 0 ; index < response.body().getContent().size(); index++){

                                myData.add(new ModelFgAllPage(false,
                                        response.body().getContent().get(index).getType(),
                                        response.body().getContent().get(index).getName(),
                                        response.body().getContent().get(index).getCode(),
                                        response.body().getContent().get(index).getRate(),
                                        response.body().getContent().get(index).getUserSelect().getIsFollow(),
                                        2,"전체"));

                            }

                            singleList.add(new ModelFgAllPage(false, 0, "", "", 0, 0, 2,"단일 기업 순"));
                            for(int index = 1 ; index < myData.size(); index++){
                                Log.e("타입", "값 : "+myData.get(index).getType());
                                if(myData.get(index).getType() == 0){
                                    singleList.add(new ModelFgAllPage(false,
                                            myData.get(index).getType(),
                                            myData.get(index).getTitle(),
                                            myData.get(index).getCode(),
                                            myData.get(index).getRate(),
                                            myData.get(index).getFollow(),
                                            2,"단일 기업 순"));
                                }
                            }

                            sumList.add(new ModelFgAllPage(false, 0, "", "", 0, 0, 2,"묶음 기업 순"));
                            for(int index = 1 ; index < myData.size(); index++){
                                if(myData.get(index).getType() == 1){
                                    sumList.add(new ModelFgAllPage(false,
                                            myData.get(index).getType(),
                                            myData.get(index).getTitle(),
                                            myData.get(index).getCode(),
                                            myData.get(index).getRate(),
                                            myData.get(index).getFollow(),
                                            2,"묶음 기업 순"));
                                }
                            }
                        }
                        allList.addAll(myData);
                        myAdapter.notifyDataSetChanged();
                        state = true;
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelUserFollow> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });

        return state;
    }

    void followClick(int position, String code, int follow, int type){

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
                   myData.get(position).setFollow(follow);
                   myAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });
    }
}
