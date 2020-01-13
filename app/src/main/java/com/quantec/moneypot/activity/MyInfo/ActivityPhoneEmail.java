package com.quantec.moneypot.activity.MyInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.util.SharedPreference.SharedPreferenceUtil;

public class ActivityPhoneEmail extends AppCompatActivity {

    ConstraintLayout emailLayout;
    TextView idText, emailText, phoneText;
    String userId, hpNumber, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_email);

        // 스테이터스 바 색상 변경 -> 화이트
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

        emailLayout = findViewById(R.id.emailLayout);
        email = "";
        userId = SharedPreferenceUtil.getInstance(ActivityPhoneEmail.this).getStringExtra("userId");
        hpNumber = SharedPreferenceUtil.getInstance(ActivityPhoneEmail.this).getStringExtra("hpNumber");

        idText = findViewById(R.id.idText);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);

        if(email.isEmpty()){
            emailText.setText("info@quantec.co.kr");
        }else{
            emailText.setText(email);
        }
        idText.setText(userId);
        phoneText.setText(hpNumber);

        emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityPhoneEmail.this, ActivityChangedEmail.class);
                startActivity(intent);

            }
        });


    }
}
