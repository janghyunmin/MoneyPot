package com.quantec.moneypot.fido;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.dream.magic.fido.rpsdk.callback.FIDOCallbackResult;
import com.dream.magic.fido.rpsdk.client.FIDORequestCode;
import com.dream.magic.fido.rpsdk.client.FidoResult;
import com.dream.magic.fido.rpsdk.client.LOCAL_AUTH_TYPE;
import com.dream.magic.fido.rpsdk.client.MagicFIDOUtil;

import com.quantec.moneypot.R;

/**
 * Deregistration Activity
 *  - FIDO 해지 하기 위한 화면
 **/

public class DeregisterActivity extends AppCompatActivity {
	
	private EditText edit_userID = null;
	
	// RP SDK Object
	private FIDODeRegistration mDeregist = null;
	private final int ALERT_REQUEST = 0xA3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rp_activity_deregister);

		edit_userID = (EditText) findViewById(R.id.edit_userID);
		Button btn_f = (Button) findViewById(R.id.btn_fido);
		
//		String userName = ActivityFidoMain.getAuthID();
//		if( userName != null)
//			edit_userID.setText(userName);
		
		// RPContext를 위한 세팅				
		
		btn_f.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				/**
				 * Step 01 Initialize Deregistration
				 *  -해지 할 등록된 인증서 선택
				 */
				// RP SDK 생성
				mDeregist = new FIDODeRegistration(DeregisterActivity.this,  fidoCallback);
				
				String userID = edit_userID.getText().toString();

				mDeregist.deRegistFIDO(userID);
			}
			
		});
		
	}
	
	private FIDOCallbackResult fidoCallback = new FIDOCallbackResult() {

		@Override
		public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {

			if(requestCode == FIDORequestCode.REQUEST_CODE_DEREG){
				// FIDO Client에 전송하지 못 하였을 경우.
				if(fidoResult.getErrorCode() != FidoResult.RESULT_SUCCESS){
//					showDialog("FAIL", fidoResult.getDescription() + "(" +
//							fidoResult.getErrorCode() + ")");
				}else{
					String token = mDeregist.getToken();

					isSuccess = true;
//					showDialog("SUCCESS", "Deregister Success");

					//파이도 사용자를 앱자체에서 삭제
					MagicFIDOUtil magicFIDOUtil = new MagicFIDOUtil(DeregisterActivity.this);
					magicFIDOUtil.resetUserVerification(LOCAL_AUTH_TYPE.LOCAL_PACODE_TYPE, fidoCallback);
				}
			}
		}
	};
	
	//=======================Activity에서 쓰이는 Alert창==============================
	
//	private void showDialog(String title, String body) {
//
//		Intent alertIntent = new Intent(this, AlertDlg.class);
//		alertIntent.putExtra("msg", body);
//		startActivityForResult(alertIntent, ALERT_REQUEST);
//	}
	
	private boolean isSuccess = false;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == ALERT_REQUEST && isSuccess == true){
			finish();
		}
	}
}
