package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab2.Fg_CookPage.ModelPortList;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;

public class ActivityPotCook extends AppCompatActivity {


    ViewPager viewPager;
    TabLayout tabLayout;

    int currentPage = 0;
    TextView defaultNum, defaultTitle, nextBt, prevBt, okBt;
    LinearLayout next2Bt;

//    ArrayList<ModelPortList> modelPortLists;
    ArrayList<String> stCode = new ArrayList<>();
    ArrayList<String> stName = new ArrayList<>();

    int portCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_cook);

//        modelPortLists = new ArrayList<>();

        defaultTitle = findViewById(R.id.defaultTitle);
        defaultNum = findViewById(R.id.defaultNum);

        nextBt = findViewById(R.id.nextBt);
        prevBt = findViewById(R.id.prevBt);
        okBt = findViewById(R.id.okBt);
        next2Bt = findViewById(R.id.next2Bt);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                viewPager.setCurrentItem(currentPage);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = AnimationUtils.loadAnimation(ActivityPotCook.this, R.anim.fade);
                animation.setFillAfter(false);
                animation.setFillEnabled(false);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        nextBt.setEnabled(false);
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        nextBt.setVisibility(View.INVISIBLE);
                        prevBt.setEnabled(true);
                        okBt.setEnabled(true);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                nextBt.startAnimation(animation);
            }
        });

        prevBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextBt.setVisibility(View.VISIBLE);
                TranslateAnimation animation3 = new TranslateAnimation(
                        Animation.ABSOLUTE, 700,
                        Animation.ABSOLUTE, 0,
                        Animation.ABSOLUTE,0,
                        Animation.ABSOLUTE,0);
                animation3.setDuration(700);
                animation3.setFillAfter(false);
                animation3.setFillEnabled(false);
                animation3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        prevBt.setEnabled(false);
                        okBt.setEnabled(false);
                        nextBt.setEnabled(true);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                nextBt.startAnimation(animation3);
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

                            case RxEvent.ZZIM_PORT_SELECT_ITEM:

                                if(rxEvent.getBundle().getBoolean("click")) {
                                    portCount = rxEvent.getBundle().getInt("count");

                                    stCode.add(rxEvent.getBundle().getString("code"));
                                    stName.add(rxEvent.getBundle().getString("title"));

                                    if(portCount == 0){
                                        defaultNum.setText("선택 없음");
                                        defaultTitle.setText("2개 이상을 선택해주세요.");
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                        defaultNum.setTextColor(getResources().getColor(R.color.text_black_color));
                                    }
                                    else if(portCount < 2 && portCount > 0) {
                                        defaultNum.setText(String.valueOf(portCount)+"개");
                                        defaultTitle.setText("2개 이상을 선택해주세요.");
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                        defaultNum.setTextColor(getResources().getColor(R.color.text_black_color));

                                    }else{

                                        defaultNum.setText(String.valueOf(portCount)+"개");
                                        defaultTitle.setText("선택하신 포트의 최소 투자 금액은 200만입니다.");
                                        defaultNum.setTextColor(getResources().getColor(R.color.make_port_blue_color));
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                    }
                                }
                                else{

                                    portCount = rxEvent.getBundle().getInt("count");

                                    stCode.remove(stCode.indexOf(rxEvent.getBundle().getString("code")));
                                    stName.remove(stName.indexOf(rxEvent.getBundle().getString("title")));

                                    if(portCount == 0){
                                        defaultNum.setText("선택 없음");
                                        defaultTitle.setText("2개 이상을 선택해주세요.");
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                        defaultNum.setTextColor(getResources().getColor(R.color.text_black_color));
                                    }
                                    else if(portCount < 2 && portCount > 0) {
                                        defaultNum.setText(String.valueOf(portCount)+"개");
                                        defaultTitle.setText("2개 이상을 선택해주세요.");
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                        defaultNum.setTextColor(getResources().getColor(R.color.text_black_color));

                                    }else{

                                        defaultNum.setText(String.valueOf(portCount)+"개");
                                        defaultTitle.setText("선택하신 포트의 최소 투자 금액은 200만입니다.");
                                        defaultNum.setTextColor(getResources().getColor(R.color.make_port_blue_color));
                                        defaultTitle.setTextColor(getResources().getColor(R.color.text_gray_color));
                                    }
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

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new FgPotCookAll(), "전체");
        adapter.addFragment(new FgPotCookBasic(), "기본");
        adapter.addFragment(new FgPotCookAllocation(), "채권");
        adapter.addFragment(new FgPotCookBond(), "배당");
        adapter.addFragment(new FgPotCookTheme(), "테마");

        viewPager.setAdapter(adapter);
    }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public Adapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
