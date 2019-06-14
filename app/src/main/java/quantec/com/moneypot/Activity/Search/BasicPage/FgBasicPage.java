package quantec.com.moneypot.Activity.Search.BasicPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.Search.ActivitySearch;
import quantec.com.moneypot.Activity.Search.BasicPage.Fragment.FgRecentlySearchTab;
import quantec.com.moneypot.Activity.Search.BasicPage.Fragment.FgRecommendTab;
import quantec.com.moneypot.R;

public class FgBasicPage extends Fragment {

    ViewPager viewPager;
    TextView recentlyTab, recommendTab;

    ActivitySearch activitySearch;

    public FgBasicPage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_basicpage, container, false);

        initializeViews();

        viewPager = view.findViewById(R.id.viewPager);

        recentlyTab = view.findViewById(R.id.recentlyTab);
        recommendTab = view.findViewById(R.id.recommendTab);

        recentlyTab.setBackgroundResource(R.drawable.bt_red_search_page);
        recentlyTab.setTextColor(getResources().getColor(R.color.normal_title_color));

        recommendTab.setBackgroundResource(0);
        recommendTab.setTextColor(getResources().getColor(R.color.dark_gray_color));

        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e("페이지 체인지","positionOffset 값 : "+positionOffset);
//                Log.e("페이지 체인지","positionOffsetPixels 값 : "+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    recentlyTab.setBackgroundResource(R.drawable.bt_red_search_page);
                    recentlyTab.setTextColor(getResources().getColor(R.color.normal_title_color));

                    recommendTab.setBackgroundResource(0);
                    recommendTab.setTextColor(getResources().getColor(R.color.dark_gray_color));
                }else{
                    recentlyTab.setBackgroundResource(0);
                    recentlyTab.setTextColor(getResources().getColor(R.color.dark_gray_color));

                    recommendTab.setBackgroundResource(R.drawable.bt_blue_search_page);
                    recommendTab.setTextColor(getResources().getColor(R.color.normal_title_color));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        return view;    }

    private void initializeViews(){
        activitySearch = (ActivitySearch) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivitySearch) {
            activitySearch = (ActivitySearch) context;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recentlyTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recentlyTab.setBackgroundResource(R.drawable.bt_red_search_page);
                recentlyTab.setTextColor(getResources().getColor(R.color.normal_title_color));

                recommendTab.setBackgroundResource(0);
                recommendTab.setTextColor(getResources().getColor(R.color.dark_gray_color));
                viewPager.setCurrentItem(0, true);

            }
        });

        recommendTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recentlyTab.setBackgroundResource(0);
                recentlyTab.setTextColor(getResources().getColor(R.color.dark_gray_color));

                recommendTab.setBackgroundResource(R.drawable.bt_blue_search_page);
                recommendTab.setTextColor(getResources().getColor(R.color.normal_title_color));

                viewPager.setCurrentItem(1, true);

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        RecommendAdapter adapter = new RecommendAdapter(getChildFragmentManager());
        adapter.addFragment(new FgRecentlySearchTab(), "최근 검색어");
        adapter.addFragment(new FgRecommendTab(), "지금 뜨는 상품");
        viewPager.setAdapter(adapter);
    }
    static class RecommendAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public RecommendAdapter(FragmentManager manager) {
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
