package quantec.com.moneypot.Activity.SearchPort.BasicPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.Activity.SearchPort.ActivitySearchPort;
import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Fg_RecentlySearchTab;
import quantec.com.moneypot.Activity.SearchPort.BasicPage.Fragment.Fg_RecommendTab;
import quantec.com.moneypot.R;

public class Fg_BasicPage extends Fragment {

    ViewPager port_search_page_recommendviewpager;
    TextView port_search_page_recently_search_tab, port_search_page_recommend_tab;

    ActivitySearchPort portSearchPageActivity;

    public Fg_BasicPage() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_activitysearch_basicpage, container, false);

        initializeViews();

        port_search_page_recommendviewpager = view.findViewById(R.id.port_search_page_recommendviewpager);

        port_search_page_recently_search_tab = view.findViewById(R.id.port_search_page_recently_search_tab);
        port_search_page_recommend_tab = view.findViewById(R.id.port_search_page_recommend_tab);

        port_search_page_recently_search_tab.setBackgroundResource(R.drawable.bt_red_search_page);
        port_search_page_recently_search_tab.setTextColor(getResources().getColor(R.color.normal_title_color));

        port_search_page_recommend_tab.setBackgroundResource(0);
        port_search_page_recommend_tab.setTextColor(getResources().getColor(R.color.dark_gray_color));

        setupViewPager(port_search_page_recommendviewpager);

        port_search_page_recommendviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e("페이지 체인지","positionOffset 값 : "+positionOffset);
//                Log.e("페이지 체인지","positionOffsetPixels 값 : "+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    port_search_page_recently_search_tab.setBackgroundResource(R.drawable.bt_red_search_page);
                    port_search_page_recently_search_tab.setTextColor(getResources().getColor(R.color.normal_title_color));

                    port_search_page_recommend_tab.setBackgroundResource(0);
                    port_search_page_recommend_tab.setTextColor(getResources().getColor(R.color.dark_gray_color));
                }else{
                    port_search_page_recently_search_tab.setBackgroundResource(0);
                    port_search_page_recently_search_tab.setTextColor(getResources().getColor(R.color.dark_gray_color));

                    port_search_page_recommend_tab.setBackgroundResource(R.drawable.bt_blue_search_page);
                    port_search_page_recommend_tab.setTextColor(getResources().getColor(R.color.normal_title_color));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        return view;
    }


    private void initializeViews(){
        portSearchPageActivity = (ActivitySearchPort) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivitySearchPort) {
            portSearchPageActivity = (ActivitySearchPort) context;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        port_search_page_recently_search_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                port_search_page_recently_search_tab.setBackgroundResource(R.drawable.bt_red_search_page);
                port_search_page_recently_search_tab.setTextColor(getResources().getColor(R.color.normal_title_color));

                port_search_page_recommend_tab.setBackgroundResource(0);
                port_search_page_recommend_tab.setTextColor(getResources().getColor(R.color.dark_gray_color));
                port_search_page_recommendviewpager.setCurrentItem(0, true);

            }
        });

        port_search_page_recommend_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                port_search_page_recently_search_tab.setBackgroundResource(0);
                port_search_page_recently_search_tab.setTextColor(getResources().getColor(R.color.dark_gray_color));

                port_search_page_recommend_tab.setBackgroundResource(R.drawable.bt_blue_search_page);
                port_search_page_recommend_tab.setTextColor(getResources().getColor(R.color.normal_title_color));

                port_search_page_recommendviewpager.setCurrentItem(1, true);

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        RecommendAdapter adapter = new RecommendAdapter(getChildFragmentManager());
        adapter.addFragment(new Fg_RecentlySearchTab(), "최근 검색어");
        adapter.addFragment(new Fg_RecommendTab(), "지금 뜨는 상품");
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
