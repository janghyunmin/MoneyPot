package quantec.com.moneypot.Activity.Login.MemberShipPage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ActivityAgreePageBinding;

public class ActivityAgreePage extends AppCompatActivity implements View.OnClickListener {


    boolean selectAllBt = false;
    int[] selectBT = {0, 0, 0, 0, 0, 0};
    int[] nonSelectBt = {0, 0, 0};

    ActivityAgreePageBinding agreePageBinding;


    String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree_page);

        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("passPhoneNum");

        agreePageBinding = DataBindingUtil.setContentView(this, R.layout.activity_agree_page);

        agreePageBinding.serviceAllCheckBt.setBackgroundResource(R.drawable.ic_checkbox_gray_24_dp);
        agreePageBinding.serviceTitle1Bt1.setBackgroundResource(R.drawable.ic_checkbox_gray);
        agreePageBinding.serviceTitle1Bt2.setBackgroundResource(R.drawable.ic_checkbox_gray);
        agreePageBinding.serviceTitle2Bt1.setBackgroundResource(R.drawable.ic_checkbox_gray);
        agreePageBinding.serviceTitle2Bt2.setBackgroundResource(R.drawable.ic_checkbox_gray);
        agreePageBinding.serviceTitle2Bt3.setBackgroundResource(R.drawable.ic_checkbox_gray);
        agreePageBinding.serviceTitle2Bt4.setBackgroundResource(R.drawable.ic_checkbox_gray);
        agreePageBinding.serviceTitle3Bt1.setBackgroundResource(R.drawable.ic_checkbox_gray);
        agreePageBinding.serviceTitle3Bt2.setBackgroundResource(R.drawable.ic_checkbox_gray);
        agreePageBinding.serviceTitle3Bt3.setBackgroundResource(R.drawable.ic_checkbox_gray);

        agreePageBinding.serviceAllCheckBt.setOnClickListener(this);
        agreePageBinding.serviceTitle1Bt1.setOnClickListener(this);
        agreePageBinding.serviceTitle1Bt2.setOnClickListener(this);
        agreePageBinding.serviceTitle2Bt1.setOnClickListener(this);
        agreePageBinding.serviceTitle2Bt2.setOnClickListener(this);
        agreePageBinding.serviceTitle2Bt3.setOnClickListener(this);
        agreePageBinding.serviceTitle2Bt4.setOnClickListener(this);
        agreePageBinding.serviceTitle3Bt1.setOnClickListener(this);
        agreePageBinding.serviceTitle3Bt2.setOnClickListener(this);
        agreePageBinding.serviceTitle3Bt3.setOnClickListener(this);

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

