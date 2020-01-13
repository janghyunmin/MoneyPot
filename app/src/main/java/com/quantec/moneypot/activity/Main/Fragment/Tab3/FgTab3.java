package com.quantec.moneypot.activity.Main.Fragment.Tab3;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.activity.simulationsearch.ActivitySimulationSearch;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.aaa.Code;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.aaa.Ex;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.adapter.AdapterFgTab3;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.adapter.AdapterSelectedItem;
import com.quantec.moneypot.activity.PotDetail.DecorationItemHorizontal;
import com.quantec.moneypot.activity.simulation.ActivitySimulation;
import com.quantec.moneypot.datamanager.ChartManager;
import com.quantec.moneypot.datamodel.dmodel.ModelTransChartList;
import com.quantec.moneypot.datamodel.nmodel.ModelUserFollow;
import com.quantec.moneypot.dialog.DialogLoadingMakingPort;
import com.quantec.moneypot.dialog.DialogSimul;
import com.quantec.moneypot.dialog.ModelSimulList;
import com.quantec.moneypot.network.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgTab3 extends Fragment {

    private ActivityMain activityMain;

    FloatingSearchView floatingSearchView;
    View searchBt;

    ConstraintLayout itemEmptyLayout, itemLayout;
    ImageView userBt;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelFgTab3Follow> modelFgTab3Follows;
    AdapterFgTab3 adapterFgTab3;

    RecyclerView recyclerViewItem;
    RecyclerView.LayoutManager layoutManager2;
    ArrayList<ModelSelectItem> modelSelectItems;
    AdapterSelectedItem adapterSelectedItem;

    SnapHelper snapHelper;

    TextView resultBt, itemNum;

    private int count = 0;

    private DialogSimul dialogSimul;
    ArrayList<ModelSimulList> modelSimulLists;

    ArrayList<ModelTransChartList> modelTransChartLists;

    ArrayList<ModelPreChartList> modelPreChartLists;

    private DialogLoadingMakingPort loading;

    public FgTab3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab3, container, false);

        initializeViews();

        resultBt = view.findViewById(R.id.resultBt);
        resultBt.setBackground(activityMain.getResources().getDrawable(R.drawable.custom_bt_nonselected));
        resultBt.setTextColor(activityMain.getResources().getColor(R.color.c_cccccc));

        itemNum = view.findViewById(R.id.itemNum);

        modelTransChartLists = new ArrayList<>();

        floatingSearchView = view.findViewById(R.id.floating_search_view);
        floatingSearchView.setEnabled(false);

        searchBt = view.findViewById(R.id.searchBt);

        itemEmptyLayout = view.findViewById(R.id.itemEmptyLayout);
        itemLayout = view.findViewById(R.id.itemLayout);
        itemLayout.setVisibility(View.GONE);

        userBt = view.findViewById(R.id.userBt);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityMain);
        recyclerView.setLayoutManager(layoutManager);

        modelFgTab3Follows = new ArrayList<>();
        modelFgTab3Follows.add(new ModelFgTab3Follow("", "", 0, 0,false));
        adapterFgTab3 = new AdapterFgTab3(modelFgTab3Follows, activityMain);
        recyclerView.setAdapter(adapterFgTab3);

        recyclerViewItem = view.findViewById(R.id.recyclerViewItem);
        recyclerViewItem.setHasFixedSize(true);

        layoutManager2 = new LinearLayoutManager(activityMain, RecyclerView.HORIZONTAL, false);
        recyclerViewItem.setLayoutManager(layoutManager2);


        recyclerViewItem.addItemDecoration(new DecorationItemHorizontal(activityMain, 10));

        modelSelectItems = new ArrayList<>();

        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewItem);

        adapterSelectedItem = new AdapterSelectedItem(modelSelectItems, activityMain);
        recyclerViewItem.setAdapter(adapterSelectedItem);

        modelPreChartLists = new ArrayList<>();

        modelSimulLists = new ArrayList<>();

        return view;
    }

    private View.OnClickListener closeListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogSimul.dismiss();
        }
    };

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

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = activityMain.getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            Window window = activityMain.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        RxView.clicks(searchBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Intent intent = new Intent(activityMain, ActivitySimulationSearch.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(activityMain, (View)floatingSearchView, "searchView");
            intent.putParcelableArrayListExtra("chartData", modelPreChartLists);
            startActivityForResult(intent, 100, options.toBundle());
        });


        floatingSearchView.setQueryTextSize(12);
        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
