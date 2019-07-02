package quantec.com.moneypot.Activity.Intro;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import quantec.com.moneypot.Activity.FIDO.ActivityAuthFidoDouble;
import quantec.com.moneypot.Activity.FIDO.ActivityAuthFidoSingle;
import quantec.com.moneypot.Activity.Login.LoginPage.ActivityLoginPage;
import quantec.com.moneypot.Activity.Login.MemberShipPage.ActivityMemberShipMain;
import quantec.com.moneypot.Activity.Login.Model.dModel.InitReqDto;
import quantec.com.moneypot.Activity.Login.Model.dModel.ModelAuthReqDto;
import quantec.com.moneypot.Activity.Login.Model.dModel.ModelFidoauthReqDto;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelAppInit;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelFlushAuth;
import quantec.com.moneypot.Dialog.DialogLoadingMakingPort;
import quantec.com.moneypot.FIDO.PropertyManager;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.util.NetworkStateCheck.NetworkStateCheck;
import quantec.com.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityIntro extends AppCompatActivity {

    String userId, userCid, authCode;
    boolean fingerState;
    private MagicFIDOUtil mFidoUtil = null;
    private DialogLoadingMakingPort loadingCustomMakingPort;

    private String fcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        userId = SharedPreferenceUtil.getInstance(ActivityIntro.this).getStringExtra("userId");
        userCid = SharedPreferenceUtil.getInstance(ActivityIntro.this).getStringExtra("userCid");
        authCode = SharedPreferenceUtil.getInstance(ActivityIntro.this).getStringExtra("authCode");
        fingerState = SharedPreferenceUtil.getInstance(ActivityIntro.this).getStateExtra("fingerState");

        PropertyManager.load(this);
        mFidoUtil = new MagicFIDOUtil(this);

        if(NetworkStateCheck.getNetworkState(this)){
            Toast.makeText(ActivityIntro.this, "네트워크가 연결되어 있습니다.", Toast.LENGTH_SHORT).show();

            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ActivityIntro.this, new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String newToken = instanceIdResult.getToken();
                    Log.e("newToken", newToken);

                    fcmToken = newToken;
                    SharedPreferenceUtil.getInstance(ActivityIntro.this).putFcmToken("fcmToken", newToken);
                }
            });

            if(fcmToken == null || fcmToken.equals("")){

                fcmToken = SharedPreferenceUtil.getInstance(ActivityIntro.this).getStringExtra("fcmToken");;
            }

            NextPage();

        }else{
            Toast.makeText(ActivityIntro.this, "네트워크가 끊겼습니다.", Toast.LENGTH_SHORT).show();
        }

    }//onCreate 끝

    void NextPage(){

        if(userId.isEmpty()){

            Intent intent = new Intent(this, ActivityMemberShipMain.class);
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }
            }, 1300);

        }else{

            ModelFidoauthReqDto fidoauthReqDto = new ModelFidoauthReqDto(authCode, "PASSCODE", userId);
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String data = gson.toJson(fidoauthReqDto).replace("\\n", "").replace(" ", "")
                    .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
            Call<ModelFlushAuth> tt = RetrofitClient.getInstance(ActivityIntro.this).getService().getFlushAuthData("application/json", data);
            tt.enqueue(new Callback<ModelFlushAuth>() {
                @Override
                public void onResponse(Call<ModelFlushAuth> call, Response<ModelFlushAuth> response) {
                    if(response.code() == 200) {
                        SharedPreferenceUtil.getInstance(ActivityIntro.this).putAuthCode("authCode", response.body().getContent().getAuthCode());
                        SharedPreferenceUtil.getInstance(ActivityIntro.this).putTokenA("aToken", response.headers().get("Authorization"));

                        InitReqDto initReqDto = new InitReqDto(authCode, userCid, fcmToken, userId);
                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                        String jsonItentifyData = gson.toJson(initReqDto).replace("\\n", "").replace(" ", "")
                                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");

                        Call<ModelAppInit> getReList = RetrofitClient.getInstance().getService().getAppInitData("application/json",jsonItentifyData);
                        getReList.enqueue(new Callback<ModelAppInit>() {
                            @Override
                            public void onResponse(Call<ModelAppInit> call, Response<ModelAppInit> response) {
                                if (response.code() == 200) {
                                    if(response.body().getStatus() == 200){
                                        if(response.body().getContent().getActiveStep() >= 10) {

                                            // FIDO PASSCODE 사용 가능 여부 체크
                                            if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE)){

                                                ModelAuthReqDto initReqDto = new ModelAuthReqDto(authCode, "",userId);
                                                Call<ModelRegChk> getRegChkData = RetrofitClient.getInstance().getService().getRegChkData("application/json", initReqDto);
                                                getRegChkData.enqueue(new Callback<ModelRegChk>() {
                                                    @Override
                                                    public void onResponse(Call<ModelRegChk> call, Response<ModelRegChk> response) {
                                                        if(response.code() == 200) {

                                                            boolean finger = false;
                                                            boolean passcode = false;

                                                            for(int index = 0 ; index < response.body().getContent().size() ; index++) {

                                                                if(response.body().getContent().get(index).getSite().equals("FINGER")){
                                                                    finger = response.body().getContent().get(index).isFidoReg();
                                                                }else if(response.body().getContent().get(index).getSite().equals("PASSCODE")){
                                                                    passcode = response.body().getContent().get(index).isFidoReg();
                                                                }
                                                            }

                                                            MovedFidoPage(passcode, finger);
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(Call<ModelRegChk> call, Throwable t) {
                                                        Toast.makeText(ActivityIntro.this, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }else{

                                                loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityIntro.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                                                loadingCustomMakingPort.show();

                                                Toast.makeText(ActivityIntro.this, "파이도 사용 불가능 로그인 페이지 이동", Toast.LENGTH_SHORT).show();

                                                final Intent intent1 = new Intent(ActivityIntro.this, ActivityLoginPage.class);
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
                                        }
                                        else{

                                            loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityIntro.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                                            loadingCustomMakingPort.show();

                                            Toast.makeText(ActivityIntro.this, "액티브스텝이 10이상이 아니므로 가입페이지 이동", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(ActivityIntro.this, ActivityMemberShipMain.class);
                                            Handler mHandler = new Handler();
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    loadingCustomMakingPort.dismiss();
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }, 1300);
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<ModelAppInit> call, Throwable t) {
                            }
                        });

                    }
                }
                @Override
                public void onFailure(Call<ModelFlushAuth> call, Throwable t) {
                }
            });

        }
    }


    public void MovedFidoPage(boolean passCode, boolean finger) {

        if(passCode && finger){

            // FIDO FINGER 사용 가능 여부 체크
            if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_FINGERPRINT_TYPE)){
                //지문 사용 ON
                if(fingerState){

                    Toast.makeText(this, "지문사용 ON으로 핑거 인증으로 이동", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ActivityIntro.this, ActivityAuthFidoDouble.class);
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.putExtra("site", "FINGER");
                            startActivity(intent);
                            finish();
                        }
                    }, 100);

                }
                //지문 사용 OFF
                else{

                    Toast.makeText(this, "지문사용 OFF로 패스코드 인증으로 이동", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ActivityIntro.this, ActivityAuthFidoSingle.class);
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.putExtra("site", "PASSCODE");
                            startActivity(intent);
                            finish();
                        }
                    }, 100);

                }

            }else{
                Toast.makeText(this, "패스코드 인증으로 이동", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ActivityIntro.this, ActivityAuthFidoSingle.class);
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("site", "PASSCODE");
                        startActivity(intent);
                        finish();
                    }
                }, 100);
            }
        }
        else if(passCode){

            Toast.makeText(this, "패스코드 인증으로 이동", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ActivityIntro.this, ActivityAuthFidoSingle.class);
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("site", "PASSCODE");
                    startActivity(intent);
                    finish();
                }
            }, 100);
        }
        else{

            Intent intent = new Intent(ActivityIntro.this, ActivityLoginPage.class);
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                }
            }, 100);

            Toast.makeText(this, "파이도 재등록", Toast.LENGTH_SHORT).show();
        }
    }
}