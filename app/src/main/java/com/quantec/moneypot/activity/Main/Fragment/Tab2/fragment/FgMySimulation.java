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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Login.Model.dModel.ModelFidoauthReqDto;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelFlushAuth;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.activity.ActivityModifyFollow;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.adapter.AdapterMySimulation;
import com.quantec.moneypot.datamodel.nmodel.ModelAssetsCustom;
import com.quantec.moneypot.datamodel.nmodel.ModelCustomDetail;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;
import com.quantec.moneypot.util.SharedPreference.SharedPreferenceUtil;
import com.quantec.moneypot.util.view.refresh.Footer.LoadingView;
import com.quantec.moneypot.util.view.refresh.RefreshListenerAdapter;
import com.quantec.moneypot.util.view.refresh.TwinklingRefreshLayout;
import com.quantec.moneypot.util.view.refresh.header.SinaRefreshView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgMySimulation extends Fragment {

    private ActivityMain activityMain;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterMySimulation myAdapter;
    ArrayList<ModelMySimulation> modelMySimulations;

    TwinklingRefreshLayout refresh;

    String userId, authCode;

    public FgMySimulation() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_fgtab2_mysimulation, container, false);

        initializeViews();

        refresh = view.findViewById(R.id.refresh);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        modelMySimulations = new ArrayList<>();

        myAdapter = new AdapterMySimulation(modelMySimulations, getContext());
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
                        refreshLayout.finishRefreshing();
                        Toast.makeText(activityMain.getApplicationContext(), "탑입니다.", Toast.LENGTH_SHORT).show();

                        Call<ModelAssetsCustom> getReList = RetrofitClient.getInstance().getService().getAssetsCustom("application/json", "all");
                        getReList.enqueue(new Callback<ModelAssetsCustom>() {
                            @Override
                            public void onResponse(Call<ModelAssetsCustom> call, Response<ModelAssetsCustom> response) {
                                if (response.code() == 200) {
                                    if(response.body().getStatus() == 200){

                                        modelMySimulations.clear();

                                        if(response.body().getTotalElements() == 0){

                                            modelMySimulations.add(new ModelMySimulation(true, response.body().getTotalElements(), "", "", 0));

                                        }else{
                                            modelMySimulations.add(new ModelMySimulation(false, response.body().getTotalElements(), "", "", 0));
                                            for(int index = 0; index < response.body().getContent().size(); index++){

                                                modelMySimulations.add(new ModelMySimulation(false,
                                                        response.body().getTotalElements(),
                                                        response.body().getContent().get(index).getName(),
                                                        response.body().getContent().get(index).getCode(),
                                                        response.body().getContent().get(index).getRate()));
                                            }
                                        }
                                        myAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<ModelAssetsCustom> call, Throwable t) {
                                Log.e("실패","실패"+t.getMessage());
                            }
                        });

                    }
                },2000);
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activityMain.getApplicationContext(), "바텀입니다.", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }
        });

        Call<ModelAssetsCustom> getReList = RetrofitClient.getInstance().getService().getAssetsCustom("application/json", "all");
        getReList.enqueue(new Callback<ModelAssetsCustom>() {
            @Override
            public void onResponse(Call<ModelAssetsCustom> call, Response<ModelAssetsCustom> response) {
                if (response.code() == 200) {
                    if(response.body().getStatus() == 200){


                        if(response.body().getTotalElements() == 0){

                            modelMySimulations.add(new ModelMySimulation(true, response.body().getTotalElements(), "", "", 0));

                        }else{
                            modelMySimulations.add(new ModelMySimulation(false, response.body().getTotalElements(), "", "", 0));
                            for(int index = 0; index < response.body().getContent().size(); index++){

                                modelMySimulations.add(new ModelMySimulation(false,
                                        response.body().getTotalElements(),
                                        response.body().getContent().get(index).getName(),
                                        response.body().getContent().get(index).getCode(),
                                        response.body().getContent().get(index).getRate()));
                            }
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelAssetsCustom> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });

        RxEventBus.getInstance()
                .filteredObservable(RxEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RxEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(RxEvent rxEvent) {

                        switch (rxEvent.getActiion()) {

                            case RxEvent.POTCUSTOM_REFRESH:

                                    modelMySimulations.clear();

                                    Call<ModelAssetsCustom> getReList = RetrofitClient.getInstance().getService().getAssetsCustom("application/json", "all");
                                    getReList.enqueue(new Callback<ModelAssetsCustom>() {
                                        @Override
                                        public void onResponse(Call<ModelAssetsCustom> call, Response<ModelAssetsCustom> response) {
                                            if (response.code() == 200) {
                                                if(response.body().getStatus() == 200){

                                                    if(response.body().getTotalElements() == 0){

                                                        modelMySimulations.add(new ModelMySimulation(true, 0, "", "", 0));

                                                    }else{
                                                        modelMySimulations.add(new ModelMySimulation(false, 0, "", "", 0));
                                                        for(int index = 0; index < response.body().getContent().size(); index++){
                                                            modelMySimulations.add(new ModelMySimulation(false,
                                                                    response.body().getTotalElements(),
                                                                    response.body().getContent().get(index).getName(),
                                                                    response.body().getContent().get(index).getCode(),
                                                                    response.body().getContent().get(index).getRate()));
                                                        }
                                                    }
                                                    myAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<ModelAssetsCustom> call, Throwable t) {
                                            Log.e("실패","실패"+t.getMessage());
                                        }
                                    });

                                break;
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });

        myAdapter.setMySimulModifyClick(new AdapterMySimulation.MySimulModifyClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(activityMain, ActivityModifyFollow.class);
                startActivityForResult(intent, 100);
            }
        });

        myAdapter.setMySimulClick(new AdapterMySimulation.MySimulClick() {
            @Override
            public void onClick(int position) {

//                Call<Object> tt = RetrofitClient.getInstance(activityMain).getService().getNewsSearch("application/json", 0, 10, "이");
//                tt.enqueue(new Callback<Object>() {
//                    @Override
//                    public void onResponse(Call<Object> call, Response<Object> response) {
//                        if(response.code() == 200) {
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<Object> call, Throwable t) {
//                    }
//                });

                userId = SharedPreferenceUtil.getInstance(activityMain).getStringExtra("userId");
                authCode = SharedPreferenceUtil.getInstance(activityMain).getStringExtra("authCode");
                ModelFidoauthReqDto fidoAuthReqDto = new ModelFidoauthReqDto(authCode, "PASSCODE", userId);
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String data = gson.toJson(fidoAuthReqDto).replace("\\n", "").replace(" ", "")
                        .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
                Call<ModelFlushAuth> tt = RetrofitClient.getInstance(activityMain).getService().getFlushAuthData("application/json", data);
                tt.enqueue(new Callback<ModelFlushAuth>() {
                    @Override
                    public void onResponse(Call<ModelFlushAuth> call, Response<ModelFlushAuth> response) {
                        if(response.code() == 200) {
                            SharedPreferenceUtil.getInstance(activityMain).putAuthCode("authCode", response.body().getContent().getAuthCode());
                            SharedPreferenceUtil.getInstance(activityMain).putTokenA("aToken", response.headers().get("Authorization"));

                            Call<ModelCustomDetail> getReList = RetrofitClient.getInstance().getService().getDetailData("application/json", 2, modelMySimulations.get(position).getCode(), "one", 701);
                            getReList.enqueue(new Callback<ModelCustomDetail>() {
                                @Override
                                public void onResponse(Call<ModelCustomDetail> call, Response<ModelCustomDetail> response) {
                                    if (response.code() == 200) {

                                        for(int index = 0; index < response.body().getContent().getTags().size(); index++){
                                            Log.e("기업들", "목록 : "+response.body().getContent().getTags().get(index).getName());
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<ModelCustomDetail> call, Throwable t) {
                                    Log.e("실패","실패"+t.getMessage());
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelFlushAuth> call, Throwable t) {
                    }
                });
            }
        });


//                userId = SharedPreferenceUtil.getInstance(activityMain).getStringExtra("userId");
//                authCode = SharedPreferenceUtil.getInstance(activityMain).getStringExtra("authCode");
//                ModelFidoauthReqDto fidoAuthReqDto = new ModelFidoauthReqDto(authCode, "PASSCODE", userId);
//                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//                String data = gson.toJson(fidoAuthReqDto).replace("\\n", "").replace(" ", "")
//                        .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
//                Call<ModelFlushAuth> tt = RetrofitClient.getInstance(activityMain).getService().getFlushAuthData("application/json", data);
//                tt.enqueue(new Callback<ModelFlushAuth>() {
//                    @Override
//                    public void onResponse(Call<ModelFlushAuth> call, Response<ModelFlushAuth> response) {
//                        if(response.code() == 200) {
//                            SharedPreferenceUtil.getInstance(activityMain).putAuthCode("authCode", response.body().getContent().getAuthCode());
//                            SharedPreferenceUtil.getInstance(activityMain).putTokenA("aToken", response.headers().get("Authorization"));
//
//                            Call<ModelCustomDetail> getReList = RetrofitClient.getInstance().getService().getDetailData("application/json", 2, modelMySimulations.get(position).getCode(), "one", 701);
//                            getReList.enqueue(new Callback<ModelCustomDetail>() {
//                                @Override
//                                public void onResponse(Call<ModelCustomDetail> call, Response<ModelCustomDetail> response) {
//                                    if (response.code() == 200) {
//
//                                        for(int index = 0; index < response.body().getContent().getTags().size(); index++){
//                                            Log.e("기업들", "목록 : "+response.body().getContent().getTags().get(index).getName());
//                                        }
//                                    }
//                                }
//                                @Override
//                                public void onFailure(Call<ModelCustomDetail> call, Throwable t) {
//                                    Log.e("실패","실패"+t.getMessage());
//                                }
//                            });
//
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<ModelFlushAuth> call, Throwable t) {
//                    }
//                });

    }//onViewCreate 끝.


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(resultCode == 100){
                Call<ModelAssetsCustom> getReList = RetrofitClient.getInstance().getService().getAssetsCustom("application/json", "all");
                getReList.enqueue(new Callback<ModelAssetsCustom>() {
                    @Override
                    public void onResponse(Call<ModelAssetsCustom> call, Response<ModelAssetsCustom> response) {
                        if (response.code() == 200) {
                            if(response.body().getStatus() == 200){

                                modelMySimulations.clear();

                                if(response.body().getTotalElements() == 0){

                                    modelMySimulations.add(new ModelMySimulation(true, response.body().getTotalElements(), "", "", 0));

                                }else{
                                    modelMySimulations.add(new ModelMySimulation(false, response.body().getTotalElements(), "", "", 0));
                                    for(int index = 0; index < response.body().getContent().size(); index++){

                                        modelMySimulations.add(new ModelMySimulation(false,
                                                response.body().getTotalElements(),
                                                response.body().getContent().get(index).getName(),
                                                response.body().getContent().get(index).getCode(),
                                                response.body().getContent().get(index).getRate()));
                                    }
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelAssetsCustom> call, Throwable t) {
                        Log.e("실패","실패"+t.getMessage());
                    }
                });
            }
        }
    }
}
