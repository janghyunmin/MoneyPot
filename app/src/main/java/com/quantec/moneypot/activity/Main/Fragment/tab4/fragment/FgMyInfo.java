package com.quantec.moneypot.activity.Main.Fragment.tab4.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.dream.magic.fido.rpsdk.callback.FIDOCallbackResult;
import com.dream.magic.fido.rpsdk.client.FIDORequestCode;
import com.dream.magic.fido.rpsdk.client.FIDO_UI_TYPE;
import com.dream.magic.fido.rpsdk.client.FidoResult;
import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quantec.moneypot.R;
import com.quantec.moneypot.activity.FIDO.LoginFidoTokenData;
import com.quantec.moneypot.activity.Intro.ModelVerifiedFido;
import com.quantec.moneypot.activity.Login.Model.dModel.FidoReq;
import com.quantec.moneypot.activity.Login.Model.dModel.ModelFidoauthReqDto;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelFidoAuthCode;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelFlushAuth;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.activity.MyInfo.ActivityAuthPassword;
import com.quantec.moneypot.activity.MyInfo.ActivityMyInfo;
import com.quantec.moneypot.activity.MyInfo.ActivityPhoneEmail;
import com.quantec.moneypot.datamodel.nmodel.ModelAccountExist;
import com.quantec.moneypot.datamodel.nmodel.ModelApiToken;
import com.quantec.moneypot.dialog.DialogLoadingMakingPort;
import com.quantec.moneypot.fido.FIDONotAuthenticatioin;
import com.quantec.moneypot.fido.FIDORegistration;
import com.quantec.moneypot.fido.PropertyManager;
import com.quantec.moneypot.fido.PropertyManager2;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.util.SharedPreference.SharedPreferenceUtil;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FgMyInfo extends Fragment implements View.OnClickListener{

    private ActivityMain activityMain;
    Switch switchView;

    private FIDORegistration reg2 = null;

    private MagicFIDOUtil mFidoUtil = null;
    private FIDONotAuthenticatioin mAuth = null;
    String userId, devId, authCode;

    LinearLayout email_phoneBt, passwordBt, easyPasswordBt;
    ConstraintLayout fingerBt;

    private DialogLoadingMakingPort loadingCustomMakingPort;

    public FgMyInfo() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tab4_myinfo, container, false);

        initializeViews();

        switchView = view.findViewById(R.id.switchView);
        email_phoneBt = view.findViewById(R.id.email_phoneBt);
        passwordBt = view.findViewById(R.id.passwordBt);
        easyPasswordBt = view.findViewById(R.id.easyPasswordBt);
        fingerBt = view.findViewById(R.id.fingerBt);

        if(SharedPreferenceUtil.getInstance(activityMain).getStateExtra("fingerState")){
            switchView.setChecked(true);
        }
        else{
            switchView.setChecked(false);
        }

        PropertyManager.load(activityMain);
        PropertyManager2.load(activityMain);
        userId = SharedPreferenceUtil.getInstance(activityMain).getStringExtra("userId");
        mFidoUtil = new MagicFIDOUtil(activityMain);

        checkedUseFido();

        email_phoneBt.setOnClickListener(this);
        passwordBt.setOnClickListener(this);
        easyPasswordBt.setOnClickListener(this);

        return view;
    }

    private void initializeViews(){
        activityMain = (ActivityMain) getActivity();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityMain) {
            activityMain = (ActivityMain) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                authCode = SharedPreferenceUtil.getInstance(activityMain).getStringExtra("authCode");

                ModelFidoauthReqDto fidoAuthReqDto = new ModelFidoauthReqDto(authCode, "FINGER", userId);
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String data = gson.toJson(fidoAuthReqDto).replace("\\n", "").replace(" ", "")
                        .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
                Call<ModelFlushAuth> tt = RetrofitClient.getInstance(activityMain).getService().getFlushAuthData("application/json", data);
                tt.enqueue(new Callback<ModelFlushAuth>() {
                    @Override
                    public void onResponse(Call<ModelFlushAuth> call, Response<ModelFlushAuth> response) {
                        if(response.code() == 200) {
                            SharedPreferenceUtil.getInstance(activityMain).putAuthCode("authCode", response.body().getContent().getAuthCode());
                            SharedPreferenceUtil.getInstance(activityMain).putTokenA("aToken", response.headers().get("Authorization"));

                            if(isChecked){

                                loadingCustomMakingPort = new DialogLoadingMakingPort(activityMain, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                                loadingCustomMakingPort.show();

                                Hashtable<String, Object> authUICustom = new Hashtable<String, Object>();
                                authUICustom.put("text1", "지문 로그인 활성화");
                                authUICustom.put("text2", "보안키패드 작동중...");

                                Hashtable<String, Object> settingValue = new Hashtable<String, Object>();
                                settingValue.put("passcode", authUICustom);
                                mFidoUtil.setCustomUIValues(settingValue);
                                // RP SDK 생성
                                mAuth = new FIDONotAuthenticatioin(activityMain, fidoCallbackResult);
                                mAuth.startAuthentication(userId);

                            }
                            else{
                                SharedPreferenceUtil.getInstance(activityMain).putFingerState("fingerState", false);
                                Toast.makeText(activityMain, "지문 미사용", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    @Override
                    public void onFailure(Call<ModelFlushAuth> call, Throwable t) {
                        loadingCustomMakingPort.dismiss();
                    }
                });
            }
        });


//        Call<ModelAccountExist> getReList = RetrofitClient.getInstance(activityMain).getService().getAccountExist("application/json");
//        getReList.enqueue(new Callback<ModelAccountExist>() {
//            @Override
//            public void onResponse(Call<ModelAccountExist> call, Response<ModelAccountExist> response) {
//                if (response.code() == 200) {
//                    if(response.body().getStatus() == 200){
//                        if(response.body().getContent().getResultCode().equals("Z00001")){
//                            //계좌 보유
//                        }else{
//                            //계좌 미보유
//
//                            if(response.body().getContent().getAccount() == null || response.body().getContent().getAccount().isEmpty()){
//                                //신규계좌 개설 가입
//
//                                    Call<ModelApiToken> getReList = RetrofitClient.getInstance(activityMain).getService().getApiToken("application/json");
//                                    getReList.enqueue(new Callback<ModelApiToken>() {
//                                        @Override
//                                        public void onResponse(Call<ModelApiToken> call, Response<ModelApiToken> response) {
//                                            if (response.code() == 200) {
//                                                    Log.e("받은값", "어카운트 토큰 : "+response.body().getContent().getApiToken());
//
//                                                    Call<Object> getReList = RetrofitClient.getInstance(activityMain).getService().setAccountStatus("application/json", 0, "100004");
//                                                    getReList.enqueue(new Callback<Object>() {
//                                                        @Override
//                                                        public void onResponse(Call<Object> call, Response<Object> response) {
//                                                            if (response.code() == 200) {
//
//                                                            }
//                                                        }
//                                                        @Override
//                                                        public void onFailure(Call<Object> call, Throwable t) {
//                                                            Log.e("실패","실패"+t.getMessage());
//                                                        }
//                                                    });
//
//                                            }
//                                        }
//                                        @Override
//                                        public void onFailure(Call<ModelApiToken> call, Throwable t) {
//                                            Log.e("실패","실패"+t.getMessage());
//                                        }
//                                    });
//
//                            }else{
//                                //임시계좌 번호 가짐
//                            }
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelAccountExist> call, Throwable t) {
//                Log.e("실패","실패"+t.getMessage());
//            }
//        });

    }//onViewCreated 끝


    private FIDOCallbackResult fidoCallbackResult = new FIDOCallbackResult() {
        @Override
        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {
            if(requestCode == FIDORequestCode.REQUEST_CODE_AUTH) {
                if(fidoResult.getErrorCode() == FidoResult.RESULT_SUCCESS){
                    String token = mAuth.getToken();

                    try {
                        devId = JTdecoded(token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    verrifiedUser(token);

                }else{
                    switchView.setChecked(false);
                    loadingCustomMakingPort.dismiss();
                    // 실패 사유
                    switch(fidoResult.getErrorCode()){
                        case FidoResult.RESULT_USER_CANCEL:
                            break;
                        // 에러코드 999: 사용자 취소
                        case FidoResult.ERROR_JSON_PARSER:
                            break;
                        // 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
                        case FidoResult.ERROR_CANNOT_USE_FIDO:
                            break;
                        // 에러코드 1007: 지원하지 않는 단말기
                        case 13006:
                            break;
                        // 에러코드 13006: -인증 시 등록 된 아이디가 없는 사유로 서버에서 전달하는 에러코드
                        case 19019:
                            break;
                        // 에러코드 19019: -아이디 누락으로 인한 실패 사유로 서버에서 전달하는 에러코드
                        case FidoResult.ERROR_UNTRUSTED_FACET_ID:
                            break;
                        // 에러코드 2001: -등록되지 않은 3rd App으로 진행
                        case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
                            break;
                        // 에러코드 2002: 적합한 인증장치가 없음
                        case FidoResult.ERROR_REMOVE_KEYSTORE_IN_SW:
                            break;
                        // 에러코드 2004: -잠금 화면 설정 변경으로 인한 초기화 발생
                        case FidoResult.ERROR_NETWORK_STATE:
                            // 에러코드 4001: -네트워크 오류
                            break;
                        case FidoResult.PW_NOT_MATCH_BIO_FINGER:
                            break;
                        // 에러코드 5000: 지문 인증 실패
                        case FidoResult.ERROR_UNKNOWN:
                            break;
                        case FidoResult.ERROR_NOT_EXIST_USER_DATA:
                            break;
                        case FidoResult.ERROR_MAX_LOCK_COUNT_OVER:

                            activityMain.finish();
                            Toast.makeText(activityMain, "초과하였습니다  재등록합니다", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                    }
                }
            }
        }
    };



    private void verrifiedUser(String token){

        ModelFidoauthReqDto withdrawFido = new ModelFidoauthReqDto("", "PASSCODE", userId);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");

        Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(activityMain).getService().getFidoAuthCode("application/json", jsonItentifyData);
        getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
            @Override
            public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
                if(response.code() == 200) {

                    authCode = response.body().getContent().getAuthCode();

                    FidoReq fidoReq = new FidoReq(authCode, devId, "", "PASSCODE", "", token, "A", userId);
                    Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(activityMain).getService().getVerifyData("application/json",  fidoReq);
                    getReList.enqueue(new Callback<ModelVerifiedFido>() {
                        @Override
                        public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                            if (response.code() == 200) {

                                SharedPreferenceUtil.getInstance(activityMain).putTokenA("aToken", response.headers().get("Authorization"));
                                SharedPreferenceUtil.getInstance(activityMain).putAuthCode("authCode", response.body().getContent().getAuthCode());

                                if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_FINGERPRINT_TYPE)) {

                                    ModelFidoauthReqDto withdrawFido = new ModelFidoauthReqDto("", "FINGER", userId);
                                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                                    String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
                                            .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
                                    Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(activityMain).getService().getFidoAuthCode("application/json", jsonItentifyData);
                                    getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
                                        @Override
                                        public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
                                            if(response.code() == 200) {
                                                authCode = response.body().getContent().getAuthCode();

                                                reg2 = new FIDORegistration(fidoResult2, activityMain);

                                                if (userId.length() < 1) {
                                                    Toast.makeText(activityMain, "Please, Fill out the UserID.", Toast.LENGTH_LONG).show();
                                                    return;
                                                }
                                                // FIDO
                                                reg2.registFIDO(userId);
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<ModelFidoAuthCode> call, Throwable t) {
                                        }
                                    });
                                }
                                else{
                                    switchView.setChecked(false);
                                    loadingCustomMakingPort.dismiss();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelFidoAuthCode> call, Throwable t) {
            }
        });
    }

    private void verrifiedFingereUser(String token){

        ModelFidoauthReqDto withdrawFido = new ModelFidoauthReqDto("", "FINGER", userId);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");

        Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(activityMain).getService().getFidoAuthCode("application/json", jsonItentifyData);
        getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
            @Override
            public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
                if(response.code() == 200) {

                    authCode = response.body().getContent().getAuthCode();

                    FidoReq fidoReq = new FidoReq(authCode, devId, "", "FINGER", "", token, "R", userId);
                    Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(activityMain).getService().getVerifyData("application/json",  fidoReq);
                    getReList.enqueue(new Callback<ModelVerifiedFido>() {
                        @Override
                        public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                            if (response.code() == 200) {

                                SharedPreferenceUtil.getInstance(activityMain).putTokenA("aToken", response.headers().get("Authorization"));
                                SharedPreferenceUtil.getInstance(activityMain).putAuthCode("authCode", response.body().getContent().getAuthCode());
                                SharedPreferenceUtil.getInstance(activityMain).putFingerState("fingerState", true);

                                switchView.setChecked(true);
                                loadingCustomMakingPort.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
                            loadingCustomMakingPort.dismiss();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelFidoAuthCode> call, Throwable t) {
            }
        });
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
                    switchView.setChecked(false);
                    loadingCustomMakingPort.dismiss();
                }
            }
        }
    };


    FIDOCallbackResult fidoCallbackResult2 = new FIDOCallbackResult() {
        @Override
        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {

            if(requestCode == FIDORequestCode.REQUEST_LOCAL_CHANGE_VALUE){
                if(fidoResult.getErrorCode() == FidoResult.RESULT_SUCCESS){

                    Toast.makeText(activityMain, "등록 성공", Toast.LENGTH_SHORT).show();
                    loadingCustomMakingPort.dismiss();
                }
                else{
                    // 실패 사유
                    switch(fidoResult.getErrorCode()){
                        case FidoResult.RESULT_USER_CANCEL:

                            Toast.makeText(activityMain, "등록 취소", Toast.LENGTH_SHORT).show();
                            loadingCustomMakingPort.dismiss();

                            break;
                        // 에러코드 999: 사용자 취소
                        case FidoResult.ERROR_JSON_PARSER:
                            break;
                        // 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
                        case FidoResult.ERROR_CANNOT_USE_FIDO:
                            break;
                        // 에러코드 1007: 지원하지 않는 단말기
                        case 13006:
                            break;
                        // 에러코드 13006: -인증 시 등록 된 아이디가 없는 사유로 서버에서 전달하는 에러코드
                        case 19019:
                            break;
                        // 에러코드 19019: -아이디 누락으로 인한 실패 사유로 서버에서 전달하는 에러코드
                        case FidoResult.ERROR_UNTRUSTED_FACET_ID:
                            break;
                        // 에러코드 2001: -등록되지 않은 3rd App으로 진행
                        case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
                            break;
                        // 에러코드 2002: 적합한 인증장치가 없음
                        case FidoResult.ERROR_REMOVE_KEYSTORE_IN_SW:
                            break;
                        // 에러코드 2004: -잠금 화면 설정 변경으로 인한 초기화 발생
                        case FidoResult.ERROR_NETWORK_STATE:
                            // 에러코드 4001: -네트워크 오류
                            break;
                        case FidoResult.PW_NOT_MATCH_BIO_FINGER:
                            break;
                        // 에러코드 5000: 지문 인증 실패
                        case FidoResult.ERROR_UNKNOWN:
                            break;
                        case FidoResult.ERROR_NOT_EXIST_USER_DATA:
                            break;
                        case FidoResult.ERROR_MAX_LOCK_COUNT_OVER:
                            activityMain.finish();

                            Toast.makeText(activityMain, "초과하였습니다  재등록합니다", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                    }
                }
            }
        }
    };


    public String JTdecoded(String JWTEncoded) throws Exception {
        String[] split = JWTEncoded.split("\\.");

        Gson gson = new Gson();
        LoginFidoTokenData data = gson.fromJson(getJson(split[1]), LoginFidoTokenData.class);

        return data.deviceid;
    }

    private  String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decode =  android.util.Base64.decode(strEncoded,  Base64.URL_SAFE);
        return new String(decode, "utf-8");
    }


    private void checkedUseFido(){

        if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE) && mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_FINGERPRINT_TYPE)){
            passwordBt.setVisibility(View.GONE);
            easyPasswordBt.setVisibility(View.VISIBLE);
            fingerBt.setVisibility(View.VISIBLE);
        }
        else if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE)){
            passwordBt.setVisibility(View.GONE);
            easyPasswordBt.setVisibility(View.VISIBLE);
            fingerBt.setVisibility(View.GONE);
        }
        else{
            passwordBt.setVisibility(View.VISIBLE);
            easyPasswordBt.setVisibility(View.GONE);
            fingerBt.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.email_phoneBt:
                Intent intent2 = new Intent(activityMain, ActivityPhoneEmail.class);
                startActivity(intent2);
                break;
            case R.id.passwordBt:
                Intent intent = new Intent(activityMain, ActivityAuthPassword.class);
                startActivity(intent);
                break;
            case R.id.easyPasswordBt:

                loadingCustomMakingPort = new DialogLoadingMakingPort(activityMain, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                loadingCustomMakingPort.show();

                authCode = SharedPreferenceUtil.getInstance(activityMain).getStringExtra("authCode");

                ModelFidoauthReqDto fidoAuthReqDto = new ModelFidoauthReqDto(authCode, "PASSCODE", userId);
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String data = gson.toJson(fidoAuthReqDto).replace("\\n", "").replace(" ", "")
                        .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
                Call<ModelFlushAuth> tt = RetrofitClient.getInstance(activityMain).getService().getFlushAuthData("application/json", data);
                tt.enqueue(new Callback<ModelFlushAuth>() {
                    @Override
                    public void onResponse(Call<ModelFlushAuth> call, Response<ModelFlushAuth> response) {
                        if(response.code() == 200) {
                            SharedPreferenceUtil.getInstance(activityMain).putAuthCode("authCode", response.body().getContent().getAuthCode());
                            SharedPreferenceUtil.getInstance(activityMain).putTokenA("aToken", response.headers().get("Authorization"));

                            Hashtable<String, Object> authUICustom = new Hashtable<String, Object>();
                            authUICustom.put("text1", "암호 재설정");
                            authUICustom.put("text2", "보안키패드 작동중...");

                            Hashtable<String, Object> settingValue = new Hashtable<String, Object>();
                            settingValue.put("passcode", authUICustom);
                            mFidoUtil.setCustomUIValues(settingValue);

                            Hashtable<String, Object> authOption = new Hashtable<String, Object>();
                            authOption.put(MagicFIDOUtil.KEY_RETRY_COUNT_TO_LOCK, 5);
                            authOption.put(MagicFIDOUtil.KEY_MAX_LOCK_COUNT, 1);
                            authOption.put(MagicFIDOUtil.KEY_LOCK_TIME, Integer.MAX_VALUE);//성공 , 초기화
                            authOption.put(MagicFIDOUtil.KEY_USE_NUMBER_KEYPAD, true);
                            mFidoUtil.setAuthenticatorOptions(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, authOption);
                            mFidoUtil.setPasscodeUIType(FIDO_UI_TYPE.FIDO_PASSCODE_PIN6);
                            mFidoUtil.setPasscodeResetCallbackEnable(false);
                            // RP SDK 생성
                            mFidoUtil.changeUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE , fidoCallbackResult2 , true);

                        }
                    }
                    @Override
                    public void onFailure(Call<ModelFlushAuth> call, Throwable t) {
                        loadingCustomMakingPort.dismiss();
                    }
                });

                break;
        }

    }
}
