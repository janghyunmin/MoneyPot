package quantec.com.moneypot.Activity.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import quantec.com.moneypot.R;

public class ActivityAgreePage extends AppCompatActivity {

    LinearLayout activity_agree_page_allCheck, activity_agree_page_firstBT, activity_agree_page_secondBT;
    TextView activity_agree_page_BT;
    ImageView activity_agree_page_allImage, activity_agree_page_firstImage, activity_agree_page_secondImage;
    int[] selectBT = {0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree_page);

        activity_agree_page_allCheck = findViewById(R.id.activity_agree_page_allCheck);
        activity_agree_page_firstBT = findViewById(R.id.activity_agree_page_firstBT);
        activity_agree_page_secondBT = findViewById(R.id.activity_agree_page_secondBT);

        activity_agree_page_BT = findViewById(R.id.activity_agree_page_BT);

        activity_agree_page_allImage = findViewById(R.id.activity_agree_page_allImage);
        activity_agree_page_firstImage = findViewById(R.id.activity_agree_page_firstImage);
        activity_agree_page_secondImage = findViewById(R.id.activity_agree_page_secondImage);


        activity_agree_page_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectBT[1] == 1 && selectBT[2] == 1) {
                    Intent intent = new Intent(ActivityAgreePage.this, ActivityPhoneConfirm.class);
                    startActivity(intent);
                }
            }
        });

        //전체 동의
        activity_agree_page_allCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(selectBT[0] == 0) {
                        selectBT[0] = 1;
                        activity_agree_page_allImage.setBackgroundResource(R.drawable.start_on);
                            selectBT[1] = 1;
                            selectBT[2] = 1;
                            activity_agree_page_firstImage.setBackgroundResource(R.drawable.start_on);
                            activity_agree_page_secondImage.setBackgroundResource(R.drawable.start_on);

                    }else{
                        selectBT[0] = 0;
                        activity_agree_page_allImage.setBackgroundResource(R.drawable.start_off);
                        selectBT[1] = 0;
                        selectBT[2] = 0;
                        activity_agree_page_firstImage.setBackgroundResource(R.drawable.start_off);
                        activity_agree_page_secondImage.setBackgroundResource(R.drawable.start_off);
                    }
                checkedNextBt();
            }
        });

        //1번 동의
        activity_agree_page_firstBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectBT[1] == 0) {
                    selectBT[1] = 1;
                    activity_agree_page_firstImage.setBackgroundResource(R.drawable.start_on);
                }else{
                    selectBT[1] = 0;
                    activity_agree_page_firstImage.setBackgroundResource(R.drawable.start_off);
                }
                checkedNextBt();
             }
        });

        //2번 동의
        activity_agree_page_secondBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectBT[2] == 0) {
                    selectBT[2] = 1;
                    activity_agree_page_secondImage.setBackgroundResource(R.drawable.start_on);
                }else{
                    selectBT[2] = 0;
                    activity_agree_page_secondImage.setBackgroundResource(R.drawable.start_off);
                }
                checkedNextBt();
            }
        });
    }

    void checkedNextBt() {

        if(selectBT[1] == 1 &&selectBT[2] == 1){
            activity_agree_page_BT.setEnabled(true);
            activity_agree_page_BT.setBackgroundResource(R.color.blue_color);
        }else{
            activity_agree_page_BT.setEnabled(false);
            activity_agree_page_BT.setBackgroundResource(R.color.greyish_brown);
        }
    }
}
