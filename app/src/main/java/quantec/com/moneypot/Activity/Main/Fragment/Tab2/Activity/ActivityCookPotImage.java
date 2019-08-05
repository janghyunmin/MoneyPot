package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import quantec.com.moneypot.R;

public class ActivityCookPotImage extends AppCompatActivity {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_pot_image);

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

        image = findViewById(R.id.image);

        Intent intent1 = getIntent();
        if(intent1.getIntExtra("imageUrl", 0) == 0){
            image.setImageDrawable(getResources().getDrawable(R.drawable.img_researcher_1));
        }else if(intent1.getIntExtra("imageUrl", 0) == 1){
            image.setImageDrawable(getResources().getDrawable(R.drawable.img_newspaper));
        }else{
            image.setImageDrawable(getResources().getDrawable(R.drawable.img_market_banner_1));
        }

    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
}
