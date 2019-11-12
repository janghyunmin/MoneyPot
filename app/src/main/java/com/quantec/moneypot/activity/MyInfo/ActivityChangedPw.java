package com.quantec.moneypot.activity.MyInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.quantec.moneypot.activity.Login.Model.dModel.UserInfo;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelUserInfo;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.R;
import com.quantec.moneypot.databinding.ActivityChangedPwBinding;
import com.quantec.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityChangedPw extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    ActivityChangedPwBinding binding;

    Matcher matcher, matcher2;

    String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{8,15}$";
    String pwPattern2 = "(.)\\1\\1";
    String userId, userCid, authCode, regToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_changed_pw);

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

        userId = SharedPreferenceUtil.getInstance(ActivityChangedPw.this).getStringExtra("userId");
        userCid = SharedPreferenceUtil.getInstance(ActivityChangedPw.this).getStringExtra("userCid");

        binding.nextBt.setOnClickListener(this);
        binding.backBt.setOnClickListener(this);

        binding.regInfoPwEdit.addTextChangedListener(new passwordTextWatcher(binding.regInfoPwEdit));
        binding.regInfoRPwEditText.addTextChangedListener(new passwordTextWatcher(binding.regInfoRPwEditText));

        binding.regInfoPwEdit.setOnFocusChangeListener(this);
        binding.regInfoRPwEditText.setOnFocusChangeListener(this);

    }

    private void confirmedUserInfo(String cid, String userId, String Password){

        String md5ToPassWord = md5(Password);
        UserInfo userInfo = new UserInfo(10, "", cid,"", "", md5ToPassWord, userId, "");
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonItentifyData = gson.toJson(userInfo).replace("\\n", "").replace(" ", "")
                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
        Call<ModelUserInfo> getReList = RetrofitClient.getInstance().getService().getConfirmedUserInfo("plain/text", jsonItentifyData);
        getReList.enqueue(new Callback<ModelUserInfo>() {
            @Override
            public void onResponse(Call<ModelUserInfo> call, Response<ModelUserInfo> response) {
                if (response.code() == 200) {

                    if(response.body().getStatus() == 200){

                        authCode = response.body().getContent().getAuthCode();
                        regToken = response.headers().get("Authorization");

                        SharedPreferenceUtil.getInstance(ActivityChangedPw.this).putTokenA("aToken", regToken);
                        SharedPreferenceUtil.getInstance(ActivityChangedPw.this).putAuthCode("authCode", authCode);

                        Toast.makeText(ActivityChangedPw.this, "비밀번호 변경 완료", Toast.LENGTH_SHORT).show();

                        finish();
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelUserInfo> call, Throwable t) {
            }
        });
    }

    private boolean isValidPassword2(String password){

        matcher = Pattern.compile(pwPattern).matcher(password);
        matcher2 = Pattern.compile(pwPattern2).matcher(password);

        if(!matcher.matches()) {
            binding.regInfoPwDesc.setText("영문,숫자,특수문자를 포함하여 8글자 이상.");
            binding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_red_color));
            return false;
        }
        else {
            if(matcher2.find()){
                binding.regInfoPwDesc.setText("3자리 이상의 연속된 같은 문자/숫자를 사용할 수 없습니다.");
                binding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_red_color));
                return false;
            }
            else{

                if(password.contains(" ")){
                    binding.regInfoPwDesc.setText("비밀번호에 띄어쓰기를 포함할 수 없습니다.");
                    binding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_red_color));
                    return false;
                }
                else{
                    binding.regInfoPwDesc.setText("영문,숫자,특수문자를 포함하여 8글자 이상.");
                    binding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_gray_color));
                    return true;
                }
            }
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


    // 비밀번호 불일치 여부
    private boolean isValidChkPw(String password, String rePassword){
        if(password.trim().equals(rePassword.trim())){
            binding.regInfoRPwDesc.setVisibility(View.INVISIBLE);
            return true;
        }else{
            binding.regInfoRPwDesc.setVisibility(View.VISIBLE);
            return false;
        }
    }

    // 문자 바로 삭제 버튼 비지블 인비지블
    private void isVisibledDeleteBt(String password, String rePassword){
        if(password.isEmpty()){
            binding.regInfoPwEditDeleteBt.setVisibility(View.INVISIBLE);
            if(rePassword.isEmpty()){
                binding.regInfoRPwEditTextDeleteBt.setVisibility(View.INVISIBLE);
            }else{
                binding.regInfoRPwEditTextDeleteBt.setVisibility(View.VISIBLE);
            }
        }else{
            binding.regInfoPwEditDeleteBt.setVisibility(View.VISIBLE);
            if(rePassword.isEmpty()){
                binding.regInfoRPwEditTextDeleteBt.setVisibility(View.INVISIBLE);
            }else{
                binding.regInfoRPwEditTextDeleteBt.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {

            case R.id.regInfoPw_edit:
                isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
                break;
            case R.id.regInfoRPw_editText:
                isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
                break;
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBt:
                if(isValidPassword2(binding.regInfoPwEdit.getText().toString().trim())){
                    if(isValidChkPw(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim())){
                        confirmedUserInfo(userCid, userId, binding.regInfoPwEdit.getText().toString().trim());
                    }else{
                        requestFocus(binding.regInfoRPwEditText);
                    }
                }else{
                    requestFocus(binding.regInfoPwEdit);
                }
                break;
            case R.id.backBt:
                finish();
                break;
            case R.id.regInfoPw_edit_deleteBt:
                binding.regInfoPwEdit.setText("");
                break;
            case R.id.regInfoRPw_editText_deleteBt:
                binding.regInfoRPwEditText.setText("");
                break;
        }
    }

    private class passwordTextWatcher implements TextWatcher {
        private View view;
        private passwordTextWatcher(View view) {
            this.view = view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.regInfoPw_edit:
                    if(!binding.regInfoPwEdit.getText().toString().trim().isEmpty()) {
                        isValidPassword2(binding.regInfoPwEdit.getText().toString().trim());
                        if(!binding.regInfoRPwEditText.getText().toString().trim().isEmpty()){
                            isValidChkPw(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
                        }
                        isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
                    }else{
                        isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
                    }
                    break;
                case R.id.regInfoRPw_editText:

                    if(!binding.regInfoRPwEditText.getText().toString().trim().isEmpty()){

                        binding.nextBt.setBackgroundColor(getResources().getColor(R.color.text_red_color));
                        binding.nextBt.setEnabled(true);

                        isValidChkPw(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
                        isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
                    }
                    else{
                        binding.nextBt.setBackgroundColor(getResources().getColor(R.color.gray_brown_color));
                        binding.nextBt.setEnabled(false);

                        isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
                    }
                    break;
            }
        }
    }
}
