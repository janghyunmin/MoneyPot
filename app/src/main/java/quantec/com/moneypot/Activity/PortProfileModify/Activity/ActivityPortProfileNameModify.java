package quantec.com.moneypot.Activity.PortProfileModify.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.MultipartBody;
import quantec.com.moneypot.Activity.FinishMakePort.Model.nModel.ModelPortSavedInfo;
import quantec.com.moneypot.Activity.Intro.ErrorPojoClass;
import quantec.com.moneypot.Activity.PortProfileModify.ActivityPortProfileModify;
import quantec.com.moneypot.Activity.PortProfileModify.Model.nModel.ModelImageSavedData;
import quantec.com.moneypot.ModelCommon.nModel.SavedPotDto;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ActivityPortProfileNameModifyBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPortProfileNameModify extends AppCompatActivity {

    ActivityPortProfileNameModifyBinding profileNameModifyBinding;

    InputMethodManager imm;
    int TextNum;
    String mmCode, mmDesc;
    //한글, 영어, 숫자, 특수기호 중 ( _ - . ) 만 가능
    String Patten = "(^[^\\s][\\sa-zA-Z0-9ㄱ-ㅎ가-힣_.-]*$)";
    Animation ShakeText;

    private boolean animationShake = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileNameModifyBinding = DataBindingUtil.setContentView(this, R.layout.activity_port_profile_name_modify);
        profileNameModifyBinding.setProfileNameModify(this);

        // 스테이터스 바 색상 변경 -> 화이트
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }else{
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Intent intent = getIntent();
        TextNum = intent.getStringExtra("mName").length();
        mmCode = intent.getStringExtra("mmCode");
        mmDesc = intent.getStringExtra("mDesc");

        profileNameModifyBinding.PortProfileNameModifyNameEditTextNum.setText(String.valueOf(TextNum));
        profileNameModifyBinding.PortProfileNameModifyNameEditText.setText(intent.getStringExtra("mName"));

        //초기 뷰들 활성화 ( 진입시 조건 항상 충족 )
        ActiveView();

        //키보드 자동 올리기
        profileNameModifyBinding.PortProfileNameModifyNameEditText.requestFocus();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        //포트이름 편집 취소
        profileNameModifyBinding.PortProfileNameModifyTopCancleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //키보드 내리기
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(profileNameModifyBinding.PortProfileNameModifyNameEditText.getWindowToken(), 0);
                finish();
            }
        });

        //포트이름 편집 완료
        profileNameModifyBinding.PortProfileNameModifyTopOkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //키보드 내리기
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(profileNameModifyBinding.PortProfileNameModifyNameEditText.getWindowToken(), 0);

                SavedPotDto potDto = new SavedPotDto(mmCode, profileNameModifyBinding.PortProfileNameModifyNameEditText.getText().toString(), mmDesc);
                Call<ModelPortSavedInfo> getchartItem = RetrofitClient.getInstance().getService().getSavedMyPot("application/json", potDto);
                getchartItem.enqueue(new Callback<ModelPortSavedInfo>() {
                    @Override
                    public void onResponse(Call<ModelPortSavedInfo> call, Response<ModelPortSavedInfo> response) {

                        if(response.code() == 200) {
                            if(response.body().getErrorcode() == 200){

                                //편집된 결과 반영 확인 코드
                                Intent ModifyResult = new Intent(ActivityPortProfileNameModify.this, ActivityPortProfileModify.class);
                                ModifyResult.putExtra("modiName",profileNameModifyBinding.PortProfileNameModifyNameEditText.getText().toString());
                                setResult(333,ModifyResult);
                                finish();
                            }
                        }else{
                            Gson gson = new GsonBuilder().create();
                            ErrorPojoClass mError = new ErrorPojoClass();
                            try {
                                mError= gson.fromJson(response.errorBody().string(),ErrorPojoClass .class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(mError.getStatus() == 44001){
                                Toast.makeText(ActivityPortProfileNameModify.this, "포트이름에 금칙어가 포함되어 있습니다\n수정 후에 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelPortSavedInfo> call, Throwable t) {
                        Toast.makeText(ActivityPortProfileNameModify.this, "서버가 불안정 합니다\n잠시후 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //글자수 등등 확인
        profileNameModifyBinding.PortProfileNameModifyNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                profileNameModifyBinding.PortProfileNameModifyNameEditTextNum.setText(String.valueOf(s.length()));
                if(s.length() >= 2 && profileNameModifyBinding.PortProfileNameModifyNameEditText.getText().toString().matches(Patten)) {
                    animationShake = true;
                    profileNameModifyBinding.PortProfileNameModifyNameEditTextInfo.setTextColor(getResources().getColor(R.color.gray_brown_color));
                    finishView();
                }
                else{
                    if(!profileNameModifyBinding.PortProfileNameModifyNameEditText.getText().toString().matches(Patten)) {
                        if(animationShake) {
                            animationShake = false;
                            ShakeText = AnimationUtils.loadAnimation(ActivityPortProfileNameModify.this, R.anim.port_name_info_modify_shak);
                            profileNameModifyBinding.PortProfileNameModifyNameEditTextInfo.startAnimation(ShakeText);
                        }
                        profileNameModifyBinding.PortProfileNameModifyNameEditTextInfo.setTextColor(getResources().getColor(R.color.red_text_color));
                    }else{
                        animationShake = true;
                        profileNameModifyBinding.PortProfileNameModifyNameEditTextInfo.setTextColor(getResources().getColor(R.color.gray_brown_color));
                    }
                    if(s.length()==0){
                        profileNameModifyBinding.PortProfileNameModifyNameEditTextCancleBT.setVisibility(View.GONE);
                    }else{
                        profileNameModifyBinding.PortProfileNameModifyNameEditTextCancleBT.setVisibility(View.VISIBLE);
                    }
                    profileNameModifyBinding.PortProfileNameModifyNameEditTextNum.setTextColor(getResources().getColor(R.color.dark_gray_color));
                    profileNameModifyBinding.PortProfileNameModifyTopOkBt.setTextColor(getResources().getColor(R.color.dark_gray_color));
                    profileNameModifyBinding.PortProfileNameModifyTopOkBt.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        //포트 이름 글자 삭제
        profileNameModifyBinding.PortProfileNameModifyNameEditTextCancleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileNameModifyBinding.PortProfileNameModifyNameEditText.setText("");
            }
        });

    }//onCreate 끝

    //뷰 상태 초기화
    void ActiveView() {
        profileNameModifyBinding.PortProfileNameModifyNameEditTextNum.setTextColor(getResources().getColor(R.color.blue_color));
        profileNameModifyBinding.PortProfileNameModifyTopOkBt.setTextColor(getResources().getColor(R.color.dark_gray_color));
        profileNameModifyBinding.PortProfileNameModifyTopOkBt.setEnabled(false);
        profileNameModifyBinding.PortProfileNameModifyNameEditTextCancleBT.setVisibility(View.VISIBLE);
    }
    //뷰 상태 완료
    void finishView() {
        profileNameModifyBinding.PortProfileNameModifyNameEditTextNum.setTextColor(getResources().getColor(R.color.blue_color));
        profileNameModifyBinding.PortProfileNameModifyTopOkBt.setTextColor(getResources().getColor(R.color.normal_text_color));
        profileNameModifyBinding.PortProfileNameModifyTopOkBt.setEnabled(true);
        profileNameModifyBinding.PortProfileNameModifyNameEditTextCancleBT.setVisibility(View.VISIBLE);
    }

}
