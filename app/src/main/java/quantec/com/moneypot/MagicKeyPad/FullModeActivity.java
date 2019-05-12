package quantec.com.moneypot.MagicKeyPad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dreamsecurity.magicvkeypad.MagicVKeypadType;
import com.dreamsecurity.magicvkeypad.activitytype.MagicVKeypadActivity;
import com.dreamsecurity.magicvkeypad.activitytype.DSCharActivity;

import quantec.com.moneypot.R;

public class FullModeActivity extends Activity implements View.OnClickListener {
    private EditText insertNum = null;
    private EditText insertChar = null;
    private MagicVKeypadActivity magicVKeypad = null;
    private boolean useE2E = true;

    private Context mContext  = null;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fullmode_layout);

        mContext = this;

        // 숫자 입력창
        insertNum = (EditText) findViewById(R.id.num_start_btn);
        // 입력창 클릭 시 소프트 키보드 나오지 않도록 설정
        insertNum.setInputType(InputType.TYPE_NULL);
        insertNum.setOnClickListener(this);

        // 문자 입력창
        insertChar = (EditText) findViewById(R.id.char_start_btn);
        // 입력창 클릭 시 소프트 키보드 나오지 않도록 설정
        insertChar.setInputType(InputType.TYPE_NULL);
        insertChar.setOnClickListener(this);

        findViewById(R.id.back_btn).setOnClickListener(this);

        settingKeypad(this);
    }

    //키패드 선언 및 라이선스 검증
    private void settingKeypad(Context mContext) {
        magicVKeypad = new MagicVKeypadActivity(mContext);
        // 라이선스 검증 할 값
//        String strLicense = "TCLfnDs2pLe3tnhmIy0592DwpEldRx081Zzini6TrvY1JtEFnfiRoHbI+NGkkSwZ8i1TxfWc+/FwSFu9cz9IlwabeDQZC8qp9bhwD6g21Ht0LEugC1ruTjk8YgiBAtTfUFtOq5YjsOmUulbvsffA2zTwFydyQQil1pPn+u6UbZehBYtv5nXoRbvlY86HF/s5AnXPaZJZc0CjbWzGqiHwblxy1Ij7zEqt1hHJ2LbaI1Dz9zkrGaFlVodDSK6VQbpxzNKm259dupN5S0MedbqauKTMzlM3Wj6fRNEy5lV8guIJJXghrPRAUs/RkARLYeSIVKKPPzw05uQU6QPuPw1V9w==";

        String strLicense = "JHHtWKNNqIO+unfdYg22ZZTNo4uH7NiQXEnI3Ksflt2zu0+6h9GJIeR4g1OaKxeASg0BQLSTW5wtrTXdABLPhtbRgKkXCbpFS9wge1PDJtz5dk3FizyRu9824LGQiTzD4vRazbk/8Lss5l9SyqB4myt25ROnVx9XbPW3Kw3DZDE4XxdUkrycoaLkj+hhEpgxvWuV7pF0ZBM2Pu4a7S6ag0kZiQmF6hKYsqL2i1skHwyV/G3TNLN5sYyJgRFqlDCl7CJMNzCwrZj2bLv3/2NIVvWZeBswSVDjriHDvCsOPkQvbzLelRralhpfjlT70FxFXDNoWHLaZ7BhW0EpYpR3XQ==";

        // 라이센스 검증 및 키패드 초기화 -1 : 이미 라이선스 검증 함, 0 : 성공 , 그 외 : 검증 실패
        int initResult = magicVKeypad.initializeMagicVKeypad(strLicense);

        if(initResult == 0){
            if(useE2E) setPublickeyForE2E();
        }
        else {
            Toast.makeText(mContext, "라이선스 검증 실패로 인한 사용 불가", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //E2E 를 위한 공개키 설정
    private void setPublickeyForE2E(){
        String pubkey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApfm0Nt/8mU/lpe0HgyQ6U6qo01kuNZCRlZjh+BE83cYn9GczsSp1oUcTAXoVfpEr8X+ydjCgsnfF0Y1tTI6vvpKd7MDxdZjGcgvBZ9B5+WXhiB5Bq1ZKhso6Y8e3GZVEbUUf6t8De0DuwtNrMj6O8F0GHqQghzOV4TbynspAwmMA6VBdA8oz2IXgqWyfG3Mvw51/Re6ok8hnlodEHrmxxK+SigmlefoU66gbFObIw7JZ/+IwdVXmS38AyixvlCAlCLGg0PdYoJ0YNmtHoEDoeBzHBZOIj+ftVJgesIE/IeRQGCuVbPtkSRIsra8dS0cbRfpoFmWlBlIW+41ArOxH2QIDAQAB";

        try {

            magicVKeypad.setPublickeyForE2E(pubkey, false);
        }catch (Exception e){
            Log.d("MagicVKeypad", "setPublickeyForE2E Error : " + e.getMessage());
        }
    }


    //문자 키패드 옵션 설정 및 키패드 열기
    private void startNumKeypad() {
        // masking방식 지정
        magicVKeypad.setMasking(MagicVKeypadType.DefaultMasking);
        // title 설정
        magicVKeypad.setTitleText("숫자 키패드");
        // 가로모드 방지
//        magicVKeypad.setPortraitFixed();
        // 숫자키패드 재배열 X
        magicVKeypad.setNoUseReplace();
        // 스크린 샷 X
        magicVKeypad.setNoScreenshot();
        // 입력 글자수 제한 (공개키를 설정할 경우 최대 길이 120)
        magicVKeypad.setMaxLength(10);
        // 숫자키패드 설정 셋팅 및 열기
        magicVKeypad.startNumKeypad(MagicVKeypadType.numKeypad);
    }

    //키패드 옵션 설정 및 키패드 열기
    private void startCharKeypad() {
        // masking 방식 지정
        magicVKeypad.setMasking(MagicVKeypadType.DefaultMasking);
        // title 설정
        magicVKeypad.setTitleText("문자 키패드");
        // 가로모드 막기
//        magicVKeypad.setPortraitFixed();
        // 스크린 샷 X
        magicVKeypad.setNoScreenshot();
        // 입력 글자수 제한
        magicVKeypad.setMaxLength(10);
        // 문자키패드 열기
        magicVKeypad.startCharKeypad(MagicVKeypadType.charKeypad);
    }

    // 키패드 연결 및 Activity 종료
    public void finishActivity() {
        // 키패드 연결 종료
        magicVKeypad.finalizeMagicVKeypad();
        finish();

    }

    // Activity방식 값 전달받기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 숫자키패드
            if (requestCode == MagicVKeypadType.numKeypad) {
                //더미 값 받기
                insertNum.setText(magicVKeypad.getDummyData(data));

                // 암호화 된 값 받기
                // setPublickeyForE2E(String publickey , boolean basyncEnc)
                // 위 API를 호출하고 basyncEnc 를 false로 설정했을 경우 RSA 공개키로 암호화 필요
                byte[] encData = magicVKeypad.getEncryptData(data);

                if (encData != null) {
                    if(useE2E == false){
                        // 암호화 된 값 복호화
                        byte[] decData = magicVKeypad.decryptData(encData);
                        Toast.makeText(mContext , "복호화 된 데이터 : " + new String(decData) , Toast.LENGTH_SHORT).show();
                    }
                } else
                    insertNum.setText("");
            }
            // 문자키패드
            else if (requestCode == MagicVKeypadType.charKeypad) {
                //더미 값 받기
                insertChar.setText(magicVKeypad.getDummyData(data));

                // 암호화 된 값 받기
                byte[] encData = magicVKeypad.getEncryptData(data);

                if (encData != null) {
                    if(useE2E == false){
                        // 암호화 된 값 복호화
                        byte[] decData = magicVKeypad.decryptData(encData);
                        Toast.makeText(mContext , "복호화 된 데이터 : " + new String(decData) , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        // 취소 버튼을 눌렀을 경우
        else if (resultCode == RESULT_CANCELED) {
            if (requestCode == MagicVKeypadType.numKeypad) {
                insertNum.setText("");
            } else if (requestCode == MagicVKeypadType.charKeypad) {
                insertChar.setText("");
            }
        }
    }


    @Override
    public void onBackPressed() {
        finishActivity();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //숫자 키패드 옵션 설정 및 열기
            case R.id.num_start_btn:
                startNumKeypad();
                break;
            //문자 키패드 옵션 설정 및 열기
            case R.id.char_start_btn:
                startCharKeypad();
                break;
            case R.id.back_btn:
                finishActivity();
                break;
        }
    }

}
