
package quantec.com.moneypot.FIDO;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dream.magic.fido.rpsdk.callback.FIDOCallbackResult;
import com.dream.magic.fido.rpsdk.client.FIDORequestCode;
import com.dream.magic.fido.rpsdk.client.FidoResult;

import quantec.com.moneypot.Activity.Intro.ModelVerifiedFido;
import quantec.com.moneypot.Activity.Login.Model.dModel.FidoReq;
import quantec.com.moneypot.Network.Retrofit.RetrofitClient;
import quantec.com.moneypot.R;
import quantec.com.moneypot.util.SharedPreference.SharedPreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Authentication Activity
 *  - FIDO 인증하기 위한 화면 (Transaction Confirmation 이 있음)
 **/

public class AuthenticationActivity extends AppCompatActivity {

	// RP SDK Object
	private FIDOAuthentication mAuth = null;
	
	private final int ALERT_REQUEST = 0xA3;

	String userId, devId, authCode, site;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.rp_activity_authentication);

        PropertyManager.load(this);
        PropertyManager2.load(this);

		userId = SharedPreferenceUtil.getInstance(AuthenticationActivity.this).getStringExtra("userId");
        authCode = SharedPreferenceUtil.getInstance(AuthenticationActivity.this).getStringExtra("authCode");

		Intent intent = getIntent();
		if(intent.getStringExtra("site").equals("PASSCODE")){

            site = "PASSCODE";
			// RP SDK 생성
			mAuth = new FIDOAuthentication(AuthenticationActivity.this, fidoCallbackResult);
			mAuth.startAuthentication(userId);
		}else{

            site = "FINGER";
			// RP SDK 생성
			mAuth = new FIDOAuthentication(fidoCallbackResult, AuthenticationActivity.this);
			mAuth.startAuthentication(userId);
		}

		
	}
	
	private FIDOCallbackResult fidoCallbackResult = new FIDOCallbackResult() {

		@Override
		public void onFIDOResult(int requestCode, boolean result, FidoResult fidoResult) {
			
			if(requestCode == FIDORequestCode.REQUEST_CODE_AUTH) {

				if(fidoResult.getErrorCode() == FidoResult.RESULT_SUCCESS){
					String token = mAuth.getToken();
					System.out.println("Token Value : " + token);

					isSuccess = true;

                    try {
//                        devId = JTdecoded(token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    verrifiedUser(token, site);

				}else{
					// 실패 사유
					switch(fidoResult.getErrorCode()){
					case FidoResult.RESULT_USER_CANCEL:
						// 에러코드 999: 사용자 취소
					case FidoResult.ERROR_JSON_PARSER:
						// 에러코드 1005: 서버로부터 받은 메세지 파싱하다 에러 발생
					case FidoResult.ERROR_CANNOT_USE_FIDO:
						// 에러코드 1007: 지원하지 않는 단말기
					case 13006:
						// 에러코드 13006: -인증 시 등록 된 아이디가 없는 사유로 서버에서 전달하는 에러코드
						//				-등록 요청 진행
					case 19019:
						// 에러코드 19019: -아이디 누락으로 인한 실패 사유로 서버에서 전달하는 에러코드
						//              -인증 요청 할 때, UserName을 입력하지 않음
					case FidoResult.ERROR_UNTRUSTED_FACET_ID:
						// 에러코드 2001: -등록되지 않은 3rd App으로 진행
						//			   -FIDO Server에 FacetID 를 등록해야 함.
					case FidoResult.ERROR_NO_SUITABLE_AUTHENTICATOR:
						// 에러코드 2002: 적합한 인증장치가 없음
					case FidoResult.ERROR_REMOVE_KEYSTORE_IN_SW:
						// 에러코드 2004: -잠금 화면 설정 변경으로 인한 초기화 발생
						//             -해지 후 재등록 진행
					case FidoResult.ERROR_NETWORK_STATE:
						// 에러코드 4001: -네트워크 오류
						//			   -fidoResult.getDescription() 을 통하여 자세한 오류 내용을 알 수 있음
					case FidoResult.PW_NOT_MATCH_BIO_FINGER:

						// 에러코드 5000: 지문 인증 실패
					case FidoResult.ERROR_UNKNOWN:
						// 에러코드 9999: FIDO 로직 수행 중, 알수 없는 에러 발생
//						showDialog("FAIL", fidoResult.getDescription() + "(" +  fidoResult.getErrorCode() + ")");
					break;
					case FidoResult.ERROR_NOT_EXIST_USER_DATA:	// 에러코드 1009: -인증하려는 아이디의 데이터가 존재하지 않는 경우
						// -동일 아이디로 해지 후 재등록 요청 해야 함
//						showDialog("FAIL", "단말에 사용자의 데이터가 없습니다. 해지 후 재등록 해주세요.(" + fidoResult.getErrorCode() + ")");
					break;

						case FidoResult.ERROR_MAX_LOCK_COUNT_OVER:
							Toast.makeText(AuthenticationActivity.this, "초과하였습니다", Toast.LENGTH_SHORT).show();
							break;
					default:
//						showDialog("FAIL", fidoResult.getDescription() + "(" +  fidoResult.getErrorCode() + ")");
					}
				}
			}
		}
	};


    private void verrifiedUser(String token, String site){

        FidoReq fidoReq = new FidoReq(authCode, devId, "", site, "", token, "A", userId);
        Call<ModelVerifiedFido> getReList = RetrofitClient.getInstance(AuthenticationActivity.this).getService().getVerifyData("application/json",  fidoReq);
        getReList.enqueue(new Callback<ModelVerifiedFido>() {
            @Override
            public void onResponse(Call<ModelVerifiedFido> call, Response<ModelVerifiedFido> response) {
                if (response.code() == 200) {

                    SharedPreferenceUtil.getInstance(AuthenticationActivity.this).putTokenA("aToken", response.headers().get("Authorization"));
                    SharedPreferenceUtil.getInstance(AuthenticationActivity.this).putAuthCode("authCode", response.body().getContent().getAuthCode());

//                    Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();

                }
            }
            @Override
            public void onFailure(Call<ModelVerifiedFido> call, Throwable t) {
            }
        });
    }

//    public String JTdecoded(String JWTEncoded) throws Exception {
//        String[] split = JWTEncoded.split("\\.");
//
//        Gson gson = new Gson();
//        LoginFidoTokenData data = gson.fromJson(getJson(split[1]), LoginFidoTokenData.class);
//
//        return data.deviceid;
//    }
//
//    private  String getJson(String strEncoded) throws UnsupportedEncodingException {
//        byte[] decode =  android.util.Base64.decode(strEncoded,  Base64.URL_SAFE);
//        return new String(decode, "utf-8");
//    }
	
	//=======================Activity에서 쓰이는 Alert창==============================
	
//	private void showDialog(String title, String body) {
//		Intent alertIntent = new Intent(this, AlertDlg.class);
//		alertIntent.putExtra("msg", body);
//		startActivityForResult(alertIntent, ALERT_REQUEST);
//	}

	private boolean isSuccess = false;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == ALERT_REQUEST && isSuccess == true){

//			Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
//			startActivity(intent);

		}
	}

}