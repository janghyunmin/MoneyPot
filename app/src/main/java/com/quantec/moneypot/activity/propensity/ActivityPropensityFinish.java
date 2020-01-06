package com.quantec.moneypot.activity.propensity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.prefer.ActivityPrefer;
import com.quantec.moneypot.network.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPropensityFinish extends BasePropensityActivity {

    ImageView backBt;
    TextView grade1, grade2, nextBt;
    String propensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propensity_finish);

        actList.add(this);

        backBt = findViewById(R.id.backBt);
        grade1 = findViewById(R.id.grade1);
        grade2 = findViewById(R.id.grade2);
        nextBt = findViewById(R.id.nextBt);

        Intent intent = getIntent();
        propensity = intent.getStringExtra("propensity");

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

                ModelSetPropensity modelSetPropensity = new ModelSetPropensity();
                modelSetPropensity.setPropensity(propensity);

                Call<Object> getReList = RetrofitClient.getInstance().getService().setPropensity("application/json", modelSetPropensity);
                getReList.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.code() == 200) {

                        }
                    }
                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.e("실패","실패"+t.getMessage());
                    }
                });

//                Intent intent = new Intent(ActivityPropensityFinish.this, ActivityPrefer.class);
//                startActivity(intent);
//                actFinish();
            }
        });
    }
}
