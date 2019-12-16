package com.quantec.moneypot.activity.FIDO;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dream.magic.fido.rpsdk.callback.FIDOCallbackResult;
import com.dream.magic.fido.rpsdk.client.FIDORequestCode;
import com.dream.magic.fido.rpsdk.client.FidoResult;
import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import com.quantec.moneypot.activity.Intro.ModelRegChk;
import com.quantec.moneypot.activity.Intro.ModelVerifiedFido;
//import quantec.com.moneypot.Activity.Login.MemberShipPage.ActivityRePhoneConfirm;
import com.quantec.moneypot.activity.Login.resist.ActivityRePhoneConfirm;
import com.quantec.moneypot.activity.Login.Model.dModel.ModelFidoauthReqDto;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelFidoAuthCode;
import com.quantec.moneypot.activity.Main.ActivityMain;
import com.quantec.moneypot.dialog.DialogExitApp;
import com.quantec.moneypot.dialog.DialogFidoCancle;
import com.quantec.moneypot.activity.Login.Model.dModel.FidoReq;
import com.quantec.moneypot.activity.Login.Model.dModel.ModelWithdrawFido;
import com.quantec.moneypot.dialog.DialogLoadingMakingPort;
import com.quantec.moneypot.dialog.DialogNetworkError;
import com.quantec.moneypot.fido.FIDOAuthentication;
import com.quantec.moneypot.fido.FIDORegistration;
import com.quantec.moneypot.fido.PropertyManager;
import com.quantec.moneypot.fido.PropertyManager2;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.R;
import com.quantec.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ActivityAuthFidoSingle extends AppCompatActivity {

    // RP SDK Object
    private FIDOAuthentication mAuth = null;
    String userId, devId, authCode;
    final String site = "PASSCODE";

    private DialogLoadingMakingPort loadingCustomMakingPort;
    //FIDO관련
    private MagicFIDOUtil mFidoUtil = null;
    // RP SDK 를 사용하기 위한 객체
    private FIDORegistration reg = null;
    DialogFidoCancle dialogFidoCancle;
    private DialogExitApp dialogExitApp;
    private DialogNetworkError dialogNetworkError;

    boolean exitState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_fido_double);

        loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityAuthFidoSingle.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
        loadingCustomMakingPort.show();

        PropertyManager.load(this);
        PropertyManager2.load(this);

        userId = SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).getStringExtra("userId");

        mFidoUtil = new MagicFIDOUtil(this);
        Hashtable<String, Object> authUICustom = new Hashtable<String, Object>();
        authUICustom.put("text1", "간편 로그인");
        authUICustom.put("text2", "보안키패드 작동중...");
        Hashtable<String, Object> settingValue = new Hashtable<String, Object>();
        settingValue.put("passcode", authUICustom);
        mFidoUtil.setCustomUIValues(settingValue);

        ModelFidoauthReqDto withdrawFido = new ModelFidoauthReqDto("", "PASSCODE", userId);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");


        Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(ActivityAuthFidoSingle.this).getService().getFidoAuthCode("application/json", jsonItentifyData);
        getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
            @Override
            public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
                if(response.code() == 200) {

                    authCode = response.body().getContent().getAuthCode();
                    // RP SDK 생성
                    mAuth = new FIDOAuthentication(ActivityAuthFidoSingle.this, fidoCallbackResult);
                    mAuth.startAuthentication(userId);
                }
            }
            @Override
            public void onFailure(Call<ModelFidoAuthCode> call, Throwable t) {
            }
        });

    }

    private void resistedFido(){

        mFidoUtil = new MagicFIDOUtil(this);
        Hashtable<String, Object> authUICustom = new Hashtable<String, Object>();
        authUICustom.put("text1", "간편 비밀번호 변경");
        authUICustom.put("text2", "보안키패드 작동중...");
        Hashtable<String, Object> settingValue = new Hashtable<String, Object>();
        settingValue.put("passcode", authUICustom);
        mFidoUtil.setCustomUIValues(settingValue);

        reg = new FIDORegistration(ActivityAuthFidoSingle.this,  fidoResult);
        reg.registFIDO(userId);
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

                    dialogFidoCancle = new DialogFidoCancle(ActivityAuthFidoSingle.this, fidoCancle);
                    dialogFidoCancle.show();
                    // 실패 사유
                    switch (fidoResult.getErrorCode()) {
                        case FidoResult.RESULT_USER_CANCEL:
                            Toast.makeText(getApplicationContext(), "앱을 종료합니다", Toast.LENGTH_SHORT).show();
//                            finish();
                            break;
                        // 에러코드 999: 사용자 취소
                        case FidoResult.ERROR_JSON_PARSER:
                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
                            dialogExitApp.show();
                            break;
                        // 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
                        case FidoResult.ERROR_CANNOT_USE_FIDO:
                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
                            dialogExitApp.show();
                            break;
                        // 에러코드 1007: 지원하지 않는 단말기
                        case FidoResult.ERROR_UNTRUSTED_FACET_ID:
                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
                            dialogExitApp.show();
                            break;
                        // 에러코드 2001: -등록되지 않은 3rd App으로 진행
                        case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
                            dialogExitApp.show();
                            break;
                        // 에러코드 2002: 적합한 인증장치가 없음
                        case FidoResult.ERROR_NETWORK_STATE:
                            dialogNetworkError = new DialogNetworkError(ActivityAuthFidoSingle.this, networkError);
                            dialogNetworkError.show();
                            break;
                        // 에러코드 4001: -네트워크 오류
                        case FidoResult.PW_NOT_MATCH_BIO_FINGER:
                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
                            dialogExitApp.show();
                            break;
                        // 에러코드 5000: 지문 인증 실패
                        case FidoResult.ERROR_UNKNOWN:
                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
                            dialogExitApp.show();
                            // 에러코드 9999: FIDO 로직 수행 중, 알수 없는 에러 발생
                            break;
                        case FidoResult.RESULT_USER_SELECT_REREG:
                            // 에러코드 980: 사용자가 재등록 버튼 클릭
                            Toast.makeText(getApplicationContext(), "사용자가 재등록 버튼 클릭" , Toast.LENGTH_SHORT).show();
                            break;
                        case FidoResult.ERROR_ALREADY_REGI_USER:
                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
                            dialogExitApp.show();
                            // 에러코드 1008: -이미 등록 된 사용자
                            break;
                        case FidoResult.ERROR_MAX_LOCK_COUNT_OVER:
                            break;
                        default:
                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
                            dialogExitApp.show();
                            break;
                    }
                }
            }
        }
    };

    //네트워크 오류 앱 종료
    private View.OnClickListener networkError = new View.OnClickListener() {
        public void onClick(View v) {
            dialogNetworkError.dismiss();
            finish();
        }
    };

    //FIDO 인증 취소 앱 종료
    private View.OnClickListener exitApp = new View.OnClickListener() {
        public void onClick(View v) {
            dialogExitApp.dismiss();
            finish();
        }
    };

    //FIDO 등록 취소
    private View.OnClickListener fidoCancle = new View.OnClickListener() {
        public void onClick(View v) {
            dialogFidoCancle.dismiss();
            finish();
        }
    };

    private FIDOCallbackResult fidoCallback = new FIDOCallbackResult() {
        @Override
        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {
            // FIDO Client에 전송하지 못 하였을 경우.
            if(fidoResult.getErrorCode() != FidoResult.RESULT_SUCCESS){

            }else{
                //파이도 사용자를 앱자체에서 삭제
                resistedFido();
            }
        }
    };

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
                    verrifiedUser(token, site);

                }else{

                    Log.e("에러코드","값 : "+fidoResult.getErrorCode());
                    // 실패 사유
                    switch(fidoResult.getErrorCode()){

                        case FidoResult.RESULT_USER_CANCEL:
                            Toast.makeText(getApplicationContext(), "접속에 실패하였습니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case FidoResult.RESULT_USER_SELECT_REREG:
                            Intent intent = new Intent(ActivityAuthFidoSingle.this, ActivityRePhoneConfirm.class);
                            intent.putExtra("passwordPage", "DoFido");
                            startActivityForResult(intent, 200);
                            Toast.makeText(getApplicationContext(), "재등록합니다", Toast.LENGTH_SHORT).show();
                            break;
                        case FidoResult.ERROR_MAX_LOCK_COUNT_OVER:
                            Intent intent2 = new Intent(ActivityAuthFidoSingle.this, ActivityRePhoneConfirm.class);
                            intent2.putExtra("passwordPage", "DoFido");
                            startActivityForResult(intent2, 200);

                            Toast.makeText(getApplicationContext(), "초과하였습니다  재등록합니다", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "접속에 실패하였습니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                            finish();
                    }
                }
            }
        }
    };


    private void verrifiedPasscodeUser(String token){

        FidoReq fidoReq = new FidoReq(authCode, devId, "", "PASSCODE", "", token, "R", userId);
        Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityAuthFidoSingle.this).getService().getVerifyData("application/json",  fidoReq);
        getReList.enqueue(new Callback<ModelVerifiedFido>() {
            @Override
            public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                if (response.code() == 200) {

                    SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).putTokenA("aToken", response.headers().get("Authorization"));
                    SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).putAuthCode("authCode", response.body().getContent().getAuthCode());

                    Intent intent = new Intent(ActivityAuthFidoSingle.this, ActivityMain.class);
                    startActivity(intent);
                    finish();

                }else{
                    mFidoUtil.resetUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, fidoCallback2);
                }
            }
            @Override
            public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
                mFidoUtil.resetUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, fidoCallback2);
            }
        });
    }

    private FIDOCallbackResult fidoCallback2 = new FIDOCallbackResult() {
        @Override
        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {
        }
    };

    private void verrifiedUser(String token, String site){

        FidoReq fidoReq = new FidoReq(authCode, devId, "", site, "", token, "A", userId);
        Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityAuthFidoSingle.this).getService().getVerifyData("application/json",  fidoReq);
        getReList.enqueue(new Callback<ModelVerifiedFido>() {
            @Override
            public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                if (response.code() == 200) {

                    exitState = true;

                    SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).putTokenA("aToken", response.headers().get("Authorization"));
                    SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).putAuthCode("authCode", response.body().getContent().getAuthCode());

                    Intent intent = new Intent(ActivityAuthFidoSingle.this, ActivityMain.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
            }
        });
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200){
            if(resultCode == 201){

                authCode = SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).getStringExtra("authCode");

                ModelWithdrawFido withdrawFido = new ModelWithdrawFido(authCode, "PASSCODE", userId);
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
                        .replace("\\", "").replace("\"{", "{").replace("}\"", "}");

                Call<ModelRegChk> getRegChkData = RetrofitClient.getInstance(ActivityAuthFidoSingle.this).getService().getWithdrawFidoData("application/json", jsonItentifyData);
                getRegChkData.enqueue(new Callback<ModelRegChk>() {
                    @Override
                    public void onResponse(Call<ModelRegChk> call, Response<ModelRegChk> response) {
                        if(response.code() == 200) {
                            SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).putTokenA("aToken", response.headers().get("Authorization"));
                            mFidoUtil.resetUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, fidoCallback);
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelRegChk> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(), "앱을 종료합니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingCustomMakingPort.dismiss();
    }

    @Override
    public void onBackPressed() {
    }
}



//public class ActivityAuthFidoSingle extends AppCompatActivity {
//
//    // RP SDK Object
//    private FIDOAuthentication mAuth = null;
//    String userId, devId, authCode;
//    final String site = "PASSCODE";
//
//    private DialogLoadingMakingPort loadingCustomMakingPort;
//    //FIDO관련
//    private MagicFIDOUtil mFidoUtil = null;
//    // RP SDK 를 사용하기 위한 객체
//    private FIDORegistration reg = null;
//    DialogFidoCancle dialogFidoCancle;
//    private DialogExitApp dialogExitApp;
//    private DialogNetworkError dialogNetworkError;
//
//    private boolean reStart = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_auth_fido_double);
//
//        loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityAuthFidoSingle.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
//        loadingCustomMakingPort.show();
//
//        PropertyManager.load(this);
//        PropertyManager2.load(this);
//
//        userId = SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).getStringExtra("userId");
//
//        mFidoUtil = new MagicFIDOUtil(this);
//        Hashtable<String, Object> authUICustom = new Hashtable<String, Object>();
//        authUICustom.put("text1", "간편 로그인");
//        authUICustom.put("text2", "보안키패드 작동중...");
//        Hashtable<String, Object> settingValue = new Hashtable<String, Object>();
//        settingValue.put("passcode", authUICustom);
//        mFidoUtil.setCustomUIValues(settingValue);
//
//        ModelFidoauthReqDto withdrawFido = new ModelFidoauthReqDto("", "PASSCODE", userId);
//        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//        String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
//                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
//
//        Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(ActivityAuthFidoSingle.this).getService().getFidoAuthCode("application/json", jsonItentifyData);
//        getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
//            @Override
//            public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
//                if(response.code() == 200) {
//
//                    authCode = response.body().getContent().getAuthCode();
//                    // RP SDK 생성
//                    mAuth = new FIDOAuthentication(ActivityAuthFidoSingle.this, fidoCallbackResult);
//                    mAuth.startAuthentication(userId);
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelFidoAuthCode> call, Throwable t) {
//            }
//        });
//    }
//
//    private void resistedFido(){
//
//        mFidoUtil = new MagicFIDOUtil(this);
//        Hashtable<String, Object> authUICustom = new Hashtable<String, Object>();
//        authUICustom.put("text1", "간편 비밀번호 변경");
//        authUICustom.put("text2", "보안키패드 작동중...");
//        Hashtable<String, Object> settingValue = new Hashtable<String, Object>();
//        settingValue.put("passcode", authUICustom);
//        mFidoUtil.setCustomUIValues(settingValue);
//
//        reg = new FIDORegistration(ActivityAuthFidoSingle.this,  fidoResult);
//        reg.registFIDO(userId);
//    }
//
//    private FIDOCallbackResult fidoResult = new FIDOCallbackResult() {
//
//        @Override
//        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {
//
//            if (requestCode == FIDORequestCode.REQUEST_CODE_REGIST) {
//                if (fidoResult.getErrorCode() == FidoResult.RESULT_SUCCESS) {
//                    // 에러코드 0: 등록 성공
//                    String token = reg.getToken();
//                    try {
//                        devId = JTdecoded(token);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    verrifiedPasscodeUser(token);
//
//                } else {
//
//                    dialogFidoCancle = new DialogFidoCancle(ActivityAuthFidoSingle.this, fidoCancle);
//                    dialogFidoCancle.show();
//                    // 실패 사유
//                    switch (fidoResult.getErrorCode()) {
//                        case FidoResult.RESULT_USER_CANCEL:
////                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
////                            dialogExitApp.show();
//                            Toast.makeText(ActivityAuthFidoSingle.this, "앱을 종료합니다", Toast.LENGTH_SHORT).show();
//                            finish();
//                            break;
//                        // 에러코드 999: 사용자 취소
//                        case FidoResult.ERROR_JSON_PARSER:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
//                        case FidoResult.ERROR_CANNOT_USE_FIDO:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 1007: 지원하지 않는 단말기
//                        case FidoResult.ERROR_UNTRUSTED_FACET_ID:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 2001: -등록되지 않은 3rd App으로 진행
//                        case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 2002: 적합한 인증장치가 없음
//                        case FidoResult.ERROR_NETWORK_STATE:
//                            dialogNetworkError = new DialogNetworkError(ActivityAuthFidoSingle.this, networkError);
//                            dialogNetworkError.show();
//                            break;
//                            // 에러코드 4001: -네트워크 오류
//                        case FidoResult.PW_NOT_MATCH_BIO_FINGER:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 5000: 지문 인증 실패
//                        case FidoResult.ERROR_UNKNOWN:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            // 에러코드 9999: FIDO 로직 수행 중, 알수 없는 에러 발생
//                            break;
//                        case FidoResult.RESULT_USER_SELECT_REREG:
//                            // 에러코드 980: 사용자가 재등록 버튼 클릭
//                            Toast.makeText(ActivityAuthFidoSingle.this, "사용자가 재등록 버튼 클릭" , Toast.LENGTH_SHORT).show();
//                            break;
//                        case FidoResult.ERROR_ALREADY_REGI_USER:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            // 에러코드 1008: -이미 등록 된 사용자
//                            break;
//                        case FidoResult.ERROR_MAX_LOCK_COUNT_OVER:
//                            break;
//                        default:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                    }
//                }
//            }
//        }
//    };
//
//    //네트워크 오류 앱 종료
//    private View.OnClickListener networkError = new View.OnClickListener() {
//        public void onClick(View v) {
//            dialogNetworkError.dismiss();
//            finish();
//        }
//    };
//
//    //FIDO 인증 취소 앱 종료
//    private View.OnClickListener exitApp = new View.OnClickListener() {
//        public void onClick(View v) {
//            dialogExitApp.dismiss();
//            finish();
//        }
//    };
//
//    //FIDO 등록 취소
//    private View.OnClickListener fidoCancle = new View.OnClickListener() {
//        public void onClick(View v) {
//            dialogFidoCancle.dismiss();
//            finish();
//        }
//    };
//
//    private FIDOCallbackResult fidoCallback = new FIDOCallbackResult() {
//        @Override
//        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {
//                // FIDO Client에 전송하지 못 하였을 경우.
//                if(fidoResult.getErrorCode() != FidoResult.RESULT_SUCCESS){
//
//                }else{
//                    //파이도 사용자를 앱자체에서 삭제
//                    resistedFido();
//                }
//        }
//    };
//
//    private FIDOCallbackResult fidoCallbackResult = new FIDOCallbackResult() {
//        @Override
//        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {
//            if(requestCode == FIDORequestCode.REQUEST_CODE_AUTH) {
//                if(fidoResult.getErrorCode() == FidoResult.RESULT_SUCCESS){
//                    String token = mAuth.getToken();
//
//                    try {
//                        devId = JTdecoded(token);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    verrifiedUser(token, site);
//
//                }else{
//
//                    Log.e("에러값", "값 : "+fidoResult.getErrorCode());
//                    // 실패 사유
//                    switch(fidoResult.getErrorCode()){
//                        case FidoResult.RESULT_USER_CANCEL:
//
////                            reStart = true;
////                            onResume();
//                            Toast.makeText(ActivityAuthFidoSingle.this, "앱을 종료합니다", Toast.LENGTH_SHORT).show();
//                            finish();
//                            break;
//                            // 에러코드 999: 사용자 취소
//                        case FidoResult.ERROR_JSON_PARSER:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
//                        case FidoResult.ERROR_CANNOT_USE_FIDO:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 1007: 지원하지 않는 단말기
//                        case 13006:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 13006: -인증 시 등록 된 아이디가 없는 사유로 서버에서 전달하는 에러코드
//                        case 19019:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 19019: -아이디 누락으로 인한 실패 사유로 서버에서 전달하는 에러코드
//                        case FidoResult.ERROR_UNTRUSTED_FACET_ID:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 2001: -등록되지 않은 3rd App으로 진행
//                        case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 2002: 적합한 인증장치가 없음
//                        case FidoResult.ERROR_REMOVE_KEYSTORE_IN_SW:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 2004: -잠금 화면 설정 변경으로 인한 초기화 발생
//                        case FidoResult.ERROR_NETWORK_STATE:
////                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
////                            dialogExitApp.show();
//                            // 에러코드 4001: -네트워크 오류
//                            break;
//                        case FidoResult.PW_NOT_MATCH_BIO_FINGER:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            break;
//                            // 에러코드 5000: 지문 인증 실패
//                        case FidoResult.ERROR_UNKNOWN:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            // 에러코드 9999: FIDO 로직 수행 중, 알수 없는 에러 발생
//                            break;
//                        case FidoResult.ERROR_NOT_EXIST_USER_DATA:
//                            dialogExitApp = new DialogExitApp(ActivityAuthFidoSingle.this, exitApp);
//                            dialogExitApp.show();
//                            // 에러코드 1009: -인증하려는 아이디의 데이터가 존재하지 않는 경우
//                            break;
//                        case FidoResult.RESULT_USER_SELECT_REREG:
//                            Intent intent = new Intent(ActivityAuthFidoSingle.this, ActivityRePhoneConfirm.class);
//                            intent.putExtra("passwordPage", "DoFido");
//                            startActivityForResult(intent, 200);
//                            Toast.makeText(ActivityAuthFidoSingle.this, "재등록합니다", Toast.LENGTH_SHORT).show();
//                            break;
//                        case FidoResult.ERROR_MAX_LOCK_COUNT_OVER:
//                            Intent intent2 = new Intent(ActivityAuthFidoSingle.this, ActivityRePhoneConfirm.class);
//                            intent2.putExtra("passwordPage", "DoFido");
//                            startActivityForResult(intent2, 200);
//
//                            Toast.makeText(ActivityAuthFidoSingle.this, "초과하였습니다  재등록합니다", Toast.LENGTH_SHORT).show();
//                            break;
//                        default:
//                            Toast.makeText(ActivityAuthFidoSingle.this, "접속에 실패하였습니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
//                            finish();
//                    }
//                }
//            }
//        }
//    };
//
//    private void verrifiedPasscodeUser(String token){
//
//        FidoReq fidoReq = new FidoReq(authCode, devId, "", "PASSCODE", "", token, "R", userId);
//        Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityAuthFidoSingle.this).getService().getVerifyData("application/json",  fidoReq);
//        getReList.enqueue(new Callback<ModelVerifiedFido>() {
//            @Override
//            public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
//                if (response.code() == 200) {
//
//                    SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).putTokenA("aToken", response.headers().get("Authorization"));
//                    SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).putAuthCode("authCode", response.body().getContent().getAuthCode());
//
//                    Intent intent = new Intent(ActivityAuthFidoSingle.this, ActivityMain.class);
//                    startActivity(intent);
//                    finish();
//
//                }else{
//                    mFidoUtil.resetUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, fidoCallback2);
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
//                mFidoUtil.resetUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, fidoCallback2);
//            }
//        });
//    }
//
//    private FIDOCallbackResult fidoCallback2 = new FIDOCallbackResult() {
//        @Override
//        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {
//        }
//    };
//
//    private void verrifiedUser(String token, String site){
//
//        FidoReq fidoReq = new FidoReq(authCode, devId, "", site, "", token, "A", userId);
//        Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityAuthFidoSingle.this).getService().getVerifyData("application/json",  fidoReq);
//        getReList.enqueue(new Callback<ModelVerifiedFido>() {
//            @Override
//            public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
//                if (response.code() == 200) {
//
//                    SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).putTokenA("aToken", response.headers().get("Authorization"));
//                    SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).putAuthCode("authCode", response.body().getContent().getAuthCode());
//
//                    Intent intent = new Intent(ActivityAuthFidoSingle.this, ActivityMain.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
//            }
//        });
//    }
//
//    public String JTdecoded(String JWTEncoded) throws Exception {
//        String[] split = JWTEncoded.split("\\.");
//
//        Gson gson = new Gson();
//        LoginFidoTokenData data = gson.fromJson(getJson(split[1]), LoginFidoTokenData.class);
//
//        return data.deviceid;
//    }
//
//    private  String getJson(String strEncoded) throws UnsupportedEncodingException {
//        byte[] decode =  android.util.Base64.decode(strEncoded,  Base64.URL_SAFE);
//        return new String(decode, "utf-8");
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 200){
//            if(resultCode == 201){
//
//                authCode = SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).getStringExtra("authCode");
//
//                ModelWithdrawFido withdrawFido = new ModelWithdrawFido(authCode, "PASSCODE", userId);
//                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//                String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
//                        .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
//
//                Call<ModelRegChk> getRegChkData = RetrofitClient.getInstance(ActivityAuthFidoSingle.this).getService().getwithdrawFidoData("application/json", jsonItentifyData);
//                getRegChkData.enqueue(new Callback<ModelRegChk>() {
//                    @Override
//                    public void onResponse(Call<ModelRegChk> call, Response<ModelRegChk> response) {
//                        if(response.code() == 200) {
//
//                            SharedPreferenceUtil.getInstance(ActivityAuthFidoSingle.this).putTokenA("aToken", response.headers().get("Authorization"));
//                            mFidoUtil.resetUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, fidoCallback);
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<ModelRegChk> call, Throwable t) {
//                        Toast.makeText(ActivityAuthFidoSingle.this, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }else{
//                Toast.makeText(ActivityAuthFidoSingle.this, "앱을 종료합니다", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
////        if(reStart){
////            reStart = false;
////
////            Intent intent = new Intent(ActivityAuthFidoSingle.this, ActivityAuthFidoSingle.class);
////            intent.addFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
////            startActivity(intent);
////
////        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        loadingCustomMakingPort.dismiss();
//    }
//
//    @Override
//    public void onBackPressed() {
//    }
//}
