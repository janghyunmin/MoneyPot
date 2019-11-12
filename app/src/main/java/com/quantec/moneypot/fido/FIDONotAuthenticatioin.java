package com.quantec.moneypot.fido;

import android.content.Context;
import android.os.Build;

import com.dream.magic.fido.rpsdk.callback.FIDOCallbackResult;
import com.dream.magic.fido.rpsdk.client.Authentication;
import com.dream.magic.fido.rpsdk.client.FIDO_UI_TYPE;
import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;

import java.util.Hashtable;

public class FIDONotAuthenticatioin {

    public final int ConnectTimeOut 	= 1000 * 30;
    public final int ReadTimeOut 		= 1000 * 30;

    //RP SDK 를 사용하기 위한 객체
    private Authentication mAuth = null;
    private FIDOCallbackResult mFidoCallback = null;
    private MagicFIDOUtil magicFIDOUtil = null;

    public FIDONotAuthenticatioin(Context context, FIDOCallbackResult fidoCallback){

        mFidoCallback = fidoCallback;
        mAuth = new Authentication(context, mFidoCallback, PropertyManager.getReqUrl(), PropertyManager.getResUrl());
        mAuth.disUseWaitDialog();
        mAuth.setNetworkTimeout(ConnectTimeOut, ReadTimeOut);

        magicFIDOUtil = new MagicFIDOUtil(context);
        magicFIDOUtil.setAuthenticatorResetCallbackEnable(false);
    }

    public FIDONotAuthenticatioin(FIDOCallbackResult fidoCallback, Context context){

        mFidoCallback = fidoCallback;
        mAuth = new Authentication(context, mFidoCallback, PropertyManager2.getReqUrl(), PropertyManager2.getResUrl());
        mAuth.disUseWaitDialog();
        mAuth.setNetworkTimeout(ConnectTimeOut, ReadTimeOut);

        magicFIDOUtil = new MagicFIDOUtil(context);
        magicFIDOUtil.setAuthenticatorResetCallbackEnable(true);
    }


    public void disableDialog(){
        mAuth.disUseWaitDialog();
    }

    public String getToken(){
        return mAuth.getToken();
    }

    public void startAuthentication(String userID){

        MagicFIDOUtil.setSSLEnable(true);
        // RPContext를 위한 세팅
        Hashtable<String, String> settingValue = new Hashtable<String, String>();
        settingValue.put("userName", userID);
        settingValue.put("deviceModel", Build.MODEL);
        settingValue.put("deviceOS", "A|"+Build.VERSION.RELEASE);

        setAuthenticationOption(); //패턴, 패스코드 인증장치 제한사항 설정
        mAuth.setLocalAuthFullScreen(false); // 패턴, 지문인증장치 Full 화면 설정
        mAuth.startAuthentication(settingValue);

    }

    public void startAuthentication2(String userID){

        MagicFIDOUtil.setSSLEnable(true);
        // RPContext를 위한 세팅
        Hashtable<String, String> settingValue = new Hashtable<String, String>();
        settingValue.put("userName", userID);
        settingValue.put("deviceModel", Build.MODEL);
        settingValue.put("deviceOS", "A|"+Build.VERSION.RELEASE);

        setAuthenticationOption2(); //패턴, 패스코드 인증장치 제한사항 설정
        mAuth.setLocalAuthFullScreen(false); // 패턴, 지문인증장치 Full 화면 설정
        mAuth.startAuthentication(settingValue);

    }

    //인증장치(패턴, 패스코드) 제한사항 설정
    private void setAuthenticationOption(){
        Hashtable<String, Object> authOption = new Hashtable<String, Object>();
        authOption.put(MagicFIDOUtil.KEY_RETRY_COUNT_TO_LOCK, 5);
        authOption.put(MagicFIDOUtil.KEY_MAX_LOCK_COUNT, 1);
        authOption.put(MagicFIDOUtil.KEY_LOCK_TIME, Integer.MAX_VALUE);//성공 , 초기화
        authOption.put(MagicFIDOUtil.KEY_USE_NUMBER_KEYPAD, true);
        magicFIDOUtil.setAuthenticatorOptions(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, authOption);
        magicFIDOUtil.setPasscodeUIType(FIDO_UI_TYPE.FIDO_PASSCODE_PIN6);
    }

    //인증장치(패턴, 패스코드) 제한사항 설정
    private void setAuthenticationOption2(){
        Hashtable<String, Object> authOption = new Hashtable<String, Object>();
        authOption.put(MagicFIDOUtil.KEY_RETRY_COUNT_TO_LOCK, 5);
        authOption.put(MagicFIDOUtil.KEY_LOCK_TIME, 1);
        magicFIDOUtil.setAuthenticatorOptions(LOCAL_AUTH_TYPE.LOCAL_FINGERPRINT_TYPE, authOption);
    }
}
