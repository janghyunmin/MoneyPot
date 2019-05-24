package quantec.com.moneypot.Activity.Main.Fragment.FgTab2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import quantec.com.moneypot.R;

public class ActivityPotCook extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pot_cook);


    }

//    // Add Fragments to Tabs
//    private void setupViewPager(ViewPager viewPager) {
//        Adapter adapter = new Adapter(getChildFragmentManager());
//        adapter.addFragment(new FgAllPage(), "전체");
//        adapter.addFragment(new FgMyPotPage(), "내가 만든 포트");
//
//        viewPager.setAdapter(adapter);
//    }
//    static class Adapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//        public Adapter(FragmentManager manager) {
//            super(manager);
//        }
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }

}
