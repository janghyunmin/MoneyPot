package com.quantec.moneypot.util.NetworkStateCheck;

import android.content.Context;
import android.net.ConnectivityManager;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NetworkStateCheck {

    private static ConnectivityManager connectivityManager = null;

    public static boolean getNetworkState(Context context){
        connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()){
//            Toast.makeText(ActivityIntro.this, "인터넷이 연결되어 있습니다.", Toast.LENGTH_SHORT).show();
            return true;
        }else{
//            Toast.makeText(ActivityIntro.this, "인터넷이 끊겼습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}