//                floatingSearchView.swapSuggestions();
            }
        });



        Call<ModelUserFollow> getReList = RetrofitClient.getInstance().getService().getUserSelect("application/json", "follow");
        getReList.enqueue(new Callback<ModelUserFollow>() {
            @Override
            public void onResponse(Call<ModelUserFollow> call, Response<ModelUserFollow> response) {
                if (response.code() == 200) {
                    if(response.body().getStatus() == 200){

                       for(int index = 0; index < response.body().getContent().size(); index++){
                           modelFgTab3Follows.add(new ModelFgTab3Follow(response.body().getContent().get(index).getName(),
                                   response.body().getContent().get(index).getCode(),
                                   response.body().getContent().get(index).getRate(),
                                   response.body().getContent().get(index).getType(),false));
                       }
                        adapterFgTab3.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelUserFollow> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });




//        modelFgTab3Follows.add(new ModelFgTab3Follow("맥도날드", "MCD", 0.603, 0,false));
//
//        modelFgTab3Follows.add(new ModelFgTab3Follow("나이키2", "NKE1", 28.90, 0,false));
//        modelFgTab3Follows.add(new ModelFgTab3Follow("나이키3", "NKE2", 28.90, 0,false));
//        modelFgTab3Follows.add(new ModelFgTab3Follow("나이키4", "NKE3", 28.90, 0,false));
//        modelFgTab3Follows.add(new ModelFgTab3Follow("나이키5", "NKE4", 28.90, 0,false));
//        modelFgTab3Follows.add(new ModelFgTab3Follow("나이키6", "NKE5", 28.90, 0,false));
//        modelFgTab3Follows.add(new ModelFgTab3Follow("나이키7", "NKE6", 28.90, 0,false));
//        modelFgTab3Follows.add(new ModelFgTab3Follow("나이키8", "NKE7", 28.90, 0,false));
//
//        modelFgTab3Follows.add(new ModelFgTab3Follow("세계적인 대세 IT 기업들 모음", "묶음기업", 6.11, 1,false));
//        modelFgTab3Follows.add(new ModelFgTab3Follow("나이키", "NKE", 28.90, 0,false));
//        modelFgTab3Follows.add(new ModelFgTab3Follow("제이피모건", "JPM", -2.16, 0,false));
//        modelFgTab3Follows.add(new ModelFgTab3Follow("세계적인 대세 IT 기업들 모음", "묶음기업", 6.11, 1,false));

        adapterFgTab3.setFollowAddClick(new AdapterFgTab3.FollowAddClick() {
            @Override
            public void onClick(int position) {

                if (modelFgTab3Follows.get(position).getGubun() == 0) {

                    if (count == 0) {
                        itemEmptyLayout.setVisibility(View.GONE);
                        itemLayout.setVisibility(View.VISIBLE);
                    }
                    if (count < 10) {

                        if (CheckedList(modelFgTab3Follows.get(position).getCode())) {
                            Toast.makeText(activityMain, "중복된 기업입니다.", Toast.LENGTH_SHORT).show();
                        } else {

                            count++;
                            itemNum.setText("("+count+"/10)");

                            if(count >= 2 ){
                                resultBt.setBackground(activityMain.getResources().getDrawable(R.drawable.custom_bt_selected));
                                resultBt.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                            }else{
                                resultBt.setBackground(activityMain.getResources().getDrawable(R.drawable.custom_bt_nonselected));
                                resultBt.setTextColor(activityMain.getResources().getColor(R.color.c_cccccc));
                            }

                            if (count == 1) {
                                modelSelectItems.add(new ModelSelectItem(modelFgTab3Follows.get(position).getTitle(), modelFgTab3Follows.get(position).getCode(), modelFgTab3Follows.get(position).getRate(), false));

                                modelPreChartLists.add(new ModelPreChartList(modelFgTab3Follows.get(position).getTitle(),
                                        modelFgTab3Follows.get(position).getCode(),
                                        modelFgTab3Follows.get(position).getRate()));

                                modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                                modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                            } else if (count == 2) {
                                modelSelectItems.set(1, new ModelSelectItem(modelFgTab3Follows.get(position).getTitle(), modelFgTab3Follows.get(position).getCode(),
                                        modelFgTab3Follows.get(position).getRate(), false)).setTitle(modelFgTab3Follows.get(position).getTitle());
                                modelPreChartLists.add(new ModelPreChartList(modelFgTab3Follows.get(position).getTitle(),
                                        modelFgTab3Follows.get(position).getCode(),
                                        modelFgTab3Follows.get(position).getRate()));

                            } else if (count == 3) {
                                modelSelectItems.set(2, new ModelSelectItem(modelFgTab3Follows.get(position).getTitle(), modelFgTab3Follows.get(position).getCode(),
                                        modelFgTab3Follows.get(position).getRate(), false)).setTitle(modelFgTab3Follows.get(position).getTitle());
                                modelPreChartLists.add(new ModelPreChartList(modelFgTab3Follows.get(position).getTitle(),
                                        modelFgTab3Follows.get(position).getCode(),
                                        modelFgTab3Follows.get(position).getRate()));

                            } else {
                                modelSelectItems.add(new ModelSelectItem(modelFgTab3Follows.get(position).getTitle(), modelFgTab3Follows.get(position).getCode(), modelFgTab3Follows.get(position).getRate(), false));
                                modelPreChartLists.add(new ModelPreChartList(modelFgTab3Follows.get(position).getTitle(),
                                        modelFgTab3Follows.get(position).getCode(),
                                        modelFgTab3Follows.get(position).getRate()));

                            }
                            adapterSelectedItem.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(activityMain, "더이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }else {

                    modelSimulLists.clear();

                    modelSimulLists.add(new ModelSimulList("","", 0));
                    modelSimulLists.add(new ModelSimulList("엔비디아","NVDA", 20.41));
                    modelSimulLists.add(new ModelSimulList("AMD","AMD", -2.99));

                    modelSimulLists.add(new ModelSimulList("블리자드","ATVI", 10.01));
                    modelSimulLists.add(new ModelSimulList("나이키","NKE", -8.66));

                    dialogSimul = new DialogSimul(activityMain, count, modelSimulLists, modelFgTab3Follows.get(position).getTitle(), modelPreChartLists, closeListener);
                    dialogSimul.show();

                    dialogSimul.setDialogSimulResult(new DialogSimul.OnDialogSimuResult() {
                        @Override
                        public void finish(String title, String code, double rate) {
                            if (count == 0) {
                                itemEmptyLayout.setVisibility(View.GONE);
                                itemLayout.setVisibility(View.VISIBLE);
                            }
                            count++;
                            itemNum.setText("("+count+"/10)");

                            if(count >= 2 ){
                                resultBt.setBackground(activityMain.getResources().getDrawable(R.drawable.custom_bt_selected));
                                resultBt.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                            }else{
                                resultBt.setBackground(activityMain.getResources().getDrawable(R.drawable.custom_bt_nonselected));
                                resultBt.setTextColor(activityMain.getResources().getColor(R.color.c_cccccc));
                            }

                            if (count == 1) {
                                modelSelectItems.add(new ModelSelectItem(title, code, rate, false));
                                modelPreChartLists.add(new ModelPreChartList(title, code, rate));

                                modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                                modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                            } else if (count == 2) {
                                modelSelectItems.set(1, new ModelSelectItem(title, code, rate, false));
                                modelPreChartLists.add(new ModelPreChartList(title, code, rate));

                            } else if (count == 3) {
                                modelSelectItems.set(2, new ModelSelectItem(title, code, rate, false));
                                modelPreChartLists.add(new ModelPreChartList(title, code, rate));

                            } else {
                                modelSelectItems.add(new ModelSelectItem(title, code, rate, false));
                                modelPreChartLists.add(new ModelPreChartList(title, code, rate));

                            }
                            adapterSelectedItem.notifyDataSetChanged();
                            dialogSimul.dismiss();
                        }
                    });
                }
            }
        });

        adapterFgTab3.setFollowItemClick(new AdapterFgTab3.FollowItemClick() {
            @Override
            public void onClick(int position) {
            }
        });

        adapterSelectedItem.setSelectedDeleteClick(new AdapterSelectedItem.SelectedDeleteClick() {
            @Override
            public void onclick(int position) {

                for(int index = 0 ; index < modelPreChartLists.size(); index++){
                    if(modelSelectItems.get(position).getCode().equals(modelPreChartLists.get(index).getCode())){
                        modelPreChartLists.remove(index);
                        return;
                    }
                }

                if(count > 1 & count <= 3){
                    modelSelectItems.remove(position);
                    modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                }else if(count == 1){
                    modelSelectItems.clear();
                    itemEmptyLayout.setVisibility(View.VISIBLE);
                    itemLayout.setVisibility(View.GONE);
                }else{
                    modelSelectItems.remove(position);
                }

                count--;
                itemNum.setText("("+count+"/10)");

                if(count >= 2 ){
                    resultBt.setBackground(activityMain.getResources().getDrawable(R.drawable.custom_bt_selected));
                    resultBt.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                }else{
                    resultBt.setBackground(activityMain.getResources().getDrawable(R.drawable.custom_bt_nonselected));
                    resultBt.setTextColor(activityMain.getResources().getColor(R.color.c_cccccc));
                }
                adapterSelectedItem.notifyDataSetChanged();
            }
        });

        adapterSelectedItem.setSelectedItemClick(new AdapterSelectedItem.SelectedItemClick() {
            @Override
            public void onClick(int position) {
            }
        });

        RxView.clicks(resultBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            if(count < 2){
                Toast.makeText(activityMain, "2개 이상을 선택해 주세요.", Toast.LENGTH_SHORT).show();
            }
            else{

                loading = new DialogLoadingMakingPort(activityMain, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                loading.show();

                ModelSimulCode modelSimulCode = new ModelSimulCode();
                modelSimulCode.setCode("");
                modelSimulCode.setDescript("");
                modelSimulCode.setName("");
                modelSimulCode.setPeriod("all");
                modelSimulCode.setPropensity(701);
                modelSimulCode.setRate(0);
                modelSimulCode.setType(0);

                ArrayList<Code> codes = new ArrayList<>();


                for(int index = 0 ; index < modelPreChartLists.size(); index++){

                    Code code1 = new Code();
                    code1.setCode(modelPreChartLists.get(index).getCode());
                    code1.setType(0);
                    code1.setPtCode("");
                    code1.setWeight(0);

                    codes.add(code1);
                }

                modelSimulCode.setCodes(codes);

                Call<ModelPotSimul> getList = RetrofitClient.getInstance().getService().getPotSimul("application/json", modelSimulCode);
                getList.enqueue(new Callback<ModelPotSimul>() {
                    @Override
                    public void onResponse(Call<ModelPotSimul> call, Response<ModelPotSimul> response) {
                        if (response.code() == 200) {
                            if(response.body().getStatus() == 200){

                                modelTransChartLists.clear();
                                for(int index = 0 ; index < response.body().getContent().getChart().size() ; index++) {
                                    modelTransChartLists.add(new ModelTransChartList(response.body().getContent().getChart().get(index).getDate(),
                                            response.body().getContent().getChart().get(index).getPrice(),
                                            response.body().getContent().getChart().get(index).getExp()));
                                }
                                ChartManager.get_Instance().setTransChartLists(modelTransChartLists);
                                Intent intent = new Intent(activityMain, ActivitySimulation.class);
                                intent.putExtra("rate", response.body().getContent().getCore().getRate());
                                intent.putParcelableArrayListExtra("chartData", modelPreChartLists);
                                startActivityForResult(intent, 100);

                                loading.dismiss();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelPotSimul> call, Throwable t) {
                        Log.e("실패","실패"+t.getMessage());
                        loading.dismiss();
                    }
                });
            }
        });
    }

    boolean CheckedList(String code){
        for(ModelPreChartList modelPreChartLists : modelPreChartLists){
            if(code.equals(modelPreChartLists.getCode())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == 100){

            if (count == 0) {
                itemEmptyLayout.setVisibility(View.GONE);
                itemLayout.setVisibility(View.VISIBLE);
            }

            if (count < 10) {

                    count++;
                    itemNum.setText("("+count+"/10)");

                    if(count >= 2 ){
                        resultBt.setBackground(activityMain.getResources().getDrawable(R.drawable.custom_bt_selected));
                        resultBt.setTextColor(activityMain.getResources().getColor(R.color.c_ffffff));
                    }else{
                        resultBt.setBackground(activityMain.getResources().getDrawable(R.drawable.custom_bt_nonselected));
                        resultBt.setTextColor(activityMain.getResources().getColor(R.color.c_cccccc));
                    }

                    if (count == 1) {
                        modelSelectItems.add(new ModelSelectItem(data.getStringExtra("addTitle"),
                                data.getStringExtra("addCode"),
                                data.getDoubleExtra("addRate", 0), false));


                        modelPreChartLists.add(new ModelPreChartList(data.getStringExtra("addTitle"),
                                data.getStringExtra("addCode"),
                                data.getDoubleExtra("addRate", 0)));

                        modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                        modelSelectItems.add(new ModelSelectItem("", "", 0, true));
                    } else if (count == 2) {
                        modelSelectItems.set(1, new ModelSelectItem(data.getStringExtra("addTitle"),
                                data.getStringExtra("addCode"),
                                data.getDoubleExtra("addRate", 0), false));

                        modelPreChartLists.add(new ModelPreChartList(data.getStringExtra("addTitle"),
                                data.getStringExtra("addCode"),
                                data.getDoubleExtra("addRate", 0)));

                    } else if (count == 3) {
                        modelSelectItems.set(2, new ModelSelectItem(data.getStringExtra("addTitle"),
                                data.getStringExtra("addCode"),
                                data.getDoubleExtra("addRate", 0), false));

                        modelPreChartLists.add(new ModelPreChartList(data.getStringExtra("addTitle"),
                                data.getStringExtra("addCode"),
                                data.getDoubleExtra("addRate", 0)));

                    } else {
                        modelSelectItems.add(new ModelSelectItem(data.getStringExtra("addTitle"),
                                data.getStringExtra("addCode"),
                                data.getDoubleExtra("addRate", 0), false));

                        modelPreChartLists.add(new ModelPreChartList(data.getStringExtra("addTitle"),
                                data.getStringExtra("addCode"),
                                data.getDoubleExtra("addRate", 0)));
                    }

                    adapterSelectedItem.notifyDataSetChanged();
            }
            else {
                Toast.makeText(activityMain, "더이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
