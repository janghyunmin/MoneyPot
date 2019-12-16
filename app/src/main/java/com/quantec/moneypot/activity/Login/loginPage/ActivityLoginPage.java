package com.quantec.moneypot.activity.Login.loginPage;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.quantec.moneypot.activity.FIDO.ActivityReFidoResistor;
import com.quantec.moneypot.activity.Login.resist.ActivityRePhoneConfirm;
import com.quantec.moneypot.activity.Login.Model.dModel.LoginData;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelLoginData;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.dialog.DialogLoadingMakingPort;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.R;
import com.quantec.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLoginPage extends AppCompatActivity {

    String uid;
    TextView textId, joinBt, findPw;
    EditText textPw;

    private DialogLoadingMakingPort loadingCustomMakingPort;
    private MagicFIDOUtil mFidoUtil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mFidoUtil = new MagicFIDOUtil(this);

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

        uid = SharedPreferenceUtil.getInstance(ActivityLoginPage.this).getStringExtra("userId");

        if(uid == null || uid.equals("")){

            Intent intent = getIntent();
            uid = intent.getStringExtra("uid");
        }

        textId = findViewById(R.id.textId);
        joinBt = findViewById(R.id.joinBt);

        joinBt.setBackground(getResources().getDrawable(R.drawable.custom_bt_unselected_29dp));
        joinBt.setTextColor(getResources().getColor(R.color.c_cccccc));

        findPw = findViewById(R.id.findPw);
        textPw = findViewById(R.id.textPw);

        textId.setText("ID "+uid);

        textPw.addTextChangedListener(new passwordText());

        joinBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textPw.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "계정 정보가 없습니다.\n다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
                else{

                    LoginData loginData = new LoginData("", md5(textPw.getText().toString().trim()), uid);
                    Call<ModelLoginData> getReList = RetrofitClient.getInstance(ActivityLoginPage.this).getService().getLoginData("application/json",  loginData);
                    getReList.enqueue(new Callback<ModelLoginData>() {
                        @Override
                        public void onResponse(Call<ModelLoginData> call, Response<ModelLoginData> response) {
                            if (response.code() == 200) {

                                if(response.body().getStatus() == 200){

                                    SharedPreferenceUtil.getInstance(ActivityLoginPage.this).putUserId("userId", response.body().getContent().getUid());
                                    SharedPreferenceUtil.getInstance(ActivityLoginPage.this).putTokenA("aToken", response.headers().get("Authorization"));
                                    SharedPreferenceUtil.getInstance(ActivityLoginPage.this).putAuthCode("authCode", response.body().getContent().getAuthCode());
                                    /**
                                     *
                                     * 여기에서 FIDO 등록을 안햇을경우 다시 등록하는 화면으로 넘기는 분기처리 필요
                                     *
                                     */
                                    // FIDO FINGER 사용 가능 여부 체크
                                    if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE)){

                                        Intent intent = new Intent(ActivityLoginPage.this, ActivityReFidoResistor.class);
                                        Handler mHandler = new Handler();
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }, 100);

                                        Toast.makeText(getApplicationContext(), "파이도 재등록", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        nextMainPage();
                                    }
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "로그인 정보를 잘못 기입하셨습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelLoginData> call, Throwable t) {
                        }
                    });
                }
            }
        });

        findPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityLoginPage.this, ActivityRePhoneConfirm.class);
                intent.putExtra("passwordPage", "NotFido");
                startActivity(intent);
            }
        });

    } // onCreate 끝

    private class passwordText implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {

            if(textPw.getText().length() >= 8){
                joinBt.setEnabled(true);
                joinBt.setBackground(getResources().getDrawable(R.drawable.custom_bt_selected_29dp));
                joinBt.setTextColor(getResources().getColor(R.color.c_ffffff));
            }else{
                joinBt.setEnabled(false);
                joinBt.setBackground(getResources().getDrawable(R.drawable.custom_bt_unselected_29dp));
                joinBt.setTextColor(getResources().getColor(R.color.c_cccccc));
            }
        }
    }

    public void nextMainPage(){

        loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityLoginPage.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
        loadingCustomMakingPort.show();

        final Intent intent1 = new Intent(ActivityLoginPage.this, ActivityMain.class);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                loadingCustomMakingPort.dismiss();
                intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
                finish();
            }
        }, 1300);
    }

    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
