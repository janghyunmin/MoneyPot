package quantec.com.moneypot.Util.DeEncryptUtil;

import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.crypto.NoSuchPaddingException;

import quantec.com.moneypot.Activity.Intro.CryptLib;

public class EncryptUtil {

    private static EncryptUtil encryptUtil = null;

    public static EncryptUtil getInstance() {
        if(encryptUtil == null) {
            encryptUtil = new EncryptUtil();
        }
        return encryptUtil;
    }

    public String EncryptToDecrypt(String decryptText, String time){

        Log.e("바디값", "값 : "+ decryptText);

        String preKey = reverseString(time.substring(2,10));
        String postKey = "%^^&&@(*&@##)@)@``~~" + preKey;

        CryptLib cryptLib = null;
        try {
            cryptLib = new CryptLib();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        String encryptText = null;
        try {
            encryptText = cryptLib.encryptPlainTextWithRandomIV(decryptText, postKey, true);
            }catch (Exception e) {
               e.printStackTrace();
            }
        return encryptText;
    }

    public static String reverseString(String s) {
        return ( new StringBuffer(s) ).reverse().toString();
    }
}
