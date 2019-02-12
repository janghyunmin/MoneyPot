package quantec.com.moneypot.Activity.Login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import permissions.dispatcher.NeedsPermission;
import quantec.com.moneypot.R;
import quantec.com.moneypot.Util.Permissions.PermissionsPhone;

public class ActivityLogin extends AppCompatActivity {

    /**
     *
     * 번호 권한은 온크리에이트에 두고 진행된 페이지에서 뒤로가기시 로그인페이지를 새로 불러주는 방식으로 퍼미션 체크를 다시 한다 ( 현재 미구현 )
     *
     */

    EditText activity_login_phoneEdittext;
    TextView activity_login_Bt, activity_login_phoneEdittext_noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity_login_phoneEdittext = findViewById(R.id.activity_login_phoneEdittext);
        activity_login_Bt = findViewById(R.id.activity_login_Bt);
        activity_login_phoneEdittext_noti = findViewById(R.id.activity_login_phoneEdittext_noti);
        activity_login_phoneEdittext_noti.setVisibility(View.GONE);

        PermissionsPhone.getPhoneState(ActivityLogin.this);

        activity_login_phoneEdittext_noti.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                activity_login_phoneEdittext_noti.setVisibility(View.GONE);
            }
        });

        activity_login_Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(activity_login_phoneEdittext.getText().length() >= 10) {
                     Intent intent = new Intent(ActivityLogin.this, ActivityAgreePage.class);
                     startActivity(intent);
                 }
                 else{
                     activity_login_phoneEdittext_noti.setVisibility(View.VISIBLE);
                 }

            }
        });


    }


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

}
