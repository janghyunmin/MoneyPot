package quantec.com.moneypot.Activity.SearchPort;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fg_BasicPage;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fg_SearchedPage;
import quantec.com.moneypot.Activity.SearchPort.SearchedPage.Fragment.AllPageTab.Fg_AllPageTab;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ActivitySearchPortBinding;

public class ActivitySearchPort extends AppCompatActivity implements TextView.OnEditorActionListener, Fg_AllPageTab.OnClickEmptyText {


    Fg_BasicPage fragment_recommendPage;
    Fg_SearchedPage fragment_searchPage1, fragment_searchPage2;

    InputMethodManager imm;
    BehaviorSubject<String> userInputSubject;

    int currentPage = 0;
    boolean debounceStae;

    ActivitySearchPortBinding searchPortBinding;

    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPortBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_port);
        searchPortBinding.setSearchPortActivity(this);

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
            fragment_recommendPage = new Fg_BasicPage();
            transaction.add(R.id.activity_port_search_page_Container, fragment_recommendPage).commitAllowingStateLoss();
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
                            fragment_searchPage1 = new Fg_SearchedPage();
                            transaction2.replace(R.id.activity_port_search_page_Container, fragment_searchPage1).commitAllowingStateLoss();
                            currentPage = 1;
                            Fg_SearchedPage.getSearchText(searchPortBinding.activityPortSearchPageEditText.getText().toString());
                        }
                    }else{
                        debounceStae = false;
                    }
                });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        searchPortBinding.activityPortSearchPageTextCancleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fg_SearchedPage.searchText = "";
                searchPortBinding.activityPortSearchPageEditText.setText("");
                searchPortBinding.activityPortSearchPageTextCancleBT.setVisibility(View.INVISIBLE);
                currentPage = 0;
                if(fragment_searchPage1 != null || fragment_searchPage2 != null)
                {
                    fragment_searchPage1 = null;
                    fragment_searchPage2 = null;
                }
            }
        });

        searchPortBinding.activityPortSearchPageBackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(searchPortBinding.activityPortSearchPageEditText.getWindowToken(), 0);
                finish();
            }
        });

        searchPortBinding.activityPortSearchPageEditText.addTextChangedListener(new TextWatcher() {
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
                    searchPortBinding.activityPortSearchPageTextCancleBT.setVisibility(View.INVISIBLE);
                    if(currentPage != 0) {
                        if(fragment_searchPage1 != null || fragment_searchPage2 != null)
                        {
                            fragment_searchPage1 = null;
                            fragment_searchPage2 = null;
                        }
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.activity_port_search_page_Container, fragment_recommendPage).commitAllowingStateLoss();
                        currentPage = 0;
                    }
                }else{
                    searchPortBinding.activityPortSearchPageTextCancleBT.setVisibility(View.VISIBLE);
                }
            }
        });

        searchPortBinding.activityPortSearchPageEditText.setOnEditorActionListener(this);

        searchPortBinding.activityPortSearchPageTextCancleBT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imm.hideSoftInputFromWindow(searchPortBinding.activityPortSearchPageEditText.getWindowToken(), 0);
                return false;
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(v.getId() == R.id.activity_port_search_page_EditText && actionId==EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId==EditorInfo.IME_ACTION_NEXT) {
            imm.hideSoftInputFromWindow(searchPortBinding.activityPortSearchPageEditText.getWindowToken(), 0);

            if(searchPortBinding.activityPortSearchPageEditText.getText().length() != 0) {
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                fragment_searchPage2 = new Fg_SearchedPage();
                Fg_SearchedPage.getSearchText(searchPortBinding.activityPortSearchPageEditText.getText().toString());

                transaction2.replace(R.id.activity_port_search_page_Container, fragment_searchPage2).commitAllowingStateLoss();
                currentPage = 1;
                debounceStae = true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imm.hideSoftInputFromWindow(searchPortBinding.activityPortSearchPageEditText.getWindowToken(), 0);
        Fg_SearchedPage.searchText = "";
    }


    //검색어 없을시 검색 제안으로 검색함
    @Override
    public void onReceivedData(String text) {
        imm.hideSoftInputFromWindow(searchPortBinding.activityPortSearchPageEditText.getWindowToken(), 0);

        searchPortBinding.activityPortSearchPageEditText.setText(text);

        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        fragment_searchPage2 = new Fg_SearchedPage();
        Fg_SearchedPage.getSearchText(text);

        transaction2.replace(R.id.activity_port_search_page_Container, fragment_searchPage2).commitAllowingStateLoss();
        currentPage = 1;
        debounceStae = true;

    }
}
