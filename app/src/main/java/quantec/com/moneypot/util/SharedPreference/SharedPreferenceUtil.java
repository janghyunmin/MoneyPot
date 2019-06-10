package quantec.com.moneypot.util.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    public static final String PREFERENCE_MPot ="moneyPot";

    private static SharedPreferenceUtil sharedPreferenceModule = null;
    private static Context mContext;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static SharedPreferenceUtil getInstance(Context context) {
        mContext = context;

        if(sharedPreferenceModule == null) {
            sharedPreferenceModule = new SharedPreferenceUtil();
        }

        if(sharedPreferences == null) {
            sharedPreferences = mContext.getSharedPreferences(PREFERENCE_MPot, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharedPreferenceModule;
    }

    public void putUserId(String key, String id){
        editor.putString(key, id);
        editor.apply();
    }

    public void putTokenA(String key, String token) {
        editor.putString(key, token);
        editor.apply();
    }

    public void putUserCid(String key, String cid){
        editor.putString(key, cid);
        editor.apply();
    }

    public void putAuthCode(String key, String authcode){
        editor.putString(key, authcode);
        editor.apply();
    }

    public void putIntZzimCount(String key, int count){
        editor.putInt(key, count);
        editor.apply();
    }

    public void putUserHpNumber(String key, String hpNumber){
        editor.putString(key, hpNumber);
        editor.apply();
    }

    public void putFingerState(String key, boolean state){
        editor.putBoolean(key, state);
        editor.apply();
    }

    public int getIntExtra(String key){
        return sharedPreferences.getInt(key, 0);
    }

    public String getStringExtra(String key){
        return sharedPreferences.getString(key, "");
    }

    public boolean getStateExtra(String key){
        return sharedPreferences.getBoolean(key, false);
    }

}

