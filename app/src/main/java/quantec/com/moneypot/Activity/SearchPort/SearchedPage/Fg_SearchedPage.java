package quantec.com.moneypot.Activity.SearchPort.SearchedPage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.Cookpage1.Filter;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Model.nModel.ModelTab13mRank;
import quantec.com.moneypot.Activity.SearchPort.ActivitySearchPort;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Fg_AllPageTab;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.DescPageTab.Fg_DescPageTab;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.StockPageTab.Fg_StockPageTab;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.TitlePageTab.Fg_TitlePageTab;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelDescItem;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelEmptyItem;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelPostDescItem;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelPostStockItem;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelPostTitleItem;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelStockItem;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.dModel.ModelTitleItem;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Model.nModel.ModelSearchPage;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fg_SearchedPage extends Fragment {

    ViewPager port_search_page_searchPort_viewpager;

    TextView port_search_page_searchPort_tab1, port_search_page_searchPort_tab2,
            port_search_page_searchPort_tab3, port_search_page_searchPort_tab4;

    ActivitySearchPort portSearchPageActivity;

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

    public Fg_SearchedPage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_activitysearch_searchedpage, container, false);


        initializeViews();

        titleItemModels = new ArrayList<>();
        descItemModels = new ArrayList<>();
        stockItemModels = new ArrayList<>();
        emptyItemModels = new ArrayList<>();

        postTitleItemModels = new ArrayList<>();
        postDescItemModels = new ArrayList<>();
        postStockItemModels = new ArrayList<>();


        port_search_page_searchPort_viewpager = view.findViewById(R.id.port_search_page_searchPort_viewpager);

        port_search_page_searchPort_tab1 = view.findViewById(R.id.port_search_page_searchPort_tab1);
        port_search_page_searchPort_tab2 = view.findViewById(R.id.port_search_page_searchPort_tab2);
        port_search_page_searchPort_tab3 = view.findViewById(R.id.port_search_page_searchPort_tab3);
        port_search_page_searchPort_tab4 = view.findViewById(R.id.port_search_page_searchPort_tab4);

        port_search_page_searchPort_viewpager.setOffscreenPageLimit(4);
        bundle = new Bundle();

        return view;
    }


    private void initializeViews(){
        portSearchPageActivity = (ActivitySearchPort) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivitySearchPort) {
            portSearchPageActivity = (ActivitySearchPort) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        port_search_page_searchPort_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        Tab1Event();
                        break;
                    case 1:
                        Tab2Event();
                        break;
                    case 2:
                        Tab3Event();
                        break;
                    case 3:
                        Tab4Event();
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
                                    Tab2Event();
                                    port_search_page_searchPort_viewpager.setCurrentItem(1, true);
                                }else if(rxEvent.getBundle().getInt("allPage_MovedTab") == 1) {
                                    Tab3Event();
                                    port_search_page_searchPort_viewpager.setCurrentItem(2, true);
                                }else {
                                    Tab4Event();
                                    port_search_page_searchPort_viewpager.setCurrentItem(3, true);
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

        port_search_page_searchPort_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tab1Event();
                port_search_page_searchPort_viewpager.setCurrentItem(0, true);
            }
        });

        port_search_page_searchPort_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tab2Event();
                port_search_page_searchPort_viewpager.setCurrentItem(1, true);
            }
        });

        port_search_page_searchPort_tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tab3Event();
                port_search_page_searchPort_viewpager.setCurrentItem(2, true);
            }
        });

        port_search_page_searchPort_tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tab4Event();
                port_search_page_searchPort_viewpager.setCurrentItem(3, true);
            }
        });


        /**
         *
         * 검색어가 없을경우 추천검색어를 줘야되지만 현재 정해진 부분이 없기때문에 임시로 content 값이 null 일 경우로 분기 처리해서 null 이면 hotList를 재 호출해서 사용하는 방식으로 구현 함
         *
         */
        Filter filter = new Filter(searchText, "", "");
        Call<ModelTab13mRank> getTest2 = RetrofitClient.getInstance().getService().getPageList("application/json", filter, "E", 0,1,30);
        getTest2.enqueue(new Callback<ModelTab13mRank>() {
            @Override
            public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                if (response.code() == 200) {

                    if(response.body().getTotalElements() != 0) {
                        Log.e("이름 검색 결과", "값 : "+response.body().getContent().get(0).getName());

                    }else{
                        Log.e("이름 검색 결과", "값 : 없음");
//                        Filter filter = new Filter();
//                        Call<ModelTab13mRank> getTest2 = RetrofitClient.getInstance().getService().getPageList("application/json", filter, "H", 0,1,30);
//                        getTest2.enqueue(new Callback<ModelTab13mRank>() {
//                            @Override
//                            public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
//                                if (response.code() == 200) {
//                                        Log.e("받음값", "값 : "+response.body().getContent().get(0).getName());
//                                }
//                            }
//                            @Override
//                            public void onFailure(Call<ModelTab13mRank> call, Throwable t) {
//                                Log.e("레트로핏 실패","값 : "+t.getMessage());
//                            }
//                        });
                    }

                    Filter filter = new Filter("", searchText, "");
                    Call<ModelTab13mRank> getTest2 = RetrofitClient.getInstance().getService().getPageList("application/json", filter, "E", 0,1,30);
                    getTest2.enqueue(new Callback<ModelTab13mRank>() {
                        @Override
                        public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                            if (response.code() == 200) {
                                if(response.body().getTotalElements() != 0) {
                                    Log.e("설명 검색 결과", "값 : "+response.body().getContent().get(0).getName());
                                }else{
                                    Log.e("설명 검색 결과", "값 : 없음");
                                }

                                Filter filter = new Filter("", "", searchText);
                                Call<ModelTab13mRank> getTest2 = RetrofitClient.getInstance().getService().getPageList("application/json", filter, "E", 0,1,30);
                                getTest2.enqueue(new Callback<ModelTab13mRank>() {
                                    @Override
                                    public void onResponse(Call<ModelTab13mRank> call, Response<ModelTab13mRank> response) {
                                        if (response.code() == 200) {
                                            if(response.body().getTotalElements() != 0) {
                                                Log.e("종목 검색 결과", "값 : "+response.body().getContent().get(0).getName());
                                            }else{
                                                Log.e("종목 검색 결과", "값 : 없음");
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<ModelTab13mRank> call, Throwable t) {
                                        Log.e("레트로핏 실패","값 : "+t.getMessage());
                                    }
                                });
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelTab13mRank> call, Throwable t) {
                            Log.e("레트로핏 실패","값 : "+t.getMessage());
                        }
                    });


//                    titleItemModels.clear();
//                    descItemModels.clear();
//                    stockItemModels.clear();
//                    emptyItemModels.clear();
//
//                    postTitleItemModels.clear();
//                    postDescItemModels.clear();
//                    postStockItemModels.clear();
//
//                    int T_ItemSize, D_ItemSize, S_ItemSize;
//                    if(response.body().getTitle().get(0).getTotalNum() >= 5) {
//                        T_ItemSize = 5;
//                    }else{
//                        T_ItemSize = response.body().getTitle().get(0).getTotalNum();
//                    }
//
//                    if(response.body().getDescript().get(0).getTotalNum() >= 3) {
//                        D_ItemSize = 3;
//                    }else{
//                        D_ItemSize = response.body().getDescript().get(0).getTotalNum();
//                    }
//
//                    if(response.body().getStock().get(0).getTotalNum() >= 3) {
//                        S_ItemSize = 3;
//                    }else{
//                        S_ItemSize = response.body().getStock().get(0).getTotalNum();
//                    }
//                    // == 제목 데이터 == //
//                    titleItemModels.add(0, new ModelTitleItem(response.body().getRes_code(),response.body().getTitle().get(0).getTotalNum(), 0, "0", 0, 0, 0));
//
//                    for(int tCount = 0 ; tCount < T_ItemSize ; tCount++) {
//                        titleItemModels.add(new ModelTitleItem(response.body().getRes_code(), response.body().getTitle().get(0).getTotalNum(), response.body().getTitle().get(0).getData().get(tCount).getCode()
//                                , response.body().getTitle().get(0).getData().get(tCount).getName(), response.body().getTitle().get(0).getData().get(tCount).getRate()
//                                , response.body().getTitle().get(0).getData().get(tCount).getSelect(), response.body().getTitle().get(0).getData().get(tCount).getType()));
//                        if(tCount == T_ItemSize-1) {
//                            titleItemModels.add(new ModelTitleItem(response.body().getRes_code(), response.body().getTitle().get(0).getTotalNum(), 0, "0", 0, 0, 0));
//                        }
//                    }
//
//                    // ==제목 전체 데이터 == //
//                    postTitleItemModels.add(0, new ModelPostTitleItem(response.body().getRes_code(), response.body().getTitle().get(0).getTotalNum(), 0, "0", 0, 0, 0));
//
//                    for(int ptCount = 0 ; ptCount < response.body().getTitle().get(0).getTotalNum() ; ptCount++) {
//                        postTitleItemModels.add(new ModelPostTitleItem(response.body().getRes_code(), response.body().getTitle().get(0).getTotalNum(), response.body().getTitle().get(0).getData().get(ptCount).getCode()
//                                , response.body().getTitle().get(0).getData().get(ptCount).getName(), response.body().getTitle().get(0).getData().get(ptCount).getRate()
//                                , response.body().getTitle().get(0).getData().get(ptCount).getSelect(), response.body().getTitle().get(0).getData().get(ptCount).getType()));
//                    }
//
//
//                    // == 내용 데이터 == //
//                    descItemModels.add(0, new ModelDescItem(response.body().getRes_code(), response.body().getDescript().get(0).getTotalNum(), 0, "0", "0", 0, 0, 0));
//                    for(int dCount = 0 ; dCount < D_ItemSize ; dCount++) {
//
//                        descItemModels.add(new ModelDescItem(response.body().getRes_code(), response.body().getDescript().get(0).getTotalNum(), response.body().getDescript().get(0).getData().get(dCount).getCode()
//                                , response.body().getDescript().get(0).getData().get(dCount).getName(), response.body().getDescript().get(0).getData().get(dCount).getDesc()
//                                , response.body().getDescript().get(0).getData().get(dCount).getRate(), response.body().getDescript().get(0).getData().get(dCount).getSelect()
//                                , response.body().getDescript().get(0).getData().get(dCount).getType()));
//
//                        if(dCount == D_ItemSize-1) {
//                            descItemModels.add(new ModelDescItem(response.body().getRes_code(), response.body().getDescript().get(0).getTotalNum(), 0, "0", "0", 0, 0, 0));
//                        }
//                    }
//
//
//                    // ==내용 전체 데이터 ==//
//                    postDescItemModels.add(0, new ModelPostDescItem(response.body().getRes_code(), response.body().getDescript().get(0).getTotalNum(), 0, "0", "0", 0, 0, 0));
//
//                    for(int pdCount = 0 ; pdCount < response.body().getDescript().get(0).getTotalNum() ; pdCount++){
//                        postDescItemModels.add(new ModelPostDescItem(response.body().getRes_code(), response.body().getDescript().get(0).getTotalNum(), response.body().getDescript().get(0).getData().get(pdCount).getCode()
//                                , response.body().getDescript().get(0).getData().get(pdCount).getName(), response.body().getDescript().get(0).getData().get(pdCount).getDesc()
//                                , response.body().getDescript().get(0).getData().get(pdCount).getRate(), response.body().getDescript().get(0).getData().get(pdCount).getSelect()
//                                , response.body().getDescript().get(0).getData().get(pdCount).getType()));
//                    }
//
//
//                    // == 종목 데이터 == //
//                    stockItemModels.add(0, new ModelStockItem(response.body().getRes_code(), response.body().getStock().get(0).getTotalNum(), 0, "0", 0,0, "0", 0, 0));
//                    for(int sCount = 0 ; sCount < S_ItemSize ; sCount++) {
//                        stockItemModels.add(new ModelStockItem(response.body().getRes_code(), response.body().getStock().get(0).getTotalNum()
//                                , response.body().getStock().get(0).getData().get(sCount).getCode(), response.body().getStock().get(0).getData().get(sCount).getName()
//                                , response.body().getStock().get(0).getData().get(sCount).getRate(), response.body().getStock().get(0).getData().get(sCount).getsNum()
//                                , response.body().getStock().get(0).getData().get(sCount).getElements().get(0).getSname(), response.body().getStock().get(0).getData().get(sCount).getSelect()
//                                , response.body().getStock().get(0).getData().get(sCount).getType()));
//
//                        if(sCount == S_ItemSize-1) {
//                            stockItemModels.add(new ModelStockItem(response.body().getRes_code(), response.body().getStock().get(0).getTotalNum(), 0, "0", 0, 0, "0", 0, 0));
//                        }
//                    }
//
//                    // ==종목 전체 데이터 == //
//                    postStockItemModels.add(0, new ModelPostStockItem(response.body().getRes_code(), response.body().getStock().get(0).getTotalNum(), 0, "0", 0, 0, "0", 0, 0));
//                    for(int psCount = 0 ; psCount < response.body().getStock().get(0).getTotalNum() ; psCount++){
//                        postStockItemModels.add(new ModelPostStockItem(response.body().getRes_code(), response.body().getStock().get(0).getTotalNum(), response.body().getStock().get(0).getData().get(psCount).getCode()
//                                , response.body().getStock().get(0).getData().get(psCount).getName(), response.body().getStock().get(0).getData().get(psCount).getRate()
//                                , response.body().getStock().get(0).getData().get(psCount).getsNum(), response.body().getStock().get(0).getData().get(psCount).getElements().get(0).getSname()
//                                , response.body().getStock().get(0).getData().get(psCount).getSelect(), response.body().getStock().get(0).getData().get(psCount).getType()));
//                    }
//
//                    //포트 제목, 내용, 종목 모두 없을때 -> 검색 제안
//                    if(response.body().getRes_code() == 208) {
//
//                        // == 검색결과 없을때  검색 제안 데이터 == //
//                        titleItemModels.add(new ModelTitleItem(response.body().getRes_code(), 0,0,"0", 0, 0, 0));
//
//                        emptyItemModels.add(0, new ModelEmptyItem(response.body().getRes_code(), 0, 0, "0"));
//                        emptyItemModels.add(1, new ModelEmptyItem(response.body().getRes_code(), 0, 0, "0"));
//                        for(int gCount = 0 ; gCount < response.body().getSuggest().get(0).getTotalNum() ; gCount++) {
//
//                            emptyItemModels.add(new ModelEmptyItem(response.body().getRes_code(), response.body().getSuggest().get(0).getTotalNum()
//                                    , response.body().getSuggest().get(0).getData().get(gCount).getCode(), response.body().getSuggest().get(0).getData().get(gCount).getName()));
//                        }
//
//                    }
//
//                    bundle.putParcelableArrayList("title_list", titleItemModels);
//                    bundle.putParcelableArrayList("desc_list", descItemModels);
//                    bundle.putParcelableArrayList("stock_list", stockItemModels);
//                    bundle.putParcelableArrayList("empty_list", emptyItemModels);
//                    bundle.putParcelableArrayList("post_title_list", postTitleItemModels);
//                    bundle.putParcelableArrayList("post_desc_list", postDescItemModels);
//                    bundle.putParcelableArrayList("post_stock_list", postStockItemModels);
//                    setViewPager(port_search_page_searchPort_viewpager);
                }
            }
            @Override
            public void onFailure(Call<ModelTab13mRank> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });

//        //검색 시 최초는 무조건 페이지 1로 고정
//        Call<ModelSearchPage> getData = RetrofitClient.getInstance().getService().getSearch(searchText, 1);
//        getData.enqueue(new Callback<ModelSearchPage>() {
//            @Override
//            public void onResponse(Call<ModelSearchPage> call, Response<ModelSearchPage> response) {
//                //검색 결과 있을때
//                if (response.code() == 200) {
//
//                    titleItemModels.clear();
//                    descItemModels.clear();
//                    stockItemModels.clear();
//                    emptyItemModels.clear();
//
//                    postTitleItemModels.clear();
//                    postDescItemModels.clear();
//                    postStockItemModels.clear();
//
//                    int T_ItemSize, D_ItemSize, S_ItemSize;
//                    if(response.body().getTitle().get(0).getTotalNum() >= 5) {
//                        T_ItemSize = 5;
//                    }else{
//                        T_ItemSize = response.body().getTitle().get(0).getTotalNum();
//                    }
//
//                    if(response.body().getDescript().get(0).getTotalNum() >= 3) {
//                        D_ItemSize = 3;
//                    }else{
//                        D_ItemSize = response.body().getDescript().get(0).getTotalNum();
//                    }
//
//                    if(response.body().getStock().get(0).getTotalNum() >= 3) {
//                        S_ItemSize = 3;
//                    }else{
//                        S_ItemSize = response.body().getStock().get(0).getTotalNum();
//                    }
//                    // == 제목 데이터 == //
//                    titleItemModels.add(0, new ModelTitleItem(response.body().getRes_code(),response.body().getTitle().get(0).getTotalNum(), 0, "0", 0, 0, 0));
//
//                    for(int tCount = 0 ; tCount < T_ItemSize ; tCount++) {
//                        titleItemModels.add(new ModelTitleItem(response.body().getRes_code(), response.body().getTitle().get(0).getTotalNum(), response.body().getTitle().get(0).getData().get(tCount).getCode()
//                                , response.body().getTitle().get(0).getData().get(tCount).getName(), response.body().getTitle().get(0).getData().get(tCount).getRate()
//                                , response.body().getTitle().get(0).getData().get(tCount).getSelect(), response.body().getTitle().get(0).getData().get(tCount).getType()));
//                        if(tCount == T_ItemSize-1) {
//                            titleItemModels.add(new ModelTitleItem(response.body().getRes_code(), response.body().getTitle().get(0).getTotalNum(), 0, "0", 0, 0, 0));
//                        }
//                    }
//
//                    // ==제목 전체 데이터 == //
//                    postTitleItemModels.add(0, new ModelPostTitleItem(response.body().getRes_code(), response.body().getTitle().get(0).getTotalNum(), 0, "0", 0, 0, 0));
//
//                    for(int ptCount = 0 ; ptCount < response.body().getTitle().get(0).getTotalNum() ; ptCount++) {
//                        postTitleItemModels.add(new ModelPostTitleItem(response.body().getRes_code(), response.body().getTitle().get(0).getTotalNum(), response.body().getTitle().get(0).getData().get(ptCount).getCode()
//                                , response.body().getTitle().get(0).getData().get(ptCount).getName(), response.body().getTitle().get(0).getData().get(ptCount).getRate()
//                                , response.body().getTitle().get(0).getData().get(ptCount).getSelect(), response.body().getTitle().get(0).getData().get(ptCount).getType()));
//                    }
//
//
//                    // == 내용 데이터 == //
//                    descItemModels.add(0, new ModelDescItem(response.body().getRes_code(), response.body().getDescript().get(0).getTotalNum(), 0, "0", "0", 0, 0, 0));
//                    for(int dCount = 0 ; dCount < D_ItemSize ; dCount++) {
//
//                        descItemModels.add(new ModelDescItem(response.body().getRes_code(), response.body().getDescript().get(0).getTotalNum(), response.body().getDescript().get(0).getData().get(dCount).getCode()
//                                , response.body().getDescript().get(0).getData().get(dCount).getName(), response.body().getDescript().get(0).getData().get(dCount).getDesc()
//                                , response.body().getDescript().get(0).getData().get(dCount).getRate(), response.body().getDescript().get(0).getData().get(dCount).getSelect()
//                                , response.body().getDescript().get(0).getData().get(dCount).getType()));
//
//                        if(dCount == D_ItemSize-1) {
//                            descItemModels.add(new ModelDescItem(response.body().getRes_code(), response.body().getDescript().get(0).getTotalNum(), 0, "0", "0", 0, 0, 0));
//                        }
//                    }
//
//
//                    // ==내용 전체 데이터 ==//
//                    postDescItemModels.add(0, new ModelPostDescItem(response.body().getRes_code(), response.body().getDescript().get(0).getTotalNum(), 0, "0", "0", 0, 0, 0));
//
//                    for(int pdCount = 0 ; pdCount < response.body().getDescript().get(0).getTotalNum() ; pdCount++){
//                        postDescItemModels.add(new ModelPostDescItem(response.body().getRes_code(), response.body().getDescript().get(0).getTotalNum(), response.body().getDescript().get(0).getData().get(pdCount).getCode()
//                                , response.body().getDescript().get(0).getData().get(pdCount).getName(), response.body().getDescript().get(0).getData().get(pdCount).getDesc()
//                                , response.body().getDescript().get(0).getData().get(pdCount).getRate(), response.body().getDescript().get(0).getData().get(pdCount).getSelect()
//                                , response.body().getDescript().get(0).getData().get(pdCount).getType()));
//                    }
//
//
//                    // == 종목 데이터 == //
//                    stockItemModels.add(0, new ModelStockItem(response.body().getRes_code(), response.body().getStock().get(0).getTotalNum(), 0, "0", 0,0, "0", 0, 0));
//                    for(int sCount = 0 ; sCount < S_ItemSize ; sCount++) {
//                        stockItemModels.add(new ModelStockItem(response.body().getRes_code(), response.body().getStock().get(0).getTotalNum()
//                                , response.body().getStock().get(0).getData().get(sCount).getCode(), response.body().getStock().get(0).getData().get(sCount).getName()
//                                , response.body().getStock().get(0).getData().get(sCount).getRate(), response.body().getStock().get(0).getData().get(sCount).getsNum()
//                                , response.body().getStock().get(0).getData().get(sCount).getElements().get(0).getSname(), response.body().getStock().get(0).getData().get(sCount).getSelect()
//                                , response.body().getStock().get(0).getData().get(sCount).getType()));
//
//                        if(sCount == S_ItemSize-1) {
//                            stockItemModels.add(new ModelStockItem(response.body().getRes_code(), response.body().getStock().get(0).getTotalNum(), 0, "0", 0, 0, "0", 0, 0));
//                        }
//                    }
//
//                    // ==종목 전체 데이터 == //
//                    postStockItemModels.add(0, new ModelPostStockItem(response.body().getRes_code(), response.body().getStock().get(0).getTotalNum(), 0, "0", 0, 0, "0", 0, 0));
//                    for(int psCount = 0 ; psCount < response.body().getStock().get(0).getTotalNum() ; psCount++){
//                        postStockItemModels.add(new ModelPostStockItem(response.body().getRes_code(), response.body().getStock().get(0).getTotalNum(), response.body().getStock().get(0).getData().get(psCount).getCode()
//                                , response.body().getStock().get(0).getData().get(psCount).getName(), response.body().getStock().get(0).getData().get(psCount).getRate()
//                                , response.body().getStock().get(0).getData().get(psCount).getsNum(), response.body().getStock().get(0).getData().get(psCount).getElements().get(0).getSname()
//                                , response.body().getStock().get(0).getData().get(psCount).getSelect(), response.body().getStock().get(0).getData().get(psCount).getType()));
//                    }
//
//                    //포트 제목, 내용, 종목 모두 없을때 -> 검색 제안
//                    if(response.body().getRes_code() == 208) {
//
//                        // == 검색결과 없을때  검색 제안 데이터 == //
//                        titleItemModels.add(new ModelTitleItem(response.body().getRes_code(), 0,0,"0", 0, 0, 0));
//
//                        emptyItemModels.add(0, new ModelEmptyItem(response.body().getRes_code(), 0, 0, "0"));
//                        emptyItemModels.add(1, new ModelEmptyItem(response.body().getRes_code(), 0, 0, "0"));
//                        for(int gCount = 0 ; gCount < response.body().getSuggest().get(0).getTotalNum() ; gCount++) {
//
//                            emptyItemModels.add(new ModelEmptyItem(response.body().getRes_code(), response.body().getSuggest().get(0).getTotalNum()
//                                    , response.body().getSuggest().get(0).getData().get(gCount).getCode(), response.body().getSuggest().get(0).getData().get(gCount).getName()));
//                        }
//
//                    }
//
//                    bundle.putParcelableArrayList("title_list", titleItemModels);
//                    bundle.putParcelableArrayList("desc_list", descItemModels);
//                    bundle.putParcelableArrayList("stock_list", stockItemModels);
//                    bundle.putParcelableArrayList("empty_list", emptyItemModels);
//                    bundle.putParcelableArrayList("post_title_list", postTitleItemModels);
//                    bundle.putParcelableArrayList("post_desc_list", postDescItemModels);
//                    bundle.putParcelableArrayList("post_stock_list", postStockItemModels);
//                    setViewPager(port_search_page_searchPort_viewpager);
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelSearchPage> call, Throwable t) {
//                Log.e("레트로핏 실패", "값 : " + t.getMessage());
//            }
//        });

    }


    private void setViewPager(ViewPager viewPager){
        Fg_AllPageTab allPageTab = new Fg_AllPageTab();
        Fg_TitlePageTab titlePageTab = new Fg_TitlePageTab();
        Fg_DescPageTab descPageTab = new Fg_DescPageTab();
        Fg_StockPageTab stockPageTab = new Fg_StockPageTab();

        allPageTab.setArguments(bundle);
        titlePageTab.setArguments(bundle);
        descPageTab.setArguments(bundle);
        stockPageTab.setArguments(bundle);

        SearchPortAdapter searchPortAdapter = new SearchPortAdapter(getChildFragmentManager());
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


    void Tab1Event(){
        port_search_page_searchPort_tab1.setBackgroundResource(R.drawable.bt_red_search_page);
        port_search_page_searchPort_tab2.setBackgroundResource(0);
        port_search_page_searchPort_tab3.setBackgroundResource(0);
        port_search_page_searchPort_tab4.setBackgroundResource(0);

        port_search_page_searchPort_tab1.setTextColor(portSearchPageActivity.getResources().getColor(R.color.normal_title_color));
        port_search_page_searchPort_tab2.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
        port_search_page_searchPort_tab3.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
        port_search_page_searchPort_tab4.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
    }
    void Tab2Event() {
        port_search_page_searchPort_tab1.setBackgroundResource(0);
        port_search_page_searchPort_tab2.setBackgroundResource(R.drawable.bt_red_search_page);
        port_search_page_searchPort_tab3.setBackgroundResource(0);
        port_search_page_searchPort_tab4.setBackgroundResource(0);

        port_search_page_searchPort_tab1.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
        port_search_page_searchPort_tab2.setTextColor(portSearchPageActivity.getResources().getColor(R.color.normal_title_color));
        port_search_page_searchPort_tab3.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
        port_search_page_searchPort_tab4.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
    }
    void Tab3Event(){
        port_search_page_searchPort_tab1.setBackgroundResource(0);
        port_search_page_searchPort_tab2.setBackgroundResource(0);
        port_search_page_searchPort_tab3.setBackgroundResource(R.drawable.bt_red_search_page);
        port_search_page_searchPort_tab4.setBackgroundResource(0);

        port_search_page_searchPort_tab1.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
        port_search_page_searchPort_tab2.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
        port_search_page_searchPort_tab3.setTextColor(portSearchPageActivity.getResources().getColor(R.color.normal_title_color));
        port_search_page_searchPort_tab4.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
    }
    void Tab4Event(){
        port_search_page_searchPort_tab1.setBackgroundResource(0);
        port_search_page_searchPort_tab2.setBackgroundResource(0);
        port_search_page_searchPort_tab3.setBackgroundResource(0);
        port_search_page_searchPort_tab4.setBackgroundResource(R.drawable.bt_red_search_page);

        port_search_page_searchPort_tab1.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
        port_search_page_searchPort_tab2.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
        port_search_page_searchPort_tab3.setTextColor(portSearchPageActivity.getResources().getColor(R.color.dark_gray_color));
        port_search_page_searchPort_tab4.setTextColor(portSearchPageActivity.getResources().getColor(R.color.normal_title_color));
    }


    public static void getSearchText(String string) {
        searchText = string;
    }

}
