package com.quantec.moneypot.magickeypad;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dreamsecurity.magicvkeypad.MagicVKeypadType;
import com.dreamsecurity.magicvkeypad.viewtype.MagicVKeypadOnClickInterface;
import com.dreamsecurity.magicvkeypad.viewtype.MagicVKeypadTO;
import com.dreamsecurity.magicvkeypad.viewtype.MagicVKeypadView;

import com.quantec.moneypot.R;

public class HalfModeActivity extends AppCompatActivity implements View.OnClickListener {
    private MagicVKeypadView magicVKeypadView = null;
    private EditText insertChar = null;
    private EditText insertNum = null;

    private String charFieldName = "insertChar";
    private String numFieldName = "insertNum";

    private boolean useE2E = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halfmode_layout);

        insertChar = (EditText) findViewById(R.id.char_editView);
        insertNum = (EditText) findViewById(R.id.num_editView);

        insertChar.setOnClickListener(this);
        insertNum.setOnClickListener(this);

        findViewById(R.id.ok_btn).setOnClickListener(this);
        findViewById(R.id.cancel_btn).setOnClickListener(this);

        // 입력창 클릭 시 소프트 키보드 나오지 않도록 설정
        insertChar.setInputType(InputType.TYPE_NULL);
        insertNum.setInputType(InputType.TYPE_NULL);

        settingKeypad();

    }
    private void settingKeypad() {

        //키패드 라이선스 값 (드림시큐리티에게 패키지명 전달 후 받은 라이선스 값)
//        String strLicense = "TCLfnDs2pLe3tnhmIy0592DwpEldRx081Zzini6TrvY1JtEFnfiRoHbI+NGkkSwZ8i1TxfWc+/FwSFu9cz9IlwabeDQZC8qp9bhwD6g21Ht0LEugC1ruTjk8YgiBAtTfUFtOq5YjsOmUulbvsffA2zTwFydyQQil1pPn+u6UbZehBYtv5nXoRbvlY86HF/s5AnXPaZJZc0CjbWzGqiHwblxy1Ij7zEqt1hHJ2LbaI1Dz9zkrGaFlVodDSK6VQbpxzNKm259dupN5S0MedbqauKTMzlM3Wj6fRNEy5lV8guIJJXghrPRAUs/RkARLYeSIVKKPPzw05uQU6QPuPw1V9w==";
        String strLicense = "JHHtWKNNqIO+unfdYg22ZZTNo4uH7NiQXEnI3Ksflt2zu0+6h9GJIeR4g1OaKxeASg0BQLSTW5wtrTXdABLPhtbRgKkXCbpFS9wge1PDJtz5dk3FizyRu9824LGQiTzD4vRazbk/8Lss5l9SyqB4myt25ROnVx9XbPW3Kw3DZDE4XxdUkrycoaLkj+hhEpgxvWuV7pF0ZBM2Pu4a7S6ag0kZiQmF6hKYsqL2i1skHwyV/G3TNLN5sYyJgRFqlDCl7CJMNzCwrZj2bLv3/2NIVvWZeBswSVDjriHDvCsOPkQvbzLelRralhpfjlT70FxFXDNoWHLaZ7BhW0EpYpR3XQ==";

        magicVKeypadView = new MagicVKeypadView(this, magicVKeypadOnClickInterface);
        int initResult = magicVKeypadView.initializeMagicVKeypad(strLicense);

        if (initResult == 0) {
            //onCreate 내에서 설정해야함
//            magicVKeypadView.setNoScreenShot();
            if(useE2E) setPublickeyForE2E();
        } else {
            Toast.makeText(getApplicationContext(), "라이선스 검증 실패로 인한 사용 불가", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //E2E를 위한 공개키 설정
    private void setPublickeyForE2E(){
        String publickey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApfm0Nt/8mU/lpe0HgyQ6U6qo01kuNZCRlZjh+BE83cYn9GczsSp1oUcTAXoVfpEr8X+ydjCgsnfF0Y1tTI6vvpKd7MDxdZjGcgvBZ9B5+WXhiB5Bq1ZKhso6Y8e3GZVEbUUf6t8De0DuwtNrMj6O8F0GHqQghzOV4TbynspAwmMA6VBdA8oz2IXgqWyfG3Mvw51/Re6ok8hnlodEHrmxxK+SigmlefoU66gbFObIw7JZ/+IwdVXmS38AyixvlCAlCLGg0PdYoJ0YNmtHoEDoeBzHBZOIj+ftVJgesIE/IeRQGCuVbPtkSRIsra8dS0cbRfpoFmWlBlIW+41ArOxH2QIDAQAB";

        try {
            magicVKeypadView.setPublickeyForE2E(publickey, false);
        }catch (Exception e){
            Log.d("MagicVKeypad", "setPublickeyForE2E Error : " + e.getMessage());
        }
    }

    //문자키패드 옵션 설정 및 키패드 열기
    private void openCharKeypad() {

        //기존에 열려있는 키패드 종료
        if(magicVKeypadView.isKeyboardOpen()){
            magicVKeypadView.closeKeyboard();
        }

        //배경 클릭 가능 여부 설정 (false 일 경우 키패드 이외의 부분 클릭 불가)
//        magicVKeypadView.setCharBackgroundClickable(false);

        // masking 설정
        magicVKeypadView.setCharMasking(MagicVKeypadType.NoMasking);

        // 입력 글자수 제한
        magicVKeypadView.setCharMaxLength(10);

        //사용할 키패드의 fieldName 설정(settingKeyboard() 호출 전 무조건 설정 해야함)
        magicVKeypadView.setFieldName(charFieldName);

        //키패드 열기
        magicVKeypadView.settingKeyboard(MagicVKeypadType.charKeypad);

    }

    //숫자키패드 옵션 설정
    private void openNumKeypad() {

        //기존에 열려있는 키패드 종료
        if(magicVKeypadView.isKeyboardOpen()){
            magicVKeypadView.closeKeyboard();
        }

        //배경 클릭 가능 여부 설정(false 일 경우 키패드 이외의 부분 클릭 불가)
        magicVKeypadView.setNumBackgroundClickable(false);

        //masking 설정
        magicVKeypadView.setNumMasking(MagicVKeypadType.AllMasking);

        // 입력 글자수 제한
        magicVKeypadView.setNumMaxLength(10);

        // 숫자키패드 재배열 막기
        magicVKeypadView.setNumNoUseReplace();

        //사용할 키패드의 fieldName 설정(settingKeyboard() 호출 전 무조건 설정 해야함)
        magicVKeypadView.setFieldName(numFieldName);

        //키패드 열기
        magicVKeypadView.settingKeyboard(MagicVKeypadType.numKeypad);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //문자 키패드 입력창
            case R.id.char_editView:
                openCharKeypad();
                break;

            //숫자 키패드 입력창
            case R.id.num_editView:
                openNumKeypad();
                break;

            //확인 버튼
            case R.id.ok_btn:
                // 키패드가 열려있는지 확인
                if (magicVKeypadView.isKeyboardOpen()) {
                    // 암호화 된 값을 받아서 복호화 시킨다.
                    if (magicVKeypadView.getEncryptData() != null) {
                        //공개키 설정을 안했을 경우 대칭키 암호화 한 데이터가 리턴되며, decryptData를 이용하여 복호화 할수 있다.
                        if(useE2E == false){
                            byte[] decData = magicVKeypadView.decryptData(magicVKeypadView.getEncryptData());
                            Toast.makeText(getApplicationContext(), new String(decData), Toast.LENGTH_SHORT).show();
                        }
                    }
                    // 키패드 닫기
                    magicVKeypadView.closeKeyboard();
                }
                break;

            //취소 버튼
            case R.id.cancel_btn:
                //키패드 열려있는지 확인
                if (magicVKeypadView.isKeyboardOpen()) {
                    // 키패드를 닫는다.
                    magicVKeypadView.closeKeyboard();

                    if (magicVKeypadView.getFieldName().equals(numFieldName)) {
                        insertNum.setText("");
                    } else {
                        insertChar.setText("");
                    }
                } else {
                    //키패드와의 연결을 종료하고 모든 값을 초기화한다.
                    magicVKeypadView.finalizeMagicVKeypad();
                    finish();
                }

                break;
        }
    }

    //키패드 클릭 시 들어오는 Callback
    private MagicVKeypadOnClickInterface magicVKeypadOnClickInterface = new MagicVKeypadOnClickInterface() {
        @Override
        public void onMagicVKeypadClick(MagicVKeypadTO magicVKeypadTO) {

            // 해당 focus일 때 어떤 행동 할 건지 설정
            // 키패드 내 취소버튼 클릭 시 호출
            if (magicVKeypadTO.getFocus() == MagicVKeypadType.CancelButton) {
                magicVKeypadView.closeKeyboard();

                if (magicVKeypadView.getFieldName().equalsIgnoreCase(numFieldName))
                    insertNum.setText("");
                else
                    insertChar.setText("");
            }
            // 캐패드 내 확인 버튼 클릭 시 호출
            else if (magicVKeypadTO.getFocus() == MagicVKeypadType.OKButton) {

                // 암호화 된 값 받기
                // setPublickeyForE2E(String publickey , boolean basyncEnc)
                // 위 API를 호출하고 basyncEnc 를 false로 설정했을 경우 RSA 공개키로 암호화 필요
                byte[] encData = magicVKeypadTO.getEncryptData();
                if (encData != null) {

                    //공개키 설정을 안했을 경우 (setPublicKeyForE2E 호출 안함) 대칭키 암호화 된 데이터가 리턴되며, decryptData를 이용하여 복호화 할수 있다.
                    if(useE2E == false){
                        byte[] decData = magicVKeypadView.decryptData(magicVKeypadTO.getEncryptData());
                        Toast.makeText(getApplicationContext(), new String(decData), Toast.LENGTH_SHORT).show();
                    }
                }
                magicVKeypadView.closeKeyboard();

            }
            //숫자, 문자, space 클릭 시 호출
            else if (magicVKeypadTO.getFocus() == MagicVKeypadType.CharNumButton) {

                //암호화된 데이터 가져오기
                //magicVKeypadTO.getFocus()가 OKButton이 아닐 경우 , 대칭키로 암호화한 입력 값이 들어옴
                byte[] encData = magicVKeypadTO.getEncryptData();

                if (magicVKeypadTO.getFieldName().equalsIgnoreCase(numFieldName)) {
                    String dummyData = magicVKeypadTO.getDummyData();
                    if (dummyData != null) {
                        insertNum.setText(dummyData);
                    }
                } else if (magicVKeypadTO.getFieldName().equalsIgnoreCase(charFieldName)) {
                    String dummyData = magicVKeypadTO.getDummyData();
                    if (dummyData != null) {
                        insertChar.setText(dummyData);

                    }
                }
            }
        }
    };

    //가로모드 세로모드 설정
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // 가로모드, 세로모드 사용시 반드시 설정
        magicVKeypadView.configurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (magicVKeypadView.isKeyboardOpen()) {
            // 키패드를 닫는다.
            magicVKeypadView.closeKeyboard();

            if (magicVKeypadView.getFieldName().equals(numFieldName)) {
                insertNum.setText("");
            } else {
                insertChar.setText("");
            }
        } else {
            //키패드와의 연결을 종료하고 모든 값을 초기화한다.
            magicVKeypadView.finalizeMagicVKeypad();
            finish();
        }
    }


}

