package com.quantec.moneypot.activity.MyInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.quantec.moneypot.activity.Login.Model.dModel.ModelAuthEmailReqDto;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelAuthEmail;
import com.quantec.moneypot.activity.buttondoublecheck.RxView;
import com.quantec.moneypot.dialog.DialogLoadingMakingPort;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityChangedEmail extends AppCompatActivity {

    ImageView backBt;
    TextView emailTitle, emailAuthBt, emailReBt, emailAuthBt2;
    EditText emailEditText, emailEditText2;

    private DialogLoadingMakingPort loadingCustomMakingPort;

    String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
    Matcher matcher;

    ConstraintLayout preLayout, nowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changed_email);

        // 스테이터스 바 색상 변경 -> 화이트
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

        emailReBt = findViewById(R.id.emailReBt);
        preLayout = findViewById(R.id.preLayout);
        preLayout.setVisibility(View.VISIBLE);
        nowLayout = findViewById(R.id.nowLayout);
        nowLayout.setVisibility(View.GONE);

        backBt = findViewById(R.id.backBt);
        emailTitle = findViewById(R.id.emailTitle);
        emailAuthBt = findViewById(R.id.emailAuthBt);
        emailEditText = findViewById(R.id.emailEditText);
        emailEditText2 = findViewById(R.id.emailEditText2);

        emailAuthBt2 = findViewById(R.id.emailAuthBt2);

        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() > 0){
                    emailAuthBt.setEnabled(true);
                    emailAuthBt.setBackgroundResource(R.drawable.custom_bt_selected_29dp);
                    emailAuthBt.setTextColor(getResources().getColor(R.color.c_ffffff));
                }
                else{
                    emailAuthBt.setEnabled(false);
                    emailAuthBt.setBackgroundResource(R.drawable.custom_bt_unselected_29dp);
                    emailAuthBt.setTextColor(getResources().getColor(R.color.c_cccccc));
                }

            }
        });

        emailEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() > 0){
                    emailAuthBt2.setEnabled(true);
                    emailAuthBt2.setBackgroundResource(R.drawable.custom_bt_selected_29dp);
                    emailAuthBt2.setTextColor(getResources().getColor(R.color.c_ffffff));
                }
                else{
                    emailAuthBt2.setEnabled(false);
                    emailAuthBt2.setBackgroundResource(R.drawable.custom_bt_unselected_29dp);
                    emailAuthBt2.setTextColor(getResources().getColor(R.color.c_cccccc));
                }

            }
        });

        RxView.clicks(emailAuthBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {
            matcher = Pattern.compile(regex).matcher(emailEditText.getText().toString());
            if(!matcher.matches()){
                Toast.makeText(getApplicationContext(), "잘못된 형식의 이메일 입니다.", Toast.LENGTH_SHORT).show();
            }else{

                loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityChangedEmail.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                loadingCustomMakingPort.show();

                ModelAuthEmailReqDto authEmailReqDto = new ModelAuthEmailReqDto("", emailEditText.getText().toString().trim(), "인증 메일 입니다.","");
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String jsonItentifyData = gson.toJson(authEmailReqDto).replace("\\n", "").replace(" ", "")
                        .replace("\\", "").replace("\"{", "{").replace("}\"", "}");

                Call<ModelAuthEmail> getRegChkData = RetrofitClient.getInstance(ActivityChangedEmail.this).getService().getSendAuthEmailData("application/json", jsonItentifyData);
                getRegChkData.enqueue(new Callback<ModelAuthEmail>() {
                    @Override
                    public void onResponse(Call<ModelAuthEmail> call, Response<ModelAuthEmail> response) {
                        if(response.code() == 200) {
                                loadingCustomMakingPort.dismiss();

                                preLayout.setVisibility(View.GONE);
                                nowLayout.setVisibility(View.VISIBLE);

                        }
                    }
                    @Override
                    public void onFailure(Call<ModelAuthEmail> call, Throwable t) {
                        loadingCustomMakingPort.dismiss();
                    }
                });
            }
        });

        //인증번호 완료 버튼
        RxView.clicks(emailAuthBt2).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {

        });

        //인증번호 재발송 버튼
        RxView.clicks(emailReBt).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(empty -> {

        });


        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
