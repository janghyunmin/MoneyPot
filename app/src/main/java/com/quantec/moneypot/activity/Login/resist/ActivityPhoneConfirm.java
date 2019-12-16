package com.quantec.moneypot.activity.Login.resist;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.quantec.moneypot.activity.Login.loginPage.ActivityLoginPage;
import com.quantec.moneypot.activity.Login.Model.dModel.IdentifyDto;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelIdentifyData;
import com.quantec.moneypot.dialog.DialogBottomSheet;
import com.quantec.moneypot.dialog.DialogLoadingMakingPort;
import com.quantec.moneypot.dialog.DialogSMS;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.R;
import com.quantec.moneypot.util.NetworkStateCheck.NetworkStateCheck;
import com.quantec.moneypot.util.SharedPreference.SharedPreferenceUtil;
import com.quantec.moneypot.databinding.ActivityPhoneConfirmBinding;
import com.quantec.moneypot.util.view.keyboardevent.KeyboardVisibilityEvent;
import com.quantec.moneypot.util.view.keyboardevent.KeyboardVisibilityEventListener;
import com.quantec.moneypot.util.view.keyboardevent.Unregistrar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityPhoneConfirm extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {


    ActivityPhoneConfirmBinding binding;

    private DialogBottomSheet dialogBottomSheet;
    // 기본값(통신사) : 00 / "SKT:01, KT:02, LGT:03, SKT_MVNO:04, KT_MVNO:05, LG_MVNO : 06"
    String agencyNumber = "00";

    ModelIdentifyData modelLoginData = new ModelIdentifyData();
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000;
    final int COUNT_DOWN_INTERVAL = 1000;

    InputMethodManager imm;
    boolean openedConfirmNumber;
    DialogSMS dialogSMS;

    private DialogLoadingMakingPort loadingCustomMakingPort;
    String phoneNum;
    Unregistrar unregistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_confirm);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_confirm);
        binding.nextBt.setBackground(getResources().getDrawable(R.drawable.unselect_bt));

        binding.myinfoPhoneNumberNumberBt.setBackgroundResource(R.drawable.custom_bt_unselected_5dp);
        binding.myinfoPhoneNumberNumberBt.setText("인증 요청");
        binding.myinfoPhoneNumberNumberBt.setTextColor(getResources().getColor(R.color.text_white_color));

        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("passPhoneNum");

        //스테이터스 바 색상 변경 -> 화이트
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

