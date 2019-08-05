package quantec.com.moneypot.Activity.Main.Fragment.Tab3.Fragment;

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

import java.util.ArrayList;

import quantec.com.moneypot.Activity.Main.Fragment.Tab3.Activity.ActivityPotMarket;
import quantec.com.moneypot.Activity.Main.Fragment.Tab3.Adapter.AdapterFg3mTab;
import quantec.com.moneypot.Activity.PotDetail.ActivityPotDetail;
import quantec.com.moneypot.DataModel.dModel.Filter;
import quantec.com.moneypot.DataModel.dModel.ModelFg3mTab3Banner;
import quantec.com.moneypot.DataModel.dModel.ModelPotCookAll;
import quantec.com.moneypot.DataModel.dModel.ModelPotMarketData;
import quantec.com.moneypot.DataModel.dModel.Select;
import quantec.com.moneypot.DataModel.nModel.ModelMarketPot;
import quantec.com.moneypot.DataModel.nModel.ModelPotList;
import quantec.com.moneypot.DataModel.nModel.ModelZimData;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.util.DecimalScale.DecimalScale;
import quantec.com.moneypot.util.view.AnimatedRecyclerView;
import quantec.com.moneypot.util.view.refresh.Footer.LoadingView;
import quantec.com.moneypot.util.view.refresh.RefreshListenerAdapter;
import quantec.com.moneypot.util.view.refresh.TwinklingRefreshLayout;
import quantec.com.moneypot.util.view.refresh.header.SinaRefreshView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fg3mTab extends Fragment {

//    RecyclerView recyclerView;
    AnimatedRecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelPotMarketData> modelPotMarketData;
    AdapterFg3mTab adapterFg3mTab;

    ActivityPotMarket activityPotMarket;

    ArrayList<ModelFg3mTab3Banner> modelFg3mTab3Banners;

    TwinklingRefreshLayout refresh;

    boolean lastPageState = false;
    int pageNumber = 0;

    public Fg3mTab() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_potmarket_fg3mtab, container, false);

        initializeViews();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activityPotMarket);
        recyclerView.setLayoutManager(layoutManager);

        modelPotMarketData = new ArrayList<>();
        modelFg3mTab3Banners = new ArrayList<>();

        adapterFg3mTab = new AdapterFg3mTab(modelPotMarketData, activityPotMarket, modelFg3mTab3Banners);
        recyclerView.setAdapter(adapterFg3mTab);

        refresh = view.findViewById(R.id.refresh);

        return view;
    }


    private void initializeViews(){
        activityPotMarket = (ActivityPotMarket) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityPotMarket) {
            activityPotMarket = (ActivityPotMarket) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SinaRefreshView headerView = new SinaRefreshView(activityPotMarket);
        headerView.setArrowResource(R.drawable.anim_loading_view);
        headerView.setPullDownStr("마켓 페이지 새로고침입니다.");
        headerView.setTextColor(0xff745D5C);
        refresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(activityPotMarket);
        refresh.setBottomView(loadingView);

        refresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                        Toast.makeText(activityPotMarket, "탑입니다.", Toast.LENGTH_SHORT).show();
                        pageNumber = 0;
                        initPotList();
                    }
                },2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(lastPageState){
                            Toast.makeText(activityPotMarket, "더 이상 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                            refreshLayout.finishLoadmore();
                        }else{
                            Toast.makeText(activityPotMarket, "바텀입니다.", Toast.LENGTH_SHORT).show();
                            refreshLayout.finishLoadmore();

                            //팟 리스트 데이터 추가
                            addPotList(pageNumber);
                        }

                    }
                },2000);
            }
        });

        //팟 리스트 데이터 받기
        initPotList();

        modelFg3mTab3Banners.add(new ModelFg3mTab3Banner("","1번"));
        modelFg3mTab3Banners.add(new ModelFg3mTab3Banner("","2번"));
        modelFg3mTab3Banners.add(new ModelFg3mTab3Banner("","3번"));
        modelFg3mTab3Banners.add(new ModelFg3mTab3Banner("","4번"));
        modelFg3mTab3Banners.add(new ModelFg3mTab3Banner("","5번"));
        modelFg3mTab3Banners.add(new ModelFg3mTab3Banner("","6번"));

        adapterFg3mTab.notifyDataSetChanged();

        adapterFg3mTab.setFg3mTabItemClick(new AdapterFg3mTab.Fg3mTabItemClick() {
            @Override
            public void onClick(int position) {

            }
        });

        adapterFg3mTab.setFg3mTabZimBtClick(new AdapterFg3mTab.Fg3mTabZimBtClick() {
            @Override
            public void onClick(int position) {

                if(modelPotMarketData.get(position).isCheck()){
//                    modelPotMarketData.get(position).setCheck(false);

                    setZimPot(modelPotMarketData.get(position).getPotCode(), false, "del", position);
                }
                else{
//                    modelPotMarketData.get(position).setCheck(true);

                    setZimPot(modelPotMarketData.get(position).getPotCode(), true, "add", position);
                }

            }
        });


        adapterFg3mTab.setFg3mTabItemClick(new AdapterFg3mTab.Fg3mTabItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(activityPotMarket, ActivityPotDetail.class);
                intent1.putExtra("potCode", modelPotMarketData.get(position).getPotCode());
                startActivity(intent1);
            }
        });

    }//onViewCreate 끝


    void setZimPot(String potCode, boolean zim, String mode, int position){

        Select select = new Select(potCode, false, zim);
        Call<ModelZimData> getSelectPort = RetrofitClient.getInstance().getService().getSelectedPortDate("application/json", select, 1, mode);
        getSelectPort.enqueue(new Callback<ModelZimData>() {
            @Override
            public void onResponse(Call<ModelZimData> call, Response<ModelZimData> response) {
                if (response.code() == 200) {

                    if(zim){
                        modelPotMarketData.get(position).setCheck(true);
                    }else{
                        modelPotMarketData.get(position).setCheck(false);
                    }
                    adapterFg3mTab.notifyItemChanged(position);
                }
            }

            @Override
            public void onFailure(Call<ModelZimData> call, Throwable t) {
                Toast.makeText(getActivity(), "네트워크가 불안정 합니다\n 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initPotList(){

        modelPotMarketData.clear();
        modelPotMarketData.add(new ModelPotMarketData("", 0, "", "",0, false));
        Filter filter = new Filter();
        Call<ModelMarketPot> getReList = RetrofitClient.getInstance().getService().getPotList2("application/json", filter, "E", 90, 0,10);
        getReList.enqueue(new Callback<ModelMarketPot>() {
            @Override
            public void onResponse(Call<ModelMarketPot> call, Response<ModelMarketPot> response) {
                if (response.code() == 200) {

                    lastPageState = response.body().getPage().getLastPage();
                    pageNumber++;

                    boolean zimState;

                    if(response.body().getContent().isEmpty()){
                        Toast.makeText(activityPotMarket, "더 이상 값이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        for(int index = 0; index < response.body().getContent().size() ; index++){

                            if(response.body().getContent().get(index).getSelect() != null){
                                zimState = response.body().getContent().get(index).getSelect().getIsZim();
                            }else{
                                zimState = false;
                            }
                            modelPotMarketData.add(new ModelPotMarketData("",
                                    modelPotMarketData.size(),
                                    response.body().getContent().get(index).getName(),
                                    response.body().getContent().get(index).getCode(),
                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(index).getRateThr()*100), 2, 2),
                                    zimState));
                        }
                    }


                    adapterFg3mTab.notifyDataSetChanged();
                    recyclerView.scheduleLayoutAnimation();
                }
            }
            @Override
            public void onFailure(Call<ModelMarketPot> call, Throwable t) {
                Log.e("에러값","값 : "+t.getMessage());
            }
        });
    }

    void addPotList(int page){

        Filter filter = new Filter();
        Call<ModelMarketPot> getReList = RetrofitClient.getInstance().getService().getPotList2("application/json", filter, "E", 90, page,10);
        getReList.enqueue(new Callback<ModelMarketPot>() {
            @Override
            public void onResponse(Call<ModelMarketPot> call, Response<ModelMarketPot> response) {
                if (response.code() == 200) {

                    lastPageState = response.body().getPage().getLastPage();
                    pageNumber++;

                    boolean zimState;

                    if(response.body().getContent().isEmpty()){
                        Toast.makeText(activityPotMarket, "더 이상 값이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        for(int index = 0; index < response.body().getContent().size() ; index++){

                            if(response.body().getContent().get(index).getSelect() != null){
                                zimState = response.body().getContent().get(index).getSelect().getIsZim();
                            }else{
                                zimState = false;
                            }
                            modelPotMarketData.add(new ModelPotMarketData("",
                                    modelPotMarketData.size(),
                                    response.body().getContent().get(index).getName(),
                                    response.body().getContent().get(index).getCode(),
                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(index).getRateThr()*100), 2, 2),
                                    zimState));
                        }
                    }
                    adapterFg3mTab.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ModelMarketPot> call, Throwable t) {
                Log.e("에러값","값 : "+t.getMessage());
            }
        });
    }

}
