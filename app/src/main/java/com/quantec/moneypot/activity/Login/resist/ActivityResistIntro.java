package com.quantec.moneypot.activity.Login.resist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import permissions.dispatcher.NeedsPermission;
import com.quantec.moneypot.R;
import com.quantec.moneypot.util.Permissions.PermissionsPhone;
import com.quantec.moneypot.util.view.keyboardevent.KeyboardVisibilityEvent;
import com.quantec.moneypot.util.view.keyboardevent.KeyboardVisibilityEventListener;
import com.quantec.moneypot.util.view.keyboardevent.Unregistrar;

public class ActivityResistIntro extends AppCompatActivity{

    /**
     *
     * 번호 권한은 온크리에이트에 두고 진행된 페이지에서 뒤로가기시 로그인페이지를 새로 불러주는 방식으로 퍼미션 체크를 다시 한다
     *
     */
    EditText activity_login_phoneEdittext;
    TextView activity_login_Bt;
    ScrollView scrollView;
    Unregistrar unregistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

//        actList.add(this);

        scrollView = findViewById(R.id.scrollView);

        activity_login_Bt = findViewById(R.id.activity_login_Bt);
        activity_login_phoneEdittext = findViewById(R.id.activity_login_phoneEdittext);
        activity_login_phoneEdittext.addTextChangedListener(new hpNumberText());

        PermissionsPhone.getPhoneState(ActivityResistIntro.this);


        activity_login_Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(activity_login_phoneEdittext.getText().length() >= 10) {
                         Intent intent = new Intent(ActivityResistIntro.this, ActivityResistAgree.class);
                         intent.putExtra("passPhoneNum", activity_login_phoneEdittext.getText().toString());
                         startActivity(intent);
                         finish();
                 }
                 else{
                     Toast.makeText(getApplicationContext(), "잘못된 형식의 번호입니다.", Toast.LENGTH_SHORT).show();
                 }
            }
        });

        unregistrar = KeyboardVisibilityEvent.registerEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if(isOpen){
                    scrollView.post(new Runnable() {
                        @Override public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
            }
        });


    }//onCreate 끝

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsPhone.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_PHONE_STATE})
    public void getPhoneNumber(){
        TelephonyManager mgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String phoneNumber = mgr.getLine1Number();
        if(!TextUtils.isEmpty(phoneNumber)){
            phoneNumber = phoneNumber.replace("-","").replace("+82","0");
            activity_login_phoneEdittext.setText(phoneNumber);
        }
    }


    private class hpNumberText implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if(activity_login_phoneEdittext.getText().length() >= 10){
                activity_login_Bt.setEnabled(true);
                activity_login_Bt.setBackground(getResources().getDrawable(R.drawable.select_bt));
                activity_login_Bt.setTextColor(getResources().getColor(R.color.normal_title_color));
            }else{
                activity_login_Bt.setBackgroundColor(getResources().getColor(R.color.button_enable));
                activity_login_Bt.setTextColor(getResources().getColor(R.color.button_enable_text));
            }
        }
    }

}
