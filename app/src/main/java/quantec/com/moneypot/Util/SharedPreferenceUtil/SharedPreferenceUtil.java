package quantec.com.moneypot.Util.SharedPreferenceUtil;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    public static final String PREFERENCE_NAME = "ZzimCount";
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
            sharedPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharedPreferenceModule;
    }

    public void putIntZzimCount(String key, int count){
        editor.putInt(key, count);
        editor.apply();
    }

    public int getIntExtra(String key){
        return sharedPreferences.getInt(key, 0);
    }

}

