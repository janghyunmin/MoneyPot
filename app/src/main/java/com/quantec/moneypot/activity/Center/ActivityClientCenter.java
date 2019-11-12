package com.quantec.moneypot.activity.Center;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.quantec.moneypot.R;
import com.quantec.moneypot.databinding.ActivityClientCenterBinding;

public class ActivityClientCenter extends AppCompatActivity implements View.OnClickListener {

    ActivityClientCenterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_center);

        binding.question.setOnClickListener(this);
        binding.guide.setOnClickListener(this);
        binding.inquiry.setOnClickListener(this);
        binding.info.setOnClickListener(this);
        binding.version.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.question:
                Intent intent = new Intent(ActivityClientCenter.this, ActivityQuestion.class);
                startActivity(intent);
                break;

            case R.id.guide:
                Intent intent1 = new Intent(ActivityClientCenter.this, ActivityUseGuide.class);
                startActivity(intent1);
                break;

            case R.id.inquiry:
                Intent intentInquiry = new Intent(ActivityClientCenter.this, ActivityInquiry.class);
                startActivity(intentInquiry);
                break;

            case R.id.info:
                break;

            case R.id.version:
                Intent intent2 = new Intent(ActivityClientCenter.this, ActivityAppVersion.class);
                startActivity(intent2);
                break;

        }
    }
}
