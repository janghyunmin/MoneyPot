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
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.activity.simulationsearch.ActivitySimulationSearch;
import com.quantec.moneypot.activity.simulationsearch.ModelPreSimulSingle;
import com.quantec.moneypot.activity.simulationsearch.ModelPreSimulSum;
import com.quantec.moneypot.activity.simulationsearch.ModelSimulSingle;
import com.quantec.moneypot.activity.simulationsearch.ModelSimulSum;
import com.quantec.moneypot.activity.simulationsearch.adapter.AdapterSimulAllTabSingle;
import com.quantec.moneypot.activity.simulationsearch.adapter.AdapterSimulAllTabSum;
import com.quantec.moneypot.datamodel.nmodel.ModelElSumList;
import com.quantec.moneypot.dialog.DialogSimul;
import com.quantec.moneypot.dialog.DialogSimulSum;
import com.quantec.moneypot.dialog.ModelSimulList;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgSimulAllTab extends Fragment {

    ActivitySimulationSearch activitySimulationSearch;

    RecyclerView recyclerView1, recyclerView2;
    RecyclerView.LayoutManager layoutManagerSingle, layoutManagerSum;

    ArrayList<ModelSimulSingle> modelSimulSingles;
    ArrayList<ModelSimulSum> modelSimulSums;

    AdapterSimulAllTabSingle adapterSimulAllTabSingle;
    AdapterSimulAllTabSum adapterSimulAllTabSum;

    ArrayList<ModelPreSimulSingle> modelPreSimulSingles;
    ArrayList<ModelPreSimulSum> modelPreSimulSums;

    Bundle getSearchedData, recomTitle, MovedTab;
    ArrayList<String> preAddCode;

    private DialogSimulSum dialogSimul;

    ArrayList<ModelSimulList> modelSimulLists;

    public FgSimulAllTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_simul_alltab, container, false);

        initializeViews();

        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView2 = view.findViewById(R.id.recyclerView2);

        InitRecyclerView();

        //검색시 Fragment_SearchPage에서 받은 데이터를 가져옴
        getSearchedData = getArguments();

        recomTitle = getArguments();

        preAddCode = new ArrayList<>();
        MovedTab = new Bundle();

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

        try {
            modelSimulSingles.addAll(getSearchedData.getParcelableArrayList("singleEn"));
        }catch (Exception e){}
        try {
            modelSimulSums.addAll(getSearchedData.getParcelableArrayList("sumEn"));
        }catch (Exception e){}


        if(modelSimulSingles.size() > 0){
            int size;
            if(modelSimulSingles.size() < 7){
                size = modelSimulSingles.size();
            }else{
                size = 7;
            }
            for(int index = 0 ; index < size ; index++){
                modelPreSimulSingles.add(new ModelPreSimulSingle(modelSimulSingles.size()-1, modelSimulSingles.get(index).isEmpty(), modelSimulSingles.get(index).getName(),
                        modelSimulSingles.get(index).getCode(), modelSimulSingles.get(index).getRate()));
            }
        }

        if(modelSimulSums.size() > 0){
            int size;
            if(modelSimulSums.size() < 7){
                size = modelSimulSums.size();
            }else{
                size = 7;
            }
            for(int index = 0 ; index < size; index++){
                modelPreSimulSums.add(new ModelPreSimulSum(modelSimulSums.size()-1, modelSimulSingles.get(index).isEmpty(), modelSimulSums.get(index).getName(), modelSimulSums.get(index).getCode(),
                        modelSimulSums.get(index).getStock(), modelSimulSums.get(index).getRate()));
            }
        }

        adapterSimulAllTabSingle.setSingleEnDetailBt(new AdapterSimulAllTabSingle.SingleEnDetailBt() {
            @Override
            public void onClick(int position) {
                MovedTab.putInt("moveTab", 0);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_TRANS_PAGE, MovedTab));
            }
        });

        adapterSimulAllTabSum.setSumEnDetailBt(new AdapterSimulAllTabSum.SumEnDetailBt() {
            @Override
            public void onClick(int position) {
                MovedTab.putInt("moveTab", 1);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SEARCH_TRANS_PAGE, MovedTab));
            }
        });

        RecyclerViewState(0);

        adapterSimulAllTabSingle.setSingleEnRecomBt1(new AdapterSimulAllTabSingle.SingleEnRecomBt1() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSimulAllTabSingle.setSingleEnRecomBt2(new AdapterSimulAllTabSingle.SingleEnRecomBt2() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSimulAllTabSingle.setSingleEnRecomBt3(new AdapterSimulAllTabSingle.SingleEnRecomBt3() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSimulAllTabSingle.setSingleEnRecomBt4(new AdapterSimulAllTabSingle.SingleEnRecomBt4() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSimulAllTabSingle.setSingleEnRecomBt5(new AdapterSimulAllTabSingle.SingleEnRecomBt5() {
            @Override
            public void onClick(String title) {
                recomTitle.putString("title", title);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.SIMUL_SEARCH_TITLE, recomTitle));
            }
        });

        adapterSimulAllTabSingle.setSingleEnAddBt(new AdapterSimulAllTabSingle.SingleEnAddBt() {
            @Override
            public void onClick(int position) {

                if (checkedPreAddCode(modelPreSimulSingles.get(position).getCode())) {

                    Toast.makeText(activitySimulationSearch.getApplicationContext(), "중복된 주식 입니다.", Toast.LENGTH_SHORT).show();
                    activitySimulationSearch.supportFinishAfterTransition();
                }
                else {

                    Intent intent1 = new Intent();
                    intent1.putExtra("addTitle", modelPreSimulSingles.get(position).getTitle());
                    intent1.putExtra("addCode", modelPreSimulSingles.get(position).getCode());
                    intent1.putExtra("addRate", modelPreSimulSingles.get(position).getRate());
                    activitySimulationSearch.setResult(100, intent1);
                    activitySimulationSearch.supportFinishAfterTransition();
                }
            }
        });


        adapterSimulAllTabSum.setSumEnClick(new AdapterSimulAllTabSum.SumEnClick() {
            @Override
            public void onClick(int position) {

                Call<ModelElSumList> getReList = RetrofitClient.getInstance().getService().getElTopList("application/json", modelPreSimulSums.get(position).getCode(), 5);
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

                                dialogSimul = new DialogSimulSum(activitySimulationSearch, response.body().getTotalElements(), modelSimulLists, modelPreSimulSums.get(position).getTitle(), preAddCode, closeListener);
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

    //리사이클러뷰 초기화
    void InitRecyclerView(){

        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView2.setHasFixedSize(true);

        layoutManagerSingle = new LinearLayoutManager(getContext());
        layoutManagerSum = new LinearLayoutManager(getContext());

        recyclerView1.setLayoutManager(layoutManagerSingle);
        recyclerView2.setLayoutManager(layoutManagerSum);

        modelSimulSingles = new ArrayList<>();
        modelSimulSums = new ArrayList<>();

        modelPreSimulSingles = new ArrayList<>();
        modelPreSimulSums = new ArrayList<>();

        adapterSimulAllTabSingle = new AdapterSimulAllTabSingle(modelPreSimulSingles, activitySimulationSearch);
        adapterSimulAllTabSum = new AdapterSimulAllTabSum(modelPreSimulSums, activitySimulationSearch);

        recyclerView1.setAdapter(adapterSimulAllTabSingle);
        recyclerView2.setAdapter(adapterSimulAllTabSum);
    }


    //검색된 데이터에 따라서 리사이클러뷰를 숨김
    void RecyclerViewState(int stateNum){
        if(modelPreSimulSingles.size()<=1 & modelPreSimulSums.size() != 1){
            recyclerView1.setVisibility(View.GONE);
        }else{
            recyclerView1.setVisibility(View.VISIBLE);
        }
        if(modelPreSimulSums.size()<=1){
            recyclerView2.setVisibility(View.GONE);
        }else{
            recyclerView2.setVisibility(View.VISIBLE);
        }
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
