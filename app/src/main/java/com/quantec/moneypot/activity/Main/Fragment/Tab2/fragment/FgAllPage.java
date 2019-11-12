package com.quantec.moneypot.activity.Main.Fragment.Tab2.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.adapter.AdapterAllPage;
import com.quantec.moneypot.datamodel.dmodel.ModelFgAllPage;
import com.quantec.moneypot.activity.PotDetail.ActivitySingleDetail;
import com.quantec.moneypot.dialog.DialogBasketFilter;
import com.quantec.moneypot.R;
import com.quantec.moneypot.databinding.FgAllpageBinding;
import com.quantec.moneypot.util.DecimalScale.DecimalScale;
import com.quantec.moneypot.util.view.refresh.Footer.LoadingView;
import com.quantec.moneypot.util.view.refresh.RefreshListenerAdapter;
import com.quantec.moneypot.util.view.refresh.TwinklingRefreshLayout;
import com.quantec.moneypot.util.view.refresh.header.SinaRefreshView;

public class FgAllPage extends Fragment{

    FgAllpageBinding binding;

    //차트
    List<Entry> entries;
    LineDataSet lineDataSet;
    LineData lineData;

    RecyclerView.LayoutManager mLayoutManager;
    AdapterAllPage myAdapter;
    ArrayList<ModelFgAllPage> myData;

    private ActivityMain activityMain;
    String packName;

    private DialogBasketFilter dialogBasketFilter;
    String filterNumber = "00";

    public FgAllPage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fg_allpage, container, false);

        initializeViews();

        binding.recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        myData = new ArrayList<>();

        entries = new ArrayList<>();
        lineDataSet = new LineDataSet(entries, null);
        lineData = new LineData(lineDataSet);
        myAdapter = new AdapterAllPage(myData, getContext());
        binding.recyclerView.setAdapter(myAdapter);

        packName = getActivity().getPackageName();

        return binding.getRoot();
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

        //찜 데이터 리스트
        initZimPotList();

        SinaRefreshView headerView = new SinaRefreshView(activityMain);
        headerView.setArrowResource(R.drawable.anim_loading_view);
        headerView.setTextColor(0xff745D5C);
        binding.refresh.setHeaderView(headerView);

        LoadingView loadingView = new LoadingView(activityMain);
        binding.refresh.setBottomView(loadingView);
        binding.refresh.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                        Toast.makeText(activityMain, "탑입니다.", Toast.LENGTH_SHORT).show();
                        initZimPotList();
                    }
                },2000);
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activityMain, "바텀입니다.", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadmore();
                    }
                },2000);
            }
        });

        //팟 찜 클릭
        myAdapter.setSelectZzimClick(new AdapterAllPage.SelectZzimClick() {
            @Override
            public void onClick(int position) {

                if(myData.get(position).isZim()){
//                    setZimPot(myData.get(position).getCode(), false, "del", position);
                }else{
//                    setZimPot(myData.get(position).getCode(), true, "add", position);
                }
            }
        });

        //팟 아이템 클릭
        myAdapter.setItemClick(new AdapterAllPage.ItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent1 = new Intent(activityMain, ActivitySingleDetail.class);
                intent1.putExtra("potCode", myData.get(position).getCode());
                startActivity(intent1);
            }
        });

        myAdapter.setFilterBtClick(new AdapterAllPage.FilterBtClick() {
            @Override
            public void onClick(int position) {
//                myData.get(position).setFilterText("단일 기업 순");
//                myAdapter.notifyItemChanged(position);

                dialogBasketFilter = new DialogBasketFilter(activityMain, filterNumber, selectedAppBt, selectedAccountBt, selectedProductBt);
                dialogBasketFilter.show();
            }
        });

    }//onViewCreate 끝

    private View.OnClickListener selectedAppBt = new View.OnClickListener() {
        public void onClick(View v) {

            myData.get(0).setFilterText("전체");
            myAdapter.notifyItemChanged(0);
            filterNumber = "01";
            dialogBasketFilter.dismiss();

        }
    };
    private View.OnClickListener selectedAccountBt = new View.OnClickListener() {
        public void onClick(View v) {

            myData.get(0).setFilterText("단일 기업 순");
            myAdapter.notifyItemChanged(0);
            filterNumber = "02";
            dialogBasketFilter.dismiss();

        }
    };
    private View.OnClickListener selectedProductBt = new View.OnClickListener() {
        public void onClick(View v) {

            myData.get(0).setFilterText("묶음 기업 순");
            myAdapter.notifyItemChanged(0);
            filterNumber = "03";
            dialogBasketFilter.dismiss();

        }
    };



    void initZimPotList(){

        for(int index = 0 ; index < 20 ; index++){
                            myData.add(new ModelFgAllPage(false,
                                    index+" 번호입니다",
                                    "",
                                    DecimalScale.decimalScale(String.valueOf(0.4314*100), 2, 2),
                                    true,
                                    false,
                                    "",
                                    true,
                                    0L,
                                    "전체"));
                        }
        myAdapter.notifyDataSetChanged();


//        Filter filter = new Filter();
//        Call<ModelZimPotList> getReList = RetrofitClient.getInstance().getService().getZimPotList("application/json", filter, "Z", 0, 0,10);
//        getReList.enqueue(new Callback<ModelZimPotList>() {
//            @Override
//            public void onResponse(Call<ModelZimPotList> call, Response<ModelZimPotList> response) {
//                if (response.code() == 200) {
//
//                    myData.clear();
//
//                    if(response.body().getTotalElements() == 0){
//                        myData.add(new ModelFgTab4(true, "", "", 0, false, false, "", true, 0L));
//                    }
//                    else{
//                        for(int index = 0 ; index < response.body().getContent().size() ; index++){
//                            myData.add(new ModelFgTab4(false,
//                                    response.body().getContent().get(index).getName(),
//                                    response.body().getContent().get(index).getCode(),
//                                    DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(index).getRate()*100), 2, 2),
//                                    response.body().getContent().get(index).getIsZim(),
//                                    false,
//                                    "",
//                                    true,
//                                    0L));
//                        }
//                    }
//                    myAdapter.notifyDataSetChanged();
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelZimPotList> call, Throwable t) {
//                Log.e("에러값","값 : "+t.getMessage());
//            }
//        });
    }

}
