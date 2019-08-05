package quantec.com.moneypot;

import android.app.Application;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;

import java.util.Random;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import quantec.com.moneypot.Activity.Main.Foreground;

public class mApplication extends Application {

    public static String UID = "";

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);

        Realm.init(this);


        SharedPreferences mid = getSharedPreferences("user", MODE_PRIVATE);
        if (mid.getString("mid", "empty").equals("empty")) {

            Random rnd =new Random();
            StringBuffer buf = new StringBuffer();
            for(int i=0;i<20;i++){
                if(rnd.nextBoolean()){
                    buf.append((char)((int)(rnd.nextInt(26))+97));
                }else{
                    buf.append((rnd.nextInt(10)));
                }
            }

            SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("mid", buf.toString());
            editor.apply();

            UID = mid.getString("mid", "empty");

        }else{
            UID = mid.getString("mid", "empty");
        }

    }
}
