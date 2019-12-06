package com.quantec.moneypot.activity.Search.SearchedPage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import com.quantec.moneypot.activity.Search.ActivitySearch;
import com.quantec.moneypot.activity.Search.SearchedPage.Fragment.FgAllTab;
import com.quantec.moneypot.activity.Search.SearchedPage.Fragment.FgDescTab;
import com.quantec.moneypot.activity.Search.SearchedPage.Fragment.FgStockTab;
import com.quantec.moneypot.activity.Search.SearchedPage.Fragment.FgTitleTab;
import com.quantec.moneypot.datamodel.dmodel.Filter;
import com.quantec.moneypot.datamodel.dmodel.ModelDescItem;
import com.quantec.moneypot.datamodel.dmodel.ModelEmptyItem;
import com.quantec.moneypot.datamodel.dmodel.ModelPostDescItem;
import com.quantec.moneypot.datamodel.dmodel.ModelPostStockItem;
import com.quantec.moneypot.datamodel.dmodel.ModelPostTitleItem;
import com.quantec.moneypot.datamodel.dmodel.ModelStockItem;
import com.quantec.moneypot.datamodel.dmodel.ModelTitleItem;
import com.quantec.moneypot.datamodel.nmodel.ModelSearchedPageList;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.R;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;
import com.quantec.moneypot.util.DecimalScale.DecimalScale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgSearchedPage extends Fragment {

    ViewPager viewPager;

    TextView tab1, tab2, tab3, tab4, preTab;

    ActivitySearch activitySearch;
    //Fragment_SearchPage 에서 넘겨 받은 검색 단어
    public static String searchText;

    ArrayList<ModelTitleItem> titleItemModels;
    ArrayList<ModelDescItem> descItemModels;
    ArrayList<ModelStockItem> stockItemModels;
    ArrayList<ModelEmptyItem> emptyItemModels;

    ArrayList<ModelPostTitleItem> postTitleItemModels;
    ArrayList<ModelPostDescItem> postDescItemModels;
    ArrayList<ModelPostStockItem> postStockItemModels;

    Bundle bundle;

    int noContent = 0;
    int categoryState;

    /**
     *
     * 검색 시 제목, 설명, 검색 중에서 검색된 부분에 따라서 달라짐
     * 201 ~ 207까지 값을 가지며 200번은 제목, 설명, 종목 모두 검색이 없을때이고 이때 200 -> 208 로 대체한다
     * list에서 3개의 index를 가지며 0~2번까지 중에서 0이 최하위자릿수 2가 최상위 자릿수 이다
     * 즉  0 0 1 -> 1 이되고  0 1 0 -> 2가 된다
     * 예를들어 제목만 검색되었다면 0(종목) 0(설명) 1(제목) 이 되고 값은 1이되며 최종적으로 200+get(0)+get(1)+get(2) 를 통해서 201이 된다
     * 200 ( 전부 검색 없음 ) 일경우 208로 대체된다
     */
    ArrayList<Integer> searchStatus = new ArrayList<>();

    SearchPortAdapter searchPortAdapter;

    public FgSearchedPage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_searchedpage, container, false);

        initializeViews();

        titleItemModels = new ArrayList<>();
        descItemModels = new ArrayList<>();
        stockItemModels = new ArrayList<>();
        emptyItemModels = new ArrayList<>();

        postTitleItemModels = new ArrayList<>();
        postDescItemModels = new ArrayList<>();
        postStockItemModels = new ArrayList<>();


        viewPager = view.findViewById(R.id.viewPager);

        tab1 = view.findViewById(R.id.tab1);
        tab2 = view.findViewById(R.id.tab2);
        tab3 = view.findViewById(R.id.tab3);
        tab4 = view.findViewById(R.id.tab4);

        preTab = tab1;

        viewPager.setOffscreenPageLimit(4);
        bundle = new Bundle();

        searchPortAdapter = new SearchPortAdapter(getChildFragmentManager());

        return view;
    }

    private void initializeViews(){
        activitySearch = (ActivitySearch) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivitySearch) {
            activitySearch = (ActivitySearch) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        ClickTab(tab1);
                        break;
                    case 1:
                        ClickTab(tab2);
                        break;
                    case 2:
                        ClickTab(tab3);
                        break;
                    case 3:
                        ClickTab(tab4);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
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

                            case RxEvent.SEARCH_TRANS_PAGE:

                                if(rxEvent.getBundle().getInt("allPage_MovedTab") == 0) {
                                    ClickTab(tab2);
                                    viewPager.setCurrentItem(1, true);
                                }else if(rxEvent.getBundle().getInt("allPage_MovedTab") == 1) {
                                    ClickTab(tab3);
                                    viewPager.setCurrentItem(2, true);
                                }else {
                                    ClickTab(tab4);
                                    viewPager.setCurrentItem(3, true);
                                }
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

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickTab(tab1);
                viewPager.setCurrentItem(0, true);
            }
        });

        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickTab(tab2);
                viewPager.setCurrentItem(1, true);
            }
        });

        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickTab(tab3);
                viewPager.setCurrentItem(2, true);
            }
        });

        tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickTab(tab4);
                viewPager.setCurrentItem(3, true);
            }
        });


        /**
         *
         * 검색어가 없을경우 추천검색어를 줘야되지만 현재 정해진 부분이 없기때문에 임시로 content 값이 null 일 경우로 분기 처리해서 null 이면 hotList를 재 호출해서 사용하는 방식으로 구현 함
         *
         * 포트 제목, 내용에서 nameSort 부분을 0 또는 1로 지정하여 소팅시킬수 있다.
         *
         *
         */
        Filter filter = new Filter(searchText, "0");
        Call<ModelSearchedPageList> getTest2 = RetrofitClient.getInstance().getService().getPageList("application/json", filter, "E", 0,1,30);
        getTest2.enqueue(new Callback<ModelSearchedPageList>() {
            @Override
            public void onResponse(Call<ModelSearchedPageList> call, Response<ModelSearchedPageList> response) {
                if (response.code() == 200) {

                    titleItemModels.clear();
                    descItemModels.clear();
                    stockItemModels.clear();
                    emptyItemModels.clear();

                    postTitleItemModels.clear();
                    postDescItemModels.clear();
                    postStockItemModels.clear();

                    searchStatus.clear();

                    int T_ItemSize;

                    if(!response.body().isNoContent()) {

                        if(response.body().getTotalElements() >= 5) {
                            T_ItemSize = 5;
                        }else{
                            T_ItemSize = response.body().getTotalElements();
                        }

                        // == 제목 데이터 == //
                        titleItemModels.add(0, new ModelTitleItem(0, response.body().getTotalElements(), "", "", 0, false, false,  0));
                        for(int tCount = 0 ; tCount < T_ItemSize ; tCount++) {
                            if(response.body().getContent().get(tCount).getSelect() != null){
                                titleItemModels.add(new ModelTitleItem(1, T_ItemSize, response.body().getContent().get(tCount).getCode()
                                        , response.body().getContent().get(tCount).getName(), DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(tCount).getRate()*100), 2, 2)
                                        , response.body().getContent().get(tCount).getSelect().isZim(),response.body().getContent().get(tCount).getSelect().isDam(), 0));
                            }else{
                                titleItemModels.add(new ModelTitleItem(1, T_ItemSize, response.body().getContent().get(tCount).getCode()
                                        , response.body().getContent().get(tCount).getName(), DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(tCount).getRate()*100), 2, 2)
                                        , false, false, 0));
                            }
                        }
                        titleItemModels.add(new ModelTitleItem(2, T_ItemSize, "", "", 0, false, false,0));
                        // ==제목 전체 데이터 == //
                        postTitleItemModels.add(0, new ModelPostTitleItem(0, response.body().getTotalElements(), "", "", 0, false, false, 0));

                        for(int ptCount = 0 ; ptCount < response.body().getTotalElements() ; ptCount++) {
                            if(response.body().getContent().get(ptCount).getSelect() != null){
                                postTitleItemModels.add(new ModelPostTitleItem(1, response.body().getTotalElements(), response.body().getContent().get(ptCount).getCode()
                                        , response.body().getContent().get(ptCount).getName(), DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(ptCount).getRate()*100), 2, 2)
                                        , response.body().getContent().get(ptCount).getSelect().isZim(), response.body().getContent().get(ptCount).getSelect().isDam(), 0));
                            }else{
                                postTitleItemModels.add(new ModelPostTitleItem(1, response.body().getTotalElements(), response.body().getContent().get(ptCount).getCode()
                                        , response.body().getContent().get(ptCount).getName(), DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(ptCount).getRate()*100), 2, 2)
                                        , false, false, 0));
                            }
                        }

                        searchStatus.add(0, 1);
                    }
                    else{
                        noContent++;
                        postTitleItemModels.add(0, new ModelPostTitleItem(201, 0, "", "", 0, false, false, 0));
                        searchStatus.add(0, 0);
                    }

                    Filter filter = new Filter(searchText, "0");
                    Call<ModelSearchedPageList> getTest2 = RetrofitClient.getInstance().getService().getPageList("application/json", filter, "E", 0,1,30);
                    getTest2.enqueue(new Callback<ModelSearchedPageList>() {
                        @Override
                        public void onResponse(Call<ModelSearchedPageList> call, Response<ModelSearchedPageList> response) {
                            if (response.code() == 200) {
                                if(!response.body().isNoContent()) {

                                    int D_ItemSize;

                                    if(response.body().getTotalElements() >= 3) {
                                        D_ItemSize = 3;
                                    }else{
                                        D_ItemSize = response.body().getTotalElements();
                                    }
                                    // == 내용 데이터 == //
                                    descItemModels.add(0, new ModelDescItem(0, response.body().getTotalElements(), "", "", "", 0, false, false, 0));
                                    for(int dCount = 0 ; dCount < D_ItemSize ; dCount++) {

                                        if(response.body().getContent().get(dCount).getSelect() != null){
                                            descItemModels.add(new ModelDescItem(1, D_ItemSize, response.body().getContent().get(dCount).getCode()
                                                    , response.body().getContent().get(dCount).getName(), response.body().getContent().get(dCount).getDescript(), DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(dCount).getRate()*100), 2, 2)
                                                    , response.body().getContent().get(dCount).getSelect().isZim(), response.body().getContent().get(dCount).getSelect().isDam(), 0));
                                        }else{
                                            descItemModels.add(new ModelDescItem(1, D_ItemSize, response.body().getContent().get(dCount).getCode()
                                                    , response.body().getContent().get(dCount).getName(), response.body().getContent().get(dCount).getDescript(), DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(dCount).getRate()*100), 2, 2)
                                                    , false, false, 0));
                                        }

                                    }
                                    descItemModels.add(new ModelDescItem(2, D_ItemSize, "", "", "", 0, false, false, 0));
                                    // ==내용 전체 데이터 ==//
                                    postDescItemModels.add(0, new ModelPostDescItem(0, response.body().getTotalElements(), "", "", "", 0, false, 0));

                                    for(int pdCount = 0 ; pdCount < response.body().getTotalElements() ; pdCount++){
                                        postDescItemModels.add(new ModelPostDescItem(1, response.body().getTotalElements(), response.body().getContent().get(pdCount).getCode()
                                                , response.body().getContent().get(pdCount).getName(), response.body().getContent().get(pdCount).getDescript()
                                                , DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(pdCount).getRate()*100), 2, 2)
                                                , false, 0));
                                    }
                                    searchStatus.add(1, 2);
                                }else{
                                    noContent++;
                                    postDescItemModels.add(0, new ModelPostDescItem(202, 0, "", "", "", 0, false, 0));
                                    searchStatus.add(1, 0);
                                }

                                Filter filter = new Filter(searchText, 0);
                                Call<ModelSearchedPageList> getTest2 = RetrofitClient.getInstance().getService().getPageList("application/json", filter, "E", 0,1,30);
                                getTest2.enqueue(new Callback<ModelSearchedPageList>() {
                                    @Override
                                    public void onResponse(Call<ModelSearchedPageList> call, Response<ModelSearchedPageList> response) {
                                        if (response.code() == 200) {
                                            int S_ItemSize;

                                            if(!response.body().isNoContent()) {
                                                if(response.body().getTotalElements() >= 3) {
                                                    S_ItemSize = 3;
                                                }else{
                                                    S_ItemSize = response.body().getTotalElements();
                                                }
                                                // == 종목 데이터 == //
                                                stockItemModels.add(0, new ModelStockItem(0, response.body().getTotalElements(), "", "", 0,0, "", false, false, 0));
                                                for(int sCount = 0 ; sCount < S_ItemSize ; sCount++) {
                                                    if(response.body().getContent().get(sCount).getSelect() != null){
                                                        stockItemModels.add(new ModelStockItem(1, S_ItemSize
                                                                , response.body().getContent().get(sCount).getCode(), response.body().getContent().get(sCount).getName()
                                                                , DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(sCount).getRate()*100), 2, 2), 0
                                                                , response.body().getContent().get(sCount).getPotEls().get(0).getElName()
                                                                , response.body().getContent().get(sCount).getSelect().isZim(), response.body().getContent().get(sCount).getSelect().isDam(), 0));
                                                    }else{
                                                        stockItemModels.add(new ModelStockItem(1, S_ItemSize
                                                                , response.body().getContent().get(sCount).getCode(), response.body().getContent().get(sCount).getName()
                                                                , DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(sCount).getRate()*100), 2, 2), 0
                                                                , response.body().getContent().get(sCount).getPotEls().get(0).getElName(), false, false, 0));
                                                    }
                                                }
                                                stockItemModels.add(new ModelStockItem(2, response.body().getTotalElements(), "", "", 0, 0, "", false, false, 0));
                                                // ==종목 전체 데이터 == //
                                                postStockItemModels.add(0, new ModelPostStockItem(0, response.body().getTotalElements(), "", "", 0,0, "", false, false, 0));
                                                for(int psCount = 0 ; psCount < response.body().getTotalElements() ; psCount++){
                                                    if(response.body().getContent().get(psCount).getSelect() != null){
                                                        postStockItemModels.add(new ModelPostStockItem(1, S_ItemSize
                                                                , response.body().getContent().get(psCount).getCode(), response.body().getContent().get(psCount).getName()
                                                                , DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(psCount).getRate()*100), 2, 2), 0
                                                                , response.body().getContent().get(psCount).getPotEls().get(0).getElName(), response.body().getContent().get(psCount).getSelect().isZim(), response.body().getContent().get(psCount).getSelect().isDam(),0));
                                                    }else{
                                                        postStockItemModels.add(new ModelPostStockItem(1, S_ItemSize
                                                                , response.body().getContent().get(psCount).getCode(), response.body().getContent().get(psCount).getName()
                                                                , DecimalScale.decimalScale(String.valueOf(response.body().getContent().get(psCount).getRate()*100), 2, 2), 0
                                                                , response.body().getContent().get(psCount).getPotEls().get(0).getElName(), false, false, 0));
                                                    }
                                                }

                                                searchStatus.add(2, 4);
                                            }else{
                                                noContent++;
                                                postStockItemModels.add(0, new ModelPostStockItem(205, 0, "", "", 0,0, "", false, false, 0));
                                                searchStatus.add(2, 0);
                                            }

                                            bundle.putParcelableArrayList("title_list", titleItemModels);
                                            bundle.putParcelableArrayList("desc_list", descItemModels);
                                            bundle.putParcelableArrayList("stock_list", stockItemModels);
                                            bundle.putParcelableArrayList("empty_list", emptyItemModels);
                                            bundle.putParcelableArrayList("post_title_list", postTitleItemModels);
                                            bundle.putParcelableArrayList("post_desc_list", postDescItemModels);
                                            bundle.putParcelableArrayList("post_stock_list", postStockItemModels);

                                            categoryState = 200 + searchStatus.get(0) + searchStatus.get(1) + searchStatus.get(2);

                                            if(noContent >= 3) {
                                                //포트 제목, 내용, 종목 모두 없을때 -> 검색 제안
                                                // == 검색결과 없을때  검색 제안 데이터 == //
                                                titleItemModels.add(new ModelTitleItem(208, 0,"","", 0, false, false, 0));

                                                emptyItemModels.add(0, new ModelEmptyItem(10, 0, "", ""));
                                                emptyItemModels.add(1, new ModelEmptyItem(12, 0, "", ""));
                                                for(int gCount = 0 ; gCount < 3 ; gCount++) {
                                                    emptyItemModels.add(new ModelEmptyItem(11, 0
                                                            , response.body().getContent().get(gCount).getCode(), response.body().getContent().get(gCount).getName()));
                                                }
                                                bundle.putParcelableArrayList("post_stock_list", postStockItemModels);
                                                categoryState = 208;
                                            }

                                            bundle.putInt("category_empty", categoryState);
                                            setViewPager(viewPager);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ModelSearchedPageList> call, Throwable t) {
                                        Log.e("레트로핏 실패","값 : "+t.getMessage());
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelSearchedPageList> call, Throwable t) {
                            Log.e("레트로핏 실패","값 : "+t.getMessage());
                        }
                    });

                }
            }
            @Override
            public void onFailure(Call<ModelSearchedPageList> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }


    private void setViewPager(ViewPager viewPager){
        FgAllTab allPageTab = new FgAllTab();
        FgTitleTab titlePageTab = new FgTitleTab();
        FgDescTab descPageTab = new FgDescTab();
        FgStockTab stockPageTab = new FgStockTab();

        allPageTab.setArguments(bundle);
        titlePageTab.setArguments(bundle);
        descPageTab.setArguments(bundle);
        stockPageTab.setArguments(bundle);

//        searchPortAdapter = new SearchPortAdapter(getChildFragmentManager());
        searchPortAdapter.addFragment(allPageTab);
        searchPortAdapter.addFragment(titlePageTab);
        searchPortAdapter.addFragment(descPageTab);
        searchPortAdapter.addFragment(stockPageTab);
        viewPager.setAdapter(searchPortAdapter);
    }

    static class SearchPortAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        public SearchPortAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment){
            mFragmentList.add(fragment);
        }
    }


    void ClickTab(TextView tab){

        preTab.setBackgroundResource(0);
        preTab.setTextColor(activitySearch.getResources().getColor(R.color.dark_gray_color));

        preTab = tab;

        tab.setBackgroundResource(R.drawable.custom_bt);
        tab.setTextColor(activitySearch.getResources().getColor(R.color.normal_title_color));

    }

    public static void getSearchText(String string) {
        searchText = string;
    }
}
