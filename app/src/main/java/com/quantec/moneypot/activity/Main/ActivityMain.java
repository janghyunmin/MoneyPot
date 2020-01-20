package com.quantec.moneypot.activity.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import com.quantec.moneypot.activity.Main.Fragment.Tab1.FgTab1;
import com.quantec.moneypot.activity.Main.Fragment.Tab2.FgTab2;
import com.quantec.moneypot.activity.Main.Fragment.Tab3.FgTab3;
import com.quantec.moneypot.activity.Main.Fragment.tab4.FgTab4;
import com.quantec.moneypot.R;
import com.quantec.moneypot.databinding.ActivityMainBinding;
import com.quantec.moneypot.rxandroid.RxEvent;
import com.quantec.moneypot.rxandroid.RxEventBus;
import com.quantec.moneypot.util.statusBar.UtilStatusBar;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ActivityMain extends AppCompatActivity {

    ActivityMainBinding binding;

    FgTab1 fgTab1;
    FgTab2 fgTab2;
    FgTab3 fgTab3;
    FgTab4 fgTab4;
    Fragment currentFragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // 스테이터스 바 색상 변경 -> 화이트
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }else{
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottombar.setMode(BottomNavigationBar.MODE_FIXED);
        binding.bottombar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        binding.bottombar
                .addItem(new BottomNavigationItem(R.drawable.tab_home_active, "홈").setActiveColorResource(R.color.c_4e7cff)
                        .setInactiveIcon(getResources().getDrawable(R.drawable.tab_home_inactive)).setInActiveColorResource(R.color.c_9a9a9a))
                .addItem(new BottomNavigationItem(R.drawable.tab_follow_active, "팔로우").setActiveColorResource(R.color.c_4e7cff)
                        .setInactiveIcon(getResources().getDrawable(R.drawable.tab_follow_inactive)).setInActiveColorResource(R.color.c_9a9a9a))
                .addItem(new BottomNavigationItem(R.drawable.tab_custom_active, "자산커스텀").setActiveColorResource(R.color.c_4e7cff)
                        .setInactiveIcon(getResources().getDrawable(R.drawable.tab_custom_inactive)).setInActiveColorResource(R.color.c_9a9a9a))
                .addItem(new BottomNavigationItem(R.drawable.tab_setting_active, "설정").setActiveColorResource(R.color.c_4e7cff)
                        .setInactiveIcon(getResources().getDrawable(R.drawable.tab_setting_inactive)).setInActiveColorResource(R.color.c_9a9a9a))
                .setFirstSelectedPosition(0)
                .initialise();



        transaction = getSupportFragmentManager().beginTransaction();
        if(fgTab1 == null) {
            fgTab1 = new FgTab1();
            transaction.add(R.id.ContainerContain, fgTab1).commit();
            currentFragment = fgTab1;
        }else {

            transaction.hide(currentFragment).show(fgTab1).commit();
            currentFragment = fgTab1;
        }

        binding.bottombar.setAnimationDuration(50);
        binding.bottombar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {

                    transaction = getSupportFragmentManager().beginTransaction();

                    if(position == 0) {

                        if(fgTab1 == null) {
                            fgTab1 = new FgTab1();
                            transaction.add(R.id.ContainerContain, fgTab1).commit();
                            currentFragment = fgTab1;
                        }else {

                            transaction.hide(currentFragment).show(fgTab1).commit();
                            currentFragment = fgTab1;
                        }

                        // 스테이터스 바 색상 변경 -> 화이트
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                            Window w = getWindow(); // in Activity's onCreate() for instance
                            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                        }else{
                            Window window = getWindow();
                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(getResources().getColor(R.color.c_667ffe));

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                window.getDecorView().setSystemUiVisibility(0);
                        }

                    }else if(position == 1){

                        if(fgTab2 == null) {
                            fgTab2 = new FgTab2();
                            transaction.hide(currentFragment).add(R.id.ContainerContain, fgTab2).commit();
                            currentFragment = fgTab2;
                        }else{
                            transaction.hide(currentFragment).show(fgTab2).commit();
                            currentFragment = fgTab2;
                        }

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
                    }

                    else if(position == 2){

                        if(fgTab3 == null) {
                            fgTab3 = new FgTab3();
                            transaction.hide(currentFragment).add(R.id.ContainerContain, fgTab3).commitAllowingStateLoss();
                            currentFragment = fgTab3;
                        }else{
                            transaction.hide(currentFragment).show(fgTab3).commitAllowingStateLoss();
                            currentFragment = fgTab3;
                        }

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
                    }

                    else if(position == 3){

                        if(fgTab4 == null) {
                            fgTab4 = new com.quantec.moneypot.activity.Main.Fragment.tab4.FgTab4();
                            transaction.hide(currentFragment).add(R.id.ContainerContain, fgTab4).commit();
                            currentFragment = fgTab4;
                        }else{
                            transaction.hide(currentFragment).show(fgTab4).commit();
                            currentFragment = fgTab4;
                        }

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
                    }
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
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

                            case RxEvent.FOLLOWPAGE_MOVE:

                                transaction = getSupportFragmentManager().beginTransaction();
                                if (fgTab2 == null) {
                                    fgTab2 = new FgTab2();
                                    transaction.hide(currentFragment).add(R.id.ContainerContain, fgTab2).commit();
                                    currentFragment = fgTab2;
                                } else {
                                    transaction.hide(currentFragment).show(fgTab2).commit();
                                    currentFragment = fgTab2;
                                }
                                binding.bottombar.setFirstSelectedPosition(1)
                                    .initialise();
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

    }

    @Override
    public void onBackPressed() {
//        binding.bottombar.show(true);
        finish();
    }
}
