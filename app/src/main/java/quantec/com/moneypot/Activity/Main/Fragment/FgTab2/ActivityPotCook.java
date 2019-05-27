package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.R;

public class ActivityPotCook extends AppCompatActivity {


    ViewPager viewPager;
    TabLayout tabLayout;

    int currentPage = 0;
    TextView defaultNum, defaultTitle, nextBt, prevBt, okBt;
    LinearLayout next2Bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_cook);

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
                nextBt.setVisibility(View.GONE);
                nextBt.setAnimation(AnimationUtils.loadAnimation(ActivityPotCook.this, R.anim.potcookbt_right));
                next2Bt.setVisibility(View.VISIBLE);


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
