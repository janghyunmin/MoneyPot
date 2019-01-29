package quantec.com.moneypot.Activity.Intro;

//import org.apache.commons.codec.binary.Base64;

import android.util.Base64;
import android.util.Log;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class EncrypteUtil {

    // md5 only encrypt
    public static String encryptMd5(String password) throws NoSuchAlgorithmException {
        final byte[] defaultBytes = password.getBytes();

            final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
            md5MsgDigest.reset();
            md5MsgDigest.update(defaultBytes);
            final byte messageDigest[] = md5MsgDigest.digest();
            final StringBuffer hexString = new StringBuffer();
            for (final byte element : messageDigest) {
                final String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString + "";

    }



    private static IvParameterSpec getIvParam() throws UnsupportedEncodingException {
        String initVector = "quantecIVpKey!#$"; // 16 bytes IV

            return new IvParameterSpec(initVector.getBytes("UTF-8"));

    }
    private static Key getAesKey() throws UnsupportedEncodingException {
        String keyString = "quantecAesKey!*$"; // 128 bit key

            return new SecretKeySpec(keyString.getBytes("UTF-8"), "AES");

    }

//    public static String encryptAes(String value, boolean isEncoding) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, getAesKey(), getIvParam());
//
//            byte[] encrypted = cipher.doFinal(value.getBytes());
//            String result = Base64.encodeBase64String(encrypted);
//            return isEncoding ? URLEncoder.encode(result, "UTF-8") : result;
//
//    }

//    public static String decryptAes(String encrypted, boolean isEncoding) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//
//            encrypted = isEncoding ? URLDecoder.decode(encrypted, "UTF-8") : encrypted;
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.DECRYPT_MODE, getAesKey(), getIvParam());
//
//            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
//            return new String(original);
//    }


    // RSA 512bit key-pair
    // https://offbyone.tistory.com/346
    public static KeyPair genRSAKeyPair() throws NoSuchAlgorithmException {

            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator gen;

            gen = KeyPairGenerator.getInstance("RSA");
            gen.initialize(512, secureRandom);
            KeyPair keyPair = gen.genKeyPair();

            return keyPair;

    }

    public static PrivateKey genRSAKeyPrivate(String base64Str) throws NoSuchAlgorithmException, InvalidKeySpecException {

//            KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(base64Str.trim()));  // PKCS8 decode the encoded RSA private key
        KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(base64Str.trim(), Base64.NO_WRAP));  // PKCS8 decode the encoded RSA private key
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePrivate(keySpec);

    }
    public static PublicKey genRSAKeyPublic(String base64Str) throws NoSuchAlgorithmException, InvalidKeySpecException {
//            KeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(base64Str.trim()));  // PKCS8 decode the encoded RSA private key
        KeySpec keySpec = new X509EncodedKeySpec(Base64.decode(base64Str.trim(), Base64.NO_WRAP));  // PKCS8 decode the encoded RSA private key
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return (RSAPublicKey) kf.generatePublic(keySpec);
    }



    /**
     * Public Key로 RSA 암호화를 수행합니다.
     * @param plainText 암호화할 평문입니다. publicKey 공개키 입니다.
     * @return
     */

    public static String encryptRSA(String plainText, Key pKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pKey);
            byte[] bytePlain = cipher.doFinal(plainText.getBytes());
//            String encrypted = Base64.encodeBase64String(bytePlain);//Base64.getEncoder().encodeToString(bytePlain); in java.util.Base64
        String encrypted = Base64.encodeToString(bytePlain, Base64.NO_WRAP);//Base64.getEncoder().encodeToString(bytePlain); in java.util.Base64
        return encrypted;
    }


    // 암호화를 publickey로 했으면 복호화는 privatekey로 해야 함.. 반대도 마찬가지.

    /**
     * Private Key로 RAS 복호화를 수행합니다.
     *
     * @param encrypted 암호화된 이진데이터를 base64 인코딩한 문자열 입니다. privateKey 복호화를 위한 개인키 입니다.
     * @return
     * @throws Exception
     */

    public static String decryptRSA(String encrypted, Key pKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
            Cipher cipher = Cipher.getInstance("RSA");
//            byte[] byteEncrypted = Base64.decodeBase64(encrypted);//Base64.getDecoder().decode(encrypted.getBytes()); in java.util.Base64
        byte[] byteEncrypted = Base64.decode(encrypted.trim(), Base64.DEFAULT);//Base64.getDecoder().decode(encrypted.getBytes()); in java.util.Base64
        cipher.init(Cipher.DECRYPT_MODE, pKey);
            byte[] bytePlain = cipher.doFinal(byteEncrypted);
            String decrypted = new String(bytePlain, "utf-8");
            return decrypted;
    }
}

