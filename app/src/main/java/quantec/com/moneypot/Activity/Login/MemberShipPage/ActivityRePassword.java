package quantec.com.moneypot.Activity.Login.MemberShipPage;

//import androidx.databinding.DataBindingUtil;

//import quantec.com.moneypot.databinding.ActivityRePasswordBinding;


//public class ActivityRePassword extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{
//
//    ActivityRePasswordBinding binding;
//
//    Matcher matcher, matcher2;
//
//    String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z]).{8,15}$";
//    String pwPattern2 = "(.)\\1\\1";
//    String regUid, regCid, authCode, regToken, hpNumber, userName;
//
//    DialogClosedSMS dialogClosedSMS;
//    DialogRePassword dialogRePassword;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_re_password);
//
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_re_password);
//        binding.setRePassword(this);
//
//        //스테이터스 바 색상 변경 -> 화이트
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }else{
//            Window window = getWindow();
//
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.main_page_status_bar_color));
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
//
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//
//        binding.regInfoPwEdit.addTextChangedListener(new MyTextWatcher(binding.regInfoPwEdit));
//        binding.regInfoRPwEditText.addTextChangedListener(new MyTextWatcher(binding.regInfoRPwEditText));
//
//        binding.regInfoPwEdit.setOnClickListener(this);
//        binding.regInfoRPwEditText.setOnClickListener(this);
//        binding.nextBt.setOnClickListener(this);
//        binding.backBt.setOnClickListener(this);
//        binding.regInfoPwEditDeleteBt.setOnClickListener(this);
//        binding.regInfoRPwEditTextDeleteBt.setOnClickListener(this);
//
//        binding.regInfoPwEdit.setOnFocusChangeListener(this);
//        binding.regInfoRPwEditText.setOnFocusChangeListener(this);
//
//        Intent intent = getIntent();
//        regUid = intent.getStringExtra("uid");
//        regCid = intent.getStringExtra("cid");
//        hpNumber = intent.getStringExtra("hpNumber");
//        userName = intent.getStringExtra("userName");
//        authCode = intent.getStringExtra("authCode");
//
//    }
//
//    private  String getJson(String strEncoded) throws UnsupportedEncodingException {
//        byte[] decode =  android.util.Base64.decode(strEncoded,  Base64.URL_SAFE);
//        return new String(decode, "utf-8");
//    }
//
//
//    private void requestFocus(View view) {
//        if (view.requestFocus()) {
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        }
//    }
//
//    //뒤로가기 가입 절차 확인 버튼
//    private View.OnClickListener backOkListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            dialogClosedSMS.dismiss();
////            Intent intent = new Intent(ActivityRePassword.this, ActivityMemberShipMain.class);
////            startActivity(intent);
//            finish();
////            overridePendingTransition(R.anim.leftin_activity,R.anim.rightout_activity);
//        }
//    };
//
//    //뒤로가기 가입 절차 취소 버튼
//    private View.OnClickListener backCancleListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            dialogClosedSMS.dismiss();
//        }
//    };
//
//    private View.OnClickListener okListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            Intent intent = new Intent(ActivityRePassword.this, ActivityLoginPage.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
//
//        }
//    };
//
//
//    private boolean isValidPassword(String password){
//
//        matcher = Pattern.compile(pwPattern).matcher(password);
//        matcher2 = Pattern.compile(pwPattern2).matcher(password);
//
//        if(!matcher.matches()) {
//            binding.regInfoPwDesc.setText("영문,숫자,특수문자를 포함하여 8글자 이상.");
//            binding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_red_color));
//            return false;
//        }
//        else {
//            if(matcher2.find()){
//                binding.regInfoPwDesc.setText("3자리 이상의 연속된 같은 문자/숫자를 사용할 수 없습니다.");
//                binding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_red_color));
//                return false;
//            }
//            else{
//
//                if(password.contains(" ")){
//                    binding.regInfoPwDesc.setText("비밀번호에 띄어쓰기를 포함할 수 없습니다.");
//                    binding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_red_color));
//                    return false;
//                }
//                else{
//                    binding.regInfoPwDesc.setText("영문,숫자,특수문자를 포함하여 8글자 이상.");
//                    binding.regInfoPwDesc.setTextColor(getResources().getColor(R.color.text_gray_color));
//
//                    return true;
//                }
//            }
//        }
//    }
//
//    // 비밀번호 불일치 여부
//    private boolean isValidChkPw(String password, String rePassword){
//        if(password.trim().equals(rePassword.trim())){
//            binding.regInfoRPwDesc.setVisibility(View.INVISIBLE);
//            return true;
//        }else{
//            binding.regInfoRPwDesc.setVisibility(View.VISIBLE);
//            return false;
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.nextBt:
//
//                if(isValidPassword(binding.regInfoPwEdit.getText().toString().trim())){
//                    if(isValidChkPw(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim())){
//
//                        confirmedUserInfo(regCid, regUid, binding.regInfoPwEdit.getText().toString().trim(), hpNumber, userName);
//
//                    }else{
//                        requestFocus(binding.regInfoRPwEditText);
//                    }
//                }else{
//                    requestFocus(binding.regInfoPwEdit);
//                }
//
//                break;
//            case R.id.backBt:
//                dialogClosedSMS = new DialogClosedSMS(ActivityRePassword.this, backCancleListener, backOkListener);
//                dialogClosedSMS.show();
//                break;
//            case R.id.regInfoPw_edit_deleteBt:
//                binding.regInfoPwEdit.setText("");
//                break;
//            case R.id.regInfoRPw_editText_deleteBt:
//                binding.regInfoRPwEditText.setText("");
//                break;
//        }
//    }
//
//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        switch (v.getId()) {
//
//            case R.id.regInfoPw_edit:
//                isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
//                break;
//            case R.id.regInfoRPw_editText:
//                isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
//                break;
//        }
//    }
//
//    // 문자 바로 삭제 버튼 비지블 인비지블
//    private void isVisibledDeleteBt(String password, String rePassword){
//        if(password.isEmpty()){
//            binding.regInfoPwEditDeleteBt.setVisibility(View.INVISIBLE);
//            if(rePassword.isEmpty()){
//                binding.regInfoRPwEditTextDeleteBt.setVisibility(View.INVISIBLE);
//            }else{
//                binding.regInfoRPwEditTextDeleteBt.setVisibility(View.VISIBLE);
//            }
//        }else{
//            binding.regInfoPwEditDeleteBt.setVisibility(View.VISIBLE);
//            if(rePassword.isEmpty()){
//                binding.regInfoRPwEditTextDeleteBt.setVisibility(View.INVISIBLE);
//            }else{
//                binding.regInfoRPwEditTextDeleteBt.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    private class MyTextWatcher implements TextWatcher {
//        private View view;
//        private MyTextWatcher(View view) {
//            this.view = view;
//        }
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        }
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        }
//        public void afterTextChanged(Editable editable) {
//            switch (view.getId()) {
//                case R.id.regInfoPw_edit:
//                    if(!binding.regInfoPwEdit.getText().toString().trim().isEmpty()) {
//                        isValidPassword(binding.regInfoPwEdit.getText().toString().trim());
//                        if(!binding.regInfoRPwEditText.getText().toString().trim().isEmpty()){
//                            isValidChkPw(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
//                        }
//                        isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
//                    }else{
//                        isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
//                    }
//                    break;
//                case R.id.regInfoRPw_editText:
//
//                    if(!binding.regInfoRPwEditText.getText().toString().trim().isEmpty()){
//                        isValidChkPw(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
//                        isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
//                    }
//                    else{
//                        isVisibledDeleteBt(binding.regInfoPwEdit.getText().toString().trim(), binding.regInfoRPwEditText.getText().toString().trim());
//                    }
//                    break;
//            }
//        }
//    }
//
//
//    private void confirmedUserInfo(String cid, String userId, String Password, String hpNumber, String userName){
//
//        String md5ToPassWord = md5(Password);
//
//        UserInfo userInfo = new UserInfo(10, authCode, cid,"", hpNumber, md5ToPassWord, userId, userName);
//
//        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//        String jsonItentifyData = gson.toJson(userInfo).replace("\\n", "").replace(" ", "")
//                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
//
//        Call<ModelUserInfo> getReList = RetrofitClient.getInstance().getService().getConfirmedUserInfo("plain/text", jsonItentifyData);
//        getReList.enqueue(new Callback<ModelUserInfo>() {
//            @Override
//            public void onResponse(Call<ModelUserInfo> call, Response<ModelUserInfo> response) {
//                if (response.code() == 200) {
//
//                    if(response.body().getStatus() == 200){
//
//                        authCode = response.body().getContent().getAuthCode();
//                        regToken = response.headers().get("Authorization");
//
//                        SharedPreferenceUtil.getInstance(ActivityRePassword.this).putTokenA("aToken", regToken);
//                        SharedPreferenceUtil.getInstance(ActivityRePassword.this).putAuthCode("authCode", authCode);
//
//
//                        dialogRePassword = new DialogRePassword(ActivityRePassword.this, okListener);
//                        dialogRePassword.show();
//
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<ModelUserInfo> call, Throwable t) {
//            }
//        });
//    }
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
//
//}
