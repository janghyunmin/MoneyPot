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

import io.realm.Realm;
import io.realm.RealmResults;
import quantec.com.moneypot.Activity.FIDO.ActivityAuthFidoDouble;
import quantec.com.moneypot.Activity.FIDO.ActivityAuthFidoSingle;
import quantec.com.moneypot.Activity.Login.LoginPage.ActivityLoginPage;
import quantec.com.moneypot.Activity.Login.MemberShipPage.ActivityMemberShipMain;
import quantec.com.moneypot.Activity.Login.Model.dModel.InitReqDto;
import quantec.com.moneypot.Activity.Login.Model.dModel.ModelAuthReqDto;
import quantec.com.moneypot.Activity.Login.Model.dModel.ModelFidoauthReqDto;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelAppInit;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelFlushAuth;
import quantec.com.moneypot.Activity.Main.Foreground;
import quantec.com.moneypot.DataModel.nModel.ModelCommonData;
import quantec.com.moneypot.Database.Realm.CommonCode;
import quantec.com.moneypot.Database.Realm.CommonList.AgeList;
import quantec.com.moneypot.Database.Realm.CommonList.ExpList;
import quantec.com.moneypot.Database.Realm.CommonList.GainList;
import quantec.com.moneypot.Database.Realm.CommonList.IncomeList;
import quantec.com.moneypot.Database.Realm.CommonList.PartnerList;
import quantec.com.moneypot.Database.Realm.CommonList.TimeList;
import quantec.com.moneypot.Database.Realm.CommonList.TypeList;
import quantec.com.moneypot.Database.Realm.CommonList.WeightList;
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

    Realm realm;
    CommonCode commonCode;
    AgeList ageList;
    ExpList expList;
    GainList gainList;
    IncomeList incomeList;
    PartnerList partnerList;
    TimeList timeList;
    TypeList typeList;
    WeightList weightList;

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

        realm = Realm.getDefaultInstance();
        RealmResults<CommonCode> results = realm.where(CommonCode.class)
                .findAllAsync();

        //>>>>>전체 삭제<<<<<//
//        realm.beginTransaction();
//        RealmResults<CommonCode> results = realm.where(CommonCode.class)
//                .findAll();
//        results.deleteAllFromRealm();
//        realm.commitTransaction();
//        realm.close();
        //>>>>>>><<<<<<<<<//

        if(NetworkStateCheck.getNetworkState(this)){
            Toast.makeText(ActivityIntro.this, "네트워크가 연결되어 있습니다.", Toast.LENGTH_SHORT).show();

            if(results.where().count() == 0){
                InsertCommonDate();
            }

            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(ActivityIntro.this, new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String newToken = instanceIdResult.getToken();

                    fcmToken = newToken;
                    SharedPreferenceUtil.getInstance(ActivityIntro.this).putFcmToken("fcmToken", newToken);
                }
            });

            if(fcmToken == null || fcmToken.equals("")){

                fcmToken = SharedPreferenceUtil.getInstance(ActivityIntro.this).getStringExtra("fcmToken");;
            }

            NextPage();

        }
        else{
            Toast.makeText(ActivityIntro.this, "네트워크가 끊겼습니다.", Toast.LENGTH_SHORT).show();
        }

    }//onCreate 끝


    void InsertCommonDate(){

        Call<ModelCommonData> getTest2 = RetrofitClient.getInstance().getService().getCommonData();
        getTest2.enqueue(new Callback<ModelCommonData>() {
            @Override
            public void onResponse(Call<ModelCommonData> call, Response<ModelCommonData> response) {
                if(response.code() == 200) {

                    realm.beginTransaction();
                    commonCode = realm.createObject(CommonCode.class);

                        for(int index = 0 ; index < response.body().getContent().getTYPE().size() ; index++){

                            ageList = realm.createObject(AgeList.class);
                            expList = realm.createObject(ExpList.class);
                            gainList = realm.createObject(GainList.class);
                            incomeList = realm.createObject(IncomeList.class);
                            partnerList = realm.createObject(PartnerList.class);
                            timeList = realm.createObject(TimeList.class);
                            typeList = realm.createObject(TypeList.class);
                            weightList = realm.createObject(WeightList.class);

                            ageList.setCode(response.body().getContent().getAGE().get(index).getCode());
                            ageList.setGrade(response.body().getContent().getAGE().get(index).getGrade());
                            ageList.setQuestion(response.body().getContent().getAGE().get(index).getQuestion());
                            ageList.setType(response.body().getContent().getAGE().get(index).getType());
                            commonCode.getAgeLists().add(ageList);

                            expList.setCode(response.body().getContent().getEXP().get(index).getCode());
                            expList.setGrade(response.body().getContent().getEXP().get(index).getGrade());
                            expList.setQuestion(response.body().getContent().getEXP().get(index).getQuestion());
                            expList.setType(response.body().getContent().getEXP().get(index).getType());
                            commonCode.getExpLists().add(expList);

                            gainList.setCode(response.body().getContent().getGAIN().get(index).getCode());
                            gainList.setGrade(response.body().getContent().getGAIN().get(index).getGrade());
                            gainList.setQuestion(response.body().getContent().getGAIN().get(index).getQuestion());
                            gainList.setType(response.body().getContent().getGAIN().get(index).getType());
                            commonCode.getGainLists().add(gainList);

                            incomeList.setCode(response.body().getContent().getINCOME().get(index).getCode());
                            incomeList.setGrade(response.body().getContent().getINCOME().get(index).getGrade());
                            incomeList.setQuestion(response.body().getContent().getINCOME().get(index).getQuestion());
                            incomeList.setType(response.body().getContent().getINCOME().get(index).getType());
                            commonCode.getIncomeLists().add(incomeList);

                            partnerList.setCode(response.body().getContent().getPARTNER().get(index).getCode());
                            partnerList.setGrade(response.body().getContent().getPARTNER().get(index).getGrade());
                            partnerList.setQuestion(response.body().getContent().getPARTNER().get(index).getQuestion());
                            partnerList.setType(response.body().getContent().getPARTNER().get(index).getType());
                            commonCode.getPartnerLists().add(partnerList);

                            timeList.setCode(response.body().getContent().getTIME().get(index).getCode());
                            timeList.setGrade(response.body().getContent().getTIME().get(index).getGrade());
                            timeList.setQuestion(response.body().getContent().getTIME().get(index).getQuestion());
                            timeList.setType(response.body().getContent().getTIME().get(index).getType());
                            commonCode.getTimeLists().add(timeList);

                            typeList.setCode(response.body().getContent().getTYPE().get(index).getCode());
                            typeList.setGrade(response.body().getContent().getTYPE().get(index).getGrade());
                            typeList.setQuestion(response.body().getContent().getTYPE().get(index).getQuestion());
                            typeList.setType(response.body().getContent().getTYPE().get(index).getType());
                            commonCode.getTypeLists().add(typeList);

                            weightList.setCode(response.body().getContent().getWEIGHT().get(index).getCode());
                            weightList.setGrade(response.body().getContent().getWEIGHT().get(index).getGrade());
                            weightList.setQuestion(response.body().getContent().getWEIGHT().get(index).getQuestion());
                            weightList.setType(response.body().getContent().getWEIGHT().get(index).getType());
                            commonCode.getWeightLists().add(weightList);
                        }

                    realm.commitTransaction();
                }
            }
            @Override
            public void onFailure(Call<ModelCommonData> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });
    }

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

        }
        else{

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
            }
            else{
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