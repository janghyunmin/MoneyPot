package com.quantec.moneypot.util.Permissions;

import androidx.core.app.ActivityCompat;

import permissions.dispatcher.PermissionUtils;
import com.quantec.moneypot.activity.Login.resist.ActivityResistIntro;

public class PermissionsPhone {
    private static final int PHONESTATE = 1;
    private static final String[] PERMISSION_PHONESTATE = new String[]{"android.permission.READ_PHONE_STATE"};

    private PermissionsPhone(){
    }

    public static void getPhoneState(ActivityResistIntro target){
        if(PermissionUtils.hasSelfPermissions(target, PERMISSION_PHONESTATE)){
            target.getPhoneNumber();
        }else{
            ActivityCompat.requestPermissions(target, PERMISSION_PHONESTATE, PHONESTATE);
        }
    }

    public static void onRequestPermissionsResult(ActivityResistIntro target, int requestCode, int[] grantResults){

        switch (requestCode) {
            case PHONESTATE:
                if(PermissionUtils.getTargetSdkVersion(target) < 23 && !PermissionUtils.hasSelfPermissions(target, PERMISSION_PHONESTATE)){
                    return;
                }
                if(PermissionUtils.verifyPermissions(grantResults)){
                    target.getPhoneNumber();
                }
                break;
            default:
                break;
        }
    }
}
