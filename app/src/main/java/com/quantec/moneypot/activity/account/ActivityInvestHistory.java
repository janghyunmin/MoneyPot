package com.quantec.moneypot.activity.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.account.fragment.FgHistoryComplete;
import com.quantec.moneypot.activity.account.fragment.FgHistoryProgress;
import com.quantec.moneypot.activity.account.fragment.FgHistoryStock;

public class ActivityInvestHistory extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    ImageView backBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest_history);

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

        backBt = findViewById(R.id.backBt);

        //Initializing the TabLayout;
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("보유 주식"));
        tabLayout.addTab(tabLayout.newTab().setText("진행 내역"));
        tabLayout.addTab(tabLayout.newTab().setText("완료 내역"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundResource(R.drawable.tab_line);
        //Initializing ViewPager
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        //Creating adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }//onCreate 끝

    public class TabPagerAdapter extends FragmentStatePagerAdapter {

        //Count number of tabs
        private int tabCount;

        public TabPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {

            //Returning the current tabs
            switch (position){
                case 0:
                    FgHistoryStock fgHistoryStock = new FgHistoryStock();
                    return fgHistoryStock;
                case 1:
                    FgHistoryProgress fgHistoryProgress = new FgHistoryProgress();
                    return fgHistoryProgress;
                case 2:
                    FgHistoryComplete fgHistoryComplete = new FgHistoryComplete();
                    return fgHistoryComplete;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
}
