package quantec.com.moneypot.Activity.ClientCenter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import quantec.com.moneypot.R;

public class ActivityQuestion extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText searchEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

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


    }//onCreate 끝

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
