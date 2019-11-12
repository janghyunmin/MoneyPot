package com.quantec.moneypot.activity.MyInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dream.magic.fido.rpsdk.callback.FIDOCallbackResult;
import com.dream.magic.fido.rpsdk.client.FIDORequestCode;
import com.dream.magic.fido.rpsdk.client.FIDO_UI_TYPE;
import com.dream.magic.fido.rpsdk.client.FidoResult;
import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import permissions.dispatcher.NeedsPermission;
import com.quantec.moneypot.activity.FIDO.LoginFidoTokenData;
import com.quantec.moneypot.activity.Intro.ModelVerifiedFido;
import com.quantec.moneypot.activity.Login.Model.dModel.FidoReq;
import com.quantec.moneypot.activity.Login.Model.dModel.ModelFidoauthReqDto;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelFidoAuthCode;
import com.quantec.moneypot.activity.Login.Model.nModel.ModelFlushAuth;
import com.quantec.moneypot.BuildConfig;
import com.quantec.moneypot.dialog.DialogExitApp;
import com.quantec.moneypot.dialog.DialogFidoCancle;
import com.quantec.moneypot.dialog.DialogLoadingMakingPort;
import com.quantec.moneypot.dialog.DialogNetworkError;
import com.quantec.moneypot.fido.FIDONotAuthenticatioin;
import com.quantec.moneypot.fido.FIDORegistration;
import com.quantec.moneypot.fido.PropertyManager;
import com.quantec.moneypot.fido.PropertyManager2;
import com.quantec.moneypot.network.retrofit.RetrofitClient;
import com.quantec.moneypot.R;
import com.quantec.moneypot.databinding.ActivityMyInfoBinding;
import com.quantec.moneypot.util.GlideEngine.MyGlideEngine;
import com.quantec.moneypot.util.Permissions.PermissionsDispatcher;
import com.quantec.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMyInfo extends AppCompatActivity implements View.OnClickListener {

    ActivityMyInfoBinding binding;

    String mImageUrl;

    static final int PHOTO = 1;
    static final int CODE_FINISH = 99;

    private String mCurrentPhotoPath;
    Uri selectedUri = null;


    private MagicFIDOUtil mFidoUtil = null;
    private FIDONotAuthenticatioin mAuth = null;
    String userId, devId, authCode;

    private FIDORegistration reg2 = null;

    DialogFidoCancle dialogFidoCancle;
    private DialogExitApp dialogExitApp;
    private DialogNetworkError dialogNetworkError;
    private DialogLoadingMakingPort loadingCustomMakingPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_info);


        if(SharedPreferenceUtil.getInstance(ActivityMyInfo.this).getStateExtra("fingerState")){
            binding.switchView.setChecked(true);
        }
        else{
            binding.switchView.setChecked(false);
        }

        PropertyManager.load(this);
        PropertyManager2.load(this);
        userId = SharedPreferenceUtil.getInstance(ActivityMyInfo.this).getStringExtra("userId");
        mFidoUtil = new MagicFIDOUtil(this);

        checkedUseFido();

        binding.emailPhone.setOnClickListener(this);
        binding.password.setOnClickListener(this);
        binding.fidoPassword.setOnClickListener(this);

        binding.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                authCode = SharedPreferenceUtil.getInstance(ActivityMyInfo.this).getStringExtra("authCode");

                ModelFidoauthReqDto fidoAuthReqDto = new ModelFidoauthReqDto(authCode, "FINGER", userId);
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String data = gson.toJson(fidoAuthReqDto).replace("\\n", "").replace(" ", "")
                        .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
                Call<ModelFlushAuth> tt = RetrofitClient.getInstance(ActivityMyInfo.this).getService().getFlushAuthData("application/json", data);
                tt.enqueue(new Callback<ModelFlushAuth>() {
                    @Override
                    public void onResponse(Call<ModelFlushAuth> call, Response<ModelFlushAuth> response) {
                        if(response.code() == 200) {
                            SharedPreferenceUtil.getInstance(ActivityMyInfo.this).putAuthCode("authCode", response.body().getContent().getAuthCode());
                            SharedPreferenceUtil.getInstance(ActivityMyInfo.this).putTokenA("aToken", response.headers().get("Authorization"));

                            if(isChecked){

                                loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityMyInfo.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                                loadingCustomMakingPort.show();

                                Hashtable<String, Object> authUICustom = new Hashtable<String, Object>();
                                authUICustom.put("text1", "지문 로그인 활성화");
                                authUICustom.put("text2", "보안키패드 작동중...");

                                Hashtable<String, Object> settingValue = new Hashtable<String, Object>();
                                settingValue.put("passcode", authUICustom);
                                mFidoUtil.setCustomUIValues(settingValue);
                                // RP SDK 생성
                                mAuth = new FIDONotAuthenticatioin(ActivityMyInfo.this, fidoCallbackResult);
                                mAuth.startAuthentication(userId);

                            }
                            else{
                                SharedPreferenceUtil.getInstance(ActivityMyInfo.this).putFingerState("fingerState", false);
                                Toast.makeText(ActivityMyInfo.this, "지문 미사용", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    @Override
                    public void onFailure(Call<ModelFlushAuth> call, Throwable t) {
                        loadingCustomMakingPort.dismiss();
                    }
                });
            }
        });

        //포트 이미지 초기화
        Glide.with(this)
                .load(mImageUrl)
                .error(R.drawable.noname_img)
                .placeholder(R.drawable.noname_img)
                .circleCrop()
                .into(binding.userImage);


        //앨범 선택
        binding.albumBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsDispatcher.selectPhotoWithCheck(ActivityMyInfo.this);
            }
        });
        //사진 촬영 선택
        binding.photoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionsDispatcher.takePictureWithCheck(ActivityMyInfo.this);
            }
        });

    }// onCreate 끝


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void takePicture(){
        dispatchTakePictur();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void selectPhoto(){

//        Matisse.from(ActivityMyInfo.this)
//                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
//                .countable(true)
//                .theme(R.style.Matisse_Dracula)
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .thumbnailScale(0.85f)
//                .imageEngine(new GlideEngine())
//                .forResult(CODE_FINISH);

        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(false)
                .showSingleMediaType(true)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .imageEngine(new MyGlideEngine())
                .forResult(CODE_FINISH);
    }

    private void dispatchTakePictur(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            }catch (IOException e){}
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID +".fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir  = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_FINISH && resultCode == RESULT_OK) {
            selectedUri = Matisse.obtainResult(data).get(0);
            Glide.with(this)
                    .load(selectedUri)
                    .placeholder(R.drawable.noname_img)
                    .error(R.drawable.noname_img)
                    .circleCrop()
                    .into(binding.userImage);
        }
        if(requestCode == PHOTO && resultCode == RESULT_OK) {
            File file = new File(mCurrentPhotoPath);
            selectedUri = Uri.fromFile(file);
            Glide.with(this)
                    .load(selectedUri)
                    .placeholder(R.drawable.noname_img)
                    .error(R.drawable.noname_img)
                    .circleCrop()
                    .into(binding.userImage);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_phone:
                Intent intent2 = new Intent(ActivityMyInfo.this, ActivityPhoneEmail.class);
                startActivity(intent2);
                break;
            case R.id.password:
                Intent intent = new Intent(ActivityMyInfo.this, ActivityAuthPassword.class);
                startActivity(intent);
                break;
            case R.id.fidoPassword:

                loadingCustomMakingPort = new DialogLoadingMakingPort(ActivityMyInfo.this, "주식투자는 단기적 수익을 쫒기 보다는\n장기적으로 보아야 성공할 수 있습니다.");
                loadingCustomMakingPort.show();

                authCode = SharedPreferenceUtil.getInstance(ActivityMyInfo.this).getStringExtra("authCode");

                ModelFidoauthReqDto fidoAuthReqDto = new ModelFidoauthReqDto(authCode, "PASSCODE", userId);
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                String data = gson.toJson(fidoAuthReqDto).replace("\\n", "").replace(" ", "")
                        .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
                Call<ModelFlushAuth> tt = RetrofitClient.getInstance(ActivityMyInfo.this).getService().getFlushAuthData("application/json", data);
                tt.enqueue(new Callback<ModelFlushAuth>() {
                    @Override
                    public void onResponse(Call<ModelFlushAuth> call, Response<ModelFlushAuth> response) {
                        if(response.code() == 200) {
                            SharedPreferenceUtil.getInstance(ActivityMyInfo.this).putAuthCode("authCode", response.body().getContent().getAuthCode());
                            SharedPreferenceUtil.getInstance(ActivityMyInfo.this).putTokenA("aToken", response.headers().get("Authorization"));

                            Hashtable<String, Object> authUICustom = new Hashtable<String, Object>();
                            authUICustom.put("text1", "암호 재설정");
                            authUICustom.put("text2", "보안키패드 작동중...");

                            Hashtable<String, Object> settingValue = new Hashtable<String, Object>();
                            settingValue.put("passcode", authUICustom);
                            mFidoUtil.setCustomUIValues(settingValue);

                            Hashtable<String, Object> authOption = new Hashtable<String, Object>();
                            authOption.put(MagicFIDOUtil.KEY_RETRY_COUNT_TO_LOCK, 5);
                            authOption.put(MagicFIDOUtil.KEY_MAX_LOCK_COUNT, 1);
                            authOption.put(MagicFIDOUtil.KEY_LOCK_TIME, Integer.MAX_VALUE);//성공 , 초기화
                            authOption.put(MagicFIDOUtil.KEY_USE_NUMBER_KEYPAD, true);
                            mFidoUtil.setAuthenticatorOptions(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, authOption);
                            mFidoUtil.setPasscodeUIType(FIDO_UI_TYPE.FIDO_PASSCODE_PIN6);
                            mFidoUtil.setPasscodeResetCallbackEnable(false);
                            // RP SDK 생성
                            mFidoUtil.changeUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE , fidoCallbackResult2 , true);

                        }
                    }
                    @Override
                    public void onFailure(Call<ModelFlushAuth> call, Throwable t) {
                        loadingCustomMakingPort.dismiss();
                    }
                });


                break;
        }
    }


    private void checkedUseFido(){

        if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE) && mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_FINGERPRINT_TYPE)){
            binding.password.setVisibility(View.GONE);
            binding.passwordImage.setVisibility(View.GONE);

            binding.fidoPassword.setVisibility(View.VISIBLE);
            binding.fidoPasswordImage.setVisibility(View.VISIBLE);

            binding.finger.setVisibility(View.VISIBLE);
            binding.switchView.setVisibility(View.VISIBLE);
        }
        else if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE)){
            binding.password.setVisibility(View.GONE);
            binding.passwordImage.setVisibility(View.GONE);

            binding.fidoPassword.setVisibility(View.VISIBLE);
            binding.fidoPasswordImage.setVisibility(View.VISIBLE);

            binding.finger.setVisibility(View.GONE);
            binding.switchView.setVisibility(View.GONE);

        }
        else{
            binding.password.setVisibility(View.VISIBLE);
            binding.passwordImage.setVisibility(View.VISIBLE);

            binding.fidoPassword.setVisibility(View.GONE);
            binding.fidoPasswordImage.setVisibility(View.GONE);

            binding.finger.setVisibility(View.GONE);
            binding.switchView.setVisibility(View.GONE);
        }
    }

    public String JTdecoded(String JWTEncoded) throws Exception {
        String[] split = JWTEncoded.split("\\.");

        Gson gson = new Gson();
        LoginFidoTokenData data = gson.fromJson(getJson(split[1]), LoginFidoTokenData.class);

        return data.deviceid;
    }

    private  String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decode =  android.util.Base64.decode(strEncoded,  Base64.URL_SAFE);
        return new String(decode, "utf-8");
    }


    private void verrifiedUser(String token){

        ModelFidoauthReqDto withdrawFido = new ModelFidoauthReqDto("", "PASSCODE", userId);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");

        Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(ActivityMyInfo.this).getService().getFidoAuthCode("application/json", jsonItentifyData);
        getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
            @Override
            public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
                if(response.code() == 200) {

                    authCode = response.body().getContent().getAuthCode();

                    FidoReq fidoReq = new FidoReq(authCode, devId, "", "PASSCODE", "", token, "A", userId);
                    Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityMyInfo.this).getService().getVerifyData("application/json",  fidoReq);
                    getReList.enqueue(new Callback<ModelVerifiedFido>() {
                        @Override
                        public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                            if (response.code() == 200) {

                                SharedPreferenceUtil.getInstance(ActivityMyInfo.this).putTokenA("aToken", response.headers().get("Authorization"));
                                SharedPreferenceUtil.getInstance(ActivityMyInfo.this).putAuthCode("authCode", response.body().getContent().getAuthCode());

                                if(mFidoUtil.isAvailableFIDO(LOCAL_AUTH_TYPE.LOCAL_FINGERPRINT_TYPE)) {

                                    ModelFidoauthReqDto withdrawFido = new ModelFidoauthReqDto("", "FINGER", userId);
                                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                                    String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
                                            .replace("\\", "").replace("\"{", "{").replace("}\"", "}");
                                    Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(ActivityMyInfo.this).getService().getFidoAuthCode("application/json", jsonItentifyData);
                                    getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
                                        @Override
                                        public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
                                            if(response.code() == 200) {
                                                authCode = response.body().getContent().getAuthCode();

                                                reg2 = new FIDORegistration(fidoResult2, ActivityMyInfo.this);

                                                if (userId.length() < 1) {
                                                    Toast.makeText(ActivityMyInfo.this, "Please, Fill out the UserID.", Toast.LENGTH_LONG).show();
                                                    return;
                                                }
                                                // FIDO
                                                reg2.registFIDO(userId);
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<ModelFidoAuthCode> call, Throwable t) {
                                        }
                                    });
                                }
                                else{
                                    binding.switchView.setChecked(false);
                                    loadingCustomMakingPort.dismiss();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelFidoAuthCode> call, Throwable t) {
            }
        });
    }

    private void verrifiedFingereUser(String token){

        ModelFidoauthReqDto withdrawFido = new ModelFidoauthReqDto("", "FINGER", userId);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String jsonItentifyData = gson.toJson(withdrawFido).replace("\\n", "").replace(" ", "")
                .replace("\\", "").replace("\"{", "{").replace("}\"", "}");

        Call<ModelFidoAuthCode> getRegChkData = RetrofitClient.getInstance(ActivityMyInfo.this).getService().getFidoAuthCode("application/json", jsonItentifyData);
        getRegChkData.enqueue(new Callback<ModelFidoAuthCode>() {
            @Override
            public void onResponse(Call<ModelFidoAuthCode> call, Response<ModelFidoAuthCode> response) {
                if(response.code() == 200) {

                    authCode = response.body().getContent().getAuthCode();

                    FidoReq fidoReq = new FidoReq(authCode, devId, "", "FINGER", "", token, "R", userId);
                    Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(ActivityMyInfo.this).getService().getVerifyData("application/json",  fidoReq);
                    getReList.enqueue(new Callback<ModelVerifiedFido>() {
                        @Override
                        public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                            if (response.code() == 200) {

                                SharedPreferenceUtil.getInstance(ActivityMyInfo.this).putTokenA("aToken", response.headers().get("Authorization"));
                                SharedPreferenceUtil.getInstance(ActivityMyInfo.this).putAuthCode("authCode", response.body().getContent().getAuthCode());
                                SharedPreferenceUtil.getInstance(ActivityMyInfo.this).putFingerState("fingerState", true);

                                binding.switchView.setChecked(true);
                                loadingCustomMakingPort.dismiss();
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
                            loadingCustomMakingPort.dismiss();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ModelFidoAuthCode> call, Throwable t) {
            }
        });
    }

    private FIDOCallbackResult fidoCallbackResult = new FIDOCallbackResult() {
        @Override
        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {
            if(requestCode == FIDORequestCode.REQUEST_CODE_AUTH) {
                if(fidoResult.getErrorCode() == FidoResult.RESULT_SUCCESS){
                    String token = mAuth.getToken();

                    try {
                        devId = JTdecoded(token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    verrifiedUser(token);

                }else{
                    binding.switchView.setChecked(false);
                    loadingCustomMakingPort.dismiss();
                    // 실패 사유
                    switch(fidoResult.getErrorCode()){
                        case FidoResult.RESULT_USER_CANCEL:
                            break;
                        // 에러코드 999: 사용자 취소
                        case FidoResult.ERROR_JSON_PARSER:
                            break;
                        // 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
                        case FidoResult.ERROR_CANNOT_USE_FIDO:
                            break;
                        // 에러코드 1007: 지원하지 않는 단말기
                        case 13006:
                            break;
                        // 에러코드 13006: -인증 시 등록 된 아이디가 없는 사유로 서버에서 전달하는 에러코드
                        case 19019:
                            break;
                        // 에러코드 19019: -아이디 누락으로 인한 실패 사유로 서버에서 전달하는 에러코드
                        case FidoResult.ERROR_UNTRUSTED_FACET_ID:
                            break;
                        // 에러코드 2001: -등록되지 않은 3rd App으로 진행
                        case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
                            break;
                        // 에러코드 2002: 적합한 인증장치가 없음
                        case FidoResult.ERROR_REMOVE_KEYSTORE_IN_SW:
                            break;
                        // 에러코드 2004: -잠금 화면 설정 변경으로 인한 초기화 발생
                        case FidoResult.ERROR_NETWORK_STATE:
                            // 에러코드 4001: -네트워크 오류
                            break;
                        case FidoResult.PW_NOT_MATCH_BIO_FINGER:
                            break;
                        // 에러코드 5000: 지문 인증 실패
                        case FidoResult.ERROR_UNKNOWN:
                            break;
                        case FidoResult.ERROR_NOT_EXIST_USER_DATA:
                            break;
                        case FidoResult.ERROR_MAX_LOCK_COUNT_OVER:

                            finish();
                            Toast.makeText(ActivityMyInfo.this, "초과하였습니다  재등록합니다", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                    }
                }
            }
        }
    };

    FIDOCallbackResult fidoCallbackResult2 = new FIDOCallbackResult() {
        @Override
        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {

            if(requestCode == FIDORequestCode.REQUEST_LOCAL_CHANGE_VALUE){
                if(fidoResult.getErrorCode() == FidoResult.RESULT_SUCCESS){

                    Toast.makeText(ActivityMyInfo.this, "등록 성공", Toast.LENGTH_SHORT).show();
                    loadingCustomMakingPort.dismiss();
                }
                else{
                    // 실패 사유
                    switch(fidoResult.getErrorCode()){
                        case FidoResult.RESULT_USER_CANCEL:

                            Toast.makeText(ActivityMyInfo.this, "등록 취소", Toast.LENGTH_SHORT).show();
                            loadingCustomMakingPort.dismiss();

                            break;
                        // 에러코드 999: 사용자 취소
                        case FidoResult.ERROR_JSON_PARSER:
                            break;
                        // 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
                        case FidoResult.ERROR_CANNOT_USE_FIDO:
                            break;
                        // 에러코드 1007: 지원하지 않는 단말기
                        case 13006:
                            break;
                        // 에러코드 13006: -인증 시 등록 된 아이디가 없는 사유로 서버에서 전달하는 에러코드
                        case 19019:
                            break;
                        // 에러코드 19019: -아이디 누락으로 인한 실패 사유로 서버에서 전달하는 에러코드
                        case FidoResult.ERROR_UNTRUSTED_FACET_ID:
                            break;
                        // 에러코드 2001: -등록되지 않은 3rd App으로 진행
                        case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
                            break;
                        // 에러코드 2002: 적합한 인증장치가 없음
                        case FidoResult.ERROR_REMOVE_KEYSTORE_IN_SW:
                            break;
                        // 에러코드 2004: -잠금 화면 설정 변경으로 인한 초기화 발생
                        case FidoResult.ERROR_NETWORK_STATE:
                            // 에러코드 4001: -네트워크 오류
                            break;
                        case FidoResult.PW_NOT_MATCH_BIO_FINGER:
                            break;
                        // 에러코드 5000: 지문 인증 실패
                        case FidoResult.ERROR_UNKNOWN:
                            break;
                        case FidoResult.ERROR_NOT_EXIST_USER_DATA:
                            break;
                        case FidoResult.ERROR_MAX_LOCK_COUNT_OVER:
                            finish();

                            Toast.makeText(ActivityMyInfo.this, "초과하였습니다  재등록합니다", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                    }
                }
            }
        }
    };

    private FIDOCallbackResult fidoResult2 = new FIDOCallbackResult() {
        @Override
        public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {

            if (requestCode == FIDORequestCode.REQUEST_CODE_REGIST) {

                if (fidoResult.getErrorCode() == FidoResult.RESULT_SUCCESS) {
                    // 에러코드 0: 등록 성공
                    String token = reg2.getToken();
                    try {
                        devId = JTdecoded(token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    verrifiedFingereUser(token);
                } else {
                    binding.switchView.setChecked(false);
                    loadingCustomMakingPort.dismiss();
                }
            }
        }
    };


}
