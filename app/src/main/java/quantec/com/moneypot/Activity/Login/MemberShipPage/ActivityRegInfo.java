package quantec.com.moneypot.Activity.Login.MemberShipPage;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dream.magic.fido.rpsdk.callback.FIDOCallbackResult;
import com.dream.magic.fido.rpsdk.client.FIDORequestCode;
import com.dream.magic.fido.rpsdk.client.FidoResult;
import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import permissions.dispatcher.NeedsPermission;
import quantec.com.moneypot.Activity.Intro.ModelVerifiedFido;
import quantec.com.moneypot.Activity.Login.Model.dModel.FidoReq;
import quantec.com.moneypot.Activity.Login.Model.dModel.FidoTokenData;
import quantec.com.moneypot.Activity.Login.Model.dModel.UserInfo;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelUserInfo;
import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.Dialog.DialogClosedSMS;
import quantec.com.moneypot.Dialog.DialogFidoCancle;
import quantec.com.moneypot.Dialog.DialogFidoFinger;
import quantec.com.moneypot.Dialog.DialogLoadingMakingPort;
import quantec.com.moneypot.FIDO.FIDORegistration;
import quantec.com.moneypot.FIDO.PropertyManager;
import quantec.com.moneypot.FIDO.PropertyManager2;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ActivityRegInfoBinding;
import quantec.com.moneypot.util.Permissions.PermissionFido;
import quantec.com.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegInfo extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    ActivityRegInfoBinding regInfoBinding;
    Matcher matcher, matcher2;

    String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{8,15}$";
    String pwPattern2 = "(.)\\1\\1";
    String regUid, regCid, authCode, regToken, hpNumber, userName;

    DialogClosedSMS dialogClosedSMS;
    DialogFidoCancle dialogFidoCancle;
    private DialogLoadingMakingPort loadingCustomMakingPort;

    //FIDO관련
    private MagicFIDOUtil mFidoUtil = null;
    // RP SDK 를 사용하기 위한 객체
    private FIDORegistration reg = null;
    private FIDORegistration reg2 = null;
    private final int ALERT_REQUEST = 0xA3;

    private DialogFidoFinger dialogFidoFinger;
    String devId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        regInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_reg_info);

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

        regInfoBinding.regInfoPwEdit.addTextChangedListener(new MyTextWatcher(regInfoBinding.regInfoPwEdit));
        regInfoBinding.regInfoRPwEditText.addTextChangedListener(new MyTextWatcher(regInfoBinding.regInfoRPwEditText));

        regInfoBinding.regInfoPwEdit.setOnClickListener(this);
        regInfoBinding.regInfoRPwEditText.setOnClickListener(this);
        regInfoBinding.nextBt.setOnClickListener(this);
        regInfoBinding.backBt.setOnClickListener(this);
        regInfoBinding.regInfoPwEditDeleteBt.setOnClickListener(this);
        regInfoBinding.regInfoRPwEditTextDeleteBt.setOnClickListener(this);

        regInfoBinding.regInfoPwEdit.setOnFocusChangeListener(this);
        regInfoBinding.regInfoRPwEditText.setOnFocusChangeListener(this);

        Intent intent = getIntent();
        regUid = intent.getStringExtra("uid");
        regCid = intent.getStringExtra("cid");
        hpNumber = intent.getStringExtra("hpNumber");
        userName = intent.getStringExtra("userName");

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ActivityRegInfo.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
            }
        });

    }//onCreate 끝

    private void resistedFido(){

        loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityRegInfo.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
        loadingCustomMakingPort.show();

        PropertyManager.load(this);
        PropertyManager2.load(this);
        mFidoUtil = new MagicFIDOUtil(this);

        Hashtable<String, Object> authUICustom = new Hashtable<String, Object>();
        authUICustom.put("text1", "간편 비밀번호 등록");
        authUICustom.put("text2", "보안키패드 작동중...");

        Hashtable<String, Object> settingValue = new Hashtable<String, Object>();
        settingValue.put("passcode", authUICustom);

        mFidoUtil.setCustomUIValues(settingValue);

        Log.e("패스코드 등록여부", "등록 : "+  mFidoUtil.getLocalVerifyState(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE).getErrorCode());

        if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE)){
            reg = new FIDORegistration(ActivityRegInfo.this,  fidoResult);
            reg.registFIDO(regUid);

        }
        else{

            Intent intent = new Intent(ActivityRegInfo.this, ActivityMain.class);
            startActivity(intent);
//            actFinish();

            Toast.makeText(ActivityRegInfo.this, "바로 로그인", Toast.LENGTH_SHORT).show();
        }
    }

    private FIDOCallbackResult fidoResult = new FIDOCallbackResult() {

        @Override
        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {

            if (requestCode == FIDORequestCode.REQUEST_CODE_REGIST) {
                if (fidoResult.getErrorCode() == FidoResult.RESULT_SUCCESS) {
                    // 에러코드 0: 등록 성공
                    String token = reg.getToken();
                    try {
                        devId = JTdecoded(token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    verrifiedPasscodeUser(token);

                } else {

                    dialogFidoCancle = new DialogFidoCancle(ActivityRegInfo.this, fidoCancle);
                    dialogFidoCancle.show();

//                    // 실패 사유
//                    switch (fidoResult.getErrorCode()) {
//                        case FidoResult.RESULT_USER_CANCEL:
//                            break;
//                        // 에러코드 999: 사용자 취소
//                        case FidoResult.ERROR_JSON_PARSER:
//                            loadingCustomMakingPort.dismiss();
//                            // 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
//                        case FidoResult.ERROR_CANNOT_USE_FIDO:
//                            // 에러코드 1007: 지원하지 않는 단말기
//                        case FidoResult.ERROR_UNTRUSTED_FACET_ID:
//                            // 에러코드 2001: -등록되지 않은 3rd App으로 진행
//                            //			   -FIDO Server에 FacetID 를 등록해야 함.
//                        case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
//                            // 에러코드 2002: 적합한 인증장치가 없음
//                        case FidoResult.ERROR_NETWORK_STATE:
//                            // 에러코드 4001: -네트워크 오류
//                            //			   -fidoResult.getDescription() 을 통하여 자세한 오류 내용을 알 수 있음
//                        case FidoResult.PW_NOT_MATCH_BIO_FINGER:
//                            // 에러코드 5000: 지문 인증 실패
//                        case FidoResult.ERROR_UNKNOWN:
//                            // 에러코드 9999: FIDO 로직 수행 중, 알수 없는 에러 발생
//                            showDialog("FAIL", fidoResult.getDescription() + "(" + fidoResult.getErrorCode() + ")");
//                            break;
//                        case FidoResult.RESULT_USER_SELECT_REREG:
//                            // 에러코드 980: 사용자가 재등록 버튼 클릭
//                            Toast.makeText(ActivityRegInfo.this, "사용자가 재등록 버튼 클릭" , Toast.LENGTH_SHORT).show();
//                            break;
//                        case FidoResult.ERROR_ALREADY_REGI_USER:
//                            // 에러코드 1008: -이미 등록 된 사용자
//                            //             -해지 후 재등록 요청을 해야 함.
//                            showDialog("FAIL", "이미 등록 된 사용자입니다. 해지 후 재등록 해주세요.(" + fidoResult.getErrorCode() + ")");
//                            break;
//                        default:
//                            showDialog("FAIL", fidoResult.getDescription() + "(" + fidoResult.getErrorCode() + ")");
//                            break;
//                    }
                }
            }
        }
    };

    public String JTdecoded(String JWTEncoded) throws Exception {
        String[] split = JWTEncoded.split("\\.");
        Gson gson = new Gson();
        FidoTokenData data = gson.fromJson(getJson(split[1]), FidoTokenData.class);
        return data.deviceid;
    }

    private  String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decode =  android.util.Base64.decode(strEncoded,  Base64.URL_SAFE);
        return new String(decode, "utf-8");
    }

    private FIDOCallbackResult fidoResult2 = new FIDOCallbackResult() {
        @Override
        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {

            if (requestCode == FIDORequestCode.REQUEST_CODE_REGIST) {
                if (fidoResult.getErrorCode() == FidoResult.RESULT_SUCCESS) {
                    // 에러코드 0: 등록 성공
                    String token = reg2.getToken();
                    try {
                        devId = JTdecoded(token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    verrifiedFingereUser(token);
                } else {

//                    Intent intent = new Intent(ActivityRegInfo.this, MainActivity.class);
//                    startActivity(intent);
//                    actFinish();

//                    // 실패 사유
//                    switch (fidoResult.getErrorCode()) {
//                        case FidoResult.RESULT_USER_CANCEL:
//                            // 에러코드 999: 사용자 취소
//                            loadingCustomMakingPort.dismiss();
//                        case FidoResult.ERROR_JSON_PARSER:
//                            // 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
//                        case FidoResult.ERROR_CANNOT_USE_FIDO:
//                            // 에러코드 1007: 지원하지 않는 단말기
//                        case FidoResult.ERROR_UNTRUSTED_FACET_ID:
//                            // 에러코드 2001: -등록되지 않은 3rd App으로 진행
//                            //			   -FIDO Server에 FacetID 를 등록해야 함.
//                        case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
//                            // 에러코드 2002: 적합한 인증장치가 없음
//                        case FidoResult.ERROR_NETWORK_STATE:
//                            // 에러코드 4001: -네트워크 오류
//                            //			   -fidoResult.getDescription() 을 통하여 자세한 오류 내용을 알 수 있음
//                        case FidoResult.PW_NOT_MATCH_BIO_FINGER:
//                            // 에러코드 5000: 지문 인증 실패
//                        case FidoResult.ERROR_UNKNOWN:
//                            // 에러코드 9999: FIDO 로직 수행 중, 알수 없는 에러 발생
//                            showDialog("FAIL", fidoResult.getDescription() + "(" + fidoResult.getErrorCode() + ")");
//                            break;
//                        case FidoResult.RESULT_USER_SELECT_REREG:
//                            // 에러코드 980: 사용자가 재등록 버튼 클릭
//                            Toast.makeText(ActivityRegInfo.this, "사용자가 재등록 버튼 클릭" , Toast.LENGTH_SHORT).show();
//                            break;
//                        case FidoResult.ERROR_ALREADY_REGI_USER:
//                            // 에러코드 1008: -이미 등록 된 사용자
//                            //             -해지 후 재등록 요청을 해야 함.
//                            showDialog("FAIL", "이미 등록 된 사용자입니다. 해지 후 재등록 해주세요.(" + fidoResult.getErrorCode() + ")");
//                            break;
//                        default:
//                            showDialog("FAIL", fidoResult.getDescription() + "(" + fidoResult.getErrorCode() + ")");
//                            break;
//                    }
                }
            }
        }
    };


    private void verrifiedPasscodeUser(String token){

        FidoReq fidoReq = new FidoReq(authCode, devId, "", "PASSCODE", "", token, "R", regUid);
        Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityRegInfo.this).getService().getVerifyData("application/json",  fidoReq);
        getReList.enqueue(new Callback<ModelVerifiedFido>() {
            @Override
            public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                if (response.code() == 200) {

                    authCode = response.body().getContent().getAuthCode();

                    SharedPreferenceUtil.getInstance(ActivityRegInfo.this).putTokenA("aToken", response.headers().get("Authorization"));
                    SharedPreferenceUtil.getInstance(ActivityRegInfo.this).putAuthCode("authCode", authCode);
                    SharedPreferenceUtil.getInstance(ActivityRegInfo.this).putFingerState("fingerState", false);

                    if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_FINGERPRINT_TYPE)) {

                        dialogFidoFinger = new DialogFidoFinger(ActivityRegInfo.this, fingerResist, fingerCancle);
                        dialogFidoFinger.show();

                    }else{
                        Toast.makeText(ActivityRegInfo.this, "지문없이 메인으로 이동" , Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ActivityRegInfo.this, ActivityMain.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
//                        actFinish();

                    }

                }else{
                    mFidoUtil.resetUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, fidoCallback);
                }
            }
            @Override
            public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
                mFidoUtil.resetUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, fidoCallback);
            }
        });
    }

    private void verrifiedFingereUser(String token){

        FidoReq fidoReq = new FidoReq(authCode, devId, "", "FINGER", "", token, "R", regUid);
        Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityRegInfo.this).getService().getVerifyData("application/json",  fidoReq);
        getReList.enqueue(new Callback<ModelVerifiedFido>() {
            @Override
            public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                if (response.code() == 200) {

                    SharedPreferenceUtil.getInstance(ActivityRegInfo.this).putTokenA("aToken", response.headers().get("Authorization"));
                    SharedPreferenceUtil.getInstance(ActivityRegInfo.this).putAuthCode("authCode", response.body().getContent().getAuthCode());
                    SharedPreferenceUtil.getInstance(ActivityRegInfo.this).putFingerState("fingerState", true);

                    Intent intent = new Intent(ActivityRegInfo.this, ActivityMain.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
//                    actFinish();
                }else{
                    mFidoUtil.resetUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, fidoCallback);
                }
            }
            @Override
            public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
                mFidoUtil.resetUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, fidoCallback);
            }
        });
    }

    private FIDOCallbackResult fidoCallback = new FIDOCallbackResult() {
        @Override
        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {
            if(requestCode == FIDORequestCode.REQUEST_CODE_DEREG){
            }
        }
    };

    //FIDO 등록 취소
    private View.OnClickListener fidoCancle = new View.OnClickListener() {
        public void onClick(View v) {
            dialogFidoCancle.dismiss();
//            actFinish();
        }
    };

    //지문등록 확인
    private View.OnClickListener fingerResist = new View.OnClickListener() {
        public void onClick(View v) {
            dialogFidoFinger.dismiss();

            reg2 = new FIDORegistration(fidoResult2, ActivityRegInfo.this);

            if (regUid.length() < 1) {
                Toast.makeText(ActivityRegInfo.this, "Please, Fill out the UserID.", Toast.LENGTH_LONG).show();
                return;
            }
            // FIDO
            reg2.registFIDO(regUid);
        }
    };

    //지문등록 취소
    private View.OnClickListener fingerCancle = new View.OnClickListener() {
        public void onClick(View v) {
            dialogFidoFinger.dismiss();
//            Intent intent = new Intent(ActivityRegInfo.this, MainActivity.class);
//            startActivity(intent);
//            actFinish();

            loadingCustomMakingPort.dismiss();
        }
    };


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    //뒤로가기 가입 절차 확인 버튼
    private View.OnClickListener backOkListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogClosedSMS.dismiss();
            Intent intent = new Intent(ActivityRegInfo.this, ActivityMemberShipMain.class);
            startActivity(intent);
