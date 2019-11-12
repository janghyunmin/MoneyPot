package com.quantec.moneypot.fido;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;

import com.dream.magic.fido.rpsdk.callback.FIDOCallbackResult;
import com.dream.magic.fido.rpsdk.client.FIDO_UI_TYPE;
import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;
import com.dream.magic.fido.rpsdk.client.Registration;

import java.util.Hashtable;
public class FIDORegistration {

	public final int ConnectTimeOut 	= 1000 * 30;
	public final int ReadTimeOut 		= 1000 * 30;

	//RP SDK 를 사용하기 위한 객체
	private Registration mRegist = null;
	private FIDOCallbackResult mFidoCallback = null;

	private String mAuthCode = null;
	private Context mContext = null;
	private MagicFIDOUtil magicFIDOUtil = null;

	public FIDORegistration(Context context, FIDOCallbackResult fidoCallback){
		mFidoCallback = fidoCallback;

		mRegist = new Registration(context, mFidoCallback, PropertyManager.getReqUrl(), PropertyManager.getResUrl());
		mRegist.disUseWaitDialog();
		mRegist.setNetworkTimeout(ConnectTimeOut, ReadTimeOut);
		magicFIDOUtil = new MagicFIDOUtil(mContext);
		mContext = context;
	}

	public FIDORegistration(FIDOCallbackResult fidoCallback, Context context){
		mFidoCallback = fidoCallback;

		mRegist = new Registration(context, mFidoCallback, PropertyManager2.getReqUrl(), PropertyManager2.getResUrl());
		mRegist.disUseWaitDialog();
		mRegist.setNetworkTimeout(ConnectTimeOut, ReadTimeOut);
		magicFIDOUtil = new MagicFIDOUtil(mContext);
		mContext = context;
	}

	public void registFIDO(String userID){
		registerFIDO(userID);
	}

	public String getToken(){
		return mRegist.getToken();
	}

	public void setAuthCode(String authcode) {
        this.mAuthCode = authcode;
    }

	//=========
	private void registerFIDO(Object obj){

		// SSL 검증 (false : SSL 검증 skip / defult : true)
		MagicFIDOUtil.setSSLEnable(true);

		// RPContext를 위한 세팅
		Hashtable<String, String> settingValue = new Hashtable<String, String>();
		settingValue.put("userName", (String) obj);
		settingValue.put("deviceModel", Build.MODEL);
		settingValue.put("deviceOS", "A|"+VERSION.RELEASE);

		setAuthenticationOption(); //패턴, 패스코드 인증장치 제한사항 설정
		mRegist.setLocalAuthFullScreen(false); // 패턴, 지문인증장치 Full 화면 설정
		mRegist.startRegistration(settingValue);
	}

	//인증장치(패턴, 패스코드) 제한사항 설정
	private void setAuthenticationOption(){
		Hashtable<String, Object> authOption = new Hashtable<String, Object>();
		authOption.put(MagicFIDOUtil.KEY_RETRY_COUNT_TO_LOCK, 5);
		authOption.put(MagicFIDOUtil.KEY_MAX_LOCK_COUNT, 1);
//		authOption.put(MagicFIDOUtil.KEY_LOCK_TIME, 1);//성공 , 초기화
		authOption.put(MagicFIDOUtil.KEY_USE_NUMBER_KEYPAD, true);
		magicFIDOUtil.setAuthenticatorOptions(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, authOption);
		magicFIDOUtil.setPasscodeUIType(FIDO_UI_TYPE.FIDO_PASSCODE_PIN6);
		magicFIDOUtil.setPasscodeResetCallbackEnable(false);

	}

}