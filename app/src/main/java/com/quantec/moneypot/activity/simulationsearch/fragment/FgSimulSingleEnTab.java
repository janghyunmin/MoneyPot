package com.quantec.moneypot.activity.simulationsearch.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.quantec.moneypot.activity.Main.Fragment.Tab3.aaa.Ex;
import com.quantec.moneypot.activity.simulationsearch.ActivitySimulationSearch;
import com.quantec.moneypot.activity.simulationsearch.ModelSimulSingle;
import com.quantec.moneypot.activity.simulationsearch.adapter.AdapterSimulSingleEnTab;
import com.quantec.moneypot.database.room.entry.RoomEntity;
import com.quantec.moneypot.database.room.local.RoomDao;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

import java.util.ArrayList;
import java.util.List;

public class FgSimulSingleEnTab extends Fragment {

    ActivitySimulationSearch activitySimulationSearch;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AdapterSimulSingleEnTab adapterSingleEn;

    Bundle bundle, recomTitle;

    ArrayList<ModelSimulSingle> modelSimulSingles;
    ArrayList<String> preAddCode;

    public FgSimulSingleEnTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_simul_singleentab, container, false);

        initializeViews();

        recyclerView = view.findViewById(R.id.titlePage_tab_recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        modelSimulSingles = new ArrayList<>();

        adapterSingleEn = new AdapterSimulSingleEnTab(modelSimulSingles, activitySimulationSearch);
        recyclerView.setAdapter(adapterSingleEn);

        bundle = getArguments();
        recomTitle = getArguments();
        preAddCode = new ArrayList<>();

        return view;
    }

    private void initializeViews() {
        activitySimulationSearch = (ActivitySimulationSearch) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActivitySimulationSearch) {
            activitySimulationSearch = (ActivitySimulationSearch) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelSimulSingles.addAll(bundle.getParcelableArrayList("singleEn"));
        preAddCode.addAll(recomTitle.getStringArrayList("preAddCode"));

        adapterSingleEn.setSimulSingleEnClick(new AdapterSimulSingleEnTab.SimulSingleEnClick() {
            @Override
            public void onClick(int position) {

                if (checkedPreAddCode(modelSimulSingles.get(position).getCode())) {
                    Toast.makeText(activitySimulationSearch.getApplicationContext(), "중복된 주식 입니다.", Toast.LENGTH_SHORT).show();
                    activitySimulationSearch.finish();
                }
                else {
                    Intent intent = new Intent();
                    intent.putExtra("addTitle", modelSimulSingles.get(position).getName());
                    intent.putExtra("addCode", modelSimulSingles.get(position).getCode());
                    intent.putExtra("addRate", modelSimulSingles.get(position).getRate());
                    activitySimulationSearch.setResult(100, intent);
                    activitySimulationSearch.finish();
                }
            }
        });

        adapterSingleEn.setSingleEnRecomBt1(new AdapterSimulSingleEnTab.SingleEnRecomBt1() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSingleEn.setSingleEnRecomBt2(new AdapterSimulSingleEnTab.SingleEnRecomBt2() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSingleEn.setSingleEnRecomBt3(new AdapterSimulSingleEnTab.SingleEnRecomBt3() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSingleEn.setSingleEnRecomBt4(new AdapterSimulSingleEnTab.SingleEnRecomBt4() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSingleEn.setSingleEnRecomBt5(new AdapterSimulSingleEnTab.SingleEnRecomBt5() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });
    }

    boolean checkedPreAddCode(String addCode) {

        boolean key = false;
        for(String getCode : preAddCode){
            if(getCode.equals(addCode)){
                key = true;
            }
        }
        return key;
    }
}
