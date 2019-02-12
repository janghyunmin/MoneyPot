package quantec.com.moneypot.Activity.Intro;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import permissions.dispatcher.NeedsPermission;
import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Model.nModel.ModelZzimCount;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.Util.Permissions.PermissionsPhone;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityIntro extends AppCompatActivity {

    String secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

//        if(getNetworkState() != null && getNetworkState().isConnected()) {
//            if (getNetworkState().getType() == ConnectivityManager.TYPE_WIFI) {
//                Toast.makeText(IntroScreen.this, "와이파이 연결됨",Toast.LENGTH_SHORT).show();
//            } else if (getNetworkState().getType() == ConnectivityManager.TYPE_MOBILE) {
//                Toast.makeText(IntroScreen.this, "LTE 연결됨",Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(IntroScreen.this, "인터넷 연결 안됨",Toast.LENGTH_SHORT).show();
//        }


//        Call<Object> getTest = RetrofitClient.getInstance().getService().getTest("MP0001",1,100);
//        getTest.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//                if(response.code() == 200) {
//                    Log.e("스프링 테스트", "값 : "+ response.body().toString());
//                }else{
//                    Log.e("응답 실패","값 : "+ response.code()+"이유 : "+ response.message());
//                }
//            }
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Log.e("레트로핏 실패","값 : "+t.getMessage());
//            }
//        });
//
//        Call<ModelTest2> getTest2 = RetrofitClient.getInstance().getService().getTest2(0,0,10);
//        getTest2.enqueue(new Callback<ModelTest2>() {
//            @Override
//            public void onResponse(Call<ModelTest2> call, Response<ModelTest2> response) {
//                if(response.code() == 200) {
//                    Log.e("모델테스트2 스프링 테스트", "에러 코드 값 : "+ response.body().getErrorcode());
//                    Log.e("모델테스트2 스프링 테스트", "포트 이름 값 : "+ response.body().getContent().get(0).getName());
//                    Log.e("모델테스트2 스프링 테스트", "포트 코드 값 : "+ response.body().getContent().get(0).getStCode());
//                    Log.e("모델테스트2 스프링 테스트", "페이지 사이즈 값 : "+ response.body().getPage().getPageSize());
//                    Log.e("모델테스트2 스프링 테스트", "소트 엠티 값 : "+ response.body().getPage().getSort().isEmpty());
//                }else{
//                    Gson gson = new GsonBuilder().create();
//                    ErrorPojoClass mError = new ErrorPojoClass();
//                    try {
//                        mError= gson.fromJson(response.errorBody().string(),ErrorPojoClass .class);
//                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getDetails());
//                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getMessage());
//                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getTimestamp());
//                        Log.e("스프링 에러", "에러메시지 값 : "+ mError.getErrorcode());
//                    } catch (IOException e) {
//                        // handle failure to read error
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelTest2> call, Throwable t) {
//                Log.e("레트로핏 실패","값 : "+t.getMessage());
//            }
//        });


//        try {
//            // RSA 키쌍을 생성합니다.
//            KeyPair keyPair = EncrypteUtil.genRSAKeyPair();
//            PublicKey publicKey = keyPair.getPublic();
//            PrivateKey privateKey = keyPair.getPrivate();
//            String plainText = "암호화 할 문자열 입니다.";
//            // Base64 인코딩된 암호화 문자열 입니다.
//            String encrypted = EncrypteUtil.encryptRSA(plainText, publicKey);
//            Log.e("A","encrypted : " + encrypted);
//            // 복호화 합니다.
//            String decrypted = EncrypteUtil.decryptRSA(encrypted, privateKey);
//            Log.e("B","decrypted : " + decrypted);
//            // Base64 인코딩된 암호화 문자열 입니다.
//            String encrypted2 = EncrypteUtil.encryptRSA(plainText, privateKey);
//            Log.e("C","encrypted2 : " + encrypted2);
//            // 복호화 합니다.
//            String decrypted2 = EncrypteUtil.decryptRSA(encrypted2, publicKey);
//            Log.e("D","decrypted2 : " + decrypted2);
//            // 공개키를 Base64 인코딩한 문자일을 만듭니다.
////            String base64PublicKey = Base64.encodeBase64String(publicKey.getEncoded());//Base64.getEncoder().encodeToString(bytePublicKey);
//            String base64PublicKey = Base64.encodeToString(publicKey.getEncoded(), Base64.NO_WRAP);//Base64.getEncoder().encodeToString(bytePublicKey);
//            Log.e("E","Base64 Public Key : " + base64PublicKey);
//            // 개인키를 Base64 인코딩한 문자열을 만듭니다.
////            String base64PrivateKey = Base64.encodeBase64String(privateKey.getEncoded());//Base64.getEncoder().encodeToString(bytePrivateKey);
//            String base64PrivateKey = Base64.encodeToString(privateKey.getEncoded(), Base64.NO_WRAP);//Base64.getEncoder().encodeToString(bytePrivateKey);
//            Log.e("F","Base64 Private Key : " + base64PrivateKey);
//
//            Log.e("TEST1","Encrypted By key : " + EncrypteUtil.encryptRSA(plainText, EncrypteUtil.genRSAKeyPublic(base64PublicKey)));
//            Log.e("TEST2","Decrypted By key : " + EncrypteUtil.decryptRSA(encrypted, EncrypteUtil.genRSAKeyPrivate(base64PrivateKey)));
//
//            Log.e("TEST3","Encrypted2 By key : " + EncrypteUtil.encryptRSA(plainText, EncrypteUtil.genRSAKeyPrivate(base64PrivateKey)));
//            Log.e("TEST4","Decrypted2 By key : " + EncrypteUtil.decryptRSA(encrypted2, EncrypteUtil.genRSAKeyPublic(base64PublicKey)));
//
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//        }


//        User user = null;
//        try {
//            user = new User("afasdfsdf", md5("!quant0330"), "quantec");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Call<Object> getTest2 = RetrofitClient.getInstance().getService().getTestLogin("application/json", user);
//        getTest2.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//                if(response.code() == 200) {
//
////                    Log.e("권한","값 : "+response.headers().get("Authorization"));
////                    Key key = Keys.secretKeyFor(SignatureAlgorithm.RS256);
//
////                    String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
////                    Log.e("풀린값","값 : "+ Jwts.parser().setSigningKey(key).parseClaimsJws(response.headers().get("Authorization")));
//                    secretKey = response.headers().get("Bare");
//                    try {
//                        JTdecoded(response.headers().get("Authorization"));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Log.e("레트로핏 실패","값 : "+t.getMessage());
//            }
//        });
//        Call<ModelZzimCount> getData = RetrofitClient.getInstance().getService().getPortCallData(1);
//        getData.enqueue(new Callback<ModelZzimCount>() {
//            @Override
//            public void onResponse(Call<ModelZzimCount> call, Response<ModelZzimCount> response) {
//                if (response.code() == 200) {
//                    SharedPreferenceUtil.getInstance(ActivityIntro.this).putIntZzimCount("PortZzimCount", response.body().getTotalNum());
//                    NextPageModve();
//                } else if (response.code() == 301) {
//                    SharedPreferenceUtil.getInstance(ActivityIntro.this).putIntZzimCount("PortZzimCount", 0);
//                    NextPageModve();
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelZzimCount> call, Throwable t) {
//                Log.e("레트로핏 실패", "값 : " + t.getMessage());
//            }
//        });


        NextPageModve();
    }//onCreate 끝

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionsPhone.onRequestPermissionsResult(this, requestCode, grantResults);
//    }
//
//    @NeedsPermission({Manifest.permission.READ_PHONE_STATE})
//    public void getPhoneNumber(){
//        TelephonyManager mgr = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
//        @SuppressLint("MissingPermission") String phoneNumber = mgr.getLine1Number();
//        if(!TextUtils.isEmpty(phoneNumber)){
//            phoneNumber = phoneNumber.replace("-","").replace("+82","0");
//            Log.e("전화번호","값 : "+phoneNumber);
//        }
//    }

    //        public static String decrypt(String encrypted, Key pKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
