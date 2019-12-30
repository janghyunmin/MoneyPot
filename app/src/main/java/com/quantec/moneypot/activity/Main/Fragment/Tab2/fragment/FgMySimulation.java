package com.quantec.moneypot.activity.Main.Fragment.Tab2.fragment;

import android.content.Context;
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
import com.quantec.moneypot.activity.Main.Fragment.Tab2.adapter.AdapterMySimulation;
import com.quantec.moneypot.datamodel.nmodel.ModelAssetsCustom;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.util.view.refresh.Footer.LoadingView;
import com.quantec.moneypot.util.view.refresh.RefreshListenerAdapter;
import com.quantec.moneypot.util.view.refresh.TwinklingRefreshLayout;
import com.quantec.moneypot.util.view.refresh.header.SinaRefreshView;

import java.util.ArrayList;

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

                            modelMySimulations.add(new ModelMySimulation(true, 0, "", "", 0));

                        }else{
                            modelMySimulations.add(new ModelMySimulation(false, 0, "", "", 0));
                            for(int index = 0; index < response.body().getContent().size(); index++){
//                                modelMySimulations.add(new ModelMySimulation(false,
//                                        response.body().getTotalElements(),
//                                        response.body().getContent().get(index).getName(),
//                                        response.body().getContent().get(index).getCode(),
//                                        response.body().getContent().get(index).getRate()));
                                modelMySimulations.add(new ModelMySimulation(false,
                                        response.body().getTotalElements(),
                                        response.body().getContent().get(index).getName(),
                                        response.body().getContent().get(index).getCode(),
                                        20.55));
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
