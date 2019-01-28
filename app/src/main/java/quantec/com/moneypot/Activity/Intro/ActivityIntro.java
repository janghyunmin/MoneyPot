package quantec.com.moneypot.Activity.Intro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityIntro extends AppCompatActivity {

    public static String secretKey;

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



//        String login = "{\"email\":"+email+",\"password\":"+password+",\"userId\":"+userId+"}";

        User user = null;
        try {
            user = new User("afasdfsdf", md5("!quant0330"), "quantec");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<Object> getTest2 = RetrofitClient.getInstance().getService().getTestLogin("application/json", user);
        getTest2.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.code() == 200) {

//                    Log.e("권한","값 : "+response.headers().get("Authorization"));
//                    Key key = Keys.secretKeyFor(SignatureAlgorithm.RS256);

//                    String jws = Jwts.builder().setSubject("Joe").signWith(key).compact();
//                    Log.e("풀린값","값 : "+ Jwts.parser().setSigningKey(key).parseClaimsJws(response.headers().get("Authorization")));

                    secretKey = response.headers().get("Bare").trim();

                    try {
                        JWTUtils.decoded(response.headers().get("Authorization"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    RS256_Decode()

                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("레트로핏 실패","값 : "+t.getMessage());
            }
        });




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

    }//onCreate 끝


    public static String getBase64incode(String content){
        return new String(Base64.encode(content.getBytes(), 0));
    }

    public static String getBase64decode(String content){
        return new String(Base64.decode(content.getBytes(), 0));
    }

/*
    public static String decrypt(byte[] input) throws Exception
    {
        KeyFactory keyFactory = KeyFactory.getInstance("HS512");
        PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(Base64.decode(secretKey, Base64.DEFAULT));
        Key decryptionKey = keyFactory.generatePrivate(privSpec);
        Cipher rsa = Cipher.getInstance("RSA/None/PKCS1Padding");
        rsa.init(Cipher.DECRYPT_MODE, decryptionKey);
        return new String(rsa.doFinal(input), "utf-8");
    }

    public static String RS256_Decode(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        byte[] textBytes = Base64.decode(str, 0);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "RSA");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
        cipher.init(Cipher.DECRYPT_MODE, newKey);
        return new String(cipher.doFinal(textBytes), "UTF-8");
    }

*/

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "RSA");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "RSA");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }


    private static String decodeBase64(String coded){
        byte[] valueDecoded= new byte[0];
        try {
            valueDecoded = Base64.decode(coded.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
        }
        return new String(valueDecoded);
    }

    public static class JWTUtils {

        public static void decoded(String JWTEncoded) throws Exception {
            try {
                String[] split = JWTEncoded.split("\\.");
                Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
                Log.d("JWT_DECODED", "Body: " + getJson(split[1]));

//                testBody.add(getJson(split[1]));

//                MyData data =new MyData() ;
//                Gson gson = new Gson();
//                data= gson.fromJson(getJson(split[1]), MyData.class);

                Gson gson = new Gson();
                MyData data = gson.fromJson(getJson(split[1]), MyData.class);

//                Log.e("이름", "값 : "+ data.userName);

//                RS256_Decode(data.userName);

//                decrypt(data.userName);
//                A(data.userName);

//                Log.e("이름", "값 : "+ RS256_Decode(data.userName));


//                Log.e("값","값 : "+  URLDecoder.decode(getBase64decode(secretKey), "UTF-8"));

//                byte decoded[] = Base64.getDecoder().decode(encodedText.getBytes());
//                try {
//                    String originText = new String(decoded,"utf-8");
//                    System.out.println("인코딩 : "+encodedText+"\n디코딩 : "+originText);
//                }
//                catch(Exception e) {e.printStackTrace();}
//                Log.e("값","값 : "+  decodeBase64(secretKey));



            } catch (UnsupportedEncodingException e) {
                //Error
            }
        }

        private static String getJson(String strEncoded) throws UnsupportedEncodingException {
            byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
            return new String(decodedBytes, "UTF-8");
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


//    public static String decryptData(String text, String pri_key) throws UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//
//            byte[] data =Base64.decode(text,Base64.DEFAULT);
//            PrivateKey privateKey = getPrivateKey(Base64.decode(pri_key.getBytes("utf-8"),Base64.DEFAULT));
//
//            Cipher cipher = Cipher.getInstance("RSA");
//            cipher.init(Cipher.DECRYPT_MODE, privateKey);
//            return new String(cipher.doFinal(data),"utf-8");
//    }

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
