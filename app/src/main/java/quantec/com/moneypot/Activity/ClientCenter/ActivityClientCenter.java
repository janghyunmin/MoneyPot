package quantec.com.moneypot.Activity.ClientCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import quantec.com.moneypot.R;

public class ActivityClientCenter extends AppCompatActivity implements View.OnClickListener {

    TextView question, guide, inquiry, info, version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_center);

        question = findViewById(R.id.question);
        guide = findViewById(R.id.guide);
        inquiry = findViewById(R.id.inquiry);
        info = findViewById(R.id.info);
        version = findViewById(R.id.version);


        question.setOnClickListener(this);
        guide.setOnClickListener(this);
        inquiry.setOnClickListener(this);
        info.setOnClickListener(this);
        version.setOnClickListener(this);

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
