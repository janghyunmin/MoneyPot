package quantec.com.moneypot.Activity.FIDO;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dream.magic.fido.rpsdk.callback.FIDOCallbackResult;
import com.dream.magic.fido.rpsdk.client.FIDORequestCode;
import com.dream.magic.fido.rpsdk.client.FidoResult;
import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import quantec.com.moneypot.Activity.Intro.ModelVerifiedFido;
import quantec.com.moneypot.Activity.Login.Model.dModel.FidoReq;
import quantec.com.moneypot.Activity.Main.ActivityMain;
import quantec.com.moneypot.Dialog.DialogFidoFinger;
import quantec.com.moneypot.Dialog.DialogLoadingMakingPort;
import quantec.com.moneypot.FIDO.FIDORegistration;
import quantec.com.moneypot.FIDO.PropertyManager;
import quantec.com.moneypot.FIDO.PropertyManager2;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegFido extends AppCompatActivity {


    private MagicFIDOUtil mFidoUtil = null;

    // RP SDK 를 사용하기 위한 객체
    private FIDORegistration reg = null;
    private FIDORegistration reg2 = null;

    private final int ALERT_REQUEST = 0xA3;

    private DialogFidoFinger dialogFidoFinger;

    String userId, devId, authCode;

    private DialogLoadingMakingPort loadingCustomMakingPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_fido);

        loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityRegFido.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
        loadingCustomMakingPort.show();

