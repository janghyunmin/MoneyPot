package quantec.com.moneypot.Activity.Intro;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import quantec.com.moneypot.Activity.FIDO.ActivityAuthFidoDouble;
import quantec.com.moneypot.Activity.FIDO.ActivityAuthFidoSingle;
import quantec.com.moneypot.Activity.Login.LoginPage.ActivityLoginPage;
import quantec.com.moneypot.Activity.Login.MemberShipPage.ActivityMemberShipMain;
import quantec.com.moneypot.Activity.Login.Model.dModel.InitReqDto;
import quantec.com.moneypot.Activity.Login.Model.dModel.ModelAuthReqDto;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelAppInit;
import quantec.com.moneypot.Dialog.DialogLoadingMakingPort;
import quantec.com.moneypot.FIDO.PropertyManager;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityIntro extends AppCompatActivity {

    String userId, userCid, authCode;
    boolean fingerState;
    private MagicFIDOUtil mFidoUtil = null;
    private DialogLoadingMakingPort loadingCustomMakingPort;

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


//        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);

//        Glide.with(this).load(R.drawable.source).into(imageViewTarget);
//            Glide.with(this)
//                    .load(R.raw.loading)
//                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
////                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                    .into(imageView);

//        ModelFidoauthReqDto withdrawFido = new ModelFidoauthReqDto(authCode, "PASSCODE", userId);
//        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//        String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
//                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
//
//        Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(ActivityIntro.this).getService().getFidoAuthCode("application/json", jsonItentifyData);
//        getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
//            @Override
//            public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
//                if(response.code() == 200) {
//                    Log.e("겟오스코드", "값 : "+ response.body().getContent().getAuthCode());
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelFidoAuthCode> call, Throwable t) {
//            }
//        });


//        ModelFidoauthReqDto a = new ModelFidoauthReqDto("634666", "PASSCODE", userId);
//        Gson g = new GsonBuilder().disableHtmlEscaping().create();
//        String d = g.toJson(a).replace("\\n", "").replace(" ", "")
//                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
//
//        Call<ModelFlushAuth> tt = RetrofitClient.getInstance(ActivityIntro.this).getService().getFlushAuthData("application/json", d);
//        tt.enqueue(new Callback<ModelFlushAuth>() {
//            @Override
//            public void onResponse(Call<ModelFlushAuth> call, Response<ModelFlushAuth> response) {
//                if(response.code() == 200) {
//                    SharedPreferenceUtil.getInstance(ActivityIntro.this).putAuthCode("authCode", response.body().getContent().getAuthCode());
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelFlushAuth> call, Throwable t) {
//            }
//        });

        NextPageModve();

//        ModelWithdrawFido withdrawFido = new ModelWithdrawFido(authCode, "PASSCODE", userId);
//        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//        String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
//                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");

//        Call<ModelFlushAuth> getRegChkData = RetrofitClient.getInstance(ActivityAuthFidoDouble.this).getService().getFlushAuthData("application/json", jsonItentifyData);
//        getRegChkData.enqueue(new Callback<ModelFlushAuth>() {
//            @Override
//            public void onResponse(Call<ModelFlushAuth> call, Response<ModelFlushAuth> response) {
//                if(response.code() == 200) {
//
//                    SharedPreferenceUtil.getInstance(ActivityAuthFidoDouble.this).putTokenA("aToken", response.headers().get("Authorization"));
//                    SharedPreferenceUtil.getInstance(ActivityAuthFidoDouble.this).putAuthCode("authCode", response.body().getAuthcode());
//
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelFlushAuth> call, Throwable t) {
//                Toast.makeText(ActivityAuthFidoDouble.this, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
//            }
//        });


    }//onCreate 끝

    void NextPageModve(){

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

//            Intent intent = new Intent(this, ActivityMemberShipMain.class);
//            Handler mHandler = new Handler();
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    startActivity(intent);
//                    finish();
//                }
//            }, 1300);

        }else{

            InitReqDto initReqDto = new InitReqDto(authCode, userCid,"", userId);
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


    public void MovedFidoPage(boolean passcod, boolean finger) {

        if(passcod && finger){

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
        else if(passcod){

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

    public NetworkInfo getNetworkState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

}


//public class ActivityIntro extends AppCompatActivity {
//    String secretKey, userId, userCid;
//    private MagicFIDOUtil mFidoUtil = null;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_intro);
//
////        secretKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAw6woEWfRDJRSgnpypp2zsXMlQdK1yMLleTGPxoXAccoyhVVrYTfDthj6kdA3izMb9BlzljxVqnqgd7o1qP/P7QIDAQABAkAtmCTyAVXl733WZ3/lsSOY+rK4xseTE07zUYgmAy6XlNR4HgfAjehTWkaD5r4GGwLrm4AVJzXFlQNQKDwR2xtBAiEA7cNbuEplv/SCjpyLFWsaDSIqR3UHi6iOWaVG5aMuHtECIQDSrlHhHNxtUEEUVKskbLDrtM75bM5YxYKD/YYzm0g+XQIgUtnRez2JiUM1v2YzXQpOMKdoHjBcqcDZocljFCAxsRECIDbaKflJUV6ooXkQnkpqfhmwRp8MJCFasyN4U4OGL2YFAiBIvxAz4xHaXeM5THAAOGZ/CYK6IELJ4kDnroMZinsJlQ==";
////        try {
////            JTdecoded("eyJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJ3ZWIiLCJyb2xlIjoiIiwidXNlckxldmVsIjpudWxsLCJvcyI6IndlYiIsInBhbmFsdHkiOjE1NTM4NTAwNDU0MTMsImlzcyI6IjEwMDAwMDAxMjMiLCJ1c2VyTmFtZSI6IkxUVlR5WHUxQjBZK0VRM1lGSG4vOGs4T2VYcVE0NWQycnJWZTBIclhsMkhuOHFQdkI1T01PbEc2SXBnbngzNnlUSTc3cGVxU2JzMFN6L2FWaC9FK1dnPT0iLCJleHAiOjE1NTQxNjg3ODYsImlhdCI6MTU1NDE2ODQyNn0.XxMtDDf6CRS3KMqdK4nsoeVoWS-3xPOcz-nL-KO40scXXG_UZswdCqt6os5-kyaDiS8T2C5jYbG2_sQvOSrQiA");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//
////        userId = SharedPreferenceUtil.getInstance(ActivityIntro.this).getStringExtra("userId");
////        userCid = SharedPreferenceUtil.getInstance(ActivityIntro.this).getStringExtra("userCid");
////
////        PropertyManager.load(this);
////        mFidoUtil = new MagicFIDOUtil(this);
//
////        if(getNetworkState() != null && getNetworkState().isConnected()) {
////            if (getNetworkState().getType() == ConnectivityManager.TYPE_WIFI) {
////                Toast.makeText(IntroScreen.this, "와이파이 연결됨",Toast.LENGTH_SHORT).show();
////            } else if (getNetworkState().getType() == ConnectivityManager.TYPE_MOBILE) {
////                Toast.makeText(IntroScreen.this, "LTE 연결됨",Toast.LENGTH_SHORT).show();
////            }
////        } else {
////            Toast.makeText(IntroScreen.this, "인터넷 연결 안됨",Toast.LENGTH_SHORT).show();
////        }
////        Call<Object> getTest = RetrofitClient.getInstance().getService().getTest("MP0001",1,100);
////        getTest.enqueue(new Callback<Object>() {
////            @Override
////            public void onResponse(Call<Object> call, Response<Object> response) {
////                if(response.code() == 200) {
////                    Log.e("스프링 테스트", "값 : "+ response.body().toString());
////                }else{
////                    Log.e("응답 실패","값 : "+ response.code()+"이유 : "+ response.message());
////                }
////            }
////            @Override
////            public void onFailure(Call<Object> call, Throwable t) {
////                Log.e("레트로핏 실패","값 : "+t.getMessage());
////            }
////        });
////
////        Call<ModelTest2> getTest2 = RetrofitClient.getInstance().getService().getTest2(0,0,10);
////        getTest2.enqueue(new Callback<ModelTest2>() {
////            @Override
////            public void onResponse(Call<ModelTest2> call, Response<ModelTest2> response) {
////                if(response.code() == 200) {
////                    Log.e("모델테스트2 스프링 테스트", "에러 코드 값 : "+ response.body().getErrorcode());
////                    Log.e("모델테스트2 스프링 테스트", "포트 이름 값 : "+ response.body().getContent().get(0).getName());
////                    Log.e("모델테스트2 스프링 테스트", "포트 코드 값 : "+ response.body().getContent().get(0).getStCode());
////                    Log.e("모델테스트2 스프링 테스트", "페이지 사이즈 값 : "+ response.body().getPage().getPageSize());
////                    Log.e("모델테스트2 스프링 테스트", "소트 엠티 값 : "+ response.body().getPage().getSort().isEmpty());
////                }else{
////                    Gson gson = new GsonBuilder().create();
////                    ErrorPojoClass mError = new ErrorPojoClass();
////                    try {
////                        mError= gson.fromJson(response.errorBody().string(),ErrorPojoClass .class);
////                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getDetails());
////                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getMessage());
////                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getTimestamp());
////                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getErrorcode());
////                    } catch (IOException e) {
////                        // handle failure to read error
////                    }
////                }
////            }
////            @Override
////            public void onFailure(Call<ModelTest2> call, Throwable t) {
////                Log.e("레트로핏 실패","값 : "+t.getMessage());
////            }
////        });
//
///**
//        OneMap oneMap = new OneMap();
//        Call<Object> getTest2 = RetrofitClient.getInstance().getService().getTestLogin22("application/json", "안녕하세요", "WHF%2FF%2BMWwMIkkyqdyxLOiepRgM8sNWvlYEgGJmZKKZE%3D", oneMap);
//        getTest2.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//                if(response.code() == 200) {
//
//                    Log.e("받음값","값 : "+response.body().toString());
//
//                }
//            }
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Log.e("레트로핏 실패","값 : "+t.getMessage());
//            }
//        });
//*/
//
//
////        try {
////            // RSA 키쌍을 생성합니다.
////            KeyPair keyPair = EncrypteUtil.genRSAKeyPair();
////            PublicKey publicKey = keyPair.getPublic();
////            PrivateKey privateKey = keyPair.getPrivate();
////            String plainText = "암호화 할 문자열 입니다.";
////            // Base64 인코딩된 암호화 문자열 입니다.
////            String encrypted = EncrypteUtil.encryptRSA(plainText, publicKey);
////            Log.e("A","encrypted : " + encrypted);
////            // 복호화 합니다.
////            String decrypted = EncrypteUtil.decryptRSA(encrypted, privateKey);
////            Log.e("B","decrypted : " + decrypted);
////            // Base64 인코딩된 암호화 문자열 입니다.
////            String encrypted2 = EncrypteUtil.encryptRSA(plainText, privateKey);
////            Log.e("C","encrypted2 : " + encrypted2);
////            // 복호화 합니다.
////            String decrypted2 = EncrypteUtil.decryptRSA(encrypted2, publicKey);
////            Log.e("D","decrypted2 : " + decrypted2);
////            // 공개키를 Base64 인코딩한 문자일을 만듭니다.
//////            String base64PublicKey = Base64.encodeBase64String(publicKey.getEncoded());//Base64.getEncoder().encodeToString(bytePublicKey);
////            String base64PublicKey = Base64.encodeToString(publicKey.getEncoded(), Base64.NO_WRAP);//Base64.getEncoder().encodeToString(bytePublicKey);
////            Log.e("E","Base64 Public Key : " + base64PublicKey);
////            // 개인키를 Base64 인코딩한 문자열을 만듭니다.
//////            String base64PrivateKey = Base64.encodeBase64String(privateKey.getEncoded());//Base64.getEncoder().encodeToString(bytePrivateKey);
////            String base64PrivateKey = Base64.encodeToString(privateKey.getEncoded(), Base64.NO_WRAP);//Base64.getEncoder().encodeToString(bytePrivateKey);
////            Log.e("F","Base64 Private Key : " + base64PrivateKey);
////
////            Log.e("TEST1","Encrypted By key : " + EncrypteUtil.encryptRSA(plainText, EncrypteUtil.genRSAKeyPublic(base64PublicKey)));
////            Log.e("TEST2","Decrypted By key : " + EncrypteUtil.decryptRSA(encrypted, EncrypteUtil.genRSAKeyPrivate(base64PrivateKey)));
////
////            Log.e("TEST3","Encrypted2 By key : " + EncrypteUtil.encryptRSA(plainText, EncrypteUtil.genRSAKeyPrivate(base64PrivateKey)));
////            Log.e("TEST4","Decrypted2 By key : " + EncrypteUtil.decryptRSA(encrypted2, EncrypteUtil.genRSAKeyPublic(base64PublicKey)));
////
////        } catch (NoSuchPaddingException e) {
////            e.printStackTrace();
////        } catch (NoSuchAlgorithmException e) {
////            e.printStackTrace();
////        } catch (InvalidKeyException e) {
////            e.printStackTrace();
////        } catch (BadPaddingException e) {
////            e.printStackTrace();
////        } catch (IllegalBlockSizeException e) {
////            e.printStackTrace();
////        } catch (UnsupportedEncodingException e) {
////            e.printStackTrace();
////        } catch (InvalidKeySpecException e) {
////            e.printStackTrace();
////        }
////
////        String plainText = "안녕하세요";
////        String key = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAw6woEWfRDJRSgnpypp2zsXMlQdK1yMLleTGPxoXAccoyhVVrYTfDthj6kdA3izMb9BlzljxVqnqgd7o1qP/P7QIDAQABAkAtmCTyAVXl733WZ3/lsSOY+rK4xseTE07zUYgmAy6XlNR4HgfAjehTWkaD5r4GGwLrm4AVJzXFlQNQKDwR2xtBAiEA7cNbuEplv/SCjpyLFWsaDSIqR3UHi6iOWaVG5aMuHtECIQDSrlHhHNxtUEEUVKskbLDrtM75bM5YxYKD/YYzm0g+XQIgUtnRez2JiUM1v2YzXQpOMKdoHjBcqcDZocljFCAxsRECIDbaKflJUV6ooXkQnkpqfhmwRp8MJCFasyN4U4OGL2YFAiBIvxAz4xHaXeM5THAAOGZ/CYK6IELJ4kDnroMZinsJlQ==";
////
////        CryptLib cryptLib = null;
////        try {
////            cryptLib = new CryptLib();
////        } catch (NoSuchAlgorithmException e) {
////            e.printStackTrace();
////        } catch (NoSuchPaddingException e) {
////            e.printStackTrace();
////        }
////
////
////        String cipherText = null;
////        try {
////            cipherText = cryptLib.encryptPlainTextWithRandomIV(plainText, key, true);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////        Log.e("깃헙테스트", " 값  : "+cipherText);
////
////        String decryptedString = null;
////        try {
////            decryptedString = cryptLib.decryptCipherTextWithRandomIV(cipherText, key, true);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        Log.e("깃헙테스트2", " 값2  : "+decryptedString);
//
//
////        User user = null;
////        try {
////            user = new User("afasdfsdf", md5("!quant0330"), "quantec");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////        Call<Object> getTest2 = RetrofitClient.getInstance().getService().getTestLogin("application/json", user);
////        getTest2.enqueue(new Callback<Object>() {
////            @Override
////            public void onResponse(Call<Object> call, Response<Object> response) {
////                if(response.code() == 200) {
////
//////                    Log.e("권한","값 : "+response.headers().get("Authorization"));
//////                    Key key = Keys.secretKeyFor(SignatureAlgorithm.RS256);
////
//////                    String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
//////                    Log.e("풀린값","값 : "+ Jwts.parser().setSigningKey(key).parseClaimsJws(response.headers().get("Authorization")));
////                    secretKey = response.headers().get("Bare");
////                    try {
////                        JTdecoded(response.headers().get("Authorization"));
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
////            @Override
////            public void onFailure(Call<Object> call, Throwable t) {
////                Log.e("레트로핏 실패","값 : "+t.getMessage());
////            }
////        });
////        Call<ModelZzimCount> getData = RetrofitClient.getInstance().getService().getPortCallData(1);
////        getData.enqueue(new Callback<ModelZzimCount>() {
////            @Override
////            public void onResponse(Call<ModelZzimCount> call, Response<ModelZzimCount> response) {
////                if (response.code() == 200) {
////                    SharedPreferenceUtil.getInstance(ActivityIntro.this).putIntZzimCount("PortZzimCount", response.body().getTotalNum());
////                    NextPageModve();
////                } else if (response.code() == 301) {
////                    SharedPreferenceUtil.getInstance(ActivityIntro.this).putIntZzimCount("PortZzimCount", 0);
////                    NextPageModve();
////                }
////            }
////            @Override
////            public void onFailure(Call<ModelZzimCount> call, Throwable t) {
////                Log.e("레트로핏 실패", "값 : " + t.getMessage());
////            }
////        });
////        NextPageModve();
//    }//onCreate 끝
//
////    @Override
////    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
////        PermissionsPhone.onRequestPermissionsResult(this, requestCode, grantResults);
////    }
////
////    @NeedsPermission({Manifest.permission.READ_PHONE_STATE})
////    public void getPhoneNumber(){
////        TelephonyManager mgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
////        @SuppressLint("MissingPermission") String phoneNumber = mgr.getLine1Number();
////        if(!TextUtils.isEmpty(phoneNumber)){
////            phoneNumber = phoneNumber.replace("-","").replace("+82","0");
////            Log.e("전화번호","값 : "+phoneNumber);
////        }
////    }
//
//    //        public static String decrypt(String encrypted, Key pKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
////                Cipher cipher = Cipher.getInstance("RSA");
//////                byte[] bytes = Base64coder.decode(encrypted);
////            byte[] bytes = Base64.decode(encrypted, 0);
////            cipher.init(Cipher.DECRYPT_MODE, pKey);
////                byte[] bytePlain = cipher.doFinal(bytes);
////                String decrypted = new String(bytePlain, "utf-8");
////                return decrypted;
////        }
////        public PublicKey PublicKey(String base64Str) throws NoSuchAlgorithmException, InvalidKeySpecException {
////            KeySpec keySpec = new X509EncodedKeySpec(Base64.decode(base64Str.trim(), 0));
////            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
////            return keyFactory.generatePublic(keySpec);
////        }
//
//        public  void JTdecoded(String JWTEncoded) throws Exception {
//                String[] split = JWTEncoded.split("\\.");
//                Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
//                Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
//
//                Gson gson = new Gson();
//                MyData data = gson.fromJson(getJson(split[1]), MyData.class);
//
//                Log.e("이름","값:"+data.userName);
//                Log.e("키","값:"+secretKey);
//
////                String base64PrivateKey = Base64.encodeToString(secretKey.getBytes("utf-8"), Base64.NO_WRAP);//Base64.getEncoder().encodeToString(bytePrivateKey);
////                Log.e("TEST5","최종값 :" + EncrypteUtil.decryptRSA(data.userName, EncrypteUtil.genRSAKeyPublic(secretKey)));
//            Log.e("TEST5","최종값 :" + EncrypteUtil.decryptRSA(data.userName.trim(), EncrypteUtil.genRSAKeyPrivate(secretKey)));
//        }
//
//        private  String getJson(String strEncoded) throws UnsupportedEncodingException {
//            byte[] decode =  android.util.Base64.decode(strEncoded,  Base64.URL_SAFE);
//            return new String(decode, "utf-8");
//        }
//
////    String enccriptData(String txt) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
////        String encoded = "";
////        byte[] encrypted = null;
////
////            byte[] publicBytes = Base64.decode(secretKey.trim(), Base64.NO_WRAP);
////            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
////            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
////            PublicKey pubKey = keyFactory.generatePublic(keySpec);
////            Cipher cipher = Cipher.getInstance("RSA"); //or try with "RSA"
////            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
////            encrypted = cipher.doFinal(Base64.decode(txt,Base64.NO_WRAP));
////        String decrypted = new String(encrypted, "utf-8");
//////            encoded = Base64.encodeToString(encrypted, Base64.DEFAULT);
////
////        return decrypted;
////    }
//
//    public static final String md5(final String s) {
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = java.security.MessageDigest
//                    .getInstance("MD5");
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//
//            // Create Hex String
//            StringBuffer hexString = new StringBuffer();
//            for (int i = 0; i < messageDigest.length; i++) {
//                String h = Integer.toHexString(0xFF & messageDigest[i]);
//                while (h.length() < 2)
//                    h = "0" + h;
//                hexString.append(h);
//            }
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//    void NextPageModve(){
//
//        if(userId.isEmpty()){
//            Intent intent = new Intent(this, ActivityMemberShipMain.class);
//            Handler mHandler = new Handler();
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    startActivity(intent);
//                    finish();
//                }
//            }, 1300);
//
//        }else{
//
//            InitReqDto initReqDto = new InitReqDto(userCid, "", userId);
//            Call<ModelAppInit> getReList = RetrofitClient.getInstance().getService().getAppInitData("application/json",initReqDto);
//            getReList.enqueue(new Callback<ModelAppInit>() {
//                @Override
//                public void onResponse(Call<ModelAppInit> call, Response<ModelAppInit> response) {
//                    if (response.code() == 200) {
//
//                        if(response.body().getStatus() == 200){
//
//                            if(response.body().getContent().getActiveStep() >= 10) {
//
//                                // FIDO 사용 가능 여부 체크
//                                if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE)){
//
//                                    Call<ModelRegChk> getRegChkData = RetrofitClient.getInstance().getService().getRegChkData(userId);
//                                    getRegChkData.enqueue(new Callback<ModelRegChk>() {
//                                        @Override
//                                        public void onResponse(Call<ModelRegChk> call, Response<ModelRegChk> response) {
//                                            if(response.code() == 200) {
//
//                                                SharedPreferenceUtil.getInstance(ActivityIntro.this).putAuthCode("authCode", response.body().getContent().get(0).getAuthcode());
//
//                                                MovedFidoPage(response.body().getContent().get(1).isFidoReg(), response.body().getContent().get(0).isFidoReg());
//
//                                            }
//                                        }
//                                        @Override
//                                        public void onFailure(Call<ModelRegChk> call, Throwable t) {
//                                            Toast.makeText(ActivityIntro.this, "서버가 불안정합니다\n잠시 후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                                }else{
//                                    Toast.makeText(ActivityIntro.this, "파이도 사용 불가능 로그인 페이지 이동", Toast.LENGTH_SHORT).show();
//
//                                    final Intent intent1 = new Intent(ActivityIntro.this, ActivityLoginPage.class);
//                                    Handler mHandler = new Handler();
//                                    mHandler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                            startActivity(intent1);
//                                            finish();
//                                        }
//                                    }, 1300);
//                                }
//                            }
//                            else{
//                                Toast.makeText(ActivityIntro.this, "액티브스텝이 10이상이 아니므로 가입페이지 이동", Toast.LENGTH_SHORT).show();
//
//                                Intent intent = new Intent(ActivityIntro.this, ActivityMemberShipMain.class);
//                                Handler mHandler = new Handler();
//                                mHandler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                }, 1300);
//                            }
//                        }
//                    }
//                }
//                @Override
//                public void onFailure(Call<ModelAppInit> call, Throwable t) {
//                }
//            });
//        }
//    }
//    public void MovedFidoPage(boolean passcod, boolean finger) {
//
//        if(passcod && finger){
//            Toast.makeText(this, "핑거로 이동", Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(ActivityIntro.this, ActivityAuthFidoDouble.class);
//            Handler mHandler = new Handler();
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    intent.putExtra("site", "FINGER");
//                    startActivity(intent);
//                    finish();
//                }
//            }, 1300);
//
//        }
//        else if(passcod){
//            Toast.makeText(this, "패스코드 이동", Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(ActivityIntro.this, ActivityAuthFidoDouble.class);
//            Handler mHandler = new Handler();
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                    intent.putExtra("site", "PASSCODE");
//                    startActivity(intent);
//                    finish();
//                }
//            }, 1300);
//
//        }
//        else{
//            Toast.makeText(this, "파이도 재등록", Toast.LENGTH_SHORT).show();
//        }
//    }
//    public NetworkInfo getNetworkState() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return networkInfo;
//    }
//}