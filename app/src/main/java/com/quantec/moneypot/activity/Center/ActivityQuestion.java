package com.quantec.moneypot.activity.Center;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import com.quantec.moneypot.activity.Center.Fragment.FgAccount;
import com.quantec.moneypot.activity.Center.Fragment.FgCommpany;
import com.quantec.moneypot.activity.Center.Fragment.FgProductCraft;
import com.quantec.moneypot.activity.Center.Fragment.FgService;
import com.quantec.moneypot.R;

public class ActivityQuestion extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText searchEditText;
    ImageView backBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        backBt = findViewById(R.id.backBt);

        searchEditText = findViewById(R.id.searchEditText);
        //Initializing the TabLayout;
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("회사"));
        tabLayout.addTab(tabLayout.newTab().setText("서비스"));
        tabLayout.addTab(tabLayout.newTab().setText("상품 및 전략"));
        tabLayout.addTab(tabLayout.newTab().setText("계좌 개설"));
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
    }


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
                    FgCommpany mainTabFragment1 = new FgCommpany();
                    return mainTabFragment1;
                case 1:
                    FgService mainTabFragment2 = new FgService();
                    return mainTabFragment2;
                case 2:
                    FgProductCraft mainTabFragment3 = new FgProductCraft();
                    return mainTabFragment3;
                case 3:
                    FgAccount mainTabFragment4 = new FgAccount();
                    return mainTabFragment4;

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