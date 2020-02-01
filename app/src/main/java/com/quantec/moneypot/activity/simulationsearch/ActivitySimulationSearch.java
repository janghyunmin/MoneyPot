package com.quantec.moneypot.activity.simulationsearch;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.ModelPreChartList;
import com.quantec.moneypot.activity.simulationsearch.fragment.FgSimulAllTab;
import com.quantec.moneypot.activity.simulationsearch.fragment.FgSimulSingleEnTab;
import com.quantec.moneypot.activity.simulationsearch.fragment.FgSimulSumEnTab;
import com.quantec.moneypot.database.room.entry.RoomEntity2;
import com.quantec.moneypot.database.room.local.RoomDao;
import com.quantec.moneypot.database.room.local.SearchRoomDatabase;
import com.quantec.moneypot.database.room.viewmodel.SearchViewModel2;
import com.quantec.moneypot.datamodel.nmodel.ModelSearchOrder;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySimulationSearch extends AppCompatActivity {

    ViewPager viewPager;
    TextView tab1, tab2, tab3, preTab, closeBt;

    Bundle bundle;
    SearchPortAdapter searchPortAdapter;

    private SearchViewModel2 searchViewModel;
    private RoomDao roomDao;
    private List<RoomEntity2> roomEntity2s;

    FloatingSearchView floatingSearchView;
    Bundle filterValue;
    List<ModelBlog> name;

    InputMethodManager imm;
    BehaviorSubject<String> userInputSubject;

    public static ArrayList<String> recommandSearchList;

    ArrayList<ModelSimulSingle> modelSimulSingles;
    ArrayList<ModelSimulSum> modelSimulSums;

    String newQueryTitle;

    ArrayList<ModelPreChartList> modelPreChartLists;
    ArrayList<String> preAddCode;

    /**
     *
     * 버튼 없애고 싶으면 FloatingSearchView xml에서 해당 색상을 다음과 같이 변경해준다 app:floatingSearch_clearBtnColor="#00000000"
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_search);

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        bundle = new Bundle();


        modelPreChartLists = new ArrayList<>();
        preAddCode = new ArrayList<>();
        preAddCode.clear();
        preAddCode.add("");

        Intent intent1 = getIntent();
        modelPreChartLists = intent1.getParcelableArrayListExtra("chartData");

        for(ModelPreChartList mChart : modelPreChartLists){
            preAddCode.add(mChart.getCode());
        }
        bundle.putStringArrayList("preAddCode", preAddCode);

        modelSimulSingles = new ArrayList<>();
        modelSimulSums = new ArrayList<>();

        recommandSearchList = new ArrayList<>();

        viewPager = findViewById(R.id.viewPager);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        closeBt = findViewById(R.id.closeBt);

        preTab = tab1;

        viewPager.setOffscreenPageLimit(3);


        searchPortAdapter = new SearchPortAdapter(getSupportFragmentManager());

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        floatingSearchView = findViewById(R.id.floating_search_view);
        floatingSearchView.setQueryTextSize(12);

        filterValue = new Bundle();

        floatingSearchView.setOnClearSearchActionListener(new FloatingSearchView.OnClearSearchActionListener() {
            @Override
            public void onClearSearchClicked() {
                Log.e("삭제함","삭제완료");
            }
        });

        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {

                if(viewPager.getCurrentItem() != 0){
                    viewPager.setCurrentItem(0, true);
                }

                newQueryTitle = newQuery;

                if(newQueryTitle.isEmpty()){
                    modelSimulSingles.clear();
                    modelSimulSums.clear();

                    modelSimulSingles.add(new ModelSimulSingle(true, 0, "", "", 0));
                    modelSimulSums.add(new ModelSimulSum(true, 0, "", "", "", 0));

                    bundle.putParcelableArrayList("singleEn", modelSimulSingles);
                    bundle.putParcelableArrayList("sumEn", modelSimulSums);

                    setViewPager(viewPager);
                }else{
                    if(RoomSearchedData(newQueryTitle)){
                        setViewPager(viewPager);
                    }
                }
            }
        });

        InputStream XmlFileInputStream = getResources().openRawResource(R.raw.blog);
        String jsonString = readTextFile(XmlFileInputStream);
        Gson gson = new Gson();
        ModelBlog[] modelBlogs =  gson.fromJson(jsonString, ModelBlog[].class);
        name = Arrays.asList(modelBlogs);

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

        searchViewModel = ViewModelProviders.of(ActivitySimulationSearch.this).get(SearchViewModel2.class);
        roomDao = SearchRoomDatabase.getINSTANCE(ActivitySimulationSearch.this).roomDao();

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
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        userInputSubject = BehaviorSubject.create();

        Call<ModelSearchOrder> getTest2 = RetrofitClient.getInstance().getService().getSearchOrder(5);
        getTest2.enqueue(new Callback<ModelSearchOrder>() {
            @Override
            public void onResponse(Call<ModelSearchOrder> call, Response<ModelSearchOrder> response) {
                if(response.code() == 200) {

                    for(int index = 0; index < response.body().getContent().size() ; index++){
                        recommandSearchList.add(response.body().getContent().get(index).getWord());
                    }

                    modelSimulSingles.add(new ModelSimulSingle(true, 0, "", "", 0));
                    modelSimulSums.add(new ModelSimulSum(true, 0, "", "", "", 0));

                    bundle.putParcelableArrayList("singleEn", modelSimulSingles);
                    bundle.putParcelableArrayList("sumEn", modelSimulSums);
                    bundle.putStringArrayList("preAddCode", preAddCode);

                    setViewPager(viewPager);
                }
            }
            @Override
            public void onFailure(Call<ModelSearchOrder> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });

        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();
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

                            case RxEvent.SIMUL_SEARCH_TITLE:

                                newQueryTitle = rxEvent.getBundle().getString("title");

                                floatingSearchView.setSearchText(newQueryTitle);

                                if(viewPager.getCurrentItem() != 0){
                                    viewPager.setCurrentItem(0, true);
                                }

                                if(newQueryTitle.isEmpty()){
                                    modelSimulSingles.clear();
                                    modelSimulSums.clear();

                                    modelSimulSingles.add(new ModelSimulSingle(true, 0, "", "", 0));
                                    modelSimulSums.add(new ModelSimulSum(true, 0, "", "", "", 0));

                                    bundle.putParcelableArrayList("singleEn", modelSimulSingles);
                                    bundle.putParcelableArrayList("sumEn", modelSimulSums);

                                    setViewPager(viewPager);
                                }else{
                                    if(RoomSearchedData(newQueryTitle)){
                                        setViewPager(viewPager);
                                    }
                                }

                                break;

                            case RxEvent.SEARCH_TRANS_PAGE:

                                if(rxEvent.getBundle().getInt("moveTab") == 0) {
                                    clickTab(tab2);
                                    viewPager.setCurrentItem(1, true);
                                }else{
                                    clickTab(tab3);
                                    viewPager.setCurrentItem(2, true);
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


    }//onCreate 끝

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        imm.hideSoftInputFromWindow(floatingSearchView.getWindowToken(), 0);
    }

    //3 Converts the input stream into a String
    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }

    //검색어 찾기
    boolean RoomSearchedData(String searchText){
        new Thread(new Runnable() {
            @Override
            public void run() {

                modelSimulSingles.clear();
                modelSimulSums.clear();

                roomEntity2s = roomDao.findStock("%"+searchText+"%","%"+searchText+"%", "%"+searchText+"%");

                if(roomEntity2s != null && roomEntity2s.size() != 0){
                    modelSimulSingles.add(new ModelSimulSingle(false, 0, "", "", 0));
                    modelSimulSums.add(new ModelSimulSum(false, 0, "", "", "", 0));
                    for(int index = 0; index < roomEntity2s.size(); index++){

                        if(roomEntity2s.get(index).getType()==0){
                            modelSimulSingles.add(new ModelSimulSingle(false, roomEntity2s.size(), roomEntity2s.get(index).getName(),
                                    roomEntity2s.get(index).getCode(), roomEntity2s.get(index).getRate()));
                        }else{
                            modelSimulSums.add(new ModelSimulSum(false, roomEntity2s.size(), roomEntity2s.get(index).getName(), roomEntity2s.get(index).getCode(),
                                    roomEntity2s.get(index).getElStock(), roomEntity2s.get(index).getRate()));
                        }
                    }
                }
                else{
                    modelSimulSingles.add(new ModelSimulSingle(true, 0, "", "", 0));
                    modelSimulSums.add(new ModelSimulSum(true, 0, "", "", "", 0));
                }
                bundle.putParcelableArrayList("singleEn", modelSimulSingles);
                bundle.putParcelableArrayList("sumEn", modelSimulSums);
            }
        }).start();
        return true;
    }

    private void setViewPager(ViewPager viewPager){
        FgSimulAllTab fgSimulAllTab = new FgSimulAllTab();
        FgSimulSingleEnTab fgSimulSingleEnTab = new FgSimulSingleEnTab();
        FgSimulSumEnTab fgSimulSumEnTab = new FgSimulSumEnTab();

        fgSimulAllTab.setArguments(bundle);
        fgSimulSingleEnTab.setArguments(bundle);
        fgSimulSumEnTab.setArguments(bundle);

        searchPortAdapter.addFragment(fgSimulAllTab);
        searchPortAdapter.addFragment(fgSimulSingleEnTab);
        searchPortAdapter.addFragment(fgSimulSumEnTab);
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
        preTab.setTextColor(getResources().getColor(R.color.dark_gray_color));
        preTab = tab;
        tab.setBackgroundResource(R.drawable.custom_bt);
        tab.setTextColor(getResources().getColor(R.color.normal_title_color));
    }

}
