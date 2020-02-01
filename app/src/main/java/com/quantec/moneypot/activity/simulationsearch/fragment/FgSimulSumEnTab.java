package com.quantec.moneypot.activity.simulationsearch.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.quantec.moneypot.activity.simulationsearch.ActivitySimulationSearch;
import com.quantec.moneypot.activity.simulationsearch.ModelSimulSum;
import com.quantec.moneypot.activity.simulationsearch.adapter.AdapterSimulSumEnTab;
import com.quantec.moneypot.datamodel.nmodel.ModelElSumList;
import com.quantec.moneypot.dialog.DialogSimulSum;
import com.quantec.moneypot.dialog.ModelSimulList;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgSimulSumEnTab extends Fragment {

    ActivitySimulationSearch activitySimulationSearch;

    ArrayList<ModelSimulSum> modelSimulSums;
    RecyclerView recyclerView;
    AdapterSimulSumEnTab adapterSimulSumEnTab;
    LinearLayoutManager linearLayoutManager;

    Bundle bundle, recomTitle;
    ArrayList<String> preAddCode;

    private DialogSimulSum dialogSimul;

    ArrayList<ModelSimulList> modelSimulLists;

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

        preAddCode = new ArrayList<>();
        bundle = getArguments();
        recomTitle = getArguments();

        modelSimulLists = new ArrayList<>();
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
        preAddCode.addAll(recomTitle.getStringArrayList("preAddCode"));

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

        adapterSimulSumEnTab.setSimulSumEnClick(new AdapterSimulSumEnTab.SimulSumEnClick() {
            @Override
            public void onClick(int position) {

                Call<ModelElSumList> getReList = RetrofitClient.getInstance().getService().getElTopList("application/json", modelSimulSums.get(position).getCode(), 5);
                getReList.enqueue(new Callback<ModelElSumList>() {
                    @Override
                    public void onResponse(Call<ModelElSumList> call, Response<ModelElSumList> response) {
                        if (response.code() == 200) {
                            if(response.body().getStatus() == 200){

                                modelSimulLists.clear();

                                for(int index = 0; index < response.body().getTotalElements(); index++){
                                    modelSimulLists.add(new ModelSimulList(response.body().getContent().get(index).getName(),
                                            response.body().getContent().get(index).getCode(),
                                            response.body().getContent().get(index).getRate()));
                                }

                                dialogSimul = new DialogSimulSum(activitySimulationSearch, response.body().getTotalElements(), modelSimulLists, modelSimulSums.get(position).getName(), preAddCode, closeListener);
                                dialogSimul.show();

                                dialogSimul.setDialogSimulResult(new DialogSimulSum.OnDialogSimuResult() {
                                    @Override
                                    public void finish(String title, String code, double rate) {

                                        if (checkedPreAddCode(code)) {

                                            activitySimulationSearch.supportFinishAfterTransition();
                                            dialogSimul.dismiss();
                                            Toast.makeText(activitySimulationSearch.getApplicationContext(), "중복된 주식 입니다.", Toast.LENGTH_SHORT).show();
                                        }
                                        else {

                                            Intent intent1 = new Intent();
                                            intent1.putExtra("addTitle", title);
                                            intent1.putExtra("addCode", code);
                                            intent1.putExtra("addRate", rate);
                                            activitySimulationSearch.setResult(100, intent1);
                                            activitySimulationSearch.supportFinishAfterTransition();
                                            dialogSimul.dismiss();
                                        }
                                    }
                                });

                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelElSumList> call, Throwable t) {
                        Log.e("실패","실패"+t.getMessage());
                    }
                });

            }
        });

    }//onViewCreate 끝

    private View.OnClickListener closeListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogSimul.dismiss();
        }
    };

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



