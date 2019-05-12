package quantec.com.moneypot.Activity.MyInfomation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.magic.fido.rpsdk.callback.FIDOCallbackResult;
import com.dream.magic.fido.rpsdk.client.FIDORequestCode;
import com.dream.magic.fido.rpsdk.client.FIDO_UI_TYPE;
import com.dream.magic.fido.rpsdk.client.FidoResult;
import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import quantec.com.moneypot.Activity.BaseActivity.BaseActivity;
import quantec.com.moneypot.Activity.FIDO.ActivityAuthFidoSingle;
import quantec.com.moneypot.Activity.FIDO.LoginFidoTokenData;
import quantec.com.moneypot.Activity.Intro.ModelRegChk;
import quantec.com.moneypot.Activity.Intro.ModelVerifiedFido;
import quantec.com.moneypot.Activity.Login.MemberShipPage.ActivityRePhoneConfirm;
import quantec.com.moneypot.Activity.Login.Model.dModel.FidoReq;
import quantec.com.moneypot.Activity.Login.Model.dModel.ModelFidoauthReqDto;
import quantec.com.moneypot.Activity.Login.Model.dModel.ModelWithdrawFido;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelFidoAuthCode;
import quantec.com.moneypot.Dialog.DialogExitApp;
import quantec.com.moneypot.Dialog.DialogFidoCancle;
import quantec.com.moneypot.Dialog.DialogLoadingMakingPort;
import quantec.com.moneypot.Dialog.DialogNetworkError;
import quantec.com.moneypot.FIDO.FIDOAuthentication;
import quantec.com.moneypot.FIDO.FIDONotAuthenticatioin;
import quantec.com.moneypot.FIDO.FIDORegistration;
import quantec.com.moneypot.FIDO.PropertyManager;
import quantec.com.moneypot.FIDO.PropertyManager2;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import quantec.com.moneypot.databinding.ActivityMyInfomationBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMyInfomation extends BaseActivity implements View.OnClickListener {

    ActivityMyInfomationBinding binding;
    private MagicFIDOUtil mFidoUtil = null;
    private FIDONotAuthenticatioin mAuth = null;
    String userId, devId, authCode;

    private FIDORegistration reg2 = null;

    DialogFidoCancle dialogFidoCancle;
    private DialogExitApp dialogExitApp;
    private DialogNetworkError dialogNetworkError;
    private DialogLoadingMakingPort loadingCustomMakingPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_infomation);
        binding.setMyInfomation(this);

        mainList.add(0, this);

        if(SharedPreferenceUtil.getInstance(ActivityMyInfomation.this).getStateExtra("fingerState")){
            binding.switchView.setChecked(true);
        }
        else{
            binding.switchView.setChecked(false);
        }

        PropertyManager.load(this);
        PropertyManager2.load(this);
        userId = SharedPreferenceUtil.getInstance(ActivityMyInfomation.this).getStringExtra("userId");
        mFidoUtil = new MagicFIDOUtil(this);

        checkedUseFido();

        binding.emailPhone.setOnClickListener(this);
        binding.password.setOnClickListener(this);
        binding.fidoPassword.setOnClickListener(this);

        binding.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityMyInfomation.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                    loadingCustomMakingPort.show();

                    Hashtable<String, Object> authUICustom = new Hashtable<String, Object>();
                    authUICustom.put("text1", "지문 로그인 활성화");
                    authUICustom.put("text2", "보안키패드 작동중...");

                    Hashtable<String, Object> settingValue = new Hashtable<String, Object>();
                    settingValue.put("passcode", authUICustom);
                    mFidoUtil.setCustomUIValues(settingValue);
                    // RP SDK 생성
                    mAuth = new FIDONotAuthenticatioin(ActivityMyInfomation.this, fidoCallbackResult);
                    mAuth.startAuthentication(userId);

                }
                else{
                    SharedPreferenceUtil.getInstance(ActivityMyInfomation.this).putFingerState("fingerState", false);
                    Toast.makeText(ActivityMyInfomation.this, "지문 미사용", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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
                    binding.switchView.setChecked(false);
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

                            finish();
                            Toast.makeText(ActivityMyInfomation.this, "초과하였습니다  재등록합니다", Toast.LENGTH_SHORT).show();
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

        Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(ActivityMyInfomation.this).getService().getFidoAuthCode("application/json", jsonItentifyData);
        getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
            @Override
            public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
                if(response.code() == 200) {

                    authCode = response.body().getContent().getAuthCode();

                    FidoReq fidoReq = new FidoReq(authCode, devId, "", "PASSCODE", "", token, "A", userId);
                    Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityMyInfomation.this).getService().getVerifyData("application/json",  fidoReq);
                    getReList.enqueue(new Callback<ModelVerifiedFido>() {
                        @Override
                        public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                            if (response.code() == 200) {

                                SharedPreferenceUtil.getInstance(ActivityMyInfomation.this).putTokenA("aToken", response.headers().get("Authorization"));
                                SharedPreferenceUtil.getInstance(ActivityMyInfomation.this).putAuthCode("authCode", response.body().getContent().getAuthCode());

                                if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_FINGERPRINT_TYPE)) {

                                    ModelFidoauthReqDto withdrawFido = new ModelFidoauthReqDto("", "FINGER", userId);
                                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                                    String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
                                            .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
                                    Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(ActivityMyInfomation.this).getService().getFidoAuthCode("application/json", jsonItentifyData);
                                    getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
                                        @Override
                                        public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
                                            if(response.code() == 200) {
                                                authCode = response.body().getContent().getAuthCode();

                                                reg2 = new FIDORegistration(fidoResult2, ActivityMyInfomation.this);

                                                if (userId.length() < 1) {
                                                    Toast.makeText(ActivityMyInfomation.this, "Please, Fill out the UserID.", Toast.LENGTH_LONG).show();
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
                                }else{
                                    binding.switchView.setChecked(false);
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
                    binding.switchView.setChecked(false);
                    loadingCustomMakingPort.dismiss();
                }
            }
        }
    };

    private void verrifiedFingereUser(String token){

        ModelFidoauthReqDto withdrawFido = new ModelFidoauthReqDto("", "FINGER", userId);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");

        Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(ActivityMyInfomation.this).getService().getFidoAuthCode("application/json", jsonItentifyData);
        getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
            @Override
            public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
                if(response.code() == 200) {

                    authCode = response.body().getContent().getAuthCode();

                    FidoReq fidoReq = new FidoReq(authCode, devId, "", "FINGER", "", token, "R", userId);
                    Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityMyInfomation.this).getService().getVerifyData("application/json",  fidoReq);
                    getReList.enqueue(new Callback<ModelVerifiedFido>() {
                        @Override
                        public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                            if (response.code() == 200) {

                                SharedPreferenceUtil.getInstance(ActivityMyInfomation.this).putTokenA("aToken", response.headers().get("Authorization"));
                                SharedPreferenceUtil.getInstance(ActivityMyInfomation.this).putAuthCode("authCode", response.body().getContent().getAuthCode());
                                SharedPreferenceUtil.getInstance(ActivityMyInfomation.this).putFingerState("fingerState", true);

                                binding.switchView.setChecked(true);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_phone:
                Intent intent2 = new Intent(ActivityMyInfomation.this, ActivityPhoneEmail.class);
                startActivity(intent2);
                break;
            case R.id.password:
                Intent intent = new Intent(ActivityMyInfomation.this, ActivityAuthPassword.class);
                startActivity(intent);
                break;
            case R.id.fidoPassword:

                loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityMyInfomation.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                loadingCustomMakingPort.show();

                Hashtable<String, Object> authUICustom = new Hashtable<String, Object>();
                authUICustom.put("text1", "암호 재설정");
                authUICustom.put("text2", "보안키패드 작동중...");

                Hashtable<String, Object> settingValue = new Hashtable<String, Object>();
                settingValue.put("passcode", authUICustom);
                mFidoUtil.setCustomUIValues(settingValue);

                Hashtable<String, Object> authOption = new Hashtable<String, Object>();
                authOption.put(MagicFIDOUtil.KEY_RETRY_COUNT_TO_LOCK, 5);
                authOption.put(MagicFIDOUtil.KEY_MAX_LOCK_COUNT, 1);
                authOption.put(MagicFIDOUtil.KEY_USE_NUMBER_KEYPAD, true);
                mFidoUtil.setAuthenticatorOptions(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, authOption);
                mFidoUtil.setPasscodeUIType(FIDO_UI_TYPE.FIDO_PASSCODE_PIN6);
                mFidoUtil.setPasscodeResetCallbackEnable(false);
                // RP SDK 생성
                mFidoUtil.changeUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE , fidoCallbackResult2 , true);

                break;
        }
    }

    FIDOCallbackResult fidoCallbackResult2 = new FIDOCallbackResult() {
        @Override
        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {

            if(requestCode == FIDORequestCode.REQUEST_LOCAL_CHANGE_VALUE){
                if(fidoResult.getErrorCode() == FidoResult.RESULT_SUCCESS){

                    Toast.makeText(ActivityMyInfomation.this, "등록 성공", Toast.LENGTH_SHORT).show();
                    loadingCustomMakingPort.dismiss();
                }
                else{
                    // 실패 사유
                    switch(fidoResult.getErrorCode()){
                        case FidoResult.RESULT_USER_CANCEL:

                            Toast.makeText(ActivityMyInfomation.this, "등록 취소", Toast.LENGTH_SHORT).show();
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
                            finish();

                            Toast.makeText(ActivityMyInfomation.this, "초과하였습니다  재등록합니다", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                    }
                }
            }
        }
    };

    private void checkedUseFido(){

        if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE) && mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_FINGERPRINT_TYPE)){
            binding.password.setVisibility(View.GONE);
            binding.passwordImage.setVisibility(View.GONE);

            binding.fidoPassword.setVisibility(View.VISIBLE);
            binding.fidoPasswordImage.setVisibility(View.VISIBLE);

            binding.finger.setVisibility(View.VISIBLE);
            binding.switchView.setVisibility(View.VISIBLE);
        }
        else if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE)){
            binding.password.setVisibility(View.GONE);
            binding.passwordImage.setVisibility(View.GONE);

            binding.fidoPassword.setVisibility(View.VISIBLE);
            binding.fidoPasswordImage.setVisibility(View.VISIBLE);

            binding.finger.setVisibility(View.GONE);
            binding.switchView.setVisibility(View.GONE);

        }
        else{
            binding.password.setVisibility(View.VISIBLE);
            binding.passwordImage.setVisibility(View.VISIBLE);

            binding.fidoPassword.setVisibility(View.GONE);
            binding.fidoPasswordImage.setVisibility(View.GONE);

            binding.finger.setVisibility(View.GONE);
            binding.switchView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityRemove();
        Log.e("쌓인 액티비티","값 : "+ mainList.size());
    }
}
