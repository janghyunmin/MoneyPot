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

import okhttp3.MultipartBody;
import quantec.com.moneypot.Activity.PortProfileModify.ActivityPortProfileModify;
import quantec.com.moneypot.Activity.PortProfileModify.Model.nModel.ModelImageSavedData;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.databinding.ActivityPortProfileDescModifyBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPortProfileDescModify extends AppCompatActivity {

    ActivityPortProfileDescModifyBinding descModifyBinding;

    InputMethodManager imm;
    int TextNum, mmCode;
    //한글, 영어, 숫자, 특수기호 중 ( _ - . ) 만 가능
    String Patten = "(^[^\\s][\\sa-zA-Z0-9ㄱ-ㅎ가-힣_.-]*$)";
    Animation ShakeText;
    private boolean animationShake = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        descModifyBinding = DataBindingUtil.setContentView(this, R.layout.activity_port_profile_desc_modify);
        descModifyBinding.setProfileDescModify(this);

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
        TextNum = intent.getStringExtra("mDesc").length();
        mmCode = intent.getIntExtra("mmCode",0);

        descModifyBinding.PortProfileDescModifyDescEditTextNum.setText(String.valueOf(TextNum));
        descModifyBinding.PortProfileDescModifyDescEditText.setText(intent.getStringExtra("mDesc"));

        //초기 뷰 상태 활성화 ( 진입시 조건 항상 충족 )
        InitActiveView();

        descModifyBinding.PortProfileDescModifyDescEditText.requestFocus();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        //포트 설명 편집 취소
        descModifyBinding.PortProfileDescModifyTopCancleBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //키보드 내리기
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(descModifyBinding.PortProfileDescModifyDescEditText.getWindowToken(), 0);
                finish();
            }
        });

        //포트 설명 편집 완료
        descModifyBinding.PortProfileDescModifyTopOkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //키보드 내리기
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(descModifyBinding.PortProfileDescModifyDescEditText.getWindowToken(), 0);

                MultipartBody.Part opt = MultipartBody.Part.createFormData("opt", "1");
                MultipartBody.Part name = MultipartBody.Part.createFormData("name", "");
                MultipartBody.Part desc = MultipartBody.Part.createFormData("desc", descModifyBinding.PortProfileDescModifyDescEditText.getText().toString());
                MultipartBody.Part ucode = MultipartBody.Part.createFormData("ucode", String.valueOf(mmCode));
                MultipartBody.Part wch = MultipartBody.Part.createFormData("wch", "1");

                Call<ModelImageSavedData> getchartItem = RetrofitClient.getInstance().getService().getTextUpload(opt, ucode, name, desc, wch);
                getchartItem.enqueue(new Callback<ModelImageSavedData>() {
                    @Override
                    public void onResponse(Call<ModelImageSavedData> call, Response<ModelImageSavedData> response) {
                        if (response.code() == 200) {//설명에 금칙어 없을때
                            //편집된 결과 반영 확인 코드
                            Intent ModifyResult = new Intent(ActivityPortProfileDescModify.this, ActivityPortProfileModify.class);
                            ModifyResult.putExtra("modiDesc",descModifyBinding.PortProfileDescModifyDescEditText.getText().toString());
                            setResult(334,ModifyResult);
                            finish();
                        }else if(response.code() == 303){ //설명에 금칙어 있을때
                            Toast.makeText(ActivityPortProfileDescModify.this, "포트설명에 금칙어가 포함되어 있습니다\n수정 후에 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelImageSavedData> call, Throwable t) {
                        Log.e("레트로핏 실패", "값 : " + t.getMessage());
                    }
                });
            }
        });

        //글자수 등등 확인
        descModifyBinding.PortProfileDescModifyDescEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                descModifyBinding.PortProfileDescModifyDescEditTextNum.setText(String.valueOf(s.length()));

                if(s.length() >= 10 && descModifyBinding.PortProfileDescModifyDescEditText.getText().toString().matches(Patten)) {
                    animationShake = true;
                    descModifyBinding.PortProfileDescModifyDescEditTextInfo.setTextColor(getResources().getColor(R.color.gray_brown_color));
                    finishView();
                }
                else{
                    if(!descModifyBinding.PortProfileDescModifyDescEditText.getText().toString().matches(Patten)) {

                        if(animationShake) {
                            animationShake = false;
                            ShakeText = AnimationUtils.loadAnimation(ActivityPortProfileDescModify.this, R.anim.port_name_info_modify_shak);
                            descModifyBinding.PortProfileDescModifyDescEditTextInfo.startAnimation(ShakeText);
                        }
                        descModifyBinding.PortProfileDescModifyDescEditTextInfo.setTextColor(getResources().getColor(R.color.red_text_color));
                    }else{
                        animationShake = true;
                        descModifyBinding.PortProfileDescModifyDescEditTextInfo.setTextColor(getResources().getColor(R.color.gray_brown_color));
                    }
                    if(s.length()==0){
                        descModifyBinding.PortProfileDescModifyDescEditTextCancleBT.setVisibility(View.GONE);
                    }else{
                        descModifyBinding.PortProfileDescModifyDescEditTextCancleBT.setVisibility(View.VISIBLE);
                    }
                    descModifyBinding.PortProfileDescModifyDescEditTextNum.setTextColor(getResources().getColor(R.color.dark_gray_color));
                    descModifyBinding.PortProfileDescModifyTopOkBt.setTextColor(getResources().getColor(R.color.dark_gray_color));
                    descModifyBinding.PortProfileDescModifyTopOkBt.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //포트 설명 글 삭제
        descModifyBinding.PortProfileDescModifyDescEditTextCancleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descModifyBinding.PortProfileDescModifyDescEditText.setText("");
            }
        });

    }//onCreate 끝

    //뷰상태 초기화
    void InitActiveView(){
        descModifyBinding.PortProfileDescModifyDescEditTextNum.setTextColor(getResources().getColor(R.color.blue_color));
        descModifyBinding.PortProfileDescModifyTopOkBt.setTextColor(getResources().getColor(R.color.dark_gray_color));
        descModifyBinding.PortProfileDescModifyTopOkBt.setEnabled(false);
        descModifyBinding.PortProfileDescModifyDescEditTextCancleBT.setVisibility(View.VISIBLE);
    }
    //뷰 상태 완료
    void finishView() {
        descModifyBinding.PortProfileDescModifyDescEditTextNum.setTextColor(getResources().getColor(R.color.blue_color));
        descModifyBinding.PortProfileDescModifyTopOkBt.setTextColor(getResources().getColor(R.color.normal_text_color));
        descModifyBinding.PortProfileDescModifyTopOkBt.setEnabled(true);
        descModifyBinding.PortProfileDescModifyDescEditTextCancleBT.setVisibility(View.VISIBLE);
    }
}