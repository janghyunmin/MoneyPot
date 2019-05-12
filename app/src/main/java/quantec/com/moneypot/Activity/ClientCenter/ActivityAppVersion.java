package quantec.com.moneypot.Activity.ClientCenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import quantec.com.moneypot.R;

public class ActivityAppVersion extends AppCompatActivity implements View.OnClickListener {

    ImageView backBt;
    TextView recentlyVersion, nowVersion, versionUpBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_version);

        backBt = findViewById(R.id.backBt);

        recentlyVersion = findViewById(R.id.recentlyVersion);
        nowVersion = findViewById(R.id.nowVersion);
        versionUpBt = findViewById(R.id.versionUpBt);

        recentlyVersion.setText("v.01.11");
        nowVersion.setText("v.01.01");

        backBt.setOnClickListener(this);
        versionUpBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.versionUpBt:
                Toast.makeText(ActivityAppVersion.this, "버전을 업그레이드 합니다.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.backBt:
                finish();
                break;

        }

    }
}
