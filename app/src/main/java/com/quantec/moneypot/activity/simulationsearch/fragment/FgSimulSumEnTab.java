package com.quantec.moneypot.activity.simulationsearch.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.simulationsearch.ActivitySimulationSearch;
import com.quantec.moneypot.activity.simulationsearch.ModelSimulSum;
import com.quantec.moneypot.activity.simulationsearch.adapter.AdapterSimulSumEnTab;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

import java.util.ArrayList;

public class FgSimulSumEnTab extends Fragment {

    ActivitySimulationSearch activitySimulationSearch;

    ArrayList<ModelSimulSum> modelSimulSums;
    RecyclerView recyclerView;
    AdapterSimulSumEnTab adapterSimulSumEnTab;
    LinearLayoutManager linearLayoutManager;

    Bundle bundle, recomTitle;

    public FgSimulSumEnTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_simul_sumentab, container, false);

        initializeViews();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(activitySimulationSearch);
        recyclerView.setLayoutManager(linearLayoutManager);
        modelSimulSums = new ArrayList<>();

        adapterSimulSumEnTab = new AdapterSimulSumEnTab(modelSimulSums, activitySimulationSearch);
        recyclerView.setAdapter(adapterSimulSumEnTab);

        bundle = getArguments();
        recomTitle = new Bundle();

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

        modelSimulSums.addAll(bundle.getParcelableArrayList("sumEn"));


        adapterSimulSumEnTab.setSingleEnRecomBt1(new AdapterSimulSumEnTab.SingleEnRecomBt1() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSimulSumEnTab.setSingleEnRecomBt2(new AdapterSimulSumEnTab.SingleEnRecomBt2() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSimulSumEnTab.setSingleEnRecomBt3(new AdapterSimulSumEnTab.SingleEnRecomBt3() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSimulSumEnTab.setSingleEnRecomBt4(new AdapterSimulSumEnTab.SingleEnRecomBt4() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSimulSumEnTab.setSingleEnRecomBt5(new AdapterSimulSumEnTab.SingleEnRecomBt5() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });
    }
}
