package quantec.com.moneypot.Activity.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import quantec.com.moneypot.R;

public class ActivitySetting extends AppCompatActivity {

    TextView productTitle, alramTitle, secessionTitle;
    ImageView backBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        backBt = findViewById(R.id.backBt);
        productTitle = findViewById(R.id.productTitle);
        alramTitle = findViewById(R.id.alramTitle);
        secessionTitle = findViewById(R.id.secessionTitle);

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
