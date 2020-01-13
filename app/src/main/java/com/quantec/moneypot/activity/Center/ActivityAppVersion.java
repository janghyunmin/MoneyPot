package com.quantec.moneypot.activity.center;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.quantec.moneypot.R;

public class ActivityAppVersion extends AppCompatActivity implements View.OnClickListener {

    ImageView backBt;
    TextView recentlyVersion, nowVersion, versionUpBt;
    String preVersion, thisVersion;
    boolean version = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_version);

        backBt = findViewById(R.id.backBt);

        recentlyVersion = findViewById(R.id.recentlyVersion);
        nowVersion = findViewById(R.id.nowVersion);
        versionUpBt = findViewById(R.id.versionUpBt);

        backBt.setOnClickListener(this);
        versionUpBt.setOnClickListener(this);

        preVersion = "v.01.11";
        thisVersion = "v.01.11";

        recentlyVersion.setText(thisVersion);
        nowVersion.setText(preVersion);

        if(preVersion.equals(thisVersion)){
            versionUpBt.setText("현재 최신 버전입니다.");
            versionUpBt.setTextColor(getResources().getColor(R.color.c_cccccc));
            versionUpBt.setBackground(getResources().getDrawable(R.drawable.rectangle_dark_gray));

            version = false;
        }else{
            versionUpBt.setText("업데이트가 필요합니다.");
            versionUpBt.setTextColor(getResources().getColor(R.color.text_white_color));
            versionUpBt.setBackground(getResources().getDrawable(R.drawable.rectangle_able));

            version = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.versionUpBt:
                if(version){

                    Toast.makeText(ActivityAppVersion.this, "버전을 업그레이드 합니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ActivityAppVersion.this, "현재 최신 버전입니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.backBt:
                finish();
                break;

        }
    }
}
