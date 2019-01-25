package quantec.com.moneypot.Activity.Intro;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import quantec.com.moneypot.Activity.Main.MainActivity;
import quantec.com.moneypot.Model.nModel.ModelZzimCount;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityIntro extends AppCompatActivity {
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

        Call<ModelZzimCount> getData = RetrofitClient.getInstance().getService().getPortCallData(1);
        getData.enqueue(new Callback<ModelZzimCount>() {
            @Override
            public void onResponse(Call<ModelZzimCount> call, Response<ModelZzimCount> response) {
                if (response.code() == 200) {
                    SharedPreferenceUtil.getInstance(ActivityIntro.this).putIntZzimCount("PortZzimCount", response.body().getTotalNum());
                    NextPageModve();
                } else if (response.code() == 301) {
                    SharedPreferenceUtil.getInstance(ActivityIntro.this).putIntZzimCount("PortZzimCount", 0);
                    NextPageModve();
                }
            }
            @Override
            public void onFailure(Call<ModelZzimCount> call, Throwable t) {
                Log.e("레트로핏 실패", "값 : " + t.getMessage());
            }
        });
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
