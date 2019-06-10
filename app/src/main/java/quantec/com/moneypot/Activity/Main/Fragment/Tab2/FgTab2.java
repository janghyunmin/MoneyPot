package quantec.com.moneypot.Activity.Main.Fragment.Tab2;

import android.content.Context;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;
import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.FgTab2Binding;

public class FgTab2 extends Fragment {

    FgTab2Binding binding;
    private ActivityMain activityMain;
    boolean currentState = false;
    int currentPage = 0;

    Bundle cookPage3State, cookPageMarginState;

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
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        cookPage3State = new Bundle();
        cookPageMarginState = new Bundle();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

//        if(DataManager.get_INstance().isCheckTab1() || DataManager.get_INstance().isCheckCookPage()) {
//            DataManager.get_INstance().setCheckTab1(false);
//            DataManager.get_INstance().setCheckCookPage(false);
//            RxEventBus.getInstance().post(new RxEvent(RxEvent.ZZIM_PORT_LOAD, null));
//        }
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
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
