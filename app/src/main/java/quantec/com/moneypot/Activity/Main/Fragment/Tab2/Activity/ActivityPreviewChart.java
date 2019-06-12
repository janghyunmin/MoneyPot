package quantec.com.moneypot.Activity.Main.Fragment.Tab2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import quantec.com.moneypot.R;

public class ActivityPreviewChart extends AppCompatActivity {

    Button prevBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_chart);

        prevBt = findViewById(R.id.prevBt);

        prevBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityPreviewChart.this, ActivityPotCook.class);
                setResult(100, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityPreviewChart.this, ActivityPotCook.class);
        setResult(100, intent);
        finish();
    }
}
