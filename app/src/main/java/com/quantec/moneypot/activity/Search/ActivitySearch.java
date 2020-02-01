package com.quantec.moneypot.activity.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.quantec.moneypot.activity.Search.BasicPage.FgBasicPage;
import com.quantec.moneypot.activity.Search.SearchedPage.FgSearchedPage;
import com.quantec.moneypot.activity.Search.SearchedPage.Fragment.FgAllTab;
import com.quantec.moneypot.R;
import com.quantec.moneypot.database.room.entry.RoomEntity2;
import com.quantec.moneypot.database.room.local.RoomDao;
import com.quantec.moneypot.database.room.local.SearchRoomDatabase;
import com.quantec.moneypot.database.room.viewmodel.SearchViewModel2;
import com.quantec.moneypot.databinding.ActivitySearchBinding;
import com.quantec.moneypot.datamodel.nmodel.ModelSearchDb;
import com.quantec.moneypot.network.retrofit.RetrofitClient;

public class ActivitySearch extends AppCompatActivity implements TextView.OnEditorActionListener, FgAllTab.OnClickEmptyText{
    FgBasicPage fragment_recommendPage;
    FgSearchedPage fragment_searchPage1, fragment_searchPage2;

    InputMethodManager imm;
    BehaviorSubject<String> userInputSubject;

    int currentPage = 0;
    boolean debounceStae;

    ActivitySearchBinding binding;

    private SearchViewModel2 searchViewModel;
    private RoomDao roomDao;

    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        //        스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(fragment_recommendPage == null) {
            fragment_recommendPage = new FgBasicPage();
            transaction.add(R.id.container, fragment_recommendPage).commitAllowingStateLoss();
        }

        userInputSubject = BehaviorSubject.create();
        userInputSubject
                .debounce(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(prediction -> {
                    if(!debounceStae) {
                        if (prediction.length() != 0) {
                            FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                            fragment_searchPage1 = new FgSearchedPage();
                            transaction2.replace(R.id.container, fragment_searchPage1).commitAllowingStateLoss();
                            currentPage = 1;
                            FgSearchedPage.getSearchText(binding.searchEditText.getText().toString());
                        }
                    }else{
                        debounceStae = false;
                    }
                });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        binding.cancleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FgSearchedPage.searchText = "";
                binding.searchEditText.setText("");
                binding.cancleBt.setVisibility(View.INVISIBLE);
                currentPage = 0;
                if(fragment_searchPage1 != null || fragment_searchPage2 != null)
                {
                    fragment_searchPage1 = null;
                    fragment_searchPage2 = null;
                }
            }
        });

        binding.backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(binding.searchEditText.getWindowToken(), 0);
                finish();
            }
        });

        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                userInputSubject.onNext(s.toString());
                if(s.length() == 0){
                    binding.cancleBt.setVisibility(View.INVISIBLE);
                    if(currentPage != 0) {
                        if(fragment_searchPage1 != null || fragment_searchPage2 != null)
                        {
                            fragment_searchPage1 = null;
                            fragment_searchPage2 = null;
                        }
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.container, fragment_recommendPage).commitAllowingStateLoss();
                        currentPage = 0;
                    }
                }else{
                    binding.cancleBt.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.searchEditText.setOnEditorActionListener(this);

        binding.cancleBt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imm.hideSoftInputFromWindow(binding.searchEditText.getWindowToken(), 0);
                return false;
            }
        });


        Call<ModelSearchDb> getReList = RetrofitClient.getInstance(ActivitySearch.this).getService().getStockDb();
        getReList.enqueue(new Callback<ModelSearchDb>() {
            @Override
            public void onResponse(Call<ModelSearchDb> call, Response<ModelSearchDb> response) {
                if (response.code() == 200) {

                    searchViewModel = ViewModelProviders.of(ActivitySearch.this).get(SearchViewModel2.class);
                    roomDao = SearchRoomDatabase.getINSTANCE(ActivitySearch.this).roomDao();
                    DeleteAll();


                    for (int index = 0; index < response.body().getContent().getRateList().size(); index++) {

                        String elCodes = "";
                        String descript = "";
                        int follow = 0;

                        if(response.body().getContent().getRateList().get(index).getUserSelect() != null){
                            follow = response.body().getContent().getRateList().get(index).getUserSelect().getIsFollow();
                        }else{
                            follow = 0;
                        }

                        if(response.body().getContent().getRateList().get(index).getElCodes() != null){
                            elCodes = response.body().getContent().getRateList().get(index).getElCodes().toString();
                        }else{
                            elCodes = "";
                        }

                        if(response.body().getContent().getRateList().get(index).getSearchField() != null){
                            descript = response.body().getContent().getRateList().get(index).getSearchField().toString();
                        }else{
                            descript = "";
                        }

                        RoomDataInsert("",
                                response.body().getContent().getRateList().get(index).getType(),
                                response.body().getContent().getRateList().get(index).getCode(),
                                response.body().getContent().getRateList().get(index).getName(),
                                elCodes,
                                descript,
                                response.body().getContent().getRateList().get(index).getRate(),
                                follow);
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelSearchDb> call, Throwable t) {
                Log.e("실패","실패"+t.getMessage());
            }
        });



    }//onCreate 끝

    //최근 검색어 저장 이벤트
    void RoomDataInsert(String title, int type, String code, String name, String elStock, String descript, double rate, int follow){

        new Thread(new Runnable() {
            @Override
            public void run() {

                searchViewModel.insert2(new RoomEntity2(title, type, code, name, elStock, descript, rate, follow));

            }
        }).start();
    }



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(v.getId() == R.id.searchEditText && actionId==EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId== EditorInfo.IME_ACTION_NEXT) {
            imm.hideSoftInputFromWindow(binding.searchEditText.getWindowToken(), 0);

            if(binding.searchEditText.getText().length() != 0) {
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                fragment_searchPage2 = new FgSearchedPage();
                FgSearchedPage.getSearchText(binding.searchEditText.getText().toString());

                transaction2.replace(R.id.container, fragment_searchPage2).commitAllowingStateLoss();
                currentPage = 1;
                debounceStae = true;
            }
        }
        return false;
    }

    //딜리트 올
    void DeleteAll(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                roomDao.delete2();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imm.hideSoftInputFromWindow(binding.searchEditText.getWindowToken(), 0);
//        FgSearchedPage.searchText = "";
    }

    //검색어 없을시 검색 제안으로 검색함
    @Override
    public void onReceivedData(String text) {
        imm.hideSoftInputFromWindow(binding.searchEditText.getWindowToken(), 0);

        binding.searchEditText.setText(text);

        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        fragment_searchPage2 = new FgSearchedPage();
        FgSearchedPage.getSearchText(text);

        transaction2.replace(R.id.container, fragment_searchPage2).commitAllowingStateLoss();
        currentPage = 1;
        debounceStae = true;
    }

}
