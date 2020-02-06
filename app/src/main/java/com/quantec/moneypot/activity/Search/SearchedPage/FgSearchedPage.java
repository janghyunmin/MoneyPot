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
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.quantec.moneypot.activity.Search.ActivitySearch;
import com.quantec.moneypot.activity.Search.SearchedPage.Fragment.FgAllTab;
import com.quantec.moneypot.activity.Search.SearchedPage.Fragment.FgSingleEnTab;
import com.quantec.moneypot.activity.Search.SearchedPage.Fragment.FgSumEnTab;
import com.quantec.moneypot.activity.Search.SearchedPage.Fragment.FgNewsTab;
import com.quantec.moneypot.database.room.entry.RoomEntity2;
import com.quantec.moneypot.database.room.local.RoomDao;
import com.quantec.moneypot.database.room.local.SearchRoomDatabase;
import com.quantec.moneypot.database.room.viewmodel.SearchViewModel2;
import com.quantec.moneypot.R;
import com.quantec.moneypot.datamodel.nmodel.ModelArticle;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

import static com.quantec.moneypot.util.replacetag.ReplaceTag.ReplaceTag;

public class FgSearchedPage extends Fragment {

    ViewPager viewPager;

    TextView tab1, tab2, tab3, tab4, preTab;

    ActivitySearch activitySearch;
    //Fragment_SearchPage 에서 넘겨 받은 검색 단어
    public static String searchText;

    Bundle bundle;
    ArrayList<ModelSingleEn> modelSingleEns;
    ArrayList<ModelSumEn> modelSumEn;
    ArrayList<ModelNewsEn> modelNewsEns;

    SearchPortAdapter searchPortAdapter;

    private SearchViewModel2 searchViewModel;
    private RoomDao roomDao;
    private List<RoomEntity2> roomEntity2s;

    public FgSearchedPage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_searchedpage, container, false);

        initializeViews();

        modelSingleEns = new ArrayList<>();
        modelSumEn = new ArrayList<>();
        modelNewsEns = new ArrayList<>();

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

        searchViewModel = ViewModelProviders.of(activitySearch).get(SearchViewModel2.class);
        roomDao = SearchRoomDatabase.getINSTANCE(activitySearch).roomDao();

        if(RoomDataInsert2(searchText)){

            Call<ModelArticle> tt = RetrofitClient.getInstance(activitySearch).getService().getNewsSearch(0, 20, searchText);
            tt.enqueue(new Callback<ModelArticle>() {
                @Override
                public void onResponse(Call<ModelArticle> call, Response<ModelArticle> response) {
                    if(response.code() == 200) {
                        modelNewsEns.clear();

                        if(response.body().getTotalElements() == 0){
                                modelNewsEns.add(new ModelNewsEn(true, 0, "", "", ""));
                        }else{

                            modelNewsEns.add(new ModelNewsEn(false,
                                    response.body().getContent().getTotal(),
                                    "",
                                    "",
                                    ""));

                            for(int index = 0; index < response.body().getContent().getNewsData().size(); index++){
                                modelNewsEns.add(new ModelNewsEn(false,
                                        response.body().getContent().getTotal(),
                                        ReplaceTag(response.body().getContent().getNewsData().get(index).getTitle(), "decode"),
                                        response.body().getContent().getNewsData().get(index).getDate(),
                                        response.body().getContent().getNewsData().get(index).getNewsUrl()));
                            }
                        }
                        setViewPager(viewPager);
                    }
                }
                @Override
                public void onFailure(Call<ModelArticle> call, Throwable t) {
                }
            });
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        clickTab(tab1);
                        break;
                    case 1:
                        clickTab(tab2);
                        break;
                    case 2:
                        clickTab(tab3);
                        break;
                    case 3:
                        clickTab(tab4);
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

                                if(rxEvent.getBundle().getInt("moveTab") == 0) {
                                    clickTab(tab2);
                                    viewPager.setCurrentItem(1, true);
                                }else if(rxEvent.getBundle().getInt("moveTab") == 1) {
                                    clickTab(tab3);
                                    viewPager.setCurrentItem(2, true);
                                }else {
                                    clickTab(tab4);
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
                clickTab(tab1);
                viewPager.setCurrentItem(0, true);
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTab(tab2);
                viewPager.setCurrentItem(1, true);
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTab(tab3);
                viewPager.setCurrentItem(2, true);
            }
        });
        tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTab(tab4);
                viewPager.setCurrentItem(3, true);
            }
        });

    }//onViewCreated 끝

    boolean RoomDataInsert2(String searchText){
        new Thread(new Runnable() {
            @Override
            public void run() {

                modelSingleEns.clear();
                modelSumEn.clear();
//                modelNewsEns.clear();

                modelSingleEns.add(new ModelSingleEn(true, "", "", 0, 0));
                modelSumEn.add(new ModelSumEn(true, "", "", "", 0, 0));

                roomEntity2s = roomDao.findStock("%"+searchText+"%","%"+searchText+"%", "%"+searchText+"%");

                if(roomEntity2s != null && roomEntity2s.size() != 0){
                    for(int index = 0; index < roomEntity2s.size(); index++){

                        if(roomEntity2s.get(index).getType()==0){
                            modelSingleEns.add(new ModelSingleEn(true, roomEntity2s.get(index).getName(),
                                    roomEntity2s.get(index).getCode(), roomEntity2s.get(index).getRate(), roomEntity2s.get(index).getFollow()));
                        }else{
                            modelSumEn.add(new ModelSumEn(true, roomEntity2s.get(index).getName(), roomEntity2s.get(index).getCode(),
                                    roomEntity2s.get(index).getElStock(), roomEntity2s.get(index).getRate(), roomEntity2s.get(index).getFollow()));
                        }
                    }
                }
                else{
                    Log.e("찾은 데어터", "값 : 룸에 데이터가 없습니다.");
                }
                bundle.putParcelableArrayList("singleEn", modelSingleEns);
                bundle.putParcelableArrayList("sumEn", modelSumEn);
                bundle.putParcelableArrayList("newsEn", modelNewsEns);
            }
        }).start();
        return true;
    }

    private void setViewPager(ViewPager viewPager){
        FgAllTab allPageTab = new FgAllTab();
        FgSingleEnTab titlePageTab = new FgSingleEnTab();
        FgSumEnTab descPageTab = new FgSumEnTab();
        FgNewsTab stockPageTab = new FgNewsTab();

        allPageTab.setArguments(bundle);
        titlePageTab.setArguments(bundle);
        descPageTab.setArguments(bundle);
        stockPageTab.setArguments(bundle);

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

    void clickTab(TextView tab){

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