//        actList.add(this);

        agreePageBinding.nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectBT[0] == 1 && selectBT[1] == 1 && selectBT[2] == 1 && selectBT[3] == 1 && selectBT[4] == 1 && selectBT[5] == 1) {
                    Intent intent = new Intent(ActivityAgreePage.this, ActivityPhoneConfirm.class);
                    intent.putExtra("passPhoneNum", phoneNum);
                    startActivity(intent);
                }else{
                    Toast.makeText(ActivityAgreePage.this, "필수 약관을 동의해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        agreePageBinding.agreePageBackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityAgreePage.this, ActivityMemberShipMain.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.leftin_activity,R.anim.rightout_activity);
            }
        });
    }

    void checkedNextBt() {

        if (selectBT[0] == 1 && selectBT[1] == 1 && selectBT[2] == 1 && selectBT[3] == 1 && selectBT[4] == 1 && selectBT[5] == 1
                && (nonSelectBt[0] == 1 && nonSelectBt[1] == 1 && nonSelectBt[2] == 1)) {

            selectAllBt = true;
            agreePageBinding.serviceAllCheckBt.setBackgroundResource(R.drawable.ic_checkbox_red_24_dp);

            agreePageBinding.nextBt.setEnabled(true);
            agreePageBinding.nextBt.setBackgroundColor(getResources().getColor(R.color.red_text_color));
            agreePageBinding.nextBt.setTextColor(getResources().getColor(R.color.normal_title_color));
        }
        else {

            if(selectBT[0] == 1 && selectBT[1] == 1 && selectBT[2] == 1 && selectBT[3] == 1 && selectBT[4] == 1 && selectBT[5] == 1){

                selectAllBt = false;
                agreePageBinding.serviceAllCheckBt.setBackgroundResource(R.drawable.ic_checkbox_gray_24_dp);

                agreePageBinding.nextBt.setEnabled(true);
                agreePageBinding.nextBt.setBackgroundColor(getResources().getColor(R.color.red_text_color));
                agreePageBinding.nextBt.setTextColor(getResources().getColor(R.color.normal_title_color));

            }else{

                selectAllBt = false;
                agreePageBinding.serviceAllCheckBt.setBackgroundResource(R.drawable.ic_checkbox_gray_24_dp);

                agreePageBinding.nextBt.setEnabled(false);
                agreePageBinding.nextBt.setBackgroundColor(getResources().getColor(R.color.button_enable));
                agreePageBinding.nextBt.setTextColor(getResources().getColor(R.color.button_enable_text));
            }
        }
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(ActivityAgreePage.this, ActivityMemberShipMain.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.leftin_activity,R.anim.rightout_activity);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.serviceAllCheckBt:
                if(!selectAllBt) {

                    selectAllBt = true;

                    selectBT[0] = 1;
                    selectBT[1] = 1;
                    selectBT[2] = 1;
                    selectBT[3] = 1;
                    selectBT[4] = 1;
                    selectBT[5] = 1;

                    nonSelectBt[0] = 1;
                    nonSelectBt[1] = 1;
                    nonSelectBt[2] = 1;

                    agreePageBinding.serviceAllCheckBt.setBackgroundResource(R.drawable.ic_checkbox_red_24_dp);

                    agreePageBinding.serviceTitle1Bt1.setBackgroundResource(R.drawable.ic_checkbox_red);
                    agreePageBinding.serviceTitle1Bt2.setBackgroundResource(R.drawable.ic_checkbox_red);

                    agreePageBinding.serviceTitle2Bt1.setBackgroundResource(R.drawable.ic_checkbox_red);
                    agreePageBinding.serviceTitle2Bt2.setBackgroundResource(R.drawable.ic_checkbox_red);
                    agreePageBinding.serviceTitle2Bt3.setBackgroundResource(R.drawable.ic_checkbox_red);
                    agreePageBinding.serviceTitle2Bt4.setBackgroundResource(R.drawable.ic_checkbox_red);

                    agreePageBinding.serviceTitle3Bt1.setBackgroundResource(R.drawable.ic_checkbox_red);
                    agreePageBinding.serviceTitle3Bt2.setBackgroundResource(R.drawable.ic_checkbox_red);
                    agreePageBinding.serviceTitle3Bt3.setBackgroundResource(R.drawable.ic_checkbox_red);

                }else{

                    selectAllBt = false;

                    selectBT[0] = 0;
                    selectBT[1] = 0;
                    selectBT[2] = 0;
                    selectBT[3] = 0;
                    selectBT[4] = 0;
                    selectBT[5] = 0;

                    nonSelectBt[0] = 0;
                    nonSelectBt[1] = 0;
                    nonSelectBt[2] = 0;
//
                    agreePageBinding.serviceAllCheckBt.setBackgroundResource(R.drawable.ic_checkbox_gray_24_dp);

                    agreePageBinding.serviceTitle1Bt1.setBackgroundResource(R.drawable.ic_checkbox_gray);
                    agreePageBinding.serviceTitle1Bt2.setBackgroundResource(R.drawable.ic_checkbox_gray);

                    agreePageBinding.serviceTitle2Bt1.setBackgroundResource(R.drawable.ic_checkbox_gray);
                    agreePageBinding.serviceTitle2Bt2.setBackgroundResource(R.drawable.ic_checkbox_gray);
                    agreePageBinding.serviceTitle2Bt3.setBackgroundResource(R.drawable.ic_checkbox_gray);
                    agreePageBinding.serviceTitle2Bt4.setBackgroundResource(R.drawable.ic_checkbox_gray);

                    agreePageBinding.serviceTitle3Bt1.setBackgroundResource(R.drawable.ic_checkbox_gray);
                    agreePageBinding.serviceTitle3Bt2.setBackgroundResource(R.drawable.ic_checkbox_gray);
                    agreePageBinding.serviceTitle3Bt3.setBackgroundResource(R.drawable.ic_checkbox_gray);
                }
                checkedNextBt();
                break;
            case R.id.serviceTitle1_bt1:
                if(selectBT[0] == 0) {
                    selectBT[0] = 1;
                    agreePageBinding.serviceTitle1Bt1.setBackgroundResource(R.drawable.ic_checkbox_red);
                }else{
                    selectBT[0] = 0;
                    agreePageBinding.serviceTitle1Bt1.setBackgroundResource(R.drawable.ic_checkbox_gray);
                }
                checkedNextBt();
                break;
            case R.id.serviceTitle1_bt2:
                if(selectBT[1] == 0) {
                    selectBT[1] = 1;
                    agreePageBinding.serviceTitle1Bt2.setBackgroundResource(R.drawable.ic_checkbox_red);
                }else{
                    selectBT[1] = 0;
                    agreePageBinding.serviceTitle1Bt2.setBackgroundResource(R.drawable.ic_checkbox_gray);
                }
                checkedNextBt();
                break;
            case R.id.serviceTitle2_bt1:
                if(selectBT[2] == 0) {
                    selectBT[2] = 1;
                    agreePageBinding.serviceTitle2Bt1.setBackgroundResource(R.drawable.ic_checkbox_red);
                }else{
                    selectBT[2] = 0;
                    agreePageBinding.serviceTitle2Bt1.setBackgroundResource(R.drawable.ic_checkbox_gray);
                }
                checkedNextBt();
                break;
            case R.id.serviceTitle2_bt2:
                if(selectBT[3] == 0) {
                    selectBT[3] = 1;
                    agreePageBinding.serviceTitle2Bt2.setBackgroundResource(R.drawable.ic_checkbox_red);
                }else{
                    selectBT[3] = 0;
                    agreePageBinding.serviceTitle2Bt2.setBackgroundResource(R.drawable.ic_checkbox_gray);
                }
                checkedNextBt();
                break;
            case R.id.serviceTitle2_bt3:
                if(selectBT[4] == 0) {
                    selectBT[4] = 1;
                    agreePageBinding.serviceTitle2Bt3.setBackgroundResource(R.drawable.ic_checkbox_red);
                }else{
                    selectBT[4] = 0;
                    agreePageBinding.serviceTitle2Bt3.setBackgroundResource(R.drawable.ic_checkbox_gray);
                }
                checkedNextBt();
                break;
            case R.id.serviceTitle2_bt4:
                if(selectBT[5] == 0) {
                    selectBT[5] = 1;
                    agreePageBinding.serviceTitle2Bt4.setBackgroundResource(R.drawable.ic_checkbox_red);
                }else{
                    selectBT[5] = 0;
                    agreePageBinding.serviceTitle2Bt4.setBackgroundResource(R.drawable.ic_checkbox_gray);
                }
                checkedNextBt();
                break;
            case R.id.serviceTitle3_bt1:
                if(nonSelectBt[0] == 0) {
                    nonSelectBt[0] = 1;
                    agreePageBinding.serviceTitle3Bt1.setBackgroundResource(R.drawable.ic_checkbox_red);
                }else{
                    nonSelectBt[0] = 0;
                    agreePageBinding.serviceTitle3Bt1.setBackgroundResource(R.drawable.ic_checkbox_gray);
                }
                checkedNextBt();
                break;
            case R.id.serviceTitle3_bt2:
                if(nonSelectBt[1] == 0) {
                    nonSelectBt[1] = 1;
                    agreePageBinding.serviceTitle3Bt2.setBackgroundResource(R.drawable.ic_checkbox_red);
                }else{
                    nonSelectBt[1] = 0;
                    agreePageBinding.serviceTitle3Bt2.setBackgroundResource(R.drawable.ic_checkbox_gray);
                }
                checkedNextBt();
                break;
            case R.id.serviceTitle3_bt3:
                if(nonSelectBt[2] == 0) {
                    nonSelectBt[2] = 1;
                    agreePageBinding.serviceTitle3Bt3.setBackgroundResource(R.drawable.ic_checkbox_red);
                }else{
                    nonSelectBt[2] = 0;
                    agreePageBinding.serviceTitle3Bt3.setBackgroundResource(R.drawable.ic_checkbox_gray);
                }
                checkedNextBt();
                break;

        }
    }
}
