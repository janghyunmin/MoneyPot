package quantec.com.moneypot.FIDO;

import android.content.Context;
import android.util.Log;

import com.dream.magic.fido.rpsdk.callback.FIDOCallbackResult;
import com.dream.magic.fido.rpsdk.client.Deregistration;
import com.dream.magic.fido.rpsdk.client.FIDO_UI_TYPE;
import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;

import java.util.Hashtable;
public class FIDODeRegistration {
	
	public final int ConnectTimeOut 	= 1000 * 30;
	public final int ReadTimeOut 		= 1000 * 30;
	
	//RP SDK 를 사용하기 위한 객체
	private Deregistration mDeregist = null;
	private FIDOCallbackResult mFidoCallback = null;

	private MagicFIDOUtil magicFIDOUtil = null;
	
	public FIDODeRegistration(Context context, FIDOCallbackResult fidoCallback){
		mFidoCallback = fidoCallback;

		mDeregist = new Deregistration(context, mFidoCallback, PropertyManager.getReqUrl());
		mDeregist.disUseWaitDialog();
		mDeregist.setNetworkTimeout(ConnectTimeOut, ReadTimeOut);

		magicFIDOUtil = new MagicFIDOUtil(context);
	}
	
	public void deRegistFIDO(String userID){
		
		MagicFIDOUtil.setSSLEnable(true);

		// RPContext를 위한 세팅
		Hashtable<String, String> settingValue = new Hashtable<String, String>();
		settingValue.put("localType", Integer.toString(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE.getLocalType()));
		settingValue.put("userName", userID);

		mDeregist.startDeregistration(settingValue); //localType 미 설정 시 인증장치 선택 창 나옴(인증장치 2개 이상일 경우)
		
	}

	
	public String getToken(){
		return mDeregist.getToken();
	}

}