//        actList.add(this);

        userId = SharedPreferenceUtil.getInstance(ActivityRegFido.this).getStringExtra("userId");
        authCode = SharedPreferenceUtil.getInstance(ActivityRegFido.this).getStringExtra("authCode");

        PropertyManager.load(this);
        PropertyManager2.load(this);
        mFidoUtil = new MagicFIDOUtil(this);

        if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE)){
            reg = new FIDORegistration(ActivityRegFido.this,  fidoResult);
            reg.registFIDO(userId);
        }
        else{

            Intent intent = new Intent(ActivityRegFido.this, ActivityMain.class);
            startActivity(intent);
//            actFinish();

            Toast.makeText(ActivityRegFido.this, "바로 로그인", Toast.LENGTH_SHORT).show();
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

//                    actFinish();

                    // 실패 사유
                    switch (fidoResult.getErrorCode()) {
                        case FidoResult.RESULT_USER_CANCEL:
                            finish();
                            break;
                        // 에러코드 999: 사용자 취소
                        case FidoResult.ERROR_JSON_PARSER:
                            // 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
                        case FidoResult.ERROR_CANNOT_USE_FIDO:
                            // 에러코드 1007: 지원하지 않는 단말기
                        case FidoResult.ERROR_UNTRUSTED_FACET_ID:
                            // 에러코드 2001: -등록되지 않은 3rd App으로 진행
                            //			   -FIDO Server에 FacetID 를 등록해야 함.
                        case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
                            // 에러코드 2002: 적합한 인증장치가 없음
                        case FidoResult.ERROR_NETWORK_STATE:
                            // 에러코드 4001: -네트워크 오류
                            //			   -fidoResult.getDescription() 을 통하여 자세한 오류 내용을 알 수 있음
                        case FidoResult.PW_NOT_MATCH_BIO_FINGER:
                            // 에러코드 5000: 지문 인증 실패
                        case FidoResult.ERROR_UNKNOWN:
                            // 에러코드 9999: FIDO 로직 수행 중, 알수 없는 에러 발생
//                            showDialog("FAIL", fidoResult.getDescription() + "(" + fidoResult.getErrorCode() + ")");
                            break;
                        case FidoResult.RESULT_USER_SELECT_REREG:
                            // 에러코드 980: 사용자가 재등록 버튼 클릭
                            Toast.makeText(ActivityRegFido.this, "사용자가 재등록 버튼 클릭" , Toast.LENGTH_SHORT).show();
                            break;
                        case FidoResult.ERROR_ALREADY_REGI_USER:
                            // 에러코드 1008: -이미 등록 된 사용자
                            //             -해지 후 재등록 요청을 해야 함.
//                            showDialog("FAIL", "이미 등록 된 사용자입니다. 해지 후 재등록 해주세요.(" + fidoResult.getErrorCode() + ")");
                            break;
                        default:
//                            showDialog("FAIL", fidoResult.getDescription() + "(" + fidoResult.getErrorCode() + ")");
                            break;
                    }
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

//                    actFinish();

                    // 실패 사유
                    switch (fidoResult.getErrorCode()) {
                        case FidoResult.RESULT_USER_CANCEL:
                            // 에러코드 999: 사용자 취소
                        case FidoResult.ERROR_JSON_PARSER:
                            // 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
                        case FidoResult.ERROR_CANNOT_USE_FIDO:
                            // 에러코드 1007: 지원하지 않는 단말기
                        case FidoResult.ERROR_UNTRUSTED_FACET_ID:
                            // 에러코드 2001: -등록되지 않은 3rd App으로 진행
                            //			   -FIDO Server에 FacetID 를 등록해야 함.
                        case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
                            // 에러코드 2002: 적합한 인증장치가 없음
                        case FidoResult.ERROR_NETWORK_STATE:
                            // 에러코드 4001: -네트워크 오류
                            //			   -fidoResult.getDescription() 을 통하여 자세한 오류 내용을 알 수 있음
                        case FidoResult.PW_NOT_MATCH_BIO_FINGER:
                            // 에러코드 5000: 지문 인증 실패
                        case FidoResult.ERROR_UNKNOWN:
                            // 에러코드 9999: FIDO 로직 수행 중, 알수 없는 에러 발생
//                            showDialog("FAIL", fidoResult.getDescription() + "(" + fidoResult.getErrorCode() + ")");
                            break;
                        case FidoResult.RESULT_USER_SELECT_REREG:
                            // 에러코드 980: 사용자가 재등록 버튼 클릭
                            Toast.makeText(ActivityRegFido.this, "사용자가 재등록 버튼 클릭" , Toast.LENGTH_SHORT).show();
                            break;
                        case FidoResult.ERROR_ALREADY_REGI_USER:
                            // 에러코드 1008: -이미 등록 된 사용자
                            //             -해지 후 재등록 요청을 해야 함.
//                            showDialog("FAIL", "이미 등록 된 사용자입니다. 해지 후 재등록 해주세요.(" + fidoResult.getErrorCode() + ")");
                            break;
                        default:
//                            showDialog("FAIL", fidoResult.getDescription() + "(" + fidoResult.getErrorCode() + ")");
                            break;
                    }
                }
            }
        }
    };


    private void verrifiedPasscodeUser(String token){

        FidoReq fidoReq = new FidoReq(authCode, devId, "", "PASSCODE", "", token, "R", userId);
        Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityRegFido.this).getService().getVerifyData("application/json",  fidoReq);
        getReList.enqueue(new Callback<ModelVerifiedFido>() {
            @Override
            public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                if (response.code() == 200) {

                    SharedPreferenceUtil.getInstance(ActivityRegFido.this).putTokenA("aToken", response.headers().get("Authorization"));
                    SharedPreferenceUtil.getInstance(ActivityRegFido.this).putAuthCode("authCode", response.body().getContent().getAuthCode());

                    loadingCustomMakingPort.dismiss();

                    if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_FINGERPRINT_TYPE)) {

                        dialogFidoFinger = new DialogFidoFinger(ActivityRegFido.this, fingerResist, fingerCancle);
                        dialogFidoFinger.show();

                    }else{
                        Toast.makeText(ActivityRegFido.this, "지문없이 메인으로 이동" , Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ActivityRegFido.this, ActivityMain.class);
                        startActivity(intent);
//                        actFinish();
                    }

                }
            }
            @Override
            public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
            }
        });
    }


    private void verrifiedFingereUser(String token){

        FidoReq fidoReq = new FidoReq(authCode, devId, "", "FINGER", "", token, "R", userId);
        Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityRegFido.this).getService().getVerifyData("application/json",  fidoReq);
        getReList.enqueue(new Callback<ModelVerifiedFido>() {
            @Override
            public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                if (response.code() == 200) {

                    SharedPreferenceUtil.getInstance(ActivityRegFido.this).putTokenA("aToken", response.headers().get("Authorization"));
                    SharedPreferenceUtil.getInstance(ActivityRegFido.this).putAuthCode("authCode", response.body().getContent().getAuthCode());

                    Intent intent = new Intent(ActivityRegFido.this, ActivityMain.class);
                    startActivity(intent);
//                    actFinish();

                }
            }
            @Override
            public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
            }
        });
    }


//    private void showDialog(String title, String body) {
//
//        Intent alertIntent = new Intent(this, AlertDlg.class);
//        alertIntent.putExtra("msg", body);
//        startActivityForResult(alertIntent, ALERT_REQUEST);
//    }

    private boolean isSuccess = false;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == ALERT_REQUEST && isSuccess == true) {
//            finish();
//        }
//    }

    //지문등록 확인
    private View.OnClickListener fingerResist = new View.OnClickListener() {
        public void onClick(View v) {
            dialogFidoFinger.dismiss();

            reg2 = new FIDORegistration(fidoResult2, ActivityRegFido.this);
            if (userId.length() < 1) {
                Toast.makeText(ActivityRegFido.this, "Please, Fill out the UserID.", Toast.LENGTH_LONG).show();
                return;
            }
            // FIDO
            reg2.registFIDO(userId);
        }
    };

    //지문등록 취소
    private View.OnClickListener fingerCancle = new View.OnClickListener() {
        public void onClick(View v) {
            dialogFidoFinger.dismiss();
            Intent intent = new Intent(ActivityRegFido.this, ActivityMain.class);
            startActivity(intent);
//            actFinish();
        }
    };


}