//                Cipher cipher = Cipher.getInstance("RSA");
////                byte[] bytes = Base64coder.decode(encrypted);
//            byte[] bytes = Base64.decode(encrypted, 0);
//            cipher.init(Cipher.DECRYPT_MODE, pKey);
//                byte[] bytePlain = cipher.doFinal(bytes);
//                String decrypted = new String(bytePlain, "utf-8");
//                return decrypted;
//        }
//        public PublicKey PublicKey(String base64Str) throws NoSuchAlgorithmException, InvalidKeySpecException {
//            KeySpec keySpec = new X509EncodedKeySpec(Base64.decode(base64Str.trim(), 0));
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            return keyFactory.generatePublic(keySpec);
//        }

        public  void JTdecoded(String JWTEncoded) throws Exception {
                String[] split = JWTEncoded.split("\\.");
                Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
                Log.d("JWT_DECODED", "Body: " + getJson(split[1]));

                Gson gson = new Gson();
                MyData data = gson.fromJson(getJson(split[1]), MyData.class);

                Log.e("이름","값:"+data.userName);
                Log.e("키","값:"+secretKey);

//                String base64PrivateKey = Base64.encodeToString(secretKey.getBytes("utf-8"), Base64.NO_WRAP);//Base64.getEncoder().encodeToString(bytePrivateKey);
                Log.e("TEST5","최종값 :" + EncrypteUtil.decryptRSA(data.userName, EncrypteUtil.genRSAKeyPublic(secretKey)));
        }

        private  String getJson(String strEncoded) throws UnsupportedEncodingException {
            byte[] decode =  android.util.Base64.decode(strEncoded,  Base64.URL_SAFE);
            return new String(decode, "utf-8");
        }

//    String enccriptData(String txt) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
//        String encoded = "";
//        byte[] encrypted = null;
//
//            byte[] publicBytes = Base64.decode(secretKey.trim(), Base64.NO_WRAP);
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PublicKey pubKey = keyFactory.generatePublic(keySpec);
//            Cipher cipher = Cipher.getInstance("RSA"); //or try with "RSA"
//            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
//            encrypted = cipher.doFinal(Base64.decode(txt,Base64.NO_WRAP));
//        String decrypted = new String(encrypted, "utf-8");
////            encoded = Base64.encodeToString(encrypted, Base64.DEFAULT);
//
//        return decrypted;
//    }

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


    //인트로시 문제 없으면 메인으로 이동
    void NextPageModve(){
        final Intent intent1 = new Intent(this, MainActivity.class);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);
                finish();
            }
        }, 1300);
    }

    public NetworkInfo getNetworkState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }
}