//        actList.add(this);

        binding.myinfoNameEditText.addTextChangedListener(new MyTextWatcher(binding.myinfoNameEditText));
        binding.myinfoCertifyNumberEditText.addTextChangedListener(new MyTextWatcher(binding.myinfoCertifyNumberEditText));
        binding.myinfoCertifyNumberEditText2.addTextChangedListener(new MyTextWatcher(binding.myinfoCertifyNumberEditText2));
        binding.myinfoPhoneNumberEditText.addTextChangedListener(new MyTextWatcher(binding.myinfoPhoneNumberEditText));

        binding.myinfoPhoneNumberNumberBt.setOnClickListener(this);
        binding.myinfoPhoneNumberSelectText.setOnClickListener(this);
        binding.nextBt.setOnClickListener(this);
        binding.backBt.setOnClickListener(this);

        binding.myinfoPhoneNumberEditText.setOnFocusChangeListener(this);
        binding.myinfoNameEditText.setOnFocusChangeListener(this);
        binding.myinfoCertifyNumberEditText.setOnFocusChangeListener(this);
        binding.myinfoCertifyNumberEditText2.setOnFocusChangeListener(this);
        binding.myinfoPhoneNumberSelectText.setOnFocusChangeListener(this);
        binding.myinfoConfirmNumberEditText.setOnFocusChangeListener(this);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        binding.myinfoPhoneNumberEditText.setText(phoneNum);

        unregistrar = KeyboardVisibilityEvent.registerEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if(!isOpen){
                    imm.hideSoftInputFromWindow(binding.myinfoNameEditText.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText2.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(binding.myinfoPhoneNumberEditText.getWindowToken(), 0);

                    requestFocus2(binding.myinfoNameEditText);
                }
            }
        });

    } // onCreate 끝


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void requestFocus2(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    private void statedNumBt(boolean isClicked){

        imm.hideSoftInputFromWindow(binding.myinfoNameEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText2.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(binding.myinfoPhoneNumberEditText.getWindowToken(), 0);

        if(isClicked){
            binding.myinfoPhoneNumberNumberBt.setBackgroundResource(R.drawable.custom_bt_unselected_5dp);
            binding.myinfoPhoneNumberNumberBt.setText("재인증");
            binding.myinfoPhoneNumberNumberBt.setTextColor(getResources().getColor(R.color.text_white_color));

            requestFocus2(binding.myinfoNameEditText);
            visibleCertNum(true);

        }else{
            binding.myinfoPhoneNumberNumberBt.setBackgroundResource(R.drawable.custom_bt_selected_5dp);
            binding.myinfoPhoneNumberNumberBt.setText("인증 요청");
            binding.myinfoPhoneNumberNumberBt.setTextColor(getResources().getColor(R.color.text_white_color));

            visibleCertNum(false);
        }
    }

    private void visibleCertNum(boolean isVisible){
        if(isVisible){

            enabledNextBt(true);

            countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
                @Override
                public void onTick(long millisUntilFinished) {

                    long AuthCount = millisUntilFinished / 1000;

                    //초가 10보다 크면 그냥 출력
                    if((AuthCount - ((AuthCount / 60) *60)) >= 10){
                        binding.myinfoConfirmNumberCountText.setText((AuthCount / 60)+ " : " + (AuthCount - ((AuthCount / 60) * 60)));
                    }
                    else if((AuthCount - ((AuthCount / 60) *60)) < 10 && AuthCount >= 1) {
                        binding.myinfoConfirmNumberCountText.setText((AuthCount / 60) + " : 0" + (AuthCount - ((AuthCount / 60) * 60)));
                    }else if(AuthCount == 0){
                        countDownTimer.cancel();
                        countDownTimer.onFinish();
                    }
                }
                @Override
                public void onFinish() {
                    statedNumBt(false);
                }
            }.start();

        }else{
            enabledNextBt(false);
        }
    }

    private void enabledNextBt(boolean isEnable){
        if(isEnable){
            openedConfirmNumber = true;

            binding.nextBt.setEnabled(true);
            binding.nextBt.setBackground(getResources().getDrawable(R.drawable.select_bt));
            binding.nextBt.setTextColor(getResources().getColor(R.color.text_white_color));

            binding.myinfoConfirmNumber.setVisibility(View.VISIBLE);
            binding.myinfoConfirmNumberEditText.setVisibility(View.VISIBLE);
            binding.myinfoConfirmNumberCountText.setVisibility(View.VISIBLE);
            binding.myinfoConfirmNumberEditTextLine.setVisibility(View.VISIBLE);

        }else{

            binding.myinfoConfirmNumberEditText.setText("");

            openedConfirmNumber = false;

            binding.nextBt.setEnabled(false);
            binding.nextBt.setBackground(getResources().getDrawable(R.drawable.unselect_bt));
            binding.nextBt.setTextColor(getResources().getColor(R.color.button_enable_text));

            binding.myinfoConfirmNumber.setVisibility(View.INVISIBLE);
            binding.myinfoConfirmNumberEditText.setVisibility(View.INVISIBLE);
            binding.myinfoConfirmNumberCountText.setVisibility(View.INVISIBLE);
            binding.myinfoConfirmNumberEditTextLine.setVisibility(View.INVISIBLE);

        }
    }

    private boolean validateDay(String Birth1, String Birth2){

        if(Birth1.equals("3") || Birth1.equals("4") || Birth1.equals("7") || Birth1.equals("8")){
            Birth1 = "20";
        }else{
            Birth1 = "19";
        }

        int BirthY = Integer.valueOf(Birth1 + Birth2.substring(0,2));
        int BirthM =  Integer.valueOf(Birth2.substring(2, 4));
        int BirthD = Integer.valueOf(Birth2.substring(4, 6));
        //윤년일때
        if(((BirthY%4==0)&&(BirthY%100!=0))||(BirthY%400==0)){
            //월 일 날짜 범위 불 일치
            if(!(BirthM > 0 && BirthM < 13) || !(BirthD > 0 && BirthD < 32)){
                return false;
            }
            //월 일 날짜 범위 일치
            else{
                return true;
            }
        }
        //윤년아닐때
        else{
            //윤년이 아닌데 2월이면서 29일 이상일때
            if(BirthM == 2 && BirthD >= 29) {
                return false;
            }
            //2월이 아닐때
            else{

                if(!(BirthM > 0 && BirthM < 13) || !(BirthD > 0 && BirthD < 32)){
                    return false;
                }else{
                    return true;
                }
            }
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.myinfoPhoneNumber_selectText:

                imm.hideSoftInputFromWindow(binding.myinfoNameEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText2.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(binding.myinfoPhoneNumberEditText.getWindowToken(), 0);

                requestFocus2(binding.myinfoNameEditText);

                dialogBottomSheet = new DialogBottomSheet(ActivityPhoneConfirm.this, agencyNumber, sktListener, ktListener, lgListener, sktRListener, ktRListener, lgRListener, closeListener);
                dialogBottomSheet.show();
                break;

            case R.id.myinfoPhoneNumber_NumberBt:

                if (binding.myinfoNameEditText.getText().toString().isEmpty()) {
                    requestFocus2(binding.myinfoNameEditText);
                    Toast.makeText(getApplicationContext(), "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                } else {

                    if(binding.myinfoCertifyNumberEditText.getText().toString().isEmpty()){
                        requestFocus2(binding.myinfoCertifyNumberEditText);
                        Toast.makeText(getApplicationContext(), "주민 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    }else{


                        if(binding.myinfoCertifyNumberEditText.getText().length() < 6){
                            requestFocus2(binding.myinfoCertifyNumberEditText);
                            Toast.makeText(getApplicationContext(), "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }else{

                            if(binding.myinfoCertifyNumberEditText2.getText().toString().isEmpty()){
                                requestFocus2(binding.myinfoCertifyNumberEditText2);
                                Toast.makeText(getApplicationContext(), "주민 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            }else{

                                if (validateDay(binding.myinfoCertifyNumberEditText2.getText().toString(), binding.myinfoCertifyNumberEditText.getText().toString())) {

                                    if(binding.myinfoPhoneNumberEditText.getText().toString().isEmpty()){
                                        requestFocus2(binding.myinfoPhoneNumberEditText);
                                        Toast.makeText(getApplicationContext(), "전화 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                                    }else{

                                        if(agencyNumber.equals("00")){
                                            imm.hideSoftInputFromWindow(binding.myinfoNameEditText.getWindowToken(), 0);
                                            imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText.getWindowToken(), 0);
                                            imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText2.getWindowToken(), 0);
                                            imm.hideSoftInputFromWindow(binding.myinfoPhoneNumberEditText.getWindowToken(), 0);

                                            requestFocus2(binding.myinfoNameEditText);
                                            Toast.makeText(getApplicationContext(), "통신사를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                                        }else{

                                            if(NetworkStateCheck.getNetworkState(this)){

                                                loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityPhoneConfirm.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                                                loadingCustomMakingPort.show();

                                                String birthY;

                                                if(binding.myinfoCertifyNumberEditText2.getText().toString().equals("3") || binding.myinfoCertifyNumberEditText2.getText().toString().equals("4")
                                                        || binding.myinfoCertifyNumberEditText2.getText().toString().equals("7") || binding.myinfoCertifyNumberEditText2.getText().toString().equals("8")){
                                                    birthY = "20";
                                                }else{
                                                    birthY = "19";
                                                }

                                                IdentifyDto loginData = new IdentifyDto(birthY+binding.myinfoCertifyNumberEditText.getText().toString(), "", "", "",
                                                        binding.myinfoCertifyNumberEditText2.getText().toString(), "000000", "", binding.myinfoPhoneNumberEditText.getText().toString(), binding.myinfoNameEditText.getText().toString(), "0", "", agencyNumber, "auth", "0000000000");

                                                Gson gson = new GsonBuilder().create();
                                                String jsonItentifyData = gson.toJson(loginData).replace("\\n", "").replace(" ", "")
                                                        .replace("\\", "").replace("\"{", "{").replace("}\"", "}");

                                                Call<ModelIdentifyData> getReList = RetrofitClient.getInstance(ActivityPhoneConfirm.this).getService().getIdentifyData("application/json",  jsonItentifyData);
                                                getReList.enqueue(new Callback<ModelIdentifyData>() {
                                                    @Override
                                                    public void onResponse(Call<ModelIdentifyData> call, Response<ModelIdentifyData> response) {
                                                        if (response.code() == 200) {

                                                            loadingCustomMakingPort.dismiss();

                                                            if(response.body().getStatus() == 200){
                                                                if(response.body().getContent().getCode().equals("0000")){
                                                                    modelLoginData = response.body();

                                                                    if (countDownTimer != null) {
                                                                        countDownTimer.cancel();
                                                                        countDownTimer.onFinish();
                                                                    }

                                                                    statedNumBt(true);
                                                                }
                                                                else if(response.body().getContent().getCode().equals("0011")){
                                                                    requestFocus2(binding.myinfoCertifyNumberEditText);
                                                                    Toast.makeText(getApplicationContext(), "본인인증을 위한 성명/생년월일/휴대폰 번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                                                                    closedCertNum();
                                                                }
                                                                else{
                                                                    Toast.makeText(getApplicationContext(), "네트워크가 불안정 합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(Call<ModelIdentifyData> call, Throwable t) {
                                                        loadingCustomMakingPort.dismiss();
                                                    }
                                                });

                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "네트워크가 끊겼습니다.", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }
                                }
                                else {
                                    requestFocus2(binding.myinfoCertifyNumberEditText);
                                    Toast.makeText(getApplicationContext(), "생년월일 정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
                break;

            ///0:설치, 1:가입시작, 10:가입완료, 20:간편인증 등록완료, 99 해지
            case R.id.nextBt:
                if(binding.myinfoConfirmNumberEditText.getText().toString().isEmpty() || binding.myinfoConfirmNumberEditText.getText().length() < 6){
                    Toast.makeText(getApplicationContext(), "인증번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else{

                    if(NetworkStateCheck.getNetworkState(this)){

                        modelLoginData.getContent().setType("confirm");
                        modelLoginData.getContent().setInputCode(binding.myinfoConfirmNumberEditText.getText().toString());

                        Call<ModelIdentifyData> getReList = RetrofitClient.getInstance(ActivityPhoneConfirm.this).getService().getIdentifyData("application/json",  modelLoginData.getContent());
                        getReList.enqueue(new Callback<ModelIdentifyData>() {
                            @Override
                            public void onResponse(Call<ModelIdentifyData> call, Response<ModelIdentifyData> response) {
                                if (response.code() == 200) {

                                    if(response.body().getStatus() == 200){

                                        if(response.body().getContent().getCode().equals("0000")){

                                            SharedPreferenceUtil.getInstance(ActivityPhoneConfirm.this).putUserCid("userCid", response.body().getContent().getCi());
                                            SharedPreferenceUtil.getInstance(ActivityPhoneConfirm.this).putUserHpNumber("hpNumber", response.body().getContent().getMobileNum());

                                            // 10 미만이면 가입안된 유저 / 10 이상이면 가입된 유저 ( 10이면 비밀번호까지만 등록 / 20이면 파이도까지 등록된 유저 )
                                            if(response.body().getContent().getActiveStep() < 10){

                                                Intent intent = new Intent(ActivityPhoneConfirm.this, ActivityRegInfo.class);
                                                intent.putExtra("hpNumber",response.body().getContent().getMobileNum());
                                                intent.putExtra("uid",response.body().getContent().getUid());
                                                intent.putExtra("cid",response.body().getContent().getCi());
                                                intent.putExtra("userName",response.body().getContent().getName());
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();

                                            }
                                            else{

                                                Intent intent = new Intent(ActivityPhoneConfirm.this, ActivityLoginPage.class);
                                                intent.putExtra("hpNumber",response.body().getContent().getMobileNum());
                                                intent.putExtra("uid",response.body().getContent().getUid());
                                                intent.putExtra("cid",response.body().getContent().getCi());
                                                intent.putExtra("userName",response.body().getContent().getName());
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();

                                            }

                                        }
                                        else if(response.body().getContent().getCode().equals("0043")){
                                            dialogSMS = new DialogSMS(ActivityPhoneConfirm.this, okListener);
                                            dialogSMS.show();
                                        }
                                    }

                                    closedCertNum();
                                }
                            }
                            @Override
                            public void onFailure(Call<ModelIdentifyData> call, Throwable t) {
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "네트워크가 끊겼습니다.", Toast.LENGTH_SHORT).show();
                    }

                }
                break;

            case R.id.backBt:
                finish();
                break;
        }
    }


    private View.OnClickListener okListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogSMS.dismiss();
        }
    };

    private void closedCertNum(){
        binding.myinfoConfirmNumberEditText.setText("");
        if(openedConfirmNumber){
            openedConfirmNumber = false;
            countDownTimer.cancel();
            countDownTimer.onFinish();
            enabledNextBt(false);
        }
    }

    private View.OnClickListener sktListener = new View.OnClickListener() {
        public void onClick(View v) {
            binding.myinfoPhoneNumberSelectText.setText("SKT");
            binding.myinfoPhoneNumberSelectText.setTextColor(getResources().getColor(R.color.normal_text_color));
            agencyNumber = "01";
            dialogBottomSheet.dismiss();

            closedCertNum();
        }
    };
    private View.OnClickListener ktListener = new View.OnClickListener() {
        public void onClick(View v) {
            binding.myinfoPhoneNumberSelectText.setText("KT");
            binding.myinfoPhoneNumberSelectText.setTextColor(getResources().getColor(R.color.normal_text_color));
            agencyNumber = "02";
            dialogBottomSheet.dismiss();

            closedCertNum();

        }
    };
    private View.OnClickListener lgListener = new View.OnClickListener() {
        public void onClick(View v) {
            binding.myinfoPhoneNumberSelectText.setText("LG U+");
            binding.myinfoPhoneNumberSelectText.setTextColor(getResources().getColor(R.color.normal_text_color));
            agencyNumber = "03";
            dialogBottomSheet.dismiss();

            closedCertNum();

        }
    };
    private View.OnClickListener sktRListener = new View.OnClickListener() {
        public void onClick(View v) {
            binding.myinfoPhoneNumberSelectText.setText("SKT 알뜰폰");
            binding.myinfoPhoneNumberSelectText.setTextColor(getResources().getColor(R.color.normal_text_color));
            agencyNumber = "04";
            dialogBottomSheet.dismiss();

            closedCertNum();

        }
    };
    private View.OnClickListener ktRListener = new View.OnClickListener() {
        public void onClick(View v) {
            binding.myinfoPhoneNumberSelectText.setText("KT 알뜰폰");
            binding.myinfoPhoneNumberSelectText.setTextColor(getResources().getColor(R.color.normal_text_color));
            agencyNumber = "05";
            dialogBottomSheet.dismiss();

            closedCertNum();

        }
    };
    private View.OnClickListener lgRListener = new View.OnClickListener() {
        public void onClick(View v) {
            binding.myinfoPhoneNumberSelectText.setText("LG U+ 알뜰폰");
            binding.myinfoPhoneNumberSelectText.setTextColor(getResources().getColor(R.color.normal_text_color));
            agencyNumber = "06";
            dialogBottomSheet.dismiss();

            closedCertNum();

        }
    };
    private View.OnClickListener closeListener = new View.OnClickListener() {
        public void onClick(View v) {
            dialogBottomSheet.dismiss();
        }
    };

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {

            case R.id.myinfoName_editText:
                binding.scrollView.post(new Runnable() {
                    public void run() {
                        ObjectAnimator.ofInt(binding.scrollView, "scrollY", 0).setDuration(1000).start();
                    }
                });
                break;

            case R.id.myinfoCertifyNumber_editText:
                binding.scrollView.post(new Runnable() {
                    public void run() {
                        ObjectAnimator.ofInt(binding.scrollView, "scrollY", 300).setDuration(1000).start();
                    }
                });
                break;

            case R.id.myinfoCertifyNumber_editText2:
                binding.scrollView.post(new Runnable() {
                    public void run() {
                        ObjectAnimator.ofInt(binding.scrollView, "scrollY", 300).setDuration(1000).start();
                    }
                });
                break;
            case R.id.myinfoPhoneNumber_editText:
                binding.scrollView.post(new Runnable() {
                    public void run() {
                        ObjectAnimator.ofInt(binding.scrollView, "scrollY", 500).setDuration(1000).start();
                    }
                });
                break;

            case R.id.myinfoConfirmNumber_editText:
                binding.scrollView.post(new Runnable() {
                    public void run() {
                        ObjectAnimator.ofInt(binding.scrollView, "scrollY", 1000).setDuration(1000).start();
                    }
                });
                break;
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;
        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {

            if(openedConfirmNumber) {
                binding.myinfoConfirmNumberEditText.setText("");

                openedConfirmNumber = false;
                if(countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer.onFinish();
                }
                enabledNextBt(false);

            }else{

                switch (view.getId()) {
                    case R.id.myinfoName_editText:

                        break;
                    case R.id.myinfoCertifyNumber_editText:
                        if(binding.myinfoCertifyNumberEditText.getText().length() >= 6){
                            requestFocus(binding.myinfoCertifyNumberEditText2);
                        }
                        break;
                    case R.id.myinfoPhoneNumber_editText:
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
    }

}