package quantec.com.moneypot.Activity.Main.Fragment.Tab2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity.ActivityPotCook;
import quantec.com.moneypot.Activity.Main.Fragment.Tab2.Fragment.FragmentLifcycle;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.databinding.FgTab2Binding;

public class FgTab2 extends Fragment {

    FgTab2Binding binding;
    private ActivityMain activityMain;
    boolean currentState = false;
    int currentPage = 0;

    Bundle cookPage3State, cookPageMarginState;

    FloatingActionButton makePotBt;

    public static boolean refreshPage = false;

    Adapter adapter;

    FragmentLifcycle fragmentToShow;


    public FgTab2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab2, container, false);

        initializeViews();

        return binding.getRoot();
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

        setupViewPager(binding.fragmentTab2Viewpager);
        binding.SearchTabLayout.setupWithViewPager(binding.fragmentTab2Viewpager);
        binding.fragmentTab2Viewpager.setOffscreenPageLimit(3);

        fragmentToShow = (FragmentLifcycle)adapter.getItem(1);

        binding.fragmentTab2Viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                if(currentState) {
                    binding.fragmentTab2Viewpager.setCurrentItem(1);
                }
                if(position == 1){
                    fragmentToShow.onResumeFragment();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        cookPage3State = new Bundle();
        cookPageMarginState = new Bundle();

        binding.makePotBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(activityMain, ActivityPotCook.class);
                startActivityForResult(intent1, 100);
                binding.fragmentTab2Viewpager.setCurrentItem(0);
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

                            case RxEvent.MOVED_POTCOOK_PAGE:
                                binding.fragmentTab2Viewpager.setCurrentItem(1);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(resultCode == 100){
                RxEventBus.getInstance().post(new RxEvent(RxEvent.REFRESH_MY_POT, null));
                refreshPage = true;
                binding.fragmentTab2Viewpager.setCurrentItem(1);
            }
        }
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new FgAllPage(), "전체");
        adapter.addFragment(new FgMyPotPage(), "내가 만든 포트");

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
