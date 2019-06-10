package quantec.com.moneypot.Activity.MyInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import quantec.com.moneypot.Activity.Intro.ActivityIntro;
import quantec.com.moneypot.Activity.Login.Model.dModel.LoginData;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelLoginData;
import quantec.com.moneypot.Dialog.DialogLoadingMakingPort;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ActivityAuthPasswordBinding;
import quantec.com.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAuthPassword extends AppCompatActivity implements View.OnClickListener {

    ActivityAuthPasswordBinding binding;
    String userId;
    int authCount = 1;

    private DialogLoadingMakingPort loadingCustomMakingPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth_password);

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

        userId = SharedPreferenceUtil.getInstance(ActivityAuthPassword.this).getStringExtra("userId");

        binding.backBt.setOnClickListener(this);
        binding.nextBt.setOnClickListener(this);

        binding.passwordEdit.addTextChangedListener(new passwordTextWatcher());
    }


    private class passwordTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {

            if(binding.passwordEdit.getText().toString().isEmpty()){
                binding.nextBt.setBackgroundColor(getResources().getColor(R.color.gray_brown_color));
                binding.nextBt.setEnabled(false);
            }else{
                binding.nextBt.setBackgroundColor(getResources().getColor(R.color.text_red_color));
                binding.nextBt.setEnabled(true);
            }
        }
    }

    private void chaeckedAuthPassword() {

        loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityAuthPassword.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
        loadingCustomMakingPort.show();

        if(authCount < 5){
            LoginData loginData = new LoginData("", md5(binding.passwordEdit.getText().toString().trim()), userId);
            Call<ModelLoginData> getReList = RetrofitClient.getInstance(ActivityAuthPassword.this).getService().getLoginData("application/json",  loginData);
            getReList.enqueue(new Callback<ModelLoginData>() {
                @Override
                public void onResponse(Call<ModelLoginData> call, Response<ModelLoginData> response) {
                    if (response.code() == 200) {

                        if(response.body().getStatus() == 200){

                            loadingCustomMakingPort.dismiss();

                            authCount = 0;

                            SharedPreferenceUtil.getInstance(ActivityAuthPassword.this).putTokenA("aToken", response.headers().get("Authorization"));
                            SharedPreferenceUtil.getInstance(ActivityAuthPassword.this).putAuthCode("authCode", response.body().getContent().getAuthCode());

                            Intent intent = new Intent(ActivityAuthPassword.this, ActivityChangedPw.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                    else {

                        Toast.makeText(ActivityAuthPassword.this, "로그인 정보를 잘못 기입하셨습니다.\n시도 횟수( "+authCount+"/ 5 )", Toast.LENGTH_SHORT).show();
                        authCount++;
                    }
                }
                @Override
                public void onFailure(Call<ModelLoginData> call, Throwable t) {
                    loadingCustomMakingPort.dismiss();
                }
            });

        }
        else{

            loadingCustomMakingPort.dismiss();
            Toast.makeText(ActivityAuthPassword.this, "로그인 횟수를 초과하였습니다.\n본인 인증을 다시해 주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActivityAuthPassword.this, ActivityIntro.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
//            activityFinish();
        }

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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.backBt:
                finish();
                break;

            case R.id.nextBt:

                if(binding.passwordEdit.getText().toString().isEmpty()){
                    binding.nextBt.setBackgroundColor(getResources().getColor(R.color.gray_brown_color));
                }else{
                    binding.nextBt.setBackgroundColor(getResources().getColor(R.color.text_red_color));
                    chaeckedAuthPassword();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        activityRemove();
//        Log.e("쌓인 액티비티","값 : "+ mainList.size());
    }


}
