package quantec.com.moneypot.Activity.Main.Fragment.FgTab3;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab1_3m.Fg_Tab1_3m;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab2_6m.Fg_Tab2_6m;
import quantec.com.moneypot.Activity.Main.Fragment.FgTab3.Fragment.Tab3_am.Fg_Tab3_am;
import quantec.com.moneypot.Activity.Myinfo.ActivityMyinfo;
import quantec.com.moneypot.Activity.SearchPort.ActivitySearchPort;
import quantec.com.moneypot.CustomView.SwipeViewPager;
import quantec.com.moneypot.DataManager.DataManager;
import quantec.com.moneypot.R;
import quantec.com.moneypot.RxAndroid.RxEvent;
import quantec.com.moneypot.RxAndroid.RxEventBus;
import quantec.com.moneypot.databinding.FgTab3Binding;

public class Fg_tab3 extends Fragment {

    FgTab3Binding tab3Binding;

    public Fg_tab3() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tab3Binding = DataBindingUtil.inflate(inflater, R.layout.fg_tab3, container, false);

        setupViewPager(tab3Binding.rankingPageViewpager);
        tab3Binding.rankingPageTapLayout.setupWithViewPager(tab3Binding.rankingPageViewpager);
        tab3Binding.rankingPageViewpager.setOffscreenPageLimit(2);
        tab3Binding.rankingPageViewpager.setPagingEnabled(false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        return tab3Binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 검색 페이지로 이동
        tab3Binding.rankingPageTopSearchBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivitySearchPort.class);
                startActivity(intent);
            }
        });

        // 내 정보 페이지로 이동
        tab3Binding.rankingPageTopMyBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityMyinfo.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        if(DataManager.get_INstance().isModifyPort()){
            DataManager.get_INstance().setModifyPort(false);
            RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_DELETE_MODIFY, null));
        }
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new Fg_Tab1_3m(), "3개월");
        adapter.addFragment(new Fg_Tab2_6m(), "6개월");
        adapter.addFragment(new Fg_Tab3_am(), "누적");

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
