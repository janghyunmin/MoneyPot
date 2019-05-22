package quantec.com.moneypot.Activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import quantec.com.moneypot.Activity.ActivityLifeChallenge.ActivityLifeChallenge;
import quantec.com.moneypot.Activity.BaseActivity.BaseActivity;
import quantec.com.moneypot.Activity.Login.MemberShipPage.ActivityMemberShipMain;
import quantec.com.moneypot.R;

public class ActivityIntroBeginPage extends BaseActivity {

    TextView startBt, loginBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_begin_page);

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        mainList.add(0, this);

        startBt = findViewById(R.id.startBt);
        loginBt = findViewById(R.id.loginBt);

        startBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityIntroBeginPage.this, ActivityLifeChallenge.class);
                startActivity(intent);
            }
        });


        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityIntroBeginPage.this, ActivityMemberShipMain.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
