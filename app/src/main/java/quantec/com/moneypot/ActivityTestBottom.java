package quantec.com.moneypot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class ActivityTestBottom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bottom);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .setActiveColor("#FF0000")
                .setInActiveColor("#AAAAAA")
                .setBarBackgroundColor("#FFFFFF")

                .addItem(new BottomNavigationItem(R.drawable.ic_tip_icon, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_arrow_bottom, "Books"))
                .addItem(new BottomNavigationItem(R.drawable.ic_bottom_all_off, "Music"))
                .addItem(new BottomNavigationItem(R.drawable.ic_rank_check_off, "Movies & TV"))
                .addItem(new BottomNavigationItem(R.drawable.ic_gif, "Games"))
                .setFirstSelectedPosition(0)
                .initialise();


        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });
    }
}
