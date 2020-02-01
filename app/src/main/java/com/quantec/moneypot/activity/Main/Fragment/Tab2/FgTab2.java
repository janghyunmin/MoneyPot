package com.quantec.moneypot.activity.Main.Fragment.Tab2;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.fragment.FgFollow;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.fragment.FgMySimulation;
import com.quantec.moneypot.datamanager.DataManager;
import com.quantec.moneypot.R;

import com.quantec.moneypot.dialog.DialogBasketFilter;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;

public class FgTab2 extends Fragment {

    private ActivityMain activityMain;

    private DialogBasketFilter dialogBasketFilter;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public FgTab2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_tab2, container, false);

        initializeViews();

        //Initializing the TabLayout;
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("팔로우 목록"));
        tabLayout.addTab(tabLayout.newTab().setText("나의 커스텀"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(activityMain.getResources().getColor(R.color.c_9a9a9a), activityMain.getResources().getColor(R.color.c_3d3f42));
        tabLayout.setBackgroundResource(R.drawable.tab_line);
        //Initializing ViewPager
        viewPager = view.findViewById(R.id.viewPager);

        return view;
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

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = activityMain.getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            Window window = activityMain.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setOffscreenPageLimit(1);
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

    }//onViewCreate 끝


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
                    FgFollow fgFollow = new FgFollow();
                    return fgFollow;
                case 1:
                    FgMySimulation fgMySimulation = new FgMySimulation();
                    return fgMySimulation;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //찜한포트 페이지 보일때
        if(!hidden){
            if(DataManager.get_INstance().isCheckTab1()){
                DataManager.get_INstance().setCheckTab1(false);
                RxEventBus.getInstance().post(new RxEvent(RxEvent.FOLLOW_REFRESH, null));
            }
        }
    }

}
