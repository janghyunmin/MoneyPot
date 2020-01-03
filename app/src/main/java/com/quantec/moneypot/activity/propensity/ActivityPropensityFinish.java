package com.quantec.moneypot.activity.propensity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.prefer.ActivityPrefer;

public class ActivityPropensityFinish extends BasePropensityActivity {

    ImageView backBt;
    TextView grade1, grade2, nextBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propensity_finish);

        actList.add(this);

        backBt = findViewById(R.id.backBt);
        grade1 = findViewById(R.id.grade1);
        grade2 = findViewById(R.id.grade2);
        nextBt = findViewById(R.id.nextBt);

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityRemove();
                finish();
            }
        });

        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityPropensityFinish.this, ActivityPrefer.class);
                startActivity(intent);
                actFinish();
            }
        });
    }
}
