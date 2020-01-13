package com.quantec.moneypot.activity.center;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.quantec.moneypot.R;

import java.util.ArrayList;


public class ActivityQuestion extends AppCompatActivity {

    private EditText searchEditText;
    ImageView backBt, banner;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ModelQuestion> modelQuestions;
    AdapterQuestion adapterQuestion;

    RecyclerView recyclerView2;
    RecyclerView.LayoutManager layoutManager2;
    AdapterSearchedQuestion adapterSearchedQuestion;

    ArrayList<ModelQuestion> allList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


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

        banner = findViewById(R.id.banner);
        backBt = findViewById(R.id.backBt);
        searchEditText = findViewById(R.id.searchEditText);

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        modelQuestions = new ArrayList<>();
        allList = new ArrayList<>();

        adapterQuestion = new AdapterQuestion(allList, this);
        recyclerView.setAdapter(adapterQuestion);


        modelQuestions.add(new ModelQuestion(false, true, "투자 전 준비사항", "", "", ""));
        modelQuestions.add(new ModelQuestion(false,false, "", "해외주식 투자 순서는 어떻게 되나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,false,"", "CMA 계좌는 어떻게 개설하나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,false,"", "투자할 금액은 어떻게 입금하나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,true,"투자 방법", "", "", ""));
        modelQuestions.add(new ModelQuestion(false,false,"", "투자를 할 수 있는 시간이 정해져 있나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,false,"", "CMA 계좌에 잔액이 부족해도 투자 주문이 가능한가요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,false,"", "환전을 해야 해외주식을 살 수 있나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,false,"", "매수 주문 시 주문필요금액이 왜 높게 나오나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,false,"", "거래 체결은 언제 완료되나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,false,"", "주문이 실패되었어요.", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,false,"", "매수/매도 주문을 취소하고 싶어요.", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,true,"배당금 입금", "", "", ""));
        modelQuestions.add(new ModelQuestion(false,false,"", "해외 주식도 배당금이 입금되나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,true,"거래 수수료", "", "", ""));
        modelQuestions.add(new ModelQuestion(false,false,"", "수수료는 얼마인가요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,true,"매도 방법", "", "", ""));
        modelQuestions.add(new ModelQuestion(false,false,"", "매도한 주식은 원화로 입금되나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,false,"", "CMA 계좌에 입금된 금액은 타행계좌로 어떻게 이체하나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,true,"세금 관련", "", "", ""));
        modelQuestions.add(new ModelQuestion(false,false,"", "[양도소득세] 해외 주식에서 수수료 제외 후 1년 동안 250만원 이상의 수익을 냈다면?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,false,"", "[양도소득세] 250만원의 수익을 냈어요. 양도세 신고 대행/납부는 어디서 하나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        modelQuestions.add(new ModelQuestion(false,false,"", "배당금을 받을 경우 세금 납부는 어떻게 하나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));


        allList.add(new ModelQuestion(false, true, "투자 전 준비사항", "", "", ""));
        allList.add(new ModelQuestion(false,false, "", "해외주식 투자 순서는 어떻게 되나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,false,"", "CMA 계좌는 어떻게 개설하나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,false,"", "투자할 금액은 어떻게 입금하나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,true,"투자 방법", "", "", ""));
        allList.add(new ModelQuestion(false,false,"", "투자를 할 수 있는 시간이 정해져 있나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,false,"", "CMA 계좌에 잔액이 부족해도 투자 주문이 가능한가요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,false,"", "환전을 해야 해외주식을 살 수 있나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,false,"", "매수 주문 시 주문필요금액이 왜 높게 나오나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,false,"", "거래 체결은 언제 완료되나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,false,"", "주문이 실패되었어요.", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,false,"", "매수/매도 주문을 취소하고 싶어요.", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,true,"배당금 입금", "", "", ""));
        allList.add(new ModelQuestion(false,false,"", "해외 주식도 배당금이 입금되나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,true,"거래 수수료", "", "", ""));
        allList.add(new ModelQuestion(false,false,"", "수수료는 얼마인가요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,true,"매도 방법", "", "", ""));
        allList.add(new ModelQuestion(false,false,"", "매도한 주식은 원화로 입금되나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,false,"", "CMA 계좌에 입금된 금액은 타행계좌로 어떻게 이체하나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,true,"세금 관련", "", "", ""));
        allList.add(new ModelQuestion(false,false,"", "[양도소득세] 해외 주식에서 수수료 제외 후 1년 동안 250만원 이상의 수익을 냈다면?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,false,"", "[양도소득세] 250만원의 수익을 냈어요. 양도세 신고 대행/납부는 어디서 하나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));
        allList.add(new ModelQuestion(false,false,"", "배당금을 받을 경우 세금 납부는 어떻게 하나요?", "", "http://www.quantec.co.kr/SettingPage/01_fnq.html"));


        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);

        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);

        adapterSearchedQuestion = new AdapterSearchedQuestion(modelQuestions, this);
        recyclerView2.setAdapter(adapterSearchedQuestion);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    modelQuestions.clear();

                    if(s.length() != 0){
                        banner.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        recyclerView2.setVisibility(View.VISIBLE);

                        for(ModelQuestion modelQuestion: allList){
                            if(modelQuestion.getTitle().contains(s.toString())){
                                modelQuestions.add(new ModelQuestion(false, false,"",modelQuestion.getTitle(),modelQuestion.getDesc(),"http://www.quantec.co.kr/SettingPage/01_fnq.html"));
                            }
                        }
                        if(modelQuestions.size() == 0){
                            modelQuestions.add(new ModelQuestion(true, false,"","","",""));
                        }
                        adapterSearchedQuestion.notifyDataSetChanged();

                    }else{
                        banner.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView2.setVisibility(View.GONE);

                    }
            }
        });


        adapterSearchedQuestion.setEmptyInquiryBt(new AdapterSearchedQuestion.EmptyInquiryBt() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(ActivityQuestion.this, ActivityInquiry.class);
                startActivity(intent);
                finish();
            }
        });

        adapterQuestion.setQuestionItemClick(new AdapterQuestion.QuestionItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(ActivityQuestion.this, ActivityQuestionWebView.class);
                intent.putExtra("url", modelQuestions.get(position).getUrl());
                startActivity(intent);
            }
        });

        adapterSearchedQuestion.setSearchedQuestionClick(new AdapterSearchedQuestion.SearchedQuestionClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(ActivityQuestion.this, ActivityQuestionWebView.class);
                intent.putExtra("url", modelQuestions.get(position).getUrl());
                startActivity(intent);
            }
        });

    }//onCreate 끝
}


//public class ActivityQuestion extends AppCompatActivity {
//
//    private TabLayout tabLayout;
//    private ViewPager viewPager;
//    private EditText searchEditText;
//    ImageView backBt;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_question);
//
//        backBt = findViewById(R.id.backBt);
//
//        searchEditText = findViewById(R.id.searchEditText);
//        //Initializing the TabLayout;
//        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("회사"));
//        tabLayout.addTab(tabLayout.newTab().setText("서비스"));
//        tabLayout.addTab(tabLayout.newTab().setText("상품 및 전략"));
//        tabLayout.addTab(tabLayout.newTab().setText("계좌 개설"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        tabLayout.setBackgroundResource(R.drawable.tab_line);
//        //Initializing ViewPager
//        viewPager = (ViewPager)findViewById(R.id.viewPager);
//
//        //Creating adapter
//        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        //Set TabSelectedListener
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
//
//        backBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
//
//
//    public class TabPagerAdapter extends FragmentStatePagerAdapter {
//        //Count number of tabs
//        private int tabCount;
//
//        public TabPagerAdapter(FragmentManager fm, int tabCount) {
//            super(fm);
//            this.tabCount = tabCount;
//        }
//        @Override
//        public Fragment getItem(int position) {
//
//            //Returning the current tabs
//            switch (position){
//                case 0:
//                    FgCommpany mainTabFragment1 = new FgCommpany();
//                    return mainTabFragment1;
//                case 1:
//                    FgService mainTabFragment2 = new FgService();
//                    return mainTabFragment2;
//                case 2:
//                    FgProductCraft mainTabFragment3 = new FgProductCraft();
//                    return mainTabFragment3;
//                case 3:
//                    FgAccount mainTabFragment4 = new FgAccount();
//                    return mainTabFragment4;
//
//                default:
//                    return null;
//            }
//        }
//        @Override
//        public int getCount() {
//            return tabCount;
//        }
//    }
//}
