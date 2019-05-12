package quantec.com.moneypot.Activity.Login.MemberShipPage;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import quantec.com.moneypot.Activity.FIDO.ActivityAuthFidoSingle;
import quantec.com.moneypot.Activity.Login.Model.dModel.IdentifyDto;
import quantec.com.moneypot.Activity.Login.Model.dModel.ModelConfrimIdentifyData;
import quantec.com.moneypot.Activity.Login.Model.nModel.ModelIdentifyData;
import quantec.com.moneypot.Dialog.DialogBottomSheet;
import quantec.com.moneypot.Dialog.DialogLoadingMakingPort;
import quantec.com.moneypot.Dialog.DialogSMS;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.Util.SharedPreferenceUtil.SharedPreferenceUtil;
import quantec.com.moneypot.Util.SoftKeyboardUtil.BackPressEditText;
import quantec.com.moneypot.databinding.ActivityRePhoneConfirmBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRePhoneConfirm extends AppCompatActivity  implements View.OnClickListener, View.OnFocusChangeListener{

    ActivityRePhoneConfirmBinding binding;
    private DialogBottomSheet dialogBottomSheet;
    // 기본값(통신사) : 00 / "SKT:01, KT:02, LGT:03, SKT_MVNO:04, KT_MVNO:05, LG_MVNO : 06"
    String agencyNumber = "00";

    ModelIdentifyData modelLoginData = new ModelIdentifyData();
    ModelConfrimIdentifyData modelConfrimIdentifyData = new ModelConfrimIdentifyData();

    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000;
    final int COUNT_DOWN_INTERVAL = 1000;

    InputMethodManager imm;

    boolean openedConfirmNumber;
    DialogSMS dialogSMS;

    String passwordPage;

    private DialogLoadingMakingPort loadingCustomMakingPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(ActivityRePhoneConfirm.this, R.layout.activity_re_phone_confirm);
        binding.setRePhoneConfirm(this);

//        binding.myinfoPhoneNumberNumberBt.setBackgroundResource(R.drawable.rectangle_gray_5dp);

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

        binding.myinfoPhoneNumberEditText.setOnBackPressListener(onBackPressListener);
        binding.myinfoNameEditText.setOnBackPressListener(onBackPressListener);
        binding.myinfoCertifyNumberEditText.setOnBackPressListener(onBackPressListener);
        binding.myinfoCertifyNumberEditText2.setOnBackPressListener(onBackPressListener);
        binding.myinfoConfirmNumberEditText.setOnBackPressListener(onBackPressListener);

        Intent intent = getIntent();
        passwordPage = intent.getStringExtra("passwordPage");

    }


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
            binding.myinfoPhoneNumberNumberBt.setBackgroundResource(R.drawable.rectangle_red_5dp);
            binding.myinfoPhoneNumberNumberBt.setText("재인증");
            binding.myinfoPhoneNumberNumberBt.setTextColor(getResources().getColor(R.color.text_white_color));

            requestFocus2(binding.myinfoNameEditText);
            visibleCertNum(true);

        }else{
            binding.myinfoPhoneNumberNumberBt.setBackgroundResource(R.drawable.rectangle_red_5dp);
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
            binding.nextBt.setBackgroundColor(getResources().getColor(R.color.text_red_color));
            binding.nextBt.setTextColor(getResources().getColor(R.color.text_white_color));

            binding.myinfoConfirmNumber.setVisibility(View.VISIBLE);
            binding.myinfoConfirmNumberEditText.setVisibility(View.VISIBLE);
            binding.myinfoConfirmNumberCountText.setVisibility(View.VISIBLE);
            binding.myinfoConfirmNumberEditTextLine.setVisibility(View.VISIBLE);

        }else{

            binding.myinfoConfirmNumberEditText.setText("");

            openedConfirmNumber = false;

            binding.nextBt.setEnabled(false);
            binding.nextBt.setBackgroundColor(getResources().getColor(R.color.button_enable));
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

                dialogBottomSheet = new DialogBottomSheet(ActivityRePhoneConfirm.this, agencyNumber, sktListener, ktListener, lgListener, sktRListener, ktRListener, lgRListener, closeListener);
                dialogBottomSheet.show();
                break;

            case R.id.myinfoPhoneNumber_NumberBt:

                if (binding.myinfoNameEditText.getText().toString().isEmpty()) {
                    requestFocus2(binding.myinfoNameEditText);
                    Toast.makeText(ActivityRePhoneConfirm.this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                } else {

                    if(binding.myinfoCertifyNumberEditText.getText().toString().isEmpty()){
                        requestFocus2(binding.myinfoCertifyNumberEditText);
                        Toast.makeText(ActivityRePhoneConfirm.this, "주민 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    }else{


                        if(binding.myinfoCertifyNumberEditText.getText().length() < 6){
                            requestFocus2(binding.myinfoCertifyNumberEditText);
                            Toast.makeText(ActivityRePhoneConfirm.this, "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }else{

                            if(binding.myinfoCertifyNumberEditText2.getText().toString().isEmpty()){
                                requestFocus2(binding.myinfoCertifyNumberEditText2);
                                Toast.makeText(ActivityRePhoneConfirm.this, "주민 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                            }else{

                                if (validateDay(binding.myinfoCertifyNumberEditText2.getText().toString(), binding.myinfoCertifyNumberEditText.getText().toString())) {

                                    if(binding.myinfoPhoneNumberEditText.getText().toString().isEmpty()){
                                        requestFocus2(binding.myinfoPhoneNumberEditText);
                                        Toast.makeText(ActivityRePhoneConfirm.this, "전화 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                                    }else{

                                        if(agencyNumber.equals("00")){
                                            imm.hideSoftInputFromWindow(binding.myinfoNameEditText.getWindowToken(), 0);
                                            imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText.getWindowToken(), 0);
                                            imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText2.getWindowToken(), 0);
                                            imm.hideSoftInputFromWindow(binding.myinfoPhoneNumberEditText.getWindowToken(), 0);

                                            requestFocus2(binding.myinfoNameEditText);
                                            Toast.makeText(ActivityRePhoneConfirm.this, "통신사를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                                        }else{

                                            loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityRePhoneConfirm.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
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

                                            Call<ModelIdentifyData> getReList = RetrofitClient.getInstance(ActivityRePhoneConfirm.this).getService().getIdentifyData("application/json",  jsonItentifyData);
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
                                                                Toast.makeText(ActivityRePhoneConfirm.this, "본인인증을 위한 성명/생년월일/휴대폰 번호를 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                                                                closedCertNum();
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
                                    }

                                } else {
                                    requestFocus2(binding.myinfoCertifyNumberEditText);
                                    Toast.makeText(ActivityRePhoneConfirm.this, "생년월일 정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
                break;

            case R.id.nextBt:
                if(binding.myinfoConfirmNumberEditText.getText().toString().isEmpty() || binding.myinfoConfirmNumberEditText.getText().length() < 6){
                    Toast.makeText(ActivityRePhoneConfirm.this, "인증번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else{

                    modelLoginData.getContent().setType("confirm");
                    modelLoginData.getContent().setInputCode(binding.myinfoConfirmNumberEditText.getText().toString());

                    Call<ModelIdentifyData> getReList = RetrofitClient.getInstance(ActivityRePhoneConfirm.this).getService().getIdentifyData("application/json",  modelLoginData.getContent());
                    getReList.enqueue(new Callback<ModelIdentifyData>() {
                        @Override
                        public void onResponse(Call<ModelIdentifyData> call, Response<ModelIdentifyData> response) {
                            if (response.code() == 200) {

                                if(response.body().getStatus() == 200){

                                    if(response.body().getContent().getCode().equals("0000")){

                                        SharedPreferenceUtil.getInstance(ActivityRePhoneConfirm.this).putUserId("userId", response.body().getContent().getUid());
                                        SharedPreferenceUtil.getInstance(ActivityRePhoneConfirm.this).putUserCid("userCid", response.body().getContent().getCi());
                                        SharedPreferenceUtil.getInstance(ActivityRePhoneConfirm.this).putUserHpNumber("hpNumber", response.body().getContent().getMobileNum());
                                        SharedPreferenceUtil.getInstance(ActivityRePhoneConfirm.this).putAuthCode("authCode", response.body().getContent().getAuthCode());

                                        if(passwordPage.equals("NotFido")){
                                            Intent intent = new Intent(ActivityRePhoneConfirm.this, ActivityRePassword.class);
                                            intent.putExtra("hpNumber",response.body().getContent().getMobileNum());
                                            intent.putExtra("uid",response.body().getContent().getUid());
                                            intent.putExtra("cid",response.body().getContent().getCi());
                                            intent.putExtra("userName",response.body().getContent().getName());
                                            intent.putExtra("authCode", response.body().getContent().getAuthCode());
                                            startActivity(intent);

                                        }else{
                                            Intent intent = new Intent(ActivityRePhoneConfirm.this, ActivityAuthFidoSingle.class);
                                            intent.putExtra("hpNumber",response.body().getContent().getMobileNum());
                                            intent.putExtra("uid",response.body().getContent().getUid());
                                            intent.putExtra("cid",response.body().getContent().getCi());
                                            intent.putExtra("userName",response.body().getContent().getName());
                                            intent.putExtra("authCode", response.body().getContent().getAuthCode());
                                            setResult(201, intent);
                                            finish();

                                        }

                                    }
                                    else if(response.body().getContent().getCode().equals("0043")){
                                        dialogSMS = new DialogSMS(ActivityRePhoneConfirm.this, okListener);
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

    private BackPressEditText.OnBackPressListener onBackPressListener = new BackPressEditText.OnBackPressListener() {
        @Override
        public void onBackPress()
        {
            imm.hideSoftInputFromWindow(binding.myinfoNameEditText.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(binding.myinfoCertifyNumberEditText2.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(binding.myinfoPhoneNumberEditText.getWindowToken(), 0);

            requestFocus2(binding.myinfoNameEditText);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
    }


}