//            actFinish();
            overridePendingTransition(R.anim.leftin_activity,R.anim.rightout_activity);
        }
    };

    //뒤로가기 가입 절차 취소 버튼
    private View.OnClickListener backCancleListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogClosedSMS.dismiss();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionFido.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.READ_PHONE_STATE})
    public void getFido(){

                if(isValidPassword2(regInfoBinding.regInfoPwEdit.getText().toString().trim())){
                    if(isValidChkPw(regInfoBinding.regInfoPwEdit.getText().toString().trim(), regInfoBinding.regInfoRPwEditText.getText().toString().trim())){
                        getAppInit();
                    }else{
                        requestFocus(regInfoBinding.regInfoRPwEditText);
                    }
                }else{
                    requestFocus(regInfoBinding.regInfoPwEdit);
                }
    }

    public void getReFido(){
        new AlertDialog.Builder(this)
                .setTitle("권한 설정")
                .setMessage("진행을 위해서 전화 권한을 설정해 줘야 합니다.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .create()
                .show();
    }

    private boolean isValidPassword2(String password){

        matcher = Pattern.compile(pwPattern).matcher(password);
        matcher2 = Pattern.compile(pwPattern2).matcher(password);

        if(!matcher.matches()) {
            regInfoBinding.regInfoPwDesc.setText("영문,숫자,특수문자를 포함하여 8글자 이상.");
            regInfoBinding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_red_color));
            return false;
        }
        else {
            if(matcher2.find()){
                regInfoBinding.regInfoPwDesc.setText("3자리 이상의 연속된 같은 문자/숫자를 사용할 수 없습니다.");
                regInfoBinding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_red_color));
                return false;
            }
            else{

                    if(password.contains(" ")){
                        regInfoBinding.regInfoPwDesc.setText("비밀번호에 띄어쓰기를 포함할 수 없습니다.");
                        regInfoBinding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_red_color));
                        return false;
                    }
                    else{
                        regInfoBinding.regInfoPwDesc.setText("영문,숫자,특수문자를 포함하여 8글자 이상.");
                        regInfoBinding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_gray_color));
                        return true;
                    }
            }
        }
    }

    // 비밀번호 불일치 여부
    private boolean isValidChkPw(String password, String rePassword){
        if(password.trim().equals(rePassword.trim())){
            regInfoBinding.regInfoRPwDesc.setVisibility(View.INVISIBLE);
            return true;
        }else{
            regInfoBinding.regInfoRPwDesc.setVisibility(View.VISIBLE);
            return false;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBt:
                PermissionFido.getFidoState(ActivityRegInfo.this);
                break;
            case R.id.backBt:
                dialogClosedSMS = new DialogClosedSMS(ActivityRegInfo.this, backCancleListener, backOkListener);
                dialogClosedSMS.show();
                break;
            case R.id.regInfoPw_edit_deleteBt:
                regInfoBinding.regInfoPwEdit.setText("");
                break;
            case R.id.regInfoRPw_editText_deleteBt:
                regInfoBinding.regInfoRPwEditText.setText("");
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {

            case R.id.regInfoPw_edit:
                isVisibledDeleteBt(regInfoBinding.regInfoPwEdit.getText().toString().trim(), regInfoBinding.regInfoRPwEditText.getText().toString().trim());
                break;
            case R.id.regInfoRPw_editText:
                isVisibledDeleteBt(regInfoBinding.regInfoPwEdit.getText().toString().trim(), regInfoBinding.regInfoRPwEditText.getText().toString().trim());
                break;
        }
    }

    // 문자 바로 삭제 버튼 비지블 인비지블
    private void isVisibledDeleteBt(String password, String rePassword){
        if(password.isEmpty()){
            regInfoBinding.regInfoPwEditDeleteBt.setVisibility(View.INVISIBLE);
            if(rePassword.isEmpty()){
                regInfoBinding.regInfoRPwEditTextDeleteBt.setVisibility(View.INVISIBLE);
            }else{
                regInfoBinding.regInfoRPwEditTextDeleteBt.setVisibility(View.VISIBLE);
            }
        }else{
            regInfoBinding.regInfoPwEditDeleteBt.setVisibility(View.VISIBLE);
            if(rePassword.isEmpty()){
                regInfoBinding.regInfoRPwEditTextDeleteBt.setVisibility(View.INVISIBLE);
            }else{
                regInfoBinding.regInfoRPwEditTextDeleteBt.setVisibility(View.VISIBLE);
            }
        }
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.regInfoPw_edit:
                    if(!regInfoBinding.regInfoPwEdit.getText().toString().trim().isEmpty()) {
                        isValidPassword2(regInfoBinding.regInfoPwEdit.getText().toString().trim());
                        if(!regInfoBinding.regInfoRPwEditText.getText().toString().trim().isEmpty()){
                            isValidChkPw(regInfoBinding.regInfoPwEdit.getText().toString().trim(), regInfoBinding.regInfoRPwEditText.getText().toString().trim());
                        }
                        isVisibledDeleteBt(regInfoBinding.regInfoPwEdit.getText().toString().trim(), regInfoBinding.regInfoRPwEditText.getText().toString().trim());
                    }else{
                        isVisibledDeleteBt(regInfoBinding.regInfoPwEdit.getText().toString().trim(), regInfoBinding.regInfoRPwEditText.getText().toString().trim());
                    }
                    break;
                case R.id.regInfoRPw_editText:

                    if(!regInfoBinding.regInfoRPwEditText.getText().toString().trim().isEmpty()){
                        isValidChkPw(regInfoBinding.regInfoPwEdit.getText().toString().trim(), regInfoBinding.regInfoRPwEditText.getText().toString().trim());
                        isVisibledDeleteBt(regInfoBinding.regInfoPwEdit.getText().toString().trim(), regInfoBinding.regInfoRPwEditText.getText().toString().trim());
                    }
                    else{
                        isVisibledDeleteBt(regInfoBinding.regInfoPwEdit.getText().toString().trim(), regInfoBinding.regInfoRPwEditText.getText().toString().trim());
                    }
                    break;
            }
        }
    }

    private void getAppInit(){

        confirmedUserInfo(regCid, regUid, regInfoBinding.regInfoPwEdit.getText().toString().trim(), hpNumber, userName);
    }




    private void confirmedUserInfo(String cid, String userId, String Password, String hpNumber, String userName){

        String md5ToPassWord = md5(Password);

        UserInfo userInfo = new UserInfo(10, "", cid,"", hpNumber, md5ToPassWord, userId, userName);

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

                        SharedPreferenceUtil.getInstance(ActivityRegInfo.this).putUserId("userId", response.body().getContent().getUid());
                        SharedPreferenceUtil.getInstance(ActivityRegInfo.this).putTokenA("aToken", regToken);
                        SharedPreferenceUtil.getInstance(ActivityRegInfo.this).putAuthCode("authCode", authCode);

                        resistedFido();

                    }
                }
            }
            @Override
            public void onFailure(Call<ModelUserInfo> call, Throwable t) {
            }
        });
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
    public void onBackPressed() {
        Intent intent = new Intent(ActivityRegInfo.this, ActivityMemberShipMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loadingCustomMakingPort != null){
            loadingCustomMakingPort.dismiss();
        }
    }
}